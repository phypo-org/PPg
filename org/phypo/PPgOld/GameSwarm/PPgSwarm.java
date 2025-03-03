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

import org.phypo.PPg.PPgUtils.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;

//***********************************
public class PPgSwarm extends FrameGamer {

		static boolean sTraceFps = false;
	 
		public PPgSwarm( String[] args  ){
					super( null,  new JFrame( "Swarm"),  "Swarm", args );
				//	super( new JDialog(), null,  "Swarm", args );

					//			setTranslucent();

				MyGamer lGamer = new MyGamer("Test", this,  1, 1 );

			

				PanelBox lPanelBox = new SimplePanel( lGamer, 0,0, cWidth, cHeight );
				//lPanelBox.setTranslucent();
					
				addPanelBox( lPanelBox  );

				//				MyWorld lWorld = new MyWorld( pWidth, pHeight ); //new MyWorld( getBounds().width, getBounds().height );

				MyWorld lWorld = new MyWorld(  cWidth, cHeight);
				if( sTraceFps ) 
						lWorld.sDebugFps = true;


				lWorld.addGamerHuman( lGamer );

			 				
				initDisplayBuffer();				
				displayBuffer();
		}


		/*
		PPgSwarm(	Point2D.Double pPosViewInit, boolean  pFlagFullScreen, boolean  pFlagDeco, int pWidth, int pHeight, int pDepth ) {
				super( "Swarm", pPosViewInit, pFlagFullScreen, pFlagDeco, pWidth, pHeight, pDepth );

				MyGamer lGamer = new MyGamer("Test", this,  1, 1 );

				PanelBox lPanelBox = new SimplePanel( lGamer, 0,0, pWidth, pHeight );
				addPanelBox( lPanelBox  );

				MyWorld lWorld = new MyWorld( pWidth, pHeight ); //new MyWorld( getBounds().width, getBounds().height );

				if( sTraceFps ) 
						lWorld.sDebugFps = true;


				lWorld.addGamerHuman( lGamer );

			 				
				initDisplayBuffer();				
				displayBuffer();

				//	new DialogPref( lTabSwarm, 4 );
				

		}
		*/
		//---------------------------------
		//---------------------------------
		//---------------------------------

		public static void main(String[] args) {
				
				/*		Integer lVerbose  = PPgParam.GetInteger( args, "-v", 0 );
				
				init
				///				IniParam.ReadIni(lIniObj);
				//				World.sFurtifMode = ExistParam( args, "--F" );
				//World.sDebug      = ExistParam( args, "--G" );

				
				int lW      = PPgParam.GetInteger( args, "-w", 1000 );
				int lH      = PPgParam.GetInteger( args, "-h", 800 );


				int lDepth  = 0;

				boolean lFlagDeco = !PPgParam.ExistParam( args, "-D" );
				
				PPgSwarm.sTraceFps = PPgParam.ExistParam( args, "-fps" );

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
				*/

				int lBeginLevel  = PPgParam.GetInteger( args, "-L", 0 );
				World.OpenIniFile( "Config.ini" );
				
				
				String lFileXml = PPgParam.GetString( args, "-X", null );

				Ressources.Load();
				

				//				PPgSwarm lGame = new PPgSwarm( new Point2D.Double( 200, 200 ), lFlagFullScreen, lFlagDeco, lW, lH, lDepth );
				PPgSwarm lGame = new PPgSwarm( args );
				//	lGame.initFromParam( args );
				
				FactoryLevel lFactory = new FactoryLevel( 0 );

				if( lBeginLevel != 0 )
						lFactory.setBeginLevel( lBeginLevel );

				World.Get().setFlagCollision( (byte)(EnumFaction.Blue.getCode() + EnumFaction.Green.getCode()),  true );

				
				World.Get().start();				
		}
}
//***********************************
