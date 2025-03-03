package com.phipo.GLib;


import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

//**********************************************************

interface InterfaceDisplayGamer extends ActionListener{
		public void displayBuffer();
		public void drawBuffer();
		public boolean isReadyToDraw();
		public void setDrawReady( Graphics2D pGC  );

		public void beginSelectionPosition();

		//		public InterfaceRender getRenderMini();
		//		public InterfaceRender getRenderMain();
		//		public InterfaceRender getRenderInfoSelect();
		//		public InterfaceRender getRenderOrder();

		public Gamer getGamer();

		//		public Point2D.Double getViewPoint();
}
//**********************************************************
