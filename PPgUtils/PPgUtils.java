package org.phypo.PPg.PPgUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	//--------------------------------------------
	public static boolean CheckAscii (byte iArray[]) {
		for (int i=0; i< iArray.length; i++) {
			if (iArray[i] < 0) return false;
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
	//--------------------------------------------
	public static long CopyInputStreamToFile(InputStream iInputStream, File iFile, int iBufferSize)  throws IOException {

        long lTotalSize=0;
               try ( FileOutputStream outputStream = new FileOutputStream( iFile, false)) {
            int lSz;
            byte[] bytes = new byte[iBufferSize];
            InputStream inputStream;
            while ((lSz = iInputStream.read(bytes)) != -1) {
            	outputStream.write(bytes, 0, lSz);
            	lTotalSize+=lSz;
            }
        }
        return lTotalSize;
	}
}
//*************************************************
