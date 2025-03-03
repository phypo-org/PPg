import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;

//*************************************************
// Creation d'une unique pixmap a partir de plusieurs representant une animation
// detection de la couleur de transparence, et essai de compression
//*************************************************
class Sequence{
		ImageIcon [] cVectImg = new ImageIcon[128];

		int cMaxWidth = 0;
		int cMaxHeight = 0;
		int cMinWidth = 2000;
		int cMinHeight = 2000;

		int cMaxMap=0;
		
		Sequence() {
				for( int i=0; i< 128 ; i++ )
						cVectImg[i] = null;
		}
		boolean add( int pIndex, File pFile , WorkDir pWorkDir ){
				ImageIcon lIconImg = null;

				System.out.println( "Sequence.add " + pIndex );

				if( pFile.getName().endsWith( ".bmp" ) == true ){
						Image lImage = CompressPixmap.Loadbitmap( pFile);
						if( lImage == null ){
								System.out.println( "Error Sequence.add "+ pFile.getPath() );
								return false;
						}
						lIconImg = new ImageIcon( lImage );
						ImageIcon lTmpImageIcon = CompressPixmap.ForceTransparencie( lIconImg );
						if( lTmpImageIcon != null ){								
								lIconImg = lTmpImageIcon;
						}								 

						// On pourrait ajouter une detection du format le plus petit possible 
						// enlever les pixels inutile
				}
				else
						lIconImg = new 	ImageIcon(pFile.getPath());

				if( lIconImg != null )
						cVectImg[pIndex] = lIconImg;

				if( pIndex > cMaxMap )
						cMaxMap = pIndex;
				
				if( lIconImg.getIconWidth() >  cMaxWidth )
						cMaxWidth = lIconImg.getIconWidth();
				if( lIconImg.getIconHeight() >  cMaxHeight )
						cMaxHeight = lIconImg.getIconHeight();

				if( lIconImg.getIconWidth() <  cMinWidth )
						cMinWidth = lIconImg.getIconWidth();
				if( lIconImg.getIconHeight() <  cMinHeight )
						cMinHeight = lIconImg.getIconHeight();

				return true;
		}
		//------------------------------------------------
		int testCompress() {
				
				int lMin = 2000;
				for( int i=0; i<= cMaxMap ; i++ ){
						lMin = Math.min( CompressPixmap.TestCompress( cVectImg[i] ), lMin);
						//						System.out.println( " compress:" + lMin );
						if( lMin <= 0 )
								return lMin;
				}
				return lMin;
		}
		//------------------------------------------------
		void draw( Graphics2D lG, int pY, int pWidth, int pCompress ){

				for( int i=0; i<= cMaxMap ; i++ ){
						ImageIcon lImg = cVectImg[i];
						lG.drawImage( lImg.getImage(), i*pWidth, pY,
													lImg.getIconWidth()-pCompress*2,
													lImg.getIconHeight()-pCompress*2,
													null); 
				}
		}		
};
//*************************************************
class Action{

		//=-------------------------
		enum Orientation {
				POS_NORTH("n", 0, 0.0),
				POS_NE   ("ne",    1, 45.0),
				POS_EAST ("e",  2, 90.0),
				POS_SE   ("se",    3, 135.0),
				POS_SOUTH("s", 4, 180.0),
				POS_SW   ("sw",    5, 225.0),
				POS_WEST ("w",  6, 270.0),
				POS_NW   ("nw",    7, 315.0),
				POS_NONE ("none",  8, 0);
				
				String cName=null;
				int    cCode=-1;
				double cAngle=0;
				
				public String getName() { return cName; }
				public int    getCode() { return cCode; }
				public double getAngle() { return cAngle; }
				
				Orientation( String pName, int pCode, double pAngle ) { 
						cName = pName;
						cCode = pCode;
						cAngle = pAngle;
				}
				
				
				public static final HashMap<String,Orientation > sHashOrientation = new HashMap<String,Orientation>();
				public static final Orientation []sTabOrientation = new Orientation[9];
				static {
						for( Orientation  lPoint: Orientation.values() ) {
								sHashOrientation.put( lPoint.cName, lPoint );
								sTabOrientation[lPoint.cCode] = lPoint;
						}
				}
				
