package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;

 
//*************************************************
public class Transform3d implements TransformBase{
		
		public Float3 cPos;
		public Float3 cRot;
		public Float3 cScale;

		public Float3 getRotate()  { return cRot;}
		public Float3 getTranslat(){ return cPos;}
		public Float3 getScale()   { return cScale;}

		public Transform3d(){;}

		public Transform3d( Float3 pPos, Float3 pRot, Float3 pScale ){
				cPos   = pPos;
				cRot   = pRot;
				cScale = pScale;
		}
		//------------------------------------------------
		public void rotate( Float3 pRot) { cRot = pRot;}
		public void rotateX( float pVal){ if( cRot == null) cRot = new Float3(); cRot.get()[0]=pVal;}
		public void rotateY( float pVal){ if( cRot == null) cRot = new Float3();cRot.get()[1]=pVal;}
		public void rotateZ( float pVal){ if( cRot == null) cRot = new Float3();cRot.get()[2]=pVal;}


		public void move( Float3 pPos ) { cPos = pPos; }
		public void moveX( float pVal){ if( cPos == null) cPos = new Float3(); cPos.get()[0]=pVal;}
		public void moveY( float pVal){ if( cPos == null) cPos = new Float3();cPos.get()[1]=pVal;}
		public void moveZ( float pVal){ if( cPos == null) cPos = new Float3();cPos.get()[2]=pVal;}

		public void scale( Float3 pScale ) { cScale = pScale; }
		public void scaleX( float pVal){ if( cScale == null) cScale = new Float3(); cScale.get()[0]=pVal;}
		public void scaleY( float pVal){ if( cScale == null) cScale = new Float3();cScale.get()[1]=pVal;}
		public void scaleZ( float pVal){ if( cScale == null) cScale = new Float3();cScale.get()[2]=pVal;}

		//------------------------------------------------
		public void renderGL( GL2 pGl){

				// ATTENTION A L ORDRE DES OPERATIONS

				if( cPos  != null ){
						//						System.out.println( "Transform3d.renderGL glTranslate" );
						cPos.glTranslate(pGl);
				}

				if( cRot != null ){
						//						System.out.println( "Transform3d.renderGL glRotate" );
						cRot.glRotate(pGl); // A VERIFIER !!!!
				}
				
				if( cScale != null ){
						//						System.out.println( "Transform3d.renderGL cScale" );
						cScale.glScaled(pGl);
				}
		}

}
//*************************************************
