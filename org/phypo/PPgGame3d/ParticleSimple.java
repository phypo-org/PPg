
package org.phypo.PPgGame3d;

import java.lang.Math;

import java.util.*;



import org.phypo.PPg.PPgMath.*;
 

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.*;


//*************************************************
class ParticleFactoryFire extends ParticleEngine {

		Float3 cWind;

		public ParticleFactoryFire( Float3 pWind, float pUnit,  int pMaxNbParticle, int pNbCreateAtOnce ){
				//				super(ParticleInterface.EnumParticleForm.POINT, pMaxNbParticle, pNbCreateAtOnce, false );
				super( pMaxNbParticle, pNbCreateAtOnce, false );

				cUnit = pUnit;

				cWind = pWind;

				ModelQuadric lQuad = new ModelQuadric();
				lQuad.setSphere( cUnit, 6, 6);

				cSharedModel =  new CompilObj( lQuad );
		}
				
				
				//------------------------------------------------
		public ParticleInterface  createParticle( PPgRandom pRand, int lNum){
				
				
				PPgRandom lRand =	World3d.sGlobalRandom;
				
				ParticleBase lPart = new ParticleBase( new Float3( lRand.nextFloat( cUnit*10f),
																													 lRand.nextFloat( cUnit*1f),
																													 lRand.nextFloat( cUnit*10f)
																													 ));
				
				
				lPart.cSpeed =  new Float3( lRand.nextFloat(cUnit), 
																		100f*cUnit+lRand.nextFloat(cUnit*40f),
																		lRand.nextFloat(cUnit));

				if( cWind != null )
						lPart.cSpeed.add( cWind );
				
				//			lPart.cScale = new Float3( f*cUnit+lRand.nextFloatPositif(3*cUnit), 0f, 0f );
				lPart.cScale =  new Float3( 3.0f, 
																		6+lRand.nextFloat(1),
																		3.0f);
				
				lPart.cInflation = Float3.GetRandom( lRand, -3f );
				lPart.cSpin = Float3.GetRandom( lRand, 700 );

				lPart.cColor = new Color4( 0.75f+lRand.nextFloatPositif(0.25f),
																	 0.55f+lRand.nextFloatPositif(0.3f),
																	 lRand.nextFloatPositif(0.3f), 
																	 0.1f+lRand.nextFloatPositif(0.2f));
				
				lPart.cColorFade =  new Color4( -0.5f, -1f, -0.2f, -0.25f);
				lPart.setTimeOfLife( 0.5 + lRand.nextDoublePositif(1.5) );

		/*
		//			lPart.setAcceleration( new Float3( lRand.nextDouble(0.3f*cUnit),
		//																				 0.6f*cUnit+lRand.nextFloat(0.1f*cUnit),
				//																				 lRand.nextDouble(0.3f*cUnit)));
		
				//		lPart.setSize( 7f*cUnit+lRand.nextFloat(3*cUnit), -2f*cUnit, -0.5f*cUnit );
				*/

				return lPart;
		}

};

//*************************************************
class ParticleFactorySmoke extends ParticleEngine {

		Float3 cWind;


		public ParticleFactorySmoke(  Float3 pWind, float pUnit,  int pMaxNbParticle, int pNbCreateAtOnce ){
				super( pMaxNbParticle, pNbCreateAtOnce, false );

				cUnit = pUnit;

				ModelQuadric lQuad = new ModelQuadric();
				lQuad.setSphere( cUnit, 6, 6);
				cWind = pWind;

				cSharedModel =  new CompilObj( lQuad );
		}
				
				
				//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){

				PPgRandom lRand =	World3d.sGlobalRandom;

				
				ParticleBase lPart = new ParticleBase( new Float3( lRand.nextFloat( cUnit*10f),
																													 lRand.nextFloat( cUnit*10f),
																													 lRand.nextFloat( cUnit*10f)
																													 ));

				lPart.cSpeed =  new Float3( lRand.nextFloat(4*cUnit), 
																		90f*cUnit+lRand.nextFloat(cUnit*40f),
																		lRand.nextFloat(4*cUnit));
				if( cWind != null )
						lPart.cSpeed.add( cWind );
		
		
		
		lPart.cAcceleration = new Float3( pRand.nextFloat(0.5f*cUnit), -pRand.nextFloat(0.5f*cUnit), pRand.nextFloat(0.5f*cUnit));

		lPart.cScale =  new Float3( 1.0f, 
																0.3f+lRand.nextFloat(0.3f),
																1.0f);
		lPart.cInflation = Float3.GetRandom( lRand, 4f );
		lPart.cSpin = Float3.GetRandom( lRand, 360f );

		/*
		lPart.cColor = new Color4( 0.2f+pRand.nextFloatPositif(0.03f),
															 0.2f+pRand.nextFloatPositif(0.3f),
															 0.1f+pRand.nextFloatPositif(0.3f), 
															 0.4f-pRand.nextFloatPositif(0.005f) );
		lPart.cColorFade = new Color4( 0.05f, 0.05f, 0.05f, 0.005f );
		*/

		lPart.cColor = new Color4( 0.2f+pRand.nextFloatPositif(0.2f),
															 0.2f+pRand.nextFloatPositif(0.2f),
															 0.2f+pRand.nextFloatPositif(0.2f), 
															 0.01f-pRand.nextFloatPositif(0.01f) );

		lPart.cColorFade = new Color4( 0.0f, 0.0f, 0.0f, 0.005f );
		lPart.setTimeOfLife( 0.5 + pRand.nextDoublePositif(1.5) );

		
				return lPart;
		}

};
/*
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
*/


//*************************************************
public class ParticleSimple{


		final static public ParticleEngine CreateParticleFactoryFire( Float3 pWind, float pUnit,  int pMaxNbParticle, int pNbCreateAtOnce){
				return new ParticleFactoryFire( pWind, pUnit, pMaxNbParticle, pNbCreateAtOnce );						
		}
		final static public ParticleEngine CreateParticleFactorySmoke(  Float3 pWind,  float pUnit, int pMaxNbParticle, int pNbCreateAtOnce ){
				return new ParticleFactorySmoke( pWind,  pUnit,  pMaxNbParticle, pNbCreateAtOnce );						
		}
		/*
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

		*/
};

						
