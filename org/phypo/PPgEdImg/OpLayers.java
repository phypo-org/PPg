package org.phypo.PPgEdImg;


import java.util.*;
import java.lang.*;

import java.awt.Image;
import java.awt.image.*;

import java.awt.*;
import java.awt.geom.Area;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgWin.*;

//*************************************************

class OpLayers  extends OpGraf {

		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		//------------------------------------------------
		public OpLayers(EdImgInst pMyInst ){
				super( pMyInst );
		}

		//------------------------------------------------
		//----------------- INTERFACE --------------------
		//------------------------------------------------

		String cHistoData;

		//------------------------------------------------
		//------------------------------------------------
 		public void cmdFirstLayer( ImageIcon pImg, int pW, int pH  ){
				if( pImg != null ){
						cMyInst.cLayerGroup.loadFirstImg( pImg );
						histoOpNewFrameImg();				
				} else {
						cMyInst.cLayerGroup.createNewLayer( PPgLayerEdImg.LayerMask.FILL, "Background", pW, pH);

						histoOpNewFrameVoid( pW, pH);
				}
		}
		//------------------------------------------------
		public void cmdAddNewLayer(){
				PPgLayerEdImg lNewLayer = cMyInst.cLayerGroup.createNewLayer( PPgLayerEdImg.LayerMask.TRANSPARENT, null );	

				
					// histo
			if( cMyInst.cInReplay == false ){
						cHistoData = "n " + lNewLayer.getMyId();
						cMyInst.cOpControl.save( this );
				}
		}
		//------------------------------------------------
		public boolean cmdDeleteCurrentLayer(){

			cMyInst.cLayerGroup.cMyInst.cLayerGroup.deleteCurrentLayer();	

					// histo
			if( cMyInst.cInReplay == false ){
						cHistoData = "X cl";
						cMyInst.cOpControl.save( this );
				}

			return true;
		}
		//------------------------------------------------
		public boolean cmdDupCurrentLayer(){

			 cMyInst.cLayerGroup.dupCurrentLayer();
				
					// histo
			if( cMyInst.cInReplay == false ){
						cHistoData = "d ";
						cMyInst.cOpControl.save( this );
				}
				return true ;
		}
		//------------------------------------------------
		public boolean cmdMergeDownCurrentLayer(){

				if( cMyInst.cLayerGroup.mergeDownCurrentLayer() == false)
						return false;


					// histo
				if( cMyInst.cInReplay == false ){
						cHistoData = "m ";
						cMyInst.cOpControl.save( this );
				}

				return true ;
		}
		//------------------------------------------------
		public boolean cmdCurrentLayerUp(){
				
				if( cMyInst.cLayerGroup.upCurrentLayer() == false ){
						return false ;
				}
				
					// histo
				if( cMyInst.cInReplay == false ){						

						cHistoData = "u ";
						cMyInst.cOpControl.save( this );
				}
				else
						cMyInst.cFrame.actualize();

				return true ;
		}
		//------------------------------------------------
		public boolean cmdCurrentLayerDown(){
				if( cMyInst.cLayerGroup.downCurrentLayer() == false ){
						return false;
				}
				
					// histo
				if( cMyInst.cInReplay == false ){						

						cHistoData = "u ";
						cMyInst.cOpControl.save( this );
				}
				else
						cMyInst.cFrame.actualize();

				return true ;
		}
		//------------------------------------------------
		public void cmdSetCurrentLayer( int pCur, boolean pMaj ){

				cMyInst.cLayerGroup.setCurrentLayer( pCur );
				if( pMaj )
						DialogLayers.sTheDialog.redraw();

				// histo
				cHistoData = "c " + pCur;
				cMyInst.cOpControl.save( this );
		}
		//------------------------------------------------
		public void cmdClearAll(){
				cMyInst.cLayerGroup.clearAll( );

				
				cHistoData = "x all " ;
				cMyInst.cOpControl.save( this );
		}
		//------------------------------------------------
		public void cmdClearCurrentLayer( Area pArea){
				cMyInst.cLayerGroup.getCurrentLayer().clear( pArea);

				cHistoData = "x current " ;
				cMyInst.cOpControl.save( this );
		}
		//------------------------------------------------
		public void cmdFillColor( Color pColor,  Area pArea ){
				
		}
		//------------------------------------------------
		public void cmdFillAll( Color pColor,  Area pArea ){
				cMyInst.cLayerGroup.clearVisibleAll( pColor, pArea );
				cMyInst.cFrame.actualize();
				
				cHistoData = "f all " ; // AJOUTER COULEUR
				cMyInst.cOpControl.save(  this );
		}
		//------------------------------------------------
		public void cmdPasteSelection(){

				if( cMyInst.cSelectZone.getActiveArea() == null )
						return ;

				Rectangle lBounds = cMyInst.cSelectZone.getForcedArea().getBounds();

				cMyInst.cLayerGroup.getCurrentLayer().setImage( lBounds.x, lBounds.y, lBounds.width, lBounds.height, 
																											cMyInst.cSelectZone.getSelectPixels(), 
																											cMyInst.cSelectZone.getForcedArea() );
				cHistoData = "s ps " ; // AJOUTER taille ...
				cMyInst.cOpControl.save(  this );	
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------
		public void histoOpNewFrameImg(){
				
				cHistoData = "I " ;
				cMyInst.cOpControl.save( this );
		}
		//------------------------------------------------
		public void histoOpNewFrameVoid( int pW, int pH ){

				cHistoData = "N " + pW +','+ pH;
				cMyInst.cOpControl.save( this );
		}
		//------------------------------------------------



		public String histoGetName() { return "Layer"; }


		//------------------------------------------------
		public String histoGetData(){
				return cHistoData;
		}		
		//------------------------------------------------
		public String histoTraductToComment( String pData ){
				char lSubCode = pData.charAt(0);
				switch( lSubCode ){
				case 'n':return "new layer";
				case 'x':return "clear layer";
				case 'X':return "delete layer";
				case 'd':return "dup layer";
				case 'm':return "merge down layer";
				case '^':return "up current layer";
				case 'v':return "down current layer" ;
				case 'c':return "current layer " + pData.substring( 2 );
				case 'N':return "New frame";
				case 'I':return "New frame image";
			}	
				return "unknown : " + pData ;
		}

		//------------------------------------------------
		// Attention c'est le prototype qui est appeler !

		public void histoReplay( String pData ){
				char lSubCode = pData.charAt(0);

				switch( lSubCode ){

				case 'c': 
						int lCur = Integer.decode(  pData.substring( 2 ));
						cMyInst.cLayerGroup.setCurrentLayer( lCur );
						break;

				case 'n': 
						cmdAddNewLayer();
						break;

				case 'x': 
						cmdDeleteCurrentLayer();
						break;

				case 'd': 
						cmdDupCurrentLayer();
						break;

				case 'N':
						cMyInst.cLayerGroup.destroyAllLayer();

						cMyInst.setDefault();

						Point lPt = new Point();
						PPgWinUtils.ReadPoint( lPt, pData, 2 );
						cMyInst.cLayerGroup.addNewLayer(  PPgLayerEdImg.LayerMask.FILL, "Background",lPt.x, lPt.y, BufferedImage.TYPE_INT_ARGB_PRE );
						break;

				case 'I':
						cMyInst.cLayerGroup.destroyAllLayer();
						cMyInst.setDefault();
						cMyInst.cLayerGroup.loadFirstImg( cMyInst.cFrame.getOriginalImage());
						break;
				}	
		}
}