				static public Orientation FindByName( String pName ) { return sHashOrientation.get( pName ); }
				static public Orientation Get( int i ) { return sTabOrientation[i];}
		}
		//=-------------------------
		

		

		String cAction;
		Sequence cVectDir[] = new Sequence[32];
		

		Action( String pAction ){
				cAction = pAction;
				for( int i=0; i< 32 ; i++ )
						cVectDir[i] = null;

		}

		void destroy( String pAction ){
				for( int i=0; i< 32 ; i++ ){
						cVectDir[i] = null;
				}
		}

		boolean add( String pStrDir, int pIndex, File pFile , WorkDir pWorkDir ){

				Orientation lOrient = Orientation.FindByName( pStrDir );
				if( lOrient == null ){
						System.out.println( "Error Orientation " + pStrDir );
						return false;
				}
				if( cVectDir[lOrient.getCode()] == null ){
						cVectDir[lOrient.getCode()] = new Sequence();
				}
				System.out.println( "Action.add " + pStrDir + " " + pFile + " " + pIndex );

				return cVectDir[lOrient.getCode()].add( pIndex, pFile,  pWorkDir);
		}

		//------------------------------------------------
		
		boolean fusion( WorkDir pWorkDir ){				

				

				int lMaxWidth = 0;
				int lMaxHeight = 0;
				int lMinWidth = 2000;
				int lMinHeight = 2000;

				int lMaxSeq =0;
				int lMaxMap =0;

				System.out.println( "*****************************************" );

				//========================================
				for( int i=0; i< 32 ; i++ ){
						if( cVectDir[i] == null )
								continue;
						

						Sequence lSeq = cVectDir[i];	
						
						if( lSeq.cMaxWidth >  lMaxWidth )
								lMaxWidth = lSeq.cMaxWidth;
						if( lSeq.cMaxHeight >  lMaxHeight )
								lMaxHeight = lSeq.cMaxHeight;
						
						if( lSeq.cMinWidth <  lMinWidth )
								lMinWidth = lSeq.cMinWidth;
						if( lSeq.cMinHeight <  lMinHeight )
								lMinHeight = lSeq.cMinHeight;					
						
						
						if( lSeq.cMaxMap > lMaxMap )
								lMaxMap = lSeq.cMaxMap;
						
						System.out.println( "Action.fusion " + i  );

						lMaxSeq++;
				}

				if( lMaxWidth != lMinWidth || lMinHeight != lMaxHeight ){
						System.out.println( "Error bad size for map" );
						return false;
				}

				lMaxMap++;

				System.out.println( "Action.fusion lMaxMap:" + lMaxMap +  " lMaxSeq:" + lMaxSeq
														+ " lMaxWidth:" + lMaxWidth + "lMaxHeight:" +lMaxHeight );

				if( lMaxWidth*lMaxMap == 0 ||  lMaxHeight*lMaxSeq == 0){
						System.out.println( "Pixmap is void for " + pWorkDir.cPathIn );
				}
				//========================================




				int lCompress = 0;

				//========================================
				// phase de modification de la taille 
				// des pixmap (en s'alignant sur la plus grande ! 
				if( CompressPixmap.Compress ){
						System.out.print( " ++++ Compress ");
	
						int lMinCompress= 10000;
						
						for( int i=0; i< 32 ; i++ ){
								if( cVectDir[i] == null )
										continue;
								
								
								Sequence lSeq = cVectDir[i];	
								lMinCompress = Math.min( lSeq.testCompress(), lMinCompress );
								System.out.print( " +" + lMinCompress + " ");

								if( lMinCompress <= 0 )
										break;
						}

						if( lMinCompress > 0 ){								
								// On peut enlever n pixel de chaque cote de l'image
								lCompress = lMinCompress;						
								System.out.println( " >>>> " + lCompress );
						}
				}
				//========================================
				

				lMaxWidth  -= lCompress*2;
				lMaxHeight -= lCompress*2;

				System.out.println( lMaxWidth +"x" + lMaxHeight + "   "+ lMaxWidth*lMaxMap +"x"+ lMaxHeight*lMaxSeq);
				String lStrSz = new String( "_"+lMaxMap+"x"+lMaxSeq+"-"+lMaxWidth+"x"+lMaxHeight );

				//========================================
				// Maintenant on va vraiment essayer de fusioner les pixmaps

				BufferedImage lBufOut = new BufferedImage( lMaxWidth*lMaxMap, lMaxHeight*lMaxSeq, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D g = lBufOut.createGraphics();
				g.setComposite( AlphaComposite.Src );
				g.setColor( Color.blue );
				g.fillRect( 0, 0, lMaxWidth*lMaxMap*2, lMaxHeight*lMaxSeq*2 );
				
				int lY=0;
				for( int i=0; i< 32 ; i++ ){
						if( cVectDir[i] == null )
								continue;
						
						Sequence lSeq = cVectDir[i];	
						
						lSeq.draw( g, lY*lMaxHeight, lMaxWidth, lCompress  ); 

						lY++;
				}
				g.dispose();
				
				File lFilePathOut=  pWorkDir.cPathOut;
				lFilePathOut.mkdirs();
				File lNewName =  new File( lFilePathOut,  CompressPixmap.GoodName(cAction)+lStrSz+".png");

				System.out.println( "lNewName:" + lNewName + " Parent:" + lFilePathOut.getParent());	

				//				lFilePathOut = new File( lFilePathOut.getParent() );
				//lNewName.mkdirs();						

				
				if( CompressPixmap.WriteImage( lBufOut, lNewName, "png" ) == false ){
						System.out.println( "Error write failed for " + lNewName );	
				}

				for( int i=0; i< 32 ; i++ ){
						if( cVectDir[i] == null )
								continue;						
						cVectDir[i] = null;
				}
					
				return true;
		}
};
//*************************************************
class WorkDir{
		
