package org.phypo.PPg.PPgData;

import java.util.ArrayList;
import java.util.HashMap;

import org.phypo.PPg.PPgUtils.Log;


//*************************************************
// ATTENTION !!!! A FAIRE :remove client not implemented

public class DataChgNotifier {

	protected  HashMap<String, ArrayList<DataChgNotifierClient>> cClients = new HashMap<>();

	//-------------------------------------------------
	private static DataChgNotifier sInstance;   // A possible singleton

	private DataChgNotifier() {;}
	//-------------------------------------------------
	public static DataChgNotifier Instance() {
		if( sInstance == null ) {
			sInstance = new DataChgNotifier();			
		}
		return sInstance;
	}
	//-------------------------------------------------
	public void register( String iKey, DataChgNotifierClient iClient ) {
		if( iKey == null ) {
			Log.Err( "DataChgNotifier.register key is null");
			return ;
		}

		if( iClient == null ) {
			Log.Err( "DataChgNotifier.register client is null for key : " +  iKey );
			return ;
		}

		ArrayList<DataChgNotifierClient> lList = cClients.get(iKey);
		if( lList  == null ) {
			lList = new ArrayList<DataChgNotifierClient>();
			lList.add( iClient);
			cClients.put( iKey,  lList );
		} else {
			if( lList.contains( iClient) == false ){
				lList.add( iClient);
			}
		}			
	}
	//-------------------------------------------------
	public  boolean prepareReload(String iStr, long iTimeStamp ) {
		//	Log.Dbg3("PrepareReload <" + iStr +">");

		ArrayList<DataChgNotifierClient> lList = cClients.get(iStr);
		
		if( lList == null)
			return false;

		boolean lNeed = false;
		for( DataChgNotifierClient lClient :  lList ) {
			if( lClient ==null) continue;

			lClient.setNeedReload( iTimeStamp > lClient.getLastTimeStamp() );

			//		Log.Dbg( "  prepareReload ? "  +iStr+"-"+iTimeStamp +  " > "+lClient.getLastTimeStamp() + " -> " +  ( iTimeStamp > lClient.getLastTimeStamp()) );

			if( lClient.getNeedReload() ) {
				Log.Dbg( ">>>>>>>>>>>>>>> DataChgNotifier Prepare NEED RELOAD : "+iStr+" : "+ (iTimeStamp -lClient.getLastTimeStamp()) );
				lClient.setTmpTimeStamp( iTimeStamp );
			}
			lNeed = lNeed || lClient.getNeedReload();		
		}
		return lNeed;
	}
	//-------------------------------------------------
	public  void reloadIfNeeded() {
		for( ArrayList<DataChgNotifierClient>  lList :  cClients.values()) {
			if( lList == null ) continue;
			for( DataChgNotifierClient lClient : lList ) {

				if( lClient.getNeedReload() || lClient.getForceReload() ) {

					Log.Dbg( "        DataChgNotifier.reloadIfNeeded callSynLoad " +   lClient.getNeedReload() + " " + lClient.getForceReload()  + " " +lClient.toString());

					lClient.setForceReload( ! lClient.callSyncLoad() ); // callSyncLoad make the call to load the table
					if( lClient.getForceReload() == false ) {
						lClient.setNeedReload(  false );
						lClient.setLastTimeStamp(  lClient.getTmpTimeStamp() );
						//	Log.Dbg( "        DataChgNotifier setNeedReload false  timestamp : " + lClient.getLastTimeStamp());
					}
				}
			}
		} 
	}	
	//-------------------------------------------------
	public  void razTimeStamps() {
		for( ArrayList<DataChgNotifierClient>  lList :  cClients.values()) {			
			for( DataChgNotifierClient lClient : lList ) {
				lClient.razLastTimeStamp() ;		
			}
		}
	}
}
//*************************************************
