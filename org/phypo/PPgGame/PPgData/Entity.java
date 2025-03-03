package org.phypo.PPgGame.PPgData;


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



//*************************************************

public class Entity {

		EntityProto cEntityProto;

		
		String       cName = null;
		int          cCode=-1;
		String       cText=null;

		public AttributPool cAttributPool = null;

		//------------------------------------------------

		Entity( EntityProto pEntityProto ){
				cEntityProto = pEntityProto;

				cName = cEntityProto.cName;

				cAttributPool = new AttributPool( pEntityProto.cAttributProto, pEntityProto.cAttribut );
			}
		//------------------------------------------------

		public String toString() {

				String lTmp = cName ;
				
				return lTmp;
		}

		//------------------------------------------------
		// Lecture depuis le .ini
		boolean  read( PPgIniFile pIni, String pSection) {
				
				System.out.println( "\treading : " +  pSection );
				String lModelName = pIni.get( pSection, DefIni.sModel );
				if( lModelName != null ){
						EntityProto lModel = EntityProto.sHashEntityProto.get( lModelName );	
						if( lModel == null ) {
								System.err.println( "*** Error : ProtoGroundSquare.Read Extend : model not found for " 
																		+ pSection + " " +  lModelName );
								return false;
						}
						//// A FAIRE !!!!!!!		
						//// A FAIRE !!!!!!!					copyPropriertyFrom( lModel );
						//// A FAIRE !!!!!!!		
				}


				cCode = pIni.get( pSection, DefIni.sCode ).charAt(0);
				cText = pIni.get( pSection, DefIni.sText, cText );


				//				cOriginalImg = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sImg, null );				
				//				cOriginalImgBack = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sBackground, null );

				//				cColor = pIni.getColor( pSection, DefIni.sColor, cColor );

				return true;
		}
		//------------------------------------------------

		static int sIdGen = 1;

		//------------------------------------------------
		static public boolean Read(  PPgIniFile pIni ) {

				return true;
		}
		// Comportement 
		// Action a faire, en cours ...

		
		// Ils faut qu'il existe des proprietes pouvant etre associees a tout les types d'objets chaque a chaque proprietes on peut associer un mode d'action
		// un temps d'actions un sns +/- enemi/amis portées, rayon d'action

		// c'est proprietes peuvent etre associe  a des unites des lieux des items ...
		

		// Cout en ressource a la creation
		// Cout en ressource en existence

		// Capactite de production de ressource
		// Capacite de Construction d'autre entity

		// Les ressources propres a chaque unite Vie, Mana, Endurence, Experience ...  + capacite de recuperation pour chacune

		// Deplacement : Vitesse acceleration, agilté, Capacite de deplacement en fonction du terrain
		// Detection   : vision, vison en fonction du terrain, vision des unitées furtives, sous marine...

		// Les differentes capacites peuvent etre ajoute au unites sous formes de listes d'effets  ( armes,sort, boucliers pouvant agir sur tout )
		// Capacite d'attaque  : -> listes des armes et des sorts  (chaque arme avec les effets les ressources des unités)
		// Capacite de defense : -> listes des defense et des sorts
};
