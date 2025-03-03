package com.phipo.TGLib;



import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;


import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;


import java.io.*;
import java.io.PrintStream;
import java.util.*;

import com.phipo.PGWin.*;


//***********************************
public class TGLib extends Thread   {

		String      sVersion = "1.0";

		World  cWorld;

		public static PrintStream sTrace=System.out;
		public static TGLib       sTheProg;


		//-----------------------------
		public TGLib( String pFileXml, boolean pFlagFullScreen, int pW, int pH, int pDepth) {
				
				sTheProg = this;
				XmlLoader.Init();
				
				cWorld = new World();
				
				XmlLoader lXmlLoader = new XmlLoader( pFileXml );

				if( World.sTheWorld.getVectGamerHuman().size() > 1 )
						pFlagFullScreen = false;
		
				for( GamerHuman lGamer: World.sTheWorld.getVectGamerHuman() ){
						FrameGamer2D lFrame2D = new FrameGamer2D( lGamer,
																											new Point2D.Double( 200, 200 ), 
																											pFlagFullScreen, pW, pH, pDepth );				
				}
				
				cWorld.start();

				try {
						while( true )
								sleep(100);										
				}catch(Exception e){ }
			
		}
		//---------------------------------
		//---------------------------------
		//---------------------------------
		static String GetParamString( String[] args, String p_prefix, String pDefault ){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return arg.substring( l );
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean ExistParam( String[] args, String p_prefix){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return true;
								}
				}
				return false;
		}
		//---------------------------------
		
		static Integer GetParamInt( String[] args, String p_prefix, Integer pDefault){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										try{
												return new Integer( arg.substring(l));
										}catch(NumberFormatException ex){
												System.out.println( "Mauvais format pour commande "+p_prefix);
												return null;
					}					
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean GetParam( String[] args, String p_prefix ){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
										return true;
				}
				return false;
		}	
		//-----------------------------
		//-----------------------------
		//-----------------------------

		public static void main(String[] args) {
				
				Integer lVerbose  = GetParamInt( args, "-v", 0 );
				
				
				///				IniParam.ReadIni(lIniObj);
				World.sFurtifMode = ExistParam( args, "--F" );
				World.sDebug      = ExistParam( args, "--G" );

				
				int lW      = GetParamInt( args, "-w", 800 );
				int lH      = GetParamInt( args, "-h", 600 );

				int lDepth  = 0;

				boolean lFlagFullScreen = false;
				String lStrFullScreen = GetParamString( args, "-S", null );

				if(lStrFullScreen != null ){

						lFlagFullScreen = true;
						if( lStrFullScreen.compareTo( "800x600-8" )==0 ){
								lW = 600;
								lH = 800;
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



				String lFileXml = GetParamString( args, "-X", null );
				if( lFileXml != null ){
						TGLib lMonitor = new TGLib( lFileXml, lFlagFullScreen, lW, lH, lDepth );
				}						
		}
}
//***********************************
