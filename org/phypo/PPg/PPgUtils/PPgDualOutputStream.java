package org.phypo.PPg.PPgUtils;

import java.io.IOException;
import java.io.OutputStream;


// Duplique un flux en entree sur deux flux en sortie

//*********************************************************************
public class PPgDualOutputStream  extends OutputStream  {

		OutputStream cA=null;
		OutputStream cB=null;


		//---------------------------
		public PPgDualOutputStream( OutputStream pA,  OutputStream pB)  {
				cA = pA;
				cB = pB;
		}

		// --- Redefinitions pour OutputStream ---
		@Override
		public void close() throws IOException {
				try{
						cA.close();
						cB.close();
				}
				catch(IOException e )
						{
								throw e;
						}
		}
		//---------------------------

		@Override
		public void flush() throws IOException {
				try{
						cA.flush();
						cB.flush();
				}
				catch(IOException e )
						{
								throw e;
						}
		}
		//---------------------------
		@Override
		public void write( int b ) throws IOException {
				try{
						cA.write(b);
						cB.write(b);
				}
				catch(IOException e )
						{
								throw e;
						}
		}
		//---------------------------
		@Override
		public void write( byte[] b ) throws IOException {
				try{
						cA.write( b );
						cB.write( b );
				}
				catch(IOException e )
						{
								throw e;
						}
		}
		//---------------------------

		@Override
		public void write( byte[] b, int off, int len ) throws IOException {
				try{
						cA.write( b, off, len );
						cB.write( b, off, len );
				}
				catch(IOException e )
						{
								throw e;
						}
		}
}
//*********************************************************************
