package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************
class ActionEntityMove extends ActionEntity {

		ArrayList<PathCase> cPath = null;
		Point2D.Double cPointDest = null;

		PathCase cNextCase = null;
		PathCase cTheoCurrentCase = cNextCase;

		double cWaiting = 0;

		ActionEntityMove( EntityMobil pEntity, Point2D.Double pPointDest, ArrayList<PathCase> pPath ){
				super( pEntity );

				cTheoCurrentCase = pEntity.getCurrentCase();

				cPointDest = (Point2D.Double) pPointDest.clone();
				cPath = pPath;
				pEntity.cPath = pPath;
		}
		
		//---------------------------------
		boolean move(double pTimediff)
     {
				 // S'il n'y a de Path, on le calcule
				 if( cPath == null ){
						 ((EntityMobil)cEntity).cPath  = cPath = new ArrayList<PathCase>();
						 if( PathFinder.GetPath( World.sPathCarte, cEntity, cPointDest, cPath ) == false ){
								return false;
						 }
				 }
				 
				 // On recupere la case courante 
				 PathCase lPositionCase = cEntity.getCurrentCase();
				 
				 // la case suivante
				 if( cNextCase == null ){
						 if( cPath.isEmpty() == false ){
								 cNextCase = cPath.remove( cPath.size()-1 );
						}						 
				 }
				 
				 
				 
				 if( cNextCase == lPositionCase ){
						 if( cPath.isEmpty() == false ) {
								 cTheoCurrentCase = cNextCase;
								 cNextCase = cPath.remove( cPath.size()-1 );
						 }
						 else
								 cNextCase = null;	
				 }
				 
				 
				 double lDistX;
				 double lDistY;
				 
				 if( cNextCase == null ){

						 // Positionnement fin !!!!
						lDistX  = cPointDest.getX() - cEntity.getMX();
						lDistY  = cPointDest.getY() - cEntity.getMY();
						double lNorme = Math.sqrt(lDistX*lDistX + lDistY*lDistY );

						/////////////		 System.out.println( "Fin DistX=" + lDistX + " DistY=" + lDistY );

						 // On est arrive a la derniere case du parcours !

						 double lSpeed = cEntity.getPrototype().getMoving( cTheoCurrentCase )*pTimediff;
						 if( lNorme > lSpeed) {
								 lSpeed /= lNorme;
								 ((EntityMobil)cEntity).setSpeed( lSpeed*lDistX, lSpeed*lDistY );
						 }
						 else {
								 // On est arrive au bon endroit
								 ////								cEntity.setAction(null);
								 /////		System.out.println( " *********** STOP ********** " );
								 ((EntityMobil)cEntity).setSpeed( 0, 0 );						
								 ((EntityMobil)cEntity).moveTo( cPointDest );	// On est sur la bonne case de toute facon!					
								 ((EntityMobil)cEntity).cPath  = null;
								 return false;
						 }
				 }
				 else {
						 lDistX = cNextCase.getMiddleMeterX() - cEntity.getMX();
						 lDistY = cNextCase.getMiddleMeterY() - cEntity.getMY();

						 //////////						 System.out.print( "\tDistX=" + lDistX + " DistY=" + lDistY );

						 double lNorme = Math.sqrt(lDistX*lDistX + lDistY*lDistY );

						 //	 double lSpeed = (cEntity.getPrototype().getMoving( lPositionCase )*pTimediff)/ lNorme;
						 double lSpeed = (cEntity.getPrototype().getMoving( cTheoCurrentCase )*pTimediff)/ lNorme;
						 
						 //////					 System.out.print( "\tSpeed:" + lSpeed );
						 //						 System.out.println( " Speed:" + lSpeed  );						 
						 ////						 // Probleme la trajectoire peut empieter sur des case mauvaise -> Speed a 0 ! On va tricher
						 //						 if( lSpeed == 0 ){								 
						 //								 lSpeed = (cEntity.getPrototype().getMoving( cNextCase )*pTimediff)/ lNorme;

						 
						 // Pour eviter bug de depassement de la position suivante 
						 // (On peut peu etre faire mieux
						 if( lSpeed > 0.5 )
								 lSpeed = 0.5;
						 else
						 if( lSpeed < -0.5 )
								 lSpeed = -0.5;


								 
						 ((EntityMobil)cEntity).setSpeed( lSpeed*lDistX, lSpeed*lDistY );
						 ///////					 System.out.println( "\tSpeedX:" + lSpeed*lDistX + " SpeedY:" + lSpeed*lDistY );

				 }
				 return true;
		 }


		//-----------------------------
		boolean refindPath(){
				
				ArrayList<PathCase> lPath = new ArrayList<PathCase>();				
				if( PathFinder.GetPath( World.sPathCarte, cEntity, cPointDest, lPath ) ){
						((EntityMobil)cEntity).cPath = cPath  = lPath;
						return true;
				}
				return false;
		}
		//-----------------------------
		void setPath( Point2D.Double pPointDest, ArrayList<PathCase> pPath ){
				
				cPointDest = pPointDest;
				((EntityMobil)cEntity).cPath =cPath = pPath;
		}
		
		//-----------------------
		// On va aller de case en case avec pour objectif la case suivante

