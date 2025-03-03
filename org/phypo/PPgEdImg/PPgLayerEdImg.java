package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;


import java.awt.*;
import java.awt.Image;
import java.awt.image.*;

import java.awt.geom.Area;

import org.phypo.PPg.PPgImg.*;

//*************************************************
class PPgLayerEdImg {

		enum LayerMask{
				FILL,
						TRANSPARENT
						};
				

		LayerMask cMask;
		
		public LayerMask getMyMask() { return cMask;}

		int cMyId=-1;
		public int getMyId() { return cMyId;}
		
		BufferedImage cMyBufferImg = null;
		public BufferedImage getBufferImg() { return cMyBufferImg;}
		public void          setBufferImg(BufferedImage pBufferImg ) { cMyBufferImg = pBufferImg;}

		// A FAIRE AJOUTER UN MASQUE A CHAQHE LAYER


		public int getWidth()  { return getBufferImg().getWidth(); }
		public int getHeight() { return getBufferImg().getHeight(); }
		public Dimension getSize(double  pZoom ) {
				return new Dimension( (int)(cMyBufferImg.getWidth()*pZoom), 
															(int)(cMyBufferImg.getHeight()*pZoom) );
		}

		public int getType() { return cMyBufferImg.getType(); }

		String cName ="";
		public String getName()               { return cName; }
		public void   setName( String pName ) { cName = pName; }
		

		boolean cVisible = true;
		
		public boolean isVisible()                    { return cVisible; }
		public void    setVisible( boolean pVisible )  { cVisible = pVisible; }

		//------------------------------------------------
		public PPgLayerEdImg( LayerMask pMask, String pName, int pWidth, int pHeight, int pImageType, int pId ) {

				cMyId = pId;
				cMask = pMask;
				System.out.println("----------- PPgLayerEdImg "+ pMask.toString() );

				cMyBufferImg = new BufferedImage( pWidth, pHeight, pImageType );
				cName = pName;

				clear( null );
		}
		//------------------------------------------------
		public void resize( int pW, int pH, int pAlgo ){

				if( pAlgo == -1 ) {
						BufferedImage cBufOut = ImgUtils.GetSameBufferImage( cMyBufferImg, pW, pH );

						ImgUtils.CopyToImage( cMyBufferImg, cBufOut );

						cMyBufferImg = cBufOut;										
				}
				else {
						BufferedImage cBufOut = ImgUtils.GetSameBufferImage( cMyBufferImg, pW, pH );

						Image lTmpImg  = cMyBufferImg.getScaledInstance(  pW, pH, pAlgo ); //Image.SCALE_SMOOTH ));										
						ImgUtils.CopyToImage( lTmpImg, cBufOut );
						cMyBufferImg = cBufOut;										
			}
		}
		//------------------------------------------------
		public void clear(Area pClip){
				System.out.println("---------- PPgLayerEdImg clear "+ cMask.toString() );

				switch( cMask ){
				case FILL:	clearFill( Color.white, pClip ); break;
				case TRANSPARENT:	clearTransparent( pClip  ); break;
				}
		}
		//------------------------------------------------
		public Graphics2D getGraphics() { return (Graphics2D) cMyBufferImg.getGraphics(); }
		//------------------------------------------------
		public void clearFill( Color pColor,  Area pClip ){
				System.out.println("---------- PPgLayerEdImg clearFill " + pColor.toString());
				Graphics2D lG= (Graphics2D) cMyBufferImg.getGraphics(); 

				Composite lMemComposite = lG.getComposite();
				if( pClip != null )	lG.clip( pClip );

				//		AlphaComposite lCompositeClear = AlphaComposite.getInstance( AlphaComposite.CLEAR, 0.0f);
				//				lG.setComposite(lCompositeClear);
				lG.setColor( pColor);
				lG.fillRect(0, 0, getWidth(), getHeight());

				lG.setComposite(lMemComposite);
		}
		//------------------------------------------------
		public void clearTransparent( Area pClip ){

				System.out.println("---------- PPgLayerEdImg clearTransparent" );

				
				Graphics2D lG= (Graphics2D) cMyBufferImg.getGraphics();
 
				if( pClip != null )	lG.clip( pClip );

				Composite lMemComposite = lG.getComposite();

				AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
				lG.setComposite(lCompositeClear);
				lG.setColor( new Color(0, 0, 0, 0));
				lG.fillRect(0, 0, getWidth(), getHeight());

				lG.setComposite(lMemComposite);
		}
		//------------------------------------------------
		public void setImage( int pX, int pY, int pW, int pH, Image pImg, Area pClip ){

				Graphics2D lG= (Graphics2D) cMyBufferImg.getGraphics(); 

				if( pClip != null )	lG.clip( pClip );


				lG.drawImage(  pImg, pX, pY, pW, pH,null );
		}
		//------------------------------------------------
		public void setImage( Image pImg ){

				ImgUtils.CopyToImage( pImg, cMyBufferImg ); 
		}
}
//*************************************************
