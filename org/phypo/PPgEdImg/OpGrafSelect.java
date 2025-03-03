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
public class OpGrafSelect extends OpGrafUtil{


		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		public boolean usePickColor() { return false; }

		//------------------------------------------------
		public void cmdMenuOp(  MouseEvent pEv  ){
				// mettre les commande du menu select 
		}
		//------------------------------------------------



		public String histoGetName() { return "Select"; }


		Point cMemPoint;
		Point cMemPoint2;

		Area  cSavArea;
		
		PPgSelectBox cSelectBox ;

		PPgSelectBox.TypeSelectBox cTypeResize = PPgSelectBox.TypeSelectBox.None;

		//------------------------------------------------
		public OpGrafSelect( EdImgInst pMyInst){

				super( pMyInst );
		}
		

		//------------------------------------------------
		protected void beginOp(  Point pPoint ){
								
				cMemPoint =   pPoint ;
				cSavArea = (Area) cMyInst.cSelectZone.getForcedArea().clone();


				if( cMyInst.cSelectZone.isActive() == false ){

						// SELECT DE TOUT L'ECRAN
						cMyInst.cSelectZone.addShape( new Area( new Rectangle2D.Float( 0, 0,  cMyInst.cLayerGroup.getWidth(), cMyInst.cLayerGroup.getHeight() )));
						cMyInst.cSelectZone.setActive( true );

						// A FAIRE HISTO
						cMyInst.cFrame.actualize();
						cInWork = false;				
						
						return ;
				}
				

				Rectangle lBounds = cMyInst.cSelectZone.getForcedArea().getBounds();
						
				cSelectBox= new PPgSelectBox( lBounds, SelectZone.fSzHandle );
				if( ( cTypeResize = cSelectBox.witchHandle( pPoint )) == PPgSelectBox.TypeSelectBox.None ){


						// On n'a cliquer sur aucun handle
						if( lBounds.contains( pPoint ) == false ){
								
								// si on clique en dehors de la zone de selection on l'annule
								cMyInst.cSelectZone.reset();
								cMyInst.cFrame.actualize();
								cInWork = false;				
								//						System.out.println("DESELECT ALL ");
							return ;
						}	
				}

				System.out.println("HANDLE "+ cTypeResize);

				
				moveOp(  pPoint );
		}

		//------------------------------------------------

		protected void moveOp( Point pPoint){


				cMyInst.cLayerGroup.clearOverlay();
				Graphics2D lG = cMyInst.cLayerGroup.getOverlayGraphics();
				
				
				//			lG.setXORMode( Color.black );
				// je reprend une copie a chque fois pour eviter les pb d'arrondies pour scale
				Area lArea = (Area)cSavArea.clone();  // cMyInst.cSelectZone.getArea();
				cMyInst.cSelectZone.setArea( lArea );

				Rectangle lBounds = lArea.getBounds();

				double    lDx = pPoint.x - cMemPoint.x;  //deplacemet
				double    lDy = pPoint.y - cMemPoint.y;
				
				// A FAIRE IL FAUDRAIT DETECTER L4 INVERSION SUR LES? AXES ET INVERSER L4IMAGES S4IL Y LIEU

				if( cTypeResize ==  PPgSelectBox.TypeSelectBox.None ){		
						
						cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

	
						AffineTransform lTransform = AffineTransform.getTranslateInstance( lDx, lDy );
						lArea.transform( lTransform );
						cSelectBox = new PPgSelectBox( lArea.getBounds(), SelectZone.fSzHandle  );
				}
				else {
						double lSx =(lDx+lBounds.width)/lBounds.width ;
						double lSy =(lDy+lBounds.height)/lBounds.height;
						
						
						switch( cTypeResize ){
								
						case BottomLeft: {
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR ));	
 
								lDx = cMemPoint.x- pPoint.x;  //deplacemet
								lSx =(lDx+lBounds.width)/lBounds.width ;
								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform(  AffineTransform.getScaleInstance(  lSx, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x-lDx, lBounds.y ));
						} break;
								
								
						case TopRight:{
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR ));	

