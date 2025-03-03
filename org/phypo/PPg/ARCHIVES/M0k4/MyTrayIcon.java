package com.phipo.PPg.M0k4;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.text.*;
import javax.swing.ImageIcon;

import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.Ellipse2D; 
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

import java.util.*;
import java.util.jar.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;


import com.phipo.PPg.PPgWin.*;
import com.phipo.PPg.PPgUtils.*;

//*************************************************

public class MyTrayIcon extends PPgTrayIcon {

		JFileChooser lChooser;
		TreeMap<String, M0k4_Gadget>  lGadgetsRunning ; 
		
		String cFilePref = "M0k4.ini";

		MyTrayIcon( String pNameIcon, String pTooltip ){
				super( pNameIcon, pTooltip );

				readPref( cFilePref );

		}
		//------------------------------------------------
		 M0k4_Gadget getGadget( URL lURL ){

				try {
						JarFile  lJar = new JarFile(lURL.getPath() );

					// Get the manifest
						Manifest lManifest = lJar.getManifest();
						
						Attributes lMainAttrib = lManifest.getMainAttributes();
						System.out.println( "Manifest Main:" + lMainAttrib.entrySet());
						
						// Get the main class name 
						String lMainName = (String)lMainAttrib.get( Attributes.Name.MAIN_CLASS );
						
						// Load the gadget class
						URL [] lURLs = {  lURL };

						System.out.println( "URL:" + lURLs[0]);
						System.out.println( "Name:" + lMainName );
						
						URLClassLoader lLoader = new URLClassLoader( lURLs );
						Class lClass =  lLoader.loadClass( lMainName ); 
						if( lClass != null ) {
								M0k4_Gadget  lGadget = (M0k4_Gadget)lClass.newInstance(); 
								lGadget.gadgetSetManifest( lManifest );	
								lGadget.gadgetSetURL( lURL );
								return lGadget;
						}						
				}catch( SecurityException ex ){
						System.err.println( ex );
						ex.printStackTrace();
						JOptionPane.showMessageDialog( null,
																					 ex.getMessage() ,
																					 "getGadget",
																					 JOptionPane.ERROR_MESSAGE);
						}catch(InstantiationException ex) {
						System.err.println( ex );
								ex.printStackTrace();
								JOptionPane.showMessageDialog( null,
																							 ex.getMessage(),
																							  "getGadget",
																							 JOptionPane.ERROR_MESSAGE);
						}catch(IllegalAccessException ex) { 
						System.err.println( ex );
								ex.printStackTrace();
								JOptionPane.showMessageDialog( null,
																							 ex.getMessage(),
																							  "getGadget",
																							 JOptionPane.ERROR_MESSAGE);
						}catch(ClassNotFoundException ex) { 
						System.err.println( ex );
						System.err.println( ex );
								ex.printStackTrace();
								JOptionPane.showMessageDialog( null,
																							 ex.getMessage(),
																							  "getGadget",
																							 JOptionPane.ERROR_MESSAGE);
						}catch( IOException ex ){
														ex.printStackTrace();
														JOptionPane.showMessageDialog( null,
																													 ex.getMessage(),
																													  "getGadget",
																													 JOptionPane.ERROR_MESSAGE);
												}										

				return null;
		 }
		//------------------------------------------------
		M0k4_Gadget  loadGadget( String pName, URL lURL ){
				 
				 M0k4_Gadget  lGadget = getGadget( lURL );

				 if( lGadget != null ) {
						 String lNameBase = lGadget.gadgetGetName();

						 if( lNameBase == null || lNameBase.length() == 0 )
								 lNameBase = "Unknown";
						 
						 String lName = lNameBase; 
						 if( pName != null )
								 lName = pName;
								
						 if( lGadgetsRunning == null ) lGadgetsRunning = new TreeMap<>();
						 
						 for( int i=0; i< 256; i++ ) {
								 if( lGadgetsRunning.get( lName ) == null )
										 break;																
								 lName = lNameBase + '('+(i+1)+')';
						 }
						 
						 lGadget.gadgetSetInstanceName( lName );
						 
						 lGadgetsRunning.put( lName, lGadget );
						 lGadget.gadgetSetOwner( this ); // permit gadget to call MyTrayIcon
						 lGadget.gadgetInitialize();
								
						 // Pour prendre en compte le nouveau gadget dans les menus!
						 cPopup = new PopupMenu();
						 callInitMenu();
				 }
				 return lGadget;
		}
		//------------------------------------------------
		void  loadGadget(){
				
				if( lChooser == null )
						lChooser = new JFileChooser("./" );
				FileNameExtensionFilter lFilter = new FileNameExtensionFilter( "jar file", "jar" );
				lChooser.setFileFilter( lFilter);
				if( lChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						
						// Open the jar file !
						System.out.println("You chose to open this file: " +
															 lChooser.getSelectedFile());
						try {
								
								M0k4_Gadget  lGadget = loadGadget( null,  lChooser.getSelectedFile().toURI().toURL() );								
								
								
						}catch( IOException ex ){
								System.err.println( ex );
								ex.printStackTrace();
								JOptionPane.showMessageDialog( null,
																								ex.getMessage(),
																							 "loadGadget",
																							 JOptionPane.ERROR_MESSAGE);
						}
				}
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public boolean callInitMenu(){
				

				// Create a popup menu components
				//======================
				MenuItem lNewGadgetItem     = new MenuItem("New gadget ...");

				cPopup.add( lNewGadgetItem );
				lNewGadgetItem .addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										
										cPopup.setEnabled(false);
										try{
										loadGadget();
										
										savePref( cFilePref );
										}catch( Exception ex ){
										}
										cPopup.setEnabled(true);
								}
					});

			//===================
				if( lGadgetsRunning != null && lGadgetsRunning.size() > 0){

						Menu lRunningGadgetsMenu    = new Menu("Running gadgets");
												
						for( final Map.Entry<String, M0k4_Gadget>  lEntry : lGadgetsRunning.entrySet() ) {

								Menu lGadgetMenu = new Menu( lEntry.getKey() );
								lRunningGadgetsMenu.add( lGadgetMenu );
								//=========
								MenuItem lDialogGadgetItem = new MenuItem("Option");
								lGadgetMenu.add( lDialogGadgetItem  );
								
								lDialogGadgetItem.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
														lEntry.getValue().gadgetOpenDialogOption();						
												}
										});
								//=========

								lEntry.getValue().gadgetGetMenu( lGadgetMenu );
								
								//=========
								MenuItem lStopGadgetItem = new MenuItem("Stop gadget");
								lGadgetMenu.add( lStopGadgetItem );
								
								lStopGadgetItem.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
														lEntry.getValue().gadgetDestroy();
														lEntry.getValue().dispose();
														lGadgetsRunning.remove( lEntry.getKey() );
														savePref( cFilePref );
														cPopup = new PopupMenu();
														callInitMenu();
												}
										});
								//=========
						}
						cPopup.add( lRunningGadgetsMenu  );								
				}
				
				//=========
				MenuItem lRemoveStartFile  = new MenuItem("Remove start file");
				cPopup.add(lRemoveStartFile  );
				
				lRemoveStartFile.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										try{
												Files.delete( FileSystems.getDefault().getPath( cFilePref ));
										}catch( Exception ex) {
												;}
								}
						});

				//===================
				/*
				//===================
				if( lGadgetsRunning != null && lGadgetsRunning.size() > 0){

						Menu lStopGadgetMenu    = new Menu("Stop gadget");
						
						
						for( final Map.Entry<String, M0k4_Gadget>  lEntry : lGadgetsRunning.entrySet() ) {

								MenuItem lItem = new MenuItem( lEntry.getKey() );
								lStopGadgetMenu.add( lItem );
								
								lItem.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
														lEntry.getValue().destroyAll();
														lEntry.getValue().dispose();
														lGadgetsRunning.remove( lEntry.getKey() );

														
														cPopup = new PopupMenu();
														callInitMenu();
												}
										});
								cPopup.add( lStopGadgetMenu  );
						}
				}

				//===================
				MenuItem lOptionsItem       = new MenuItem("Option ...");
				cPopup.add( lOptionsItem );
				lOptionsItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										
										cPopup.setEnabled(false);
										//										new DialogOptions();
										cPopup.setEnabled(true);
								}
						});
				*/
				//======================



				//======================
				MenuItem lAboutItem   = new MenuItem("About");			
				cPopup.add( lAboutItem );
				lAboutItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										cPopup.setEnabled(false);

										URL lURLIcon = getClass().getResource("/com/phipo/PPg/M0k4/M0k4_80x80.png");

										JOptionPane.showConfirmDialog( null,	"M0k4\n1.0\nPhilippe Poupon 2013", "About M0k4", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( lURLIcon ));	

										/*										JOptionPane.showConfirmDialog( null,	"M0k4\n1.0\nPhilippe Poupon 2013", "About M0k4", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( "M0k4_80x80.png" ));	
										*/
										
										cPopup.setEnabled(true);
					}
						});
				 
				//======================

				super.callInitMenu();

				return true;
		}
		//------------------------------------------------
		//------------- gadget calls ---------------------
		//------------------------------------------------

		public void gadgetCall_changeMainClass( M0k4_Gadget pOld, M0k4_Gadget pNew ){
				lGadgetsRunning.put( pOld.gadgetGetInstanceName(), pNew );
		}	

		//------------------------------------------------
		void savePref( String pStrFile ){				

				File lFile = new File( pStrFile );
				try{
						
						if( ! lFile.exists() || ! lFile.canWrite() ){
								if(  lFile.createNewFile( ) == false ){
										JOptionPane.showMessageDialog( null,
																									 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																									 "TimAlarm error", JOptionPane.ERROR_MESSAGE);										
										return;
								}
						}
				} catch( IOException ex ){
						JOptionPane.showMessageDialog( null,
																					 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																					 "TimAlarm error", JOptionPane.ERROR_MESSAGE);
						return ;
				}
				
				
				PPgIniFile lIni = new PPgIniFile();

				int i=0;
				for( M0k4_Gadget lGadget: lGadgetsRunning.values() ){
						
						lIni.set( "RUNNING", "GADGET_"+i++, lGadget.gadgetGetInstanceName() + ':' + lGadget.gadgetGetURL() );
				}

				lIni.writeIni( pStrFile );
		}
		//------------------------------------------------
		void readPref( String pStrFile ){

				File lFile = new File( pStrFile );
				if( ! lFile.exists() || ! lFile.canRead() ){
						return;
				}
		
				PPgIniFile lIni = new PPgIniFile( pStrFile );
				if( lIni == null )
						return ;

				for( int i=0; i<256; i++ ){
						String lStrGadget = lIni.get(  "RUNNING", "GADGET_"+i, null );
						if( lStrGadget != null ){
								int lIndex =  lStrGadget.indexOf( ':' );

								String lStrName = lStrGadget.substring( 0, lIndex );
								String lStrURL  = lStrGadget.substring( lIndex+1 );
								
								try{
								loadGadget(lStrName, new URL(lStrURL) );
								}catch( Exception ex ) {
								}
						}

				}
				
		}
}
//*************************************************
