package org.phypo.PPg.PPgWin;


import java.io.OutputStream;

import javax.swing.JTextArea;

// Redirige un Stream vers un JTextArea.
//   Utilisation !
//   trace = new TraceAreaStream( 5, 80 );
//   out = new PrintStream( trace );

//*********************************************************************
public class PPgTextStream extends OutputStream {
  
		JTextArea cText = null;
		StringBuffer cStrBuf;
		
		//---------------------------
		public PPgTextStream( JTextArea pText ) {
				cText   = pText;
				cStrBuf = new StringBuffer();
		}
		
		
		// --- Redefinitions pour OutputStream ---
		public void close(){
				;
		}
		
		//---------------------------

		public void flush() {
				cText.append( cStrBuf.toString() );
				//	cText.setCaretPosition( cText.

				cStrBuf.setLength(0);
		} 
		
		//---------------------------
		public void write( int b ){    
				cStrBuf.append( b );
				if( b == '\n' )
						flush();						
		}
		
		//---------------------------
		public void write( byte[] b ){
				
				for( int i=0; i< b.length; i++) {						
						cStrBuf.append((char)b[i] );
						if( (char)b[i] == '\n')						
								flush();								
				}				
		}
		//---------------------------
		
		public void write( byte[] b, int off, int len ){
				
				for( int i=0; i< len; i++) {						
						cStrBuf.append((char)b[i] );
						if( (char)b[i] == '\n')						
								flush();								
				}				
		}
		
    //---------------------------
		public JTextArea getTextArea() { return cText; }
};
//*********************************************************************
