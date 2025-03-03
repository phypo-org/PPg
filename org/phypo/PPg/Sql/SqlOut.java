package org.phypo.PPg.Sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


// **********************************
public interface SqlOut{

		public void setResult( int pNumResult,  ResultSetMetaData pMeta,  ResultSet pResultSet ) throws SQLException;
}
// **********************************
