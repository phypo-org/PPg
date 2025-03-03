package org.phypo.GameSwarm;


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




import org.phypo.PPgGame.PPgGame.*;


//***********************************
public class GamerEvent {

		
		//=====================================
		public enum EventType{
				    Fire1On(  "Fire1On" ),
						Fire1Off( "Fire1Off" ),
						Fire2On(  "Fire2On" ),
						Fire2Off( "Fire1Off" ),
						Warp(     "Warp" );

				private final String cMyName;


				EventType( String pName) {
						cMyName  = pName;
				}

				public String getName()   { return cMyName;  }

		};

		//=====================================
		

		public EventType cEventType; 
		public Point2D.Double cMousePos;
		public Point2D.Double cMousePos2;
		public int            cKey;

		public boolean cFlagCtrl;
		public boolean cFlagShift;
		public boolean cFlagAlt;


		public GamerEvent( GamerEvent.EventType pEventType, 
											 Point2D.Double pMousePos, 
											 Point2D.Double pMousePos2, 
											 int pKey,  
											 boolean pFlagCtrl,
											 boolean pFlagShift, 
											 boolean pFlagAlt ) {
				cEventType = pEventType;
				cMousePos  = pMousePos;
				cMousePos2  = pMousePos2;
				cKey       = pKey;
				cFlagCtrl  = pFlagCtrl;
				cFlagShift = pFlagShift;
				cFlagAlt   = pFlagAlt;
		}
};
//***********************************

