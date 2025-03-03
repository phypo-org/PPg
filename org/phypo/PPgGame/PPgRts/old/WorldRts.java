package org.phypo.PPg.PPgRts;


import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;

import javax.swing.*;


import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgData.*;


//*************************************************&&

public class WorldRts extends World
{
		GroundMap cMap = null;                                
		public GroundMap getMap() { return cMap; }


		public double getWidth()  { return cMap.getMeterWidth();}
		public double getHeight() { return cMap.getMeterHeight();}

		//------------------------------------------------

    public WorldRts() {// int pWidth, int pHeight) {
				
				//    super( pWidth , pHeight );
				//        getBackground().setColor(new Color(188,164,255));
				//        getBackground().fill();
        
    }

		//------------------------------------------------
		//------------------------------------------------		
		//------------------------------------------------

		public boolean readScenario( PPgIniFile pWorldIni, String pScenario ) {
				

				
				//				System.out.println( "Loading scenarios  " );

				System.out.print( "Loading global for  scenario : " + pScenario +"\t" );

				String lFileCurrent = pWorldIni.get( DefIni.sScenario + '.' + pScenario, DefIni.sFile );
				if( lFileCurrent == null  ) {
						System.out.println( "Error : Scenario not found : " + pScenario );
						return false;
				}



				System.out.println( "Loading current scenario : " + pScenario + " -> " + lFileCurrent );
				
				PPgIniFile lScenarioIni = new PPgIniFile();			
				
				if(  lScenarioIni.readIni( lFileCurrent ) == false  ) {
						System.out.println( "Error : Scenario  file not found : " +  lFileCurrent +" for " + pScenario );
						return false;
				}
				

				System.out.println( "Loading ground prototype ... " );				
				String lProtoIni = lScenarioIni.get( "Map", "FileGround" );
				if( lProtoIni != null  ) {
						PPgIniFile lTmpIni = new PPgIniFile( lProtoIni );	
						if( 	ProtoGroundSquare.Read( lTmpIni ) == false )
								return false;
				}
				else { 
						if(	ProtoGroundSquare.Read( lScenarioIni ) == false )
								return false;
				}
				System.out.println( "-> ok" );
				
				
				
				System.out.println( "Loading map ... " );
				if( (cMap = GroundMap.Read( lScenarioIni )) == null)
						return false;
				
				System.out.println( "Scaling ... " );
				ProtoGroundSquare.MakeScaledImg( cMap  ); // Pour tenir compte de la taille des Square de la map
				cMap.makeScaledImg();
				System.out.println( "-> ok" );
				
				
				
				
				
				System.out.println( "Loading resources ... " );
				if( Resource.Read( pWorldIni) == false )
						return false;

				System.out.println( "Scaling ... " );
				Resource.MakeScaledImg( (int)(cMap.getSizeSquare()*World.Get().cGeneralScale)); 
				System.out.println( "-> ok" );



				System.out.println( "Loading attributes ... " );
				if( AttributProto.Read( pWorldIni) == false )
						return false;
				System.out.println( "-> ok" );




				System.out.println( "Loading entities ... " );
				if( EntityProto.Read( pWorldIni) == false )
						return false;
				System.out.println( "-> ok" );


				Panel2D.Read( pWorldIni, "Panel2D" );

				//				System.out.println( "Loading units ... " );
				//				System.out.println( "-> ok" );
								
				
				System.out.println( "*************************************************************************" );
				System.out.println( "********************************** WORLD ********************************" );

				
				AttributProto.PrintAll();

				EntityProto.PrintAll();

				System.out.println( "*************************************************************************" );

				return true;

		}

		//------------------------------------------------
		public boolean initFromIniFile(  String pFilename, String pScenario ) {

				PPgIniFile lIni = new PPgIniFile( pFilename );

				return readScenario( lIni, pScenario);
		}
}
//*************************************************