		boolean exec( double pTimeDiff ){
				

				if( cWaiting > 0 ){
						cWaiting -= pTimeDiff;
						if( cWaiting > 0 )
								return true;
						cWaiting = 0;
				}
				

				///////				System.out.println( "Move->exec");

				if( move(pTimeDiff) == false ){

						// On est arrive a destination 								
						//						activateNextAction();
						///////						System.out.println( ">>>return fini");

						return false; // C'EST FINI 
				}
				
				
				
				if( ((EntityMobil)cEntity).cSpeedX == 0 &&  ((EntityMobil)cEntity).cSpeedY == 0 ){
						System.out.println( "XXXXXXXXXXXXXXXXXXX Move->Speed 0 XXXXXXXXXXXXXXXXXXX");

						// VERIFIER POURQUOI ??????
						return true;
				}
				
				// La prochaine position
				double pX = cEntity.getMX() + ((EntityMobil)cEntity).cSpeedX*pTimeDiff;
				double pY = cEntity.getMY() + ((EntityMobil)cEntity).cSpeedY*pTimeDiff;
				
				
				if( World.GetPathCarte().moveEntity( cEntity, pX, pY ) == true ){

						
						cEntity.setOrientation( Util.GetOrientation( pX, pY, cEntity.getMX(), cEntity.getMY() ));

						cEntity.moveTo( pX, pY );
						// calculer la direction vers la prochaine case et tourner l'entity 
								
						//////						System.out.println( "Move");
						return true;  // ON CONTINUIERA AU PROCHAIN TOUR
				}
				
			 				
				
				// Il y a un probleme ! On va regarder dans la case ou l'on aurait du aller								
				
				PathCase lCase = World.sPathCarte.getMeter( pX, pY );
				if( lCase == null ){
						// La il a vraiment un pb !!!!
						System.out.println( "case null");
						return false;
				}
				
				
						
				// On est sur une mauvaise case pour une raison X (pb de vitesse ?)
				//				if( lCase != cTheoCurrentCase ){						
				//				if( refindPath() == false ){
				//				return false;   // On arrete les frais !								
				//		}
				//		return true;
				//	}
				
				
				
				// On est sur une case valide mais il y a un obstacle				
				//				double lCoef = cEntity.getPrototype().getMoving( lCase ); 
				//				if( lCoef == 0 ){
				//						// nouveau decor, erreur du PathFinder ? -> On cherche un autre chemin
				//						
				//						if( refindPath() == false ){
				//								System.out.println( "On arrete les frais ");
				//								
				//								return false;   // On arrete les frais !
				//						}						
				//						return true;
				//				}
				
								
				if( lCase.isEmpty(cEntity) == false ){
						Entity lCurrentEntity = lCase.getEntityZone();
						if( lCurrentEntity != null ){
								if( lCurrentEntity.isMobil() == false ){
										
										// C'est un batiment !
												
										// On va essayer de le contourner
										PathCase lPositionCase = cEntity.getCurrentCase();

										int lDx = lCase.getX()-lPositionCase.getX();
										int lDy = lCase.getY()-lPositionCase.getY();
										if( lDx != 0 )
												((EntityMobil)cEntity).cSpeedX = 0;
										if( lDy != 0 )
												((EntityMobil)cEntity).cSpeedY = 0;
										
										if( ((EntityMobil)cEntity).cSpeedX != 0 || ((EntityMobil)cEntity).cSpeedY != 0 ){
												

											  pX = cEntity.getMX() + ((EntityMobil)cEntity).cSpeedX*pTimeDiff;
												pY = cEntity.getMY() + ((EntityMobil)cEntity).cSpeedY*pTimeDiff;				
												
												if( World.GetPathCarte().moveEntity( cEntity, pX, pY ) == true ){														
														cEntity.moveTo( pX, pY );								
														return true;  // ON CONTINUIERA AU PROCHAIN TOUR
												}
												if( refindPath() == false ){
														return false;   // On arrete les frais !
												}
												
												// POUR LE MOMENT ON VA CHERCHER UN AUTRE CHEMIN
												if( refindPath() == false ){
														System.out.println( "On arrete les frais 2 ");
														return false;   // On arrete les frais !
												}										
										}
								}	else {
										
										// C'est un mobil !
										
										if( ((EntityMobil)lCurrentEntity).cSpeedX != 0 ||  ((EntityMobil)lCurrentEntity).cSpeedY != 0 ){
												
												// Il bouge, on va attendre, avec un peu de chance il va partir 
												// (METTRE UN temps maximum d'attente)
												
												((EntityMobil)cEntity).cSpeedX = 0;
												((EntityMobil)cEntity).cSpeedY = 0;
												
												
												System.out.println( "Attente mobil ");												
												cWaiting = 1; // en seconde
												
												return true;
										}
										
										
										// il ne bouge pas !

										ArrayList<PathCase> lNewPath = new 	ArrayList<PathCase>();				
										if( PathFinder.GetPath( World.sPathCarte, cEntity, cPointDest, lNewPath ) ){
												// Il y a un autre chemin !!!!
												
												/// A VOIR si c'est un ami On demande l autre entity de se pousser 
												//		if( lCurrentEntity.isFriend() && lNewPath.size() > 2 &&  lNewPath.size() >  cPath.size()*1.10 ){
												//
												//	if( lCurrentEntity.askShootYou( cEntity ) ){
												//
												//
												
												((EntityMobil)cEntity).cPath = cPath = lNewPath;																
										}
										else {
												// On attend !
												//System.out.println( "Attente mobil 2");
												//On laisse tomber !
												return false;
										}														
								}										
						}
				}				
								
				return true;
		}
		//--------------------------------------
		String getStringInfo(){
				return " Moving to " + cPointDest;
		}
}
//***********************************
