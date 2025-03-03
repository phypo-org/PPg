package com.phipo.GLib;



import org.w3c.dom.*;



import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;

import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;


//*************************************************
public class World  extends Thread {

    static double [] sVectFps= new double[20];

		public static double sCurrentTime=0;
		

		public static Point2D.Double sSizeWinMiniMap    = new Point2D.Double(100,100);
		public static Point2D.Double sSizeWinInfoSelect = new Point2D.Double(200,100);
		public static Point2D.Double sSizeWinOrder      = new Point2D.Double(200,100);

		public static int sSizeSmallIcon = 32;
		public static int sSizeTinyIcon = 16;
		public static int sSizeIcon = 64;

		public static ImageIcon sIconDestroy = null;
		public static ImageIcon sSmallIconDestroy = null;
		public static ImageIcon sIconGuard = null;
		public static ImageIcon sSmallIconGuard = null;
		public static ImageIcon sIconRepair = null;
		public static ImageIcon sSmallIconRepair = null;
		public static ImageIcon sIconStop = null;
		public static ImageIcon sSmallIconStop = null;

		public static boolean sDrawHealthBar = true;

		//-------------- Debug -------------
		public static boolean sFurtifMode=false;

		public static boolean sDebug    =false;
		public static boolean sDebugPath=false;
		public static boolean sDebugZone=false;
		public static boolean sDebugVision=false;

		// --------------------------------

		public static double sGameTimeAcceleration = 1.0;
		public static Color  sBagroundColor = Color.white;

		public static double sGeneralScale         = 1.0;
		public static final HashMap<String, Integer> cHashAlliance = new HashMap<String,Integer>();
		
		//================
		public enum MiddleType{
				AIR_TYPE   ( "Air", 1 ),
				GROUND_TYPE( "Ground", 2 ),
				AIR_GROUND_TYPE( "Air,Ground", 3 ),
				WATER_TYPE( "Water", 4 ),
				AIR_WATER_TYPE( "Air,Water", 5 ),
				GROUND_WATER_TYPE( "Ground,Water", 6 ),
				AIR_GROUND_WATER_TYPE( "Air,Ground,Water", 7),
				SUBMARINE_TYPE( "Submarine", 8 ),
				WATER_SUBMARINE_TYPE( "Water,Submarine", 12 ),
				ALL_MIDDLE_TYPE( "All", 15 );
				
				
				String cName = "NoName";
				int    cCode;
				
				public String getName() { return cName; }
				public int    getCode() { return cCode; }

				MiddleType( String pName, int pCode ) { 
						cName = pName;
						cCode = pCode;
				}
				
				public static final HashMap<String, MiddleType> cHashMiddleType = new HashMap<String,MiddleType>();
				static {
						for( MiddleType  lMiddleType: MiddleType.values() ) {
								cHashMiddleType.put( lMiddleType.cName,  lMiddleType);
						}
				}
				
				static public MiddleType FindByName( String pName ) { return cHashMiddleType.get( pName );
				}
		}
		//================

		String cName= "NoName";
		static DecorCarte sDecorCarte=null;
		static public DecorCarte GetDecorCarte() { return sDecorCarte; }

		static PathCarte sPathCarte=null;
		static public PathCarte GetPathCarte() { return sPathCarte; }

		static double lDivFPS;
		public static double getDivFPS() { return lDivFPS; }

		public static World sTheWorld = null;


		boolean  cContinue;

		static public void   Stop() {
				sTheWorld.cContinue = false;
		}

		boolean  cPause = false;

		public static void FlipPause(){
				if( sTheWorld.cPause )
						sTheWorld.cPause = false;
				else
						sTheWorld.cPause = true;						
		}
		public static boolean IsPause() { return  sTheWorld.cPause;}

		//		ArrayList<Entity>    cVectEntity    = new ArrayList<Entity>();
		ArrayList<Entity>    cVectDestroyEntity    = new ArrayList<Entity>();
		HashSet<Entity>      cHashEntity    = new HashSet<Entity>();

		public void addEntity( Entity pEntity ){
				//				cVectEntity.add(  pEntity );
				cHashEntity.add( pEntity );
				if( sPathCarte.setEntity( pEntity, pEntity.getMX(),  pEntity.getMY()) == false )
						System.err.println("*** Error World.addEntity  -> Collision" );

		}
		public void subEntity( Entity pEntity ){
				//				cVectEntity.remove(  pEntity );
				cHashEntity.remove(  pEntity );
				if( sPathCarte.setEntity( pEntity, pEntity.getMX(),  pEntity.getMY()) == false )
						System.err.println("*** Error World.addEntity  -> Collision" );

		}
		public void destroyEntityAsap( Entity pEntity ){
				if( cHashEntity.remove( pEntity ) )
						cVectDestroyEntity.add( pEntity );				
		}


		
		public boolean existEntity( Entity pEntity) { return cHashEntity.contains( pEntity ); }

