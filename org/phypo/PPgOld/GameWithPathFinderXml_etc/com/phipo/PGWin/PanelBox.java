package com.phipo.GLib;


import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;


//*************************************************

public class PanelBox extends  Rectangle {
		
		GamerHuman cGamer;
		public GamerHuman getGamer() { return cGamer; }

		RenderBase cRender;
		public RenderBase getRender() { return cRender; }

		Component cDrawComponent = null;
		
		PanelBox( Component   pDrawComponent,
							GamerHuman  pGamer, 
							RenderBase  pRender,
							int pX, int pY, int pWidth, int pHeight ) {
				super( pX, pY, pWidth, pHeight );

				cDrawComponent = pDrawComponent;
				cGamer  = pGamer;
				cRender = pRender;
				cRender.setPanel( this );
		}				


		public void display() {

				AffineTransform lTrans = cRender.getGC().getTransform();

				cRender.getGC().setColor( Color.black );
				cRender.getGC().drawRect( (int)getX()-1, (int)getY()-1, (int)getWidth()+2, (int)getHeight()+2 );

				cRender.getGC().setClip( (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight() );
				cRender.getGC().translate( (int)getX(), (int)getY() );

				cRender.paint( );

				paint(); 

				cRender.getGC().setTransform(lTrans );

				cRender.getGC().setColor( Color.blue );
				cRender.getGC().drawRect( (int)getX()-1, (int)getY()-1, (int)getWidth()+2, (int)getHeight()+2 );

		}
		public void paint() {;}

		public void mousePressed( MouseEvent pEv ) {;}
		public void mouseReleased( MouseEvent pEv ) {;}
		public void mouseClicked( MouseEvent pEv ) {;}
		public void mouseDragged( MouseEvent pEv ){;}
		public void mouseMoved( MouseEvent pEv ){;}
		public	void keyPressed( KeyEvent pEv ){;}
};

//*************************************************