		File cFile=null;
		File cPathIn;
		File cPathOut;

		TreeSet<File> cSetFile = new TreeSet<File>();
		HashMap<String,Action> cMapAction = new HashMap<String,Action>();

		WorkDir( File pFile, File pPathIn, File pPathOut ){
				cFile = pFile;
				cPathIn = pPathIn;
				cPathOut = pPathOut;

				System.out.println( ">>>>>WorkDir pPathIn:" + pPathIn + " cPathOut:" + cPathOut );
		}

		void add( File pFile) {
				cSetFile.add( pFile );
		}

		int size(){
				return cSetFile.size();
		}

		void createAllPixmap(){
				Iterator<File> lIter = cSetFile.iterator();


				String lOldAction = "";
				String lOldDir ="";
				
				while( lIter.hasNext() ){
						File lFile = lIter.next();
						String lFileName = lFile.getName();
						System.out.println( "File:" + lFileName );
						
						String lBegining = lFileName.substring( 0, lFileName.lastIndexOf( '.' ));
						System.out.print( " Begin:" + lBegining );
						
						String lNum    = lBegining.substring( lBegining.length()-CompressPixmap.SizeNum );
						System.out.print( " lNum:" + lNum );	
						
						 lBegining    = lBegining.substring( 0, lBegining.length()-CompressPixmap.SizeNum);
						System.out.print( " Begin2:" + lBegining );
						
						String lStrAction = "none";
						String lStrDir  = null;

						if( CompressPixmap.Direction ){
								
								int lIndex     = lBegining.lastIndexOf( ' ' );
								if( lIndex == -1 )
										lIndex     = lBegining.lastIndexOf( '_' );
								if( lIndex != -1 ){

										lStrAction = lBegining.substring( 0, lIndex);
										lStrDir  = lBegining.substring( lIndex+1 );										
								}
								
								System.out.print( " lStrAction:" + lStrAction );								
								
								if( lStrDir == null )
										lStrDir = "none";

								System.out.println( " lStrDir:" + lStrDir);

								Action lAction = cMapAction.get(lStrAction);
								if( lAction == null ) {
										lAction = new Action( lStrAction );
										cMapAction.put( lStrAction, lAction );
								}

								int lIndexImg = Integer.valueOf( lNum );
								if( lAction.add( lStrDir, lIndexImg, lFile, this ) == false ){
										System.out.println( "Error for work :" + cFile );
										return ;
								}										
						}							 
				}				
		}	
		
