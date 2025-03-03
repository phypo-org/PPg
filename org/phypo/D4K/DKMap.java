package org.phypo.D4K;


import org.phypo.PPg.Sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;


//***********************************

class DKMap {
    static int sUniqId=1;

    public String   cName="Void";
    public String   cKey="";
    public int      cId=0;

    public HashMap<String,Machine> cMachines= new HashMap<>();
    
    FrameMachines   cMyFrameMachines;
    FrameCanvasCnx  cMyFrameCnx;
    //-----------------------------
    void openFrameMachines( boolean iReload ){
	if( cMyFrameMachines == null ) {	    
	   cMyFrameMachines = new FrameMachines( this );
	   D4K.Instance.addChild( cMyFrameMachines );
	} else {
	    if( iReload == true ) {
		cMyFrameMachines.reload();	
	    }
	}	    	
	cMyFrameMachines.show();	
    }
    //-----------------------------
    void openFrameCnx( boolean iReload ){
	if( cMyFrameCnx == null ) {	    
	   cMyFrameCnx = new FrameCanvasCnx( this );
	   D4K.Instance.addChild( cMyFrameCnx );
	} else {
	    if( iReload == true ) {
		cMyFrameCnx.reload();	
	    }
	}	    	
	cMyFrameCnx.show();
	cMyFrameCnx.forceRepaint();
    }
    //-----------------------------
    public String getName()              { return cName; }    
    public void   setName(String iName ) { cName = iName; }
    
    public String getKey()              { return cKey; }    
    public void   setKey(String iKey )  { cKey = iKey; }

    public Integer getId() { return cId; }
    //-----------------------------
    public DKMap( String iName, String iKey ){
	cName = iName;
	cKey  = iKey;
	cId   = sUniqId++;

    }
    //-----------------------------
    public DKMap(  ){
	cName = "Void";
	cId   = 0;
    }
    //-----------------------------
    void debug()
    {
	System.out.print( getName() + " " + getKey() + " " + getId() );
    }
    //-----------------------------
    void loadFromDatabase()
    {
	//	System.out.print( ">>>>>>>>>>>> LoadMap for " );	
	//	debug();	
	//	System.out.println("");
	
	SqlConnex lConnex = D4K.Instance.getDatabaseConnex();
	if( lConnex == null ) {
	    System.err.println( "*** Error : loadFromDatabase failed - Connection to database failed");
	    return ;
	}
	
	System.out.println( "--- Connect to database --- " );

	try {
	    Statement lStatement = lConnex.getConnection().createStatement();

	    String lStrSql= D4K.GetIniFile().get( "SQL", "RequestAlertPusher", null);
	    
	    if( lStrSql== null ) {
		System.err.println( "*** Error : loadFromDatabase failed - [SQL]RequestAlertPusher is void");
		return ;
	    }
		
	    // Ajouter la taille du payload, du paquet ..."
	
	    if( lStatement.execute( lStrSql ) ){	

		ResultSet lResultSet = lStatement.getResultSet();												
		if( lResultSet == null ){
		    System.out.println( "loadFromDatabase.read Result is null");
		    return ;
		}
	    
		int       lRowNum = 0;
		int       lError=0;
		while( lResultSet.next() ) {		    
		    int lColumn = 1;
		    lRowNum++;
		    try{
		    
			String cSrcIP    = lResultSet.getString(lColumn++);
			int    cSrcPort  = lResultSet.getInt(lColumn++);
			String cDestIP   = lResultSet.getString(lColumn++);
			int    cDestPort = lResultSet.getInt(lColumn++);
			String cProto    = lResultSet.getString(lColumn++);
			String cAppProto = lResultSet.getString(lColumn++);

			if( cSrcPort <= 0 || cDestPort <= 0  
			    || cSrcIP.length() < 7 || cDestIP.length() < 7
			    || cSrcIP == "null" || cDestIP == "null" )
			    {
				lError++;
				continue;
			    }
			    
			////		System.out.println( lRowNum + ">"+ cSrcIP +":" + cSrcPort + " -> " + cDestIP + ":" + cDestPort + " "+ cProto +"|" +  cAppProto );
			recordConnection( cSrcIP, cSrcPort, cDestIP, cDestPort, cProto, cAppProto );
		    }
		    catch( SQLException ex ){
			System.out.println( "loadFromDatabase.read SQLException" 
					    + "  : " + lRowNum + " : " + lColumn
					    + " <" + ex +">" );						
			ex.printStackTrace();
		    }					
		} // while
		System.out.println( "Error:" + lError  + " cMachines:" + cMachines.size() );
	    }
	}catch(  SQLException ex ){
	    System.out.println( "loadFromDatabase.read SQLException"
				+ " <" + ex +">" );						
	    ex.printStackTrace();
	}			
    }
    //-----------------------------
    void recordConnection( String iSrcIP, int iSrcPort, String iDestIP, int iDestPort, String iProto, String iAppProto )
    {
	// on creer les deux machines, a chacune d'elle on ajoute un port e

	Service lServiceSrc = setMachine( true, iSrcIP, iSrcPort, iProto, iAppProto, null );
	setMachine( false, iDestIP, iDestPort, iProto, iAppProto, lServiceSrc );
    }
    //-------------------------------------------
    Service setMachine( boolean iSrc, String iIP, int iPort,  String iProto, String iAppProto, Service lOther ){
	Machine lMachine = cMachines.get( iIP );
	if( lMachine == null ) {  // Si la machine n'existe pas on la cree
	    lMachine = new Machine( iIP );
	    cMachines.put( iIP, lMachine );
	}
	return lMachine.setService( iSrc, iPort, iProto, iAppProto, lOther );
    }    
}

//***********************************
