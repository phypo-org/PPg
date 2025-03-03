package org.phypo.PPgGame.PPgData;





import java.util.*;

import org.phypo.PPg.PPgUtils.PPgIniFile;

import java.io.*;
import java.awt.Color;
import java.awt.Image;


//*************************************************

public class AttributProto{

		final public static int MAX_ATTRIBUT = 16;

		public final static AttributProto [] sAttributProto = new AttributProto [MAX_ATTRIBUT];
		public static int   sNbAttribut=0;
	
		public final static HashMap<String, AttributProto> sHashAttributProto = new HashMap<String, AttributProto>();
		
		public static  AttributProto GetProto( String pName ){
				return sHashAttributProto.get( pName );
		}
		public static  AttributProto GetProto( int pId ){
				return sAttributProto[pId];
		}
		//------------------------------------------------

		public int     cId        = 0;
		public String  cName      = null;
		public String  cText      = "";
		public Color   cColor     = Color.green;
		public boolean cStatusBar = false;


		public int getId() { return cId; }
		//------------------------------------------------
		public AttributProto( String pName, int pId ){
				cId = pId;
				cName = pName;
		}		
		//------------------------------------------------
		// Lecture depuis le .ini
		boolean  read( PPgIniFile pIni, String pSection) {
				

				cText  = pIni.get( pSection, DefIni.sText, cText );
				cColor = pIni.getColor( pSection, DefIni.sColor, cColor );

				cStatusBar= pIni.getboolean( pSection, DefIni.sStatusBar, cStatusBar ); 

				return true;
		}
		//------------------------------------------------
		public String toString() {
				return cName + " Id:"+ cId 
						+ " text:" + cText
						+ " status bar:"  + cStatusBar;
		}
		//------------------------------------------------
		static public void PrintAll() {

				System.out.println( "--------------- AttributProto ---------------" );

				for( int i=0; i< sNbAttribut; i++ ) {
						AttributProto lProto  =sAttributProto[i];
						System.out.println( lProto.toString() );
				}

				System.out.println( "--------------- ----------------- ---------------" );
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		static public boolean Read(  PPgIniFile pIni ) {

				for( int i=0; i< MAX_ATTRIBUT; i++ )
						sAttributProto[i] = null;


				ArrayList<String> lContents = new ArrayList<String>();
				if( pIni.getCollection( DefIni.sAttribut, DefIni.sContents, lContents, " \t", ",:;" ) == null )
						return false;

				
				for( String lProtoName: lContents) {

						AttributProto lProto = new AttributProto( lProtoName, sNbAttribut );
						sHashAttributProto.put( lProto.cName, lProto );								
						sAttributProto[sNbAttribut++] = lProto;
										
						lProto.read( pIni, DefIni.sAttribut+"."+lProtoName );
				}
				return true;
		}

}


//*************************************************

		
