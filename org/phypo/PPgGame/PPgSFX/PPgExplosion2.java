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

public class PPgExplosion2 extends ActorLocation {


		double cRadius;
		double cCurrentRadius;
		double cInc;

		Color  cCurrentColor;
		float R,G,B,A;
		float cdR,cdG,cdB,cdA;

		double cDuration;
		double cHalfLife;

		public PPgExplosion2( EnumFaction pFaction, Point2D.Double pLocation, int pSize, 
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

				cdR = (pR-pEndR)/(float)cDuration;
				cdG = (pG-pEndG)/(float)cDuration;
				cdB = (pB-pEndB)/(float)cDuration;
				cdA = (pA-pEndA)/(float)cDuration;

				cCurrentColor = new Color( R, G, B, A);
		}


		//		public void worldCallInit() {;}
		public void worldCallAct( double pTimeDelta) {

				double lCurrent = cInc*pTimeDelta;
				if( lCurrent > cHalfLife )
						cCurrentRadius -= cRadius*lCurrent;
				else
						cCurrentRadius += cRadius*lCurrent;

				R += cdR;
				G += cdG;
				B += cdB;
				A += cdA;

						
				cCurrentColor = new Color( R, G, B, A);

		}
		//	public void worldCallClose() {;}	
		public void worldCallDraw( Graphics2D pG ) {
				
				pG.setColor(cCurrentColor);
				pG.fillOval( (int)(cLocation.x-cCurrentRadius*0.5), (int)(cLocation.y-cCurrentRadius*0.5), (int) (cCurrentRadius), (int)(cCurrentRadius));
		}

};
//*************************************************