		//------------------------------------------------		
		boolean fusionPixmap(){
				Iterator<Action> lIterAction = cMapAction.values().iterator();
				while( lIterAction.hasNext() ){
						Action lAction = lIterAction.next();
						if( lAction.fusion( this ) == false ){
								System.out.println( "Error fusionPixmap :" + cPathIn );
						}								
				}
				return true;
		}
};

//*************************************************

public class CompressPixmap{

		static boolean Verbose    = false;
		static boolean Compress   = false;
		static boolean Transparencie=false;
		static boolean Direction  = true;
		static int     SizeNum    = 4;

		//---------------------------------
		static String GoodName( String pString ){
				
				StringBuilder lTmpStr = new StringBuilder( pString.length()+1 );																													 
				for( int k=0; k<pString.length(); k++ ){
						char c =  pString.charAt(k);
						if( c == ' ' || c == '\t' || c == '\n' )
								lTmpStr.append( '_' );
						else {										
								if( Character.isLetterOrDigit( c ) || 'c' == '_' || 'c' == '-' || c=='/' || c=='\\' || c == ':')
										lTmpStr.append( c );												
								else
										lTmpStr.append( '_' );												
						}
				}
				return  lTmpStr.toString();
		}				
		//---------------------------------
		static void  Process( File pPathIn, File pPathOut,  WorkDir pCurrentWorkDir ){


				File lFile = pPathIn;
				
				if( lFile.isDirectory() == true ){													

						WorkDir lNewWorkDir = new WorkDir( lFile, pPathIn, pPathOut );

						String lEntry[] = lFile.list( );
						
						for( int i=0; i< lEntry.length; i++) {								
								File lPathIn  = new File( pPathIn , lEntry[i]);
								File lPathOut = null;

								if( lPathIn.equals( lPathOut ) == false )
										lPathOut = new File( pPathOut, GoodName(lEntry[i]));
								else
										lPathOut = new File( pPathOut, lEntry[i]);
																										 								
								Process( lPathIn, lPathOut, lNewWorkDir );								
						}					
						
						// On a fait tout les fichiers !
						if( lNewWorkDir.size() > 1 ){
								lNewWorkDir.createAllPixmap();
								lNewWorkDir.fusionPixmap();	

						}
				}
	   	else {						
						if( lFile.getName().endsWith( ".bmp" ) == false )
								return ;

						if( pCurrentWorkDir != null )
								pCurrentWorkDir.add( lFile);						
				}				
		}
		//------------------------------------------------
		static public BufferedImage GetBufferedImage( ImageIcon pSrc ){
				BufferedImage lBuf = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE				
				Graphics2D g = lBuf.createGraphics();
				g.setComposite( AlphaComposite.Src );
				g.drawImage( pSrc.getImage(), 0, 0,  null); 
				g.dispose();
				return lBuf;
		}
		//------------------------------------------------
		static public boolean WriteImage( BufferedImage pImage, File pFile, String pFormat ){
				
				Iterator lIterWriters = ImageIO.getImageWritersByFormatName(pFormat);
				ImageWriter lWriter = (ImageWriter)lIterWriters.next();				
				
				try {						
						ImageOutputStream llIOs = ImageIO.createImageOutputStream(pFile);
						lWriter.setOutput(llIOs);
											 						
						lWriter.write(pImage);
				}
				catch(Exception e){
						System.out.println( e );
						return false;
				}
				return true;
		}
		//------------------------------------------------
		static public ImageIcon ForceTransparencie( ImageIcon pSrc ){

				
				int w = pSrc.getIconWidth();
				int h = pSrc.getIconHeight();
				System.out.println( "w*h="  w*h );
				int [] lPixels = new int [w*h];
				PixelGrabber pg = new PixelGrabber( pSrc.getImage(), 0, 0, w, h, lPixels, 0, w);
				try{
						pg.grabPixels();
				} catch( InterruptedException e){
						System.out.println( "ForceTransparencie exception: " + e );
						return null;
				}
				
				if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
						System.out.println("ForceTransparencie image fetch aborted or errored");
						return null;
				}


