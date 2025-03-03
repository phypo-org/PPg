package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;


//***********************************
class ActionEntityDeath extends ActionEntity {

		double cTime=0;

		ActionEntityDeath( Entity pEntity, double pTime ){
				super( pEntity );

				cTime = pTime;
		}		

		//------------------------------------------------
		boolean exec( double pTimediff ){


				cTime -= pTimediff;
				if( cTime <=0 ){

						World.sTheWorld.destroyEntityAsap( cEntity );

						return false;  // Arret de la commande en cours 
				}
				return true;
		}			
		//--------------------------------------
		String getStringInfo(){
				return " Death  " + cEntity.getMyId() 
						+ " : " + cEntity.cLife;
		}
}

//*************************************************
