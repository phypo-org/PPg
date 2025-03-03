package org.phypo.PPgGame.PPgG3d;




import org.lwjgl.opengl.*;

import org.lwjgl.util.glu.Quadric;
    



//*************************************************
public class ModelQuadric extends  ModelBase {


		public enum Primitiv{ 
				CYLINDER, 
						CONE,
						DISK,
						PARTIAL_DISK,
						SPHERE };

		public Primitiv cPrimTyp;
		Quadric  cQuadric;

		public Quadric getQuadric() { return cQuadric; }
		
		public void setOrientationInside( boolean pBool ){
				if( pBool ) cQuadric.setOrientation( GLU.GLU_INSIDE );
				else  cQuadric.setOrientation( GLU.GLU_OUTSIDE );
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
		public void renderGL( Aspect3d pAspect ){

				//		System.out.println(  "--- ModelQuadric.renderGL " + cPrimTyp);
		
		if( pAspect != null ){

				switch( pAspect.getDrawStyle() ){

				case FILL: cQuadric.setDrawStyle( GLU.GLU_FILL ); break;
				case LINE: cQuadric.setDrawStyle( GLU.GLU_LINE ); break;
				case SILHOUETTE: cQuadric.setDrawStyle( GLU.GLU_SILHOUETTE ); break;
				case POINT: cQuadric.setDrawStyle( GLU.GLU_POINT ); break;

				}
		}
		else  {
				cQuadric.setDrawStyle(  GLU.GLU_LINE );	

		}
		
				switch( cPrimTyp ){

				case CYLINDER:
						((Cylinder)cQuadric).draw( cRadius, cRadius2, cHeight, cSlices, cStacks);
						break;

				case CONE :
						((Cylinder)cQuadric).draw( cRadius, 0.0f, cHeight, cSlices, cStacks);
						break;
				case DISK: 
						((Disk)cQuadric).draw( cRadius2, cRadius, cSlices, cLoops );
						break;

				case PARTIAL_DISK:
						((PartialDisk)cQuadric).draw( cRadius2, cRadius, cSlices, cLoops, cStartAngle, cSweepAngle );
						break;

				case SPHERE:
							((Sphere)cQuadric).draw( cRadius, cSlices, cStacks );
									break;
				}
		}
		//------------------------------------------------
		
		public void setCylinder( float baseRadius,
												float topRadius,
												float height,
												int   slices,
												int   stacks){

				cQuadric = new Cylinder();
				cQuadric.setOrientation( GLU.GLU_OUTSIDE );
				cPrimTyp = Primitiv.CYLINDER;
				
				cRadius  = baseRadius;
				cRadius2 = topRadius;
				cHeight  = height;
				cSlices  = slices;
				cStacks  = stacks;
		}
	
		//------------------------------------------------


		public void setDisk(	float innerRadius,
										float outerRadius,
										int slices,
										int loops ){

				cQuadric = new Disk();
					cQuadric.setOrientation( GLU.GLU_OUTSIDE );
					cPrimTyp = Primitiv.DISK;

				cRadius  = outerRadius;
				cRadius2 = innerRadius;
				
				cSlices = slices;
				cLoops   = loops;
		}

		//------------------------------------------------

		public void setPartialDisk(	float innerRadius,
														float outerRadius,
														int slices,
														int loops,
														float startAngle,
														float sweepAngle){

				cQuadric = new PartialDisk();
				cQuadric.setOrientation( GLU.GLU_OUTSIDE );
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
				
				cQuadric = new Sphere();
				cQuadric.setOrientation( GLU.GLU_OUTSIDE );
				cPrimTyp = Primitiv.SPHERE;
				
				cRadius  = radius;
				cSlices  = slices;
				cStacks  = stacks;
		}
}
//*************************************************
