package org.phypo.PPg.PPgUtils;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

//*************************************************

public class PPgLog{
	public static PrintStream Out =  System.out ;
	public static PrintStream Err =  System.err;

	public static PrintStream Dbg = new PrintStream( new OutputStream() {
		@Override
		public void write(int b) {
			//DO NOTHING
		}}
	);

	//Add file support !!!!
	static public boolean UseFile( String iFilename ){
		try {
			PrintStream lTmp = new PrintStream(  iFilename );
			if( Dbg == Out )
				Dbg = lTmp;
			Out = lTmp ;
			Err = lTmp ;
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	static public boolean UseDbgFile( String iFilename ){
		try {
			Dbg = new PrintStream(  iFilename );
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	static public boolean UseDbg( ){
		Dbg = Out;
		return true;
	}

}
//*************************************************
