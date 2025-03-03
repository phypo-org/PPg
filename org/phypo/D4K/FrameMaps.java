package org.phypo.D4K;


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
class  FrameMapsLine implements PPgTableLine{

    DKMap        cMap;

    
    FrameMapsLine( DKMap iMap){
	cMap  = iMap;				
    }
        
    public String  getColumnName( int pCol){
	switch( pCol ){
	case 0 : return "Map name";
	case 1 : return "Database key";
	case 2 : return "Id";
	    //				case 1 : return "User";
	}
	return null;
    }
    
    public int     getColumnCount(){
	return 3;
    }
    
    public Object  getValueAt( int pCol ){
	switch( pCol ){
	case 0 : return cMap.getName();
	case 1 : return cMap.getKey();
	case 2 : return cMap.getId();
	}
	return null;
    }
    
    public boolean isCellEditable( int pCol ){
	switch( pCol ){
	case 0 :	return true;
	case 1 :	return true;
	case 2 :	return false;
	}
	return false;
    }
    
    public void    setValueAt( Object pValue, int pCol ){
	switch( pCol ){
	case 0 :   cMap.setName((String)pValue) ;
	case 1 :   cMap.setKey((String)pValue) ;
	}
    }

    
    
    public Class   getColumnClass( int pCol ){
	switch( pCol ){
	case 0 :	return cMap.getName().getClass();
	case 1 :	return cMap.getKey().getClass();
	case 2 :	return cMap.getId().getClass();
	}
	return null;
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
	FrameMapsLine lLine = (FrameMapsLine)pLine;
	switch( pCol ){
	case 0 : return cMap.getName().compareTo( lLine.cMap.getName() );
	case 1 : return cMap.getKey().compareTo( lLine.cMap.getKey() );
	case 2 : return cMap.getId().compareTo( lLine.cMap.getId() );
	}
	return 0;
    }
}
//***********************************
public class FrameMaps extends PPgFrameTable {
    int cLastMenuLine = -1;
    
    public FrameMaps( PPgIniFile pIni){				
	super( "Maps",  new PPgTable( new FrameMapsLine( new DKMap("First", "Key" )), new ArrayList() ), false, true);
	getTable().getJTable().getTableHeader().addMouseListener( this );		
	
	if( pIni != null ){
	    /*
	      String lStrServer = pIni.get( "SQL", "Servers" );			
	      if(lStrServer != null ){
		
	      StringTokenizer lTok = new StringTokenizer( lStrServer, "," );
	      String lName;
	      while( lTok.hasMoreTokens() ){
	      lName=lTok.nextToken().trim();
	      getTable().add( new ServerLine( new SqlServer( pIni, "SQL", lName ), null));
	      }
	    */
	}
    }
    //--------------------------------------------
    public void addNewMap( DKMap iMap ) {
	 
	add( new FrameMapsLine( iMap ) );
	getTable().fireTableDataChanged();
    }
    
    //-------------------------------------------
    //-------------------------------------------
    //	     	implements MouseListener
    //-------------------------------------------
    //-------------------------------------------
    final String cStrViewMachines  = "Show machines";
    final String cStrViewCnx       = "Show connexions";
    final String cStrNew  = "New Map";
    final String cStrEdit = "Edit Map";
    final String cStrDel  = "Delete Map";
    final String cStrLoad = "Load Map from database ...";

    
    //-------------
    public void mousePressed( MouseEvent pEv )  { 
	
	System.out.println( "FrameMaps.mousePressed" );
		
	cLastMenuLine = getMouseLineNumber( pEv );
			
	if(  SwingUtilities.isLeftMouseButton( pEv ) == true &&  pEv.getClickCount() == 2 ){
	    FrameMapsLine lLine = (FrameMapsLine)getLine( cLastMenuLine );
	    if( lLine != null ){
		lLine.cMap.openFrameMachines( false );

		//								if( SwingUtilities.isLeftMouseButton( pEv ) ){
		//										SqlTerm lSqlTerm = new SqlTerm( lCurrentServer.cServer, null );								
		//										PPAppli.ThePPAppli.addChild( lSqlTerm );
		//
		//										lSqlTerm.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );										
		//								}
		//								else
		
		//if( SwingUtilities.isMiddleMouseButton( pEv ) )  {	
		//	FrameSqlMultiTerm lConnex = new FrameSqlMultiTerm( lCurrentServer.cServer, null, "" );								
		//		PPgAppli.TheAppli.addChild( lConnex );
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
		    lItem= new JMenuItem( cStrViewMachines );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );
		    
		    lItem= new JMenuItem( cStrViewCnx );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );

		    lItem= new JMenuItem( cStrDel );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );
		    
		    lItem= new JMenuItem( cStrLoad );
		    lPopmenu.add( lItem );
		    lItem.addActionListener( this );
										
		    //   lPopmenu.add( new JSeparator() );			 
		}
	    
								
		lPopmenu.show( pEv.getComponent(),
			       pEv.getX(),
			       pEv.getY() );												
	    }					
    }    //-------------------------------------------
    //-------------------------------------------
    //		implements ActionListener
    //-------------------------------------------
    //-------------------------------------------
    public void actionPerformed( ActionEvent pEv ){ 
				
	if( pEv.getActionCommand().equals( cStrNew  )) {
	    new DialogNewMap();

	    //SqlLogin lLogin = new SqlLogin( SqlTools.TheAppli, lServer, IniParam.sDialogX, IniParam.sDialogY );
	    //	    if( lLogin.getValidation() ){
	    //	add( new ServerLine( lServer, lLogin ) );
	    //}
	} else if( pEv.getActionCommand().equals(cStrDel  ) && cLastMenuLine != -1) {
	    FrameMapsLine lLine  = (FrameMapsLine)getTable().getList().get( cLastMenuLine );
	    if( lLine != null ){
		int lPos = 	getTable().getList().indexOf( lLine );
		if( lPos != -1 ) {												
		    removeLineAndRedraw( cLastMenuLine ); 		       	
		}
	    }
	} else if( pEv.getActionCommand().equals(cStrLoad ) && cLastMenuLine != -1) {
	    FrameMapsLine lLine  = (FrameMapsLine)getTable().getList().get( cLastMenuLine );
	    if( lLine!= null ){
		lLine.cMap.loadFromDatabase();
		lLine.cMap.openFrameMachines( true );
	    }
	    
	} else if( pEv.getActionCommand().equals(cStrViewMachines ) && cLastMenuLine != -1) {
	    FrameMapsLine lLine  = (FrameMapsLine)getTable().getList().get( cLastMenuLine );
	    lLine.cMap.openFrameMachines( false );
	} else if( pEv.getActionCommand().equals(cStrViewCnx ) && cLastMenuLine != -1) {
	    FrameMapsLine lLine  = (FrameMapsLine)getTable().getList().get( cLastMenuLine );
	    lLine.cMap.openFrameCnx( false );
	}
    }

}
