package org.phypo.PPg.PPgWin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// **********************************

public class PPgFrameLog{

		String cName;
		protected ByteArrayOutputStream    cArrayStream=null;
		protected PrintStream              cPrintStream=null;
	
		PPTraceTerm cTerm=null;
		public PrintStream stream() { return cPrintStream;}

		//-----------------------
		public PPgFrameLog( String pName ){
				cName =  pName ;
				cArrayStream = new ByteArrayOutputStream();
				cPrintStream = new PrintStream(cArrayStream);				
		}
		//-----------------------
		public void destroy(){
				cArrayStream = null;
				cPrintStream = null;
				cTerm = null;
		}
		//-----------------------
		public void open(){
				if( cTerm == null ){
						cTerm = new PPTraceTerm( cName );
						cTerm.getLog().setText( cArrayStream.toString() );
						cPrintStream = cTerm.stream();
						cArrayStream = null;
				}	
				cTerm.front();
		}
		//-----------------------
		public void close(){
				if( cTerm != null ){
						cTerm.hide();
						cArrayStream = new ByteArrayOutputStream();
						String lStr = cTerm.getLog().getText();
						PPgAppli.TheAppli.removeChild( cTerm );
						cTerm = null;
						cArrayStream.write( lStr.getBytes(), 0, lStr.length());
						cPrintStream = new PrintStream(cArrayStream);											
				}			 
		}
		//-----------------------


		
};
// **********************************
