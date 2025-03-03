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

abstract public class AnimAction {
		
		int cNbOrientation=1;     
		//		double  cSector;
		// pour accelerer les calculs
		double  cInvSector;    
		double  cHalfSector;

		//------------------------------------------------
		public AnimAction() {
		}

		//--------
		void setNbOrientation( int pNb ){	
				cNbOrientation = pNb;
				//				cSector = 360/cNbOrientation;
				cInvSector = cNbOrientation/360.0;
				cHalfSector = 360/(cNbOrientation*2.0);
		}
		//--------
		// Transpose l'angle en un index dans le tableau des sequences

		int getDir(double pAngle) { return (int)(((pAngle-cHalfSector)*cInvSector)+0.50001);}
		//--------
		public abstract void draw( int pMX, int pMY, double pMagnify, Graphics pGraf, double pAngle, double pTime );
		//-------
		void debug() {	
				System.out.println( "ANIM Act orientation:" +cNbOrientation + " invSector:" + cInvSector + " halfSector:" + cHalfSector );				
		}
		//------------------------------------------------
		public abstract boolean loadXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader, String pGenPath);
}

//*************************************************
