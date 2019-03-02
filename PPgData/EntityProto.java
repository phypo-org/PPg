package org.phypo.PPg.PPgData;


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
import org.phypo.PPg.PPgData.*;



//*************************************************
public class EntityProto{

		public final static HashMap<String, EntityProto> sHashEntityProto = new HashMap<String, EntityProto>();
		

		public static EntityProto  GetProto( String pTypeName){
				return sHashEntityProto.get( pTypeName );
		}
		public static EntityProto  GetProto( String pName, String pType ){
				return sHashEntityProto.get( pType + '.' + pName   );
		}
		// Le nom et le text  ne sont pas copié
		//------------------------------------------------

		//	ItemPool     cItem;

		String       cName = null;
		String       cType = "";
		String       cText = "";
		
		ImageIcon cOriginalImg=null;


		public AttributProto [] cAttributProto = new AttributProto [AttributProto.MAX_ATTRIBUT];
		public Attribut []      cAttribut      = new Attribut [AttributProto.MAX_ATTRIBUT];

		//------------------------------------------------

		public EntityProto( String pName, String pType ) {

				cName = pName;
				cType = pType;

			for( int i=0; i< AttributProto.sNbAttribut; i++ ) {
					cAttributProto[i] = null;
					cAttribut[i] = null;
			}
		}		

		//------------------------------------------------
		public void copyPropriertyFrom( EntityProto pModel) {


			cOriginalImg = pModel.cOriginalImg;

			for( int i=0; i< AttributProto.sNbAttribut; i++ ) {
					cAttributProto[i] = pModel.cAttributProto[i];

					if( pModel.cAttribut[i] != null )
							cAttribut[i] = new Attribut( pModel.cAttribut[i] );
			}
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public String toString() {
				String lTmp = cName 
						+ " text:" + cText + " " +  AttributProto.sNbAttribut +"\n";
				
				for( int i=0; i< AttributProto.sNbAttribut; i++ ) {

						lTmp = lTmp + " i:" +  i +"  ";

						if( cAttributProto[i] != null ) {
								lTmp = lTmp +	cAttributProto[i].cName + " " + 	cAttribut[i].toString() + "\n";
						}
				}
				return lTmp;
		}
		//------------------------------------------------
		static public void PrintAll() {

				System.out.println( "--------------- EntityProto ---------------" );

				Iterator<EntityProto> lIter = sHashEntityProto.values().iterator();
				while( lIter.hasNext() ){
						 EntityProto lProto = lIter.next();

						 System.out.println( lProto.toString() );
				}

				System.out.println( "--------------- ----------------- ---------------" );
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		// Lecture depuis le .ini
		boolean  read( PPgIniFile pIni, String pSection) {
				

				String lModelName = pIni.get( pSection, DefIni.sModel );
				if( lModelName != null ){
						EntityProto lModel = sHashEntityProto.get( lModelName );	
						if( lModel == null ) {
								System.err.println( "*** Error : EntityProto.Read  : model not found for " 
																		+ pSection + " " +  lModelName );
								return false;
						}
						copyPropriertyFrom( lModel );
				}


				cText = pIni.get( pSection, DefIni.sText, cText );

					 
				cOriginalImg = PPgIniFile.ReadIcon( pIni, pSection, DefIni.sImg, null );				
				

				ArrayList<String> lAttributs = new ArrayList<String>();
				if( pIni.getCollection( pSection, DefIni.sAttribut, lAttributs, " \t", ",:;" ) != null ) {						
						
						for( String lAttributName: lAttributs) {
								AttributProto lAttributProto = AttributProto.GetProto( lAttributName );
								
								if( lAttributProto == null ) {
										System.err.println( "*** Error : EntityProto.Read  : AttributProto not found for " + lAttributName );
										return false;
								}
								
								cAttributProto[ lAttributProto.getId() ] = lAttributProto;
 
								Attribut lAttribut = new Attribut(lAttributProto); 

								cAttribut[ lAttributProto.getId() ] = lAttribut;
								String lVal = pIni.get( pSection, lAttributName );
								if( lVal != null ) 
										lAttribut.init( lVal );

								System.out.print( "\tattribut:" + lAttributProto.cName + " " + lAttributProto.getId() + " " +lAttribut.toString() );

						}

				}
				return true;
		}
		//------------------------------------------------
		static public boolean Read(  PPgIniFile pIni ) {

				ArrayList<String> lCategory = new ArrayList<String>();

				if( pIni.getCollection( DefIni.sEntity, DefIni.sCategory, lCategory, " \t", ",:;" ) == null )
						return false;

				for( String lType: lCategory) {
				
						ArrayList<String> lContents = new ArrayList<String>();
						if( pIni.getCollection( DefIni.sEntity+'.'+ lType, DefIni.sContents, lContents, " \t", ",:;" ) == null )						return false;
						
						for( String lProtoName: lContents) {
								
								// On peut redefinir un objet
								EntityProto lProto = sHashEntityProto.get( lProtoName  );	
								if( lProto == null ) {
										lProto = new EntityProto ( lProtoName, lType );
								}
								
								if( lProto.read( pIni, DefIni.sEntity +'.'+ lType + "." + lProtoName ) )
								sHashEntityProto.put( lType+'.'+lProto.cName, lProto );
								else {
										System.err.println( "*** Error : EntityProto.Read failed for " + lProtoName );
										return false;
								}								
						}
				}								
				return true;
		}
};

//*************************************************
