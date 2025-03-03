package com.phipo.GLib;


import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.SwingUtilities;
//***********************************
public class GamerMessage{

		public enum Order{
				CURSOR_AT,
				SELECT_AT,
				SELECT_RECT,
				ORDER_AT,
				MENU_AT,
				ORDER_ENTITY,
				ACTION_EVENT,				
				CANCEL
		};

		Order cOrder;
		Rectangle2D.Double cRect;
		boolean           cCtrl;
		boolean           cShift;
		AWTEvent        cEv;

		GamerMessage( AWTEvent pEv, Order pOrder  ){

						cOrder = pOrder;
						cEv    = pEv;
		}
		GamerMessage( AWTEvent pEv, Order pOrder, Point2D.Double pPosition, 
											boolean pCtrl, boolean pShift ){
						cOrder = pOrder;
						if( pPosition != null )
								cRect = new Rectangle2D.Double( pPosition.getX(), pPosition.getY(), 0, 0 );

						cCtrl  = pCtrl;
						cShift = pShift;
						cEv    = pEv;
				}
		GamerMessage( AWTEvent pEv, Order pOrder, Rectangle2D.Double pRect, 
											boolean pCtrl, boolean pShift ){
						cOrder = pOrder;
						if( pRect != null )
								cRect  = new Rectangle2D.Double( pRect.getX(), pRect.getY(), pRect.getWidth(), pRect.getHeight() );

						cCtrl  = pCtrl;
						cShift = pShift;
						cEv    = pEv;
				}
		};
//***********************************
