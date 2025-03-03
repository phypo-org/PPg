package com.phipo.GLib;


import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;

import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;

import org.w3c.dom.*;

//*************************************************
public class PrototypeCase  extends PrototypeXmlLoader{

		public final static int MAX_CASE=32;

		public final static HashMap<String,PrototypeCase> sHashPrototypeCase = new HashMap<String,PrototypeCase>();
		
		public static PrototypeCase GetPrototypeCase( String pName ){
				return sHashPrototypeCase.get( pName );
		}
		
		public boolean isMobil() { return false; }

		char   cCode=0;
		String cName;
		public String getName() { return cName; }
		int    cId;     // utiliser pour les ProtoUnit pour les deplacements
		public int   getId() { return cId; }

		double cVisionBonus =1; // a voir (remplacer par difference sur Z )
		double cFightBonus  =1;
		double cHideBonus   =1;
		double cRangeBonus  =1; // a voir (remplacer par difference sur Z )
		double cSpeedBonus  =1; // a voir
		double cMobilityBonus=1;  // a voir

		Color cColor= Color.white;
		Color getColor() { return cColor; }
		
		ImageIcon cImg=null;
		ImageIcon getImg() { return cImg; }

		ImageIcon cImgBack=null;
		ImageIcon getImgBack() { return cImgBack; }

		//------------------------------------------------
		PrototypeCase( String pName, int pCaseId ){
				cName = pName;
				cId = pCaseId;
		}
		//------------------------------------------------
		public String toString() {
				return cName + " code:"+ cCode 
						+ " vision:" + cVisionBonus
						+ " fight:" + cFightBonus
						+ " hide:" + cHideBonus
						+ " range:" + cRangeBonus
						+ " color:" + cColor;
		}
		//------------------------------------------------
		static PrototypeCase GetPrototype(char pChar){
				
				Iterator<PrototypeCase> lIter = sHashPrototypeCase.values().iterator();
				while( lIter.hasNext() ){
						PrototypeCase lProtoCase = lIter.next();
						if( lProtoCase.cCode == pChar )
								return lProtoCase;								
				}
				return null;
		}		
		//------------------------------------------------
		//--------------------- XML ----------------------
		//------------------------------------------------

		static int sCaseId=1;

		public String getMyTag() { return XmlLoader.TAG_XML_CASE_PROTO; }
		public PrototypeXmlLoader findByName( String pName )    { return sHashPrototypeCase.get( pName );		}

		public PrototypeXmlLoader create( String pName)  {
				PrototypeCase lNew = new PrototypeCase( pName, sCaseId++ );
				
				if( sCaseId >= MAX_CASE ){
						System.err.println( "Error too many PrototypeCase define. Maximum is " + MAX_CASE );
						return null;
				}

				sHashPrototypeCase.put( pName, lNew );
				return lNew;
		}
				
		public void doInherit( PrototypeXmlLoader pMother ){
				PrototypeCase lMother = (PrototypeCase)pMother;
				// Tout n'est pas herite !
				cVisionBonus   = lMother.cVisionBonus ;
				cFightBonus    = lMother.cFightBonus ;
				cHideBonus     = lMother.cHideBonus ;
				cRangeBonus    = lMother.cRangeBonus ;
				cSpeedBonus    = lMother.cSpeedBonus ;
				cMobilityBonus = lMother.cMobilityBonus ;
				
				cColor = lMother.cColor;
		}
		
		//------------------------------------------------
		public  boolean loadXmlNode( Node pMotherNode, Node pNode, XmlLoader pXmlLoader ){
				
				System.out.println( "\t\tloadXmlNode for PrototypeCase " + cName);

				//CREER DES COULEURS SUPLEMENTAIRE
				// <COLOR Name="" RGB="1,2,3"\>
				
				//------------------
				cColor = XmlLoader.GetAttributeColor( null, pNode, XmlLoader.TAG_XML_COLOR, cColor );
				//------------
				String lFile = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_FILE );
				if( lFile != null){
						ImageIcon lTmpImage = new ImageIcon( lFile );
						//						double lScale = lTmpImage.getIconWidth()/(World.GetCaseSize()*World.sGeneralScale);
						//						
						//						cImg   = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(lTmpImage.getIconWidth()/lScale), 
						//																																						(int)(lTmpImage.getIconHeight()/lScale), 
						//																																						Image.SCALE_SMOOTH ));

						System.out.println( "DecorCarte.sSizeCase = " + DecorCarte.sSizeCase + " World.sGeneralScale =" + World.sGeneralScale );
						cImg   = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(DecorCarte.sSizeCase*World.sGeneralScale), 
																																						(int)(DecorCarte.sSizeCase*World.sGeneralScale), 
																																						Image.SCALE_SMOOTH ));						
						cImg.getImage().setAccelerationPriority(1);
				}
				//------------
				String lFileBack = XmlLoader.GetAttributeVal( null,  pNode, XmlLoader.TAG_XML_BACKGROUND );
				if( lFileBack != null){
						ImageIcon lTmpImage = new ImageIcon( lFileBack );
						//						double lScale = lTmpImage.getIconWidth()/(World.GetCaseSize()*World.sGeneralScale);
						//						
						//						cImg   = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(lTmpImage.getIconWidth()/lScale), 
						//																																						(int)(lTmpImage.getIconHeight()/lScale), 
						//																																						Image.SCALE_SMOOTH ));

						cImgBack   = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(DecorCarte.sSizeCase*World.sGeneralScale), 
																																						(int)(DecorCarte.sSizeCase*World.sGeneralScale), 
																																						Image.SCALE_SMOOTH ));						
						cImgBack.getImage().setAccelerationPriority(1);
				}
				//------------
				String lSymbol = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_SYMBOL );
				if( lSymbol != null){
						cCode = lSymbol.charAt(0);
				}
				//------------
				cFightBonus  = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_FIGHT_BONUS,  cFightBonus );
				cVisionBonus = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_VISION_BONUS, cVisionBonus );
				cRangeBonus  = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_RANGE_BONUS,  cRangeBonus );
				cHideBonus   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_HIDE_BONUS,   cHideBonus );
				//------------
				
				return true;
		}
		//------------------
		public boolean loadXmlSubNode(Node  lNode ) {
				System.out.println( "\t\tloadXmlSubNode for PrototypeCase " + cName);
				return true;
		}

}
