package com.phipo.GLib;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.FactoryConfigurationError;  
import javax.xml.parsers.ParserConfigurationException;
 
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  

import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import java.awt.geom.*;
import java.awt.*;


import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;

//*************************************************
public class Ressource {

		String cName = null;
		double cVolatility=1.0;
		double getVolatility() { return cVolatility; }

		ImageIcon cImg= null; // Pour le decor
		ImageIcon getImg() { return cImg; }
		ImageIcon cIcon= null;
		ImageIcon getIcon() { return cIcon; }
		ImageIcon cSmallIcon= null;
		ImageIcon getSmallIcon() { return cSmallIcon; }
		ImageIcon cTinyIcon= null;
		ImageIcon getTinyIcon() { return cTinyIcon; }

		
		// ************** STATIC **************

		public static boolean LoadXml( Node pNode, XmlLoader pXmlLoader ){

				String lName = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_NAME );
				if( lName == null )								
						return false;
				
				Ressource lRessource = null;
				if( (lRessource = FindRessource( lName )) == null ){
						lRessource = new Ressource();
						lRessource.cName = lName;
						
						RessourceTab[sCurrentRessourceId++] = lRessource;
				}
				
				String lStrImg = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_ICON );
				
				ImageIcon lTmpImage = new ImageIcon( lStrImg );
				lRessource.cImg = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(DecorCarte.sSizeCase*World.sGeneralScale), 
																																								 (int)(DecorCarte.sSizeCase*World.sGeneralScale), 		
																																								 Image.SCALE_SMOOTH ));
				
				lRessource.cIcon  = new ImageIcon( lTmpImage.getImage().getScaledInstance( World.sSizeIcon, 
																																									 World.sSizeIcon, Image.SCALE_SMOOTH ));
				lRessource.cSmallIcon  = new ImageIcon( lTmpImage.getImage().getScaledInstance( World.sSizeSmallIcon,
																																												World.sSizeSmallIcon, Image.SCALE_SMOOTH ));				
				lRessource.cTinyIcon  = new ImageIcon( lTmpImage.getImage().getScaledInstance( World.sSizeTinyIcon,
																																												World.sSizeTinyIcon, Image.SCALE_SMOOTH ));				

				lRessource.cVolatility = XmlLoader.GetAttributeDouble( null, pNode , XmlLoader.TAG_XML_VOLATILITY, lRessource.cVolatility );

				return true;
		}

		//------------------------------------------------

		final public static int MAX_RESSOURCE = 64;

		public static int Length() { return sCurrentRessourceId; }
		public static int sCurrentRessourceId=0;
		public static Ressource [] RessourceTab = new Ressource[MAX_RESSOURCE];

		//---------------------------
		final public static Ressource GetRessource( int pInd ){
				return RessourceTab[pInd];
		}
		//---------------------------
		final public static Ressource FindRessource( String pStr){
				int lIndex = FindRessourceIndex( pStr );
				if( lIndex != -1 )
						return RessourceTab[lIndex];
				return null;
		}	
		//---------------------------
		final public static int FindRessourceIndex( String pStr){
				for( int i=0; i< sCurrentRessourceId; i++)
						if( RessourceTab[i].cName.equals( pStr ))
								return i;
				return -1;
		}	
};

//*************************************************
