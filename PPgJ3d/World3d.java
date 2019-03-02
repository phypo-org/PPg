package org.phypo.PPg.PPgJ3d;



import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

 
import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Font;
import java.text.DecimalFormat;

import java.awt.*;

import org.phypo.PPg.PPgMath.*;

import org.phypo.PPg.PPgUtils.*;


//*************************************************
// Classe gerant le monde !
//*************************************************

abstract public class World3d 
		implements GLEventListener{

		public static PPgIniFile sTheIniFile = null;



		public Engine cEngine;
		public Engine getEngine() { return cEngine; }
		public GLWindow getGLWindow() { return cEngine.getGLWindow(); }


		protected Kamera3d cKamera;
		public Kamera3d getCurrentKamera() { return cKamera; }

 		ActorLocation cActorKamera;
		public void setKamera( ActorLocation pActorKamera ){ 	cActorKamera = pActorKamera;		}



		TextRenderer cTextDefaultRender;
		static public PPgRandom sGlobalRandom = new PPgRandom();



		//---------------------------------------------------
		public static boolean OpenIniFile( String pFileName ){

				sTheIniFile = new PPgIniFile();	

				return sTheIniFile.readIni( pFileName );	
		}

		// ---------------- FPS / time ----------------------
		// Pour indiquer les fps (mettre dans un aspect)



		static public double sCurrentFpsTime = 0;

		int cFpsCompteur=0;
		int cFpsCompteurSec=0;
		int cFpsCompteurMem=0;
		double cLastTurnTimeFpsSec;

		int cTurnCompteur=0;
		int cTurnCompteurSec=0;
		int cTurnCompteurMem=0;


		double cLastTurnTime;


		public  double cGameTimeAcceleration = 1.0;
		private double cCurrentTime=0;

		protected  double cGameTime=0;
		protected  double cGameTurnTimeDiff = 0;

		public double getGameTime() { return cGameTime; }
		public double getGameTurnTimeDiff() { return cGameTurnTimeDiff; }

		//		public  double cWantedFrameDuration = 0.04; // en seconde -> 25fps
		public  double cWantedFrameDuration = 0.01; // en seconde -> 100fps

		public void setFps( int pFps ) {
				cWantedFrameDuration = 1.0/ (double)pFps;
		}


		static public boolean sDebugFps = false;
		// ---------------- FPS / time ----------------------



		//------------------ Collision -------------------
		// A FAIRE : recuperer le code du moteur C++ pour les nlles collisionqs

		byte cFlagCollisionFactionFilter = 0;
		boolean cFlagCollisionBox = false;

		public void setFlagCollision( byte pFactionFilter, boolean pCollisionBox ){
				cFlagCollisionFactionFilter = pFactionFilter;
				cFlagCollisionBox = pCollisionBox;				
		}
		//------------------ Collision -------------------
			



		//------------- Pause/Stop -------------------

		boolean  cContinue = true;
		boolean  cPause = false;

		static public void   Stop() {
				sTheWorld.cContinue = false;
		}
		public static void FlipPause(){
				if( sTheWorld.cPause )
						sTheWorld.cPause = false;
				else
						sTheWorld.cPause = true;						
		}
		public static boolean IsPause() { return  sTheWorld.cPause;}




		//--------------  Gamer --------------------------
		protected ArrayList<Gamer>      cVectGamer       = new ArrayList<Gamer>();
		protected GamerHuman            cGamerHuman  = null;

		public void addGamer( Gamer pGamer ){
				cVectGamer.add( pGamer );
		}
		public void addGamerHuman( GamerHuman pGamer ){
				cGamerHuman =pGamer ;
				addGamer( pGamer );
		}
		public ArrayList<Gamer>      getVectGamer()  { return cVectGamer;}
		public GamerHuman            getGamerHuman() { return cGamerHuman;}
		//--------------  Gamer --------------------------




		//		Point2D.Double cSize = new Point2D.Double( 800, 600); // Size of the World
		//		public Point2D.Double getSize()  { return cSize; }

		public RectFloat3 cWorldBox;

		public  float getWidth()   { return cWorldBox.getWidth() ;}
		public  float getHeight()  { return cWorldBox.getHeight();}
		public  float getDepth()   { return cWorldBox.getDepth() ;}


		//		Point2D.Double cMousePos = new Point2D.Double();
		// cMouseBouton
		// cKeyPress
		// cModifKey

		public  Color  cBackgroundColor = Color.black;

		public  double cGeneralScale         = 1.0;  




		//-------------- Debug -------------
		public static boolean sFurtifMode = false;
		public static int     sDebugMode  = 0;
		public static int     sTraceMode  = 0;
		public static int     sVerbose    = 0;

		public static String  sDataPath   ="";
		public static String  sLevel      ="";


		public static final double sNanoSec = ((double)1)/1000000000;


		
		//----------------- Actor ----------------------------
		protected ArrayList<ActorBase>      cActorVirtual  = new ArrayList<ActorBase>();
		protected ArrayList<ActorLocation>  cActors      = new ArrayList<ActorLocation>();

		public    ArrayList<ActorLocation>  getActorLocation() { return cActors; }


		protected ArrayList<ActorBase>      cNewActorVirtual  = new ArrayList<ActorBase>();
		protected ArrayList<ActorLocation>  cNewActors      = new ArrayList<ActorLocation>();



		// Une liste pour les model a detruire ( notament les CompilObj !) auquel il faut un GL2
		protected ArrayList<ModelBase> cModelToDelete = new ArrayList<ModelBase>();
		public void addModelToDeleteList( ModelBase pModel ){
				cModelToDelete.add( pModel );
		}

		public void addVirtualActor( ActorBase pActor ) {

				//		System.out.println( "World3d.addVirtualActor");

				cNewActorVirtual.add( pActor );
				pActor.worldCallInit();
		}
		public void addActor( ActorLocation pActor ) {
				cNewActors.add( pActor );
				pActor.worldCallInit();
		}

		//------------------------------------------------
		public ActorLocation findActorLocation( double px, double py ){

				double lDistMin = 10e30;
				ActorLocation lFind =null;

				 
				for( ActorLocation lActor : cActors ) {

						double lDx = lActor.cLocation.x() -px;
						double lDy = lActor.cLocation.y() -py;

						double lDist = lDx*lDx + lDy*lDy;

						if( lDist < lDistMin ){
								lDistMin = lDist;
								lFind = lActor;
						}
				}
				return lFind;
		}
		//------------------------------------------------
		public ActorLocation findActorLocation( ActorLocation pVal ){

				double lDistMin = 10e30;
				ActorLocation lFind =null;

				 
				for( ActorLocation lActor : cActors ) {

						if( lActor == pVal ) continue ;

						double lDist = lActor.cLocation.distanceSq( pVal.cLocation );

						if( lDist < lDistMin ){
								lDistMin = lDist;
								lFind = lActor;
						}
				}
				return lFind;
		}
		//----------------- Actor -------------------------



		//------------------------------------------------
		static public  World3d Get() { return sTheWorld; }
		//------------------------------------------------
		static public World3d sTheWorld = null;

		//------------------------------------------------
		public World3d( DimFloat3 pSz, Engine pEngine ) {
				this( new RectFloat3( pSz ), pEngine);
		}
		//------------------------------------------------

		public World3d( RectFloat3 pWorldBox, Engine pEngine ) {// double pWith, double pHeight) {
				//cSize.setLocation( pWith, pHeight );

			 System.out.println( "World creating ..." );

				cWorldBox = pWorldBox;
				cGameTime = cCurrentTime = System.nanoTime()*sNanoSec;
				sTheWorld = this;
				cEngine = pEngine;
				cEngine.setWorld( this,  DefaultUserControl3d.sTheDefaultUserControl3d  );
		}
		//------------------------------------------------
		public void  setUserControl( UserControl3d pUserControl){
				cEngine.setUserControl( pUserControl );
		}
		public UserControl3d getUserControl() {
				return cEngine.getUserControl();
		}

		//------------------------------------------------
		//-------------  Boucle principale  --------------
		//------------------------------------------------
		public javax.swing.Timer  sTimer;
		/*
		// Gestion par un timer swing
		public void runTimer( int lTimeMili ){
				
				run();

				sTimer = new javax.swing.Timer(lTimeMili, this);
				sTimer.start();

				
				//	while( cContinue ){
				//}
				//	worldCallEndRun();					 			
		}
		*/
		//------------------------------------------------
		// call by the timer
		/*
    public void actionPerformed(ActionEvent pEv ) {

				if( cContinue == false ){
						sTimer.stop();
						worldCallEndRun();					 			
						return;
				}
				action();
				cEngine.callWinRedraw();
		}
		*/
		//------------------------------------------------
		//	pas de timer (thread independant)
		public void run(){
				
				System.out.println("World.run 1 pass");
				
				cContinue = true;
				
				try{						
						Thread.sleep( 1000 );
				}
				catch(Exception e){			 
						System.out.println( e );
						e.printStackTrace();
				}
				
				
				cLastTurnTime = System.nanoTime()*sNanoSec;
				
				////		cEngine.initEngine( this );				
				
				worldCallBeginRun(); // for dervied class
				
				while( cContinue ){
						action();
						cEngine.callWinRedraw();
				}
				worldCallEndRun();					 			
		}		
		//------------------------------------------------

		public void runExtern(){
				
				System.out.println("World.run Extern 1 pass");
				
				cContinue = true;
				
				try{						
						Thread.sleep( 1000 );
				}
				catch(Exception e){			 
						System.out.println( e );
						e.printStackTrace();
				}
				
				
				cLastTurnTime = System.nanoTime()*sNanoSec;
			
				////		cEngine.initEngine( this );				
				
				worldCallBeginRun(); // for dervied class
				
				//	while( cContinue ){
				// action();
				//}
				//	worldCallEndRun();					 			
		}		

		//------------------------------------------------
    public void action() {

				//				System.out.println( "World.action" );

				if( cContinue == false ){
						worldCallEndRun();	// for dervied class				 			
						return;
				}

				try{	
				 cLastTurnTime = System.nanoTime()*sNanoSec;
				
			
				//															System.out.println("World.run ");
				 //				System.out.println( "call input " );
				//				worldCallInputTurn();
				
				if( cPause == false ){									
						cCurrentTime = System.nanoTime()*sNanoSec;       // resultat en seconde 
						double lTimeDiff = cCurrentTime - cLastTurnTime; // temps passe depuis la dernier fois en seconde !
						
						double lTimeCycle = cWantedFrameDuration - lTimeDiff; // ecart avec le temps desiré
						
						if(  lTimeCycle > 0  )  {
								// on a trop de fps !
								//		System.out.println( " -> " +lTimeDiff  + " sleep("+ lTimeCycle*1000.0 +")" );
								
								Thread.sleep( (int)(lTimeCycle*1000.0));
								
								cCurrentTime = System.nanoTime()*sNanoSec;
								lTimeDiff = cCurrentTime - cLastTurnTime; // temps passe depuis la dernier fois en seconde !
						}
						
									
						// sinon bugs divers (tant pis ca va ralentir)
						// sinon deplacement trop rapide ?
						
						if( lTimeDiff > 0.01  || lTimeDiff <= 0 )
								lTimeDiff = 0.01; 
						

						cGameTurnTimeDiff = lTimeDiff*cGameTimeAcceleration;
						cGameTime += cGameTurnTimeDiff;
						
						// execute les ordres des joueurs
						for( Gamer lGamer: cVectGamer){
								lGamer.worldCallExecOrder(lTimeDiff);
						}
						
						sCurrentFpsTime  = lTimeDiff;
						//	System.out.println( "cFpsCompteur:" + cFpsCompteur + " " +sVectFpsTime [cFpsCompteur%sVectFpsTime.length]);
						cFpsCompteur++;
						
						
						//===================
						oneActorsTurn( cGameTurnTimeDiff );
						//===================										
						
						
				}
				else {
						System.out.println( "Pause sleep" );
						cLastTurnTime = System.nanoTime()*sNanoSec;
						Thread.sleep((int)(cWantedFrameDuration ));						
				}
				///////								sceneDraw();
				
				
				// execute les renders pour les joueurs humains !
				//										System.out.println( "World.run ready:" + lGamerHuman.isReadyToDraw());
				
				
				}	catch(Exception e){			 
						System.out.println( e );
								e.printStackTrace();
				}
		}		
		//------------------------------------------------
		//   Un tour d'execution pour chaque acteur
		//------------------------------------------------
		
		void oneActorsTurn( double pTimeDiff) {


				cTurnCompteur++;
				//				System.out.println( "World.oneActorsTurn "+ pTimeDiff);


			// Ajout des nouveaux acteurs
				cActorVirtual.addAll( cNewActorVirtual );
				cNewActorVirtual.clear();
				
				cActors.addAll( cNewActors );
				cNewActors.clear();


				//=====================================

				worldCallBeginTurn(pTimeDiff);
				
				/*
				if( sDebugFps) {


								System.out.println( "World.oneTurn " + pTimeDiff 
																		+" ActorsVirtual :" + cActorVirtual.size()
																		+" Actors : " + cActors.size());
				}
				*/
				// Detection des acteurs en fin de vie
				// ou execution de leur actions


				for( ActorBase lActor : cActorVirtual ) {

						//				System.out.println( "oneActorsTurn- VirtualActor");

						if( lActor.testTimeOfLife( cGameTime ) ){
								//			System.out.println( "oneActorsTurn- VirtualActor st Deleted");

								lActor.setDeleted();								
						}
						else {
								//			System.out.println( "oneActorsTurn- VirtualActor worldCallAct");

								lActor.worldCallAct( (float)pTimeDiff );
						}
				}	
				
				for( ActorLocation lActor : cActors ) {
						if( lActor.testTimeOfLife( cGameTime ) ){
								lActor.setDeleted();								
						}
						else
								lActor.worldCallAct( (float)pTimeDiff );
				}	

				if( cActorKamera != null )
						cActorKamera.worldCallAct(  (float)pTimeDiff );

				
				// Detection des collisions et executions des fonction ad hoc
				if( cFlagCollisionFactionFilter != 0  ){
						detectCollision(	cFlagCollisionFactionFilter, cFlagCollisionBox, cActors );
				}
						

				worldCallAfterActRun(pTimeDiff);

				
				//=====================================
				

				ActorBase lActor;
				
				// elimination des Acteurs mort
				ListIterator<ActorBase> lIterActor = cActorVirtual.listIterator() ;
				while( lIterActor.hasNext() ){
						if( ( lActor = lIterActor.next()).isDeleted() ){								
								lActor.worldCallClose();
								lIterActor.remove();	
								lActor.execDeleted();
					}			 
				}	
				
				ListIterator<ActorLocation> lIterActorLoc = cActors.listIterator() ;
				while( lIterActorLoc.hasNext() ){
						if( ( lActor = lIterActorLoc.next()).isDeleted() ){
								lActor.worldCallClose();
								lIterActorLoc.remove();	
								lActor.execDeleted();
					}			 
				}	
				
				//				for( Gamer lGamer: cVectGamer ){
				//						lGamer.run( pTimeDiff );
				//				}

				worldCallEndTurn(pTimeDiff); 

		}
		//------------------------------------------------
		// Affichage de la scene
		//------------------------------------------------
		
		public void sceneDraw(GL2 pGl ){

				//				System.out.println( "World.sceneDraw " );

				//================================================
				//On profite d'avoir un GL2 pour detruire les model ( CompilObj)
				{
						for( ModelBase lModel : cModelToDelete )
								lModel.destroy( pGl );
						cModelToDelete.clear();
				}	
				//================================================

				
				worldCallBeginSceneDraw(pGl );


				for( ActorBase lActor : cActorVirtual ) { // A VIRER APRES LES TEST 
						lActor.worldCallDraw( cEngine );
				}	
				for( ActorLocation lActor : cActors ) {
						//faire un test pour tester si l'acteur est actif ...
						lActor.worldCallDraw( cEngine );
				}
				
				if( cActorKamera != null )
						cActorKamera.worldCallDraw( cEngine );

				
				if( sDebugFps ){
						
						cLastTurnTime = cCurrentTime;	

						if( (cLastTurnTime  - cLastTurnTimeFpsSec ) > 1.0){

								cFpsCompteurSec = cFpsCompteur - cFpsCompteurMem;
								cFpsCompteurMem = cFpsCompteur;
								cTurnCompteurSec = cTurnCompteur - cTurnCompteurMem;
								cTurnCompteurMem = cTurnCompteur;
								cLastTurnTimeFpsSec = cLastTurnTime;
						}
								

						if( Engine.sTheGlDrawable != null ) {

								if( cTextDefaultRender ==null ){
										cTextDefaultRender = new TextRenderer(new Font("SansSerif", Font.BOLD, 24));
										cTextDefaultRender.setColor( 0f, 1.0f, 0.2f, 0.5f );
								}

								//								String lStr =  Integer.toString( cFpsCompteurSec ) + ' '  + Integer.toString( cTurnCompteurSec ) +' ' + Integer.toString(cActorVirtual.size()) +"/"+ Integer.toString(cActors.size());
								//								cTextDefaultRender.beginRendering( Engine.sTheGlDrawable.getWidth(), Engine.sTheGlDrawable.getHeight());
													 								
								//						//						cTextDefaultRender.draw( lStr, 10, 10);
								//						cTextDefaultRender.endRendering();								

						}
				}
					
				worldCallEndSceneDraw(pGl);
		}			
		//------------------------------------------------
		public boolean limitToWorld( Float3 pPos ) {

				boolean lFlagDetect = false;

				for( int i = 0; i< 3; i ++ ) {
						
						if( pPos.cVect[i] < cWorldBox.cXYZ.cVect[i] ){
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i];	
								lFlagDetect = true;

						} else 	if( pPos.cVect[i] > cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i] ) {
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i];
								lFlagDetect = true;
						}			
				}
				return lFlagDetect;
		}
		//------------------------------------------------
		public boolean moebiusWorld( Float3 pPos ) {

				boolean lFlagDetect = false;

				for( int i = 0; i< 3; i++ ) {
						
						if( pPos.cVect[i] < cWorldBox.cXYZ.cVect[i] ){
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i];
								lFlagDetect = true;

						} else 	if( pPos.cVect[i] > cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i] ) {
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i];	
								lFlagDetect = true;
						}			
				}
				return lFlagDetect;					
		}
		//------------------------------------------------
		public boolean bounceOnWorldLimit( Float3 pPos, Float3 pSpeed ) {

				boolean lFlagDetect = false;

				for( int i = 0; i< 3; i++ ) {
						
						if( pPos.cVect[i] < cWorldBox.cXYZ.cVect[i] ){
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i];	
								pSpeed.cVect[i] = -pSpeed.cVect[i];
								lFlagDetect = true;
								
						} else 	if( pPos.cVect[i] > cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i] ) {
								pPos.cVect[i] = cWorldBox.cXYZ.cVect[i]+cWorldBox.cSize.cVect[i];
								pSpeed.cVect[i] = -pSpeed.cVect[i];
								lFlagDetect = true;
						}						
				}
				return lFlagDetect;
		}

		//------------------------------------------------
		public boolean outOfWorld( Float3 pPos ) {

				return ! cWorldBox.contains( pPos );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		// Detection des collisions entre ActorLocation
		//  avec filtre pour les Factions

		void detectCollision( byte lFactionFilter, boolean pFlagBox, ArrayList<ActorLocation> pActors ){
				
				try {
						
						//					System.out.println( "=================================== detectCollision " + pActors.size() +" ================================="   + cFlagCollisionFactionFilter);
						
						
						ListIterator<ActorLocation> lIterActor = pActors.listIterator() ;
						while( lIterActor.hasNext() ){
								
								ActorLocation lActor1 = lIterActor.next();
								if( lActor1.filterFaction( lFactionFilter ) == false) 
										continue;

								//			System.out.println( "===   detectCollision "   + lActor1.getFaction() + " " + lActor1.getName() );

										
								if( lIterActor.nextIndex()+1 < pActors.size() ){
										ListIterator<ActorLocation> lIterActor2 = pActors.listIterator( lIterActor.nextIndex()+1);
										while( lIterActor2.hasNext() ){
												ActorLocation lActor2 =  lIterActor2.next();
												if( lActor2.filterFaction( lFactionFilter ) == false 
														|| lActor2.isSameFaction( lActor1 ))
														continue;

												//				System.out.println( "===      detectCollision "   + lActor1.getFaction() + " " + lActor2.getFaction() + " " + lActor2.getName() );

												/*
												if( pFlagBox == true ) {
														if( lActor1.collisionBoundingBox( lActor2 )
																&& lActor1.worldCallAcceptCollision( lActor2 )      // et si les deux acceptent la collision !
																&& lActor2.worldCallAcceptCollision( lActor1 )){
																
																lActor1.worldCallDetectCollision( lActor2, true );
																lActor2.worldCallDetectCollision( lActor1, false );
														}
												}															
												else 	*/
												{

														
														//									if( lActor1.collisionBoundingSphere(lActor2))
														//																System.out.println( "*********                detectCollision "   + lActor1.getFaction() + " " + lActor2.getFaction() + " " + lActor2.getName() );

										
														if( lActor1.collisionBoundingSphere(lActor2)
																&& lActor1.worldCallAcceptCollision( lActor2 ) 
																&& lActor2.worldCallAcceptCollision( lActor1 )){
																
																//						System.out.println( " CALL ACCEPT OK");
																lActor1.worldCallDetectCollision( lActor2, true );
																lActor2.worldCallDetectCollision( lActor1, false );
														}																					
												}
										}
								}
						}
				} catch( Exception lEx ) {	
							System.err.println("lEx");
							lEx.printStackTrace();   
				}																
		}

		//------------------------------------------------
		// utilise les filtre si != 0
		
		// Pour une liste d'acteur independante (Swarm)
		public boolean detectCollisionActor( byte lFactionFilter, boolean pFlagBox, ActorLocation pActor1, ArrayList<ActorLocation> pActors, boolean pExecCall  ){

				boolean lCollisionDetect = false;
				
				try {
						
						//						System.out.println( "=================================== detectCollisionActor( " + pActors.size() +" ================================="   + cFlagCollisionFactionFilter);
						
						
						if(lFactionFilter !=0 &&  pActor1.filterFaction( lFactionFilter ) == false) 
								return false;
						
						//		System.out.println( "===   detectCollision "   + pActor1.getFaction() + " " + pActor1.getName() );

						
						ListIterator<ActorLocation> lIterActor2 = pActors.listIterator();
						while( lIterActor2.hasNext() ){
								ActorLocation lActor2 =  lIterActor2.next();
								if( lFactionFilter !=0 && (lActor2.filterFaction( lFactionFilter ) == false 
																					 || lActor2.isSameFaction( pActor1 )))
										continue;
								
								//								System.out.println( "===      detectCollisionActor "   + pActor1.getFaction() + " " + lActor2.getFaction() + " " + lActor2.getName() );
								
								if( pExecCall ) {		
										/*
										if( pFlagBox == true ) {
												if( pActor1.collisionBoundingBox( lActor2 )
														&& pActor1.worldCallAcceptCollision( lActor2 )      // et si les deux acceptent la collision !
														&& lActor2.worldCallAcceptCollision( pActor1 )){
														
														pActor1.worldCallDetectCollision( lActor2, true );
														lActor2.worldCallDetectCollision( pActor1, false );
														lCollisionDetect = true;
												}
										}															
										else */
										{
												
												if( pActor1.collisionBoundingSphere(lActor2)
														&& pActor1.worldCallAcceptCollision( lActor2  ) 
														&& lActor2.worldCallAcceptCollision( pActor1 )){
														
														
														//														System.out.println( "Actor CALL ACCEPT OK ");
														
														pActor1.worldCallDetectCollision( lActor2, true );
														lActor2.worldCallDetectCollision( pActor1, false );
														lCollisionDetect = true;
												}								
										}													
								}
								else {
										/*
										if( pFlagBox == true ) {
												if( pActor1.collisionBoundingBox( lActor2 )) {
														return true;
												}
										}															
										else 
										*/
										{
												
												if( pActor1.collisionBoundingSphere(lActor2) ){
														return true;														
												}								
										}													
										
								}
										
						}
				} catch( Exception lEx ) {	
							System.err.println("lEx");
							lEx.printStackTrace();   
				}						
										
				return lCollisionDetect;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		@Override
				public void init(GLAutoDrawable pGlDraw){

				System.out.println( "World.init GLEventListener" );
				cEngine.init( pGlDraw );

				runExtern();
		}
		//------------------------------------------------
		// Called by the drawable to initiate OpenGL rendering by the client
		@Override
				public void display(GLAutoDrawable pGlDraw){
				
				//				System.out.println( "World.display GLEventListener" );

				action();
				cEngine.display( pGlDraw );	 // call sceneDraw()				
		}
		//------------------------------------------------
		@Override
		public void dispose(GLAutoDrawable pGlDraw){

				System.out.println( "World.dispose GLEventListener" );

				cEngine.dispose( pGlDraw );				
		}
		//------------------------------------------------
		@Override
				public void reshape( GLAutoDrawable pGlDraw, int x, int y, int width, int height) {
				
				System.out.println( "World. GLEventListener" );

				cEngine.reshape( pGlDraw, x, y, width, height );			 
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public void keyPressed(KeyEvent e) {;}
		// Fonctions a derivé, appeler par le fonctionnement de World
		//		public void worldCallInputTurn()                       {;}
		public void worldCallBeginRun()                    {;}
		public void worldCallEndRun()                      {;}

		public void worldCallBeginTurn( double pTimeDiff)  {;}
		public void worldCallAfterActRun(double pTimeDiff) {;}
		public void worldCallEndTurn( double pTimeDiff)    {
		}

		public void worldCallBeginSceneDraw(GL2 pGl )   {;}
		public void worldCallEndSceneDraw(GL2 pGl)     {;}

		// Fonctions a derivé, provoquer de facon externe 


		public void worldExternalCallEndLevel( ActorBase pCaller, boolean pGamerFail ) {;}  // pour faire un niveau suivant par exemple

		//------------------------------------------------
		public void callEngineInit(GL2 pGl){;}
		public void callEngineStop() {;}

		public void callEngineDrawBegin(GL2 pGl ){;}
		public void callEngineDrawEnd( GL2 pGl){;}
};
//*************************************************
