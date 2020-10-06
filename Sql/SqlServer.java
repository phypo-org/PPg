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
	public ServerType cServerType=ServerType.UNKNOWN;


	public String cUrl = null;
	public String cName = null;
	public String cMachine= null;
	public String cPort= null;
	public String cUser= null;
	public String cPass= null;
	public String cBase= null;    
	public String cOptions= null;
	public String cDriver= null;



	public SqlServer( ){

		cUrl = new String();
		cName = new String(  );
		cMachine = new String(  );
		cPort = new String(  );
		cUser = new String(  );
		cPass = new String(  );
		cBase = new String(  );
		cOptions = new String(  );
		cDriver = new String();
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
		cOptions = new String( pSqlServer.cOptions );
		cDriver = new String(pSqlServer.cDriver);
	}
	public SqlServer( ServerType pType, String pUrl, String pName, String pMachine, String pPort,
			String pUser, String pPass, String pBase, String pOptions, String pDriver ){

		cServerType = pType;
		cUrl = new String( pUrl );
		cName = new String( pName );
		cMachine = new String( pMachine );
		cPort = new String( pPort );
		cUser = new String( pUser );
		cPass = new String( pPass );
		cBase = new String( pBase );
		cOptions = new String( pOptions );
		cDriver = new String(pDriver);
	}
	public SqlServer( ServerType pType, char pUrl[], char pName[], char pMachine[], char pPort[],
			char pUser[], char pPass[], char pBase[], char pOptions[], char pDriver[] ){

		cServerType = pType;
		cUrl = new String( pUrl );
		cName = new String( pName );
		cMachine = new String( pMachine );
		cPort = new String( pPort );
		cUser = new String( pUser );
		cPass = new String( pPass );
		cBase = new String( pBase );
		cOptions = new String( pOptions );
		cDriver = new String(pDriver);
	}

	public SqlServer( PPgIniFile pFileIni, String pKeySection, String pName ){

		String  lStr = pFileIni.get( pKeySection, pName );

		if( lStr != null ){
			PPgToken lTok = new PPgToken( lStr, "", "," );

			try{
				cName =pName;			
				cServerType= ServerType.Find( lTok.nextToken().toString() ); 
				//cUrl = lTok.nextToken().toString();
				cMachine =lTok.nextTokenStringTrim();			
				cPort =lTok.nextTokenStringTrim();			
				cUser =lTok.nextTokenStringTrim();			
				cPass =lTok.nextTokenStringTrim();			
				cBase =lTok.nextTokenStringTrim();			
				cOptions =lTok.nextTokenStringTrim();
				cUrl=lTok.nextTokenStringTrim();
				cDriver=lTok.nextTokenStringTrim();
			}
			catch( Exception lE){
				System.err.println( "SqlServer : Error when reading init file : " + lStr );
				lE.printStackTrace();
			}
		}
	}
	//----------------------
	boolean isVoid(String pStr) { if( pStr==null || pStr.length()==0) return true; return false;}
	//----------------------
	public String debugString(){
		String lStr;
		if( cServerType != cServerType.UNKNOWN ) {
			lStr =  "<"+cServerType.toString()+":"+cName+":" 
				+ cMachine +":" + cPort +":" + cUser+":" + cPass+":"+cBase+":" + cOptions+">";
			}
		else {
			lStr =  "<"+cUrl+":"+cDriver+":"+":"+cName+":" 
					+ cMachine +":" + cPort +":" + cUser+":" + cPass+":"+cBase+":" + cOptions+">";
		}
				
		return  lStr;
	}
	//-----------------------
	// Options not tested
	public boolean isComplete(){
		if( (cServerType == ServerType.UNKNOWN && isVoid(cUrl)&&isVoid(cDriver))
				|| isVoid(cName)
				|| isVoid(cMachine)
				|| isVoid(cPort)
				|| isVoid(cUser)
				|| isVoid(cPass)){
			return false;
		}
		return true;
	}
};