								lDy = cMemPoint.y- pPoint.y;  //deplacemet
								lSy =(lDy+lBounds.height)/lBounds.height ;
								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform( AffineTransform.getScaleInstance(   lSx, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x, lBounds.y-lDy ));
						}	break;
																

						case TopLeft:{
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR ));	

								lDx = cMemPoint.x- pPoint.x;  //deplacemet
								lSx =(lDx+lBounds.width)/lBounds.width ;
								lDy = cMemPoint.y- pPoint.y;  //deplacemet
								lSy =(lDy+lBounds.height)/lBounds.height ;
								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform( AffineTransform.getScaleInstance(   lSx, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x-lDx, lBounds.y-lDy ));
						} break;
						

						case MiddleTop: {
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR ));	

								lDy = cMemPoint.y- pPoint.y;  //deplacemet
								lSy =(lDy+lBounds.height)/lBounds.height ;
								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform(  AffineTransform.getScaleInstance(  1, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x, lBounds.y-lDy ));
						} break;
		
						case MiddleLeft: {
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR ));	

								lDx = cMemPoint.x- pPoint.x;  //deplacemet
								lSx =(lDx+lBounds.width)/lBounds.width ;
								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform(  AffineTransform.getScaleInstance(  lSx, 1 ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x-lDx, lBounds.y ));
						} break;
								



						case MiddleRight:
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR ));	

								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform( AffineTransform.getScaleInstance(  lSx, 1 ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x, lBounds.y ));
								break;
								
						case MiddleBottom: 
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR ));	

								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform(  AffineTransform.getScaleInstance(  1, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x, lBounds.y ));
								break;

						case BottomRight: {
								cMyInst.cCanvas.setCursor( Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR ));	

								lArea.transform( AffineTransform.getTranslateInstance(  -lBounds.x, -lBounds.y ));
								lArea.transform(  AffineTransform.getScaleInstance(   lSx, lSy ));
								lArea.transform( AffineTransform.getTranslateInstance(  lBounds.x, lBounds.y ));
						} break;
								
						case Center:
						case None: {						
								AffineTransform lTransform = AffineTransform.getTranslateInstance( lDx, lDy );
								lArea.transform( lTransform );
								cSelectBox = new PPgSelectBox( lArea.getBounds(), SelectZone.fSzHandle  );
						} break;
						}

						cSelectBox = new PPgSelectBox( lArea.getBounds(), SelectZone.fSzHandle);												
				}

				//				lG.setColor( Color.black );
				//				cSelectBox.draw( lG );
				
				//		cMemPoint =   pPoint ;
				
				cMyInst.cCanvas.actualize();
				
		}
		//------------------------------------------------
		protected void finalizeOp(  Point pPoint ){
				
				//		cMyInst.cLayerGroup.restoreCurrentLayer();

				cMyInst.setCurrentCursor();

				cMyInst.cLayerGroup.clearOverlay();
				cMyInst.cFrame.actualize();				
		}
		//------------------------------------------------
		public void cancelOp(){

				cMyInst.setCurrentCursor();

				cMyInst.cSelectZone.setShape( cSavArea );
				//	cMyInst.cLayerGroup.restoreCurrentLayer();

				cMyInst.cLayerGroup.clearOverlay();
				cMyInst.cCanvas.actualize();				
		}
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------
		public String histoTraductToComment(  String pData ){

				return "Select";				
		}
		//------------------------------------------------
		public String histoGetData(){


				return "None";
		}
		//------------------------------------------------
		public void histoReplay(  String pData ){
				
				PPgRef<Integer> lRefInt = new PPgRef<Integer>();
				int lPos = 2;

				// Il faut changer l' activation de la selection courante 
		}			
}

//*************************************************
