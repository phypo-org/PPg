package org.phypo.PPg.PPgJ3d;



import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;


import org.phypo.PPg.PPgData.*;

//***********************************
// Classe de base pour les joueurs (humain ou non)
//***********************************

abstract public class Gamer  {
		
		// Les ressources detenues par le jouer
		//		double [] cRessourceTab  = new double[Ressource.sCurrentRessourceId];
		//		double [] getRessources() { return cRessourceTab; }

		String           cName;
		public String    getName() { return cName; }
		
		int              cGamerId;
		final int getGamerId() { return cGamerId; }

		int              cGroupId;
		final int getGroupId() { return cGroupId; }

		//		abstract void beginSelectionPosition( Entity pEntity, String pAction, PrototypeUnit pProtoUnit, ImageIcon pIcone );

		boolean isHuman() { return false; }
		boolean isReady() { return true; }
		void    display() {;}

		// Les ressources detenues par le jouer
		ResourcePool cResources= new ResourcePool();
		public ResourcePool getResources() { return cResources; }


		//		public void printRessources(){
		//				for( int i=0; i < Ressource.sCurrentRessourceId; i++ ){
		//						System.out.println( "Ressource[" + i + "] = " + cRessourceTab[i] );
		//				}
		//		}


		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		final boolean isFriend( int pGroupId ){ 

				if( pGroupId == 0 )
						return false;

				return pGroupId == cGroupId;
		}
		//--------------------------------------

		final boolean isEnemy(  int pGroupId ) { 

				if( pGroupId == 0 )
						return false;

				return pGroupId != cGroupId;
		}
		//--------------------------------------
		final boolean isMine( int pGroupId )  { 
				return pGroupId == cGamerId;
		} 

		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		public Gamer( String pName, int pGamerId, int pGroupId ){
								
				cName    = pName;
				cGamerId = pGamerId;
				cGroupId = pGroupId;
			 
		}
		//--------------------------------------
		void run(double pTimeDiff)				
		{				
				// DEPLACER VERS D'AUTRES CLASSE (Une classe Ressources appartenant au joueur ??
				// Cela pourrait etre un classe derivé d'Actor !!! avec un pointeur special dans Gamer

				/*				for( int i=0; i< Ressource.sCurrentRessourceId; i++){

						// Certaine Ressources peuvent etre volatile comme l'electricite par exemple
						// Pour simpliier on met une valeur de vol pour chaque seconde ( 0.9 par exemple )
						// il faut faire le calcul pour chaque tour ( donc calculer un nouveau rapport pour chaque tour)
						// Ce n'est pas tres bon mathematiquemment car le resultat varie en fonction du nombre de tour par seconde.

						double lVolatility = Ressource.GetRessource( i ).getVolatility();
						if( lVolatility != 1 ){
								lVolatility = (1-lVolatility)/pTimeDiff;
								lVolatility = 1-lVolatility;
								cRessourceTab[i] *= lVolatility;
						}
				}
				*/
		}



		// Fonctions a derivé
		public void worldCallExecOrder(double pTimeDiff) {;}

}
//***********************************
