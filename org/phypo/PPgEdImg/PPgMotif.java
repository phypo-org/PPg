package org.phypo.PPgEdImg;

import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.File;


//*************************************************

public class PPgMotif {

		String cName = null;
		Paint cMyPaint = null;

		Paint getPaint() { return cMyPaint; }

		//------------------------------------------------
		public	PPgMotif( String pName, BufferedImage pBuf ) {
				//	super(  pBuf, new Rectangle( 0, 0, pBuf.getWidth(), pBuf.getHeight()) );

				cMyPaint = new TexturePaint(  pBuf, new Rectangle( 0, 0, pBuf.getWidth(), pBuf.getHeight()));
				cName = pName;
		}
		//------------------------------------------------
		public	PPgMotif( String pName, Paint pPaint ) {
				//	super(  pBuf, new Rectangle( 0, 0, pBuf.getWidth(), pBuf.getHeight()) );

				cMyPaint = pPaint;
				cName = pName;
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		static public	PPgMotif CreateMotif( File pFile ) {
				ImageIcon lIcon = new ImageIcon( pFile.getPath() );
				
				BufferedImage lBuf = new BufferedImage( lIcon.getIconWidth(), lIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE );
				Graphics lG = lBuf.getGraphics();
				
				lIcon.paintIcon( null, lG, 0, 0 ); 
				
				
				return new PPgMotif( pFile.getName(), lBuf );
		}
		//------------------------------------------------
		static public	PPgMotif CreateMotifGradiant( String pName, OpGrafUtil.Type pType, int pW, int pH, int pNbCycling, Color pExternal, Color pInternal ) {


				 float[] lFractions = new float[pNbCycling+1];
				 Color[] lColors    = new Color[pNbCycling+1];

				 
				 float lInc = 1.0f/pNbCycling;
				 
				 boolean lFlagColor = false;

				 for( int i= 0; i< pNbCycling+1; i++ ){

						 lFractions[i] = i*lInc ;

						 if( lFlagColor )
								 lColors[i] = pExternal;
						 else
								 lColors[i] = pInternal;
						 
						 lFlagColor = !lFlagColor;
				 }
				 lFractions[pNbCycling] = 1.0f;

				Rectangle lRect = new Rectangle( 0, 0, pW, pH );				

				RadialGradientPaint lPaint = new RadialGradientPaint( lRect, lFractions, lColors,  MultipleGradientPaint.CycleMethod.REFLECT  );				
				
				return new PPgMotif( pName, lPaint );
		}
		//------------------------------------------------
		static public PPgMotif sBackgroundTransparentMotif16x16 = null;
				
		//------------------------------------------------

		static public void InitDefaultMotif(){
				
				BufferedImage lBuf = new BufferedImage(  16, 16, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D lG= (Graphics2D) lBuf.getGraphics();
				
				lG.setColor( Color.pink );
				lG.fillRect( 0,0, 8, 8 );
				lG.fillRect( 8,8, 8, 8 );
				lG.setColor( Color.gray );
				lG.fillRect( 8,0, 8, 8 );
				lG.fillRect( 0,8, 8, 8 );

				sBackgroundTransparentMotif16x16 = new PPgMotif( "Alpha0Brush16x16", lBuf);

				
				
 		}
		//------------------------------------------------
};
//*************************************************
