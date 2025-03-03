package com.phipo.PPg.PPgG3d;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;




//*************************************************
public class  Aspect3d {
		
		Float4 cColorMaterial;
		Float4 cColorEmission;

		public DrawStyle  cDrawStyle = DrawStyle.LINE ;

		boolean cBackFaceVisible = false;




		final public void setColorMaterial( Float4 pColor ) { cColorMaterial = new Float4( pColor ); }
		final public void setColorEmission( Float4 pColor ) { cColorEmission = new Float4( pColor ); }

		//==================================
		public  enum DrawStyle{ FILL, LINE, SILHOUETTE, POINT };

		final public DrawStyle getDrawStyle()            { return cDrawStyle; }
		final public void setDrawStyle( DrawStyle pDrawStyle ) { cDrawStyle = pDrawStyle; }
		//==================================

		//------------------------------------------------

		//------------------------------------------------

		public Aspect3d( Float4 pColor  ){
				this( pColor, DrawStyle.FILL );
		}

		//------------------------------------------------
		public Aspect3d( Float4 pColor, DrawStyle pDrawSyle  ){				
				if( pColor != null )
						cColorMaterial = new Float4( pColor );

				cDrawStyle = pDrawSyle ;
		}
		
		//------------------------------------------------
		public boolean renderGL(){
			
				boolean cFlagBlend = false;

				if( cBackFaceVisible )
						GL11.glEnable( GL11.GL_CULL_FACE );



				if( cColorMaterial != null && cColorMaterial.cVect[3] < 1.0f )
						cFlagBlend = true;

				if( cColorEmission != null && cColorEmission.cVect[3] < 1.0f )
						cFlagBlend = true;

				
				if( cFlagBlend ) {
						GL11.glEnable( GL11.GL_BLEND) ;

						GL11.glDepthMask( false );
						GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
				}

				if( cColorMaterial != null )
						cColorMaterial.glMaterial();
				
				if( cColorEmission != null )
						cColorEmission.glMaterial();	




				//				GL11.glEnable( GL11.GL_POINT_SMOOTH );
				//				GL11.glEnable( GL11.GL_LINE_SMOOTH );
				//				GL11.glEnable( GL11.GL_POLYGON_SMOOTH );

				return cFlagBlend;
		}	

		//------------------------------------------------
		public void cleanRenderGL(){

				GL11.glDepthMask( true );
				GL11.glDisable( GL11.GL_BLEND );



				//				GL11.glEnable( GL11.GL_POINT_SMOOTH );
				//				GL11.glEnable( GL11.GL_LINE_SMOOTH );
				//				GL11.glEnable( GL11.GL_POLYGON_SMOOTH );

				if( cBackFaceVisible )
						GL11.glDisable( GL11.GL_CULL_FACE );
        
		}
};
//*************************************************
