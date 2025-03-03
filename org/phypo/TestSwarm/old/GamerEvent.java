package org.phypo.PPg.PPgTestSwarm;


import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;

import java.io.*;
import java.io.PrintStream;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.WindowConstants;

import java.awt.geom.Point2D;
import java.awt.event.*;




import org.phypo.PPg.PPgGame.*;


//***********************************
public class GamerEvent {

		
		//=====================================
		public enum EventType{
				Fire1( "Fire1" );

				private final String cMyName;


				EventType( String pName) {
						cMyName  = pName;
				}

				public String getName()   { return cMyName;  }

		};

		//=====================================
		

		public EventType cEventType; 
		public Point2D.Double cMousePos;
		public int            cKey;

		public boolean cFlagCtrl;
		public boolean cFlagShift;
		public boolean cFlagAlt;


		public GamerEvent( GamerEvent.EventType pEventType, 
											 Point2D.Double pMousePos, 
											 int pKey,  
											 boolean pFlagCtrl,
											 boolean pFlagShift, 
											 boolean pFlagAlt ) {
				cEventType = pEventType;
				cMousePos  = pMousePos;
				cKey       = pKey;
				cFlagCtrl  = pFlagCtrl;
				cFlagShift = pFlagShift;
				cFlagAlt   = pFlagAlt;
		}
};
//***********************************

