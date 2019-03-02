package org.phypo.PPg.PPgUtils;




//***********************************
public class PPgTrace {

		public static int sVerbose=0;
		public static void Traceln( int pVerbose, String pToTrace ){
				if( sVerbose >= pVerbose  )
						System.out.println( pToTrace );
		}
		//-----------------------------
		public static void Traceln( String pToTrace ){
				System.out.println( pToTrace );
		}		
};
//***********************************
