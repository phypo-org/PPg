package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgSFX.*;



//*************************************************

public class SwarmBoid extends ActorMobil {

		
		//		public  Point2D.Double cPosition;

		public  double cNormSpeed=1;
		
		public Color cColor;

		protected Swarm cMySwarm = null;

		//------------------------------------------------
		public SwarmBoid( double pX, double pY, Color pColor ) {
				super(  pX,  pY, EnumFaction.Neutral );

				cColor =  pColor ;


				//				System.out.print( "Boid x:" + x + " y:" + y);
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

				/*		
				// Respect des limites de l'ecran 
				if( cLocation.x < 0 )
						cLocation.x =0;
				
				if( cLocation.y < 0 )
						cLocation.y =0;
				
				if( cLocation.x >1280  )
						cLocation.x  = 1280;
				
				if( cLocation.y > 1024 )
						 cLocation.y = 1024;
				*/				

				setRotation( cSpeed.getDirection() );
				setLocation(cLocation);   
    }

 		//-------------------------- 
		public void draw2( Graphics2D pG){

				//				System.out.println( "draw x:" +x +" y:" + y );

				pG.setColor( cColor );

				pG.drawLine( (int)cLocation.x, (int)cLocation.y, (int)(cLocation.x+cSpeed.x), (int)(cLocation.y+cSpeed.y) );	
					
				int lSz = 1;
						pG.drawLine( (int)cLocation.x+lSz, (int)cLocation.y+lSz, (int)(cLocation.x+cSpeed.x+lSz), (int)(cLocation.y+cSpeed.y)-lSz );				
			 			pG.drawLine( (int)cLocation.x-lSz, (int)cLocation.y-lSz, (int)(cLocation.x+cSpeed.x-lSz), (int)(cLocation.y+cSpeed.y)+lSz );	
							
		}				 
		//-------------------------- 

		public void worldCallDraw( Graphics2D pG ){
		
		Point2D.Double lPos      = getLocation();
		PPgVector lSpeed         = getSpeed();
				
		int lVx[]=new int[5];
		int lVy[]=new int[5];

		

		int lSize =(int)getBoundingSphere();

		// La pointe
		lVx[0] = lSize;
		lVy[0] = 0;


		// partie basse
		lVx[1] = -lSize;
		lVy[1] = -lSize;

		// la base
		lVx[2] = -lSize/2;
		lVy[2] = 0;

		// partie haute
		lVx[3] = -lSize;
		lVy[3] = lSize;

		lVx[4] = lVx[0];
		lVy[4] = lVy[0];

		lSpeed.transformDirection( lVx, lVy, 5 );

		
		for( int i=0; i< 5; i++ ) {
				lVx[i] += lPos.x;
				lVy[i] += lPos.y;
		}
		

		pG.setColor( cColor);
		pG.fillPolygon( lVx, lVy, 4 );
		
		
		pG.setColor( Color.black );
		pG.drawPolyline(  lVx, lVy, 5 );
	
		
		pG.setColor( Color.blue );
		
		
		pG.drawLine( (int)lPos.getX(), (int)lPos.getY(), 
								 (int)(lPos.getX()+lSpeed.getX()/5), 
								 (int)(lPos.getY()+lSpeed.getY()/5) );	

		}

		//------------------------------------------------
		public boolean worldCallAcceptCollision( ActorLocation pActor ) { 
				
				return true;
		}

};
