package org.phypo.PPg.Sql;

import java.awt.Frame;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// **********************************

public class SqlConnex {
    SqlServer    cServer = null;
    SqlServer    cServerBackup = null;
    SqlServer    cServerCurrent = null;
    
		
		
    PrintStream  cOStream = System.out;
    
    Connection   cConnect;
    char         cSeparator = '\0';
    boolean      cPrintColumnName=true;
    boolean      cPrintInfo=true;
    
    Statement    cCurrentStatement  = null;
    
    
    public  Connection getConnection() { return  cConnect; }
    public  SqlServer getSqlServer() { return cServer; }
    public  SqlServer getSqlServerBackup() { return cServerBackup; }
    public  SqlServer getSqlServerCurrent() { return cServerCurrent; }
    
    
    //-----------------------
    public void setPrintStream( PrintStream  pOStream ){
				cOStream = pOStream;
    }
    //-----------------------
    public SqlConnex( SqlServer pServer, PrintStream pOStream){
				
				cServer  = pServer;
				if( pOStream != null )
						cOStream = pOStream;
    }
    
    public SqlConnex( SqlServer pServer, SqlServer pServerBackup, PrintStream pOStream){
				
				cServer  = pServer;
				cServerBackup  = pServerBackup;
				if( pOStream != null )
						cOStream = pOStream;
    }
    //-----------------------		
    public SqlConnex( SqlServer pServer){
				
				cServer  = pServer;
    }		
    //-----------------------
    public SqlConnex( SqlServer pServer, SqlServer pServerBackup ){
				
				cServer  = pServer;
				cServerBackup  = pServerBackup;
    }
    //-----------------------
    void setSeparator( char pSep )         { cSeparator = pSep; }
    void setPrintColumnName(boolean pFlag) { cPrintColumnName = pFlag; }
    void setPrintInfo( boolean pFlag)      { cPrintInfo  = pFlag; }
    
