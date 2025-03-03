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

 
//***************************************************************
// Conversion de plusieurs BMP en JPG avec resize eventuelle
//***************************************************************

public class ConvertBmp{
		static boolean Verbose=false;

		static int sFlagResizeX = -1;
		static int sFlagResizeY = -1;

		//---------------------------------
		static boolean Process( String pPathIn, String pPathOut, boolean pFlagRecursif,
														int  pFlagTransparencie,  boolean pFlagResizeGlobal,  boolean pFlagResize, boolean pFlagClean ) {
				
				
				//				if( Verbose )System.out.println("process path:" +  pPathIn );
				
				File lFile = new File( pPathIn );
				
				if( lFile.isDirectory() == true && pFlagRecursif ){
						
						String lEntry[] = lFile.list( );
						
						for( int i=0; i< lEntry.length; i++) {								
								String lPathIn  = pPathIn  + "/" + lEntry[i];
								String lPathOut = pPathOut + "/" + lEntry[i];
								
								if( pFlagClean &&  lPathIn.equals( lPathOut ) == false ){
										StringBuilder lTmpStr = new StringBuilder( lPathOut.length()+1 );																													 
										for( int k=0; k<lPathOut.length(); k++ ){
												char c =  lPathOut.charAt(k);
												if( c == ' ' || c == '\t' || c == '\n' )
														lTmpStr.append( '_' );
												else
														lTmpStr.append( c );												
										}
										lPathOut = lTmpStr.toString();
								}
										
								Process( lPathIn , lPathOut, pFlagRecursif, pFlagTransparencie, pFlagResizeGlobal, pFlagResize, pFlagClean );
						}
						
				}
				else {
						DoFile( lFile, pPathIn, pPathOut, pFlagTransparencie );
						if( Verbose ) System.out.println();
						else System.out.print(".");
						return true;
				}				
				
				return true;
		}
		//---------------------------------
		static void DoFile( File pFileSrc, String pPathIn, String pPathOut, int  pFlagTransparencie ){
				
				String lStrFileName = pFileSrc.getName();			
				
				ImageIcon lIconImg = null;
				
				
				if( Verbose ) 						System.out.println( "Processing " + pFileSrc );
				


				// ======= LECTURE ==========
				if( lStrFileName.endsWith( ".jpg" ) 
						|| lStrFileName.endsWith( ".jpeg" )
						|| lStrFileName.endsWith( ".png" )
						|| lStrFileName.endsWith( ".gif" )){
						
						lIconImg = new 	ImageIcon(pFileSrc.getPath());						
				} else if( lStrFileName.endsWith( ".bmp" )  ){
						Image lImg = Loadbitmap( pFileSrc.getPath() );
						
						if( lImg == null ){
								System.err.println( " : bmp read failed");
								return ;
						}
						lIconImg = new ImageIcon( lImg );
				}
				else{
						return;
				}

		

				// ======= TRANSPARENCE =====
				if( pFlagTransparencie > 0 ){
						if( Verbose ) 	System.out.print( " transparencie");
						
						ImageIcon lTmpImageIcon = ForceTransparencie( lIconImg, pFlagTransparencie );
						if( lTmpImageIcon != null ){
								if( Verbose ) 	System.out.print( ":ok ");	
								lIconImg = lTmpImageIcon;
						}
						else
								if( Verbose ) 	System.out.print( ":failed " + pFileSrc );			
				}


				// ========= RESIZE ==========
				if( sFlagResizeX != -1 || sFlagResizeY != -1 ){

						int w = lIconImg.getIconWidth();
						int h = lIconImg.getIconHeight();

						if( sFlagResizeX != -1 )
								w = sFlagResizeX;

						if( sFlagResizeY != -1 )
								h = sFlagResizeY;
						lIconImg = new ImageIcon( lIconImg.getImage().getScaledInstance( (int)w, (int)h, Image.SCALE_SMOOTH ));						
				}



				
				// ========== ECRITURE =========
				BufferedImage lBuf = GetBufferedImage( lIconImg );
				if( lBuf != null ){
						
						File lFilePathOut= new File( pPathOut );
						//						System.out.println( lFilePathOut.getParent()  );
						//						System.out.println( lFilePathOut.getName()  );
						
						String lNewName =  lFilePathOut.getName().substring( 0, lStrFileName.length()-4);
						
						lNewName = lFilePathOut.getParent() + "/" + lNewName;

						lFilePathOut = new File( lFilePathOut.getParent() );
						lFilePathOut.mkdirs();						


						if( Verbose ) 	System.out.print( " -> " + lNewName );	
						
						
						if( WriteImage( lBuf, lNewName, "png" ) == false ){
								if( Verbose ) 	System.out.print( " : write failed");	
								else	System.out.println( "fail:"+ pFileSrc);	
						}						
				}
				else
						System.err.println( " : bmp convertion failed");
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
		static public boolean WriteImage( BufferedImage pImage, String pName, String pFormat ){
				
				Iterator lIterWriters = ImageIO.getImageWritersByFormatName(pFormat); // ou png
				ImageWriter lWriter = (ImageWriter)lIterWriters.next();				
				
				try {
						File lIConFile = new File( pName + "." + pFormat );
						
						ImageOutputStream llIOs = ImageIO.createImageOutputStream(lIConFile);
						lWriter.setOutput(llIOs);
						
						
						//    Finally, the image may be written to the output stream:
						
						lWriter.write(pImage);
				}
				catch(Exception e){
						System.err.println( e );
						return false;
				}
				return true;
		}
		//------------------------------------------------
		static public ImageIcon ForceTransparencie( ImageIcon pSrc, int pLevel ){

				
				int w = pSrc.getIconWidth();
				int h = pSrc.getIconHeight();
				
				int [] lPixels = new int [w*h];
				PixelGrabber pg = new PixelGrabber( pSrc.getImage(), 0, 0, w, h, lPixels, 0, w);
				try{
						pg.grabPixels();
				} catch( InterruptedException e){
						System.err.println( "ForceTransparencie exception: " + e );
						return null;
				}
				
				if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
						System.err.println("ForceTransparencie image fetch aborted or errored");
						return null;
				}
				
				// On par du principe qu'en general les coins du sprite sont vides,
				// on va donc essayer de determiner la valeur transparente en prenant comme
				// valeur l'un d'entre eux
				
				int []lTransparent = new int[pLevel*pLevel];

				int lNbTrans=0;
				if( pLevel == 1 ){
						lNbTrans = 1;
						if( lPixels[0] == lPixels[w*h-1] ){
								lTransparent[0] = lPixels[0];
						}
						else 	if( lPixels[0] == lPixels[w-1] ){
								lTransparent[0] = lPixels[0];
						}
						else	if( lPixels[w-1] == lPixels[w*h-1] ){
								lTransparent[0] = lPixels[w-1];
						} 	else return null;
				}
				else
						for( int y=0; y<pLevel; y++ )
								for( int x=0; x<pLevel; x++)
										lTransparent[lNbTrans++] = lPixels[x+y*w];
				
				//				System.out.printf( "lTransparent:%1$x", lTransparent );
				//				System.out.println();
				
				for( int y=0; y<h; y++ ){
						for( int x=0; x<w; x++){
								
								
								int lPixel = lPixels[x+y*w];
								//								System.out.printf( "%1$x ", lPixel );
								
								if( pLevel > 1 ) {
										for( int i=0 ; i< lNbTrans; i++ )
												if( lPixel == lTransparent[i] ){
														//										System.out.print( "-");
														lPixels[x+y*w] =0; // on met alpha a 0 !			
														break;
												}
								}
								else {
										if( lPixel == lTransparent[0] ) {
												//										System.out.print( "-");
												lPixels[x+y*w] =0; // on met alpha a 0 !			
										}
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
		
				static public Image Loadbitmap (String sfile){
				Image image;
				//System.out.println("loading:"+sfile);
				try
						{
								FileInputStream fs=new FileInputStream(sfile);
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
												System.err.println( "Mauvais format pour commande "+p_prefix);
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
				System.out.println("ConvertBmp v1.0 : Philippe Poupon : 2006 ");
				System.out.println("convert bmp file to jpeg");
				System.out.println("Usage:");
				System.out.println("\t-Isource path");
				System.out.println("\t[-Odestination path]");
				System.out.println("\t[-C]\tclean file an path names");
				System.out.println("\t[-T]\tdetect and force transparencies");
				// INACTIF				System.out.println("\t[-R]\tresize with Transparencies, same size for all image");
				// INACTIF				System.out.println("\t[-r]\tresize with Transparencies");
				System.out.println("\t[-X]\tresize en X");
				System.out.println("\t[-Y]\tresize en Y");

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
				
				int lFlagTransparencie = 0;
				if( ExistParam( args, "-T" ) ){
						lFlagTransparencie = GetParamInt( args, "-T", 1);
				}

				sFlagResizeX = GetParamInt( args, "-X", -1);				
				sFlagResizeY = GetParamInt( args, "-Y", -1);
				

				
				boolean lFlagResizeGlobal = false;
				if( ExistParam( args, "-S" ) )
							lFlagResizeGlobal = true;
				
				
				boolean lFlagResize = false;
				if( ExistParam( args, "-s" ) )
						lFlagResize = true;

				boolean lFlagClean = false;
				if( ExistParam( args, "-C" ) )
						lFlagClean = true;
				
				
				if( ExistParam( args, "-V" ) )
						Verbose = true;
				if( ExistParam( args, "-v" ) )
						Verbose = true;
				
				
				Process( lPathIn, lPathOut, lFlagRecursif, lFlagTransparencie, lFlagResizeGlobal, lFlagResize, lFlagClean);
				
				//				lGenTabImgHtml.createMiniature( lSrc, 128, 128, 0, "test.jpg" );
		}		
}
 
