package org.phypo.PPg.PPgFX;

import java.io.PrintStream;

public interface TreeFXObjInterface<KEY> {
	
	public String getObjName();
	public KEY getKey();
	default String writeFile( String iTypeFile, int iNumRecord, PrintStream iFout ) { return "Not implemented"; }
	
}
