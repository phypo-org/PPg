package org.phypo.PPgGame.PPgG3d;




import org.lwjgl.opengl.GL11;



//*************************************************

public class Facet{

		Float3 cNorm ;
		Float3 cPoints[];


		public Float3   getNormal() { return cNorm; }
		public Float3[] getPoints() { return cPoints; }

		

		//------------------------------------------------
		public Facet(){
		}

		//------------------------------------------------
		public Facet( Facet pSrc ){

				setPoints( pSrc.cPoints, pSrc.cNorm );
		}

		//------------------------------------------------
		public Facet(Float3 lPoints[]){

				setPoints( lPoints );			
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC ){

				cPoints = new Float3 [ 3 ];
				cPoints[0] = new Float3( pA );
				cPoints[1] = new Float3( pB );
				cPoints[2] = new Float3( pC );
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC, Float3 pD ){

				cPoints = new Float3 [ 4 ];
				cPoints[0] = new Float3( pA );
				cPoints[1] = new Float3( pB );
				cPoints[2] = new Float3( pC );
				cPoints[3] = new Float3( pD );
		}
		//------------------------------------------------
		public Facet(Float3 lPoints[], Float3 pNorm){

				setPoints( lPoints, pNorm );
		}

		//------------------------------------------------
		protected void cpyPoints( Float3 pPoints[] ){

				for( int i = 0; i < pPoints.length; i++){
						cPoints[i] = new Float3( pPoints[i] );
				}
		}
		//------------------------------------------------
		public void setPoints( Float3 pPoints[] ){

				cPoints = new Float3 [ pPoints.length ];

				cpyPoints( pPoints );

				recomputeNormal();
		}
		//------------------------------------------------
		public void setPoints( Float3 pPoints[], Float3 pNorm ){

				cPoints = new Float3 [ pPoints.length ];

				cpyPoints( pPoints );

				if( pNorm == null )
						recomputeNormal();
				else
						cNorm = new Float3( pNorm );
		}
		//------------------------------------------------	
		// Lent !

		public void addPoint( Float3 pPoint ){
				
				Float3 lOldPoints[] = cPoints;
				cPoints = new Float3 [ lOldPoints.length+1 ];
				
				cpyPoints( lOldPoints );
				cPoints[ lOldPoints.length ] = new Float3( pPoint );

				recomputeNormal();
		}
		//------------------------------------------------	

		public void addPoints( Float3 pPoints[] ){
				
				Float3 lOldPoints[] = cPoints;

				cPoints = new Float3 [ lOldPoints.length+pPoints.length ];
				cpyPoints( lOldPoints );

				for( int i = 0; i < pPoints.length; i++){
						cPoints[lOldPoints.length+i] = new Float3( pPoints[i] );
				}
				recomputeNormal();
		}
		//------------------------------------------------

		public void recomputeNormal() {
				if( cPoints.length < 3 )
						return ;

				cNorm = new Float3();
				Calcul3d.Normal( cPoints[0], cPoints[1], cPoints[2], cNorm );
		}
		//------------------------------------------------
		public void renderGL( Aspect3d pAspect ) {

				//			System.out.println( "Facet renderGL " +  cPoints.length + "  " +  pAspect.cDrawStyle);

				switch( pAspect.cDrawStyle ) {
						
				case POINT: {
						GL11.glDisable( GL11.GL_LIGHTING );
						GL11.glBegin(GL11.GL_POINTS);
						for( int i=0; i< cPoints.length; i++ ){
								cPoints[i].glVertex( );		
						}
						GL11.glEnd();						
						GL11.glEnable(GL11.GL_LIGHTING);
				}break;
				case LINE :
				case SILHOUETTE : {
						GL11.glDisable( GL11.GL_LIGHTING );
						GL11.glBegin(GL11.GL_LINE_STRIP);
						for( int i=0; i< cPoints.length; i++ ){
								cPoints[i].glVertex( );		
						}
						cPoints[0].glVertex( );		
						GL11.glEnd();						
						GL11.glEnable(GL11.GL_LIGHTING);
				}break;
						
				case FILL: {
						
						//				System.out.println( "Facet renderGL FILL " +  cPoints.length + "  " +  pAspect.cDrawStyle);

					switch( cPoints.length ){
						case 0: break;
						case 1: {
								GL11.glDisable( GL11.GL_LIGHTING );
								GL11.glBegin( GL11.GL_POINTS );
								cPoints[0].glVertex();
								GL11.glEnd();
								GL11.glEnable(GL11.GL_LIGHTING);
						} break;
								
						case 2:{
								GL11.glDisable( GL11.GL_LIGHTING );
								GL11.glBegin(GL11.GL_LINE_STRIP);
								cPoints[0].glVertex( );
								cPoints[1].glVertex( );
								GL11.glEnd();
								GL11.glEnable(GL11.GL_LIGHTING);
						} break;
								
						case 3:{
								if( cNorm != null )
										cNorm.glNormal();
								
								GL11.glBegin( GL11.GL_TRIANGLES );      
								cPoints[0].glVertex( );
								cPoints[1].glVertex( );
								cPoints[2].glVertex( );
								GL11.glEnd();
						} break;
						
						case 4:{
								if( cNorm != null )
										cNorm.glNormal();
					System.out.println( "Facet renderGL FILL 4 " );
								
								GL11.glBegin( GL11.GL_QUADS );
								cPoints[0].glVertex( );
								cPoints[1].glVertex( );
								cPoints[2].glVertex( );
								cPoints[3].glVertex( );
								GL11.glEnd();
						} break;
								
						default:{
								if( cNorm != null )
										cNorm.glNormal();
								
								GL11.glBegin( GL11.GL_POLYGON );
								for( int i=0; i< cPoints.length; i++ ){
										cPoints[i].glVertex( );
										
										GL11.glEnd();
								} break;								
						}
						}
				}
				}
		}
}

//*************************************************
