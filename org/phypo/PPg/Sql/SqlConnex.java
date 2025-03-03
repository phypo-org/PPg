package org.phypo.PPg.Sql;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Enumeration;

import org.phypo.PPg.PPgUtils.PPgTrace;
import org.phypo.PPg.PPgUtils.PPgUtils;
import org.phypo.PPg.Sql.SqlServer.ServerType;

// **********************************

public class SqlConnex {
	SqlServer    cServer = null;
	SqlServer    cServerBackup = null;
	SqlServer    cServerCurrent = null;


	PrintStream  cOStream = System.out;

	Connection   cConnect = null;
	char         cSeparator = '\0';
	boolean      cPrintColumnName=true;
	boolean      cPrintInfo=true;

	Statement    cCurrentStatement  = null;
	boolean      cCurrentHaveResult = false;

	public  Connection getConnection()       { return cConnect;      }
	public  SqlServer  getSqlServer()        { return cServer;        }
	public  SqlServer  getSqlServerBackup()  { return cServerBackup;  }
	public  SqlServer  getSqlServerCurrent() { return cServerCurrent; }
	public  boolean isClose() { return  cConnect == null;}

	//-----------------------
	public void setPrintStream( PrintStream  pOStream ){
		cOStream = pOStream;
	}
	//-----------------------
	public SqlConnex( SqlServer pServer, PrintStream pOStream){

		cServer  = pServer;
		if( pOStream != null )
			cOStream = pOStream;
	}

	public SqlConnex( SqlServer pServer, SqlServer pServerBackup, PrintStream pOStream){

		cServer  = pServer;
		cServerBackup  = pServerBackup;
		if( pOStream != null )
			cOStream = pOStream;
	}
	//-----------------------
	public SqlConnex( SqlServer pServer){

		cServer  = pServer;
	}
	//-----------------------
	public SqlConnex( SqlServer pServer, SqlServer pServerBackup ){

		cServer  = pServer;
		cServerBackup  = pServerBackup;
	}
	//-----------------------
	void setSeparator( char pSep )         { cSeparator = pSep; }
	void setPrintColumnName(boolean pFlag) { cPrintColumnName = pFlag; }
	void setPrintInfo( boolean pFlag)      { cPrintInfo  = pFlag; }

