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
// Regroupe la meme sequence d'action, vue sous tout les angles disponibles

class AnimActionSeq extends AnimAction{
		
		
		AnimSeq [] cVectAnimSeq = new AnimSeq[32]; // 32 angles au maximum
		
		//------------------------------------------------
		public AnimActionSeq() {
				for( int i=0; i<32; i++){
						cVectAnimSeq[i] = null;
				}
		}

		//--------
		void setSequence( double pAngle, AnimSeq pSeq ){
				
				System.out.println( "ANIM setSequence " + pAngle +" -> "+ getDir( pAngle));
				
				cVectAnimSeq[getDir( pAngle)] = pSeq;
		}
		//----------------
		public void draw( int pMX, int pMY, double pMagnify, Graphics pGraf, double pAngle, double pTime ){
				ImageIcon lImg = get(  pAngle,  pTime );
				if( lImg != null )
						//		lImg.paintIcon( PPAppli.GetAppli(), pGraf, pMX-lImg.getIconeWidth()*0.5, pMY-lImg.getIconeHeight()*0.5 );							
						pGraf.drawImage( lImg.getImage(), (int)(pMX-lImg.getIconWidth()), (int)(pMY-lImg.getIconHeight()),
														 lImg.getIconWidth(), pMY-lImg.getIconHeight(), null );
				
		}
		//--------
		// Renvoit l'image correpondant a un angle et un temps donne
		
		public  ImageIcon get( double pAngle, double pTime ){
				
				int lDir = getDir(pAngle);
				
				System.out.println( "AnimActionSeq.get " + pAngle + " -> " +  lDir);

				AnimSeq lSeq = cVectAnimSeq[lDir];
				if( lSeq == null )
						return null;
				return  lSeq.get( pTime );
		}
		//-------
		void debug() {
				System.out.println( "ANIM Act orientation:" +cNbOrientation + " invSector:" + cInvSector + " halfSector:" + cHalfSector );
				for( int i=0; i< 32 ;i++){
						if( cVectAnimSeq[i] != null ){
								System.out.print( " " + i + ":" + (i/cInvSector)+cHalfSector+ " > " );
								cVectAnimSeq[i].debug();
						}
				}												
		}
		//------------------------------------------------

		public boolean loadXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader, String pGenPath ){
				
				double  lAngle  = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_ORIENTATION, 0);
				
				System.out.println( "ANIM  AnimActionSeq.loadXml angle:" + lAngle + "->" + getDir(lAngle)  
														+ " val:" +  XmlLoader.GetAttributeVal( null, pNode,  XmlLoader.TAG_XML_ORIENTATION ));

				AnimSeq lSeq = AnimSeq.LoadXml( pMotherNode, pNode, pXmlLoader, pGenPath );

				if( lSeq == null )
						return false;

				setSequence( lAngle, lSeq );

				System.out.println( "ANIM  AnimActionSeq.loadXml ok" );
				
				return true;
		}
}

//*************************************************
