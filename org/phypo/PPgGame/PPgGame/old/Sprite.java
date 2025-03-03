package org.phypo.PPg.PPgGame;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;
import java.awt.Graphics2D;

import java.util.Random;
import java.util.ArrayList;


import javax.swing.ImageIcon;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;

import org.phypo.PPg.PPgImg.*;




//*************************************************

public class Sprite extends ActorMobil {

		PPgImgBase cMyImg;

		int     cState = 0;
		double  cTimeAnim; // entre 0.0 et 1.0 !

		public PPgImgBase getImg() { return cMyImg; }

		Rectangle2D.Double cBoundingBox = null;

		//------------------------------------------------
		public void setState( int pState ){
				cState = pState;
		}
		//------------------------------------------------
		public void setTimeAnim( double pTime){
				cTimeAnim = pTime;
		}
		//------------------------------------------------
		public int geAnimtNbState()      { return cMyImg.getAnimNbState(); }
		public int getAnimSizeSequence() { return cMyImg.getAnimSizeSequence(); }
		public double getAnimDuration()  { return cMyImg.getAnimDuration(); }

		//------------------------------------------------

		public Sprite( double pX, double pY, EnumFaction pFaction, PPgImgBase pImg) {

				super( pX, pY, pFaction );
				cMyImg = pImg;

				if( cMyImg != null ) {
						setBoundingSphere( Math.max( cMyImg.getWidth(), cMyImg.getHeight() ));						
				}
		}
		//------------------------------------------------
		public void computeBoundingBox(){
				if( cBoundingBox == null ){
						cBoundingBox = new Rectangle2D.Double( cLocation.x-getImg().getWidth()*0.5, cLocation.y-getImg().getHeight()*0.5,
																									 getImg().getWidth(), getImg().getHeight() );
				}
				else {
						cBoundingBox.x = cLocation.x-getImg().getWidth()*0.5;
						cBoundingBox.y = cLocation.y-getImg().getHeight()*0.5;
				}
				//		else
				//				cBoundingBox?.setRect( cLocation.x, cLocation.y, getImg().getWidth(), getImg.getHeight());
		}
		//------------------------------------------------
		public Rectangle2D.Double getBoundingBox() {
				return cBoundingBox;
		}
		//------------------------------------------------
		public void worldCallAct( double pTimeDelta) {		 
				
				super.worldCallAct( pTimeDelta );
				
		}
		//------------------------------------------------
		public void worldCallDraw( Graphics2D pG) {
				cMyImg.draw( pG, (int)cLocation.x,  (int) cLocation.y, cState, cTimeAnim  );


				if( (World.sDebugMode & 2) > 0 ){
						pG.setColor( Color.white );
						pG.fillOval( (int)(cLocation.x-1),(int)(cLocation.y-1), 2,2);

						int lSz = (int)getBoundingSphere();
						pG.drawOval( (int)(cLocation.x-lSz*0.5),(int)(cLocation.y-lSz*0.5), lSz, lSz );
						if( cBoundingBox != null )
								pG.drawRect( (int)(cBoundingBox.x),(int)(cBoundingBox.y), (int)cBoundingBox.width, (int)cBoundingBox.height );


				}


		}
		/*
		//------------------------------------------------
		
		public void worldCallDraw( Graphics2D pG) {


				//				System.out.println( " Sprite worldCallDraw " + cImg);
				int lSize =(int)getBoundingSphere();

				if( cImg != null )
						//						cImg.paintIcon( FrameGamer.GetGamePanel(), pG, (int)cLocation.x-lSize/2, (int) cLocation.y -lSize/2);	
				
					pG.drawImage( cImg.getImage(), (int)cLocation.x-lSize/2, (int) cLocation.y-lSize/2,  null);

				//				pG.setColor( Color.white );
				//				pG.drawRect(  (int)(cLocation.x-lSize/2), (int)(cLocation.y-lSize/2), (int)lSize, (int)lSize );
		}
		*/
};

//*************************************************
