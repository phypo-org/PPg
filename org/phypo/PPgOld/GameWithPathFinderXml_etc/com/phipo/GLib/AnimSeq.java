package com.phipo.GLib;


import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************

// Une animation toute simple

class AnimSeq{
		double cDuration=1;
		double cInvDuration=1;
		double cFactor =1;
		String cName = null;
		
		ImageIcon [] cVectImg = null;

		static HashMap<String,AnimSeq> sVectAnimSeq = new HashMap<String,AnimSeq>();
		static AnimSeq FindByName( String pStr ){ return sVectAnimSeq.get( pStr ); }

		//--------
		AnimSeq( String pName, double pDuration ){
				cName = pName;
				cDuration = pDuration;
		}
		//--------
		void setDuration( double pDuration){
				cDuration = pDuration;
				// calcul du facteur multiplicatif donant la bonne image
				cFactor = (1/cDuration)*cVectImg.length;
		}
		//--------
		ImageIcon get( double pTime ){						

				System.out.println( "AnimSeq.get " + pTime + " -> " + (pTime%cDuration)*cFactor );

				return cVectImg[(int)((pTime%cDuration)*cFactor)];
		}
		//-------
		void addImage( ImageIcon pImage, double pDuration ){
				
				int lNb = 0;
				if( cVectImg != null )
						lNb = cVectImg.length;
				System.out.println( "ANIM AnimSeq.addImage 1 " + lNb );
				
				ImageIcon [] lTmpImg = new ImageIcon[lNb+1];
				
				for( int i=0; i< lNb; i++ )
						lTmpImg[i] = cVectImg[i];
				
				lTmpImg[lNb] = pImage;
				cVectImg = lTmpImg;
				
				if( pDuration != 0 )
						setDuration( pDuration );
				else
						setDuration( cDuration );

				System.out.println( "ANIM AnimSeq.addImage 2 --  " + lNb  );
		}
		//-------
		void debug() {
				int lNb = 0;
				if( cVectImg != null )
						lNb = cVectImg.length;


				System.out.println( "ANIM Seq " + cName + " duration:" + cDuration + " invDuration:" +  cInvDuration + " factor:" + cFactor + " nb:" + lNb);				
				for( int i=0; i< lNb; i++ )
						System.out.println( "ANIM " + i + " " + this );
		}
		//------------------------------------------------
		//-----------------  STATIC  ---------------------
		//------------------------------------------------

