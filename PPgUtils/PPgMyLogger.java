package org.phypo.Util;

import java.util.logging.Level;
import java.util.logging.Logger;




class MyLogger extends Logger{

    protected MyLogger(String arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public void info( String pStr ) { log( Level.INFO, pStr ); }
    
};
