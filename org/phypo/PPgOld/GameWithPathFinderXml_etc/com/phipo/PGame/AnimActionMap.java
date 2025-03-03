package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.geom.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************
// Regroupe la meme sequence d'action, vue sous tout les angles disponibles

class AnimActionMap extends AnimAction{
		
		double cDuration=1;
		double cInvDuration=1;
		double cFactor =1;

		//--------
		int get( double pTime ){
				return (int)((pTime%cDuration)*cFactor);
		}
		//--------

		ImageIcon cImageIcon = null ;
		Image     cImage; // A CALCULER

		int cNbKeyFrame=0;
		int cWidthFrame=0; // A CALCULER !
		int cHeightFrame=0; // A CALCULER !
		int cHalfWidthFrame=0; // A CALCULER !
		int cHalfHeightFrame=0; // A CALCULER !

		//------------------------------------------------
		public AnimActionMap() {
		}
		//----------------
		public void draw( int pMX, int pMY, double pMagnify, Graphics pGraf, double pAngle, double pTime ){

				int lKey = (int)((pTime%cDuration)*cFactor);
				int lDir = getDir(pAngle);
				
				int lPosX = lKey*cWidthFrame;
				int lPosY = lDir*cHeightFrame;

				// Magnify ?????
				
				int lX = pMX-cHalfWidthFrame;
				int lY = pMY-cHalfHeightFrame;

				pGraf.drawImage( cImage, lX, lY,  lX + cWidthFrame, lY+cHeightFrame,
												 lPosX, lPosY, lPosX+cWidthFrame, lPosY+cHeightFrame,												 
												 null );
		}
		//-------
		void debug() {
				super.debug();
		}
		//------------------------------------------------

		// PROBLEME D INCOHERENCE AU NIVEAU DES TAILLES, IL VA FALLOIR TOUT REVOIR !!!!!
		// CHANGER LE CHARGEMENT DES PROTOTYPE UNITS ???????

		public boolean loadXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader, String pGenPath ){
				
				System.out.println( "*************** AnimActionMap loadXml" );
				//				String lName  = XmlLoader.GetAttributeVal( pNode, XmlLoader.TAG_XML_NAME );

				cNbOrientation  = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_ORIENTATION, cNbOrientation);
				super.setNbOrientation( cNbOrientation ); // fait des calcul
				

				int lHeight   = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_HEIGHT, 0 );
				int lWidth    = XmlLoader.GetAttributeInt( pMotherNode, pNode, XmlLoader.TAG_XML_WIDTH,  0 );
				

				// ----- Scale 
				double lScale = XmlLoader.GetAttributeDouble( pMotherNode,  pNode, XmlLoader.TAG_XML_SCALE, 1 );

				System.out.println( "ANIM  AnimActionMap.loadXml ok" );

				cNbKeyFrame = XmlLoader.GetAttributeInt( pMotherNode,pNode, XmlLoader.TAG_XML_KEYFRAME, 1 );
				String lFileStr = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_FILE );

				File lFile = null;
				if( pGenPath != null )
						lFile = new File( pGenPath, lFileStr.toString() );
				else
						lFile = new File( lFileStr.toString() );
				
				System.out.println( "++++++++++++++++++++ " + lScale + " "+ World.sGeneralScale );
				
				if( (cImageIcon = Util.LoadImageFromFile( lFile, lWidth, lHeight, false, lScale*World.sGeneralScale )) == null ){
						System.out.println( "AnimActionMap.loadXml fail to load " + lFile.getPath() );

						return false;
				}


				cImage = cImageIcon.getImage(); // gagne un appel de fonction lors de l'affichage !
				cImage.setAccelerationPriority((float)0.8);
				
				cWidthFrame  = cImageIcon.getIconWidth()  / cNbKeyFrame;     // doit etre un entier !
				cHeightFrame = cImageIcon.getIconHeight() / cNbOrientation;  // doit etre un entier !

				cHalfWidthFrame =  cWidthFrame/2;  // evite une division lors de l'affichage
				cHalfHeightFrame= cHeightFrame/2;

				cDuration = XmlLoader.GetAttributeDouble( pMotherNode, pNode, XmlLoader.TAG_XML_DURATION, 1 ); 

				// calcul du facteur multiplicatif donant la bonne image
				cFactor = (1/cDuration)*cNbKeyFrame;


				return true;
		}
}

//*************************************************
