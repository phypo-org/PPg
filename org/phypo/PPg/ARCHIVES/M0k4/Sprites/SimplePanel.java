package com.phipo.PPg.M0k4.Sprites;




import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;

import java.awt.geom.Point2D;

import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;




import com.phipo.PPg.PPgGame.*;


/**
 ** Sous fentre permettant de voir les application et les connexions 
 ** sous forme graphique.
 ** 
 */


//***********************************
final public class SimplePanel extends PanelBox
{ 		
		int cMemClickX=0;
		int cMemClickY=0;
		
		int cMemClickX2=0;
		int cMemClickY2=0;
		
		Rectangle cRectSelect=null;
		
		boolean cGrepping = true;

		//--------------------------
		public SimplePanel( GamerHuman pGamer, 
												int pX, int pY, int pWidth, int pHeight) { 		
				
 				super( pGamer, pX, pY, pWidth, pHeight );	

				System.out.println( "SimplePanel.SimplePanel " );	

				/*		
						Color lClear = new Color(0.f, 0.f, 0.f, 0.f);
						getGC().setColor( lClear );
						getGC().fillRect( 0, 0, 200, 200 );
				*/
	
		}  																					
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		public void mousePressed( MouseEvent pEv ) {

				//			System.out.println( "SimplePanel.mousePressed" );
			getGamerHuman().actionButton1Pressed( new Point2D.Double(), false,false,false ); 
			//	cGrepping = !cGrepping; 
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {

				//		System.out.println( "SimplePanel.mouseReleased" );


		}		

		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {

				//				System.out.println( "SimplePanel.mouseClicked" );

		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				System.out.println( "SimplePanel.mouseDragged" );
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				if( !cGrepping )
						return ;
						
				//				System.out.println( "SimplePanel.mouseMoved" );
				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );

				getGamerHuman().actionMouseMoved( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		/*
		public void clear(){
				// On efface pour pouvoir redessinner 
				//			cGC.setColor( Color.black );
				//	cGC.fillRect( (int)getX()-1, (int)getY()-1, (int)getWidth()+2, (int)getHeight()+2 );
				
				Composite lMemComposite = cGC.getComposite();

				AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f );
				cGC.setComposite(lCompositeClear);
				cGC.setColor( new Color(1f, 0, 0, 0));

				cGC.fillRect(0, 0, (int)getWidth()+2, (int)getHeight()+2);

				cGC.setComposite(lMemComposite);
		}
		*/
		//------------------------------------------ 
		static Paint sGradientPaint = null;
		
		public void paint(){
				
				
				if( World.IsPause()) {						
						cGC.drawString( "PAUSE", 100, 100 );
						return ; 
				}
				/*
					
					Composite lMemComposite = cGC.getComposite();
					
					AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f );
					cGC.setComposite(lCompositeClear);
					cGC.setColor( new Color(1f, 0, 0, 0));
					cGC.fillRect(0, 0, (int)getWidth(), (int)getHeight());
					
					cGC.setComposite(lMemComposite);
					
					//		cGC.setColor( Color.red);
					//			cGC.fillRect(0, 0, (int)getWidth(), (int)getHeight());
					cGC.setColor( Color.red);
					cGC.fillRect(0, 0, 100, 100);
				*/
				World.Get().sceneDraw( getGC() );			
		}	
}
//***********************************















