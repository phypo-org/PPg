package org.phypo.PPg.PPgGame;


import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;


//*************************************************
// Sous fenetre logique du GamePanel
//*************************************************

abstract public class PanelBox extends  Rectangle {
		
		// vient de Render
		protected Graphics2D     cGC = null;
		public    Graphics2D     getGC() { return cGC; }


		public Dimension  cSize = new Dimension(0,0);
		public boolean isRedrawReady() { return cGC != null; }
		public boolean isRedrawFinish() { return cGC == null; }

		protected RenderBase cMyRender=null;
		public void setRender( RenderBase pRender ){ cMyRender=pRender;}
		

		public void setRedrawFinish() { cGC = null; }
		public void setRedrawReady( Rectangle pDrawRect, Dimension pDim, Graphics2D pGC  ){
				//				System.out.println( "******************** PanelBox.setRedrawReady ******************** " );

				//////				cDrawRect.setRect( pDrawRect.getX(), pDrawRect.getY(), pDrawRect.getWidth(), pDrawRect.getHeight());
				cSize.setSize(pDim);
				cGC = pGC;
		}

		double cMagnify = 2;   // Calculer magnify en fonction de la taille de la fenetre et de la taille de la carte 		
		public double getMagnify() { return cMagnify; }
		// fin Render

		GamerHuman cGamer = null;
		public GamerHuman getGamerHuman() { return cGamer; }
		

		Color      cMyBackgroundColor = Color.black;
		public void setMyBackgroundColor( Color pColor ) { cMyBackgroundColor= pColor; }

		boolean cTranslucent = false;
		public void setTranslucent(){
				cTranslucent = true;
				cMyBackgroundColor = new Color( 0,0,0,0 );
		}

		
		//------------------------------------------------
		public PanelBox( GamerHuman  pGamer, 
							int pX, int pY, int pWidth, int pHeight ) {
				super( pX, pY, pWidth, pHeight );

				cGamer  = pGamer;
		}				

		//------------------------------------------------

		public void display() {


				/*		System.out.println( "PanelBox.display " 
														+(int)getX()
														+ " " + (int)getY()
														+ " " +(int)getWidth()+2
														+ " " +(int)getHeight()+2); 
				*/
				//
				AffineTransform lSavTrans = cGC.getTransform();


	
				//==================

				// On ajoute un clipping pour eviter tout debordement
				cGC.setClip( (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight() );

				clear();

				// On tient compte de la position de la PanelBox 
				cGC.translate( (int)getX(), (int)getY() );

				///////cRender.paint( );

				paint();   // Fonction ou appeler le render !!!
				
				cGC.setTransform( lSavTrans );

				//	cGC.setColor( Color.blue );
				//		cGC.drawRect( (int)getX()-1, (int)getY()-1, (int)getWidth()+2, (int)getHeight()+2 );
	
		}
		//------------------------------------------------
		public void clear(){
				// On efface pour pouvoir redessinner 


				if( cTranslucent == false ){

						//				System.out.println("PanelBox.clear color");
						cGC.setColor( Color.black);
						cGC.fillRect( (int)getX()-1, (int)getY()-1, (int)getWidth()+2, (int)getHeight()+2 );
				} else {

						//							System.out.println("PanelBox.clear transcent");

						Composite lMemComposite = cGC.getComposite();

						AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f );
						cGC.setComposite(lCompositeClear);
						//	cGC.setColor( new Color(1f, 0, 0, 0));
						
						cGC.fillRect(0, 0, (int)getWidth()+2, (int)getHeight()+2);
						
						cGC.setComposite(lMemComposite);
				}
				
		}
		//------------------------------------------------
		public void paint() {
				try {
						if ( cMyRender != null ) 
								cMyRender.paint( cGC );

				} catch( Exception e){
						System.err.println("catch " + e
															 + "  in  PanelBox.paint"  );
						e.printStackTrace();
				}
		}
		//------------------------------------------

		protected boolean cFlagCtrl  = false;
		protected boolean cFlagShift = false;
		protected boolean cFlagAlt   = false;

	 


		public void evalueModifier( MouseEvent pEv ) {
				int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
				int lShift = InputEvent.SHIFT_DOWN_MASK;
				int lAlt   = InputEvent.ALT_DOWN_MASK;

				cFlagCtrl  = false;
				cFlagShift = false;
				cFlagAlt   = false;

				if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
						cFlagCtrl = true;
						System.out.println( "CTRL" );
				}
				if( (pEv.getModifiersEx() & lShift) == lShift ){
						cFlagShift = true;
						System.out.println( "SHIFT" );
				}
				if( (pEv.getModifiersEx() & lAlt) == lAlt ){
						cFlagAlt = true;
						System.out.println( "ALT" );
				}		
		}
		//------------------------------------------------

		public void mousePressed( MouseEvent pEv ) {;}
		public void mouseReleased( MouseEvent pEv ) {;}
		public void mouseClicked( MouseEvent pEv ) {;}
		public void mouseDragged( MouseEvent pEv ){;}
		public void mouseMoved( MouseEvent pEv ){;}
		public void keyPressed( KeyEvent pEv ){;}
};

//*************************************************
