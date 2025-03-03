package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafDropper extends OpGrafUtil{


		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}




		// PAS D HISTORIQUE
		public String histoGetName() { return null; }
		public void histoReplay(  String pData ){;}
		public String histoTraductToComment(  String pData ){return null;}
		public String histoGetData(){return null;}
		protected void finalizeOp(  Point pPoint ){;}

		BufferedImage cBufImg = null;

		Color cColor = null;

		JButton cButtonColor1 = null;
		//	JButton cButtonColor2 = null;


		PPgIntField cLabelR = null;
		PPgIntField cLabelG = null;
		PPgIntField cLabelB = null;
		PPgIntField cLabelA = null;

		//------------------------------------------------
		static public  Color PickColor(  EdImgInst pMyInst, Point pPoint ){

				BufferedImage lBufImg = ImgUtils.GetSameBufferImage( pMyInst.cLayerGroup.getCurrentLayer().getBufferImg());

				pMyInst.cLayerGroup.draw( lBufImg.createGraphics() ); 

				Color lColor = new Color( lBufImg.getRGB( pPoint.x, pPoint.y ), true );
				return lColor;
		}
		//------------------------------------------------
		public OpGrafDropper( EdImgInst pMyInst){

				super( pMyInst );
		}
		//------------------------------------------------
		protected void beginOp(  Point pPoint ){
				
				System.out.println( "\tOpGrafDropper beginOp" );

				cBufImg = 	ImgUtils.GetSameBufferImage( cMyInst.cLayerGroup.getCurrentLayer().getBufferImg());
				cMyInst.cLayerGroup.draw( cBufImg.createGraphics() ); 
				moveOp(  pPoint );
		}
		//------------------------------------------------

		protected void moveOp( Point pPoint){
				
				int lPixel = cBufImg.getRGB( pPoint.x, pPoint.y );
				cColor = new Color( lPixel, true );

				if( witchColor() == 1 ){
						cButtonColor1.setBackground( cColor );	
				}
				/*else	 {
						cButtonColor2.setBackground( cColor );	
				}
				*/
		}	
		//------------------------------------------------
		public void cmdFinalizeOp( Point pPoint ){
				if( cInWork == false )
						return;

				// C EST OpProps QUI HISTORISE
				cMyInst.cOpProps.cmdSetColor( cColor );//, witchColor() );

				cBufImg = null;				
				cColor = null;
		}

			
		//------------------------------------------------
		public void cancelOp(){

				cBufImg = null;				
				cColor  = null;
		}
		//------------------------------------------------
		public void makeToolBar( JToolBar pBar){ 

				pBar.add( (cButtonColor1 = PPgWinUtils.MakeButton(" 1 ", this )));
				//				pBar.add( (cButtonColor2 = PPgWinUtils.MakeButton(" 2 ", this )));

				cButtonColor1.setOpaque( true );
				//				cButtonColor2.setOpaque( true );

				cButtonColor1.setBackground( cMyInst.cOpProps.getColor());
				//				cButtonColor2.setBackground( cMyInst.cOpProps.getColor2());

				pBar.add( (cLabelR = new PPgIntField( "Red"  , cMyInst.cOpProps.getColor().getRed(),   PPgField.HORIZONTAL)));
				pBar.add( (cLabelG = new PPgIntField( "Green", cMyInst.cOpProps.getColor().getGreen(), PPgField.HORIZONTAL)));
				pBar.add( (cLabelB = new PPgIntField( "Blue" , cMyInst.cOpProps.getColor().getBlue(),  PPgField.HORIZONTAL)));
				pBar.add( (cLabelA = new PPgIntField( "Alpha", cMyInst.cOpProps.getColor().getAlpha(), PPgField.HORIZONTAL)));
		}
}

//*************************************************
