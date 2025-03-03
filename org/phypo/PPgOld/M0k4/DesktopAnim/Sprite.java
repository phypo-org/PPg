package com.phipo.PPg.M0k4.DesktopAnim;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;
import java.awt.Graphics2D;

import java.util.Random;
import java.util.ArrayList;

import javax.swing.*;


import java.awt.*;


import com.phipo.PPg.PPgGame.*;
import com.phipo.PPg.PPgMath.*;

import com.phipo.PPg.PPgImg.*;




//*************************************************

public class Sprite extends JDialog {

		public JLabel cText;


		PPgImgBase cMyImg;
		PPgRandom cRand = new PPgRandom();

		int     cState = 0;
		double  cTimeAnimSeq; // entre 0.0 et 1.0 !

		double cLastTimeAnim =  cRand.nextDoublePositif( 100) ;
		double cAleaTimeAnim = 1+cRand.nextDoublePositif( 1 ) ;
		double cTimeDiff=0;

		public PPgImgBase getImg() { return cMyImg; }


		Dimension cDimension;
		float cVx=0f;
		float cVy=0f;
		float cX;
		float cY;

		Point cLocation = new Point( 50, 80 );
		DesktopAnim cMyFather;
		String cName;
		public String getName() { return cName; }

		//------------------------------------------------
		public void setState( int pState ){
				cState = pState;
		}
		//------------------------------------------------
		public int geAnimtNbState()      { return cMyImg.getAnimNbState(); }
		public int getAnimSizeSequence() { return cMyImg.getAnimSizeSequence(); }
		public double getAnimDuration()  { return cMyImg.getAnimDuration(); }

		//------------------------------------------------

		public Sprite( DesktopAnim pFather, String pName, float pVx, float pVy,   PPgImgBase pImg) {
				super();

				System.out.println( "Sprite.Sprite");
				
				cMyFather = pFather;
				cName = pName;

				cDimension = new Dimension( pImg.getWidth()+2, 
																		pImg.getHeight()+2);

				setSize( cDimension );
				getContentPane().setLayout( new BorderLayout() );		
				setUndecorated( true );
				//				setAlwaysOnTop(cMyFather.cAlwaysOnTop);
				getContentPane().setLayout( new BorderLayout() );		

				setLocation(cLocation);

				cVx = pVx;
				cVy = pVy;

				//			System.out.println( "Sprite 1" );

				cMyImg = pImg;
				//	cText.setText( "cName" );

				final Sprite lSprite = this;
				cText  = new JLabel() {
								public void paint(Graphics g){
										
										//	System.out.println( "    Sprite paint" );
										
										lSprite.draw( ( Graphics2D)g, cDimension.width/2+1, cDimension.height/2+1, cTimeDiff );
								}
						};
				
				//				System.out.println( "Sprite 2" );
				cText.setPreferredSize(cDimension ); 
				
				//				System.out.println( "Sprite 2.1" );
				//sText.setFont( sText.getFont().deriveFont(sFontSize));
				//sText.setForeground( sColor );
				getContentPane().add( cText, BorderLayout.CENTER  );

				//				System.out.println( "Sprite 2.2" );
				setBackground(new Color(0,0,0,0));
				//				System.out.println( "Sprite 2.3" );
				setOpacity( cMyFather.cOpacity );
														
				//				System.out.println( "Sprite 3" );

				cX = cLocation.x;
				cY = cLocation.y;

				//				System.out.println( "Sprite 4" );

				setVisible(true);
				//				System.out.println( "Sprite.end Sprite");
		}
		//------------------------------------------------
		public void outOfWorld() {

				if( cX < 0  ) {
						cX = 0;
						cVx = -cVx;
				}

				if( cY < 0  ) {
						cY = 0;
						cVy = -cVy;
				}

				if( cX > cMyFather.cScreenSize.width ) {
						cX = cMyFather.cScreenSize.width;
						cVx = -cVx;
				}

				if( cY > cMyFather.cScreenSize.height ){
						cY =  cMyFather.cScreenSize.height ;
						cVy = -cVy;
				}
		}
		//------------------------------------------------
		public void animate( double pTtimeDiff ){

				cTimeDiff = pTtimeDiff;

				//	System.out.println( "Sprite animate " + cName + cTimeDiff);
				outOfWorld();


				cX +=  cVx * cTimeDiff;
				cY +=  cVy * cTimeDiff;


				
				if( ((int)cX) != cLocation.x 
						||((int)cY) != cLocation.y ){
						
						cLocation.x = (int) cX;
						cLocation.y = (int) cY;

						setLocation(cLocation);
				}
				cText.repaint();				
		}
		//------------------------------------------------
		public void draw( Graphics2D pG, int pX, int pY, double pTimeDelta) {

				//			System.out.println( "  Sprite draw" );
										
				//============== ANIM ==============
				cTimeAnimSeq= (cLastTimeAnim/ getAnimDuration())%1.0;
				
				cLastTimeAnim += pTimeDelta*cAleaTimeAnim;
				
				//==================================				
				
				cMyImg.draw( pG, pX, pY, cState, cTimeAnimSeq  );										
		}
}
//*************************************************
