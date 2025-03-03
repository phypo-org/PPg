package com.phipo.GLib;


import java.io.*;
import java.util.*;
import java.lang.*;


//*************************************************
public class DecorCase {
		
		// Tableau des entite presente dans la case !


		int cX, cY;
		double cMx;
		double cMy;

		int getX() { return cX;}
		int getY() { return cY;}

		
		double getMiddleX() { return cX+0.5;}
		double getMiddleY() { return cY+0.5;}
		
		double getMiddleMeterX() { return cMx; }
		double getMiddleMeterY() { return cMy; }

		float cZ = 0;               // elevation !		
		PrototypeCase cPrototypeCase;

		public PrototypeCase getProto() { return cPrototypeCase;}


		//------------------
		public DecorCase( int pX, int pY, PrototypeCase pProtoCase ) {
				
				cX = pX;
				cY = pY;

				cMx = (pX+0.5) * DecorCarte.sSizeCase ;
				cMy = (pY+0.5) * DecorCarte.sSizeCase ;

				cZ = 0;

				cPrototypeCase = pProtoCase;
		}		
};
//*************************************************
