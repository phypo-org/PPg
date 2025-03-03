package org.phypo.D4K;

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




public class D4K extends PPgAppli{

    public static D4K Instance=null;
    
    static String sStrIniFile="D4K.ini";
    
    static String sVersion     = "0.21";
    static String sAppliName   = "Deception for Keepers - Alpha";
    static String sSocietyName = "org.phypo";
    static String sDate        = "2020/01/01";
    static String sEmail= "d4k@phypo.org";;

    static int    sVerbose = 0;

    SqlServer     cDatabaseServer;


    ArrayList cMaps = new ArrayList<DKMap>();
    
    PPgHelpChild   cFrameHelp   = null;
    FrameMaps      cFrameMaps    = null;
    public static PrintStream sTrace=System.out;

    //-----------------------------
    JLabel       cFootBar=null;
   
    public static JLabel  GetFootBar()               { return Instance.cFootBar;  }
    public static void    SetFootText( String pStr ) { Instance.cFootBar.setText( pStr ); }

    //---------------------
    PPgIniFile  cIniFile ;	
    public static PPgIniFile  GetIniFile()       { return Instance.cIniFile;}
    public        PPgIniFile  getStdIniFile()    { return cIniFile; }
    //---------------------
   
    public D4K( PPgIniFile pIni ){
	
	super( sAppliName, true);
	
	Instance = this;
	
	cIniFile = pIni;

	
	createMenuBar();
	createToolBar();				
	createFrames();
	
 	cDatabaseServer = new SqlServer( cIniFile, "SQL", "Server" );
	
	cDatabaseServer.debugString();
    }
    //-------------------------------------
    public SqlConnex  getDatabaseConnex()   {
	
	if( cDatabaseServer.isComplete() == false )
	    {
		SqlLogin lLogin;
		if( (lLogin =new SqlLogin( this, cDatabaseServer, 100, 100 )).getValidation() == false)
		    {
			return null;
		    }
	    }
	
	SqlConnex lSqlConnex = null;
       
	if( cDatabaseServer.isComplete() == false 
	    || (lSqlConnex=new SqlConnex( cDatabaseServer, Instance.sTrace )).connect() == false )
	    {
		lSqlConnex = null;
		return null;
	    }

	
	return lSqlConnex;
    }
    //-------------------------------------
    
    //-------------------------------------
    protected void createFrames() {
	cFootBar = new JLabel( "footbar" );
	getContentPane().add( cFootBar, BorderLayout.SOUTH );
	openFrameMaps();
    }
    //-------------------------------------
    void openFrameMaps(){
	if( cFrameMaps == null ) {
	    cFrameMaps = new FrameMaps( cIniFile );
	    addChild(cFrameMaps);
	}
	cFrameMaps.show();
    }
    //-------------------------------------
    void createToolBar(){
    }
    //-------------------------------------

    JMenu cEditMenu;
    public JMenu getEditMenu() { return cEditMenu;}
    JMenu cViewMenu;
    public JMenu getViewMenu() { return cViewMenu;}
     
    public final String cStrSavConfig    = "Save config";
    public final String cStrNewMap       = "New map";
    public final String cStrQuit         = "Quit";

    final String cStrShowMapsWindow      = "Maps window";

    public final String cStrHelpContents = "Help contents";
    public final String cStrAbout        = "About D4K" ;
 

    protected void createMenuBar() {
	JMenu lMenu;
	
	//======== FILE =======
	lMenu = new JMenu("File");
	addItem( lMenu,  cStrNewMap);
	lMenu.add( new JSeparator() );
	addItem( lMenu, cStrQuit );
	c_menubar.add(lMenu);	
	//=====================

    	//======== EDIT =======
	cEditMenu = lMenu = new JMenu("Edit");
	c_menubar.add(lMenu);	
	//=====================
	
	//======== VIEW =======
	cViewMenu = lMenu = new JMenu("View");
	
	
	c_menubar.add(lMenu);	
	//=====================
	
	//======== WINDOWS =======
	lMenu = new JMenu("Windows");
	
	addItem( lMenu,  cStrShowMapsWindow);
	
	c_menubar.add(lMenu);	
	//=====================
	
	//======== HELP ======= 
	lMenu = new JMenu("Help");
	/////////////////				addItem( lMenu, cStrHelpContents );
	addItem( lMenu, cStrAbout );
	c_menubar.add(lMenu);	
	//=====================

    }
    
    //---------------------
    public void actionPerformed( ActionEvent pEv ){

	if( pEv.getActionCommand().equals( cStrQuit )) {
	    System.exit(0);		
	} else if( pEv.getActionCommand().equals( cStrNewMap )) {
	    new DialogNewMap();
	    //								cIniFile.writeFile( sStrIniFile );
	} else  if( pEv.getActionCommand().equals( cStrSavConfig)) {
	    saveIni( cIniFile );
	    //								cIniFile.writeFile( sStrIniFile );
	} else	if( pEv.getActionCommand().equals( cStrShowMapsWindow ) && cFrameMaps != null ) {		
	    cFrameMaps.show();
	} else if( pEv.getActionCommand().equals( cStrHelpContents )){
	    if( cFrameHelp == null )
		addChild( ( cFrameHelp=new PPgHelpChild( "book.html") ));						
	    else
		cFrameHelp.front();
	} else if( pEv.getActionCommand().equals( cStrAbout )){
	    new PPAbout( sAppliName, sVersion, sSocietyName, sDate, sEmail );
	}
    }
    //---------------------
    public void itemStateChanged(ItemEvent pEv ){
	
	Object lSource = pEv.getItemSelectable();	
    }
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
    //-----------------------------
    //-----------------------------
    void addNewMap( String iMapName, String iMapKey  )
    {
	DKMap lNewMap = new DKMap( iMapName, iMapKey );
	cMaps.add( lNewMap );
	cFrameMaps.addNewMap( lNewMap );
    }
  
    //-----------------------------
    //-----------------------------
    //-----------------------------
    public static void main(String[] args) {
				
	System.out.println( "main " + args );
		    
	sVerbose  = PPgParam.GetInt( args, "-v", 0);

	sStrIniFile = PPgParam.GetString( args, "-I", sStrIniFile );
	
	PPgIniFile lIniObj = new PPgIniFile( sStrIniFile);
		    
	String  lType     = PPgParam.GetString( args, "-T", null );
	String  lHost     = PPgParam.GetString( args, "-M", null );
	String  lUrl      = PPgParam.GetString( args, "-U", null );
	Integer lPort     = PPgParam.GetInt   ( args, "-P", 0 );  
		    
	    
	IniParam.ReadIni( lIniObj );
		    
	new D4K( lIniObj );
		    
		    
	String lTraceTerminal = lIniObj.get( "Debug", "TraceTerminal" );
	
	if( (lTraceTerminal != null && lTraceTerminal.equals( "true" ))
		    || PPgParam.ExistParam( args, "-t" )){

	    System.err.println( "******************* Create Trace ");
	    PPTraceTerm lTraceWin = new PPTraceTerm("Trace");
	    sTrace = lTraceWin.stream();
	    System.setOut( Instance.sTrace );
	}

	Instance.SetFootText( "Initialisation" );
		
	D4K.Instance.addNewMap( "Test1", "KeyTest1");  
	D4K.Instance.addNewMap( "Test2", "KeyTest2");  

			
	Instance.setVisible(true);		    
		    
	PPgTrace.sVerbose = sVerbose;							
    }
}

