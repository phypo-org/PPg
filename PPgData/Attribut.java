package org.phypo.PPg.PPgData;




import java.util.*;


//*************************************************
// Permet de gerer les attribut pour une entite
//*************************************************

public class Attribut{

		AttributProto cProto = null;

		double        cVal = 0;       // la valeur courante !
		double        cValMax = 0;       // la valeur courante !
		double        cDelta = 0;     // la progression par seconde s'il y a lieu


		//-----------------------------------------

		public Attribut( AttributProto pProto  ) {

				cProto  = pProto;
		}
		//-----------------------------------------

		public Attribut( Attribut pSrc ) {

				cProto  = pSrc.cProto;
				cValMax = pSrc.cValMax;
				cVal    = pSrc.cVal;
				cDelta  = pSrc.cDelta;
		}
		//------------------------------------------------
		// Lecture depuis le .ini
		public boolean  init(  String pStr) {
				
				try {
						char cCmd = '>'; // max
						StringTokenizer lTok=new StringTokenizer( pStr, "," );

						//						System.out.print( "Attribut.init " + pStr );

						while( lTok.hasMoreTokens() ) {
								String lVal = lTok.nextToken();
								if( lVal != null ) {
										//										System.out.print( " Token:" + lVal );

										if( Character.isDigit( lVal.charAt( 0 )) == false ) {
												cCmd = 	lVal.charAt( 0 );
												lVal = lVal.substring( 1 );
										}	
										//										System.out.print( " Token:" + lVal );


										float lTmpVal = Float.parseFloat(  lVal );
										switch( cCmd ) {
										case '>' : cValMax = lTmpVal; break;
										case '+' : cDelta  = lTmpVal; break;
										case '-' : cDelta  = -lTmpVal; break;
										case '=' : cVal    = lTmpVal; break;
										default:
												System.err.println( "Attibut.init : bad format for " +  pStr ); 
												return false;
										}
								}
						}														
				} catch( Exception ex ) {
						System.err.println( "Attibut.init : execption for " +  pStr ); 
				}
				return false;
		}
		//------------------------------------------------
		public String toString() {
				return			cProto.cName 
						+ " max:" + cValMax 
						+ " current:" + cVal
						+ " delta:" + cDelta;					 
		}
}
//*************************************************
