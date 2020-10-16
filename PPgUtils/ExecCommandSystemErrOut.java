package org.phypo.PPg.PPgUtils;

public class ExecCommandSystemErrOut extends ExecCommandSystem {
	
	public StringBuffer                cErrStr = null;
	public StringBuffer                cOutStr = null;

	
	@Override
	public void execOutLine( String iLine) {		
		if( cOutStr == null ) cOutStr=new StringBuffer();
		cOutStr.append(iLine);
		cOutStr.append('\n');
	}
	@Override
	public void execErrLine( String iLine) {	
		if( cErrStr == null ) cErrStr=new StringBuffer();
		cErrStr.append(iLine);
		cErrStr.append('\n');
	}

}
