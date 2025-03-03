package org.phypo.TestSwarm2;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.TestSwarm.*;

//*************************************************


public class SwarmMouse extends Swarm {

		public	SwarmMouse( String pName, MyGamer pMyGamer, Color pColor, 
												int pNbBoid, int pX, int pY, int pRandom,
												
												double pTargetAttract,
												double pCohesionAttract,
												double pAlignAttract,
												double pSeparationRepuls,
												int pVitesseMax,
												int pDistanceMax,
												int pDistanceEvit,
												boolean pUseSqrt,
												double pMaxAttract ) {
				
				super( pName, pMyGamer,pColor, 
							 pNbBoid, pX,  pY,  pRandom,
							 
							 pTargetAttract,
							 pCohesionAttract,
							 pAlignAttract,
							 pSeparationRepuls,
							 pVitesseMax,
							 pDistanceMax,
							 pDistanceEvit,
							 pUseSqrt,
							 pMaxAttract);
		}


		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {

				//					System.out.println( "Swarm.act " + cName );

				Point2D.Double lMousePos = 	cMyGamer.getLastPoint();

				moveAll( pTimeDelta, (int)lMousePos.x, (int)(lMousePos.y ) );	
		}
};
//*************************************************
