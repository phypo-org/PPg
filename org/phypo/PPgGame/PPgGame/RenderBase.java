package org.phypo.PPgGame.PPgGame;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;
import java.awt.*;



//**********************************************************
// Fonctions de base a implementer pour le rendu dans une fenetre
//**********************************************************
abstract public class RenderBase {



		protected GamerHuman cGamer   = null;
		protected Graphics2D cGC      = null;
		protected PanelBox   cMyPanel = null;

		public RenderBase( GamerHuman pGamer, PanelBox pPanel  ){
				cGamer   = pGamer;
				cMyPanel = pPanel;
		}

		abstract public double getMagnify();
		abstract public void paint( Graphics2D pGC );
};
//**********************************************************
