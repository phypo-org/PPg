package org.phypo.PPg.PPgSwarm;



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



import org.phypo.PPg.PPgGame.*;

//***********************************
public class MyGamer extends GamerHuman {


		//=====================================

		FleetGamer cMyFleet = null;

		MyGamer(  String pName, InterfaceDisplayGamer pDisplayGamer, int pGamerId, int pGroupId ){
				super( pName, pDisplayGamer, pGamerId, pGroupId );

		}
		
		//------------------------------------------------
		public void       setMyFleet( FleetGamer pFleetGamer  )  { cMyFleet = pFleetGamer; }
		public FleetGamer getMyFleet( )  { return cMyFleet; }

		//------------------------------------------------
		// appeler pa le thread de swing

	 		public void actionButton1Pressed( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {

					//	super.actionButton1Pressed(pMousePos,pFlagCtrl,pFlagShift,pFlagAlt);

					addGamerEvent( new GamerEvent( GamerEvent.EventType.Fire1On, pMousePos, null, 0, pFlagCtrl,pFlagShift,pFlagAlt) );

					//					cMyFleet.fire1();
		}
		//------------------------------------------------
	 		public void actionButton1Released( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {

					//	super.actionButton1Pressed(pMousePos,pFlagCtrl,pFlagShift,pFlagAlt);

					addGamerEvent( new GamerEvent( GamerEvent.EventType.Fire1Off, pMousePos, null, 0, pFlagCtrl,pFlagShift,pFlagAlt) );

					//					cMyFleet.fire1();
		}
		//------------------------------------------------
		public void actionButton2Pressed( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				
				//	super.actionButton1Pressed(pMousePos,pFlagCtrl,pFlagShift,pFlagAlt);
				
				addGamerEvent( new GamerEvent( GamerEvent.EventType.Fire2On, pMousePos, null, 0, pFlagCtrl,pFlagShift,pFlagAlt) );

					//					cMyFleet.fire1();
		}
		//------------------------------------------------
	 		public void actionButton2Released( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {

					//	super.actionButton1Pressed(pMousePos,pFlagCtrl,pFlagShift,pFlagAlt);

					addGamerEvent( new GamerEvent( GamerEvent.EventType.Fire2Off, pMousePos, null, 0, pFlagCtrl,pFlagShift,pFlagAlt) );

					//					cMyFleet.fire1();
		}
		//------------------------------------------------
		public void actionSelectRect( Point2D.Double pMinPos, Point2D.Double pMaxPos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) {
				cLastPoint = pMaxPos;
				addGamerEvent( new GamerEvent( GamerEvent.EventType.Warp, pMinPos, pMaxPos, 0, pFlagCtrl,pFlagShift,pFlagAlt) );				
		}
		//------------------------------------------------
		public void actionMouseMoved( Point2D.Double pMousePos, boolean pFlagCtrl, boolean pFlagShift, boolean pFlagAlt ) { 
				cLastPoint = pMousePos;
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

		public void worldCallExecOrder( double pTimeDiff) {

				GamerEvent pEvent;

				while( (pEvent = cMyEventQueue.poll()) != null ){

						execOneOrder( pEvent, pTimeDiff );
				}
		}
		//------------------------------------------------
		void execOneOrder( GamerEvent  pEvent, double pTimeDiff ) {
				switch( pEvent.cEventType ){

				case Fire1On:
						cMyFleet.gamerCallFire1On();
						break;
				case Fire1Off:
						cMyFleet.gamerCallFire1Off();
						break;
				case Fire2On:
						cMyFleet.gamerCallFire2On();
						break;
				case Fire2Off:
						cMyFleet.gamerCallFire2Off();
						break;
				case Warp:
						cMyFleet.gamerCallWarp( pEvent.cMousePos2 );
						break;
				}
		}
};
//***********************************		