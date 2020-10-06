package org.phypo.PPg.Sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


// **********************************
public interface SqlResultsExecData{
		
    public void oneRow( int pNumResult,  int pNumRow, ResultSetMetaData pMeta,  ResultSet pResultSet ) throws SQLException;
}
// **********************************
