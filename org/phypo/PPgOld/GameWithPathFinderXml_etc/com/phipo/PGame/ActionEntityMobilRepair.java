package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;


//***********************************
class ActionEntityMobilRepair extends ActionEntity {

		Entity  cTargetEntity = null; 

		ActionEntityMove cCurrentMove = null;

		
		ActionEntityMobilRepair( EntityMobil pEntity, Entity pTargetEntity){
				super( pEntity );
				
				cTargetEntity = pTargetEntity;

		}		
		//------------------------------------------------		
		public void finish(){

				cEntity.setWorkingConstruct( false );
		}
		//------------------------------------------------		
		boolean exec( double pTimediff ){

				cEntity.setWorkingConstruct(true );

				System.out.println( "Repair->exec");

				Point2D.Double lDest = cTargetEntity.getPosition();
				Point2D.Double lLocal = cEntity.getPosition();
				
				// Verifier si les deux rectangle des 2 entites se recoupe
				// tenir compte enventuellement d'une distance en plus
				//  sinon deplacer l'entity de reparation
				// Calcul de la distance la plus petite entre les deux rectangle
				// si distance trop grande -> en
				// Calcul de la distance
				// si distance trop grande -> creer une action move et la mettre en first dans les deplacements
				
				// Il faudrait determiner la position optimale 
				// a'il y a lieu deplace,ent

				// Prevoir aussi une reparation en deplacement comme Total annhilation !!!
				
				if( cCurrentMove != null ){
						if( cCurrentMove.exec( pTimediff )== false ){
								System.out.println( "false");
								cCurrentMove = null;
						}
						else{
								System.out.println( "true");
								return true;
						}
				}
				
				// faire un test de distance min et max 

				double lDistance = lDest.distance( lLocal );

				if( lDistance > (cTargetEntity.getPrototype().getRayon() +cEntity.getPrototype().getRayon())*1.1
						||  lDistance < (cTargetEntity.getPrototype().getRayon() +cEntity.getPrototype().getRayon())) {
												
						//				System.out.println( "Repair->exec create move");

						Point2D.Double lNewPos = new Point2D.Double();
						Util.GetNewPos( cEntity.getPosition(),
														cTargetEntity.getPosition(),
														(cTargetEntity.getPrototype().getRayon() +cEntity.getPrototype().getRayon()), lNewPos );
						

						// d'abord se mettre en bonne position
						ArrayList<PathCase> lPath = new ArrayList<PathCase>();
						if( PathFinder.GetPath( World.sPathCarte, cEntity, lNewPos, lPath ) == true )
								cCurrentMove = new ActionEntityMove( ((EntityMobil)cEntity), lNewPos, lPath );
				}
				else {
						//						System.out.println( "Repair->exec frac");

						double lFrac = (cEntity.getPrototype().cWork/cTargetEntity.getPrototype().cCostWork)*pTimediff;

						cTargetEntity.cLife += cTargetEntity.getPrototype().cLife*lFrac;
						if( cTargetEntity.cLife >= cTargetEntity.getPrototype().cLife ){
								cTargetEntity.cLife = cTargetEntity.getPrototype().cLife;
								cTargetEntity.setInConstruct( false );
								cEntity.setWorkingConstruct( false );

								return false;  // Arret de la commande en cours 
						}
				}
				return true;
		}			

		//--------------------------------------
		String getStringInfo(){
				return " Repair  " + cTargetEntity.getMyId() 
						+ " : " + cTargetEntity.cLife;
		}
}

//***********************************
