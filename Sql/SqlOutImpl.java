package org.phypo.PPg.Sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

//import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.*;


import org.phypo.PPg.PPgUtils.PPgPair;
//import com.sun.rowset.CachedRowSetImpl;
//import com.sun.rowset.CachedRowSetImpl;
// **********************************

public class SqlOutImpl implements SqlOut {
		 
		public  ArrayList< PPgPair<ResultSetMetaData, CachedRowSet > > cDatas      = new  ArrayList<>();
		
		public  void setResult( int pNumResult,  ResultSetMetaData pMeta,  ResultSet pResultSet ) throws SQLException{
			//CachedRowSetImpl lVal = new CachedRowSetImpl();
			CachedRowSet lVal = RowSetProvider.newFactory().createCachedRowSet();
			lVal.populate(pResultSet);
			cDatas.add( new PPgPair<ResultSetMetaData,CachedRowSet >(pMeta, lVal) );
		}	
		
};
// **********************************
