package com.phipo.PPg.M0k4.Sprites;



import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;

import java.io.*;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.WindowConstants;

import java.awt.geom.Point2D;
import java.awt.event.*;



import com.phipo.PPg.PPgGame.*;

//***********************************
public class MyGamer extends GamerHuman {


		//=====================================

		//		SwarmMouse cMySwarm = null;

		MyGamer(  String pName, InterfaceDisplayGamer pDisplayGamer, int pGamerId, int pGroupId ){
				super( pName, pDisplayGamer, pGamerId, pGroupId );

		}
		
		//------------------------------------------------
		// appeler pa le thread de swing

	 		public void actionButton1Pressed( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {

					//	super.actionButton1Pressed(pMousePos,pFlagCtrl,pFlagShift,pFlagAlt);

					addGamerEvent( new GamerEvent( GamerEvent.EventType.Fire1, pMousePos, 0, pFlagCtrl,pFlagShift,pFlagAlt) );

					//					cMySwarm.fire1();
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		
		ConcurrentLinkedQueue<GamerEvent> cMyEventQueue = new ConcurrentLinkedQueue<GamerEvent>();
		
		void addGamerEvent( GamerEvent pEvent ) {
				cMyEventQueue.add( pEvent );
		}

		//------------------------------------------------
		// Appeler par le thread de World

		public void worldCallExecOrder() {

				GamerEvent pEvent;

				while( (pEvent = cMyEventQueue.poll()) != null ){

						execOneOrder( pEvent );
				}
		}
		//------------------------------------------------
		void execOneOrder( GamerEvent  pEvent ) {
				switch( pEvent.cEventType ){

				case Fire1:
						//						cMySwarm.fire1();
						break;
				}
		}
		
};
//***********************************		