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

public class PPgSimpleExplosion extends ActorLocation {


		double cRadius;
		double cCurrentRadius;
		double cInc;

		Color  cCurrentColor;
		float R,G,B,A;

		double cDuration;

		public PPgSimpleExplosion( EnumFaction pFaction, Point2D.Double pLocation, int pSize, float pR, float pG, float pB,  float pA, double pDuration ) {

				super( pLocation.x, pLocation.y, pFaction );

				cRadius = pSize;
				cCurrentRadius =0;

				cInc = 1.0/pDuration ;
				cDuration = pDuration;
				setTimeOfLife( pDuration );

				R = pR;
				G = pG;
				B = pB;
				A = pA;

				cCurrentColor = new Color( R, G, B, A);
		}


		//		public void worldCallInit() {;}
		public void worldCallAct( double pTimeDelta) {

				double lCurrent = cInc*pTimeDelta;
				cCurrentRadius += cRadius*lCurrent;
				A -= lCurrent;
				if( A < 0.0f )
						A = 0.0f;

						
				cCurrentColor = new Color( R, G, B, A);

		}
		//	public void worldCallClose() {;}	
		public void worldCallDraw( Graphics2D pG ) {
				
				pG.setColor(cCurrentColor);
				pG.fillOval( (int)(cLocation.x-cCurrentRadius*0.5), (int)(cLocation.y-cCurrentRadius*0.5), (int) (cCurrentRadius), (int)(cCurrentRadius));
		}

};
//*************************************************

