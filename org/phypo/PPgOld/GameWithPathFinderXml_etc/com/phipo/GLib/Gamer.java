package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.*;


//***********************************
abstract public class Gamer implements InterfaceHandlerXmlNode {
		
		// Les ressources detenues par le jouer
		double [] cRessourceTab  = new double[Ressource.sCurrentRessourceId];
		double [] getRessources() { return cRessourceTab; }

		String           cName;
		public String           getName() { return cName; }
		
		int              cGamerId;
		final int getGamerId() { return cGamerId; }

		int              cGroupId;
		final int getGroupId() { return cGroupId; }

		abstract void beginSelectionPosition( Entity pEntity, String pAction, PrototypeUnit pProtoUnit, ImageIcon pIcone );
		abstract void execOrder();

		boolean isHuman() { return false; }
		boolean isReady() { return true; }
		void    display() {;}


		public void printRessources(){
				for( int i=0; i < Ressource.sCurrentRessourceId; i++ ){
						System.out.println( "Ressource[" + i + "] = " + cRessourceTab[i] );
				}
		}


		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		final boolean isFriend( Entity pEntity ){ 
				int lEntityGroupId = pEntity.getGroupId();

				if( lEntityGroupId == 0 )
						return false;

				return lEntityGroupId == cGroupId;
		}
		//--------------------------------------

		final boolean isEnemy( Entity pEntity ) { 
				int lEntityGroupId = pEntity.getGroupId();

				if( lEntityGroupId == 0 )
						return false;

				return lEntityGroupId != cGroupId;
		}
		//--------------------------------------
		final boolean isMine( Entity pEntity )  { 
				return pEntity.getGamerId() == cGamerId;
		} 

		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		public Gamer( String pName, int pGamerId, int pGroupId ){
								
				cName    = pName;
				cGamerId = pGamerId;
				cGroupId = pGroupId;
				
				for( int i=0; i < Ressource.sCurrentRessourceId; i++ ){
						 cRessourceTab[i] = 0;
				}
		}
		//--------------------------------------
		void run(double pTimeDiff)				
		{				

				for( int i=0; i< Ressource.sCurrentRessourceId; i++){

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
		}
		//------------------------------------------------
		//-----------------  XML -------------------------
		//------------------------------------------------

		public boolean processNodeXml( Node pMotherNode, Node pNode, XmlLoader pXmlLoader  ){

				System.out.println( "+++++++++++++ Gamer.processNodeXml " + pNode.getNodeName() );

				// ---------------------
				if( pNode.getNodeName().equals( XmlLoader.TAG_XML_BUILDING )
						||  pNode.getNodeName().equals( XmlLoader.TAG_XML_UNIT)) {
						
						String lProtoName = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_PROTO );
						PrototypeUnit lProtoUnit = null;
						if( lProtoName == null 	|| (lProtoUnit = PrototypeUnit.GetPrototypeUnit( lProtoName )) == null ){
								System.err.println( "Error Building/Unit with no prototype :" + lProtoName + " for gamer "+ cName );
								return false;
						}
						
						Point2D.Double lPosition = XmlLoader.GetAttributePosition( null, pNode, null );
						if( lPosition == null ){
								System.err.println( "Error Building/Unit with no position :" + lProtoName + " for gamer "+ cName );
								return false;
						}
						
						Entity lEntity = null;

						if( pNode.getNodeName().equals( XmlLoader.TAG_XML_BUILDING )	)
								lEntity = new Entity( this, getGroupId(), lProtoUnit, lPosition.getX(), lPosition.getY());
						else
								lEntity = new EntityMobil( this, getGroupId(), lProtoUnit, lPosition.getX(), lPosition.getY());

						if( lEntity != null )
								World.sTheWorld.addEntity( lEntity );
				}
				else
						// ---------------------
						if( pNode.getNodeName().equals( XmlLoader.TAG_XML_RESSOURCE_AT_BEGIN) ){

								System.out.println( ">>>>>>> Gamer.processNodeXml TAG_XML_RESSOURCE_AT_BEGIN" );

								if( XmlLoader.GetAttributeRessource( pNode, cRessourceTab, XmlLoader.TAG_XML_RESSOURCE_AT_BEGIN ) == false )
										return false;
						}

				// ---------------------

				return true;
		}
		//------------------------------------------------
		boolean loadXml( Node pNode, XmlLoader pXmlLoader ){
				
				return  pXmlLoader.processNode( pNode, this );
		}
}
//***********************************
