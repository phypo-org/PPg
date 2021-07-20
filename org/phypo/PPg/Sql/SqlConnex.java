package org.phypo.PPg.Sql;

import java.awt.Frame;

import java.util.Enumeration;

import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.phypo.PPg.PPgUtils.Log;
import org.phypo.PPg.PPgUtils.PPgUtils;

// **********************************

public class SqlConnex {
	SqlServer    cServer = null;
	SqlServer    cServerBackup = null;
	SqlServer    cServerCurrent = null;


	PrintStream  cOStream = System.out;

	Connection   cConnect = null;
	char         cSeparator = '\0';
	boolean      cPrintColumnName=true;
	boolean      cPrintInfo=true;

	Statement    cCurrentStatement  = null;
	boolean      cCurrentHaveResult = false;

	public  Connection getConnection()       { return cConnect;      }
	public  SqlServer  getSqlServer()        { return cServer;        }
	public  SqlServer  getSqlServerBackup()  { return cServerBackup;  }
	public  SqlServer  getSqlServerCurrent() { return cServerCurrent; }


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

		if( pServer == null || pServer.isComplete() == false )
			return false;

		try{	

			switch( pServer.cServerType ) {			
			case SYBASE:
				Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
				pServer.cUrl = new String("jdbc:sybase:Tds:"+pServer.cMachine+":"+pServer.cPort);
				break;
			case MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				pServer.cUrl = new String("jdbc:mysql://" +pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
				break;
			case MARIADB:
				Class.forName("org.mariadb.jdbc.Driver");
				pServer.cUrl = new String("jdbc:mariadb://"+pServer.cMachine+":"+pServer.cPort+"/"+pServer.cBase);
				break;
			case UNKNOWN: 
				Class.forName(pServer.cDriver);
				pServer.cUrl = new String(pServer.cUrl +"://"+pServer.cMachine+":"+pServer.cPort );
				break;
			}
			
			char sep = '?';
			
			if( pServer.cOptions.length() > 0 ) {
				//"?allowMultiQueries=true";
				pServer.cUrl = new String(pServer.cUrl + sep + pServer.cOptions);
				sep = '&';
			}
			
			if( pServer.cCerts != null ) {
				//&useSSL=true&...
			    @SuppressWarnings("unchecked")
				Enumeration<String> enums = (Enumeration<String>) pServer.cCerts.propertyNames();
			    while (enums.hasMoreElements()) {
			    	String key = enums.nextElement();
			    	String value = pServer.cCerts.getProperty(key);
					pServer.cUrl += new String(sep + key + '=' + value);
					sep = '&';
			    }
			}

			Log.Dbg( "URL:" + pServer.cUrl );

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

		if( pServer.cServerType == pServer.cServerType.SYBASE && pServer.cBase != null && pServer.cBase.length() > 0 )
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
	public boolean connectIfNeeded(){

		if( isConnected())
			return true;

		if( internalConnect( cServer ) == false )
			return  internalConnect( cServerBackup );

		return true;
	}
	//-----------------------
	boolean isConnected() { return   cConnect != null ;}
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
			if( cConnect != null )
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
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);


			//================================
			int lNbResult = 0;

			int rowsAffected = 0;

			ResultSetMetaData lResultSetmd = null;
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {
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
						//	lColumn = new StringBuffer("[ " + lRowNum + "]");

						for (int i = 1; i <= lNumColumns; i++) {
							format( lColumn, lResultSet.getString(i), lResultSetMeta.getColumnDisplaySize(i)+1);
							//	lColumn.append("\t" + lResultSet.getString(i));											
						}
						cOStream.println(lColumn.toString());														
					}
					cOStream.println( "\n"+lRowNum + " rows.");
				}							
				else 	{										
					lRowsAffected = cCurrentStatement.getUpdateCount();										
					if (lRowsAffected >= 0) {			
						if( cPrintInfo ) {
							//	cOStream.println( rowsAffected + " rows Affected.");
						}
					}
				}

