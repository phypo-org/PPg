package org.phypo.PPgEdImg;


import java.util.*;
import java.lang.*;

import java.awt.Image;
import java.awt.image.*;
import java.awt.geom.Area;

import java.awt.*;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgImg.ImgUtils;

//*************************************************

class PPgLayerGroup  {

		public EdImgInst cMyInst = null;


		int cCurrentLayerId = 0;



		ArrayList<PPgLayerEdImg>  cMyLayers = new ArrayList<PPgLayerEdImg>();
		public ArrayList<PPgLayerEdImg> getLayers() { return cMyLayers; }

		


		int                   cCurrentLayer = 0;		
		public int            getCurrentLayerRow () { return cCurrentLayer; }

		public PPgLayerEdImg  getCurrentLayer() { if( cMyLayers.size() == 0 ) return null; return cMyLayers.get( cCurrentLayer ); }

		public void  setCurrentLayer( int pCur) {
				if( pCur < cMyLayers.size() )
						cCurrentLayer = pCur;

				//	if( pNotifDialog &&DialogLayers.sTheDialogLayers != null ){
				//	DialogLayers.sTheDialogLayers.selectRow( pCur );										
		}

		public Graphics2D         getGraphics() { return getCurrentLayer().getGraphics(); }


		// ============= Overlay =============

		PPgLayerEdImg cOverlay = null;

		//-----------------------------
		void createOverlay(){
				if( cOverlay == null ){						
						cOverlay=	new PPgLayerEdImg( PPgLayerEdImg.LayerMask.TRANSPARENT, "Overlay", getWidth(), getHeight(), getImageType(), -1 );
						cOverlay.setVisible( true );
				}
		}
		//-----------------------------

		public PPgLayerEdImg  getOverlay()         { createOverlay(); return cOverlay;	}
		public Graphics2D     getOverlayGraphics() { createOverlay(); return cOverlay.getGraphics(); }

		public void clearOverlay(){ if( cOverlay != null ) cOverlay.clear( null ); }
		
		void resizeOverlay(){
				cOverlay = null;
				// report the creation to a future call of a overlay's fonction
		}

		// ============= Overlay =============



		//=================================
		BufferedImage cSavImg = null;

