package org.phypo.PPg.Sql;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.phypo.PPg.PPgData.DataChgNotifier;
import org.phypo.PPg.PPgData.DataChgNotifierClient;
import org.phypo.PPg.PPgData.HistoryDuration;
import org.phypo.PPg.PPgFX.DataViewObj;
import org.phypo.PPg.PPgUtils.Log;
import javafx.application.Platform;

@SuppressWarnings("unchecked")

//********************************************************************

public abstract class SqlTableReader< KEY, OBJ extends DataViewObj>  implements DataChgNotifierClient {

	public HistoryDuration         cDuration     = HistoryDuration.Day1;
	protected HashMap<KEY, OBJ>    cMap          = new HashMap<>();
	protected HashSet<KEY>         cAlive        = null;

	
	protected void killZombieAtLoad() {
		cAlive = new HashSet<>();
	}
	//-------------------------------------------------
	//	protected ArrayList<SqlTableReaderObserver<KEY, OBJ>> cObservers=new ArrayList<>();

	//	void registerObservers( SqlTableReaderObserver<KEY, OBJ>  iObs) {
	//		cObservers.add( iObs );
	//	}

	//-------------------------------------------------
	//	MyTableFX( String iTitle, boolean iFilter){
	//	super(iTitle, iFilter);
	//}
	/*
	public  void          myViewClearLines()         { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) lObs.myViewClearLines(); }
	public  OBJ           myViewAddLine(OBJ iObj)    { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) return lObs.myViewAddLine( iObj); }
	public  void          myViewRemoveLine(OBJ iObj) { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) lObs.myViewRemoveLine( iObj); }
	public  Iterator<OBJ> myViewGetIterator()        { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) return lObs.myViewGetIterator(); }
	public  void          myViewAutoResizeColumns()  { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) lObs.myViewAutoResizeColumns(); }
	public  void          myViewRefreshView()        { for( SqlTableReaderObserver<KEY, OBJ> lObs: cObservers) lObs.myViewRefreshView(); }
	 */
	public  abstract void          myViewClearLines();
	public  abstract OBJ           myViewAddLine(OBJ iObj);
	public  abstract void          myViewRemoveLine(OBJ  iObj );
	public  abstract Iterator<OBJ> myViewGetIterator();
	public  abstract void          myViewAutoResizeColumns();
	public  abstract void          myViewRefreshView();

	//-------------------------------------------------
	public void clearLines(){
		cMap.clear();
		myViewClearLines(); // DataView.clearLine
	}
	//-------------------------------------------------
	public OBJ addLine( OBJ iObj ) {
		OBJ lTmp = myViewAddLine( iObj);

		if( lTmp != null ) {
			cMap.put( (KEY)lTmp.getKey(), lTmp);
		}

		return lTmp;
	}
	//-------------------------------------------------
	public void removeObject( OBJ iObj, Iterator<OBJ> iIterObj ) {
		cMap.remove( (KEY)iObj.getKey());
		iIterObj.remove();
	}
	//-------------------------------------------------
	public void removeObject( OBJ iObj ) {
		myViewRemoveLine( iObj );
		cMap.remove((KEY)iObj.getKey());
	}
	//-------------------------------------------------
	OBJ getFromKey( KEY iKey ) {
		return cMap.get( iKey );
	}
	//-------------------------------------------------
	public abstract boolean hasViewRight();
	public abstract boolean hasModifRight(); 
	protected abstract boolean load(SqlConnex iConnex);
	public synchronized boolean callSyncLoad( SqlConnex iConnex ) { return load(iConnex);}


	//-------------------------------------
	String cName="Not define";
	public final String getTargetName() { return cName;}

	//HistoryDuration cDuration=HistoryDuration.Day1;

	long        cLastTimeStamp = 0;
	long        cTmpTimeStamp = 0;
	boolean     cFlagNeedReload=true;
	boolean     cFlagForceReload = false;

