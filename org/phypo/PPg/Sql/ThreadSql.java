package org.phypo.PPg.Sql;

import org.phypo.PPg.PPgUtils.Log;

public class ThreadSql extends Thread{
	String    cName;
	protected SqlConnex      cSqlConnex = null;
	
	protected ThreadSql( String iName ){
		cName = iName;
	}
	//----------------------------------------------------	
	protected boolean getConnex( SqlServer iServer ){

		if (cSqlConnex == null) {
			if (iServer.isComplete() == false) {
				Log.Err( cName + " database login incomplete");
				return false;
			}
			
			if((cSqlConnex = new SqlConnex( iServer, Log.GetErr() )).connect() == false) {
				Log.Err( cName + " getConnex failed");
				cSqlConnex = null;
				return false;
			}
		}
		return cSqlConnex != null;
	}	
}
