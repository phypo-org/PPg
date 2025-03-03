package org.phypo.TestSwarm2;



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

import org.phypo.PPg.PPgUtils.*;


import org.phypo.PPgGame.PPgGame.*;

//***********************************
public class Test extends FrameGamer {

		Test(	Point2D.Double pPosViewInit, boolean  pFlagFullScreen, boolean  pFlagDeco, int pWidth, int pHeight, int pDepth ) {
				super(  null,  new JFrame( "Swarm"), "Swarm", pPosViewInit, pFlagFullScreen, pFlagDeco, pWidth, pHeight, pDepth );

			  MyGamer lGamer = new MyGamer("Test", this,  1, 1 );
				PanelBox lPanelBox = new SimplePanel( lGamer, 0,0, pWidth, pHeight );
				addPanelBox( lPanelBox  );
				
				MyWorld lWorld = new MyWorld( pWidth, pHeight );
				
				lWorld.addGamerHuman( lGamer );


				double lMaxAttract = 150;

				Swarm lSwarm1 = new SwarmMouse( "Blue", lGamer, Color.blue, 
																	 500, 500,400,50,
																	 0.003,  // TargetAttrack
																	 0.002,   // CohesionAttrack
																	 0.003,   // AlignAttrack
																	 0.3, // SeparationRepuls
																	 15, // VitesseMax
																	 30, // distanceMax) ;
																	 5, // Evitement
																	 true, lMaxAttract );
				
				Swarm lSwarm2 = new SwarmMouse("Green",  lGamer, Color.green,
																	 500, 500,400,50,
																	 0.003,  // TargetAttrack
																	 0.002,   // CohesionAttrack
																	 0.002,   // AlignAttrack
																	 0.2, // SeparationRepuls
																	 15, // VitesseMax
																	 80, // distanceMax) ;
																	 20, // Evitement
																	 true, lMaxAttract );

				Swarm lSwarm3 = new SwarmMouse( "Yellow", lGamer, Color.yellow, 
																	 500, 500,400,50,
																	 0.004,  // TargetAttrack
																	 0.003,   // CohesionAttrack
																	 0.005,   // AlignAttrack
																	 0.1, // SeparationRepuls
																	 25, // VitesseMax
																	 40, // distanceMax) ;
																	 10, // Evitement
																	 true, lMaxAttract );
				/*
				Swarm lSwarm4 = new SwarmMouse( "Red", lGamer, Color.red,
																	 500, 500,400,50,
																	 0.002,  // TargetAttrack
																	 0.004,   // CohesionAttrack
																	 0.004,   // AlignAttrack
																	 0.5, // SeparationRepuls
																	 25, // VitesseMax
																	 60, // distanceMax) ;
																	 25, // Evitement
																	 true, lMaxAttract );
				*/
				/*
				Swarm lSwarm5 = new SwarmMouse( "Pink", lGamer, Color.pink,
																	 500, 500,400,50,
																	 0.003,  // TargetAttrack
																	 0.005,   // CohesionAttrack
																	 0.006,   // AlignAttrack
																	 0.5, // SeparationRepuls
																	 25, // VitesseMax
																	 60, // distanceMax) ;
																	 25, // Evitement
																	 true, lMaxAttract );
				*/
				Swarm lSwarm6 = new SwarmFlux( "Black", lGamer, new Color( 200, 200, 255 ),
																	 500, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000 );


				FrameGamer.Get().setMyBackgroundColor( Color.black );
				//				setBackground( Color.black );
				for( int x=250; x<600; x+=20 ){						
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 100 ) );
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 170 ) );
				}

				//				setBackground( Color.black );
				for( int x=250; x<600; x+=20 ){						
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 250 ) );
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 300 ) );
				}

				for( int y= 0; y<700; y+=20 ){
						Swarm.AddAvoidPoint(	 new Point2D.Double( 760, y ) );
						Swarm.AddAvoidPoint(	 new Point2D.Double( 700, y ) );						
				}
				Swarm.AddAvoidPoint(	 new Point2D.Double( 760, 720 ) );
				Swarm.AddAvoidPoint(	 new Point2D.Double( 760, 730 ) );
				Swarm.AddAvoidPoint(	 new Point2D.Double( 760, 760 ) );
				Swarm.AddAvoidPoint(	 new Point2D.Double( 760, 790 ) );

				for( int y= 50; y<600; y+=10 ){
						Swarm.AddAvoidPoint(	 new Point2D.Double( 40, y ) );
						Swarm.AddAvoidPoint(	 new Point2D.Double( 90, y ) );						
				}
						
				Swarm.AddAvoidPoint(	 new Point2D.Double( 300, 300 ) );
				Swarm.AddAvoidPoint(	 new Point2D.Double( 400, 200 ) );
				//				Swarm.AddAvoidPoint(	 new Point2D.Double( 500, 200 ) );
								
		 				Swarm.AddAttractPoint(  new Point2D.Double( 600, 700 ) );

				//				lSwarm2.addAvoidPoint(	 new Point2D.Double( 400, 200 ) );
								
	 			Swarm.AddAttractPoint(	 new Point2D.Double( 400, 700 ) );

				Swarm[] lTabSwarm = { lSwarm1, lSwarm2, lSwarm3,   lSwarm6 };


				lWorld.addVirtualActor( lSwarm1  );
				lWorld.addVirtualActor( lSwarm2 );

				lWorld.addVirtualActor( lSwarm3  );
				//		lWorld.add( lSwarm4 );

				//				lWorld.add( lSwarm5  );
				lWorld.addVirtualActor( lSwarm6 );


				initDisplayBuffer();				
				displayBuffer();

				new DialogPref( lTabSwarm, 4 );
				

		}
		//---------------------------------
		//---------------------------------
		//---------------------------------

		public static void main(String[] args) {
				
				Integer lVerbose  = PPgParam.GetInteger( args, "-v", 0 );
				
				
				///				IniParam.ReadIni(lIniObj);
				//				World.sFurtifMode = ExistParam( args, "--F" );
				//World.sDebug      = ExistParam( args, "--G" );

				
				int lW      = PPgParam.GetInteger( args, "-w", 1000 );
				int lH      = PPgParam.GetInteger( args, "-h", 800 );

				int lDepth  = 0;

				boolean lFlagDeco = !PPgParam.ExistParam( args, "-D" );
				

				boolean lFlagFullScreen = false;
				String lStrFullScreen = PPgParam.GetString( args, "-S", null );


				if(lStrFullScreen != null ){

						lFlagFullScreen = true;
						if( lStrFullScreen.compareTo( "800x600-8" )==0 ){
								lW = 800;
								lH = 600;
								lDepth = 8;
						}
						else
						if( lStrFullScreen.compareTo( "1280x1024-32" )==0 ){
								lW = 1280;
								lH = 1024;
								lDepth = 32;
						}
						else {
								// garde le mode courant !!!
								lW=0;
								lH=0;
								lDepth = 0;
						}								
				}



				String lFileXml = PPgParam.GetString( args, "-X", null );

				new Test( new Point2D.Double( 200, 200 ), lFlagFullScreen, lFlagDeco, lW, lH, lDepth );				
				
				World.Get().start();

				/*			try {
						while( true )
								sleep(100);										
				}catch(Exception e){ }
				*/
		}
}
//***********************************
