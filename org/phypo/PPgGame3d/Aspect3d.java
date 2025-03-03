package org.phypo.PPgGame3d;


import com.jogamp.opengl.*;

import com.jogamp.opengl.util.texture.Texture;

//*************************************************
public class  Aspect3d {
		
		public Color4 cColorMaterial;
		public Color4 cColorEmission;

		float cShininess = -1f;

		Texture cTexture;

		public DrawStyle  cDrawStyle = DrawStyle.LINE ;

		boolean cBackFaceVisible = false;

		public void setShininess ( float pShininess  ){
				cShininess = pShininess ;
		}

		public void setTexture( Texture pTexture ){
				cTexture = pTexture;
		}
		public Texture getTexture() { return cTexture; }

		final public void setColorMaterial( Color4 pColor ) { cColorMaterial = new Color4( pColor ); }
		final public void setColorEmission( Color4 pColor ) { cColorEmission = new Color4( pColor ); }

		//==================================
		public  enum DrawStyle{ FILL, LINE, SILHOUETTE, POINT };

		final public DrawStyle getDrawStyle()            { return cDrawStyle; }
		final public void setDrawStyle( DrawStyle pDrawStyle ) { cDrawStyle = pDrawStyle; }
		//==================================

		//------------------------------------------------

		//------------------------------------------------
		public Aspect3d( Texture pTexture   ){
				cTexture = pTexture;
				cDrawStyle = DrawStyle.FILL ;
		}
		//------------------------------------------------

		public Aspect3d( Color4 pColor  ){
				this( pColor, DrawStyle.FILL );
		}
		//------------------------------------------------

		public Aspect3d( Color4 pColor, Color4 pColorEm  ){
				this( pColor, DrawStyle.FILL );
				
				cColorEmission = pColorEm;
		}
		//------------------------------------------------

		public Aspect3d( Color4 pColor, Color4 pColorEm, DrawStyle pDrawSyle  ){
				this( pColor, pDrawSyle );
				
				cColorEmission = pColorEm;
		}
		//------------------------------------------------
		public Aspect3d( Color4 pColor, DrawStyle pDrawSyle  ){				
				if( pColor != null )
						cColorMaterial = new Color4( pColor );

				cDrawStyle = pDrawSyle ;
		}
		//------------------------------------------------
		public Aspect3d( Color4 pColor, Texture pTexture  ){				
				if( pColor != null )
						cColorMaterial = new Color4( pColor );

				cDrawStyle = DrawStyle.FILL ;
				cTexture = pTexture;
		}
		//------------------------------------------------
		public boolean renderGL( GL2 pGl ){
			
				boolean cFlagBlend = false;
				
				if( cBackFaceVisible )
						pGl.glEnable( GL2.GL_CULL_FACE );


				
				if( cColorMaterial != null && cColorMaterial.cVect[3] < 1.0f )
						cFlagBlend = true;
				else	if( cColorEmission != null && cColorEmission.cVect[3] < 1.0f )
						cFlagBlend = true;

	
				
				if( cFlagBlend ) {
						pGl.glEnable( GL2.GL_BLEND) ;
						pGl.glBlendFunc( GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA );
						
									//		pGl.glBlendFunc( GL2.GL_SRC_ALPHA, GL2.GL_ONE );
						
						pGl.glDepthMask( false );
						//	pGl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); 
				}
				//			else {
				//					gl.glBlendFunc(GL_ONE, GL_ZERO); 

				//				pGl.glEnable( GL2.GL_POINT_SMOOTH );
				//				pGl.glEnable( GL2.GL_LINE_SMOOTH );
				//				pGl.glEnable( GL2.GL_POLYGON_SMOOTH );
			
				if( cShininess >= 0 )
						pGl.glMaterialf( GL.GL_FRONT, GL2.GL_SHININESS, cShininess );

				if( cColorMaterial != null ){
						switch( cDrawStyle ) {								
						case POINT:
						case LINE :
						case SILHOUETTE :
							cColorMaterial.glColor( pGl);	
							break; // phi 20140909
						default:
								cColorMaterial.glMaterial( pGl);
						}
				}
				
				if( cColorEmission != null )
						cColorEmission.glEmission( pGl );	
	

				if( cTexture != null ){
						pGl.glEnable(GL2.GL_TEXTURE_2D);	
						return true;
				}
				
				return cFlagBlend;
		}	

		//------------------------------------------------
		public void cleanRenderGL( GL2 pGl){

				if( cTexture != null ){
						pGl.glDisable(GL2.GL_TEXTURE_2D);	
				}

				pGl.glDepthMask( true );
				pGl.glDisable( GL2.GL_BLEND );



				//				pGl.glEnable( GL2.INT_SMOOTH );
				//				pGl.glEnable( GL2.GL_LINE_SMOOTH );
				//				pGl.glEnable( GL2.GL_POLYGON_SMOOTH );

				if( cBackFaceVisible )
						pGl.glDisable( GL2.GL_CULL_FACE );
        
				if( cColorEmission != null )
						Color4.Black.glEmission( pGl );			
		}
};
//*************************************************
