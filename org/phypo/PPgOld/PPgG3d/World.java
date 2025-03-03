package com.phipo.PPg.PPgG3d;



import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;



import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import java.awt.*;

import com.phipo.PPg.PPgMath.*;

import com.phipo.PPg.PPgUtils.*;


//*************************************************
// Classe gerant le monde !
//*************************************************

abstract public class World{

		public static PPgIniFile sTheIniFile = null;

		public Engine cEngine;

		Kamera3d cKamera; 
		ActorLocation cActorKamera;

		public void setKamera( ActorLocation pActorKamera ){
				cActorKamera = pActorKamera;
		}

		//---------------------------------------------------
		public static boolean OpenIniFile( String pFileName ){

				sTheIniFile = new PPgIniFile();	

				return sTheIniFile.readIni( pFileName );	
		}

		// ---------------- FPS / time ----------------------
		// Pour indiquer les fps (mettre dans un aspect)
    static public double [] sVectFpsTime = new double[50];
		static public double sCurrentFpsTime = 0;
		int cFpsCompteur=0;

		static public PPgRandom sGlobalRandom = new PPgRandom();


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


		public boolean sDebugFps = false;
		// ---------------- FPS / time ----------------------



		//------------------ Collision -------------------

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


		public void addVirtualActor( ActorBase pActor ) {
				cNewActorVirtual.add( pActor );
				pActor.worldCallInit();
		}
		public void addActor( ActorLocation pActor ) {
				cNewActors.add( pActor );
				pActor.worldCallInit();
		}


		//----------------- Actor ----------------------------




		//------------------------------------------------
		static public  World Get() { return sTheWorld; }
		//------------------------------------------------
		static public World sTheWorld = null;


		public World( RectFloat3 pWorldBox, Engine pEngine ) {// double pWith, double pHeight) {
				//cSize.setLocation( pWith, pHeight );

				cWorldBox = pWorldBox;
				cGameTime = cCurrentTime = System.nanoTime()*sNanoSec;
				sTheWorld = this;
				cEngine = pEngine;
		}

		//------------------------------------------------
		//-------------- Boucle principale --------------
		//------------------------------------------------

		public void run(){

				//		System.out.println("World.run 1");

				
				cContinue = true;
				
				try{						
						Thread.sleep( 1000 );
				}
				catch(Exception e){			 
						System.out.println( e );
						e.printStackTrace();
				}
				
				
				double lLastTurnTime = System.nanoTime()*sNanoSec;

				cEngine.initEngine( this );

				
				worldCallBeginRun();
				
				while( cContinue ){
						try{	
								double lTmpTime = System.nanoTime()*sNanoSec;
								

								//															System.out.println("World.run ");
								System.out.println( "call input " );
								worldCallInputTurn();

								if( cPause == false ){									
										cCurrentTime = System.nanoTime()*sNanoSec;       // resultat en seconde 
										double lTimeDiff = cCurrentTime - lLastTurnTime; // temps passe depuis la dernier fois en seconde !
										
										double lTimeCycle = cWantedFrameDuration - lTimeDiff; // ecart avec le temps desiré

										if(  lTimeCycle > 0  )  {
												// on a trop de fps !
												//		System.out.println( " -> " +lTimeDiff  + " sleep("+ lTimeCycle*1000.0 +")" );
												
												Thread.sleep( (int)(lTimeCycle*1000.0));
												
												cCurrentTime = System.nanoTime()*sNanoSec;
												lTimeDiff = cCurrentTime - lLastTurnTime; // temps passe depuis la dernier fois en seconde !
										}
										
									
										// sinon bugs divers (tant pis ca va ralentir)
										// sinon deplacement trop rapide
										
										if( lTimeDiff > 0.10  || lTimeDiff <= 0 )
												lTimeDiff = 0.10; 
										
										cGameTurnTimeDiff = lTimeDiff*cGameTimeAcceleration;
										cGameTime += cGameTurnTimeDiff;

										// execute les ordres des joueurs
										for( Gamer lGamer: cVectGamer){
												lGamer.worldCallExecOrder(lTimeDiff);  
										}

										// Pour afficher la dure 
										/*
											for(int i=0;i<sVectFpslength-1; i++)
											sVectFps[i]=sVectFps[i+1];
											sVectFps[sVectFps.length-1]=lTimeDiff;
										*/
										sVectFpsTime [cFpsCompteur%sVectFpsTime.length] = sCurrentFpsTime  = lTimeDiff;
										System.out.println( "cFpsCompteur:" + cFpsCompteur + " " +sVectFpsTime [cFpsCompteur%sVectFpsTime.length]);
										 cFpsCompteur++;

										
										

										// Ajout des nouveaux acteurs
										cActorVirtual.addAll( cNewActorVirtual );
										cNewActorVirtual.clear();

										cActors.addAll( cNewActors );
										cNewActors.clear();


										oneTurn( cGameTurnTimeDiff );
																				
										
										lLastTurnTime = cCurrentTime;						
								}
								else {
										System.out.println( "Pause sleep" );
										lLastTurnTime = System.nanoTime()*sNanoSec;
										Thread.sleep((int)(cWantedFrameDuration ));						
								}
								sceneDraw();

								
								// execute les renders pour les joueurs humains !
								//										System.out.println( "World.run ready:" + lGamerHuman.isReadyToDraw());
										
									
						}	catch(Exception e){			 
								System.out.println( e );
								e.printStackTrace();
						}
				}
				worldCallEndRun();					 			
		}
		
