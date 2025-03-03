package org.phypo.D4K;


import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


import org.phypo.PPg.PPgWin.*;

import org.phypo.PPg.PPgUtils.*;


//*************************************************

public class  DataCanvasCnx {
    
    DKMap cDKMap;
    FrameCanvasCnx    cFrameCnx;
    ArrayList<Sprite> cSelection= new ArrayList<Sprite>();
    ArrayList<Sprite> getSelection() { return cSelection;}

    // cVectSprite devrait sans doute appartenir a OrsMonitor
    Hashtable<String,Sprite>         cVectSprite = new Hashtable<String,Sprite>();
		
    public Hashtable<String,Sprite> getSpriteTable() { return cVectSprite;}

    //--------------------------
    public DataCanvasCnx( DKMap iDKMap, FrameCanvasCnx iFrameCnx ) {
	cDKMap = iDKMap;
    	cFrameCnx = iFrameCnx;
    }
    //--------------------------------------------
    public void reload(){
	
	//	iFrameCnx.clear();
	cVectSprite.clear();
	addMap( cDKMap );
    }	
   //--------------------------------------------
    public void addMap( DKMap iDKMap  ) {

	System.out.println( "DataCanvasCnx addMap " + iDKMap.cMachines.size() );
	
	for ( Machine lMach : iDKMap.cMachines.values() ) {
	    addSprite( new SpriteAppli(lMach) );
	    System.out.println( "add machines: " + lMach.cIP );
	}
	
	cFrameCnx.setToBeRedraw();	
    }
    //--------------------------
    void addSprite(  Sprite pSprite ) {
	cVectSprite.put( pSprite.getName(), pSprite );

	//	FrameCnxMachines.setToBeRedraw();
    }		
    //--------------------------
    boolean removeSprite(  Sprite pSprite ) {				

	boolean lResult = false;

	if( cVectSprite.remove(  pSprite.getName() ) != null )
	    lResult = true;

				
	// Pour detruire les connexions relie au sprite par exemple
	Iterator<Sprite> l_iter = cVectSprite.values().iterator();

	while( l_iter.hasNext() ){
	    Sprite lSprite = l_iter.next();

	    if( lSprite.isDependantOf( pSprite )) {								
		l_iter.remove();
		lSprite.destroy(true);
	    }
	}								

	cFrameCnx.setToBeRedraw();

	return lResult;
    }	
    //--------------------------
    void deleteAll() {				
				
	// Pour detruire les connexions relie au sprite par exemple
	Iterator<Sprite> l_iter = cVectSprite.values().iterator();
				
	while( l_iter.hasNext() ){
	    Sprite lSprite = l_iter.next();						
	    lSprite.destroy(false);
	}		

	cVectSprite.clear();	

	cFrameCnx.setToBeRedraw();
    }
    //--------------------------
    void deleteGhosts() {				
				
	ArrayList<Sprite> lListToDel = new ArrayList<Sprite>();

	//				Iterator l_iter = cVectSprite.values().iterator();
				
	for( Sprite lSprite : cVectSprite.values() ){
	    //				while( l_iter.hasNext() ){						
	    //						Sprite lSprite = (Sprite)l_iter.next();						
	    if( (lSprite.getState() == Sprite.IS_DEAD  
		 || lSprite.getState() == Sprite.TO_DEL)
		&& lSprite.isExternal() == false) {
		lListToDel.add( lSprite );
	    }
	}
						
	for( Sprite lSprite:lListToDel ){												
	    removeSprite( lSprite );
	}		

	for( Sprite lSprite:lListToDel ){												
	    lSprite.destroy(true);
	}		

	cFrameCnx.setToBeRedraw();
    }

