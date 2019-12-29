package org.phypo.PPg.PPgUtils;


import java.util.*;
import java.io.*;

//***********************************
public class SimpleNotifier{
    

    HashMap<SimpleNotifierSubject, Set<SimpleNotifierObserver> > cNotifs = new HashMap<>();

    //-------------------------------------
    public void register( SimpleNotifierSubject iSub, SimpleNotifierObserver iObs ){
	
	Set<SimpleNotifierObserver> lSetObs = cNotifs.get( iSub );
	if( lSetObs == null ) {
	    lSetObs = new  HashSet<SimpleNotifierObserver>();
	    cNotifs.put( iSub, lSetObs );
	}
	lSetObs.add( iObs );	    
    }
    //-------------------------------------
    public void unRegister( SimpleNotifierSubject iSub, SimpleNotifierObserver iObs ){
	Set<SimpleNotifierObserver> lSetObs = cNotifs.get( iSub );
	if( lSetObs != null ){
	    lSetObs.remove( iObs );
	    if( lSetObs.size() == 0){
		cNotifs.remove( iSub );
	    }
	}
    }
    //-------------------------------------
    public void unRegister( SimpleNotifierSubject iSub ){
	Set<SimpleNotifierObserver> lSetObs = cNotifs.get( iSub );
	if( lSetObs != null ){
	    lSetObs.clear();            // vraiment utile ?
	    cNotifs.remove( iSub );
	}
    }
    //-------------------------------------
    public void unRegister( SimpleNotifierObserver iObs ){

	for( SimpleNotifierSubject lSub : cNotifs.keySet() ) {
	    unRegister( lSub, iObs );
	}	
    }
    //-------------------------------------
    public void notify(  SimpleNotifierSubject iSub ){

	Set<SimpleNotifierObserver> lSetObs = cNotifs.get( iSub );
	if( lSetObs != null ) {
	    for(  SimpleNotifierObserver lObs : lSetObs ){
		lObs.notify(iSub);
	    }
	}	
    }
    //-------------------------------------
    public <OBJECT> void notify(  SimpleNotifierSubject iSub, OBJECT iObj ){

	Set<SimpleNotifierObserver> lSetObs = cNotifs.get( iSub );
	if( lSetObs != null ) {
	    for(  SimpleNotifierObserver lObs : lSetObs ){
		lObs.notify(iSub, iObj);
	    }
	}	
    }

}
    
//***********************************
	
