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

import org.phypo.PPg.PPgWin.*;


//***********************************
class  FrameMachinesLine implements PPgTableLine{

    // une indirection pour pouvoir changer la position d'une colonne, sans tout refaire !
    int cPos[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
    
    Machine        cObj;
    Integer        cIntegerClass = new Integer(0);
    
    FrameMachinesLine( Machine  iObj){
	cObj  = iObj;				
    }
        
    public String  getColumnName( int pCol){
	
	switch( cPos[pCol] ){
	case 0 : return "IP";
	case 1 : return "Name";
	case 2 : return "Info";
	case 3 : return "Nb Msg";    
	case 4 : return "Nb Ports";
	case 5 : return "Ports";
    	case 6 : return "Protocoles";
    	case 7 : return "Applications";
    	case 8 : return "Connections to";
	    //				case 1 : return "User";
	}
	return null;
    }
    
    public int     getColumnCount(){
	return cPos.length;
    }
    
    public Object  getValueAt( int pCol ){
	
	switch( cPos[pCol] ){
	case 0 : return cObj.cIP;
	case 1 : return cObj.cName;
	case 2 : return cObj.cInfo;
	case 3 : return cObj.cNbMsg;
	case 4 : return cObj.cServices.size();
	case 5 : return cObj.getPorts();
	case 6 : return cObj.getProtos();
	case 7 : return cObj.getApplis();
	case 8 : return cObj.getConnections();
	}
	return null;
    }
    
    public boolean isCellEditable( int pCol ){
	switch(  cPos[pCol]  ){
	case 0 :	return false;
	case 1 :	return true;
	case 2 :	return false;
	case 3 :	return false;
	case 4 :	return false;
	case 5 :	return false;
	case 6 :	return false;
	case 7 :	return false;
	case 8 :	return false;
	}
	return false;
    }
    
    public void    setValueAt( Object pValue, int pCol ){
	switch( cPos[pCol] ){
	case 1 :   cObj.cName = ((String)pValue) ;
	}
    }

    
    
    public Class   getColumnClass( int pCol ){
	switch( cPos[pCol] ){
	case 0 : return cObj.cIP.getClass();
	case 1 : return cObj.cName.getClass();
	case 2 : return cObj.cInfo.getClass();
	case 3 : return cIntegerClass.getClass();
	case 4 : return cIntegerClass.getClass();
	case 5 : return cObj.cIP.getClass();
	case 6 : return cObj.cIP.getClass();
	case 7 : return cObj.cIP.getClass();
	case 8 : return cObj.cIP.getClass();
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
	FrameMachinesLine lLine = (FrameMachinesLine)pLine;
	switch( cPos[pCol] ){
	case 0 : return cObj.cIP.compareTo( lLine.cObj.cIP );
	case 1 : return cObj.cName.compareTo( lLine.cObj.cName );
	case 2 : return cObj.cInfo.compareTo( lLine.cObj.cInfo );
	case 3 : return cObj.cServices.size() - lLine.cObj.cServices.size();
	case 4 : return cObj.cNbMsg - lLine.cObj.cNbMsg;
	}
	return 0;
    }
}
//***********************************
public class FrameMachines extends PPgFrameTable {
    int cLastMenuLine = -1;

    DKMap cDKMap;

    //--------------------------------------------
    
    public FrameMachines(  DKMap iDKMap ){				
	super( "Maps",  new PPgTable( new FrameMachinesLine( new Machine("0.0.0.0" )), new ArrayList() ), true, true);

	cDKMap = iDKMap;
	addMap( cDKMap );
    }
    //--------------------------------------------
    public void reload(){
	
	clear();
	addMap( cDKMap );
    }	
   //--------------------------------------------
    public void addMap( DKMap iDKMap  ) {

	System.out.println( "addMap " + iDKMap.cMachines.size() );

	for ( Machine lMach : iDKMap.cMachines.values() ) {
	    add(   new FrameMachinesLine( lMach) );
	    System.out.println( "add machines: " + lMach.cIP );
	}
	getTable().fireTableDataChanged();	
    }
    
    //-------------------------------------------
    //-------------------------------------------
    //	     	implements MouseListener
    //-------------------------------------------
    //-------------------------------------------
    final String cStrDel  = "Delete Map";
    //-------------
    public void mousePressed( MouseEvent pEv )  { 
	
	System.out.println( "FrameMachines.mousePressed" );
				
	cLastMenuLine = getMouseLineNumber( pEv );
							
	if(  SwingUtilities.isLeftMouseButton( pEv ) == true &&  pEv.getClickCount() == 2 ){
	    FrameMachinesLine lCurrent = (FrameMachinesLine)getLine( cLastMenuLine );
	    if( lCurrent != null ){
		// Ouvrir un dialogue avec le detail de la machine
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
								
								
		if( cLastMenuLine != -1 ){
		    
		    lItem= new JMenuItem( cStrDel );
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
				
	//SqlLogin lLogin = new SqlLogin( SqlTools.TheAppli, lServer, IniParam.sDialogX, IniParam.sDialogY );
	//	    if( lLogin.getValidation() ){
	//	add( new ServerLine( lServer, lLogin ) );
	//}

	if( cLastMenuLine != -1 )
	    {
		FrameMachinesLine lLine  = (FrameMachinesLine)getLine( cLastMenuLine );
		if( lLine != null ){

		    System.out.println( "cLastMenuLine:" + cLastMenuLine );
		
	    
		    if( pEv.getActionCommand().equals(cStrDel  ) && cLastMenuLine != -1) {		    
		
		
			//   System.out.println( "modelindex:" + modelIndex );
			// DefaultTableModel model = (DefaultTableModel)getTable().getJTable().getModel();
			// model.removeRow(modelIndex);
			
			
			
			cDKMap.cMachines.remove( lLine.cObj.cIP );
			
			removeLineAndRedraw( cLastMenuLine ); 		       	
		    }
		}
		else
		    {
			
		    }
	    }
    }

}
