package org.phypo.PPgGame.PPgSFX;





import java.awt.Graphics2D;


import java.io.IOException;
import javax.swing.SwingUtilities;
import java.awt.geom.Point2D;

import java.util.*;


import java.awt.*;



import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgMath.*;

//*************************************************

public class PPgExplosion extends ActorLocation {


		double cRadius;
		double cCurrentRadius;
		double cInc;

		Color  cCurrentColor;
		float R,G,B,A;
		float cdR,cdG,cdB,cdA;


		double cTime = 0;
		double cDuration;
		double cHalfLife;

		public PPgExplosion( EnumFaction pFaction, Point2D.Double pLocation, int pSize, 
													float pR, float pG, float pB, float pA, 
													double pDuration, boolean pImplose ) {
				super( pLocation.x, pLocation.y, pFaction );

				cRadius = pSize;
				cCurrentRadius =0;

				cDuration = pDuration*2;

				if( pImplose )
						cHalfLife = cDuration*0.5;
				else
						cHalfLife = cDuration;
				
				cInc = 1.0/cHalfLife;

						
				setTimeOfLife( pDuration );

				R = pR;
				G = pG;
				B = pB;
				A = pA;

				cdR=0;
				cdG=0;
				cdB=0;
				cdA = -A;

				cCurrentColor = new Color( R, G, B, A);
		}

		public PPgExplosion( EnumFaction pFaction, Point2D.Double pLocation, int pSize, 
													float pR, float pG, float pB, float pA, 
													float pEndR, float pEndG, float pEndB, float pEndA, 
													double pDuration, boolean pImplose ) {

				super( pLocation.x, pLocation.y, pFaction );

				cRadius = pSize;
				cCurrentRadius =0;

				cDuration = pDuration;

				if( pImplose )
						cHalfLife = cDuration*0.5;
				else
						cHalfLife = cDuration;
				
				cInc = 1.0/cHalfLife;

						
				setTimeOfLife( pDuration );

				R = pR;
				G = pG;
				B = pB;
				A = pA;

				cdR = pEndR-R;
				cdG = pEndG-G;
				cdB = pEndB-B;
				cdA = pEndA-A;

				cCurrentColor = new Color( R, G, B, A);
		}


		//		public void worldCallInit() {;}
		public void worldCallAct( double pTimeDelta) {

				double lCurrent = cInc*pTimeDelta;
				cTime += lCurrent;

				if( cTime > cHalfLife )
						cCurrentRadius -= cRadius*lCurrent;
				else
						cCurrentRadius += cRadius*lCurrent;

				R += cdR*lCurrent;
				G += cdG*lCurrent;
				B += cdB*lCurrent;
		 		A += cdA*lCurrent;

				if( R > 1 )
						R = 1;
				else	if( R < 0 )
						R =0;

				if( G > 1 )
						G = 1;
				else if( G < 0 )
						G =0;

				if( B > 1 )
						B = 1;
				else if( B < 0 )
						B = 0;

				if( A > 1 )
						A = 1;
				else if( A < 0 )
						A = 0;
						
				cCurrentColor = new Color( R, G, B, A);

		}
		//	public void worldCallClose() {;}	
		public void worldCallDraw( Graphics2D pG ) {
				
				pG.setColor(cCurrentColor);
				pG.fillOval( (int)(cLocation.x-cCurrentRadius*0.5), (int)(cLocation.y-cCurrentRadius*0.5), (int) (cCurrentRadius), (int)(cCurrentRadius));
		}

};
//*************************************************

