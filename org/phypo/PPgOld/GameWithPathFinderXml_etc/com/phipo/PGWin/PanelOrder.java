package com.phipo.GLib;


import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.SwingUtilities;

//*************************************************

final public class PanelOrder extends  PanelBox {


	public PanelOrder( Component pDrawComponent, GamerHuman pGamer, RenderOrder  pRender,
											int pX, int pY, int pWidth, int pHeight){
				super( pDrawComponent, pGamer, pRender, pX, pY, pWidth, pHeight  );
	
		}
		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {
								
				System.out.println( "PanelOrder.mouseClicked" );
				if( SwingUtilities.isLeftMouseButton( pEv ) == true){
																
						cGamer.sendMessage( new GamerMessage( pEv, GamerMessage.Order.ORDER_ENTITY ) );
				}			 
		}
};
//*************************************************
