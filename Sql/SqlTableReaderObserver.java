package org.phypo.PPg.Sql;

import java.util.Iterator;

import org.phypo.PPg.PPgFX.DataViewObj;

public interface SqlTableReaderObserver< KEY, OBJ extends DataViewObj >  {

	public  void          myViewClearLines();
	public  OBJ           myViewAddLine(OBJ iObj);
	public  void          myViewRemoveLine(OBJ  iObj );
	public  Iterator<OBJ> myViewGetIterator();
	public  void          myViewAutoResizeColumns();
	public  void          myViewRefreshView();

}
