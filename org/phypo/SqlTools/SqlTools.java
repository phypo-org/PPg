package org.phypo.SqlTools;



import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JFrame;

import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.JToolBar;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.tree.*;
import javax.swing.WindowConstants;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.Sql.*;

import java.io.*;
import java.io.PrintStream;
import java.util.*;


//***********************************
public class SqlTools extends PPgAppli{
    
    static String sStrIniFile="SqlTools.ini";
    
    String      sVersion = "1.01";
    PPgHelpChild cFrameHelp=null;
    
    PPgIniFile  cIniFile ;	
    public static PPgIniFile  GetIniFile() { return ((SqlTools)TheAppli).cIniFile;}
    public  PPgIniFile  getStdIniFile()    { return cIniFile; }
    
    static FrameSqlServer  sFrameServer = null;
    
    JLabel cFootBar=null;
    
    public static PrintStream sTrace=System.out;
    
    //-----------------------------
    public static JLabel  GetFootBar(){
	return ((SqlTools)TheAppli).cFootBar;
    }
    //-----------------------------
    public static void SetFootText( String pStr ){
	((SqlTools)TheAppli).cFootBar.setText( pStr );
    }
    //-----------------------------
    public SqlTools(PPgIniFile pIni) {
	super("SqlTools", true);
	
	cIniFile = pIni;
	
	Rectangle lRect = PPgIniFile.GetRectangle( cIniFile.get( "Frame", "Mother" ), "," );
	if( lRect != null)	setBounds( lRect);
	
	//				if( IniParam.sIconeSqlTools != null )
	//						setIconImage( IniParam.sIconeSqlTools.getImage());
	
	createMenuBar();
	createToolBar();				
	createFrames();	 
	
	
    }
    //-------------------------------------
    protected void createFrames() {
	cFootBar = new JLabel( "footbar" );
	getContentPane().add( cFootBar, BorderLayout.SOUTH );
	
	sFrameServer = new FrameSqlServer( cIniFile );

	addChild(sFrameServer);
	
    }
    //-------------------------------------
    void createToolBar(){
    }
    //-------------------------------------
    
    final String cStrSavConfig      = "Save config";
    final String cStrReloadConfig   = "Reload from config";
    final String cStrReloadSybase   = "Reload from Sybase";
    
    final String cStrDeleteAll      = "Delete all";
    final String cStrQuit = "Quit";
    final String cStrServersWin  = "Servers window";
    
    final String cStrHelpContents = "Help contents";
    final String cStrAbout        = "About SqlTools";
    
    JCheckBoxMenuItem cCheckViewConnection;
    JCheckBoxMenuItem cCheckDisabledIcone;
    JCheckBoxMenuItem cCheckShowNbClient;
    
    JMenu lMenu;
    
    protected void createMenuBar() {	
	
	//======== FILE =======
	lMenu = new JMenu("File");
	addItem( lMenu, cStrSavConfig );
	lMenu.add( new JSeparator() );
	addItem( lMenu, cStrQuit );
	cMenubar.add(lMenu);	
	//=====================
	
	
	//======== EDIT =======
	lMenu = new JMenu("Edit");
	cMenubar.add(lMenu);	
	//=====================
	
	
	//======== VIEW =======
	lMenu = new JMenu("View");
				
	addItem( lMenu,  cStrServersWin);

	cMenubar.add(lMenu);	
	//=====================

	//======== HELP ======= 
	lMenu = new JMenu("Help");
	/////////////////				addItem( lMenu, cStrHelpContents );
	addItem( lMenu, cStrAbout );
	cMenubar.add(lMenu);	
	//=====================

    }
    //---------------------
    public void actionPerformed( ActionEvent pEv ){

	if( pEv.getActionCommand().equals( cStrQuit )) {
	    System.exit(0);		
	}
	else
	    if( pEv.getActionCommand().equals( cStrSavConfig)) {
		saveIni( cIniFile );
		//								cIniFile.writeFile( sStrIniFile );
	    }
	    else
		if( pEv.getActionCommand().equals( cStrServersWin )) {		
		    sFrameServer.show();
		}
  		else
		    if( pEv.getActionCommand().equals( cStrReloadConfig )){
			PPgIniFile lIniObj = new PPgIniFile( sStrIniFile );
			cIniFile = lIniObj;
			IniParam.ReadIni(cIniFile);				
		    }
		    else
			if( pEv.getActionCommand().equals( cStrHelpContents )){
			    if( cFrameHelp == null )
				addChild( ( cFrameHelp=new PPgHelpChild( "book.html") ));						
			    else
				cFrameHelp.front();
			}
			else
			    if( pEv.getActionCommand().equals( cStrAbout )){
				new PPAbout( "SqlTools", sVersion, "cdv", "2004/02/04", "phipo@padev013", null );
			    }
    }
    //---------------------
    public void itemStateChanged(ItemEvent pEv ){

	Object lSource = pEv.getItemSelectable();

    }
    //---------------------
    //---------------------
    void saveIni(PPgIniFile pIni){

	pIni.set( "Frame", "Mother",
		  ((int)getLocation().getX())+","+
		  ((int)getLocation().getY())+","+
		  ((int)getSize().getWidth())+","+
		  ((int)getSize().getHeight()) );
		    
	IniParam.SaveIni(pIni);				
    }

    //-----------------------------
    public static void main(String[] args) {
				
	System.out.println( "main " + args );
		    

	Integer lVerbose  = PPgParam.GetInt( args, "-v", 0);

	sStrIniFile = PPgParam.GetString( args, "-I", sStrIniFile );
	PPgIniFile lIniObj = new PPgIniFile( sStrIniFile);
		    
	String  lType     = PPgParam.GetString( args, "-T", null );
	String  lHost     = PPgParam.GetString( args, "-M", null );
	String  lUrl      = PPgParam.GetString( args, "-U", null );
	Integer lPort     = PPgParam.GetInt( args, "-P", 0 );  
		    
	////				lIniObj.debug();
	/*
	  if( lHost == null )
	  lHost= lIniObj.get( "SrvMonitor", "Host" );
		      
	  if( lPort == null )
	  lPort = lIniObj.getInteger( "SrvMonitor", "Port" );
		      
		      
	  //				System.out.println( lHost +":" + lPort );
		      
	  if( lHost == null || lPort == null ){						
	  System.out.println( "Give me Server Monitor Host and Port !" );
	  System.exit(1);
	  }
	*/
		    
	IniParam.ReadIni(lIniObj);
		    
	new SqlTools(lIniObj);
		    
		    
	String lTraceTerminal = lIniObj.get( "Debug", "TraceTerminal" );
	if( lTraceTerminal != null && lTraceTerminal.equals( "true" )){						
	    PPTraceTerm lTraceWin = new PPTraceTerm("Trace");
	    sTrace = lTraceWin.stream();
	    System.setOut( SqlTools.sTrace );
	}
		    
	SqlTools.SetFootText( "Initialisation" );
		    
			
	TheAppli.setVisible(true);
		    
		    
	if( lVerbose != null )
	    PPgTrace.SetVerbose( lVerbose.intValue());
    }
}
//***********************************
