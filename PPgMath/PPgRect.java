package org.phypo.PPg.PPgMath;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

//*************************************************

public class PPgRect extends Rectangle2D.Double {
		
		public PPgRect( double pX, double pY, double pW, double pH) {
				super( pX, pY, pW, pH );
		}
		
		public PPgRect( Point2D.Double pMinPoint, Point2D.Double pMaxPoint) {
				super( pMinPoint.x,
							 pMinPoint.y, 
							 pMaxPoint.x - pMinPoint.x, 
							 pMaxPoint.y - pMinPoint.y );
		}
};
//*************************************************
