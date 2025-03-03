package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;

import javax.swing.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************
// Classe de base de tout ce qui va vivre
// et dans le programme

class CommandIcon extends Rectangle2D.Double {
		
		public enum OrderCommandIcon{
				VOID(""),
				CONSTRUCT("Construct"),
				DESTROY("Destroy"),
				EVOLUTION("Evolution"),
				GUARD("Guard"),
				STOP("Stop");

				String cName=null;
				public String getName() { return cName; }
				OrderCommandIcon( String pName ){ cName = pName;}
		};

		Entity  cEntity          = null;
		OrderCommandIcon  cOrder = OrderCommandIcon.VOID;
		OrderCommandIcon getOrder() { return cOrder;}

		String  cOrderStr        = null;
		ImageIcon cIcon = null;
		ImageIcon getIcon() { return cIcon; }

		PrototypeUnit cPrototype = null;
		PrototypeUnit getPrototype() { return cPrototype; }

		PanelBox  cPanel;
		PanelBox  getPanel() { return cPanel; }

		public CommandIcon( PanelBox pPanel, Entity pEntity, OrderCommandIcon pOrder, PrototypeUnit pPrototype, ImageIcon pIcon ){
				cEntity = pEntity;
				cOrder  = pOrder;
				cPrototype = pPrototype;
				cIcon = pIcon;
				cPanel = pPanel;
		}
}

//*************************************************
