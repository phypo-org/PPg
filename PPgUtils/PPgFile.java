package org.phypo.PPg.PPgUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

//****************************
public class PPgFile {

	//--------------------------------------------
	public static long CopyInputStreamToFile(InputStream iInputStream, File iFile, int iBufferSize)  throws IOException {

		long lTotalSize=0;
		try ( FileOutputStream lOutputStream = new FileOutputStream( iFile, false)) {
			int lSz;
			byte[] bytes = new byte[iBufferSize];
			while ((lSz = iInputStream.read(bytes)) != -1) {
				lOutputStream.write(bytes, 0, lSz);
				lTotalSize+=lSz;
			}
			lOutputStream.close();            
		}
		return lTotalSize;
	}
	//--------------------------------------------
	public static boolean writeFile( byte [] iBytes, File iFile ){


		FileOutputStream lOutputStream = null;
		try {
			lOutputStream = new FileOutputStream( iFile, false);
			lOutputStream.write(iBytes, 0, iBytes.length );
		} catch ( Exception e) {
			Log.Err(  "PPgFile.writeFile failed for : " + iFile.getAbsolutePath() + " - Exception : " + e.toString() );			
			return false;
		}
		finally{
			try {
				if( lOutputStream != null)
					lOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;   
	}
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

		try ( ByteArrayOutputStream  lAOS = new ByteArrayOutputStream( (int) ((lLengthFile/10)+32) );	
				GZIPOutputStream lGOS = new GZIPOutputStream(lAOS) ) {

			long lRead = Files.copy( iFile.toPath(), lGOS );
			lGOS.close();  

			lReturn.set2(lRead);

			if( lRead != lLengthFile ) {
				lReturn.set1( Long.valueOf( -1 ) );
			} else {
				lReturn.set1( lLengthFile );
				lReturn.set2( Long.valueOf( lAOS.size()) );
				lReturn.set3( lAOS.toByteArray() );
			}
			lAOS.close();
			return lReturn;
		}
	}
	//--------------------------------------------
	public static Tuple.Three< Long, Long, byte[]> Compress( byte iBytesIn[] ) throws IOException {

		long lLengthFile = iBytesIn.length;
		if( lLengthFile  == 0 )
			return null;
		//=======

		Tuple.Three<Long, Long, byte[]> lReturn = Tuple.Instance.new Three<>();

		try ( ByteArrayOutputStream  lAOS = new ByteArrayOutputStream( (int) ((lLengthFile/10)+32) );	
				GZIPOutputStream lGOS = new GZIPOutputStream(lAOS) ) {
			lGOS.write(iBytesIn);
			lGOS.finish();  
			lGOS.close();  

			lReturn.set1( lLengthFile );
			lReturn.set2( Long.valueOf( lAOS.size()) );
			lReturn.set3( lAOS.toByteArray() );
			lAOS.close();
		}

		return lReturn;
	}
	//==============================
	public static long Decompress( final byte iBytesIn[], File iFile) throws IOException {

		try ( InflaterInputStream lIIS    = new GZIPInputStream(new ByteArrayInputStream( iBytesIn ) )){
			long lSz = Files.copy( lIIS, iFile.toPath() );			
			lIIS.close();

			return lSz;
		}
	}
	//==============================
	public static  byte[] Decompress(byte iBytesIn[] ) throws IOException {

		try ( InputStream lIIS = new GZIPInputStream(new ByteArrayInputStream( iBytesIn))) {
			byte lData[] = lIIS.readAllBytes();
			lIIS.close();

			Log.Dbg4( "PPgFile.Decompress size : " + lData.length );

			return lData;
		}
	}
	//==============================
	public static long aaaDecompress( byte iBytesIn[], File iFile) throws IOException {

		InflaterInputStream lIIS    = new InflaterInputStream(new ByteArrayInputStream( iBytesIn ) );
		return Files.copy( lIIS, iFile.toPath() );	
	}

	//==============================
	public static  byte[] aaaaDecompress( byte iBytesIn[] ) throws IOException {

		InputStream lIIS = new InflaterInputStream(new ByteArrayInputStream( iBytesIn));
		ByteArrayOutputStream lBAOS = new ByteArrayOutputStream();
		lIIS.transferTo(lBAOS);
		return lIIS.readAllBytes();
	}
}
//****************************
