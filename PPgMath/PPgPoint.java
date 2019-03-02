package org.phypo.PPg.PPgMath;


import java.awt.geom.Point2D;



//*************************************************

public class PPgPoint extends Point2D.Double {

		public PPgPoint( double pX, double pY) {
				super( pX, pY );
		}

		public PPgPoint( Point2D.Double pPoint ) {
				super( pPoint.x, pPoint.y );
		}
};
//*************************************************