				cCurrentHaveResult = cCurrentStatement.getMoreResults();								
			}	while (cCurrentHaveResult|| lRowsAffected != -1);

			//================================
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
	public boolean sendCommandAndSetResult( String pQuery, SqlOut pSqlOut ){
		if( pQuery.length() == 0)
			return true;

		try{
			if( cConnect == null &&  connect() == false )
				return false;

			cCurrentStatement = cConnect.createStatement();
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);
			//==========================
			int lNbResult = 0;

			int rowsAffected = 0;

			ResultSetMetaData lResultSetmd = null;
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {
					ResultSet         lResultSet     = cCurrentStatement.getResultSet();
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					pSqlOut.setResult( lNbResult, lResultSetMeta, lResultSet );

					lNbResult++;				
				}								
				else 	{										
					lRowsAffected = cCurrentStatement.getUpdateCount();	
					if (lRowsAffected >= 0) {		
						//cOStream.println(rowsAffected + " rows Affected.");
					}
				}

				cCurrentHaveResult = cCurrentStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);
			//==========================

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
	public PreparedStatement prepareCommand( String pQuery ) {
		try{
			return cConnect.prepareStatement( pQuery );
		}
		catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
			sqe.printStackTrace();
		}
		return null;
	}
	//-----------------------
	public CallableStatement prepareCallProc( String pQuery ) {
		try{
			return cConnect.prepareCall( pQuery );
		}
		catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
			sqe.printStackTrace();
		}
		return null;
	}
	//-----------------------
	public boolean executeWithNoReturn ( PreparedStatement iStatement) {
		try{
			iStatement.executeUpdate();
			return true;
		}
		catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
			sqe.printStackTrace();
		}
		try {
			iStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iStatement = null;
		return false;
	}
	//-----------------------
	public boolean executeQuery( PreparedStatement iStatement, SqlResultsExecData iExec) {
		boolean lResult = false;
		try{
			//================================
			int lNbResult = 0;						
			int lRowsAffected =0;
			
			ResultSet lResultSet = iStatement.executeQuery();
			
			if( lResultSet != null ) {
				cCurrentHaveResult = true;
			}
			do {
				if (cCurrentHaveResult) {		   		  
					if( lResultSet == null )
						break;
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {		    	
						iExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}		
					lNbResult++;	
				}							
				else 	{										
					lRowsAffected = iStatement.getUpdateCount();
				}								
				cCurrentHaveResult = iStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);						
			//================================
			lResult = true;
			}
		catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
			sqe.printStackTrace();
		}

		try {
			iStatement.close();
		}catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState());						
			sqe.printStackTrace();
			return false;
		}				
		iStatement = null;		

		return lResult;
	}
	//-----------------------
	public boolean executeQuery( String iQuery, SqlResultsExecData iExec) {
		PreparedStatement lStatement = prepareCommand( iQuery );
		
		if( lStatement != null ) {
			return executeQuery( lStatement, iExec );
		}
		Log.Err( "SqlConnex.executeSelect - prepareCommand failed <<<" + iQuery + ">>>");
		return false;
	}
	//-----------------------
	// On attende des resultats
	public boolean sendCommandResult(String pQuery, SqlResultsExecData pExec ){

		if( pQuery.length() == 0)    return true;

		try{
			if( cConnect == null &&  connect() == false )
				return false;

			cCurrentStatement = cConnect.createStatement();					
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);

			//================================
			int lNbResult = 0;						
			int lRowsAffected =0;

			do {
				if (cCurrentHaveResult) {		   		  
					ResultSet         lResultSet     = cCurrentStatement.getResultSet();
					if( lResultSet == null )
						break;
					ResultSetMetaData lResultSetMeta = lResultSet.getMetaData();

					//    int lNumColumns = lResultSetMeta.getColumnCount();
					int lRowNum = 0;
					for( lRowNum = 0; lResultSet.next(); lRowNum++) {		    	
						pExec.oneRow( lNbResult, lRowNum, lResultSetMeta, lResultSet);
					}		
					lNbResult++;	
				}							
				else 	{										
					lRowsAffected = cCurrentStatement.getUpdateCount();
				}								
				cCurrentHaveResult = cCurrentStatement.getMoreResults();
			}	while (cCurrentHaveResult || lRowsAffected != -1);						
			//================================
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
	public void eatResults() throws SQLException
	{
		int lNbResult = 0;

		int rowsAffected = 0;

		ResultSetMetaData lResultSetmd = null;
		int lRowsAffected =0;
		//========================
		do {
			if (cCurrentHaveResult)
			{
			}								
			else 	{										
				lRowsAffected = cCurrentStatement.getUpdateCount();										
				if (lRowsAffected >= 0) {			
					if( cPrintInfo ) {
						//	cOStream.println(rowsAffected + " rows Affected.");
					}
				}
			}

			cCurrentHaveResult= cCurrentStatement.getMoreResults();								
		} while (cCurrentHaveResult || lRowsAffected != -1);
		//========================

		cCurrentStatement.close();		
		cCurrentStatement = null;
	}
	//-----------------------
	public boolean sendCommand(String pQuery ){
		if( pQuery.length() == 0)
			return true;

		try{
			if( cConnect == null &&  connect() == false )
				return false;

			cCurrentStatement = cConnect.createStatement();						
			//					System.out.println( "+++++> cCurrentStatement.execute <<<" +pQuery +">>>");
			cCurrentHaveResult = cCurrentStatement.execute(pQuery);
			//						System.out.println( "++++++++++++++++++++++++++++++++++++++++++++++++++++");
			eatResults();	    
		}
		catch (SQLException sqe) {
			cOStream.println("Unexpected exception : " +															 
					sqe.toString() + ", sqlstate = " + sqe.getSQLState() + " <<<"+ pQuery +">>>" );		
			
			sqe.printStackTrace();
			return false;
		}
		catch (Exception e) {
			cOStream.println("Unexpected exception : " +															 
					e.toString()  + " <<<"+ pQuery +">>>" );						
			e.printStackTrace();
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
	//-----------------------
	static public String SqlQuote(String iStr ) {
		return PPgUtils.DupliqueChar(iStr,'\'');
	}
}
//***********************************
