package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************
public class EntityMobil extends Entity {

		double cSpeedX = 0;
		double cSpeedY = 0;
		
		void setSpeed( double pSpeedX, double pSpeedY ){
				cSpeedX = pSpeedX; cSpeedY = pSpeedY;
		}

		// Pour le debug 
		ArrayList<PathCase> cPath = null ;
		ArrayList<PathCase> getPath() { return cPath;}


		//------------------------------------------
		public EntityMobil( Gamer pGamer, int pGroupId, PrototypeUnit pProto,
									 double pX, double pY  ){
				super( pGamer, pGroupId, pProto, pX, pY );
		}

		//------------------------------------------
		public void sendOrderTerrain( Point2D.Double pPoint, Entity.ModeAddMission pModeAdd ){
				System.out.println( "EntityMobil.sendOrderWalking to " + pPoint );
				
				if( pModeAdd == Entity.ModeAddMission.REMPLACE_ALL ) {
						
						ArrayList<PathCase> lPath = new ArrayList<PathCase>();
						if( PathFinder.GetPath( World.sPathCarte, this, pPoint, lPath ) == true )
								addMission( new ActionEntityMove( this, pPoint, lPath ), pModeAdd );
				}
				else
						addMission( new ActionEntityMove( this, pPoint, null ), pModeAdd );
		}
}
//***********************************
