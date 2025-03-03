package org.phypo.PPgGame.PPgRts;


import java.io.*;
import java.util.*;
import java.lang.*;


//*************************************************
public class GroundSquare {
		
		// Tableau des entite presente dans la case !


		int cX, cY;  // position dans le tableau
		//		double cMx;  // position en metres
		//		double cMy;

		int getX() { return cX;}
		int getY() { return cY;}

		
		double getMiddleX() { return cX+0.5;}
		double getMiddleY() { return cY+0.5;}
		
		//		double getMiddleMeterX() { return cMx; }
		//	double getMiddleMeterY() { return cMy; }

		float cZ = 0;               // elevation !		
		ProtoGroundSquare cProto;   // le prototype de la case 

		public  ProtoGroundSquare getProto() { return cProto;}


		//------------------
		public GroundSquare( int pX, int pY, ProtoGroundSquare pProto ) {
				
				cX = pX;
				cY = pY;

				//			cMx = (pX+0.5) * GroundMap.sSizeSquare ;
				//				cMy = (pY+0.5) * GroundMap.sSizeSquare ;

				cZ = 0;

				cProto = pProto;
		}		
};
//*************************************************
