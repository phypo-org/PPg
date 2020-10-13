package org.phypo.PPg.PPgUtils;


import java.util.*;


//*************************************************

public class PPgUtils{

		//--------------------------------------------
		public static String DupliqueChar( String pSrc, char pChar ){

				int lLength = pSrc.length();
				StringBuilder pBuffer = new StringBuilder( lLength + 8 );
				
				for( int i=0 ; i < lLength; i++ ) {

						char c = pSrc.charAt(i);

						if( c == pChar )
								pBuffer.append(pChar);

						pBuffer.append( c );
				}
				
				return pBuffer.toString();
		}
		//--------------------------------------------
		public static String DupliqueChar( char pSrc, char pChar ){

				StringBuilder pBuffer = new StringBuilder( 2 );

				if( pSrc == pChar )
						pBuffer.append(pChar);

				pBuffer.append( pSrc );
				
				return pBuffer.toString();
		}
		//--------------------------------------------
		public static Collection<String> Split( String pStr, Collection<String> pCollect, String pToeat, String pSep ) {
				
				PPgToken lTok = new PPgToken(  pStr, pToeat, pSep );

				StringBuilder lStr;

				while( (lStr=lTok.nextToken())!= null){
						pCollect.add(lStr.toString());						
				}
				return pCollect ;
		}
}
//*************************************************
