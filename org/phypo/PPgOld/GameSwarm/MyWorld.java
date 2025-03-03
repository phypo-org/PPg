package org.phypo.PPg.PPgSwarm;

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



import org.phypo.PPg.PPgGame.*;


//***********************************
public class MyWorld extends World {

	 
		double cWidth;
		double cHeight;

		public MyWorld( int pWidth, int pHeight ) {
				cWidth = pWidth;
				cHeight = pHeight;
		}

		public double getWidth()  { return cWidth;}
		public double getHeight() { return cHeight;}


		int cLevel =6;


		//------------------------------------------------
		public void worlCallBeginRun()  { 
				
				
				double lMaxAttract = 150;
				
				FleetGamer lFleet1 = new FleetGamer( "Blue", World.Get().getGamerHuman(), Color.blue, 
																						 0, 500, 400, 50,
																						 0.008,  // TargetAttrack
																						 0.0002,   // CohesionAttrack
																						 0.003,   // AlignAttrack
																						 15, // SeparationRepuls
																						 50, // VitesseMax
																						 10000, // distanceMax) ;
																						 50, // Evitement
																						 true, lMaxAttract, 1.0 );
				
				lFleet1.setFaction( EnumFaction.Blue );
				((MyGamer)World.Get().getGamerHuman()).setMyFleet( lFleet1 );
				World.Get().addActor( lFleet1  );
				
				
				lFleet1.addBoid( new ShipLaser  ( Ressources.sMyShipLaserImg,   0, 0 ) );
				lFleet1.addBoid( new ShipMissile( Ressources.sMyShipMissileImg, 0, 0 ) );
				lFleet1.addBoid( new ShipField  ( Ressources.sMyShipFieldImg,   0, 0 ) );

				
				// The first level !
				World.Get().addVirtualActor( FactoryLevel.sTheFactoryLevel.getCurrentLevel() );				


				//		lFleet1.addBoid( new ShipMissile( , 0, 0 ) );
				//		lFleet1.addBoid( new ShipField( ,  0, 0 ) );
				//		lFleet1.addBoid( new ShipField( , 0, 0 ) );
		}		
		
		//-----------------------------------------------------------
		public void worldExternalCallEndLevel( ActorBase pCaller, boolean pGamerFail ) {

				if( pGamerFail ){
						System.out.println( " ******************** GAME OVER ********************** " );
						//						System.exit(0);
				}
				
				System.out.println( " ******************** worldExternalCallEndLevel ********************** " + pCaller  );
				//			System.exit(1);

				LevelBase lLevel=  FactoryLevel.sTheFactoryLevel.nextLevel();				
				World.Get().addVirtualActor( lLevel  );   


				Random lRand = new Random();
				Fleet lMyFleet= ((MyGamer)World.Get().getGamerHuman()).getMyFleet();

				lMyFleet.addBoid( new ShipLaser(   Ressources.sMyShipLaserImg,   0, 0 ) );
				lMyFleet.addBoid( new ShipField(   Ressources.sMyShipFieldImg,   0, 0 ) );
				lMyFleet.addBoid( new ShipMissile( Ressources.sMyShipMissileImg, 0, 0 ) );
		} 
}
//***********************************
