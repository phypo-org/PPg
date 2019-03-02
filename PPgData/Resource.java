package org.phypo.PPg.PPgData;


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



import org.phypo.PPg.PPgUtils.*;


//*************************************************
// Une ressource presente dans le jeu et qui evolue 
// (bois,or, energie, mana...)
//*************************************************

public class Resource {

		String cName = null;
		double cVolatility=1.0;

			public double getVolatility() { return cVolatility; }

		ImageIcon cOriginalImg= null; // Pour le decor
		ImageIcon cImg= null; // Pour le decor
			public ImageIcon getImg() { return cImg; }
		ImageIcon cIcon= null;
			public ImageIcon getIcon() { return cIcon; }
		ImageIcon cSmallIcon= null;
			public ImageIcon getSmallIcon() { return cSmallIcon; }
		ImageIcon cTinyIcon= null;
			public ImageIcon getTinyIcon() { return cTinyIcon; }

		//------------------------------------------------
		public Resource( String pName ){
				cName = pName ;
				
		}
		//------------------------------------------------

		// Lecture depuis le .ini
		boolean  read( PPgIniFile pIni, String pSection) {

				System.out.println( "\treading : " +  pSection );

				cImg = cOriginalImg = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sImg, null );
				if( cOriginalImg == null)
						return false;

				ImageIcon lTmpIcon = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sIcon, null );
				if( lTmpIcon == null )
						lTmpIcon = cOriginalImg;

				cIcon        = new ImageIcon(lTmpIcon.getImage().getScaledInstance( DefIni.sSizeIcon,       DefIni.sSizeIcon,       Image.SCALE_SMOOTH ));	
				cIcon.getImage().setAccelerationPriority(1);
				cSmallIcon   = new ImageIcon(lTmpIcon.getImage().getScaledInstance( DefIni.sSizeSmallIcon,  DefIni.sSizeSmallIcon,  Image.SCALE_SMOOTH ));	
				cSmallIcon.getImage().setAccelerationPriority(1);
				cTinyIcon    = new ImageIcon(lTmpIcon.getImage().getScaledInstance( DefIni.sSizeTinyIcon,   DefIni.sSizeTinyIcon,   Image.SCALE_SMOOTH ));	
				cTinyIcon.getImage().setAccelerationPriority(1);
				
				cVolatility = pIni.getdouble( pSection, DefIni.sVolatility, cVolatility  );

				return true;
		}
		//------------------------------------------------
		static public void MakeScaledImg( int pScale ) {

				for( int i=0; i<Length(); i++) {
						
						Resource lRes = ResourceTab[i];

						lRes.cImg = new ImageIcon( lRes.cOriginalImg.getImage().getScaledInstance( pScale, pScale,	Image.SCALE_SMOOTH ));			
						lRes.cImg.getImage().setAccelerationPriority(1);
				}
		}
		//------------------------------------------------
		static public boolean Read(  PPgIniFile pIni ) {

				ArrayList<String> lContents = new ArrayList<String>();

				if( pIni.getCollection( DefIni.sResource, DefIni.sContents, lContents, " \t", ",:;" ) == null )
						return false;

				for( String lName: lContents) {
						
						// On peut redefinir un objet
						Resource lRes = Find( lName  );	
						if( lRes== null ) {
								lRes = new Resource( lName );
						}

						if( lRes.read( pIni, DefIni.sResource+"."+lName ) )
								ResourceTab[sCurrentResourceId++] = lRes;
						else {
								System.err.println( "*** Error : Resource.Read failed for " +  lName);
								return false;
						}								
				}				
				return true;
		}
		//------------------------------------------------

		final public static int MAX_RESOURCE = 64;

		public static int Length() { return sCurrentResourceId; }
		public static int sCurrentResourceId=0;
		public static Resource [] ResourceTab = new Resource[MAX_RESOURCE];

		//---------------------------
		final public static Resource Get( int pInd ){
				return ResourceTab[pInd];
		}
		//---------------------------
		final public static Resource Find( String pStr){
				int lIndex = FindIndex( pStr );
				if( lIndex != -1 )
						return ResourceTab[lIndex];
				return null;
		}	
		//---------------------------
		final public static int FindIndex( String pStr){
				for( int i=0; i< sCurrentResourceId; i++)
						if( ResourceTab[i].cName.equals( pStr ))
								return i;
				return -1;
		}	
};

//*************************************************
