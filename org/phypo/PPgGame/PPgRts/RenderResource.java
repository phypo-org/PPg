package org.phypo.PPgGame.PPgRts;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

import java.awt.font.*;




import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgData.*;


//*************************************************
// Gere l'affichage des ressources du joueur

public class RenderResource extends RenderBase{
		
		Font     cPolice    = new Font( "Monospaced", Font.PLAIN, 16 );
		boolean  cVertical  = false;



		public double getMagnify() { return 1; }

		//------------------------------------------------

		public RenderResource(GamerHuman pGamer, PanelBox pPanel, boolean pVertical ){
				super( pGamer, pPanel );
				cVertical = pVertical;
				
		// Variables renseignées/calculées au moment du paint !
		}
		//---------------------
		public void paint( Graphics2D pGC ) 
		{
				cGC   = pGC;
				FontRenderContext lFontCtx =cGC.getFontRenderContext();

				Rectangle2D	lMaxChar = cPolice.getMaxCharBounds( lFontCtx );

				cGC.setColor( World.Get().cBackgroundColor); // METTRE UNE COULEUR DANS LE .INI

				//			cGC.fillRect( 0, 0,  (int)cMyPanel.cSize.getWidth(),   (int)cMyPanel.cSize.getHeight());
				
				ResourcePool lResPool = cGamer.getResources();
				int lInter = (int)(cMyPanel.cSize.getWidth()/lResPool.length());
				
				int lY = 5; 
				int lX = 5;

				int lDistanceX = 0;
				int lDistanceY = 0;

				if( cVertical )
						lDistanceY =  (int) Math.max( lMaxChar.getHeight(), DefIni.sSizeTinyIcon)+5;
				else
						lDistanceX =  (int)((	cMyPanel.getWidth() -10) / lResPool.length());
				
				for( int i=0; i<lResPool.length(); i++, lX += lDistanceX, lY += lDistanceY ){
						Resource lRes = Resource.Get(i);
						
						int lPosX =lX;
						int lPosY =lY;
						
						lRes.getTinyIcon().paintIcon( null, cGC, lPosX,  lPosY );	
						
						lPosX += lRes.getTinyIcon().getIconWidth()+2;
						
						String lStr = new String( " "+(int)lResPool.get(i) );
						
						cGC.setColor( Color.black ); 
						cGC.drawString( lStr, lPosX+1, (int)( lPosY+1+ lMaxChar.getHeight()) ); 
						cGC.drawString( lStr, lPosX-1,  (int)(lPosY-1 + lMaxChar.getHeight()) ); 
						cGC.drawString( lStr, lPosX+1, (int)( lPosY-1+ lMaxChar.getHeight()) ); 
						cGC.drawString( lStr, lPosX-1,  (int)(lPosY+1 + lMaxChar.getHeight()) ); 
						cGC.setColor( Color.white ); 
						cGC.drawString( lStr, lPosX,  (int)(lPosY + lMaxChar.getHeight()) ); 
				}
		}
}
//*************************************************