	//-----------------------
	private  boolean internalConnect( SqlServer pServer ){
	//	System.out.println("internalConnect" );

		if( pServer == null || !pServer.isComplete() )
			return false;
/*		System.err.print("================== internalConnect ============================");	
		System.err.print("================== internalConnect ============================");
		System.err.print("================== internalConnect ============================");
		Thread.dumpStack();
		System.err.print("================== internalConnect ============================");
		System.err.print("================== internalConnect ============================");
		System.err.print("================== internalConnect ============================");
*/
		try{

			switch( pServer.cServerType ) {
			case SYBASE:
				Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
				pServer.cUrl = new String("jdbc:sybase:Tds:"+pServer.cMachine+":"+pServer.cPort);
				break;
			case MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				pServer.cUrl = new String("jdbc:mysql://" +pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
				break;
			case MARIADB:
				Class.forName("org.mariadb.jdbc.Driver");
				pServer.cUrl = new String("jdbc:mariadb://"+pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
				break;
				
			case POSTGRESQL:
			//	Class.forName("org.postgresql.jdbc.Driver");
				pServer.cUrl = new String("jdbc:postgresql://"+pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
				System.out.println("internalConnect - POSTGRESQL:" + pServer.cUrl);				
				break;
				
					
			case UNKNOWN:
				Class.forName(pServer.cDriver);
				pServer.cUrl = new String(pServer.cUrl +"://"+pServer.cMachine+":"+pServer.cPort );
				break;
			}

			char sep = '?';

			if( pServer.cOptions.length() > 0 ) {
				//"?allowMultiQueries=true";
				pServer.cUrl = new String(pServer.cUrl + sep + pServer.cOptions);
				sep = '&';
			}

			if( pServer.cCerts != null ) {
				//&useSSL=true&...
			    @SuppressWarnings("unchecked")
				Enumeration<String> enums = (Enumeration<String>) pServer.cCerts.propertyNames();
			    while (enums.hasMoreElements()) {
			    	String key = enums.nextElement();
			    	String value = pServer.cCerts.getProperty(key);
					pServer.cUrl += new String(sep + key + '=' + value);
					sep = '&';
			    }
			}
			System.out.println("POSTGRESQL:" + pServer.cUrl);

			PPgTrace.Dbg( "URL:" + pServer.cUrl );

			cConnect = DriverManager.getConnection( pServer.cUrl, pServer.cUser,  pServer.cPass );
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.internalconnect Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch(  Exception sqe){
			cOStream.println( "SqlConnex.internalconnect 2 Error : " + sqe);
			disconnect();
			cConnect = null;
			cServerCurrent = null;

			return false;
		}
		cServerCurrent = pServer;

		if( pServer.cServerType == ServerType.SYBASE && pServer.cBase != null && pServer.cBase.length() > 0 )
			return sendCommand( "use " + pServer.cBase );
		
		if( pServer.cServerType == ServerType.POSTGRESQL && pServer.cBase != null && pServer.cBase.length() > 0 )
			return sendCommand( "SET search_path TO PpgDb" );

		return true;
	}
	//-----------------------
	public boolean connect(){

		if( isConnected())
			return true;		
		
		if( !internalConnect( cServer ) )
			return  internalConnect( cServerBackup );

		return true;
	}
	//-----------------------
	public boolean connectIfNeeded(){

		if( isConnected())
			return true;

		if( !internalConnect( cServer ) )
			return  internalConnect( cServerBackup );

		return true;
	}
	//-----------------------
	boolean isConnected() { return   cConnect != null ;}
	//-----------------------
	public boolean connectOrLogin( Frame pOwner, int pX, int pY ){

		if( !internalConnect( cServer ) ) {
			if( !internalConnect( cServerBackup ) ) {

				SqlLogin lSqlLogin = new SqlLogin( pOwner, cServer,  pX,  pY );

				if( !lSqlLogin.getValidation() )
					return false;

				return internalConnect( cServer );
			}
		}
		return true;
	}
	//-----------------------
	static public boolean sDebugFlagCreateFileOrder = false;
    static public boolean sDebugFlagCreateFileOrderAdd = true;
    static public String sDebugFlagCreateFileOrderPath= ".";
    
	static public void setDebugCreateFileOrder( String iPath, boolean iCreate, boolean iAdd ) {
		sDebugFlagCreateFileOrder = iCreate;
		sDebugFlagCreateFileOrderAdd = iAdd;
		sDebugFlagCreateFileOrderPath=iPath;
	}
	private void debugCreateFileOrder( String iQuery, String iErr ) {
		try {

		if( sDebugFlagCreateFileOrder == false )
			return ;
		
		PPgTrace.Dbg3( "SqlConnex.DebugCreateFileOrder Query:" + iQuery );
		
		int lBegin = iQuery.toLowerCase().indexOf("call ");
		if( lBegin == -1 )
			return ;
		lBegin +=4;
		int lEndName = iQuery.indexOf('(', lBegin+1);
		if( lEndName == -1 )
			return;

		String lNameProc = iQuery.substring(lBegin, lEndName).strip();
		
	//	cOStream.println( "DebugSql Proc begin :" + lBegin + " end:" + lEndName  + " <" + lNameProc +">");
		String lFileName = sDebugFlagCreateFileOrderPath + lNameProc + ".psql";
		
		if( iErr != null ) {
			lFileName =  lFileName +"err";
		}
		
	//	cOStream.println( "DebugSql File:" + lFileName );
		
		File lFile = new File(lFileName);
		if( lFile.exists() && sDebugFlagCreateFileOrderAdd == false ) {
			PPgTrace.Dbg3( "DebugSql exist  QUIT" );
			return ;
		}
		
		if( lFile.canWrite() == false ) {
			PPgTrace.Dbg3( "DebugSql cannot write  QUIT" );
		//	return ;
		}
		
		//cOStream.println( "DebugSql File2:" + lFileName );
	
		    FileWriter lFileW = new FileWriter(lFileName, false);	
			@SuppressWarnings("resource")
			PrintWriter lOs = new PrintWriter( lFileW);

			if( lNameProc.startsWith("get"))
				lOs.print("select ");	
			else
				lOs.print("CALL ");
			
			int lEnd = iQuery.lastIndexOf(')');
			if( lEnd != -1 )
				iQuery = iQuery.substring(lBegin, lEnd+1).strip();

			
			lOs.println( iQuery + ";");
			if( iErr != null ) {
				lOs.println(iErr);
			}

		//	cOStream.println( "DebugSql File3:" + lFileName );
	
			lOs.close();
			lFileW.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	//-----------------------
	public void disconnect(){
		try{
			if( cConnect != null )
				cConnect.close();
		}
		catch(  Exception sqe){
			cOStream.println(sqe);
		}
		cServerCurrent = null;
		cConnect = null;
	}
	//-----------------------
	public void close(){
		disconnect();
	}
	//-----------------------
	static final String sData=new String("                                                                                                                                ");

	void format( StringBuffer pStr, String pData, int pSz ){
		if( pData == null ){
			pData = "?";
		}
		pStr.append( pData );
		pSz -= pData.length();
		if( pSz >=0 ) {
			if( pSz > 128 )
				pSz= 128;

			pStr.append( sData.substring( 0, pSz ));
		}

		if( cSeparator != '\0' )
			pStr.append('|');
	}

	//-----------------------
	public boolean sendCommandAndPrintResult(String pQuery ){
		if( pQuery.length() == 0)
			return true;

		// Statement cCurrentStatement  = null;

		try{
			if( cConnect == null &&  !connect() )
				return false;

			cCurrentStatement = cConnect.createStatement();

			//						System.out.println( "+++++> cCurrentStatement.execute <<<" +pQuery +">>>");
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);


			//================================
			int lNbResult = 0;

			int rowsAffected = 0;

			ResultSetMetaData lResultSetmd = null;
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {
					lNbResult++;

					ResultSet lResultSet = cCurrentStatement.getResultSet();
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();


					int lNumColumns = lResultSetMeta.getColumnCount();

					if( cPrintInfo ) {				
						cOStream.println("\n------------------ Result set "
								+ lNbResult 
								+ " columns : "
								+ lNumColumns 
										+ " -----------------------\n");
					}
					StringBuffer lColumn = new StringBuffer();
					if( cPrintColumnName )
						for (int i = 1; i <= lNumColumns; i++) {
							// phipo 20220922 bug Postgresql getColumnDisplaySize renvoit 2147483647
							int cSz= lResultSetMeta.getColumnDisplaySize(i);
							if( cSz < 0 || cSz > 512) cSz = lResultSetMeta.getColumnLabel(i).length();
							cOStream.println( i + " " + lResultSetMeta.getColumnName(i)+ ":"+ lResultSetMeta.getColumnDisplaySize(i));
							format( lColumn, lResultSetMeta.getColumnLabel(i), cSz+1 );
						}

					cOStream.println( lColumn.toString() );

					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {
						lColumn.setLength(0);
						//	lColumn = new StringBuffer("[ " + lRowNum + "]");

						for (int i = 1; i <= lNumColumns; i++) {
							int cSz= lResultSetMeta.getColumnDisplaySize(i);
							if( cSz < 0 || cSz > 512) cSz = lResultSetMeta.getColumnLabel(i).length();

							format( lColumn, lResultSet.getString(i), cSz+1);
							//	lColumn.append("\t" + lResultSet.getString(i));
						}
						cOStream.println(lColumn.toString());
					}
					cOStream.println( "\n"+lRowNum + " rows.");
				      lResultSet.close();
				}
				else 	{
					lRowsAffected = cCurrentStatement.getUpdateCount();
					if (lRowsAffected >= 0) {
						if( cPrintInfo ) {
							//	cOStream.println( rowsAffected + " rows Affected.");
						}
					}
				}

				cCurrentHaveResult = cCurrentStatement.getMoreResults();
			}	while (cCurrentHaveResult|| lRowsAffected != -1);
                  
			//================================
			cCurrentStatement.close();
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.sendCommandAndPrintResult- Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println("SqlConnex.sendCommandAndPrintResult - Unexpected exception : " + sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
			//						cCurrentStatement.cancel();
			return false;
		}
		finally {
			try {
				if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
					cCurrentStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cCurrentStatement = null;
		}
		return true;
	}
	//-----------------------
	// ETAIT EN COMENTAIRE
	public boolean sendCommandAndSetResult( String pQuery, SqlOut pSqlOut ){
		if( pQuery.length() == 0)
			return true;
		debugCreateFileOrder( pQuery, null );

		try{
			if( cConnect == null &&  !connect() )
				return false;

			cCurrentStatement = cConnect.createStatement();
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);
			//==========================
			int lNbResult = 0;

			int rowsAffected = 0;

			ResultSetMetaData lResultSetmd = null;
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {
					ResultSet         lResultSet     = cCurrentStatement.getResultSet();
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					pSqlOut.setResult( lNbResult, lResultSetMeta, lResultSet );

					lNbResult++;
				}
				else 	{
					lRowsAffected = cCurrentStatement.getUpdateCount();
					if (lRowsAffected >= 0) {
						//cOStream.println(rowsAffected + " rows Affected.");
					}
				}
 

				cCurrentHaveResult = cCurrentStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);
			//==========================
 			cCurrentStatement.close();
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.sendCommandAndSetResult - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println("SqlConnex.sendCommandAndSetResult - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();

			//						cCurrentStatement.cancel();
			return false;
		}
		finally {
			try {
				if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
					cCurrentStatement.close();
				}
			} catch (SQLException e) {
				debugCreateFileOrder( pQuery, e.getMessage() );
				e.printStackTrace();
			}
			cCurrentStatement = null;
		}
		return true;
	}
	
	//-----------------------
	public PreparedStatement prepareCommand( String pQuery ) {
		debugCreateFileOrder( pQuery, null );
		try{
			return cConnect.prepareStatement( pQuery );
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.prepareCommand - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( pQuery, sqe.getMessage() );
			cOStream.println("SqlConnex.prepareCommand - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}
		return null;
	}
	//-----------------------
	@SuppressWarnings("exports")
	public CallableStatement prepareCallProc( String pQuery ) {
		debugCreateFileOrder( pQuery, null );
		
		try{
			return cConnect.prepareCall( pQuery );
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.prepareCallProc -  Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( pQuery,  sqe.getMessage() );
			cOStream.println("SqlConnex.prepareCallProc - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}
		return null;
	}
	//-----------------------
	@SuppressWarnings("exports")
	public PreparedStatement prepareStatement( String pQuery ) {
		debugCreateFileOrder( pQuery, null );
		
		try{
			return cConnect.prepareStatement( pQuery );
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.prepareStatement -  Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( pQuery,  sqe.getMessage() );
			cOStream.println("SqlConnex.prepareStatement - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}
		return null;
	}
	//-----------------------
	public boolean executeWithNoReturn ( PreparedStatement iStatement) {
		try{
			iStatement.executeUpdate();
			return true;
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.executeWithNoReturn - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeWithNoReturn - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}
		try {
			iStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iStatement = null;
		return false;
	}
	//-----------------------
	public boolean executeQuery( PreparedStatement iStatement, SqlResultsExecData iExec) {		
		
		boolean lResult = false;
		try{
			//================================
			int lNbResult = 0;
			int lRowsAffected =0;

			ResultSet lResultSet = iStatement.executeQuery();			

			if( lResultSet != null ) {
				cCurrentHaveResult = true;
			}
			do {
				if (cCurrentHaveResult) {
					if( lResultSet == null )
						break;
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {
						iExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}
					lNbResult++;
				}
				else 	{
					lRowsAffected = iStatement.getUpdateCount();
				}
				cCurrentHaveResult = iStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);
                        lResultSet.close();
			//================================
			lResult = true;
			}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.executeQuery - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeQuery - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}

		try {
			iStatement.close();
		}catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeQuery - close - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
			return false;
		}
		iStatement = null;

		return lResult;
	}
	//-----------------------
	public boolean executeStatementCursor( PreparedStatement iStatement, SqlResultsExecData iExec ) {		
		
		boolean lResult = false;
		boolean lAutoCommit = getAutoCommit();
		try{
	        if( lAutoCommit ) {
	        	setAutoCommit(false); 
	        }

			//================================
			int lNbResult = 0;
			int lRowsAffected =0;

			ResultSet lResultSet = iStatement.executeQuery();			

			if( lResultSet != null ) {
				cCurrentHaveResult = true;
			}
			do {
				if (cCurrentHaveResult) {
					if( lResultSet == null )
						break;
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {
						iExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}
					lNbResult++;
				}
				else 	{
					lRowsAffected = iStatement.getUpdateCount();
				}
				cCurrentHaveResult = iStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);
                        lResultSet.close();
			//================================
			lResult = true;
            lResultSet.close();
            if( lAutoCommit ) {
            	setAutoCommit(true);  // otherwise it's wait a commit
            	}
			}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.executeQuery - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeQuery - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}

		try {
			iStatement.close();
		}catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeQuery - close - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
			return false;
		}
		iStatement = null;

		return lResult;
	}	
	//-----------------------
	public boolean executeStatementCursor( String iQuery, SqlResultsExecData iExec ) {
	
		debugCreateFileOrder( iQuery, null );
		
		//System.out.println("executeStatementCursor" + iQuery);
		CallableStatement lStatement = prepareCallProc( iQuery );
		boolean lResult = false;		
		cCurrentHaveResult = false;
		
		boolean lAutoCommit = getAutoCommit();
		try{
            if( lAutoCommit) {   
            	setAutoCommit(false);  // Postgresql necessary for cursor work
            }
			//================================
			int lNbResult = 0;
			int lRowsAffected =0;
			lStatement.registerOutParameter(1, Types.OTHER); // CURSOR

//			System.out.println("BEFORE execute");
			lStatement.execute();
	//		System.out.println("AFTER execute");
	
			ResultSet lResultSet = lStatement.getObject(1, ResultSet.class);

			if( lResultSet != null ) {
				cCurrentHaveResult = true;
				//System.out.println("SqlConnex.executeQueryCursor have Result ");
			}
			do {
				if (cCurrentHaveResult) {
					if( lResultSet == null ) {
					//	System.out.println("SqlConnex.executeQueryCursor have no Result ");
						break;
					}
					
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {
						iExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}
					lNbResult++;
				}
				else 	{
					lRowsAffected = lStatement.getUpdateCount();
				}
				cCurrentHaveResult = lStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);

             lResultSet.close();
             if( lAutoCommit ) {
            	 setAutoCommit(true);  // otherwise it's wait a commit
             }
			//================================
			lResult = true;
			}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.executeQuery - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( iQuery,  sqe.getMessage() );
			cOStream.println("SqlConnex.executeQuery - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
		}

		try {
			lStatement.close();
		}catch (SQLException sqe) {
			cOStream.println("SqlConnex.executeQuery - close - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();
			return false;
		}

		return lResult;
	}
	//-----------------------
	public boolean executeQuery( String iQuery, SqlResultsExecData iExec) {
		debugCreateFileOrder( iQuery, null );
		
		PreparedStatement lStatement = prepareCommand( iQuery );

		if( lStatement != null ) {
			return executeQuery( lStatement, iExec );
		}
		PPgTrace.Err( "SqlConnex.executeSelect - prepareCommand failed <<<" + iQuery + ">>>");
		return false;
	}
	//-----------------------
	// On attende des resultats
	public boolean sendCommandResult(String pQuery, SqlResultsExecData pExec ){
		debugCreateFileOrder( pQuery, null );
		
		cOStream.println( "SqlConnex.sendCommandResult : " +  pQuery );
		
		if( pQuery.length() == 0)    return true;

		try{
			if( cConnect == null &&  !connect() )
				return false;

			cCurrentStatement = cConnect.createStatement();
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);

			//================================
			int lNbResult = 0;
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {
					ResultSet         lResultSet     = cCurrentStatement.getResultSet();
					if( lResultSet == null )
						break;
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					//    int lNumColumns = lResultSetMeta.getColumnCount();
					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {
						pExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}
					lNbResult++;
			       lResultSet.close();

				}
				else 	{
					lRowsAffected = cCurrentStatement.getUpdateCount();
				}
				cCurrentHaveResult = cCurrentStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);
			//================================
			cCurrentStatement.close();
 		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.sendCommandResult - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( pQuery,  sqe.getMessage() );
			cOStream.println("SqlConnex.sendCommandResult - Unexpected exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());
			sqe.printStackTrace();
			disconnect();

			//						cCurrentStatement.cancel();
			return false;
		}
		finally {
			try {
				if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
					cCurrentStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cCurrentStatement = null;
		}
		return true;

	}
	//-----------------------
	public void eatResults() throws SQLException
	{
		int lNbResult = 0;

		int rowsAffected = 0;

		ResultSetMetaData lResultSetmd = null;
		int lRowsAffected =0;
		//========================
		do {
			if (cCurrentHaveResult)
			{
				ResultSet         lResultSet     = cCurrentStatement.getResultSet();
		          lResultSet.close();
			}
			else 	{
				lRowsAffected = cCurrentStatement.getUpdateCount();
				if (lRowsAffected >= 0) {
					if( cPrintInfo ) {
						//	cOStream.println(rowsAffected + " rows Affected.");
					}
				}
			}
  			cCurrentHaveResult= cCurrentStatement.getMoreResults();
		} while (cCurrentHaveResult || lRowsAffected != -1);
		//========================
     
		cCurrentStatement.close();
		cCurrentStatement = null;
	}
	//-----------------------
	public boolean sendCommand(String pQuery ){
		if( pQuery.length() == 0)
			return true;

		debugCreateFileOrder( pQuery, null );
		
		try{
			if( cConnect == null &&  !connect() )
				return false;

			cCurrentStatement = cConnect.createStatement();
			//					System.out.println( "+++++> cCurrentStatement.execute <<<" +pQuery +">>>");
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);
			//						System.out.println( "++++++++++++++++++++++++++++++++++++++++++++++++++++");
			eatResults();
		}
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.sendCommand - Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			debugCreateFileOrder( pQuery,  sqe.getMessage() );
			cOStream.println("SqlConnex.sendCommand - Unexpected sql exception : " +
					sqe.toString() + ", sqlstate = " + sqe.getSQLState() + " <<<"+ pQuery +">>>" );

			sqe.printStackTrace();
			disconnect();
			return false;
		}
		catch (Exception e) {
			cOStream.println("SqlConnex.sendCommand - Unexpected exception : " +
					e.toString()  + " <<<"+ pQuery +">>>" );
			e.printStackTrace();
			disconnect();
			return false;
		}
		finally {
			try {
				if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
					cCurrentStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cCurrentStatement = null;
		}
		return true;
	}
	//-----------------------
	public boolean cancelRequest(){
		try {
			if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
				cCurrentStatement.cancel();
				return true;
			}
		} 
		catch(SQLNonTransientConnectionException sqe){
			cOStream.println( "SqlConnex.cancelRequest -  Error : " + sqe);
			sqe.printStackTrace();
			disconnect();
		}
		catch (SQLException sqe) {
			cOStream.println( "SqlConnex.cancelRequest -  sql unexpected exception : " + sqe);
			sqe.printStackTrace();
			disconnect();
			return false;
		} catch (Exception e) {
			cOStream.println( "SqlConnex.cancelRequest -  unexpected exception : " + e);
			e.printStackTrace();
			disconnect();
			return false;
		}
		return true;
	}
	//-----------------------
	public boolean sendCommandWithGo(String pQuery ){

		while( true ){
			if( pQuery.length() == 0)
				return true;

			int lPos = pQuery.indexOf( "\ngo\n");
			if( lPos == -1 ){

				lPos = pQuery.indexOf( "\ngo");
				if( lPos == -1 ){
					return sendCommand( pQuery );
				}
				else
					return sendCommand( pQuery.substring( 0, lPos ) );
			}
			else{
				sendCommand( pQuery.substring( 0, lPos ) );
				pQuery = pQuery.substring( lPos+4, pQuery.length() );
			}
		}
	}
	//-----------------------
	static public String SqlQuote(String iStr ) {
		return PPgUtils.DupliqueChar(iStr,'\'');
	}
	public boolean getAutoCommit() {
		try {
			return cConnect.getAutoCommit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void setAutoCommit(boolean b) {
		try {
			cConnect.setAutoCommit(b);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
//***********************************
