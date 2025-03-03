package org.phypo.PPgGame.PPgGame;


import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

//**********************************************************
// Interface a implementer pour la gestion de la frame
//**********************************************************

public interface InterfaceDisplayGamer extends ActionListener{
		public void    displayBuffer();
		public void    drawBuffer(); // on dessine dans le buffer
		public boolean isReadyToDraw();
		public void    setDrawReady( Graphics2D pGC  );

		public void    beginSelectionPosition();

		//		public InterfaceRender getRenderMini();
		//		public InterfaceRender getRenderMain();
		//		public InterfaceRender getRenderInfoSelect();
		//		public InterfaceRender getRenderOrder();

		//		public GamerHuman getHumanGamer();

		//		public Point2D.Double getViewPoint();
}
//**********************************************************
