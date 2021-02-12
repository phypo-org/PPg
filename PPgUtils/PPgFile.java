package org.phypo.PPg.PPgUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

//****************************
public class PPgFile {

	//==============================
	// Compresse un fichier dans un tableau de byte 
	// Tuple 1 -> taille du fichier
	// Tuple 2 -> taille compressé (ou alors byte.size !)
	// Tuple 3 -> byte[]
	//==============================

	public static Tuple.Three< Long, Long, byte[]> Compress( File iFile ) throws IOException {

		long lLengthFile = iFile.length();
		if( lLengthFile  == 0 )
			return null;
		//=======

		Tuple.Three<Long, Long, byte[]> lReturn = Tuple.Instance.new Three<>();

		ByteArrayOutputStream  lAOS = new ByteArrayOutputStream( (int) ((lLengthFile/10)+32) );	
		GZIPOutputStream lGOS = new GZIPOutputStream(lAOS);

		long lRead = Files.copy( iFile.toPath(), lGOS );
		lGOS.close();  

		lReturn.set2(lRead);

		if( lRead != lLengthFile ) {
			lReturn.set1( Long.valueOf( -1 ) );
			return lReturn;
		}	
		lReturn.set1( lLengthFile );
		lReturn.set2( Long.valueOf( lAOS.size()) );	
		lReturn.set3( lAOS.toByteArray() );

		return lReturn;
	}
	//==============================
	public static long Decompress( byte iBytesIn[], File iFile) throws IOException {

		InflaterInputStream lIIS    = new InflaterInputStream(new ByteArrayInputStream( iBytesIn ) );
		return Files.copy( lIIS, iFile.toPath() );	
	}

	//==============================
	public static  byte[] Decompress( byte iBytesIn[] ) throws IOException {

		InputStream lIIS = new InflaterInputStream(new ByteArrayInputStream( iBytesIn));
		ByteArrayOutputStream lBAOS = new ByteArrayOutputStream();
		lIIS.transferTo(lBAOS);
		return lIIS.readAllBytes();
	}
}
//****************************
