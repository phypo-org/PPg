package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;


//***********************************
class ActionEntityAttack extends ActionEntity {

		Entity  cTargetEntity = null; 

		ActionEntityMove cCurrentMove = null;
		double           cTimeWait = 0;

		
		ActionEntityAttack( EntityMobil pEntity, Entity pTargetEntity){
				super( pEntity );
				
				cTargetEntity = pTargetEntity;
				
		}		
		//------------------------------------------------		
		public void finish(){

		}
		//------------------------------------------------		
		boolean exec( double pTimediff ){

				if( cArsenal == null )// Non arme !
						return false;

				if( World.ExistEntity( cTargetEntity ) == false )
						return false;  // la cible n'existe plus !!!
				

				/*
				
				// IL FAUDRAIT TESTER SI LA TARGET A BOUGER
				// SI C'EST LE CAS REFAIRE UN PATH FINING SINON
				// CE N'EST PAS LA PEINE !!! 
				// A VOIR !!!

				// Si un mouvement est en cours l'execute
				if( cCurrentMove != null ){
						if( cCurrentMove.exec( pTimediff )== false ){
								cCurrentMove = null;
						}
						else{
								return true;
						}
				}
				*/

				// faire un test de distance min et max 
				Point2D.Double lDest  = cTargetEntity.getPosition();
				Point2D.Double lLocal = cEntity.getPosition();
				double lDistance = lDest.distance( lLocal );

				
				// On enleve la taille des unites 
				lDistance -= (cTargetEntity.getPrototype().getRayon()+cEntity.getPrototype().getRayon());
				

				if( lDistance > cEntity.getWeaponRange() ){
						
						Point2D.Double lNewPos = new Point2D.Double();
						Orientation lOrient = Util.GetNewPos( cEntity.getPosition(),
														cTargetEntity.getPosition(), 
														cEntity.getWeaponRange()+cTargetEntity.getPrototype().getRayon()+cEntity.getPrototype().getRayon(),
														lNewPos );
						
						
						// d'abord se mettre en bonne position
						ArrayList<PathCase> lPath = new ArrayList<PathCase>();
						if( PathFinder.GetPath( World.sPathCarte, cEntity, lNewPos, lPath ) == true ){
								cCurrentMove = new ActionEntityMove( ((EntityMobil)cEntity), lNewPos, lPath );
								cCurrentMove.exec( pTimediff );
						}
				}
				else {	// On est a porte de la cible on attaque si on peut						
						cArsenal.attack( this, lOrient, lDistance, cTargetEntity );
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
