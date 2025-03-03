package org.phypo.D4K;


import java.util.*;
import java.util.*;

import org.phypo.PPg.PPgWin.Arrow;

//***********************************
class Machine {
    public    String  cIP;
    public    String  cName="";
    public    String  cInfo="";
    public    int     cNbMsg;

    StringBuilder    cPorts;
    StringBuilder    cProtos;
    StringBuilder    cApplis;
    StringBuilder    cConnections;

    public    HashMap<Integer,Service> cServices = new HashMap<>();

    //----------------------------
    public Machine( String iIP ) { cIP = iIP; cName=cIP; }	  
    //----------------------------
   public Service setService(  boolean iSrc, int iPort,  String iProto, String iAppProto, Service iOther ){
       
       cNbMsg++;
       Service lService = cServices.get( iPort );
	if( lService == null ) {
	    lService = new Service( iPort, this, iProto, iAppProto );
	    cServices.put( iPort, lService );
	}
	lService.set( iSrc, iProto, iAppProto, iOther );
	return lService;
    }
    //----------------------------
    void servicesAggregation()
    {
	cPorts  = new StringBuilder();
	cProtos = new StringBuilder();
	cApplis = new StringBuilder();
	cConnections = new StringBuilder();

	
	Set<Integer> lSetPorts = new TreeSet<>();
	Set<String> lSetProtos = new TreeSet<>();
	Set<String> lSetApplis = new TreeSet<>();
	Set<String> lSetConnections = new TreeSet<>();
	
	for ( Service lServ : cServices.values() ) {
	    lSetPorts.add( lServ.cPort );
	    
	    if( lServ.cProto != null && lServ.cProto.length() > 0 )
		lSetProtos.add( lServ.cProto );
	    
	    if( lServ.cAppProto != null && lServ.cAppProto.length() > 0 )
		lSetApplis.add( lServ.cAppProto );
	    

	    for ( Service lServDest : lServ.cConnexTo.values() ) {
		lSetConnections.add( lServDest.cMyMachine.cIP );
	    }
	}
	
	for( Integer lVal : lSetPorts ) {	    
	    cPorts.append( lVal.intValue() );
	    cPorts.append( ", " );
	}
	
	cProtos.setLength(0);
	for( String lVal : lSetProtos ) {	    
	    cProtos.append( lVal );
	    cProtos.append( ',' );
	}

	cApplis.setLength(0);
	for( String lVal : lSetApplis ) {	    
	    cApplis.append( lVal );
	    cApplis.append( ',' );
	}

	
	cConnections.setLength(0);
	cConnections.append( '[' );
	cConnections.append(  lSetConnections.size());
	cConnections.append( "] " );

	for( String lVal : lSetConnections ) {
	    cConnections.append( lVal );
	    cConnections.append( " | " );
	}
    }
    //----------------------------
    String getPorts(){
	if( cPorts == null )	servicesAggregation();
	  
	return cPorts.toString();
    }
    //----------------------------
    String getProtos(){
	if( cProtos == null )	servicesAggregation();
	    
	return cProtos.toString();
    }
    //----------------------------
    String getApplis(){
	if( cApplis == null )	servicesAggregation();
	return cApplis.toString();
    }
     //----------------------------
    String getConnections(){
	if( cConnections == null) servicesAggregation();
	return cConnections.toString();
    }
}
//***********************************
	   
