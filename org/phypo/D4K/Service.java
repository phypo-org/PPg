package org.phypo.D4K;


import java.util.*;

//***********************************
class Service {
    public int     cPort;
    public String  cIPport;  //IP +':'+Port
    public String  cProto; 
    public String  cAppProto;
    
    public int     cCptSrc;
    public int     cCptDest;
 
    public Machine cMyMachine;
    
    
    // Mettre total packet payoff ...
    
    public HashMap<String,Service> cConnexTo = new HashMap<>();
    
    //----------------------------
    public Service( int iPort, Machine iMachine, String iProto, String iAppProto) {
	cPort      = iPort;
	cMyMachine = iMachine;
	cIPport    = iMachine.cIP + ":" + Integer.toString( iPort);
	cProto     = iProto;
	cAppProto  = iAppProto;
    }
    //----------------------------
    public void connectTo( Service iOther ) {
	
	if( iOther == null ) return;

	String lDest=iOther.cMyMachine.cIP + ":" + Integer.toString( iOther.cPort);
	
	Service lOther = cConnexTo.get( lDest );
	//	System.out.println( "connectTo " + cIPport + " -> " + lDest );
	
	if( lOther == null ) {	    
	    cConnexTo.put( lDest, iOther );
	    iOther.connectTo( this );
	}
  
    }
    //----------------------------
    public void set(  boolean iSrc, String iProto, String iAppProto, Service iOther ){
	if( iSrc ) cCptSrc++;
	else       cCptDest++;
	connectTo( iOther );
    }		    
}
//***********************************
	   