				// On par du principe qu'en general les coins du sprite sont vides,
				// on va donc essayer de determiner la valeur transparente en prenant comme
				// valeur l'un d'entre eux
				
				int lTransparent = 0;
				if( lPixels[0] == lPixels[w*h-1] ){
						lTransparent = lPixels[0];
				}
				else 	if( lPixels[0] == lPixels[w-1] ){
						lTransparent = lPixels[0];
				}
				else	if( lPixels[w-1] == lPixels[w*h-1] ){
						lTransparent = lPixels[w-1];
				} 	else return null;
				
				lTransparent = lPixels[0];
				
				//				System.out.printf( "lTransparent:%1$x", lTransparent );
				//				System.out.println();
				
				for( int y=0; y<h; y++ ){
						//						System.out.print( "\t"+y+"\t");
						for( int x=0; x<w; x++){
								
								
								int lPixel = lPixels[x+y*w];
								//								System.out.printf( "%1$x ", lPixel );
								
								if( lPixel == lTransparent ) {
										//										System.out.print( "-");
										lPixels[x+y*w] =0; // on met alpha a 0 !			
								}

								//								if( x == y )
								//										lPixels[x+y*w] =    0xffffff;
						}
						//								System.out.println();
				}
				
				MemoryImageSource lMis = new MemoryImageSource( w, h, lPixels, 0, w );
				lMis.setAnimated( false );
				Image lImage = Toolkit.getDefaultToolkit().createImage( lMis );
				
				return new ImageIcon( lImage );
    }
		//------------------------------------------------
		static public int TestCompress( ImageIcon pSrc ){
				
				int w = pSrc.getIconWidth();
				int h = pSrc.getIconHeight();
				
				int [] lPixels = new int [w*h];
				PixelGrabber pg = new PixelGrabber( pSrc.getImage(), 0, 0, w, h, lPixels, 0, w);
				try{
						pg.grabPixels();
				} catch( InterruptedException e){
						System.out.println( "TestCompress exception: " + e );
						return 0;
				}
				
				if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
						System.out.println("TestCompress image fetch aborted or errored");
						return 0;
				}
				

				// On par du principe qu'en general les coins du sprite sont vides,
				// on va donc essayer de determiner la valeur transparente en prenant comme
				// valeur l'un d'entre eux
				
				int lTransparent = 0;
				if( lPixels[0] == lPixels[w*h-1] ){
						lTransparent = lPixels[0];
				}
				else 	if( lPixels[0] == lPixels[w-1] ){
						lTransparent = lPixels[0];
				}
				else	if( lPixels[w-1] == lPixels[w*h-1] ){
						lTransparent = lPixels[w-1];						
				} 	
				else {
						System.out.println( " testCompress : no transparent found " );
						return 0;
				}
				
				lTransparent = lPixels[0];

				// on va tourner autour de l image en reduisant de 1 pixel a la fois
				// jusqu a ce qu'on trouver un pixel non transapent !, on renverra
				// alors la valeur du dernier tour qui a marche
				
				int lLimite = Math.min( (w-1)/2, (h-1)/2 ); // a peu pres le plus petit des milieux

				//				System.out.println( " limite  : "+ lLimite );

				for( int lVal=0; lVal< lLimite; lVal++ ){ 
						
						//						System.out.println( " lVal  : "+ lVal + " x:" + lVal +" x:" +((w-1)-lVal) );
						for( int y=lVal; y<h-lVal; y++ ){

								//			System.out.print( " y:" + y  );
								
								if( lPixels[lVal+y*w] != lTransparent)
										return lVal-1;
								if( lPixels[((w-1)-lVal)+y*w] != lTransparent)
										return lVal-1;								
						}
						//						System.out.println( " lVal  : "+ lVal +  " y:" + lVal +" y:" +((h-1)-lVal));
								 // On teste les horizontales
						for( int x=lVal; x<w-lVal; x++ ){
								
								//	System.out.print( " x:" + x  );

								if( lPixels[x+(lVal*w)] != lTransparent)
										return lVal-1;
								if( lPixels[x+((h-1)-lVal)*w] != lTransparent)
										return lVal-1;								
						}
						//						System.out.println();
				}

