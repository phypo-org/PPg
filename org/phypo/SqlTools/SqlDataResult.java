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


// NOTE :  Cette classe fait trop de chose en interaction avec l'interface graphique et les thread !!!!

//***********************************
class SqlDataResult
		implements  SqlOut, SqlOutListener {
		
    int       cColumnCount  = 0;
    ArrayList<SqlRowLine> cRows         = null;
    String    cTitle[]      = null;
    int       cSize[]       = null;	
    
    String    cWinTitle    = null;
    
    String    cKeyAction   = null; // permet d'asscocier des menu au lignes sql !

		SqlServer      cServer;
		SqlOutListener cListener;

		public int getNbRow() { return cRows.size(); }
		ArrayList<SqlRowLine> getRows() { return cRows;}
  
    //-------------------------------------
    public SqlDataResult(  SqlServer pServer, String pTitle, String pKeyAction,  SqlOutListener pListener ){
				cWinTitle   = pTitle;
				cKeyAction  = pKeyAction;
				cServer     = pServer;
				cListener = pListener;
				if( cListener == null )
						cListener = this; // creation automatique !
   }

		SqlDataTableAction cParentAction;
		public void setParentAction( SqlDataTableAction pParentAction ) { cParentAction=pParentAction;}

    //-------------------------------------
    public  void  setResult( int pNumResult,  ResultSetMetaData pMeta,  ResultSet pResultSet ) throws SQLException {								
	
				cColumnCount = pMeta.getColumnCount();				
	
				cSize  = new int[cColumnCount];
				cTitle = new String[cColumnCount];
	
				for (int i = 1; i <= cColumnCount; i++) {	
						//						System.out.println( "Col:" + i + " >" + pMeta.getColumnLabel(i) );
						if( pMeta.getColumnLabel(i) == null )
								cTitle[i-1] = new String( "" );	
						else
								cTitle[i-1] = new String( pMeta.getColumnLabel(i) );	
						cSize[i-1]  = pMeta.getColumnDisplaySize(i);
				}	
	
	
				int lRowNum =0;
				cRows = new ArrayList<SqlRowLine>();
				for( lRowNum = 0; pResultSet.next(); lRowNum++) {				
						cRows.add( new SqlRowLine( this, pResultSet ) );
				}
				
				if( cListener!= null ) 
						cListener.SqlOutListener_setResult(  this );
		
   }

		//------------------------------------------------
		public void SqlOutListener_setResult( final SqlDataResult pResult ){
		 

				System.out.println(" FrameSqlDataTable::SqlOutListener_setResult " );

				final int fNbLine =	 pResult.cRows.size();	
			
				//++++++++++ 
				SwingUtilities.invokeLater( new Runnable(){
																
					 
								public void run(){

						
				if( fNbLine >= 0  ){						
								FrameSqlDataTable lTable = new FrameSqlDataTable( cParentAction, pResult.cWinTitle, pResult, pResult.cKeyAction  );
								lTable.getStatus().setText( "rowcount : " +  fNbLine );						
								PPgAppli.TheAppli.addChild( lTable );						
												
				} else {
					
						JOptionPane.showMessageDialog( null, "Information",
																					 "No data found for this request ", 
																					 JOptionPane.INFORMATION_MESSAGE);
				         }
								}
						} );
				//++++++++++	

		}
		//------------------------------------------------
		public void SqlOutListener_setResult(  ){
		
		}
		 
}
//***********************************
