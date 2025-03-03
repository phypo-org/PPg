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

public class SqlDataTableAction{

		public enum ActionType{ 

				ExecOrderAndOpenResultWin("ExecOrderAndOpenResultWin"),
				ExecOrderAndOpenResultEdit("ExecOrderAndOpenResultEdit")
				;
				
				private final String       cStr;

				ActionType( String pStr){
						cStr = pStr;
				}

				public static final HashMap<String,ActionType > cHashActionType = new HashMap<String, ActionType>();

				public static final ActionType Get( String pStr ) {
						return cHashActionType.get( pStr );
				}
				static {
						for( ActionType f:ActionType.values() ){
								cHashActionType.put( f.cStr, f );
						}
				}
		}


		public ActionType cType;
		public String     cLibMenu;
		public String     cData;
		public SqlRowLine cCurrentRowLine;
		public String     cName;
		public String     cNextKey;
		public SqlDataTableAction cParent;


		public SqlDataTableAction(SqlDataTableAction pParent, ActionType pType, String pLibMenu, String pData, String pName, String pNextKey){

				cParent  = pParent;

				cType    = pType;
				cLibMenu = pLibMenu;
				cData    = pData;
				cName    = pName;
				cNextKey = pNextKey; 
				
				System.out.println( "        SqlDataTableAction  type:" +pType + " libmenu:" + pLibMenu + " data:"+pData + " name:" +pName 
						+" nextKey:" +pNextKey );
		}

		;
		StringBuilder cDataStr;
		public String getDataStr() { 
				if( cDataStr != null ) 
						return cDataStr.toString();
				
				return "";
		}

		public String remplaceVariable( String pOrder )
		{
				
				for( int i=0; i< cCurrentRowLine.getColumnCount(); i++){
						String lTarget = "@"+cCurrentRowLine.getColumnName(i)+"@";
						System.out.println( "->remplace:" + lTarget + " by " + cCurrentRowLine.getValueAt(i) );
						
						if( cData.indexOf( lTarget ) != -1 ){
								pOrder = pOrder.replace( lTarget, (String)cCurrentRowLine.getValueAt(i) );
								if( cDataStr == null || cDataStr.length()==0 )
										cDataStr=new StringBuilder(" for ");
								else
										cDataStr.append( ", " );
								
								cDataStr.append(  ((String)cCurrentRowLine.getValueAt(i)) + " " );
						}
				}

				if( cParent != null ){
						System.out.println(" =============== " );
						pOrder = cParent.remplaceVariable( pOrder );
						cDataStr.append("--->").append( cParent.getDataStr() );
						System.out.println(" =============== " );
				}
				
				return pOrder;
		}
}
//***********************************