				return 0;
    }
		//------------------------------------------------
		
		static public Image Loadbitmap (File pFile)
		{	
				Image image;
				//System.out.println("loading:"+pFile);
				try
						{
								FileInputStream fs=new FileInputStream(pFile);
								int bflen=14;  // 14 byte BITMAPFILEHEADER
								byte bf[]=new byte[bflen];
								fs.read(bf,0,bflen);
								int bilen=40; // 40-byte BITMAPINFOHEADER
								byte bi[]=new byte[bilen];
								fs.read(bi,0,bilen);
								
								// Interperet data.
								int nsize = (((int)bf[5]&0xff)<<24)
										| (((int)bf[4]&0xff)<<16)
										| (((int)bf[3]&0xff)<<8)
										| (int)bf[2]&0xff;
								//								System.out.println("BMP File type is :"+(char)bf[0]+(char)bf[1]);
								//								System.out.println("BMP Size of file is :"+nsize);
								
								int nbisize = (((int)bi[3]&0xff)<<24)
										| (((int)bi[2]&0xff)<<16)
										| (((int)bi[1]&0xff)<<8)
										| (int)bi[0]&0xff;
								//								System.out.println("BMP Size of bitmapinfoheader is :"+nbisize);
								
								int nwidth = (((int)bi[7]&0xff)<<24)
										| (((int)bi[6]&0xff)<<16)
										| (((int)bi[5]&0xff)<<8)
										| (int)bi[4]&0xff;
								//								System.out.println("BMP Width is :"+nwidth);
								
								int nheight = (((int)bi[11]&0xff)<<24)
										| (((int)bi[10]&0xff)<<16)
										| (((int)bi[9]&0xff)<<8)
										| (int)bi[8]&0xff;
								//								System.out.println("BMP Height is :"+nheight);
								
								int nplanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;
								//								System.out.println("BMP Planes is :"+nplanes);
								
								int nbitcount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;
								//								System.out.println("BMP BitCount is :"+nbitcount);
								
								// Look for non-zero values to indicate compression
								int ncompression = (((int)bi[19])<<24)
										| (((int)bi[18])<<16)
										| (((int)bi[17])<<8)
										| (int)bi[16];
								//								System.out.println("BMP Compression is :"+ncompression);
								
								int nsizeimage = (((int)bi[23]&0xff)<<24)
										| (((int)bi[22]&0xff)<<16)
										| (((int)bi[21]&0xff)<<8)
										| (int)bi[20]&0xff;
								//								System.out.println("BMP SizeImage is :"+nsizeimage);
								
								int nxpm = (((int)bi[27]&0xff)<<24)
										| (((int)bi[26]&0xff)<<16)
										| (((int)bi[25]&0xff)<<8)
										| (int)bi[24]&0xff;
								//								System.out.println("BMP X-Pixels per meter is :"+nxpm);
								
								int nypm = (((int)bi[31]&0xff)<<24)
										| (((int)bi[30]&0xff)<<16)
										| (((int)bi[29]&0xff)<<8)
										| (int)bi[28]&0xff;
								//								System.out.println("BMP Y-Pixels per meter is :"+nypm);
								
								int nclrused = (((int)bi[35]&0xff)<<24)
										| (((int)bi[34]&0xff)<<16)
										| (((int)bi[33]&0xff)<<8)
										| (int)bi[32]&0xff;
								//								System.out.println("BMP Colors used are :"+nclrused);
								
								int nclrimp = (((int)bi[39]&0xff)<<24)
										| (((int)bi[38]&0xff)<<16)
										| (((int)bi[37]&0xff)<<8)
										| (int)bi[36]&0xff;
								//								System.out.println("BMP Colors important are :"+nclrimp);
								if (nbitcount==24)
										{
												if( nsizeimage == 0){
														nsizeimage = nheight * nwidth*3;
												}
												//												System.out.println("BMP nsizeimage:" + nsizeimage + " nheight:" + nheight +" nwidth:" + nwidth);
												// No Palatte data for 24-bit format but scan lines are
												// padded out to even 4-byte boundaries.
												int npad = (nsizeimage / nheight) - nwidth * 3;


												//												System.out.println("BMP	24 npad:"+ npad 
												//																					 + " nheight * nwidth=" +nheight * nwidth 
												//																					 + " ( nwidth + npad) * 3 * nheight=" + ( nwidth + npad) * 3 * nheight);

												int ndata[] = new int [nheight * nwidth];
												byte brgb[] = new byte [( nwidth + npad) * 3 * nheight];

												//												System.out.println("BMP read  " + (nwidth + npad) * 3 * nheight);

												fs.read (brgb, 0, (nwidth + npad) * 3 * nheight);
												//												System.out.println("BMP read  2 ");

												int nindex = 0;
												for (int j = 0; j < nheight; j++)
														{
																for (int i = 0; i < nwidth; i++)
																		{
																				ndata [nwidth * (nheight - j - 1) + i] =
																						(255&0xff)<<24
																						| (((int)brgb[nindex+2]&0xff)<<16)
																						| (((int)brgb[nindex+1]&0xff)<<8)
																						| (int)brgb[nindex]&0xff;

																				//																				System.out.println("BMP Encoded Color at ("
																				//								 +i+","+j+")is:"+brgb+" (R,G,B)= ("
																				//								 +((int)(brgb[2]) & 0xff)+","
																				//								 +((int)brgb[1]&0xff)+","
																				//								 +((int)brgb[0]&0xff)+")");
																				

																				nindex += 3;
																		}
																nindex += npad;
														}
												//												System.out.println("BMP Toolkit.getDefaultToolkit().createImage " );

												image =  Toolkit.getDefaultToolkit().createImage
														( new MemoryImageSource (nwidth, nheight,
																										 ndata, 0, nwidth));
												//												System.out.println("BMP Toolkit.getDefaultToolkit().createImage 2" );
										}
								else if (nbitcount == 8)
										{
												//												System.out.println("BMP	8");
												// Have to determine the number of colors, the clrsused
								// parameter is dominant if it is greater than zero.  If
								// zero, calculate colors based on bitsperpixel.
												int nNumColors = 0;
												if (nclrused > 0)
														{
																nNumColors = nclrused;
														}
												else
														{
																nNumColors = (1&0xff)<<nbitcount;
														}
												System.out.println("BMP The number of Colors is"+nNumColors);
												
												// Some bitmaps do not have the sizeimage field calculated
												// Ferret out these cases and fix 'em.
												if (nsizeimage == 0)
														{
																nsizeimage = ((((nwidth*nbitcount)+31) & ~31 ) >> 3);
																nsizeimage *= nheight;
																//																System.out.println("BMP nsizeimage (backup) is"+nsizeimage);
														}
												
												// Read the palatte colors.
												int  npalette[] = new int [nNumColors];
												byte bpalette[] = new byte [nNumColors*4];
												fs.read (bpalette, 0, nNumColors*4);
												int nindex8 = 0;
												for (int n = 0; n < nNumColors; n++)
														{
																npalette[n] = (255&0xff)<<24
																		| (((int)bpalette[nindex8+2]&0xff)<<16)
																		| (((int)bpalette[nindex8+1]&0xff)<<8)
																		| (int)bpalette[nindex8]&0xff;
																//						 System.out.println ("Palette Color "+n
																//									 +" is:"+npalette[n]+" (res,R,G,B)= ("
																//									 +((int)(bpalette[nindex8+3]) & 0xff)+","
																//									 +((int)(bpalette[nindex8+2]) & 0xff)+","
																//									 +((int)bpalette[nindex8+1]&0xff)+","
																//									 +((int)bpalette[nindex8]&0xff)+")");
																nindex8 += 4;
														}
												
												// Read the image data (actually indices into the palette)
				// Scan lines are still padded out to even 4-byte
				// boundaries.
												int npad8 = (nsizeimage / nheight) - nwidth;
												//												System.out.println("BMP nPad is:"+npad8);
												
												int  ndata8[] = new int [nwidth*nheight];
												byte bdata[] = new byte [(nwidth+npad8)*nheight];
												fs.read (bdata, 0, (nwidth+npad8)*nheight);
												nindex8 = 0;
												for (int j8 = 0; j8 < nheight; j8++)
														{
																for (int i8 = 0; i8 < nwidth; i8++)
																		{
																				ndata8 [nwidth*(nheight-j8-1)+i8] =
																						npalette [((int)bdata[nindex8]&0xff)];
																				nindex8++;
																		}
																nindex8 += npad8;
														}
												
												image =  Toolkit.getDefaultToolkit().createImage
														( new MemoryImageSource (nwidth, nheight,
																										 ndata8, 0, nwidth));
										}
								else
										{
												//												System.out.println ("BMP Not a 24-bit or 8-bit Windows Bitmap, aborting...");
												image = (Image)null;
										}
								
								fs.close();
								return image;
						}
				catch (Exception e)
						{
								System.out.println("BMP Caught exception in loadbitmap! " + e );								
						}
				return (Image) null;
		}
		//---------------------------------
		/*		static void Test(){
				BufferedImage lBufOut = new BufferedImage( 100, 100, BufferedImage.TYPE_INT_ARGB_PRE);
				Graphics2D g = lBufOut.createGraphics();
				//				g.setComposite( AlphaComposite.Src );
				g.setColor( Color.blue );
				g.fillRect( 0, 0, 100, 100 );
				g.dispose();
				
				
				if( CompressPixmap.WriteImage( lBufOut, "toto", "png" ) == false ){
						System.out.println( "Error write failed for toto" );	
				}
				
		}
		*/
		//---------------------------------
		//---------------------------------
		// ---   MAIN
		//---------------------------------
		//---------------------------------
		static String GetParamString( String[] args, String p_prefix, String pDefault ){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return arg.substring( l );
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean ExistParam( String[] args, String p_prefix){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return true;
								}
				}
				return false;
		}
		//---------------------------------
		
		static Integer GetParamInt( String[] args, String p_prefix, Integer pDefault){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										try{
												return new Integer( arg.substring(l));
										}catch(NumberFormatException ex){
												System.out.println( "Mauvais format pour commande "+p_prefix);
												return null;
					}					
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean GetParam( String[] args, String p_prefix ){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
										return true;
				}
				return false;
		}		
		//---------------------------------
		static void Help( int pExit ){
				System.out.println("ConvertPixmap v1.0 : Philippe Poupon : 2006 ");
				System.out.println("convert bmp file to jpeg");
				System.out.println("Usage:");
				System.out.println("\t-Isource path");
				System.out.println("\t[-Odestination path]");
				System.out.println("\t[-C]\tcompression");
				System.out.println("\t[-R]\ttraitement recursif");
				System.out.println("\t[-T]\tdetect and force transparencies");
				// INACTIF				System.out.println("\t[-R]\tresize with Transparencies, same size for all image");
				// INACTIF				System.out.println("\t[-r]\tresize with Transparencies");

				System.exit( pExit );
		}
		//-----------------------------------------------------
		//-----------------------------------------------------
		//-----------------------------------------------------
		//-----------------------------------------------------
				
		public static void main(String[] args) {
				
				String lPathIn  =  GetParamString( args, "-I", null );	
				String lPathOut =  GetParamString( args, "-O", null );	
				if( lPathOut == null )
						lPathOut = lPathIn;
				
				boolean lFlagRecursif = false;
				if( ExistParam( args, "-R" ) )
						lFlagRecursif = true;
				
				if( ExistParam( args, "-T" ) )
						Transparencie = true;
								
				
				if( ExistParam( args, "-C" ) )
						Compress = true;

				
				if( ExistParam( args, "-V" ) )
						Verbose = true;
				if( ExistParam( args, "-v" ) )
						Verbose = true;
				
				//	Test();
				 Process( new File(lPathIn), new File(lPathOut), null);
				
				//				lGenTabImgHtml.createMiniature( lSrc, 128, 128, 0, "test.jpg" );
		}		
}