		static public AnimSeq LoadXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader, String pGenPath  ){
				
				String lSeqName  = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_NAME );
				double lDuration = XmlLoader.GetAttributeDouble( pMotherNode, pNode, XmlLoader.TAG_XML_DURATION, 1 ); 
				double lAngle    = XmlLoader.GetAttributeDouble(  pMotherNode, pNode, XmlLoader.TAG_XML_ORIENTATION, 0);
			
				String lGenBeginFile = XmlLoader.GetAttributeVal( pMotherNode, pNode, XmlLoader.TAG_XML_GENBEGINFILE );
				String lGenEndFile   = XmlLoader.GetAttributeVal( pMotherNode, pNode, XmlLoader.TAG_XML_GENENDFILE);
				int lGenVariableSize  = (int)XmlLoader.GetAttributeDouble( pMotherNode, pNode, XmlLoader.TAG_XML_GENVARIABLESIZE,  1 );										
				int lHeight   = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_HEIGHT, AnimAll.cHeight );
				int lWidth    = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_WIDTH,  AnimAll.cWidth  );
				
				ImageIcon lImageIcon = null;
				AnimSeq lSequence = new AnimSeq( lSeqName, lDuration );

				boolean lForceTransparencie= XmlLoader.GetAttributeBoolean( pMotherNode, pNode, XmlLoader.TAG_XML_FORCE_TRANSPARENCIE, false );
				
				System.out.println( "ANIM AnimSeq.LoadXml "+ lSeqName +" "  + lWidth +","+lHeight + " " + lGenBeginFile + "," + lGenEndFile + " variable:" + lGenVariableSize + " PAth:" + pGenPath );


				String lLoadFile   = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_FILE );
				String lNameFlip   = XmlLoader.GetAttributeVal( pMotherNode, pNode, XmlLoader.TAG_XML_CREATE_FLIP_HOR );
				String lNameRotate = XmlLoader.GetAttributeVal( pMotherNode, pNode, XmlLoader.TAG_XML_CREATE_ROTATE );

				System.out.println( "ANIM AnimSeq.LoadXml File: " 
														+ lLoadFile + " Flip:"+  lNameFlip + " Rotate:" + lNameRotate);

				//-------------------------
				if( lNameFlip != null ){
						AnimSeq lSeqFlip = FindByName(lNameFlip );
						if( lSeqFlip != null ){
								System.out.println( "ANIM >>>>  AnimSeq.LoadXml Flip OK +"  +lSeqFlip.cVectImg.length);

								for( int i=0; i < lSeqFlip.cVectImg.length; i++){
										System.out.print( "ANIM AnimSeq.LoadXml Flip i:"+i );

										lImageIcon = Util.CreateFlipHorImage( lSeqFlip.get(i) );
										if( lImageIcon == null ){
												System.out.println( " Failed" );
												return null;
										}
										System.out.println( " OK" );

										lSequence.addImage( lImageIcon, 0 );
								}
						}
				}			
				else
				//-------------------------
				if( lNameRotate != null ){
						AnimSeq lSeqRot = FindByName( lNameRotate );
						if( lSeqRot != null ){
								System.out.println( "ANIM >>>>  AnimSeq.LoadXml Rot OK +"  +lSeqRot.cVectImg.length);

								for( int i=0; i < lSeqRot.cVectImg.length; i++){
										System.out.print( "ANIM AnimSeq.LoadXml Rot i:"+i );

										lImageIcon = Util.CreateRotateImage( lSeqRot.get(i), lAngle );
										if( lImageIcon == null ){
												System.out.println( " Failed" );
												return null;
										}
										System.out.println( " OK" );

										lSequence.addImage( lImageIcon, 0 );
								}
						}
				}				
				else
				//----------------------------------
				if( lLoadFile != null ) {

						System.out.println( " lLoadFile:" + lLoadFile  );

						File lFile = null;
						if( pGenPath != null )
								lFile = new File( pGenPath, lLoadFile );
						else
								lFile = new File( lLoadFile  );
																		
						if( (lImageIcon = Util.LoadImageFromFile( lFile, lWidth, lHeight, lForceTransparencie, World.sGeneralScale )) == null )
								return null;
						System.out.println( " OK" );
						
						lSequence.addImage( lImageIcon, 0 );
				}				
				else
						{
								//----------------------------------
								int lFileIndex=0;
								if( lGenBeginFile != null || lGenEndFile != null )
										do {
												StringBuffer lFileStr = new StringBuffer();

												if( lGenBeginFile != null )
														lFileStr.append( lGenBeginFile );
												String lNumber = ""+ lFileIndex++;
												for( int j=lNumber.length(); j< lGenVariableSize; j++)
														lFileStr.append('0');
												
												lFileStr.append(lNumber);
												if( lGenEndFile  != null)
														lFileStr.append(lGenEndFile);

												File lFile = null;
												if( pGenPath != null )
														lFile = new File( pGenPath, lFileStr.toString() );
												else
														lFile = new File( lFileStr.toString() );
												
												System.out.println( "ANIM AnimSeq.LoadXml file:" + lFile.getPath());
												
												if( (lImageIcon = Util.LoadImageFromFile( lFile, lWidth, lHeight, lForceTransparencie, World.sGeneralScale )) == null )
														break;
														
												System.out.println( "ANIM AnimSeq.LoadXml image:"+ lImageIcon );
																								
												lSequence.addImage( lImageIcon, 0 );
										} while(true );
						}
		
				sVectAnimSeq.put(lSequence.cName, lSequence );
				return lSequence;			 
		}
}
//*************************************************