		//------------------------------------------------
		//
		//------------------------------------------------
		
		void oneTurn( double pTimeDiff) {


				//=====================================

				worldCallBeginTurn(pTimeDiff);
				

				if( sDebugFps)
						System.out.println( "World.oneTurn " + pTimeDiff 
																+" ActorsVirtual :" + cActorVirtual.size()
																+" Actors : " + cActors.size());
				

				// Detection des acteurs en fin de vie
				// ou execution de leur actions
				for( ActorBase lActor : cActorVirtual ) {

						if( lActor.testTimeOfLife( cGameTime ) ){
								lActor.setDeleted();								
						}
						else
								lActor.worldCallAct( (float)pTimeDiff );
				}	
				
				for( ActorLocation lActor : cActors ) {
						if( lActor.testTimeOfLife( cGameTime ) ){
								lActor.setDeleted();								
						}
						else
								lActor.worldCallAct( (float)pTimeDiff );
				}	
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
					}			 
				}	
				
				ListIterator<ActorLocation> lIterActorLoc = cActors.listIterator() ;
				while( lIterActorLoc.hasNext() ){
						if( ( lActor = lIterActorLoc.next()).isDeleted() ){
								lActor.worldCallClose();
								lIterActorLoc.remove();	
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
		
		public void sceneDraw(){
				//			System.out.println( "World.draw " );
				
				beginSceneDraw( );


				for( ActorBase lActor : cActorVirtual ) {
						lActor.worldCallDraw( cEngine );
				}	
				for( ActorLocation lActor : cActors ) {
						//faire un test pour tester si l'acteur est actif ...
						lActor.worldCallDraw( cEngine );
				}
				
				cActorKamera.worldCallDraw( cEngine );

				/*
				if( sDebugFps ){
						
						String lStr = Integer.toString( (int)(1.0/sCurrentFpsTime) )+ " " + Integer.toString(cActorVirtual.size()) +"/"+ Integer.toString(cActors.size());

						pGraph.setColor( Color.black );
						pGraph.drawString( lStr, 9, 29 );
						pGraph.drawString( lStr, 11, 31 );
						pGraph.drawString( lStr, 11, 29 );
						pGraph.drawString( lStr, 9, 31 );

						pGraph.setColor( Color.white );
						pGraph.drawString( lStr, 10, 30 );
				}
				*/	
				endSceneDraw( );
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

								//		System.out.println( "===   detectCollision "   + lActor1.getFaction() + " " + lActor1.getName() );

										
								if( lIterActor.nextIndex()+1 < pActors.size() ){
										ListIterator<ActorLocation> lIterActor2 = pActors.listIterator( lIterActor.nextIndex()+1);
										while( lIterActor2.hasNext() ){
												ActorLocation lActor2 =  lIterActor2.next();
												if( lActor2.filterFaction( lFactionFilter ) == false 
														|| lActor2.isSameFaction( lActor1 ))
														continue;

												//												System.out.println( "===      detectCollision "   + lActor1.getFaction() + " " + lActor2.getFaction() + " " + lActor2.getName() );

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
		// Fonctions a derivé, appeler par le fonctionnement de World
		public void beginSceneDraw() { cEngine.beginDraw();}
		public void endSceneDraw()   { cEngine.endDraw();}

		public void worldCallInputTurn()                       {;}
		public void worldCallBeginRun()                    {;}
		public void worldCallEndRun()                      {;}

		public void worldCallBeginTurn( double pTimeDiff)  {;}
		public void worldCallAfterActRun(double pTimeDiff) {;}
		public void worldCallEndTurn( double pTimeDiff)    {
		}

		public void worldCallBeginSceneDraw( )   {;}
		public void worldCallEndSceneDraw( )     {;}

		// Fonctions a derivé, provoquer de facon externe 


		public void worldExternalCallEndLevel( ActorBase pCaller, boolean pGamerFail ) {;}  // pour faire un niveau suivant par exemple

		//------------------------------------------------
		public void callEngineInit(){;}
		public void callEngineStop() {;}

		public void callEngineRenderGL(){;}

		public void callEngineTurnBegin(){;}
		public void callEngineTurnEnd(  ){;}
};
//*************************************************