		public void saveCurrentLayer(){
				if( getCurrentLayer() != null )
						cSavImg =ImgUtils.CloneImage( getCurrentLayer().getBufferImg());
				else
						cSavImg = null;	
		}
		public void restoreCurrentLayer(){
				if( cSavImg != null && getCurrentLayer() != null ){
						ImgUtils.CopyToImage( cSavImg,  getCurrentLayer().getBufferImg());
				}
		}
    //=================================

		
		public int getWidth()  {
				if( getCurrentLayer() == null )
						return 800;
				return getCurrentLayer().getWidth(); 
		}
		//--------------------------------------
		public int getHeight() {
				if( getCurrentLayer() == null )
						return 600;
				return getCurrentLayer().getHeight();
		}
		//-------------------------------------
		public Dimension getSize(double  pZoom ) { 
				if( getCurrentLayer() == null )
						return new Dimension( 800, 600 );

				return getCurrentLayer().getSize( pZoom );
		}
		//------------------------------------	
		public int  getImageType() { 
				if( getCurrentLayer() != null )
						return getCurrentLayer().getType();
				
				return BufferedImage.TYPE_INT_ARGB_PRE; 
		}
		//------------------------------------------------
		public PPgLayerGroup( EdImgInst pMyInst ){ 

				cMyInst = pMyInst;
		}		
		//------------------------------------------------
		public PPgLayerEdImg  loadFirstImg(ImageIcon pImg  )	{
				if( getCurrentLayer() != null )
						return null;


				PPgLayerEdImg lLayer =  addNewLayer( PPgLayerEdImg.LayerMask.TRANSPARENT, "Background", pImg.getIconWidth(), pImg.getIconHeight(),
															 BufferedImage.TYPE_INT_ARGB_PRE  );				

				Graphics2D lG = getGraphics();
				pImg.paintIcon( null, lG, 0, 0 );

				return lLayer;
		}
		//------------------------------------------------
		public void clearAll(){
				for( PPgLayerEdImg lLayer : cMyLayers ){
						lLayer.clear( null );
				}

				clearOverlay();
		}
		//------------------------------------------------
		public void clearVisibleAll( Color pColor, Area pClip){

				for( PPgLayerEdImg lLayer : cMyLayers ){
						if( lLayer.isVisible() ) {
								if( pColor != null )
										lLayer.clearFill( pColor, pClip);
								else
										lLayer.clearTransparent(pClip);
						}
				}
				clearOverlay();
		}
		//------------------------------------------------
		public PPgLayerEdImg addNewLayer( PPgLayerEdImg.LayerMask pMask, String pName, int pW, int pH, int pType  ) {

				if( pName == null )
						pName = "Layer " + cMyLayers.size(); 

				// FAIRE UN LAYER TRANSPARENT !!!!
				PPgLayerEdImg lNewLayer =	new PPgLayerEdImg( pMask, pName, pW, pH, pType, cCurrentLayerId++ );
				
				cMyLayers.add( lNewLayer );
				cCurrentLayer = cMyLayers.size()-1;
				
				return lNewLayer; 
		}
		//------------------------------------------------
		public void deleteCurrentLayer(){			
				if(  cMyLayers.size() <= 1 ){
						return;
				}
				cMyLayers.remove( cCurrentLayer );
				if( cCurrentLayer > 0 )
						cCurrentLayer--;
		}
		//------------------------------------------------
		public boolean upCurrentLayer(){
				if( cCurrentLayer < 1 ){
						return false;
				}
				PPgLayerEdImg lTmpLayer = cMyLayers.get( cCurrentLayer-1 );
				cMyLayers.set( cCurrentLayer-1,  cMyLayers.get(cCurrentLayer));
				cMyLayers.set( cCurrentLayer, lTmpLayer );
				cCurrentLayer--;

				return true;
		}
		//------------------------------------------------
		public boolean downCurrentLayer(){
				if( cCurrentLayer == cMyLayers.size()-1){
						return false;
				}
				PPgLayerEdImg lTmpLayer = cMyLayers.get( cCurrentLayer+1 );
				cMyLayers.set( cCurrentLayer+1,  cMyLayers.get(cCurrentLayer));
				cMyLayers.set( cCurrentLayer, lTmpLayer );
				cCurrentLayer++;

				return true;
		}
		//------------------------------------------------
		public void dupCurrentLayer(){
				int lLayerSrc = cCurrentLayer;

				PPgLayerEdImg lNewLayer = createNewLayer( cMyLayers.get( lLayerSrc ).getMyMask(), "dup " + cMyLayers.get(lLayerSrc ).getName());
				ImgUtils.CopyToImage( cMyLayers.get( lLayerSrc ).getBufferImg(), lNewLayer.getBufferImg());
		}
		//------------------------------------------------
		public boolean mergeDownCurrentLayer() {

				if( cCurrentLayer == cMyLayers.size()-1){
						return false;
				}
				
				cMyLayers.get( cCurrentLayer ).setImage( ImgUtils.Merge( cMyLayers.get( cCurrentLayer ).getBufferImg(), cMyLayers.get( cCurrentLayer+1 ).getBufferImg() ));
				cMyLayers.remove( cCurrentLayer+1 );
				return true;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public PPgLayerEdImg createNewLayer( PPgLayerEdImg.LayerMask pMask, String pName ) {
				
				return addNewLayer( pMask,  pName, getWidth(), getHeight(), getImageType() );
		}
		//------------------------------------------------
		public PPgLayerEdImg createNewLayer( PPgLayerEdImg.LayerMask pMask, String pName, int pW, int pH ) {
				
				return addNewLayer( pMask,  pName, pW, pH, getImageType()  );
		}
		//------------------------------------------------
		public void destroyAllLayer(){
				cMyLayers.clear();
				cCurrentLayerId = 0;
				cCurrentLayer = 0;
		}
		//------------------------------------------------
		public BufferedImage getClipImage( Area pClip ){

				BufferedImage lBufOut = ImgUtils.GetSameBufferImage(cMyLayers.get(0).getBufferImg());
				Graphics2D lG = lBufOut.createGraphics();
				if( pClip != null )	lG.clip( pClip );

				draw(lG );
				return lBufOut;
		}
		//------------------------------------------------
		public BufferedImage getClipAreaImage( Area pClip ){

				if( pClip == null )
						return getClipImage( null );


				Rectangle lBounds = pClip.getBounds();

				BufferedImage lBufOut = new BufferedImage( lBounds.width, lBounds.height, cMyLayers.get(0).getBufferImg().getType());
				
				
				Graphics2D lG = lBufOut.createGraphics();
				lG.translate( -lBounds.x, -lBounds.y );
				lG.clip( pClip );

				draw(lG );
				return lBufOut;
		}
		//------------------------------------------------
		public void resize( int pW, int pH, int pAlgo ){
				
				for( PPgLayerEdImg lLayer : cMyLayers ){

						lLayer.resize( pW, pH, pAlgo );

						if( cOverlay != null){
								cOverlay.resize( pW, pH, pAlgo );
						}
				}
		}
		//------------------------------------------------
		public void draw( Graphics2D pGraf2 ) {//, PPgCanvasEdImg pCanvas ){
				
				System.out.println( "PPgLayerGroup.draw" );

				for( PPgLayerEdImg lLayer : cMyLayers ){

						System.out.println("\tPPgLayerGroup.draw " + lLayer.getName() + " visible:" +lLayer.isVisible() );
						
						if( lLayer.isVisible() ) {
								// A FAIRE AJOUTER UN MASQUE A CHAQHE LAYER
								System.out.println("\t\tdrawImage");
								pGraf2.drawImage( lLayer.getBufferImg(), 0, 0, null );
						}
				}

				if( cOverlay != null && cOverlay.isVisible() ){
						pGraf2.drawImage( cOverlay.getBufferImg(), 0, 0, null );
				}
		}
}

//*************************************************
