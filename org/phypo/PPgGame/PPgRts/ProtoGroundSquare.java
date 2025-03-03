package org.phypo.PPgGame.PPgRts;


import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;



import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPgGame.PPgData.*;
import org.phypo.PPgGame.PPgGame.*;

//*************************************************
// Prototype pour un 'carre' de decor
//*************************************************

public class ProtoGroundSquare {

		public final static int MAX_CASE=32;
		public final static HashMap<String, ProtoGroundSquare> sHashProtoSquare = new HashMap<String, ProtoGroundSquare>();
		
		public static ProtoGroundSquare GetProtoSquare( String pName ){
				return sHashProtoSquare.get( pName );
		}
		
		char   cCode=0;
		String cName;
		String cText = "";

		public String getName() { return cName; }
		int    cId;                                     // utiliser pour les ProtoUnit pour les deplacements
		public int   getId() { return cId; }

		// Mettre quelque chode de plus generique ????
		double cVisionBonus =1;                        // a voir (remplacer par difference sur Z )
		double cFightBonus  =1;
		double cHideBonus   =1;
		double cRangeBonus  =1;                        // a voir (remplacer par difference sur Z )
		double cSpeedBonus  =1;                        // a voir
		double cMobilityBonus=1;                       // a voir

		Color cColor= Color.black;
		Color getColor() { return cColor; }
		
		//REMPLACER LES ImageIcon par des objets graphiques + generaux

		ImageIcon cOriginalImg=null;

		ImageIcon cImg=null;
		ImageIcon getImg() { return cImg; }

		ImageIcon cOriginalImgBack=null;
		ImageIcon cImgBack=null;
		ImageIcon getImgBack() { return cImgBack; }

		//------------------------------------------------
		ProtoGroundSquare( String pName, int pSquareId ){
				cName = pName;
				cId = pSquareId;
		}
		//------------------------------------------------
		// Le nom et l'ide ne sont pas copié

