package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;


//***********************************
class ActionEntityInternalRepair extends ActionEntity {

		Entity  cTargetEntity = null; 


		ActionEntityInternalRepair( Entity pEntity, Entity pTargetEntity){
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

				double lFrac = (cEntity.getPrototype().cWork/cTargetEntity.getPrototype().cCostWork)*pTimediff;
				
				//				System.out.println( ">>>>cTargetEntity.cLife" + cTargetEntity.cLife + " " + cTargetEntity.getPrototype().cLife + " " + cTargetEntity.getPrototype().cLife*lFrac);
				
				cTargetEntity.cLife += cTargetEntity.getPrototype().cLife*lFrac;
				
				if( cTargetEntity.cLife >= cTargetEntity.getPrototype().cLife ){
						cTargetEntity.cLife = cTargetEntity.getPrototype().cLife;
						cTargetEntity.setInConstruct( false );
						cEntity.setWorkingConstruct( false );
						

						return false;  // Arret de la commande en cours 
				}
				return true;
		}			
		//--------------------------------------
		String getStringInfo(){
				return " Repair  " + cTargetEntity.getMyId() 
						+ " : " + cTargetEntity.cLife;
		}
}

//*************************************************
