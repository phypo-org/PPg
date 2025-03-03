package org.phypo.SqlTools;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.Sql.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ArrayList;

import org.phypo.PPg.PPgWin.*;

//***********************************
class  ServerLine implements PPgTableLine{

    SqlServer    cServer;
    SqlLogin     cLogin = null;
    
    ServerLine( String pKey,  PPgIniFile pIni){
	cServer  = new SqlServer( pIni, "SQL", pKey );				
    }
    
    ServerLine( SqlServer pServer, SqlLogin  pLogin ){
	cServer  = pServer;
	cLogin   = pLogin;
    }
    
    
    public String  getColumnName( int pCol){
	switch( pCol ){
	case 0 : return "Server";
	case 1 : return "User";
	}
	return null;
    }
    
    public int     getColumnCount(){
	return 2;
    }
    
    public Object  getValueAt( int pCol ){
	switch( pCol ){
	case 0 : return cServer.cName;
	case 1 : return cServer.cUser;
	}
	return null;
    }
    
    public boolean isCellEditable( int pCol ){
	return false;
    }
    
    public void    setValueAt( Object pValue, int pCol ){
    }
    
    public Class   getColumnClass( int pCol ){
	return cServer.cName.getClass();
    }
    
    public boolean write( PrintStream pOut ){
	return true;
    }
    public PPgTableLine createNewLine( String pVal){
	return null;
    }
    
    public boolean   isSortable(int pCol ){
	return true;
    }
    public int       compareTo(PPgTableLine pLine, int pCol){
	ServerLine lLine = (ServerLine)pLine;
	switch( pCol ){
	case 0 : return cServer.cName.compareTo( lLine.cServer.cName );
	case 1 : return cServer.cUser.compareTo( lLine.cServer.cUser );
	}
	return 0;
    }
}
//***********************************
public class FrameSqlServer extends PPgFrameTable {
    int cLastMenuLine = -1;
    
    public FrameSqlServer( PPgIniFile pIni){				
	super( "Servers",  new PPgTable( new ServerLine( new SqlServer(), null), new ArrayList() ), false, true);
	getTable().getJTable().getTableHeader().addMouseListener( this );		
	
	if( pIni != null ){
	    String lStrServer = pIni.get( "SQL", "Servers" );			
	    if(lStrServer != null ){
		
		StringTokenizer lTok = new StringTokenizer( lStrServer, "," );
		String lName;
		while( lTok.hasMoreTokens() ){
		    lName=lTok.nextToken().trim();
		    getTable().add( new ServerLine( new SqlServer( pIni, "SQL", lName ), null));
		}
	    }
	}
    }
    //-------------------------------------------
    //-------------------------------------------
    //	     	implements MouseListener
    //-------------------------------------------
    //-------------------------------------------
    final String cStrNew  = "New server";
    final String cStrEdit = "Edit server";
    final String cStrDel  = "Delete server";
    
    final String cStrOpenTerm  = "Open simple terminal";
    final String cStrOpenMultiTerm  = "Open multi terminal";    
    final String cStrOpenDatabaseWin  = "Open Databases liste";
    
    JCheckBoxMenuItem cCheckViewJuxta;
    
