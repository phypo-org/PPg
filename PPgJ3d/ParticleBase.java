
package org.phypo.PPg.PPgJ3d;





import java.lang.Math;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
 



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.*;



//*************************************************

public class ParticleBase implements ParticleInterface {

    public  Float3 cLocation  = new  Float3(0);
		public  Float3 cRotation  = new  Float3(0);
		public  Float3 cScale     = new  Float3(1);           

		public Float3 cSpeed         = new Float3();
		public Float3 cAcceleration  = new  Float3();
		
    private float cMaxSpeed = 0; 
    
		public Float3 cSpin       = new  Float3();
		public Float3 cInflation  = new  Float3() ; 

		public Color4 cColor     = new Color4(1f);
		public Color4 cCurrentColor ;
		public Color4 cColorFade ;




		//---------- Deleted --------------
		boolean cIsDeleted;

		@Override
		final public boolean isDeleted()  { return cIsDeleted;}
		@Override
		final public void    setDeleted() { cIsDeleted = true;  }
		//---------- Deleted --------------


		
		//--------- TimeOfLife -------------
		double  cTimeOfLife = -1;
		double  cTimeToDie  = -1;


		@Override
		public void setTimeOfLife( double lTime) {	

				cTimeOfLife = lTime; 
				cTimeToDie = World3d.Get().getGameTime() + cTimeOfLife;
				
				//				System.out.println( "============ setTimeOfLife " + cTimeOfLife  +  "  " + cTimeToDie  );

		}

		@Override
		public boolean testTimeOfLife(double lTime) { 
				if(  cTimeToDie == -1 )
						return false ;

				if( lTime >= cTimeToDie )
						return true;

				return false ;				
		}
		//--------- TimeOfLife -------------




		//------------------------------------------------
		public ParticleBase( Float3 pLoc ) {
				cLocation.set( pLoc );
		}
		//------------------------------------------------

		@Override
		public void callFactoryDraw( ModelBase pSharedModel, 
																 Transform3d pSharedTransform, 
																 Aspect3d pSharedAspect, 
																 GL2 pGl){

				pGl.glPushMatrix();

				//			System.out.println( "========= callFactoryDraw "  + cLocation);

				
				pSharedTransform.cPos   = cLocation;
				pSharedTransform.cRot   = cRotation;
				pSharedTransform.cScale = cScale;
   						
				pSharedTransform.renderGL(pGl);

				pSharedAspect.cColorMaterial = cCurrentColor;
				pSharedAspect.cColorEmission = cCurrentColor;

				boolean lMustClean = pSharedAspect.renderGL( pGl );

				pSharedModel.renderGL( pGl, pSharedAspect );

				if( lMustClean )pSharedAspect.cleanRenderGL( pGl );

				//				Color4.Yellow.glMaterial( pGl);
					
				/*

						Float3 A = new Float3(cLocation);
						Float3 B = new Float3(cLocation);
						Float3 C = new Float3(cLocation);
						float cSize = 0.1f;
						B.sub( new Float3( cSize, -cSize*2, -cSize ));
						C.sub( new Float3( -cSize*2, cSize, cSize*3 ));
									 //						C.sub( cSize );
									 //						B.add( cSize );
									 */
				/*
				System.out.println( "========= callFactoryDraw TRIANGLE " + cLocation + " Size:" + cSize );
								System.out.println( "========= callFactoryDraw " + A );
								System.out.println( "========= callFactoryDraw " + B );
								System.out.println( "========= callFactoryDraw " + C );
				*/
								//	Primitiv3d.DrawTriangleNorm( pGl, A, B, C  );
								//								Primitiv3d.DrawTriangleStrip( pGl, A, B, C  );
				//			Primitiv3d.DrawX( pGl, A.x(), A.y(), A.z(), 3f );
				
				pGl.glPopMatrix();
		}															
		//------------------------------------------------
		@Override
		public void callFactoryAct( float pTimeDelta  ) {
				
				cSpeed.addDelta( cAcceleration, pTimeDelta);				
				cLocation.addDelta( cSpeed, pTimeDelta );

				cRotation.addDelta( cSpin, pTimeDelta );
				cScale.addDelta( cInflation,  pTimeDelta );

				if( cCurrentColor == null ){
						if( cColorFade != null )
								cCurrentColor = new Color4( cColor );
						else
								cCurrentColor = cColor;
				}
				else	if( cColorFade != null ){
						cCurrentColor.addDelta( cColorFade, pTimeDelta );
						cCurrentColor.limitVal( 0f, 1f );
				}
		}
		//------------------------------------------------
		@Override
		public void callFactoryClose(){
		}
};

//*************************************************
