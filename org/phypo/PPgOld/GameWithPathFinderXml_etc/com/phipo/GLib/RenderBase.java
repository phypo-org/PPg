package com.phipo.GLib;

import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;
import java.awt.*;


//**********************************************************
abstract public class RenderBase {

		GamerHuman cGamer = null;
		GamerHuman getGamerHuman() { return cGamer; }

		Graphics2D        cGC = null;
		Graphics2D        getGC() { return cGC; }

		PanelBox          cMyPanel= null;
		void setPanel( PanelBox pPanel ) { cMyPanel = pPanel; }

		public Dimension  cSize = new Dimension(0,0);
		////		Rectangle         cDrawRect = new Rectangle(0, 0, 0, 0);

		public boolean isRedrawReady() { return cGC != null; }
		public boolean isRedrawFinish() { return cGC == null; }

		public void setRedrawFinish() { cGC = null; }
		public void setRedrawReady( Rectangle pDrawRect, Dimension pDim, Graphics2D pGC  ){
				
				//////				cDrawRect.setRect( pDrawRect.getX(), pDrawRect.getY(), pDrawRect.getWidth(), pDrawRect.getHeight());
				cSize.setSize(pDim);
				cGC = pGC;
		}


		double cMagnify = 2;   // Calculer magnify en fonction de la taille de la fenetre et de la taille de la carte 		
		public double getMagnify() { return cMagnify; }


		RenderBase( GamerHuman pGamer ){
				cGamer = pGamer;
		}

		abstract public void paint();
}
//**********************************************************
