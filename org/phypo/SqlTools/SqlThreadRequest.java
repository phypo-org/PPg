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


// **********************************

public class SqlThreadRequest extends Thread{ 

		String    cName;
		SqlConnex cConnex;
		String    cQuery;
		SqlOut    cSqlOut;
		SqlOutListener cListener;
		
		Boolean   cRunning=false;

		//--------------------------------------------------
		public	SqlThreadRequest(  SqlConnex pConnex, String pQuery, SqlOut pSqlOut	){

				cConnex = pConnex;
				cQuery  = pQuery;
				cSqlOut = pSqlOut;		
		}

		//--------------------------------------------------
		public	SqlThreadRequest( SqlOutListener pListener, SqlConnex pConnex, String pQuery 	){

				cConnex = pConnex;
				cQuery  = pQuery;	
				cListener = pListener;
		}
		//--------------------------------------------------
		public boolean isRunning(){

				synchronized( cRunning ) { ;
						return cRunning;
				}
		}
		//--------------------------------------------------
			public void run(){

						
					synchronized( cRunning ) { cRunning = true; }


					boolean lRes = false;
					
					if( cSqlOut != null ) {						
							cConnex.sendCommandAndSetResult( cQuery, cSqlOut );									
					} else {							
							cConnex.sendCommandAndPrintResult( cQuery );
									
							if( cListener != null ) 
									cListener.SqlOutListener_setResult();			 	 										
					}
					
					synchronized( cRunning ){	cRunning = false; }
		}
}
// **********************************