    //-----------------------
    private  boolean internalConnect( SqlServer pServer ){
				
				if( pServer == null )
						return false;

				try{
						pServer.cUrl=new String( "UNKNOWN" );
						
						if( pServer.cServerType == SqlServer.ServerType.SYBASE ) {
								
								Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
								pServer.cUrl = "jdbc:sybase:Tds:"+pServer.cMachine+":"+pServer.cPort;
								
						} else  if( pServer.cServerType == SqlServer.ServerType.MYSQL ){
								
								Class.forName("com.mysql.jdbc.Driver");
								pServer.cUrl = new String("jdbc:mysql://" +pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
						} else  if( pServer.cServerType == SqlServer.ServerType.MARIADB ){
								
								Class.forName("org.mariadb.jdbc.Driver");
								pServer.cUrl = new String("jdbc:mariadb://" +pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
						}
						
						System.out.println( "URL:" + pServer.cUrl );
						Properties props = new Properties();	
						
						//	    props.put( "user",      pServer.cUser);
						//	    props.put( "password",  pServer.cPass);
						
						//if (_gateway != null) props.put("proxy", _gateway);
						
						cConnect = DriverManager.getConnection( pServer.cUrl, pServer.cUser,  pServer.cPass );
				}
				catch(  Exception sqe){		
						cOStream.println( "SqlConnex.connect Error : " + sqe);
						sqe.printStackTrace();
						cConnect = null;
						cServerCurrent = null;
						
						return false;
				}
				cServerCurrent = pServer;
				
				if( pServer.cBase != null && pServer.cBase.length() > 0 )
						return sendCommand( "use " + pServer.cBase );
				
				return true;
    }		
    //-----------------------
    public boolean connect(){
				
				if( internalConnect( cServer ) == false )
						return  internalConnect( cServerBackup );
				
				return true;
    }
    //-----------------------
    public boolean connectOrLogin( Frame pOwner, int pX, int pY ){
				
				if( internalConnect( cServer ) == false ) {
						if( internalConnect( cServerBackup ) == false ) {
								
								SqlLogin lSqlLogin = new SqlLogin( pOwner, cServer,  pX,  pY );
								
								if( lSqlLogin.getValidation() == false )
										return false;
								
								return internalConnect( cServer );
						}
				}
				return true;
    }
    //-----------------------
    public void disconnect(){
				try{
						cConnect.close();
				}
				catch(  Exception sqe){		
						cOStream.println(sqe);
				}
				
				cServerCurrent = null;
				cConnect = null;
    }
    //-----------------------
    static final String sData=new String("                                                                                                                                ");
		
    void format( StringBuffer pStr, String pData, int pSz ){
				if( pData == null ){
						pData = "?";
				}
				pStr.append( pData );
				pSz -= pData.length();
				if( pSz >=0 ) {
						if( pSz > 128 ) 
								pSz= 128;
						
						pStr.append( sData.substring( 0, pSz ));
				}
				
				if( cSeparator != '\0' )
						pStr.append('|');
    }
    
    //-----------------------
    public boolean sendCommandAndPrintResult(String pQuery ){
				if( pQuery.length() == 0)
						return true;
				
				// Statement cCurrentStatement  = null;
				
				try{
						if( cConnect == null &&  connect() == false )
								return false;
	    
						cCurrentStatement = cConnect.createStatement();
						
						//						System.out.println( "+++++> cCurrentStatement.execute <<<" +pQuery +">>>");
						boolean lResults = cCurrentStatement.execute(pQuery);
						
						int lNbResult = 0;
						
						int rowsAffected = 0;
						
						ResultSetMetaData lResultSetmd = null;
						int lRowsAffected =0;
						
						do {
								if (lResults) {
										lNbResult++;
										
										ResultSet lResultSet = cCurrentStatement.getResultSet();												
										ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();
										
										
										int lNumColumns = lResultSetMeta.getColumnCount();
										
										if( cPrintInfo )
												cOStream.println("\n------------------ Result set " 																					 
																				 + lNbResult + " -----------------------\n");
										StringBuffer lColumn = new StringBuffer();
										
										if( cPrintColumnName )
												for (int i = 1; i <= lNumColumns; i++) {														
			    //			lColumn.append("\t" + lResultSetMeta.getColumnName(i));														
														format( lColumn, lResultSetMeta.getColumnLabel(i), lResultSetMeta.getColumnDisplaySize(i)+1 );													
												}
										
										
										cOStream.println( lColumn.toString() );
										
										int lRowNum = 0;
										for( lRowNum = 0; lResultSet.next(); lRowNum++) {
												lColumn.setLength(0);
												//														lColumn = new StringBuffer("[ " + lRowNum + "]");
												
												for (int i = 1; i <= lNumColumns; i++) {
														format( lColumn, lResultSet.getString(i), lResultSetMeta.getColumnDisplaySize(i)+1);
														//																lColumn.append("\t" + lResultSet.getString(i));																
												}
												cOStream.println(lColumn.toString());														
		    }
										cOStream.println( "\n"+lRowNum + " rows.");
								}								
								else 	{										
										lRowsAffected = cCurrentStatement.getUpdateCount();										
										if (lRowsAffected >= 0) {			
												if( cPrintInfo )
														cOStream.println( rowsAffected + " rows Affected.");
										}
								}
								
								lResults = cCurrentStatement.getMoreResults();								
						}	while (lResults || lRowsAffected != -1);
						
				}
				catch (SQLException sqe) {
						cOStream.println("Unexpected exception : " +															 
														 sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
						sqe.printStackTrace();
						
						//						cCurrentStatement.cancel();						
						return false;
				}
				finally {
				      try {
						if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
							  cCurrentStatement.close();
						  }
					} catch (SQLException e) {						
						e.printStackTrace();
					}
				      cCurrentStatement = null;
			   } 
			return true;
    }
    //-----------------------
    public boolean sendCommandAndSetResult( String pQuery, SqlOut pSqlOut ){
				if( pQuery.length() == 0)
						return true;
				
				try{
						if( cConnect == null &&  connect() == false )
								return false;
						
						cCurrentStatement = cConnect.createStatement();
		
						
						boolean lResults = cCurrentStatement.execute(pQuery);
						
						int lNbResult = 0;
						
						int rowsAffected = 0;
						
						ResultSetMetaData lResultSetmd = null;
						int lRowsAffected =0;
						
						do {
								if (lResults) {
										ResultSet         lResultSet     = cCurrentStatement.getResultSet();												
										ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();
										
										pSqlOut.setResult( lNbResult, lResultSetMeta, lResultSet );
										
										lNbResult++;																													 																			
								}								
								else 	{										
										lRowsAffected = cCurrentStatement.getUpdateCount();										
										if (lRowsAffected >= 0) {		
												cOStream.println(rowsAffected + " rows Affected.");
										}
								}
								
								lResults = cCurrentStatement.getMoreResults();								
						}	while (lResults || lRowsAffected != -1);

						cCurrentStatement.close();	
												
				}
				catch (SQLException sqe) {
						cOStream.println("Unexpected exception : " +															 
														 sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
						sqe.printStackTrace();
						
						//						cCurrentStatement.cancel();						
						return false;
				}
				finally {
				      try {
						if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
							  cCurrentStatement.close();
						  }
					} catch (SQLException e) {						
						e.printStackTrace();
					}
				      cCurrentStatement = null;
			   } 
				return true;
    }
    //-----------------------
    public boolean sendCommand(String pQuery ){
				if( pQuery.length() == 0)
						return true;
				
				try{
						if( cConnect == null &&  connect() == false )
								return false;
						
						cCurrentStatement = cConnect.createStatement();
						
						//						System.out.println( "+++++> cCurrentStatement.execute <<<" +pQuery +">>>");
						boolean lResults = cCurrentStatement.execute(pQuery);
						//						System.out.println( "++++++++++++++++++++++++++++++++++++++++++++++++++++");
						
						int lNbResult = 0;
						
						int rowsAffected = 0;
						
						ResultSetMetaData lResultSetmd = null;
						int lRowsAffected =0;
						
						do {
								if (lResults)
										{
										}								
								else 	{										
										lRowsAffected = cCurrentStatement.getUpdateCount();										
										if (lRowsAffected >= 0) {			
												if( cPrintInfo )
														cOStream.println(rowsAffected + " rows Affected.");
										}
								}
								
								lResults = cCurrentStatement.getMoreResults();								
						}	while (lResults || lRowsAffected != -1);
						
						cCurrentStatement.close();	
				}
				//catch( com.sybase.jdbc3.jdbc.SybSQLException sqe){
				//		cOStream.println("Exception : " +															 
				//										 sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
				//}
				catch (SQLException sqe) {
						cOStream.println("Unexpected exception : " +															 
														 sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
						sqe.printStackTrace();
						//						cCurrentStatement.cancel();						
						return false;
				}
				finally {
					      try {
							if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
								  cCurrentStatement.close();
							  }
						} catch (SQLException e) {						
							e.printStackTrace();
						}
					      cCurrentStatement = null;
				   } 
			return true;
    }
    //-----------------------
    public boolean cancelRequest(){
    	 try {
    	        if(cCurrentStatement != null && !cCurrentStatement.isClosed()) {
    	        	cCurrentStatement.cancel();
    	        	return true;
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return false;  	        
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        return false;
    	    }
		return true;
    }
    //-----------------------
    public boolean sendCommandWithGo(String pQuery ){
				
				while( true ){
						if( pQuery.length() == 0)
								return true;
	    
						int lPos = pQuery.indexOf( "\ngo\n");
						if( lPos == -1 ){
								
								lPos = pQuery.indexOf( "\ngo");
								if( lPos == -1 ){
										return sendCommand( pQuery );
								}
								else
										return sendCommand( pQuery.substring( 0, lPos ) );											
						}
						else{
								sendCommand( pQuery.substring( 0, lPos ) );	
								pQuery = pQuery.substring( lPos+4, pQuery.length() );
	    }								
	}
    }
}
//***********************************
