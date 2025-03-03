package org.phypo.PPgGame3d;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;


//*************************************************
public class ModelQuadric extends ModelBase {


		public enum Primitiv{ 
				CYLINDER, 
						CONE,
						DISK,
						PARTIAL_DISK,
						SPHERE };

		public Primitiv cPrimTyp;
		GLUquadric  cQuadric;

		public GLUquadric getQuadric() { return cQuadric; }
		
		public void setOrientationInside( boolean pBool ){
				if( pBool )	Engine.sTheGlu.gluQuadricOrientation( cQuadric, GLU.GLU_INSIDE );
				else	Engine.sTheGlu.gluQuadricOrientation( cQuadric, GLU.GLU_OUTSIDE );
		}

		float cRadius;
		float cRadius2;
		float topRadius;
		float cHeight;
		int   cSlices;
		int   cStacks;
		int   cLoops;

		float cStartAngle;
		float cSweepAngle;
		//------------------------------------------------

		public ModelQuadric(){
		}
		//------------------------------------------------
		public void renderGL( GL2 pGl, Aspect3d pAspect ){

				//		System.out.println(  "--- ModelQuadric.renderGL " + cPrimTyp);


				if( cQuadric == null ){
						cQuadric =  Engine.sTheGlu.gluNewQuadric();
						Engine.sTheGlu.gluQuadricOrientation( cQuadric, GLU.GLU_OUTSIDE );
			}
		Engine.sTheGlu.gluQuadricNormals( cQuadric, GLU.GLU_SMOOTH );

		boolean lUseTexture = false;

		if( pAspect != null ){

				switch( pAspect.getDrawStyle() ){

				case FILL:   Engine.sTheGlu.gluQuadricDrawStyle( cQuadric,  GLU.GLU_FILL ); 
						if( pAspect.getTexture() != null )
								{
										lUseTexture =true;

										//pGl.glEnable(GL2.GL_TEXTURE_2D);
										pAspect.getTexture().enable(pGl);
										pAspect.getTexture().bind(pGl);
										Engine.sTheGlu.gluQuadricTexture( cQuadric, true);
								}
						break;
				case LINE:   Engine.sTheGlu.gluQuadricDrawStyle( cQuadric, GLU.GLU_LINE ); break;
				case SILHOUETTE: Engine.sTheGlu.gluQuadricDrawStyle( cQuadric, GLU.GLU_SILHOUETTE ); break;
				case POINT:  Engine.sTheGlu.gluQuadricDrawStyle( cQuadric, GLU.GLU_POINT ); break;

				}
		}
		else  {
				Engine.sTheGlu.gluQuadricDrawStyle( cQuadric, GLU.GLU_LINE );	
		}

	
				switch( cPrimTyp ){

				case CYLINDER:
						Engine.sTheGlu.gluCylinder( cQuadric, cRadius, cRadius2, cHeight, cSlices, cStacks);
						break;

				case CONE :
						Engine.sTheGlu.gluCylinder( cQuadric, cRadius, 0.0f, cHeight, cSlices, cStacks);
						break;

				case DISK: 
						Engine.sTheGlu.gluDisk( cQuadric,  cRadius2, cRadius, cSlices, cLoops );
						break;

				case PARTIAL_DISK:
						Engine.sTheGlu.gluPartialDisk( cQuadric, cRadius2, cRadius, cSlices, cLoops, cStartAngle, cSweepAngle );
						break;

				case SPHERE:
							Engine.sTheGlu.gluSphere( cQuadric,  cRadius, cSlices, cStacks );
							break;
				}
 

				if( lUseTexture ) {
						pAspect.getTexture().disable(pGl);
						//		pGl.glDisable(GL2.GL_TEXTURE_2D);
				}
		}
		//------------------------------------------------
		
		public void setCylinder( float baseRadius,
												float topRadius,
												float height,
												int   slices,
												int   stacks){

	
				cPrimTyp = Primitiv.CYLINDER;
				
				cRadius  = baseRadius;
				cRadius2 = topRadius;
				cHeight  = height;
				cSlices  = slices;
				cStacks  = stacks;
		}
		//------------------------------------------------
		static public ModelQuadric GetCylinder( float baseRadius,
												float topRadius,
												float height,
												int   slices,
												int   stacks){
				ModelQuadric lModel = new ModelQuadric();
				lModel.setCylinder( baseRadius, topRadius, height, slices, stacks );
				return lModel;
		}
		//------------------------------------------------
		
		public void setCone( float baseRadius,
												float height,
												int   slices,
												int   stacks){


				cPrimTyp = Primitiv.CONE;
				
				cRadius  = baseRadius;
				cHeight  = height;
				cSlices  = slices;
				cStacks  = stacks;
		}
		//------------------------------------------------
		static public ModelQuadric GetCone( float baseRadius,
																				float height,
																				int   slices,
																				int   stacks) {

				ModelQuadric lModel = new ModelQuadric();
				lModel.setCone( baseRadius, height, slices, stacks );
				return lModel;
		}
		//------------------------------------------------


		public void setDisk(	float innerRadius,
										float outerRadius,
										int slices,
										int loops ){

		
				cPrimTyp = Primitiv.DISK;
				
				cRadius  = outerRadius;
				cRadius2 = innerRadius;
				
				cSlices = slices;
				cLoops   = loops;
		}
		//------------------------------------------------
		static public ModelQuadric GetDisk( 	float innerRadius,
																					float outerRadius,
																					int slices,
																					int loops) {

				ModelQuadric lModel = new ModelQuadric();
				lModel.setDisk( innerRadius, outerRadius, slices, loops );
				return lModel;
		}

		//------------------------------------------------

		public void setPartialDisk(	float innerRadius,
														float outerRadius,
														int slices,
														int loops,
														float startAngle,
														float sweepAngle){

		
				cPrimTyp = Primitiv.PARTIAL_DISK;

				cRadius  = outerRadius;
				cRadius2 = innerRadius;
				
				cSlices = slices;
				cLoops  = loops;

				cStartAngle = startAngle;
				cSweepAngle = sweepAngle;

		}
		//------------------------------------------------

		public void setSphere( float radius,
											int slices,
											int stacks) {
				
		
				cPrimTyp = Primitiv.SPHERE;
				
				cRadius  = radius;
				cSlices  = slices;
				cStacks  = stacks;
		}
		//------------------------------------------------
		static public ModelQuadric GetSphere( float radius,
																			 int slices,
																			 int stacks) {

				ModelQuadric lModel = new ModelQuadric();
				lModel.setSphere( radius,  slices, stacks );
				return lModel;
		}
}
//*************************************************
