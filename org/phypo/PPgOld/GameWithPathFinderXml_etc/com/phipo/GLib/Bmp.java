package com.phipo.GLib;


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

class Bmp{
		
		static public Image Loadbitmap (File pFile)
		{	
				Image image;
				System.out.println("loading:"+pFile.getName());
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
								System.out.println("BMP File type is :"+(char)bf[0]+(char)bf[1]);
								System.out.println("BMP Size of file is :"+nsize);
								
								int nbisize = (((int)bi[3]&0xff)<<24)
										| (((int)bi[2]&0xff)<<16)
										| (((int)bi[1]&0xff)<<8)
										| (int)bi[0]&0xff;
								System.out.println("BMP Size of bitmapinfoheader is :"+nbisize);
								
								int nwidth = (((int)bi[7]&0xff)<<24)
										| (((int)bi[6]&0xff)<<16)
										| (((int)bi[5]&0xff)<<8)
										| (int)bi[4]&0xff;
								System.out.println("BMP Width is :"+nwidth);
								
								int nheight = (((int)bi[11]&0xff)<<24)
										| (((int)bi[10]&0xff)<<16)
										| (((int)bi[9]&0xff)<<8)
										| (int)bi[8]&0xff;
								System.out.println("BMP Height is :"+nheight);
								
								int nplanes = (((int)bi[13]&0xff)<<8) | (int)bi[12]&0xff;
								System.out.println("BMP Planes is :"+nplanes);
								
								int nbitcount = (((int)bi[15]&0xff)<<8) | (int)bi[14]&0xff;
								System.out.println("BMP BitCount is :"+nbitcount);
								
								// Look for non-zero values to indicate compression
								int ncompression = (((int)bi[19])<<24)
										| (((int)bi[18])<<16)
										| (((int)bi[17])<<8)
										| (int)bi[16];
								System.out.println("BMP Compression is :"+ncompression);
								
								int nsizeimage = (((int)bi[23]&0xff)<<24)
										| (((int)bi[22]&0xff)<<16)
										| (((int)bi[21]&0xff)<<8)
										| (int)bi[20]&0xff;
								System.out.println("BMP SizeImage is :"+nsizeimage);
								
								int nxpm = (((int)bi[27]&0xff)<<24)
										| (((int)bi[26]&0xff)<<16)
										| (((int)bi[25]&0xff)<<8)
										| (int)bi[24]&0xff;
								System.out.println("BMP X-Pixels per meter is :"+nxpm);
								
								int nypm = (((int)bi[31]&0xff)<<24)
										| (((int)bi[30]&0xff)<<16)
										| (((int)bi[29]&0xff)<<8)
										| (int)bi[28]&0xff;
								System.out.println("BMP Y-Pixels per meter is :"+nypm);
								
								int nclrused = (((int)bi[35]&0xff)<<24)
										| (((int)bi[34]&0xff)<<16)
										| (((int)bi[33]&0xff)<<8)
										| (int)bi[32]&0xff;
								System.out.println("BMP Colors used are :"+nclrused);
								
								int nclrimp = (((int)bi[39]&0xff)<<24)
										| (((int)bi[38]&0xff)<<16)
										| (((int)bi[37]&0xff)<<8)
										| (int)bi[36]&0xff;
								System.out.println("BMP Colors important are :"+nclrimp);
								if (nbitcount==24)
										{
												if( nsizeimage == 0){
														nsizeimage = nheight * nwidth*3;
												}
												System.out.println("BMP nsizeimage:" + nsizeimage + " nheight:" + nheight +" nwidth:" + nwidth);
												// No Palatte data for 24-bit format but scan lines are
												// padded out to even 4-byte boundaries.
												int npad = (nsizeimage / nheight) - nwidth * 3;


												System.out.println("BMP	24 npad:"+ npad 
																					 + " nheight * nwidth=" +nheight * nwidth 
																					 + " ( nwidth + npad) * 3 * nheight=" + ( nwidth + npad) * 3 * nheight);

												int ndata[] = new int [nheight * nwidth];
												byte brgb[] = new byte [( nwidth + npad) * 3 * nheight];

												System.out.println("BMP read  " + (nwidth + npad) * 3 * nheight);

												fs.read (brgb, 0, (nwidth + npad) * 3 * nheight);
												System.out.println("BMP read  2 ");

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
												System.out.println("BMP Toolkit.getDefaultToolkit().createImage " );

												image =  Toolkit.getDefaultToolkit().createImage
														( new MemoryImageSource (nwidth, nheight,
																										 ndata, 0, nwidth));
												System.out.println("BMP Toolkit.getDefaultToolkit().createImage 2" );
										}
								else if (nbitcount == 8)
										{
												System.out.println("BMP	8");
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
																System.out.println("BMP nsizeimage (backup) is"+nsizeimage);
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
												System.out.println("BMP nPad is:"+npad8);
												
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
												System.out.println ("BMP Not a 24-bit or 8-bit Windows Bitmap, aborting...");
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
		
}
