package org.phypo.PPg.PPgTestSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;



//*************************************************

public class SwarmBoidMissile extends SwarmBoid {

		

		//------------------------------------------------
		public SwarmBoidMissile( double pX, double pY, Color pColor ) {
				super(  pX,  pY, pColor );

		}

		//------------------------------------------------*
    public void worldCallAct(double pTimeDelta) 
    {  
				pTimeDelta  *= 30;
				
				// cSpeed.add(cAcceleration, pTimeDelta);

				//   limitSpeed();

				////        cLocation.add( cSpeed, pTimeDelta );        
				cLocation.x += cSpeed.x;
				cLocation.y += cSpeed.y;

						
				// Respect des limites de l'ecran 
				if( cLocation.x < 0 )
						cLocation.x =0;
				
				if( cLocation.y < 0 )
						cLocation.y =0;
				
				if( cLocation.x >1280  )
						cLocation.x  = 1280;
				
				if( cLocation.y > 1024 )
						 cLocation.y = 1024;
									 					

				setRotation( cSpeed.getDirection() );
				setLocation(cLocation);   
    }
		 
		//------------------------------------------------
		public boolean worldCallAcceptCollision( ActorLocation pActor ) { 
				
				return true;
		}

		//------------------------------------------------
		
		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

				if( pActor.getActorType() == 0 && getActorType() == 666) {
						//						PPgExplosion lExplo = new PPgExplosion( getFaction(), cLocation, 32, 1.0f, 0.7f, 0.1f, 0.3 );
						PPgExplosion lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, 32, 1.0f, 0.7f, 0.1f, 0.3f, 0.8, true );
						World.Get().addActor(lExplo);
						setDeleted();
				}						
		}
};
