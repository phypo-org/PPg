package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgGame.*;
 



//*************************************************
class ParticleFactoryFire extends ParticleEngine {


		public ParticleFactoryFire( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){
				super( pX, pY, pMaxNbParticle, pNbCreateAtOnce, false );
		}
				
				
				//------------------------------------------------
		public ParticleInterface  createParticle( PPgRandom pRand, int lNum){


				
				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 30 ), getLocation().y+pRand.nextInt( 10) );


				lPart.setSpeed( pRand.nextDouble(6), -50+pRand.nextDouble(10) );
				lPart.setAcceleration( pRand.nextDouble(0.3), 6+pRand.nextDouble(0.1) );

				lPart.setSize( 7+pRand.nextDouble(3), -2, -0.5 );

				lPart.setColor( 0.85f+pRand.nextFloatPositif(0.05f),
												0.55f+pRand.nextFloatPositif(0.2f),
												pRand.nextFloatPositif(0.3f), 
												0.3f+pRand.nextFloatPositif(0.2f) );

				lPart.setColorFade( 0.15f, 0.2f, 0.3f, 0.2f );
				lPart.setTimeOfLife( 1 + pRand.nextDoublePositif(4) );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.SQUARE  );
				
				return lPart;
		}

};

//*************************************************
class ParticleFactorySmoke extends ParticleEngine {


		public ParticleFactorySmoke( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){
				super( pX, pY, pMaxNbParticle, pNbCreateAtOnce, false );
		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){


				
				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 30 ), getLocation().y+pRand.nextInt( 10) );


				lPart.setSpeed( pRand.nextDouble(8), -40+pRand.nextDouble(10) );
				lPart.setAcceleration( pRand.nextDouble(0.4), 4+pRand.nextDouble(0.5) );

				lPart.setSize( 3+pRand.nextDouble(2), 2+pRand.nextDouble(1.5),  pRand.nextDoublePositif(0.5) );

				lPart.setColor( 0.2f+pRand.nextFloatPositif(0.03f),
												0.2f+pRand.nextFloatPositif(0.3f),
												0.1f+pRand.nextFloatPositif(0.3f), 
												0.4f-pRand.nextFloatPositif(0.06f) );

				lPart.setColorFade( 0.05f, 0.05f, 0.05f, -0.03f );
				lPart.setTimeOfLife( 4 + pRand.nextDoublePositif(4) );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.CIRCLE_FILL  );
				
				return lPart;
		}

};
//*************************************************
class ParticleFactoryFragment extends ParticleEngine {
		
		float cRed;
		float cGreen;
		float cBlue;
		double cSpeed = 10;
		double cLife =2;

		public ParticleFactoryFragment( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce, float pRed, float pGreen, float pBlue, double pSpeed, double pLife ){
				super( pX, pY, pMaxNbParticle, 0, true );

				cRed = pRed;
				cGreen = pGreen;
				cBlue = pBlue;
				
				cSpeed = pSpeed;
				cLife  = pLife;

		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){


				
				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 10 ), getLocation().y+pRand.nextInt( 10) );


				lPart.setSpeed( pRand.nextDouble(cSpeed), pRand.nextDouble(cSpeed) );
				lPart.setAcceleration( pRand.nextDouble(5), pRand.nextDouble(5) );

				lPart.setSize( 5+pRand.nextDouble(3), 0,  0 );

				lPart.setColor( cRed+pRand.nextFloat(0.10f),
												cGreen+pRand.nextFloat(0.1f),
												cBlue+pRand.nextFloat(0.1f), 
												1.0f );

				lPart.setColorFade( pRand.nextFloat(0.01f), pRand.nextFloat(0.01f), pRand.nextFloat(0.01f), 0f );
				lPart.setTimeOfLife( cLife + pRand.nextDouble(cLife*0.2) );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.SQUARE  );
				
				return lPart;
		}

};
//*************************************************
class ParticleFactoryExplosion extends ParticleEngine {
		
		double cLife;

		public ParticleFactoryExplosion( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce, double pLife ){
				super( pX, pY, pMaxNbParticle, 0, true );

				cLife =pLife;
		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){


				
				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 10 ), getLocation().y+pRand.nextInt( 10) );


				lPart.setSpeed( pRand.nextDouble(80), pRand.nextDouble(80) );
				lPart.setAcceleration( pRand.nextDouble(30), pRand.nextDouble(30) );

				lPart.setSize( 4+pRand.nextDouble(2), -1*pRand.nextDouble(1.5),  0 );

				lPart.setColor( 0.9f+pRand.nextFloatPositif(0.10f),
												0.8f+pRand.nextFloatPositif(0.2f),
												pRand.nextFloatPositif(0.3f), 
												0.7f-pRand.nextFloatPositif(0.2f) );

				lPart.setColorFade( -0.05f, -0.03f, 0.0f, -0.05f );
				lPart.setTimeOfLife( cLife + pRand.nextDoublePositif(cLife) );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.SQUARE  );
				
				return lPart;
		}

};
//*************************************************
class ParticleFactoryStar extends ParticleEngine {


