package org.phypo.PPgEdImg;

import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.File;


//*************************************************

public class PPgBrush {

		String cName = null;
		BufferedImage cMyImage = null;

		BufferedImage getImage() { return cMyImage; }

		//------------------------------------------------
		public	PPgBrush( String pName, BufferedImage pBuf ) {
				//	super(  pBuf, new Rectangle( 0, 0, pBuf.getWidth(), pBuf.getHeight()) );

				cMyImage = pBuf;
				cName = pName;
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		static public	PPgBrush CreateBrush( File pFile ) {
				ImageIcon lIcon = new ImageIcon( pFile.getPath() );
				
				BufferedImage lBuf = new BufferedImage( lIcon.getIconWidth(), lIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE );
				
				
				return new PPgBrush( pFile.getName(), lBuf );
		}
		
		//------------------------------------------------
		static public PPgBrush sBackgroundTransparentBrush16x16 = null;
				
		//------------------------------------------------

		static public void InitDefaultBrush(){
				
				BufferedImage lBuf = new BufferedImage(  16, 16, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D lG= (Graphics2D) lBuf.getGraphics();
				
				lG.setColor( Color.pink );
				lG.fillRect( 0,0, 8, 8 );
				lG.fillRect( 8,8, 8, 8 );
				lG.setColor( Color.gray );
				lG.fillRect( 8,0, 8, 8 );
				lG.fillRect( 0,8, 8, 8 );

				sBackgroundTransparentBrush16x16 = new PPgBrush( "Alpha0Brush16x16", lBuf);

				
				
 		}
		//------------------------------------------------
};
//*************************************************
