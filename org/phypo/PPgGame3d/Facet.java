package org.phypo.PPgGame3d;



import com.jogamp.opengl.*;


//*************************************************

public class Facet{

		Float3 cNorm ;
		Float3 cPoints[];
		Float2 cTexPoints[];


		public Float3   getNormal()     { return cNorm; }
		public Float3[] getPoints()     { return cPoints; }
		public Float2[] getTexPoints()  { return cTexPoints; }

		

		//------------------------------------------------
		public Facet(){
		}

		//------------------------------------------------
		public Facet( Facet pSrc ){

				setPoints( pSrc.cPoints, pSrc.cNorm );
				if( pSrc.cTexPoints != null )
						setTexPoints( pSrc.cTexPoints );

				
		}

		//------------------------------------------------
		public Facet(Float3 lPoints[]){

				setPoints( lPoints );			
		}
		//------------------------------------------------
		public Facet(Float3 lPoints[], Float2 lTexPoints[]){

				setPoints( lPoints );			
				//				System.out.println("*** Facet Tex []");

				setTexPoints( lTexPoints );
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC){

				cPoints = new Float3 [ 3 ];
				cPoints[0] = new Float3( pA );
				cPoints[1] = new Float3( pB );
				cPoints[2] = new Float3( pC );
				recomputeNormal();			
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC, 
								 Float2 pTA, Float2 pTB, Float2 pTC ){

				this( pA, pB, pC );

				//				System.out.println("*** Facet Tex");

				cTexPoints = new Float2 [ 3 ];
				cTexPoints[0] = new Float2( pTA );
				cTexPoints[1] = new Float2( pTB );
				cTexPoints[2] = new Float2( pTC );				
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC, Float3 pD ){

				cPoints = new Float3 [ 4 ];
				cPoints[0] = new Float3( pA );
				cPoints[1] = new Float3( pB );
				cPoints[2] = new Float3( pC );
				cPoints[3] = new Float3( pD );
				recomputeNormal();
		}
		//------------------------------------------------
		public Facet(Float3 pA, Float3 pB, Float3 pC, Float3 pD, Float3 pE ){

				cPoints = new Float3 [ 5 ];
				cPoints[0] = new Float3( pA );
				cPoints[1] = new Float3( pB );
				cPoints[2] = new Float3( pC );
				cPoints[3] = new Float3( pD );
				cPoints[4] = new Float3( pE );

				recomputeNormal();
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
		protected void cpyTexPoints( Float2 pTexPoints[] ){

				for( int i = 0; i < pTexPoints.length; i++){
						cTexPoints[i] = new Float2( pTexPoints[i] );
				}
		}
		//------------------------------------------------
		public void setPoints( Float3 pPoints[] ){

				cPoints = new Float3 [ pPoints.length ];

				cpyPoints( pPoints );

				recomputeNormal();
		}
		//------------------------------------------------
		public void setTexPoints( Float2 pTexPoints[] ){

				cTexPoints = new Float2 [ pTexPoints.length ];

				cpyTexPoints( pTexPoints );
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
		public void renderGL( GL2 pGl, Aspect3d pAspect ) {

				//			System.out.println( "Facet renderGL " +  cPoints.length + "  " +  pAspect.cDrawStyle);

				switch( pAspect.cDrawStyle ) {
						//=====================================
			
				case POINT: {
						pGl.glDisable( GL2.GL_LIGHTING );
						pGl.glBegin( GL2.GL_POINTS);
						for( int i=0; i< cPoints.length; i++ ){
								cPoints[i].glVertex( pGl );		
						}
						pGl.glEnd();						
						pGl.glEnable( GL2.GL_LIGHTING);
				}break;
						//=====================================
				case LINE :
				case SILHOUETTE : {
						pGl.glDisable( GL2.GL_LIGHTING );

						pGl.glBegin(GL2.GL_LINE_STRIP);
						for( int i=0; i< cPoints.length; i++ ){
								cPoints[i].glVertex( pGl );		
						}
						cPoints[0].glVertex( pGl );		
						pGl.glEnd();						
						pGl.glEnable(GL2.GL_LIGHTING);
				}break;
						
						//=====================================
						
				case FILL: {
						
						//						System.out.println( "Facet renderGL FILL " +  cPoints.length + "  " +  pAspect.cDrawStyle + " cTexPoints:" + cTexPoints );

					switch( cPoints.length ){
						case 0: break;
						case 1: {
								pGl.glDisable( GL2.GL_LIGHTING );
								pGl.glBegin( GL2.GL_POINTS );
								cPoints[0].glVertex( pGl );
								pGl.glEnd();
								pGl.glEnable(GL2.GL_LIGHTING);
						} break;
								
						case 2:{
								pGl.glDisable( GL2.GL_LIGHTING );
								pGl.glBegin(GL2.GL_LINE_STRIP);
								cPoints[0].glVertex( pGl );
								cPoints[1].glVertex( pGl );
								pGl.glEnd();
								pGl.glEnable(GL2.GL_LIGHTING);
						} break;
								
						case 3:{
								if( cNorm != null )
										cNorm.glNormal( pGl );
								

								pGl.glBegin( GL2.GL_TRIANGLES );
								if( cTexPoints != null ){

										//										System.out.println( "Facet.renderGL Tex");

										cPoints[0].glVertex( pGl );
										cTexPoints[0].glTexCoord( pGl );
										cPoints[1].glVertex( pGl );
										cTexPoints[1].glTexCoord( pGl );
										cPoints[2].glVertex( pGl );										
										cTexPoints[2].glTexCoord( pGl );
								} else {
										//											System.out.println( "Facet.renderGL");
											cPoints[0].glVertex( pGl );
											cPoints[1].glVertex( pGl );
											cPoints[2].glVertex( pGl );
								}
								pGl.glEnd();
						} break;
						
						case 4:{
								if( cNorm != null )
										cNorm.glNormal( pGl );
								//										System.out.println( "Facet renderGL FILL 4 " );
								
								pGl.glBegin( GL2.GL_QUADS );
								cPoints[0].glVertex( pGl );
								cPoints[1].glVertex( pGl );
								cPoints[2].glVertex( pGl );
								cPoints[3].glVertex( pGl );
								pGl.glEnd();
						} break;
								
						default:{
								if( cNorm != null )
										cNorm.glNormal( pGl );
								
								//			System.out.println( "Facet renderGL FILL : "  + cPoints.length );

								pGl.glBegin( GL2.GL_POLYGON );
								for( int i=0; i< cPoints.length; i++ ){
										cPoints[i].glVertex( pGl );										
								} 
								pGl.glEnd();
								break;								
						}
						}
				}
				}
		}
}

//*************************************************
