package org.phypo.PPg.PPgUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Collection;


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
	public static String DupQuote( String iStr ) {
		return PPgUtils.DupliqueChar(iStr,'\'');
	}
	//--------------------------------------------
	public static String DupDoubleQuote( String iStr ) {
		return PPgUtils.DupliqueChar(iStr,'"');
	}
	//--------------------------------------------
	public static String QuoteWith( String pSrc, char pChar ){

		int lLength = pSrc.length();
		StringBuilder pBuffer = new StringBuilder( lLength + 8 );

		pBuffer.append(pChar);
		for( int i=0 ; i < lLength; i++ ) {

			char c = pSrc.charAt(i);

			if( c == pChar )
				pBuffer.append(pChar);

			pBuffer.append( c );
		}
		pBuffer.append(pChar);

		return pBuffer.toString();
	}
	//--------------------------------------------
	public static String Quote( String iStr ) {
		return QuoteWith(iStr,'\'');
	}
	//--------------------------------------------
	public static String DoubleQuote( String iStr ) {
		return QuoteWith(iStr,'"');
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

	//--------------------------------------------
	public static boolean CheckAscii (byte iArray[]) {
		for (byte element : iArray) {
			if (element < 0) return false;
		}
		return true;
	}
	//--------------------------------------------
	public static String HexDump(String iStr, StringBuilder iBuilder) {
		byte [] lArray = Base64.getDecoder().decode(iStr);

		return HexDump(lArray, iBuilder);
	}
	//---------------------
	public static String HexDump( byte iArray[] , StringBuilder iBuilder) {

		final int lWidth = 16;
		int lLength = iArray.length;

		//==========================
		for (int lOffset = 0; lOffset <  lLength; lOffset += lWidth) {
			iBuilder.append(String.format("%04d: ", lOffset));

			for (int index = 0; index < lWidth; index++) {
				if (lOffset + index < lLength) {
					iBuilder.append( String.format("%02x ", iArray[lOffset + index]));
				} else {
					iBuilder.append("   ");
				}
			}


			//	if (lOffset < lLength)
			{
				iBuilder.append("| ");
				for (int index = 0; index < lWidth; index++) {
					if (lOffset + index < lLength) {
						byte lVal= iArray[lOffset + index];
						if( lVal < 32 || lVal > 127 )
							lVal = '.';
						iBuilder.append((char)lVal );
					}
					else {
						iBuilder.append(' ' );
					}
				}
				/*
					try {
						iBuilder.append(new String(iArray, lOffset, asciiWidth, "UTF-8").replaceAll("\r\n\t", ".").replaceAll("\n", "."));
					} catch (UnsupportedEncodingException ignored) {
						//If UTF-8 isn't available as an encoding then what can we do?!
					}
				 */
			}
			iBuilder.append(String.format("%n"));
		}
		//==========================

		return iBuilder.toString();
	}
	//-------------------------------
	// lit le stream et met le resultat dans le StringBuilder
	public static boolean Read( String iTag, InputStreamReader iInput, BufferedReader iReader, StringBuilder iBuild ) throws IOException {

		boolean lFlagRead = false;

		while( iInput.ready()) {
			iBuild.append( iTag );
			iBuild.append( iReader.readLine() );
			iBuild.append( '\n' );
			lFlagRead = true;
		}
//////		Log.Dbg3(" PPgUtils.Read ->" + iBuild.toString());
		if( lFlagRead) {
			return true;
		}
		return false;
	}

}
//*************************************************