		//------------------------------
		final int getWidth()  { return (int)(sDecorCarte.getWidth()*DecorCarte.sSizeCase); }
		final int getHeight() { return (int)(sDecorCarte.getHeight()*DecorCarte.sSizeCase); }

		//------------------------------

		ArrayList<Gamer> cVectGamer  = new ArrayList<Gamer>();
		public void addGamer( Gamer pGamer ){
				cVectGamer.add( pGamer );
		}
		public ArrayList<Gamer> getVectGamer() { return cVectGamer;}

		ArrayList<GamerHuman> cVectGamerHuman  = new ArrayList<GamerHuman>();
		public void addGamerHuman( GamerHuman pGamer ){
				cVectGamer.add( pGamer );
				cVectGamerHuman.add( pGamer );
		}
		public ArrayList<GamerHuman> getVectGamerHuman() { return cVectGamerHuman;}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public World(){
				sTheWorld = this;
				for(int i=0;i<sVectFps.length; i++)
				    sVectFps[i]=0;
		}		

		public World( DecorCarte pDecorCarte ){
				sDecorCarte = pDecorCarte;
				sPathCarte = new PathCarte( pDecorCarte );

				sTheWorld = this;
		}		
		//------------------------------
		public void run(){
				

				cContinue = true;
				
				try{						
						sleep( 2000 );
				}
				catch(Exception e){			 
						System.out.println( e );
						e.printStackTrace();
						}
				
				double lNanoSec = ((double)1)/1000000000;

				double lLastTurnTime = System.nanoTime()*lNanoSec;

				while( cContinue ){
						try{					 
      								
								sleep(10);


								//								System.out.println( "World.run" );
								
								if( cPause == false ){									
										sCurrentTime = System.nanoTime()*lNanoSec;
										double lTimeDiff = sCurrentTime - lLastTurnTime; // temps passe depuis la dernier fois en seconde !
										
										
										// Pour afficher la dure 
										for(int i=0;i<sVectFps.length-1; i++)
												sVectFps[i]=sVectFps[i+1];
										

										sVectFps[sVectFps.length-1]=lTimeDiff;
										
										// sinon bugs divers (tant pis ca va ralentir)
										// sinon deplacement trop rapide
										if( lTimeDiff > 40  || lTimeDiff <= 0 )
												lTimeDiff = 40;



										OneTurn( lTimeDiff*sGameTimeAcceleration ); 

										lLastTurnTime = sCurrentTime;
								}
								else {
										lLastTurnTime = System.nanoTime()*lNanoSec;
								}
								
								// execute les renders pour les joueurs humains !
								for( GamerHuman lGamer: cVectGamerHuman ){
										//				System.out.println( "World.run " + lGamer.isReadyToDraw());

										if( lGamer.isReadyToDraw() )
												lGamer.display();										
								}
										 								

								// execute les ordres des joueurs
								for( Gamer lGamer: cVectGamerHuman ){
										lGamer.execOrder();  
								}
						}
						catch(Exception e){			 
								System.out.println( e );
								e.printStackTrace();
						}
				}				
		}
		//------------------------------
		// Lance l'animation de chaque entite une fois

		void OneTurn( double pTimeDiff ){


				// Detruire les entity a detruire !!!
				for( Entity lEntity : cVectDestroyEntity ) {						
						sPathCarte.clearEntity( lEntity, lEntity.getMX(), lEntity.getMY() );				
				}
				cVectDestroyEntity.clear();
				
				////				System.out.println( "************************* " +  pTimeDiff*1000 );

				//				for( Entity lEntity : cVectEntity ){						
				//						lEntity.run( pTimeDiff );
				//				}
				for( Entity lEntity : cHashEntity ){						
						lEntity.run( pTimeDiff );
				}
				for( Gamer lGamer: cVectGamer ){
						lGamer.run( pTimeDiff );
				}
		}





		int lGamerId=1;
		int lAllianceId=1;
		int lCurrentGamerId = 0;
		int lCurrentAllianceId = 0;
}
//*************************************************
