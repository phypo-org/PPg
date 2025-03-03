package com.phipo.PPg.PPgG3d;


import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.*;


import java.awt.event.*;

import javax.swing.SwingUtilities;
import java.util.concurrent.ConcurrentLinkedQueue;


//***********************************
// La classe qui represente un joueur humain
//***********************************

abstract public  class GamerHuman extends Gamer {

		public GamerHuman( String pName,  int pGamerId, int pGroupId ){
				super( pName, pGamerId, pGroupId );
		}

		boolean isHuman()       { return true; }


		public  ActorMobil      getMyActor() { return null;}
		/*
		Point2D.Double cPosView = new Point2D.Double( 0, 0 );
		public Point2D.Double getViewPoint() { return cPosView;}
		public void           setViewPoint( Point2D.Double pPos) { cPosView = pPos; }
		public void           setViewPoint( double pX, double pY) { 
				System.out.println( "GamerHuman.setLocation x:" + pX + " y:" + pY );
				cPosView.setLocation( pX, pY ); 
		}
		*/
		
		// Dernier point connu de la position de la souris
		protected Point2D.Float cLastPoint = new Point2D.Float(0,0);
		public Point2D.Float getLastPoint() { return cLastPoint; }
		public void setLastPoint( Point2D.Float lPoint) {  cLastPoint = lPoint; }



		// ButtonPressed
		public void actionButton1Pressed( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton2Pressed( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton3Pressed( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		
		// ButtonClicked
		public void actionButton1Clicked( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton2Clicked( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton3Clicked( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}

		// ButtonReleased
		public void actionButton1Released( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton2Released( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}
		public void actionButton3Released( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMousePos;
		}

		// MouseMoved
		public void actionMouseMoved( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) { 
				cLastPoint = pMousePos;
		}

		// MouseDragged
		public void actionMouseDraggedButton1( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) { 
				cLastPoint = pMousePos;
		}
		// MouseDragged
		public void actionMouseDraggedButton2( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) { 
				cLastPoint = pMousePos;
		}
		// MouseDragged
		public void actionMouseDraggedButton3( Point2D.Float pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) { 
				cLastPoint = pMousePos;
		}

		// Rectangle selected
		public void actionSelectRect( Point2D.Float pMinPos, Point2D.Float pMaxPos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
		}

		// Key
		public void actionKeyPress( int pKeyPress, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
		}
		public void actionKeyReleased( int pKeyPress, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ){
		}

		public Point2D.Float getMousePosition() {
				return cLastPoint;
		}
		
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
 
}

		
//*************************************************
