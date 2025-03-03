package org.phypo.SqlTools;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.Sql.*;

import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ArrayList;

import java.sql.*;


//***********************************
public class FrameSqlDataTable extends PPgFrameTable 

{
    SqlDataResult cCurrentResult;

    SqlDataResult cResult=null;
    Hashtable<String, SqlDataTableAction> cHashAction = null;
    SqlDataTableAction cParentAction;

    public FrameSqlDataTable( SqlDataTableAction pParentAction, String pTitle, SqlDataResult pResult, String pKeyAction ){				
	super( pTitle,  new PPgTable( pResult.cRows.get(0), (ArrayList)pResult.cRows ), true, true);
	cParentAction = pParentAction;

	//				getTable().getJTable().getTableHeader().addMouseListener( this );		

	//				getTable().getJTable().setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
	cResult = pResult;

	if( pKeyAction != null ){
	    setAction(pKeyAction);
	}
	setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );	    
    }
    //-------------------------------------------
    void setAction(String pKeyAction){
	//		getStdIniFile
	String lMenuData = PPgAppli.TheAppli.getStdIniFile().get( "SqlDataTableMenu", pKeyAction );
	if( lMenuData == null )
	    return ;				
				

	System.out.println( "******  setAction "+ pKeyAction + "  *****");
	try {
	    StringTokenizer lTok = new StringTokenizer( lMenuData, "," );

	    while( lTok.hasMoreElements() ){
		String lVal = PPgAppli.TheAppli.getStdIniFile().get( "SqlDataTableMenu", lTok.nextToken());


		if( lVal == null )
		    continue;
								

		StringTokenizer lTok2 = new StringTokenizer( lVal, ";" );
								
								
		if( cHashAction == null )
		    cHashAction = new Hashtable<String, SqlDataTableAction>();
		try {
										
		    String lType  =  lTok2.nextToken();
		    String lLabel =  lTok2.nextToken();
		    String lData  =  lTok2.nextToken();
		    String lName = null;
		    String lNextKey = null;

		    if( lTok2.hasMoreElements())
			lName =  lTok2.nextToken();
		    if( lTok2.hasMoreElements())
			lNextKey =  lTok2.nextToken();


										
		    cHashAction.put( lLabel, new SqlDataTableAction( cParentAction, SqlDataTableAction.ActionType.Get(lType),
								     lLabel,
								     lData, lName, lNextKey));		
		    System.out.println( "  ***  ActionlLabel= " + lLabel + " -> <Data:" +  lData + "> <Name:" + lName + "> <NextKey:" +lNextKey +">" );

		}catch( Exception ex){
		    System.err.println( ex +" Exception in FrameSqlDataTable.setAction for :" + pKeyAction );
		}						
	    }
	}catch( Exception ex){
	    System.err.println( ex +" Exception in FrameSqlDataTable.setAction for :" + pKeyAction );
	}						
    }
		

    //-------------------------------------------
    JCheckBoxMenuItem cCheckViewJuxta;

    //-------------
    public void mousePressed( MouseEvent pEv )  { 
				
	System.out.println( "MousePressed");

	if( SwingUtilities.isRightMouseButton( pEv ) == true 
	    && pEv.getClickCount() == 1 ) {
	    int lRow = getTable().getJTable().rowAtPoint( pEv.getPoint() );
	    SqlRowLine lRowLine= (SqlRowLine) getTable().getList().get( lRow );
	    if( lRowLine != null ){
								
		JPopupMenu lPopmenu = new JPopupMenu();
								
		JMenuItem lItem;
								
		if( cHashAction != null ) {
		    Enumeration<SqlDataTableAction> lEnum = cHashAction.elements();
		    while( lEnum.hasMoreElements() ){
			SqlDataTableAction lAction = lEnum.nextElement();
			lAction.cCurrentRowLine = lRowLine;
			lItem= new JMenuItem( lAction.cLibMenu  );
			lPopmenu.add( lItem );
			lItem.addActionListener( this );	 
		    }
		}
								
		lPopmenu.show( pEv.getComponent(),
			       pEv.getX(),
			       pEv.getY() );												
	    }		
	}
	////// new /////
	else 
	    if( SwingUtilities.isLeftMouseButton( pEv ) == true && pEv.getClickCount() == 2 ) {
		int lRow = getTable().getJTable().rowAtPoint( pEv.getPoint() );

		SqlRowLine lRowLine= (SqlRowLine) getTable().getList().get( lRow );
		if( lRowLine != null ){
		    new FrameEditObject( getName(), lRowLine, false, pEv.getX(), pEv.getY() );										
		}
		////// new /////
	    }
    }
    //-------------------------------------------
    //-------------------------------------------
    //		implements ActionListener
    //-------------------------------------------
    //-------------------------------------------
    public void actionPerformed( ActionEvent pEv ){ 
				
	SqlDataTableAction lAction = cHashAction.get( pEv.getActionCommand() );
	if( lAction != null && lAction.cCurrentRowLine != null ) {
	    SqlConnex lSqlConnex = null;

	    switch( lAction.cType ){
	    case ExecOrderAndOpenResultEdit:
	    case ExecOrderAndOpenResultWin:
		if( cResult.cServer.isComplete() 
		    && (lSqlConnex = new SqlConnex( cResult.cServer, null, null )).connectOrLogin(PPgAppli.TheAppli,-1,-1) ) {		
								
		    // On va remplacer tout les mot clefs de la requete du type @XXX@ par les valeurs disponibles 									

		    String lOrder   = lAction.remplaceVariable(  lAction.cData );
		    String lDataStr = lAction.getDataStr();

		    System.out.println( "Order:" + lOrder );
		    System.out.println("FrameSqlDataTable.actionPerformed lAction.cName:" + lAction.cName
				       + "  lDataStr:" + lDataStr
				       + " lAction.cNextKey:" + lAction.cNextKey);
										
		    cCurrentResult = new SqlDataResult( cResult.cServer, cResult.cServer.cName + ":"
							+lAction.cName + lDataStr,	lAction.cNextKey, null);

		    cCurrentResult.setParentAction( lAction ); // pour l'heritage des variables a remplacées 
										
		    if( lSqlConnex.sendCommandAndSetResult( lOrder, cCurrentResult ) == false ){
												

			JOptionPane.showMessageDialog( null, "Error",
						       "Error in sql request ", 
						       JOptionPane.INFORMATION_MESSAGE);
																									 										
		    }
		}
	    }
	}
			
    }

}
//*********************************** 
