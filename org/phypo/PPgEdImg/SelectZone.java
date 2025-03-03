package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Image;
import java.awt.Image.*;
import java.awt.image.BufferedImage;

import java.awt.geom.Area;


import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class SelectZone {

		static final public int fSzHandle = 10;

		static int sMemAreadMaxSize = 30;

		public EdImgInst cMyInst = null;

		boolean cActive = false;
		public boolean isActive() { return cActive; }
		public void    setActive( boolean pFlag) { cActive = pFlag; }
		
		ArrayList<Area> cMemArea = new ArrayList<Area>();
		

		Area cArea = new Area();

		BufferedImage cSelectPixels = null;


		public SelectZone( EdImgInst pInst ){
				cMyInst = pInst;
		}

		public void chgInstance( EdImgInst pInst ){
				cMyInst = pInst;
		}

		public void setSelectPixels( BufferedImage pBuf ){
				cSelectPixels = pBuf;
		}

		public BufferedImage getSelectPixels() { return cSelectPixels;}


		public void addShape( Area pArea ){
				cArea.add(  pArea );
				cSelectPixels = null;
		}

		public void subShape( Area pArea ){
				cArea.subtract( pArea );
		}
		public boolean isEmpty(){
				if( cArea != null && cArea.isEmpty() == false )
						return false;

				return true;
		}

		public void clear(){
				if( cArea != null && cArea.isEmpty() == false ){
						cMemArea.add( cArea );						
						if( cMemArea.size() > sMemAreadMaxSize ){
								for(int i=0; i<cMemArea.size()-sMemAreadMaxSize; i++)
										cMemArea.remove( i );
								//cMemArea.removeRange( 0, cMemArea.size()-sMemAreadMaxSize  );
						}
				}
				//				cArea.reset();
				cArea = new Area();	
		}
		public int memAreaSize(){ return cMemArea.size(); }

		public void recalMem(){
				if( cMemArea.size() > 0 ){
						cArea = cMemArea.get( cMemArea.size()-1);
						cMemArea.remove( cMemArea.size()-1);
				}
				cActive = true;							
		}
		public void setShape( Area pArea ){
				clear();

				cArea.add( pArea );
				cSelectPixels = null;
		}
		

		public void reset(){
				clear();

				cSelectPixels = null;
				cActive = false;
		}

		public Area getActiveArea(){
				if( isActive() )
						return cArea;

				return null;
		}
		public Area getForcedArea(){
				if( isActive() )
						return cArea;

				return null;
		}
		public void setArea( Area pArea ){
				cArea = pArea;
		}

		public void clip( Graphics2D pG ){
				if( cActive && cArea != null )		
						pG.setClip( cArea );
		}

		//------------------------------------------------
		public void drawZone( Graphics2D pG ){
				if( cActive && cMyInst != null  ) {

						if( cSelectPixels != null ){
								//			pG.clip( cArea );
								Rectangle lBounds = cArea.getBounds();
								pG.drawImage( cSelectPixels, lBounds.x, lBounds.y, lBounds.width, lBounds.height,null );
								//	pG.clip( null ); NE MARCHE PAS !!!
								//	pG.clip( new Rectangle( 0, 0, 100000, 1000000 )); // ????
						}
						
						pG.setColor( new Color( 0.5f, 0.5f, 0.5f, 0.4f ));
								//		float lPattern[]= new float[]{10.0f };
								//	pG.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, lPattern, 0.0f ) );

								//	 	pG.setXORMode( Color.black );

	 
						pG.fill( cArea );	
				}
		}
		//------------------------------------------------
		
		public void drawSelect( Graphics2D pG, double pScale ){
				
				if( cActive && cMyInst != null  ) {
						
						pG.setColor( Color.black );
						if( cMyInst != null && cMyInst.getCurrentUtil() == cMyInst.cSelect ){
								Rectangle lBounds = cArea.getBounds();
								PPgSelectBox lSelectBox = new PPgSelectBox( (int)(lBounds.x*pScale),  (int)(lBounds.y*pScale),  (int)(lBounds.width*pScale),  (int)(lBounds.height*pScale), SelectZone.fSzHandle );
								lSelectBox.draw( pG );								
						}
						
				}
		}
}
//*************************************************
