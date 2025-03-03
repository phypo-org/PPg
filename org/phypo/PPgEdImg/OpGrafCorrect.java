
package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;


//*************************************************
public class OpGrafCorrect extends  OpGraf{


		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		int cVal1 = 1;
		int cVal2 = 1;
		int cVal3 = 1;
		int cVal4 = 0;

		int [] cTab1 = null;
		int [] cTab2 = null;

		float [] cScaleOp =  null;
		float [] cOffsetOp = null;


		int cLayerId;

		//=====================================
		
		public enum CorrectCode{ GRAYSCALE_LAYER  ( "GrayScale", 'g'),
						GRAYSCALE_IMAGE  ( "GrayScale", 'G'),
						INVERT_COLOR_IMAGE  ( "InvertColor", 'I'),
						INVERT_COLOR_LAYER  ( "InvertColor", 'i'),
						POSTERIZE_IMAGE( "Posterize img", 'P' ),
						POSTERIZE_LAYER( "Posterize layer", 'p' ),
						RESCALE_OP_IMAGE( "Rescale Op", 'R' ),
						RESCALE_OP_LAYER( "Rescale Op", 'r' ),
						FILTER_RGB2BW( "Filter RGB to BW", 'b' )
					;
				private final String  cComment;
				private final char    cSubCode;
	
				CorrectCode( String pStr, char pSubCode ){
						
						cComment = pStr; 
						cSubCode = pSubCode;
				}
				String getComment() { return cComment; }
				char   getSubCode() { return cSubCode; }
				

				public static final HashMap<Character,CorrectCode> sHashCorrectCode = new HashMap<Character,CorrectCode>();
				static {
						for( CorrectCode lCorrect: CorrectCode.values() ){
								sHashCorrectCode.	put(lCorrect.cSubCode, lCorrect);
						}
				}
				
				public static CorrectCode GetCorrectCode( char pSubCode ){
						return sHashCorrectCode.get( pSubCode );
				}
		};
		//=====================================
		
		PPgLayerEdImg cLayer ;
		CorrectCode   cSubCode;


		//------------------------------------------------
		public OpGrafCorrect(EdImgInst pMyInst){
				super( pMyInst );
		}
		//------------------------------------------------
		public void setParam(  int pVal1, int pVal2, int pVal3, int pVal4 ){
			
				cVal1 = pVal1;
				cVal2 = pVal2;
				cVal3 = pVal3;
				cVal4 = pVal4;
		}
		//------------------------------------------------
		public void setParam(  float [] pScaleOp, float [] pOffsetOp){
			
				cScaleOp  = pScaleOp;
				cOffsetOp = pOffsetOp;
		}
	
		//------------------------------------------------
		public void setParam(  int [] pTab1){			
				cTab1 = pTab1;
				cTab2 = null;
		}
		//------------------------------------------------
		public void setParam(  int [] pTab1, int [] pTab2){			
				cTab1 = pTab1;
				cTab2 = pTab2;
		}
		//------------------------------------------------
		public int makeOp(  CorrectCode pSubCode  ){

				for( PPgLayerEdImg lLayer : cMyInst.cLayerGroup.getLayers() ) {
						if( lLayer.isVisible() )
								makeOpLayer(  lLayer, pSubCode  );
				}
				return 0;
		}
		//------------------------------------------------
		public int makeOpLayer(  PPgLayerEdImg pLayer, CorrectCode pSubCode ){

				cSubCode = pSubCode;
				cLayerId = pLayer.getMyId();
				

				switch( pSubCode ){

				case GRAYSCALE_LAYER:
						if( pLayer != cMyInst.cLayerGroup.getCurrentLayer() )
								break;
				case GRAYSCALE_IMAGE:
						pLayer.setBufferImg( ImgUtils.Convert2GrayScale( pLayer.getBufferImg())); 
						break;


				case INVERT_COLOR_LAYER:
						if( pLayer != cMyInst.cLayerGroup.getCurrentLayer() )
								break;
				case INVERT_COLOR_IMAGE:
						pLayer.setBufferImg( ImgUtils.InvertColor( pLayer.getBufferImg(),
																											 (cVal1==0?false:true),
																											 (cVal2==0?false:true),
																											 (cVal3==0?false:true),
																											 (cVal4==0?false:true))); 
						break;

				case POSTERIZE_LAYER:
						if( pLayer != cMyInst.cLayerGroup.getCurrentLayer() )
								break;
						//				case POSTERIZE_IMAGE:
						//						pLayer.setBufferImg( ImgUtils.Posterize( pLayer.getBufferImg(), cVal1, cVal2 )); 
						//				break;
				case POSTERIZE_IMAGE:
						pLayer.setBufferImg( ImgUtils.PosterizeRGB( pLayer.getBufferImg(), cTab1, cTab2 )); 
						break;

				case RESCALE_OP_LAYER:
							if( pLayer != cMyInst.cLayerGroup.getCurrentLayer() )
								break;
				case RESCALE_OP_IMAGE:
						pLayer.setBufferImg( ImgUtils.RescaleOp( pLayer.getBufferImg(), cScaleOp, cOffsetOp )); 
						break;
							
				case FILTER_RGB2BW:
                                    pLayer.setBufferImg( ImgUtils.FilterRGB2BW( pLayer.getBufferImg(),  cTab1, cTab2 )); 


				default : 
						return 1;
				}
				
				cMyInst.cOpControl.save( this );

				return 0;
		}		
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------
		
		public String  histoGetName() { return "Colors"; }
		
		//------------------------------------------------
			
		public String histoGetData(){
				return ""+ cSubCode.getSubCode() + ' ' + cLayerId + ' ' +cVal1;
		}		
		//------------------------------------------------
		public String histoTraductToComment( String pData ){
				char lSubCode   = pData.charAt( 0 );
				String lStrData = null;

				if( pData.length() > 1){
						 lStrData = pData.substring( 2 );
				}

				CorrectCode lCorrect = CorrectCode.GetCorrectCode( lSubCode );
				return lCorrect.getComment() + (lStrData==null?"":lStrData);
		}
		//------------------------------------------------
		public void histoReplay(  String pData ){
				
		}
}
//*************************************************
