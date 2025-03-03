package com.phipo.PPg.M0k4.Sprites;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


import com.phipo.PPg.PPgWin.*;
import com.phipo.PPg.M0k4.*;

import com.phipo.PPg.PPgGame.*;
import com.phipo.PPg.PPgSFX.*;

//*************************************************
public class Sprites extends M0k4_Gadget {
		
		
		//=====================================
		class MyGame extends FrameGamer {


				public MyGame( JDialog pDialog, String[] pArgs){
						super( pDialog, null,  "Sprite", pArgs );
						
						setTranslucent();

						MyGamer lGamer = new MyGamer("Test", this,  1, 1 );
						System.out.println(" SimplePanel "+ cWidth + " " +  cHeight );

						PanelBox lPanelBox = new SimplePanel( lGamer, 0,0, cWidth, cHeight );
						System.out.println(" SimplePanel 2" );

						lPanelBox.setTranslucent();

						addPanelBox( lPanelBox  );
						System.out.println(" SimplePanel 3" );


						MyWorld lWorld = new MyWorld(  cWidth, cHeight);

						System.out.println(" SimplePanel 4" );

						
						lWorld.addGamerHuman( lGamer );
						System.out.println(" SimplePanel 5" );

						initDisplayBuffer();				
						System.out.println(" SimplePanel 6" );
						displayBuffer();
						System.out.println(" SimplePanel 7" );

						//	setMyBackgroundColor(new Color(0, 0, 0, 1));
						//setOpacity(0.9f);

		}
		}
		//=====================================
		
		MyGame cMyGame;
		
		public boolean   sAlwaysOnTop = false;
		public boolean   sTranslucent = false;
		public Point     sLocation = new Point( 10, 10 );
		public Dimension sDimension  = new Dimension( 1000, 900 );
		public float     sOpacity  = 0.65f;


	public Sprites() {
				super();
		}

		//------------------------------------------------
		public void gadgetInitialize() {			 	

							System.out.println(" gadgetInitialize Begin ");


				if( PPgWinUtils.IsTranslucentSupported() == false ){
						System.err.println("Shaped windows are not supported");
						// System.exit();
				} else sTranslucent = true;


				getContentPane().setLayout( new BorderLayout() );		
				
				setUndecorated( true );
				setAlwaysOnTop(sAlwaysOnTop);
				
 				setLocation(sLocation);
				setSize( sDimension );

				
				
				//				if( sTranslucent ){

				//		setBackground(new Color(0,0,0,0));
				//				}
			

				
															
				String[] lArgs = { "","","" };
				cMyGame = new MyGame( this, lArgs);

				//		World.Get().setFlagCollision( (byte)(EnumFaction.Blue.getCode() + EnumFaction.Green.getCode()),  true );
				
				System.out.println(" after create game 1 " );
		  						
				ParticleEngine lPart;
				
				lPart =ParticleSimple.CreateParticleFactoryFire( 200, 500, 1000, 100  );
				World.Get().addActor( lPart );

				lPart =ParticleSimple.CreateParticleFactorySmoke( 200, 450, 1000, 100 );
				World.Get().addActor( lPart );
				
				lPart =ParticleSimple.CreateParticleFactoryExplosion( 400, 400, 1000, 0, 5 );
				World.Get().addActor( lPart );

				lPart =ParticleSimple.CreateParticleFactoryStar( 500, 400,  500, 100 );
				World.Get().addActor( lPart );
				
				lPart =ParticleSimple.CreateParticleFactorySpark( 600, 400, 300,  100, 0.7f, 0.9f, 1.0f, 50.0, 3.0 );
				World.Get().addActor( lPart );
				

				lPart =ParticleSimple.CreateParticleFactoryFragment( 500, 300, 1000,  10, 0.3f, 0.3f, 1.0f, 40.0, 7.0 );
				World.Get().addActor( lPart );
								
				World.Get().sDebugFps = true;
				System.out.println(" World.Get().start() ");
								
				World.Get().start();
																
				
				System.out.println(" gadgetInitialize End ");
		}
		//------------------------------------------------
		public void     gadgetOpenDialogOption() { ; }
}
				

//*************************************************


				