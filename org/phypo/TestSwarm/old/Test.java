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

import org.phypo.PPg.PPgUtils.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;

//***********************************
public class Test extends FrameGamer {

		Test(	Point2D.Double pPosViewInit, boolean  pFlagFullScreen, boolean  pFlagDeco, int pWidth, int pHeight, int pDepth ) {
				super( null, new JFrame( "Swarm"), "Swarm", pPosViewInit, pFlagFullScreen, pFlagDeco, pWidth, pHeight, pDepth );

				MyGamer lGamer = new MyGamer("Test", this,  1, 1 );

				PanelBox lPanelBox = new SimplePanel( lGamer, 0,0, pWidth, pHeight );
				addPanelBox( lPanelBox  );
				
				MyWorld lWorld = new MyWorld( pWidth, pHeight ); //new MyWorld( getBounds().width, getBounds().height );

				lWorld.addGamerHuman( lGamer );

				
				double lMaxAttract = 150;

				SwarmMouse lSwarm1 = new SwarmMouse( "Blue", lGamer, Color.blue, 
																	 30, 500,400,50,
																	 0.003,  // TargetAttrack
																	 0.002,   // CohesionAttrack
																	 0.003,   // AlignAttrack
																	 0.5, // SeparationRepuls
																	 15, // VitesseMax
																	 300, // distanceMax) ;
																	 35, // Evitement
																				true, lMaxAttract, 1.0 );

				//			lSwarm1.setFaction( EnumFaction.Blue );
				lGamer.setMySwarm( lSwarm1 );
				lWorld.addActor( lSwarm1  );




				Random lRand = new Random();
				for( int i=0; i< 5; i++) {
						Swarm lSwarm2 = new SwarmAleaTarget("Green",   Color.green,
																								150, 300.0+lRand.nextFloat()*300.0, 300.0+lRand.nextFloat()*400.0, 50,
																								0.003,  // TargetAttrack
																								0.002,   // CohesionAttrack
																								0.002,   // AlignAttrack
																								0.4, // SeparationRepuls
																								15, // VitesseMax
																								1000, // distanceMax) ;
																								20, // Evitement
																								true, lMaxAttract, 0.5 );
						
						Swarm lSwarm3 = new SwarmAleaTarget( "Yellow",  Color.yellow, 
																								 80, 300.0+lRand.nextFloat()*300.0, 300.0+lRand.nextFloat()*400.0,50,
																								 0.004,  // TargetAttrack
																								 0.003,   // CohesionAttrack
																								 0.005,   // AlignAttrack
																								 0.6, // SeparationRepuls
																								 25, // VitesseMax
																								 1000, // distanceMax) ;
																								 10, // Evitement
																								 true, lMaxAttract, 0.1 );
						
						lSwarm2.setFaction( EnumFaction.Green );
						lSwarm3.setFaction( EnumFaction.Green );

						lSwarm2.setAvoidActorLocation( World.Get().getActorLocation() );
		
						lWorld.addActor( lSwarm2  );
						lWorld.addActor( lSwarm3  );
				}

				
				/*
				Swarm lSwarm4 = new SwarmMouse( "Red",  Color.red,
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
				Swarm lSwarm5 = new SwarmMouse( "Pink",  Color.pink,
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


				/*
				Swarm lSwarm5 = new SwarmFlux( 7, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );
				Swarm lSwarm6 = new SwarmFlux( 5, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm7 = new SwarmFlux( 4, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm8 = new SwarmFlux( 3, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm9 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm10 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm11 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );
				Swarm lSwarm12 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm13 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );
				Swarm lSwarm14 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );

				Swarm lSwarm15 = new SwarmFlux( 2, "Black",  new Color( 200, 200, 255 ),
																	 150, 500,400,50,
																	 0.001,  // TargetAttrack
																	 0.001,   // CohesionAttrack
																	 0.001,   // AlignAttrack
																	 2.0, // SeparationRepuls
																	 5, // VitesseMax
																	 50, // distanceMax) ;
																	 200, // Evitement
																	 true, 2000, 0.3 );


				FrameGamer.Get().GetGamePanel().setMyBackgroundColor( Color.black );
				//				setBackground( Color.black );
				for( int x=250; x<600; x+=20 ){						
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 100 ) );
						Swarm.AddAvoidPoint(	 new Point2D.Double( x, 170 ) );
				}

								setBackground( Color.black );
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
				*/
				Swarm[] lTabSwarm = { lSwarm1 } ;

				//															lSwarm5, lSwarm6, lSwarm7,  lSwarm8, lSwarm9,lSwarm10,
				//															lSwarm11, lSwarm12, lSwarm13,  lSwarm14, lSwarm15 };
		/*

				//		lWorld.add( lSwarm4 );

				//				lWorld.add( lSwarm5  );
				lWorld.addActor( lSwarm5 );
				lWorld.addActor( lSwarm6 );
				lWorld.addActor( lSwarm7 );
				lWorld.addActor( lSwarm8 );
				lWorld.addActor( lSwarm9 );
				lWorld.addActor( lSwarm10 );
				lWorld.addActor( lSwarm11 );
				lWorld.addActor( lSwarm12 );
				lWorld.addActor( lSwarm13 );
				lWorld.addActor( lSwarm14 );
				lWorld.addActor( lSwarm15 );
				*/

				initDisplayBuffer();				
				displayBuffer();


					new DialogPref( lTabSwarm, 1 );
				

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
				
				World.Get().setFlagCollision( (byte)(EnumFaction.Blue.getCode() + EnumFaction.Green.getCode()),  true );
				


				/*
				ParticleEngine lPart;

				lPart =ParticleSimple.CreateParticleFactoryFire( 200, 700, 1000, 100  );
				World.Get().addActor( lPart );

				lPart =ParticleSimple.CreateParticleFactorySmoke( 200, 650, 1000, 100 );
				World.Get().addActor( lPart );
				
				lPart =ParticleSimple.CreateParticleFactoryExplosion( 400, 600, 1000, 0, 5 );
				World.Get().addActor( lPart );

				lPart =ParticleSimple.CreateParticleFactoryStar( 500, 600,  500, 100 );
				World.Get().addActor( lPart );
				
				lPart =ParticleSimple.CreateParticleFactorySpark( 600, 600, 300,  100, 0.7f, 0.9f, 1.0f, 50.0, 3.0 );
				World.Get().addActor( lPart );
				

				lPart =ParticleSimple.CreateParticleFactoryFragment( 500, 500, 1000,  10, 0.3f, 0.3f, 1.0f, 40.0, 7.0 );
				World.Get().addActor( lPart );
				*/



				World.Get().start();
				

				/*			try {
						while( true )
								sleep(100);										
				}catch(Exception e){ }
				*/
		}
}
//***********************************