    //--------------------------
    /*
    ArrayList<String> getOrsServer( boolean pIsAlive ) {				
				
	ArrayList<String> lList = new ArrayList<String>();
				
	Iterator<Sprite> l_iter = cVectSprite.values().iterator();
				
	while( l_iter.hasNext() ){						
	    Sprite lSprite = l_iter.next();						
	    if(( pIsAlive == false || lSprite.getState() != Sprite.IS_DEAD  )
	       && lSprite.getState() != Sprite.TO_DEL
	       && lSprite.getType() == Sprite.sTypeAppli 
	       && ((SurvAppli)lSprite).isOrsServer() 
	       //								&& ((SurvAppli)lSprite).isPrimary()
	       && ((SurvAppli)lSprite).getMachine() != null
	       && ((SurvAppli)lSprite).getPort() != null) {								
		lList.add( lSprite.getName()  );
	    }
	}				
	return lList;
    }
    //--------------------------
    SpriteAppli getOrsServer( String pStr, boolean pIsAlive ){

	if( pStr == null )
	    return null;
				
	Iterator<Sprite> l_iter = cVectSprite.values().iterator();
				
	while( l_iter.hasNext() ){						
	    Sprite lSprite = l_iter.next();						
	    if( ( pIsAlive == false || lSprite.getState() != Sprite.IS_DEAD  )
		&& lSprite.getState() != Sprite.TO_DEL
		&& lSprite.getType() == Sprite.sTypeAppli 
		&& ((SurvAppli)lSprite).isOrsServer() 
		//								&& ((SurvAppli)lSprite).isPrimary()
		&& lSprite.getName().equals( pStr )) {	
		return ((SurvAppli)lSprite);
	    }
	}		
	return null;				
    }
    //--------------------------
    String getOrsServerMachine( String pStr ) {	
	SurvAppli lSurv = getOrsServer( pStr, false );
	if( lSurv != null ){
	    return lSurv.getMachine();
	}
	return null;
    }		
    //--------------------------
    String getOrsServerPort( String pStr ) {				
	SurvAppli lSurv = getOrsServer( pStr, false );
	if( lSurv != null ){
	    return lSurv.getPort();
	}
	return null;
    }		
    //--------------------------
    Point getOrsServerXY( String pStr ) {				
	SurvAppli lSurv = getOrsServer( pStr, false );
	if( lSurv != null ){
	    return lSurv.getLocation();
	}		
	return null;
    }	
    */
    //-------------------------------------
    void ControlGeneral(){

	Enumeration<Sprite> lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
	    lEnum.nextElement().controlState();
	}
						
    }
    //-----------------------
    public  Sprite find( String pName ) {
	if( pName == null ) 
	    return null;

	return cVectSprite.get( pName );
    }    
    //-----------------------
    public void saveIni( PPgIniFile pIni ){
				
	pIni.remove( "Application", "Liste" );
	pIni.remove( "Connection",  "Liste" );
				
	Sprite l_sprite=null;
	Enumeration<Sprite> lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
	    lEnum.nextElement().saveIni( pIni );
	}
    }
    //-----------------------
    
    public void readIni( PPgIniFile pIni ){
	/*
	String lAppliList = pIni.get( "Machine", "Liste" );
				
	if( lAppliList != null ){
	    //						System.out.println( "readIni : " + lAppliList );
						
	    StringTokenizer lTok = new StringTokenizer(lAppliList);
	    while( lTok.hasMoreTokens() ){
		String lName=lTok.nextToken(",");
		if( lName != null ){
		    String lData = pIni.get( "Application", lName );
		    if( lData != null) {
			//												System.out.println( "->"+ lName +"->"+ lData );
			SpriteAppli lSpriteAppli =  (SpriteAppli)OrsMonitor.GetDataCanvasCnx().find( lName );
			if( lSpriteAppli == null ){														
			    SpriteAppli lAppli =SurvAppli.GetNewSurvAppli( lName, lData );
			    OrsMonitor.GetDataAppli().addSprite( lAppli );
			}
			else
			    lSpriteAppli.initFromData( lData );
		    }
		}
	    }
	}

	cFrameCnx.forceRepaint();    
	*/
    }
    
    //------------------------------------------------		
    // On ne peut pas utilise remove !!! car j'ai redeinei 
    // equal et hashCode pour les table de hash
    public void deleteSelection( ){

	for( Sprite lSprite : getSelection() ){
	    removeSprite(lSprite);
	    lSprite.destroy(true);
	    cFrameCnx.forceRepaint();
	}
	clearSelection();
    }
    //------------------------------------------------		
    public boolean removeFromSelection( Sprite pSel ){

	int i=0;
	for( Sprite lSprite :  getSelection() ){
						
	    if( lSprite == pSel ){
		getSelection().remove( i );
		return true;
	    }
	    i++;
	}
	return false;
    }
    //-------------------------- 
    void setSelection( Sprite pSel ){
				
	clearSelection();
	getSelection().add(  pSel);
    }	
    //-------------------------- 
    void addSelection( Sprite pSel ){
		
	getSelection().add( pSel);
    }	
    //-------------------------- 
    void subSelection( Sprite pSel ){
	removeFromSelection( pSel );
    }	
    //-------------------------- 
    void clearSelection( ){

	//				drawSelection( getGraphics() );
	getSelection().clear();
    }	
    //-------------------------- 
    boolean isInSelection( Sprite pSel ){

	for( Sprite lSprite : getSelection() ){
	    if( pSel  == lSprite) 
		return true;						
	}
	return false;
    }
    //-------------------------- 
    void moveSelection( int pX, int pY){

	for( Sprite lSprite :  getSelection() ){
	    lSprite.setLocation( (int)lSprite.getX()+pX, (int)lSprite.getY()+pY );
	}
    }		


    //----------------------------------------------
    void arrangeCircle(){
				
				
				
	double lMaxSz=0;
	int lNbAppli=0;
	for( Enumeration<Sprite> lEnum = cVectSprite.elements(); lEnum.hasMoreElements();  ) {				
	    Sprite lSprite = lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli ){								

		double lSz = lSprite.getWidth() + lSprite.getHeight();
		lMaxSz = ( lMaxSz < lSz ?  lSz :lMaxSz );
		lNbAppli++;
	    }
	}
				
				
	double lEcart = SurvParam.sDistanceCircle*lNbAppli;
	lEcart = ((lMaxSz*lNbAppli)/8)+SurvParam.sDistanceCircle;
				
	float lPas = (float)(3.1416*2.0)/((float)lNbAppli);
	float lAngle = 0;
				
	Enumeration<Sprite> lEnum2 = cVectSprite.elements();
	while( lEnum2.hasMoreElements() ){
						
	    Sprite lSprite =lEnum2.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		//								if( ((SurvAppli)lSprite).getNbCnx()!=1){
		lSprite.setLocation( (int)((Math.cos(lAngle)*lEcart)+lEcart*1.2), (int)((Math.sin(lAngle)*lEcart)+lEcart*1.2));
		lAngle += lPas;
		//								}
	    }
						
	}				
	cFrameCnx.forceRepaint();
    }
    //-----------------------
    void arrangeSpirale(){
				
				
	int lMinX=999999999;
	int lMinY=999999999;
				
	double lMaxSz=0;
	int lNbAppli=0;
	for( Enumeration<Sprite > lEnum = cVectSprite.elements(); lEnum.hasMoreElements(); ){
	    Sprite lSprite = lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
								
		double lSz = lSprite.getWidth() + lSprite.getHeight();
		lMaxSz = ( lMaxSz < lSz ?  lSz :lMaxSz );
		lNbAppli++;
	    }
	}
				
				
	double lEcart = SurvParam.sDistanceCircle*lNbAppli;
	lEcart = lMaxSz+SurvParam.sDistanceCircle/1.2;
				
	float lPas = (float)(3.1416/6); //6
	float lAngle = 0;
				
	Enumeration<Sprite> lEnum2 = cVectSprite.elements();
	while( lEnum2.hasMoreElements() ){
						
	    Sprite lSprite = lEnum2.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		//								if( ((SurvAppli)lSprite).getNbCnx()!=1){
		lSprite.setLocation( (int)((Math.cos(lAngle)*lEcart)), (int)((Math.sin(lAngle)*lEcart)));
		lAngle += lPas;
		lEcart += 10;
		lPas   *= 0.97;
		if( lSprite.getX() < lMinX ) 
		    lMinX = (int)lSprite.getX();
								
		if( lSprite.getY() < lMinY ) 
		    lMinY = (int)lSprite.getY();												
	    }
	}			
	// recentrage
	Enumeration<Sprite> lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		lSprite.translate( -(lMinX-20), -(lMinY-20));
	    }				
	}
	cFrameCnx.forceRepaint();
    }
    //-----------------------
    void arrangeDoubleCircle(){

	//				System.out.println( "arrangeDoubleCircle" );
				
	int lMinX=999999999;
	int lMinY=999999999;

	Enumeration<Sprite> lEnum = cVectSprite.elements();
				
	// Comptage 
	double lMaxSz=0;
	int lNbAppli=0;

	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli 	&& ((SpriteAppli)lSprite).isPrimary()){								
		//	if( ((SurvAppli)lSprite).getNbCnx()!=1 )
								
		double lSz = lSprite.getWidth() + lSprite.getHeight();
		lMaxSz = ( lMaxSz < lSz ?  lSz :lMaxSz );
		lNbAppli++;
	    }
	}
				
	if( lNbAppli == 0 ){
	    arrangeCircle();
	    return;
	}
						
	//				System.out.println( "NbAppli:" + lNbAppli  );
	// Repartition
	double lEcart = SurvParam.sDistanceCircle*lNbAppli;
	lEcart = ((lMaxSz*lNbAppli)/8)+SurvParam.sDistanceCircle;

	float lPas = (float)(3.1416*2.0)/((float)lNbAppli);
	float lAngle = 0;
				
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli &&  ((SpriteAppli)lSprite).isPrimary() ){
								
		lSprite.setLocation( (int)((Math.cos(lAngle)*lEcart)+lEcart*1.2), (int)((Math.sin(lAngle)*lEcart)+lEcart*1.2));
		((SpriteAppli)lSprite).setAngle( lAngle );
								
		lAngle += lPas;
	    }
	}
				
	// Positionnement des backup
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli &&  ((SpriteAppli)lSprite).isPrimary() == false  ){
								
		Hashtable<String,SpriteCnx> lCnxTable= ((SpriteAppli)lSprite).getTableCnx();
		Enumeration<SpriteCnx> lEnumCnx = ((SpriteAppli)lSprite).getTableCnx().elements();
		if( lEnumCnx.hasMoreElements() ){
		    SpriteCnx lCnx = lEnumCnx.nextElement();
		    SpriteAppli lDest = lCnx.getDest();
										
		    lAngle = lDest.getAngle();
		    lSprite.setLocation( (int)((Math.cos(lAngle)*(lEcart+lMaxSz*2))+lEcart*1.2), (int)((Math.sin(lAngle)*(lEcart+lMaxSz*2))+lEcart*1.2));
		    lDest.setAngle( (float)(lAngle + ( 3.1416 / (lDest.getNbCnx()*10))) );												
		}										
	    }
	    if( lSprite.getX() < lMinX ) 
		lMinX = (int)lSprite.getX();
						
	    if( lSprite.getY() < lMinY ) 
		lMinY = (int)lSprite.getY();												
	}
				
				
	//				System.out.println( "MinX:" + lMinX + " lMinY:" + lMinY );
				
				
	// recentrage
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		lSprite.translate( -(lMinX-20), -(lMinY-20));
	    }				
	}
				
	cFrameCnx.forceRepaint();
    }
				
    //-----------------------
    void arrangeDoubleCircle3(){

	//				System.out.println( "arrangeDoubleCircle" );
				
	int lMinX=999999999;
	int lMinY=999999999;

	Enumeration<Sprite> lEnum = cVectSprite.elements();
				
	// Comptage 
	int lNbAppli=0;
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli ){								

		//								System.out.println( lSprite.getName() + "\tCnx:" + ((SurvAppli)lSprite).getNbCnx()  );

		if( ((SpriteAppli)lSprite).getNbCnx()!=1 )
		    lNbAppli++;
	    }
	}
				
				
	// Repartition
	float lEcart = SurvParam.sDistanceCircle*lNbAppli;
				
	float lPas = (float)(3.1416*2.0)/((float)lNbAppli);
	float lAngle = 0;
				
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		//								if( ((SurvAppli)lSprite).getNbCnx()!=1){
		if( ((SpriteAppli)lSprite).isPrimary() ){
		    lSprite.setLocation( (int)((Math.cos(lAngle)*lEcart)+lEcart*1.2), (int)((Math.sin(lAngle)*lEcart)+lEcart*1.2));
		    ((SpriteAppli)lSprite).setAngle( lAngle );

		    lAngle += lPas;
		}
	    }
	}
				
	// Positionnement des backup
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
						
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		//								if( ((SurvAppli)lSprite).getNbCnx() == 1){
		if( ((SpriteAppli)lSprite).isPrimary() ){
		    //										System.out.println( "====== 1 ====== " + lSprite.getName()  );

		    //								Hashtable<SurvAppli> lCnxTable= ((SurvAppli)lSprite).getTableCnx();
		    Enumeration<SpriteCnx> lEnumCnx = ((SpriteAppli)lSprite).getTableCnx().elements();
		    if( lEnumCnx.hasMoreElements() ){
			SpriteCnx lCnx = lEnumCnx.nextElement();
			SpriteAppli lDest = lCnx.getDest();
												
			lAngle = lDest.getAngle();
			lSprite.setLocation( (int)((Math.cos(lAngle)*lEcart*1.8)+lEcart*1.2), (int)((Math.sin(lAngle)*lEcart*1.8)+lEcart*1.2));
			//												lDest.setAngle( (float)(lAngle + ( 3.1416*2.0 / lDest.getNbCnx())) );												
		    }										
		}
	    }
	    if( lSprite.getX() < lMinX ) 
		lMinX = (int)lSprite.getX();
						
	    if( lSprite.getY() < lMinY ) 
		lMinY = (int)lSprite.getY();												
	}


	//				System.out.println( "MinX:" + lMinX + " lMinY:" + lMinY );
				
				
	// recentrage
	lEnum = cVectSprite.elements();
	while( lEnum.hasMoreElements() ){
						
	    Sprite lSprite = lEnum.nextElement();
	    if( lSprite.getType() == Sprite.sTypeAppli ){								
		lSprite.translate( -(lMinX-10), -(lMinY-10));
	    }				
	}

	cFrameCnx.forceRepaint();
    }
};
//*************************************************