		public void copyPropriertyFrom( ProtoGroundSquare pModel) {

				cVisionBonus = pModel.cVisionBonus;
				cFightBonus  = pModel.cFightBonus;
				cHideBonus   = pModel.cHideBonus;
				cRangeBonus  = pModel.cRangeBonus;
				cSpeedBonus  = pModel.cSpeedBonus;
				cMobilityBonus = pModel.cMobilityBonus;

				cColor = pModel.cColor;
				cImg   = pModel.cImg;
				cImgBack = pModel.cImgBack;
		}
		//------------------------------------------------
		public String toString() {
				return cName + " code:"+ cCode 
						+ " vision:" + cVisionBonus
						+ " fight:"  + cFightBonus
						+ " hide:"   + cHideBonus
						+ " range:"  + cRangeBonus
						+ " color:"  + cColor
						+ " text:"   + cText;
		}
		//------------------------------------------------
		static public void PrintAll() {

				System.out.println( "--------------- ProtoGroundSquare ---------------" );

				Iterator<ProtoGroundSquare> lIter = sHashProtoSquare.values().iterator();
				while( lIter.hasNext() ){
						ProtoGroundSquare lProtoSquare = lIter.next();
						System.out.println( lProtoSquare.toString() );
				}
				System.out.println( "--------------- ----------------- ---------------" );
		}
		//------------------------------------------------
		static ProtoGroundSquare GetProto(char pChar){
				
				Iterator<ProtoGroundSquare> lIter = sHashProtoSquare.values().iterator();
				while( lIter.hasNext() ){
						ProtoGroundSquare lProtoSquare = lIter.next();
						if( lProtoSquare.cCode == pChar )
								return lProtoSquare;								
				}
				return null;
		}		
		//------------------------------------------------
		// METTRE UNE FONCTION QUI REMET TOUT A ZERO ( pour nouvelle carte!)

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		// Lecture depuis le .ini
		boolean  read( PPgIniFile pIni, String pSection) {
				
				System.out.println( "\treading : " +  pSection );
				String lModelName = pIni.get( pSection, DefIni.sModel );
				if( lModelName != null ){
						ProtoGroundSquare lModel = sHashProtoSquare.get( lModelName );	
						if( lModel == null ) {
								System.err.println( "*** Error : ProtoGroundSquare.Read Extend : model not found for " 
																		+ pSection + " " +  lModelName );
								return false;
						}
						copyPropriertyFrom( lModel );
				}


				cCode = pIni.get( pSection, DefIni.sCode ).charAt(0);
				cText = pIni.get( pSection, DefIni.sText, cText );

				cVisionBonus   =  pIni.getdouble( pSection, DefIni.sVision,  cVisionBonus );
				cFightBonus    =  pIni.getdouble( pSection, DefIni.sFight,   cFightBonus );
				cHideBonus     =  pIni.getdouble( pSection, DefIni.sHide,    cHideBonus );
				cRangeBonus    =  pIni.getdouble( pSection, DefIni.sRange,   cRangeBonus );
				cSpeedBonus    =  pIni.getdouble( pSection, DefIni.sSpeed,   cSpeedBonus );
				cMobilityBonus =  pIni.getdouble( pSection, DefIni.sMobility, cMobilityBonus );


					 

				cOriginalImg = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sImg, null );				
				cOriginalImgBack = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sBackground, null );

				cColor = pIni.getColor( pSection, DefIni.sColor, cColor );

				return true;
		}
		//------------------------------------------------
		static public void MakeScaledImg( GroundMap lMap ) {
				int lScale =  (int)(lMap.getSizeSquare()*World.Get().cGeneralScale);

				Iterator<ProtoGroundSquare> lIter = sHashProtoSquare.values().iterator();
				while( lIter.hasNext() ){
						ProtoGroundSquare lProto = lIter.next();
						if( lProto.cOriginalImg != null ) {
								lProto.cImg = new ImageIcon( lProto.cOriginalImg.getImage().getScaledInstance( lScale, lScale,	Image.SCALE_SMOOTH ));			
								lProto.cImg.getImage().setAccelerationPriority(1);
						}

						if( lProto. cOriginalImgBack != null ) {
								lProto.cImgBack = new ImageIcon( lProto.cOriginalImgBack.getImage().getScaledInstance( lScale, lScale, Image.SCALE_SMOOTH ));	
								lProto.cImgBack.getImage().setAccelerationPriority(1);
						}
				}
		}
		//------------------------------------------------

		static int sIdGen = 2;

		//------------------------------------------------
		static public boolean Read(  PPgIniFile pIni ) {

				ArrayList<String> lContents = new ArrayList<String>();

				if( pIni.getCollection( DefIni.sGroundPrototype, DefIni.sContents, lContents, " \t", ",:;" ) == null )
						return false;
				
				for( String lProtoName: lContents) {

						// On peut redefinir un objet
						ProtoGroundSquare lProto = sHashProtoSquare.get( lProtoName  );	
						if( lProto == null ) {
								lProto = new ProtoGroundSquare( lProtoName, sIdGen++ );
						}

						if( lProto.read( pIni, DefIni.sGroundPrototype+"."+lProtoName ) )
								sHashProtoSquare.put( lProto.cName, lProto );
						else {
								System.err.println( "*** Error : ProtoGroundSquare.Read failed for " + lProtoName );
								return false;
						}								
				}
				

				// DEFAULT
				ProtoGroundSquare lDefault = null;

        String lDefaultStr = pIni.get(  DefIni.sGroundPrototype, DefIni.sDefault );
				if( lDefaultStr != null ) {
						ProtoGroundSquare lDefaultVal = sHashProtoSquare.get( lDefaultStr  );	
						if( lDefaultVal != null ) {
								lDefault = new ProtoGroundSquare(  DefIni.sDefault, 1 );								
								lDefault.copyPropriertyFrom( lDefaultVal );
								sHashProtoSquare.put( lDefault.cName, lDefault );
						}
						else {
								System.err.println( "*** Error : ProtoGroundSquare.Read default value not found :" + DefIni.sGroundPrototype + " " +lDefaultStr );
								return false;								
						}
				}
				if( lDefault == null ) {
						lDefault = new ProtoGroundSquare(  DefIni.sDefault, 1 );
				}
				lDefault.cCode = ' ';
				
				return true;
		}

}

//*************************************************