    //-------------
    public void mousePressed( MouseEvent pEv )  { 
	
	System.out.println( "FrameSqlServer.mousePressed" );
				
	cLastMenuLine = -1;
	if( pEv.getSource() == getTable().getJTable() ){
	    cLastMenuLine = getMouseLineNumber( pEv );
	}
				
				
	if(  SwingUtilities.isLeftMouseButton( pEv ) == true &&  pEv.getClickCount() == 2 ){
	    ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
	    if( lCurrentServer != null ){
		//								if( SwingUtilities.isLeftMouseButton( pEv ) ){
		//										SqlTerm lSqlTerm = new SqlTerm( lCurrentServer.cServer, null );								
		//										PPAppli.ThePPAppli.addChild( lSqlTerm );
		//
		//										lSqlTerm.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );										
		//								}
		//								else
		//if( SwingUtilities.isMiddleMouseButton( pEv ) )  {	
		FrameSqlMultiTerm lConnex = new FrameSqlMultiTerm( lCurrentServer.cServer, null, "" );								
		PPgAppli.TheAppli.addChild( lConnex );
		//}
	    }
	}
	else
	    if( SwingUtilities.isRightMouseButton( pEv ) == true 
		&& pEv.getClickCount() == 1 ) {
								
		JPopupMenu lPopmenu = new JPopupMenu();
	    
		JMenuItem lItem;
								
		lItem= new JMenuItem( cStrNew );
		lPopmenu.add( lItem );
		lItem.addActionListener( this );	 
								
		if( cLastMenuLine != -1 ){
		    lItem= new JMenuItem( cStrEdit );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );	 
										
		    lItem= new JMenuItem( cStrDel );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );
										
		    lPopmenu.add( new JSeparator() );
										
		    lItem= new JMenuItem( cStrOpenTerm );
		    lPopmenu.add( lItem );																		
		    lItem.addActionListener( this );
		    lItem= new JMenuItem( cStrOpenMultiTerm );
		    lPopmenu.add( lItem );																		
		    lItem.addActionListener( this );
										
		    lPopmenu.add( new JSeparator() );
										
		    lItem= new JMenuItem( cStrOpenDatabaseWin  );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );	 
		}
	    
								
		lPopmenu.show( pEv.getComponent(),
			       pEv.getX(),
			       pEv.getY() );												
	    }					
    }
    //-------------------------------------------
    //-------------------------------------------
    //		implements ActionListener
    //-------------------------------------------
    //-------------------------------------------
    public void actionPerformed( ActionEvent pEv ){ 
				
	if( pEv.getActionCommand().equals( cStrNew  )) {
	    SqlServer lServer = new SqlServer();
	    SqlLogin lLogin = new SqlLogin( SqlTools.TheAppli, lServer, IniParam.sDialogX, IniParam.sDialogY );
	    if( lLogin.getValidation() ){
		addLineAndRedraw( new ServerLine( lServer, lLogin ) );
	    }
	}
	else
	    if( pEv.getActionCommand().equals(cStrEdit  ) && cLastMenuLine != -1) {
		ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
		if( lCurrentServer != null ){
		    SqlServer lServer = new SqlServer(lCurrentServer.cServer);
		    SqlLogin lLogin = new SqlLogin( SqlTools.TheAppli, lServer, IniParam.sDialogX, IniParam.sDialogY );
		    if( lLogin.getValidation() ){
			lCurrentServer.cServer = lServer;
			lCurrentServer.cLogin  = lLogin;
			forceRedraw();
		    }
		}
	    }		
	    else
		if( pEv.getActionCommand().equals(cStrDel  ) && cLastMenuLine != -1) {
		    ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
		    if( lCurrentServer != null ){
			int lPos = 	getTable().getList().indexOf( lCurrentServer );
			if( lPos != -1 ) {												
			    removeLineAndRedraw( lPos );
			}
		    }
		}				
		else
		    if( pEv.getActionCommand().equals(cStrOpenTerm  ) && cLastMenuLine != -1) {
			ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
			if( lCurrentServer != null ){
			    FrameSqlTerm lConnex = new FrameSqlTerm( lCurrentServer.cServer, null, "", FrameSqlTerm.SendingMode.SendSimpleAndSelectAndWin);
			    PPgAppli.TheAppli.addChild( lConnex );
			}
		    }				
		    else
			if( pEv.getActionCommand().equals(cStrOpenMultiTerm  ) && cLastMenuLine != -1) {
			    ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
			    if( lCurrentServer != null ){
				FrameSqlMultiTerm lConnex = new FrameSqlMultiTerm( lCurrentServer.cServer, null, "" );								
				PPgAppli.TheAppli.addChild( lConnex );
			    }
			}				
			else
			    if( pEv.getActionCommand().equals( cStrOpenDatabaseWin ) && cLastMenuLine != -1) {
				ServerLine lCurrentServer = (ServerLine)getLine( cLastMenuLine );
														
				//Sybase		//String lOrder =  "SELECT name , dbid, status, version, crdate FROM master..sysdatabases ORDER BY name";
				String lOrder = "SHOW DATABASES";
														
				//   String lOrder = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA";
				SqlConnex lSqlConnex = null;
														
				if( lCurrentServer.cServer.isComplete() 
				    && (lSqlConnex = new SqlConnex( lCurrentServer.cServer, null, null )).connectOrLogin(PPgAppli.TheAppli,-1,-1) ) {										
				    System.out.println( " lSqlConnex.sendCommandAndSetResult -> " + lOrder );

				    lSqlConnex.sendCommandAndSetResult( lOrder, new SqlDataResult( lCurrentServer.cServer, 
												   lCurrentServer.cServer.cName + ":Databases",	
												   "DataTableBaseMenu_SysTable", null  ) );
				}		
			    }
	
    }				
}

