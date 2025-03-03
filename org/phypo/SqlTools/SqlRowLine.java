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

import org.phypo.PPg.PPgWin.*;

import java.sql.*;
//***********************************
class SqlRowLine implements PPgTableLine, FrameEditObjectInterface{

    //		String    cField[]=null;
    Object    cField[]=null;
    SqlDataResult cResult;
    static String sSpace = new String("                                                                                                                                                                        ");

    public SqlRowLine( SqlDataResult pResult, ResultSet pResultSet ) throws SQLException {
	cResult = pResult;

	cField = new Object[pResult.cColumnCount];
	for (int i = 1; i <= pResult.cColumnCount; i++) {
	    if(  pResultSet.getString(i) == null )
		cField[i-1] = null;
	    else {
		String lTmp =  pResultSet.getString(i);
								
		if( pResultSet.getObject(i).getClass().getSuperclass() == Number.class ) {
		    int lSize = pResult.cSize[i-1];
		    if( lSize > 32  )
			lSize = 32;
		    String lStr = new String( sSpace.toCharArray(), 0, lSize - lTmp.length() );
		    //										lStr.concat( lTmp );
		    cField[i-1] = lStr+lTmp;
		}
		else
		    cField[i-1] = lTmp;
	    }								
	}
    }
		

    public String  getColumnName( int pCol){
	//				System.out.println( "getColumnName :" +pCol +" >" + cResult.cTitle[pCol] );
	return cResult.cTitle[pCol];
    }
		
    public int     getColumnCount(){
	return cResult.cColumnCount;
    }

    public Object  getValueAt( int pCol ){
	if( cField[pCol] == null )
	    return "NULL";
	return cField[pCol];
    }

    public boolean isCellEditable( int pCol ){
	return false;
    }

    public void    setValueAt( Object pValue, int pCol ){
    }

    public Class   getColumnClass( int pCol ){
	return String.class;
    }

    public boolean write( PrintStream pOut ){
	return true;
    }
    public PPgTableLine createNewLine( String pVal){
	return null;
    }


    // --------------------------------------------------
    // --------- for FrameEditObjec.PPg.PPgWin -----------
    // --------------------------------------------------

    public FrameEditObjectInterface  FEOduplicate(){
	return null; //new SqlRowLine( this );
    }
    //-----------------------
    public void FEOinitFrom( FrameEditObjectInterface pSrc ){	
				
    }	
    // -------------------------
    public int    FEOgetFieldCount(){ 
	return getColumnCount();
    }
    // -------------------------
    public String FEOgetFieldInfo(int pInd,  FrameEditObject.InfoMode pMode ) {
	if( pMode == FrameEditObject.InfoMode.TITLE ) 	      return getColumnName( pInd );
	else if( pMode == FrameEditObject.InfoMode.VALUE )    return getValueAt( pInd).toString();
	else if( pMode == FrameEditObject.InfoMode.EDITABLE ) return null;
	return null;
    }
    // -------------------------
    public boolean FEOsetField( int pInd, String pVal ) {
			
	setValueAt( pVal, pInd );
	return true;
    }
    // -------------------------
    public void FEOnotifFinish(){
	//				FrameOrsArchitect.sTheOrsArchitect.forceRepaint();
    }

}
//***********************************
