package org.phypo.SqlTools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


// **********************************
public interface SqlOutListener{
		
		public  void SqlOutListener_setResult( final SqlDataResult pResult);
		public  void SqlOutListener_setResult();
}
// **********************************
