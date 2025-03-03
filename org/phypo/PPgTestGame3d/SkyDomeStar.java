package org.phypo.PPgTestGame3d;


import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.nativewindow.util.Point;

import org.phypo.PPgGame3d.*;
import org.phypo.PPg.PPgMath.EcoMath;



//*************************************************
class ModelSkyDomeStar extends ModelBase {


		int cNbStar;
		Float3 [] cStars;
		
		//-----------------------------------------
		public  ModelSkyDomeStar( int pNb ) {

				cNbStar = pNb;

				if( cNbStar >10000 )
						cNbStar = 10000;
				
				
				cStars = new Float3[ cNbStar ];
				
				for( int i=0; i< cNbStar; i++) {
						
						float cAngle = World3d.sGlobalRandom.nextFloatPositif( (float)(Math.PI*2) );
						cStars[i] = new Float3( Math.cos(cAngle), 
																		World3d.sGlobalRandom.nextFloat( 1 ),
																		Math.sin(cAngle));
			}								
		}		
		//------------------------------------------------
		@Override
		public void renderGL( GL2 pGl, Aspect3d pAspect ) {


				pGl.glDisable(GL2.GL_LIGHTING);
				

				pGl.glBegin( GL2.GL_POINTS );
				Color4.White.glColor( pGl );

				pGl.glPointSize(1);

			for( Float3 lStar : cStars){
						lStar.glVertex( pGl);		
				}	

				pGl.glEnd();

				pGl.glEnable(GL2.GL_LIGHTING);
		}

}
//*************************************************
public class SkyDomeStar extends ActorMobil {

		
		public SkyDomeStar( int pNb ) {

				super( EnumFaction.Neutral );

				ModelBase lModel = new CompilObj( new CompilObj( new ModelSkyDomeStar( pNb )));

				Node3d lNode = new Node3d( new Leaf3d( null, lModel, null, null ));
				setNode( lNode);
				
				setSpin( new Float3( 0, 20, 0 ));

				setLocation( new Float3( 0, 0 , 50 ));

				//  float lSz = 1000;
				setScale( new Float3( 100, 100, 100 ));
		}
}
//*************************************************
