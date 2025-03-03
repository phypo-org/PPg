package org.phypo.PPg.Sql;

import org.phypo.PPg.PPgUtils.PPgTrace;

public class ThreadSql extends Thread{
	String    cName;
	protected SqlConnex      cSqlConnex = null;

	protected ThreadSql( String iName ){
		cName = iName;
	}
	//----------------------------------------------------
	protected boolean getConnex( SqlServer iServer ){

		if (cSqlConnex == null || cSqlConnex.isClose()) {
			if( cSqlConnex != null ) {
				cSqlConnex = null;	
			}
			
			if (!iServer.isComplete()) {
				PPgTrace.Err( cName + " database login incomplete");
				return false;
			}
					

			if(!(cSqlConnex = new SqlConnex( iServer, PPgTrace.GetErr() )).connect()) {
				PPgTrace.Err( cName + " getConnex failed");
				cSqlConnex = null;
				return false;
			}
		}
		return cSqlConnex != null;
	}
}
