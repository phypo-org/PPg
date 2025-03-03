package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;

import java.awt.geom.*;

//import java.awt.geom.Rectangle2D.Float;
//import java.awt.geom.Ellipse2D.Float;



import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafSelZone extends OpGrafUtil{

		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char  histoGetCode(){
				return sMyKeyCode;
		}


		public boolean usePickColor() { return false; }


		public String histoGetName() { return "Select Zone"; }


		Point cMemPoint;
		Point cMemPoint2;

		
		//------------------------------------------------
		public void cmdMenuOp(  MouseEvent pEv  ){
		}


		//------------------------------------------------
		public OpGrafSelZone( EdImgInst pMyInst){

				super( pMyInst );
		}
		//------------------------------------------------

		public Cursor getCursor( boolean pFlagMove ){

				Cursor  lCursor = cMyInst.sSelectRectCursor;
				
				switch( cType ){				
		
				case RECTANGLE: {			
						 lCursor = cMyInst.sSelectRectCursor;

						 if( PPgWinUtils.sFlagShift  ) 
								 lCursor = cMyInst.sSelectRectAddCursor;								
						 else	if( PPgWinUtils.sFlagCtrl )
								 lCursor = cMyInst.sSelectRectSubCursor;						 
						break;
				}
				case ROUND_RECT: {	
						lCursor = cMyInst.sSelectRectCursor;

						if( PPgWinUtils.sFlagShift ) 
								lCursor = cMyInst.sSelectRectAddCursor;
						else if( PPgWinUtils.sFlagCtrl )
								lCursor = cMyInst.sSelectRectSubCursor;						
						break;
				}					
						
				case OVAL: {
						lCursor = cMyInst.sSelectOvalCursor;
						
						if( PPgWinUtils.sFlagShift )
								lCursor = cMyInst.sSelectOvalAddCursor;
						else 	if( PPgWinUtils.sFlagCtrl )
								lCursor = cMyInst.sSelectOvalSubCursor;
						
						break;				
				}
				}
				return lCursor;
		}
		//------------------------------------------------
		public void setType(  Type pType ){ cType =pType; System.out.println( "Select set " + pType.toString()); }
		//------------------------------------------------
		static void DrawFigure( Graphics2D pG, Type pSubCode, Point pA, Point pB  ){
				
				switch( pSubCode ){

				case RECTANGLE: {			
						PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						lGH.drawRect ( pA, pB, false );
						break;
				}
				case ROUND_RECT: {	
						PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						lGH.drawRoundRect ( pA, pB, false, 0.2f,  0.2f );
						break;
				}					
						
				case OVAL: {
						PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						lGH.drawOval (  pA, pB, false  );
						break;
				}
				}				
		}
		//------------------------------------------------
		protected Area createSelectShape(){

				switch( cType ){
				case RECTANGLE: {						
						PPgGraphicsHelper.PrepareRect( cMemPoint,  cMemPoint2 );
						return new Area( new Rectangle2D.Float(  cMemPoint.x, cMemPoint.y, 
																										 cMemPoint2.x - cMemPoint.x, cMemPoint2.y - cMemPoint.y ));
				}
				case ROUND_RECT: {	
						//						return new RoundRectangle( cMemPoint.x, cMemPoint.
						//															cMemPoint2.x, cMemPoint2.y ); 
				}					
						
				case OVAL: {
						PPgGraphicsHelper.PrepareRect( cMemPoint,  cMemPoint2 );
						return new Area( new Ellipse2D.Float( cMemPoint.x, cMemPoint.y, 
																									cMemPoint2.x - cMemPoint.x, cMemPoint2.y - cMemPoint.y )); 
				}				
				}
				return null;
		}
		//------------------------------------------------
		protected void beginOp(  Point pPoint ){
				
				System.out.println( "\tSELECT beginOp" );

				cMemPoint =   pPoint ;

				moveOp(  pPoint );
		}
		//------------------------------------------------

		protected void moveOp( Point pPoint){


				cMyInst.cCanvas.setCursor( getCursor( true ));

				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !

				System.out.println( "=================== SelZone moveOp ===================" );
				
				//		pLayerGroup.actualizeFrame(); // pour effacer l'ancien tracé
				//		cMyInst.cLayerGroup.restoreCurrentLayer();


				cMemPoint2 = pPoint;

				cMyInst.cLayerGroup.clearOverlay();
				Graphics2D lG = cMyInst.cLayerGroup.getOverlayGraphics();
				
				
				//			lG.setXORMode( Color.red );
				lG.setColor( Color.black );

				float lPattern[]= new float[]{10.0f };

				lG.setStroke( new BasicStroke( 1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, lPattern, 0.0f ) );

				DrawFigure( lG, cType, cMemPoint, cMemPoint2  );
				//			cMyInst.cCanvas.actualize();

				System.out.println( "================= SelZone moveOp END ==============" );

				cMyInst.cCanvas.actualize();		
		}
		//------------------------------------------------

		protected void finalizeOp(  Point pPoint ){

				cMyInst.cCanvas.setCursor( getCursor( false ));


				System.out.println( "\tSELECT OpGrafFigure finalizeOp" );
				
				cMemPoint2 = pPoint;

				//		cMyInst.cLayerGroup.restoreCurrentLayer();
									

				//				System.out.println( "Select.finalizeOp :" + " Ctrl=" + PPgWinUtils.sFlagCtrl + " Shift=" + PPgWinUtils.sFlagShift );

				if( PPgWinUtils.sFlagShift ) {
						cMyInst.cSelectZone.addShape( createSelectShape() );
				}	else	if( PPgWinUtils.sFlagCtrl ) {
						cMyInst.cSelectZone.subShape( createSelectShape() );
				}	else
						cMyInst.cSelectZone.setShape( createSelectShape() );

				if( cMyInst.cSelectZone.isEmpty() )
						cMyInst.cSelectZone.setActive( false );
				else
						cMyInst.cSelectZone.setActive( true );

				cMyInst.cLayerGroup.clearOverlay();

				cMyInst.cFrame.actualize();		
		}
		//------------------------------------------------
		public void cancelOp(){
				cMyInst.cCanvas.setCursor( getCursor( false ));

				cMyInst.cLayerGroup.clearOverlay();
				cMyInst.cFrame.actualize();				
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

				//METTRE LE MODIFIER en plus + - =
				lStr.append(  "" + cType.cVal + ':' );
				PPgWinUtils.WritePoint( cMemPoint, lStr );
				
				PPgWinUtils.WritePoint( cMemPoint2, lStr );


				return lStr.toString();
		}
		//------------------------------------------------
		public void histoReplay(  String pData ){
				Type lSubCode = Type.Get( pData.charAt( 0 ) );
				
				//LIRE LE MODIFIER en plus + - =
				PPgRef<Integer> lRefInt = new PPgRef<Integer>();
				int lPos = 2;

				// Il faut changer l' activation de la selection courante 
		}			
}

//*************************************************
