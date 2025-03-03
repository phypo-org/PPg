package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafPencil extends OpGrafUtilMem {
		
		static char sMyKeyCode = ' ';

		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		Color cCurrentColor;


		//------------------------------------------------

		public OpGrafPencil(EdImgInst pMyInst){
				super( pMyInst );

				cFlagBarOpacity = true;
		}

		//------------------------------------------------
		void draw( Graphics2D pG ){

				cMyInst.cSelectZone.clip( pG );

				int [] lX = new int[cArrayPoint.size()];
				int [] lY = new int[cArrayPoint.size()];
				for( int i=0; i< cArrayPoint.size(); i++){
						lX[i] = cArrayPoint.get(i).x;
						lY[i] = cArrayPoint.get(i).y;
				}	
				pG.drawPolyline( lX, lY,  cArrayPoint.size() );
		}

		//------------------------------------------------

		public void moveOp(  Point pPoint){
				
				cArrayPoint.add( new Point( pPoint) );

				cMyInst.cLayerGroup.restoreCurrentLayer();

				
				Graphics2D lG= cMyInst.cLayerGroup.getGraphics(); // Pointe sur le layer courant
				Color lColor = cMyInst.cOpProps.getColor();// witchColor() );

				float lCol[]= new float[4];
				lColor.getComponents( lCol );

				// A FAIRE GERER POUR HISTO 

				cCurrentColor = new Color(  lCol[0], lCol[1], lCol[2], (float)(lCol[3]*sOpacity) );
				lG.setColor( cCurrentColor );

				lG.setStroke(  new BasicStroke( getStrokeSize(), cCap, cJoin));

				// A FAIRE GERER POUR HISTO 
				// Pour peindre en transparent !
				if( lColor.getAlpha() == 0 ) {
						AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
						lG.setComposite(lCompositeClear);
						lG.setColor( new Color(0, 0, 0, 0));
				}

				draw( lG );

						//	cMemPoint = pPoint ;

				cMyInst.cCanvas.actualize();

				System.out.println( "OpGrafPencil.moveOp " + cArrayPoint.size() );
		}
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------

		public String histoGetName() { return "Pencil"; }
		//------------------------------------------------
		public String histoTraductToComment( String pData ){
				return "Pencil" ;
		}
		//------------------------------------------------
		public String histoGetData(){

				System.out.println( "OpGrafPencil.histoGetData " + cArrayPoint.size() );
				StringBuilder lStr= new StringBuilder(10 + cArrayPoint.size()*8 );

				//			lStr.append( "" +  witchColor() + ':' );

				lStr.append( "" + getStrokeSize() + ':' );

				lStr.append( "" +cArrayPoint.size() + ':' );

				for( Point lPoint: cArrayPoint ){
						PPgWinUtils.WritePoint( lPoint, lStr );
				}
				return lStr.toString();
		}		 
		//------------------------------------------------
		public void histoReplay(  String pData ){

				PPgRef<Integer> lRefInt = new PPgRef<Integer>();

				int lPos = 0;

				//			lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				//		int lColorNum = lRefInt.get();
				//		
				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lSize = lRefInt.get();
				
				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lNbPt = lRefInt.get();

				
				//	int lPos = pData.indexOf( ':', lPos );

				//				int lNbPt = Integer.parseInt( pData.substring( 0, lPos++ ));

				System.out.println( "\treplay "+ pData + " ==>" +lPos + " Nb:" + lNbPt);
				

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();
				cMyInst.cOpProps.set( lG); // , lColorNum );

				lG.setStroke(  new BasicStroke( lSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );


				Point lMemPt = new Point();

				PPgWinUtils.ReadPoint( lMemPt, pData, lPos);

				cArrayPoint.clear();
 
				for(int i=0; i< lNbPt; i++){
						Point lPoint = new Point();

						lPos = PPgWinUtils.ReadPoint( lPoint, pData, lPos);
						cArrayPoint.add( lPoint );
				}					
				draw( lG );
				cArrayPoint.clear();
		}

		//------------------------------------------------		

}
//*************************************************
