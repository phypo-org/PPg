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
public class OpGrafFigure extends OpGrafUtil{

		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}


		public String histoGetName() { return "Figure"; }



		Point cMemPoint;
		Point cMemPoint2;

		//============================


		

		//------------------------------------------------
		public OpGrafFigure( EdImgInst pMyInst){

				super( pMyInst );
		}
		//------------------------------------------------
		public void setType(  Type pType ){ cType =pType; System.out.println( "Figure set " + pType.toString()); }


		//------------------------------------------------
		 void drawFigure(  boolean pCFlagClip, Graphics2D pG, Type pSubCode, Point pA, Point pB, boolean pFill, boolean pRaised ){
				
				 
				 if( pCFlagClip ) 
						 cMyInst.cSelectZone.clip( pG );
				 

				 
				 switch( pSubCode ){
				 case LINE:						
						 pG.drawLine( pA.x, pA.y, pB.x, pB.y  );	
						 break;						
						 
				 case RECTANGLE: {						
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.drawRect ( pA, pB, pFill );
						 break;
				 }
				 case ROUND_RECT: {	
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.drawRoundRect ( pA, pB, pFill, 0.2f,  0.2f );
						 break;
				 }					
				 case RECTANGLE_3D: {	
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.draw3dRect ( pA, pB, pFill, pRaised );
						 break;
				 }					
						 
				 case OVAL: {
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.drawOval (  pA, pB, pFill  );
						 break;
				 }
				}				
		}
		//------------------------------------------------
		protected void beginOp(  Point pPoint ){
				
				System.out.println( "\tbeginOp" );

				cMemPoint =   pPoint ;

				moveOp(  pPoint );
		}

		//------------------------------------------------

		protected void moveOp( Point pPoint){

				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !

				System.out.println( "\tmoveOp" );
				
				//				pLayerGroup.actualizeFrame(); // pour effacer l'ancien tracé

				cMyInst.cLayerGroup.restoreCurrentLayer();

				cMemPoint2 = pPoint;

				Graphics2D lG = cMyInst.cLayerGroup.getGraphics();
				cMyInst.cOpProps.set(lG);//, witchColor() );


				// A FAIRE GERER POUR HISTO 
				Color lColor = cMyInst.cOpProps.getColor();// witchColor() );
				if( lColor.getAlpha() == 0 ) {
						AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
						lG.setComposite(lCompositeClear);
						lG.setColor( new Color(0, 0, 0, 0));
				}

								// Pour peindre en transparent !
				if( lColor.getAlpha() == 0 ) {
						AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
						lG.setComposite(lCompositeClear);
						lG.setColor( new Color(0, 0, 0, 0));
				}

				 lG.setStroke(  new BasicStroke( getStrokeSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );

				 drawFigure( cMyInst.cSelectZone.isActive(), lG, cType, cMemPoint, cMemPoint2, sFilling, sRaised );


				cMyInst.cCanvas.actualize();
		}
		//------------------------------------------------

		protected void finalizeOp(  Point pPoint ){
				System.out.println( "\tOpGrafFigure finalizeOp" );
				
				moveOp( pPoint );
	}
		//------------------------------------------------
		public void cancelOp(){

				cMyInst.cCanvas.actualize();				
		}
		//------------------------------------------------		
		//------------------------------------------------
		//------------------------------------------------
		
		public void makeToolBar( JToolBar pBar){ 

				super.makeToolBar( pBar );

				if( cType == Type.RECTANGLE_3D ){
						pBar.add( (cCheckRaised = new JCheckBox( "Raised" )));
						cCheckRaised.setSelected( sRaised );
						cCheckRaised.addItemListener( this );
				}

			
				pBar.add( (cCheckFilling = new JCheckBox( "Fill" )));
				cCheckFilling.setSelected( sFilling );
				cCheckFilling.addItemListener( this );
		}
				
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------
		public String histoTraductToComment(  String pData ){

				char lSubCode   = pData.charAt( 0 );
				return Type.Get( pData.charAt( 0 )).cStr;				
		}
		//------------------------------------------------
		public String histoGetData(){

				StringBuilder lStr= new StringBuilder(10 + 2*8 );

				lStr.append(  "" + cType.cVal + ':'
											// + witchColor() +':'
											+ getStrokeSize() + ':' + (sFilling?'1':'0') + ':' + (sRaised?'1':'0') + ':');
				PPgWinUtils.WritePoint( cMemPoint, lStr );
				
				PPgWinUtils.WritePoint( cMemPoint2, lStr );


				return lStr.toString();
		}
		//------------------------------------------------
		public void histoReplay(  String pData ){
				Type lSubCode = Type.Get( pData.charAt( 0 ) );
				
				PPgRef<Integer> lRefInt = new PPgRef<Integer>();
				int lPos = 2;

				//			lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				//			int lColorNum = lRefInt.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lSize = lRefInt.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				boolean lFill = lRefInt.get()==1;

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				boolean lReased = lRefInt.get()==1;

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();
				cMyInst.cOpProps.set(lG);//, lColorNum);

				Point lA = new Point();

				lPos =	PPgWinUtils.ReadPoint( lA, pData, lPos);
				
				Point lB = new Point();
				lPos =	PPgWinUtils.ReadPoint( lB, pData, lPos);


				lG.setStroke(  new BasicStroke( getStrokeSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
				drawFigure( cMyInst.cSelectZone.isActive(), lG, lSubCode, lA, lB, lFill, lReased );
		}			
}

//*************************************************
