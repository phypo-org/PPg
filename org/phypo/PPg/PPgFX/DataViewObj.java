package org.phypo.PPg.PPgFX;

import java.io.PrintStream;

//*****************************************
public interface DataViewObj { 
	default public void    setInvalid() {;}
	default public boolean isInvalid() { return false; }
	
	public String    getObjName();
	public Object    getKey();
	default String   writeFile( String iTypeFile, int iNumRecord, PrintStream iFout ) { return "Not implemented"; }
	
	public void      setFrom( DataViewObj iObj);

	default public String getLabel() { return  ""; }// for tree
}
//*****************************************

