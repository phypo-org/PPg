package com.phipo.PPg.PPgG3d;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 
//*************************************************
public class Transform3d implements TransformBase{
		
		public Float3 cPos;
		public Float3 cRot;
		public Float3 cScale;
		
		public Transform3d( Float3 pPos, Float3 pRot, Float3 pScale ){
				cPos = pPos;
				cRot = pRot;
				cScale = pScale;
		}
		//------------------------------------------------
		public void rotate( Float3 pRot) { cRot = pRot;}
		public void rotateX( float pVal){ cRot.get()[0]=pVal;}
		public void rotateY( float pVal){ cRot.get()[1]=pVal;}
		public void rotateZ( float pVal){ cRot.get()[2]=pVal;}


		public void move( Float3 pPos ) { cPos = pPos; }
		public void moveX( float pVal){ cPos.get()[0]=pVal;}
		public void moveY( float pVal){ cPos.get()[1]=pVal;}
		public void moveZ( float pVal){ cPos.get()[2]=pVal;}

		public void scale( Float3 pScale ) { cScale = pScale; }
		public void scaleX( float pVal){ cScale.get()[0]=pVal;}
		public void scaleY( float pVal){ cScale.get()[1]=pVal;}
		public void scaleZ( float pVal){ cScale.get()[2]=pVal;}

		//------------------------------------------------
		public void renderGL(){

				// ATTENTION A L ORDRE DES OPERATIONS

				if( cPos  != null ){
						//						System.out.println( "Transform3d.renderGL glTranslate" );
						cPos.glTranslate();
				}

				if( cRot != null ){
						//						System.out.println( "Transform3d.renderGL glRotate" );
						cRot.glRotate(); // A VERIFIER !!!!
				}
				
				if( cScale != null ){
						//						System.out.println( "Transform3d.renderGL cScale" );
						cScale.glScaled();
				}
		}

}
//*************************************************
