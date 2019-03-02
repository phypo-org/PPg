package org.phypo.PPg.Sql;

import java.util.StringTokenizer;

import org.phypo.PPg.PPgUtils.PPgIniFile;
import org.phypo.PPg.PPgUtils.PPgToken;


// **********************************
public class SqlServer{

    public enum ServerType{
	SYBASE,
	MYSQL,
	MARIADB,
	UNKNOWN;

	static public ServerType Find(  String pStr ){

	    for( ServerType lTok :  ServerType.values() ){
		if( pStr.equalsIgnoreCase( lTok.toString() ) )
		    return lTok;
	    }
	    return UNKNOWN;
	}
    };
    public ServerType cServerType=ServerType.MYSQL;
    
    
    public String cUrl = null;
    public String cName = null;
    public String cMachine= null;
    public String cPort= null;
    public String cUser= null;
    public String cPass= null;
    public String cBase= null;
    
    public SqlServer( ){
	
	cUrl = new String();
	cName = new String(  );
	cMachine = new String(  );
	cPort = new String(  );
	cUser = new String(  );
	cPass = new String(  );
	cBase = new String(  );
    }
    public SqlServer( SqlServer pSqlServer ){
	cServerType = pSqlServer.cServerType;

	cUrl = new String( pSqlServer.cUrl );
	cName = new String( pSqlServer.cName );
	cMachine = new String( pSqlServer.cMachine );
	cPort = new String( pSqlServer.cPort );
	cUser = new String( pSqlServer.cUser );
	cPass = new String( pSqlServer.cPass );
	cBase = new String( pSqlServer.cBase );
    }
    public SqlServer( ServerType pType, String pUrl, String pName, String pMachine, String pPort,
		      String pUser, String pPass, String pBase ){
	
				cServerType = pType;
				cUrl = new String( pUrl );
				cName = new String( pName );
				cMachine = new String( pMachine );
				cPort = new String( pPort );
				cUser = new String( pUser );
				cPass = new String( pPass );
				cBase = new String( pBase );
    }
    public SqlServer( ServerType pType, char pUrl[], char pName[], char pMachine[], char pPort[],
											char pUser[], char pPass[], char pBase[] ){
				
				cServerType = pType;
				cUrl = new String( pUrl );
				cName = new String( pName );
				cMachine = new String( pMachine );
				cPort = new String( pPort );
				cUser = new String( pUser );
				cPass = new String( pPass );
				cBase = new String( pBase );
    }
		
    public SqlServer( PPgIniFile pFileIni, String pKeySection, String pName ){
				
				String  lStr = pFileIni.get( pKeySection, pName );
				
				if( lStr != null ){
						PPgToken lTok = new PPgToken( lStr, "", "," );
						
						try{
								cName =pName;			
								cServerType= ServerType.Find( lTok.nextToken().toString() ); 
								//cUrl = lTok.nextToken().toString();
								cMachine =lTok.nextToken().toString().trim();			
								cPort =lTok.nextToken().toString().trim();			
								cUser =lTok.nextToken().toString().trim();			
								cPass =lTok.nextToken().toString().trim();			
								cBase =lTok.nextToken().toString().trim();			
								System.out.println( "<"+cServerType.toString()+":"+cName+":" + cMachine +":" + cPort +":" + cUser+":" + cPass+":"+cBase+">");
						}
						catch( Exception lE){
								System.err.println( "SqlServer : Error when reading init file : " + lStr );
								lE.printStackTrace();
						}
				}
    }
		//----------------------
		public String debugString(){
				String lStr =  "<"+cServerType.toString()+":"+cName+":" 
						+ cMachine +":" + cPort +":" + cUser+":" + cPass+":"+cBase+">";
				return  lStr;
		}
		//-----------------------
    
    public boolean isComplete(){
				if(  cServerType == ServerType.UNKNOWN || cName == null  || cName.length() == 0
						 || cMachine == null  || cMachine.length() == 0
						 || cPort == null  || cPort.length() == 0
						 || cUser == null  || cUser.length() == 0
						 || cPass == null  || cPass.length() == 0){
						return false;
				}
	return true;
    }
};