	//	implement DataChgNotifierClient
	@Override public boolean  getNeedReload()                        { return cFlagNeedReload; }
	@Override public void     setNeedReload(boolean iVal )           { cFlagNeedReload = iVal; }
	@Override public boolean  getForceReload()                       { return cFlagForceReload;}
	@Override public void     setForceReload(boolean iVal)           { cFlagForceReload = iVal;}
	@Override public long     getLastTimeStamp()                     { return cLastTimeStamp; }
	@Override public void     setLastTimeStamp( long iLastTimeStamp) { cLastTimeStamp = iLastTimeStamp;}
	@Override public void     razLastTimeStamp()                     { setLastTimeStamp(0);  }
	@Override public void     setTmpTimeStamp( long iVal)            { cTmpTimeStamp = iVal; }
	@Override public long     getTmpTimeStamp()                      { return cTmpTimeStamp;}



	//-------------------------------------------------
	public void reload( SqlConnex iConnect){
		//TableSqlFX.this.clearLines();
		razLastTimeStamp();
		Platform.runLater(() -> {
			clearLines();
		});
		callSyncLoad( iConnect );
	}
	//-------------------------------------------------
	//-------------------------------------------------
	//-------------------------------------------------
//	SqlTableReader(){
		//		super( iTitle, false);
//	}
	//-------------------------------------------------
	//-------------------------------------------------
	// AutoReload
	protected SqlTableReader(String iName){
		//		super( iTitle, false);
		if( iName != null )
			cName = iName;		
	}
	//-------------------------------------------------
	protected void activeUpdateTracking() {
		if( cName == null ) {
			Log.Fatal( "SqlTableReader activeUpdateTracking - Target is null\n" 
					+ Thread.currentThread().getStackTrace().toString());
			System.exit(1);
		}
		Log.Dbg( "=================== SqlTableReader activeUpdateTracking register : " + cName );
		
		DataChgNotifier.Instance().register( cName, this);
	}
	//-------------------------------------------------
	protected boolean loadFromDatabase(SqlConnex iConnex, String iStrSql) {
		
		prepareKillZombies();

		//				SqlConnex lConnex = JizAd.Instance().getDatabaseConnex();
		if( iConnex == null ) return false;

		if( (iConnex.sendCommandResult(iStrSql,new SqlResultsExecData(){			
			public void oneRow(int pNumResult, int pNumRow, ResultSetMetaData pMeta, ResultSet pResultSet) throws SQLException {	
				setLineFromRow( pResultSet );			
			}})
				) == false ){
			Log.Err( "SqlTableReader.loadFromDatabase <" +iStrSql+"> failed");
			return false;
		}

		killZombies();
		
		myViewRefreshView();
		
		return true;	
	}
	//-------------------------------------------------
	protected void prepareKillZombies() {
		if( cAlive != null ) {
			cAlive.clear();
		}
	}
	//-------------------------------------------------
	protected void killZombies() {

		if( cAlive != null ) {
			// Si un object n'est pas dans la liste il faut le detruire
			//===========================
		//	Platform.runLater(() -> {
				int lNbDel=0;
				Iterator<OBJ> lIter = myViewGetIterator();
				if( lIter == null ) {
					return ;
				}					
				while( lIter.hasNext()) {
					OBJ lObj = lIter.next();
					if( cAlive.contains(lObj.getKey()) == false ) {
						removeObject(lObj, lIter);
						lObj.setInvalid();
					}
				}
				if( lNbDel != 0 ) {
					Log.Dbg( "loadFromDatabase " + " delete " + lNbDel);
				}
//			});
			//===========================
		}	
	}
	//-------------------------------------------------
	abstract protected OBJ createLineFromRow( ResultSet pResultSet) throws SQLException;	
	//-------------------------------------------------
	private boolean setLineFromRow( ResultSet iResultSet)throws SQLException{

		OBJ lObj = createLineFromRow( iResultSet );
		if( lObj == null ) {
//			Log.Dbg( "setLineFromRow createLine failed " + this.getTargetName() );
			return false;
		}

		OBJ lOld = cMap.get( lObj.getKey());
		if( lOld != null ) {
				Log.Dbg3( "setLineFromRow SET " + cName +"."+ lOld.getKey() );
			
			lOld.setFrom( lObj );
		} else {
				Log.Dbg3( "setLineFromRow ADD "+ cName +"."+ lObj.getKey() );
			addLine( lObj );
		}

		if( cAlive != null ) {
			// Stocking all the living 
			cAlive.add((KEY)lObj.getKey());
		}

		return true;
	}
}

//*************************************************