		public ParticleFactoryStar( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){
				super( pX, pY, pMaxNbParticle, pNbCreateAtOnce, false );
		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){


				
				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 10 ), getLocation().y+pRand.nextInt( 10) );


				lPart.setSpeed( pRand.nextDouble(80), pRand.nextDouble(80) );
				lPart.setAcceleration( pRand.nextDouble(30), pRand.nextDouble(30) );

				lPart.setSize( 1+pRand.nextDouble(1), 1*pRand.nextDouble(1.0),  0.01 );

				lPart.setColor( 0.6f+pRand.nextFloatPositif(0.10f),
												0.6f+pRand.nextFloatPositif(0.10f),
												0.6f+pRand.nextFloatPositif(0.3f), 
												0.6f+pRand.nextFloatPositif(0.10f) );

				lPart.setColorFade( 0.01f, 0.01f, 0.0f, -0.01f );
				lPart.setTimeOfLife( 5 );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.CIRCLE_FILL  );
				
				return lPart;
		}

};
//*************************************************
class ParticleFactorySpark extends ParticleEngine {

		float cRed;
		float cGreen;
		float cBlue;
		double cSpeed = 10;
		double cLife =2;
		
		public ParticleFactorySpark( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce, float pRed, float pGreen, float pBlue, double pSpeed, double pLife ){
				super( pX, pY, pMaxNbParticle, pNbCreateAtOnce, false );

				cRed = pRed;
				cGreen = pGreen;
				cBlue = pBlue;
				
				cSpeed = pSpeed;
				cLife  = pLife;
		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){

				double lDiv = 1.0/(cLife*0.5);

				ParticleBase lPart = new ParticleBase( getLocation().x+pRand.nextInt( 1 ), getLocation().y+pRand.nextInt( 11) );


				lPart.setSpeed( pRand.nextDouble(cSpeed), pRand.nextDouble(cSpeed) );
				lPart.setAcceleration( -pRand.nextDoublePositif(cSpeed*lDiv), -pRand.nextDoublePositif(cSpeed*lDiv) );

				lPart.setSize( 2+ pRand.nextDouble(2), -pRand.nextDouble(0.5), 0 );

				lPart.setColor( cRed, cGreen, cBlue, 0.9f);
						
				lPart.setColorFade( 0.1f, 0.1f, 0.1f, -0.1f );
				lPart.setTimeOfLife( cLife + pRand.nextDoublePositif(cLife) );

				lPart.setParticleForm( ParticleInterface.EnumParticleForm.SQUARE  );
				
				return lPart;
		}

};

//*************************************************
public class ParticleSimple{


		final static public ParticleEngine CreateParticleFactoryFire( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){
				return new ParticleFactoryFire( pX, pY, pMaxNbParticle, pNbCreateAtOnce );						
		}
		final static public ParticleEngine CreateParticleFactorySmoke( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){
				return new ParticleFactorySmoke( pX, pY, pMaxNbParticle, pNbCreateAtOnce );						
		}
		final static public ParticleEngine CreateParticleFactoryExplosion( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce, double pLife ){		
				return new ParticleFactoryExplosion( pX, pY, pMaxNbParticle, pNbCreateAtOnce, pLife );							
		}		
		final static public ParticleEngine CreateParticleFactoryStar( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce ){		
				return new ParticleFactoryStar( pX, pY, pMaxNbParticle, pNbCreateAtOnce );	
		}
											
		final static public ParticleEngine CreateParticleFactorySpark( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce,  float pRed, float pGreen, float pBlue, double pSpeed, double pLife ){		

				return new ParticleFactorySpark( pX, pY, pMaxNbParticle, pNbCreateAtOnce,  pRed, pGreen, pBlue, pSpeed, pLife );												
		}
		final static public ParticleEngine CreateParticleFactoryFragment( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce,  float pRed, float pGreen, float pBlue, double pSpeed, double pLife ){		

				return new ParticleFactoryFragment( pX, pY, pMaxNbParticle, pNbCreateAtOnce,  pRed, pGreen, pBlue, pSpeed, pLife );												
		}
	
						
};