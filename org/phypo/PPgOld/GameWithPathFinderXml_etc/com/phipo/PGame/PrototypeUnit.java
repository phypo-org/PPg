package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************
// Contient les caracteristiques commune a chaque type d'entite

public class PrototypeUnit extends PrototypeXmlLoader{
		
		public static final HashMap<String,PrototypeUnit> sHashPrototypeUnit = new HashMap<String,PrototypeUnit>();
		
		public static PrototypeUnit GetPrototypeUnit( String pName ){
				return sHashPrototypeUnit.get( pName );
		}

		PrototypeUnit cRoot   = null;
		PrototypeUnit cMother = null;


		String    cName;
		public String    getName() { return cName; }

		ImageIcon cImg=null;
		ImageIcon getImg() { return cImg; }
		ImageIcon cIcon= null;
		ImageIcon getIcon() { return cIcon; }
		ImageIcon cSmallIcon= null;
		ImageIcon getSmallIcon() { return cSmallIcon; }

		AnimAll   cAnimAll = null;
		AnimAll   getAnimAll() { return cAnimAll; }
		

		double    cWidth=0;
		double getWidth() { return cWidth;}

		double    cHeight=0;
		double getHeight() { return cHeight; }

		//		double cRayon=0;
		int        cRayonCase=0;
		final int    getRayonCase() { return cRayonCase; }
		final double getRayon() { return cRayonCase*PathCarte.sSizeCase; }

		
		public double cAgility=0;
		public double cStrength=0;
		public double cLife=0;
		public double cMana=0;
		public double cWeight=0;


		// Deplacements
		double [] cMovingTab = new double[PrototypeCase.MAX_CASE];
		public double getMoving( int pId ){ return cMovingTab[pId]; }
		public double getMoving( PathCase pCase ){ 
				return cMovingTab[ pCase.getProto().getId() ];
		}

		public boolean isMobil() { return true;}

		// Ressources
		double cCostWork = 0;
		RessourceTable cCostRessourceTab  = new RessourceTable();

		double cRecoltCapacity=0;
		RessourceTable cRecoltRessourceTab  = null;

		RessourceTable cProductRessourceTab = null;


		// Capacite de production/reparation d'autre unites
		double                   cWork = 0;
		ArrayList<PrototypeUnit> cConstructUnitTab = new ArrayList<PrototypeUnit>(); 
		boolean                  cSelectProductSite=false;  // selection obligatoire du site de production
		ArrayList<PrototypeUnit> cRepairUnitTab = new ArrayList<PrototypeUnit>();
		double                   cRepairRange=1;
		

		// Capacite de transport
		double cMaxLoading=0;

		//		public int cResistance;
		//		public int cEncombrement;
		//		public int cPoids;

		//-----------------------------------------------
		// L'arme principale est toujours en premier
		// c'est elle qui va conditionner le comportement 
		// de l'unite

		public PrototypeWeapon  [] cWeapon = null;
		

		//    public Armor   [] cAmor   = null;
		//    public Item    [] cItem   = null;

		//------------------------------------------------		
		boolean isRepairUnit() {
				return ! cRepairUnitTab.isEmpty();
		}
		//------------------------------------------------		
		boolean isRepairUnit( PrototypeUnit pProto) {

				return  cRepairUnitTab.indexOf( pProto ) != -1;
		}
		//------------------------------------------------		
		public PrototypeUnit( String pName ){
				cName = pName;

				for( int i=0; i<PrototypeCase.MAX_CASE; i++){
						cMovingTab[i] = 0;
				}
		}		
		//------------------------------------------------		
		public PrototypeUnit( String pName, ImageIcon pImg,
														double  pWidth,  double pHeight ){
				for( int i=0; i<PrototypeCase.MAX_CASE; i++){
						cMovingTab[i] = 0;
				}

				cName = pName;
				cImg  = pImg;	


				cImg    = new ImageIcon( pImg.getImage().getScaledInstance( (int)pWidth, (int)pHeight, Image.SCALE_SMOOTH ));
				cIcon  = new ImageIcon( pImg.getImage().getScaledInstance( World.sSizeIcon, World.sSizeIcon, Image.SCALE_SMOOTH ));
				cSmallIcon  = new ImageIcon( pImg.getImage().getScaledInstance( World.sSizeSmallIcon, World.sSizeSmallIcon, Image.SCALE_SMOOTH ));

				cImg.getImage().setAccelerationPriority(1);
				cIcon.getImage().setAccelerationPriority(1);
				cSmallIcon.getImage().setAccelerationPriority(1);

				cWidth  = pWidth;
				cHeight = pHeight;


				//				cRayon = Math.sqrt( cWidth*cWidth + cHeight* cHeight);
				//				cRayonCase = (int)((cRayon/World.sTheWorld.GetCaseSize()));
				
				//				System.err.println( "+++++++++++++ cRayon :" + cRayon + ":" +  cRayonCase );
				

				///////				cVectPrototype.add( this );
		}
		//------------------------------------------------
		public String toString() {
				return cName 
						+ " Width:" + cWidth
						+ " Height:" + cHeight
						+ " agility:" + cAgility;
		}
		//------------------
		public String getMyTag() { return XmlLoader.TAG_XML_UNIT_PROTO; }
		public PrototypeXmlLoader findByName( String pName )    { return sHashPrototypeUnit.get( pName );		}

		public PrototypeXmlLoader create( String pName)  { 
				PrototypeUnit lNew = new PrototypeUnit( pName ); 
				sHashPrototypeUnit.put( pName, lNew );
				return lNew;
		}
		//------------------

		public void doInherit( PrototypeXmlLoader pMother ){
				  cMother = (PrototypeUnit)pMother;

					if( cMother.cRoot != null )
							cRoot = cMother.cRoot;
					else
							cRoot = cMother;

					
					
				 cImg      = cMother.cImg;
				 cIcon    = cMother.cIcon;
				 cSmallIcon    = cMother.cSmallIcon;

				 cWidth    = cMother.cWidth;
				 cHeight   = cMother.cHeight;	 
				 //				 cRayon    =  cMother.cRayon;	  
				 cRayonCase    =  cMother.cRayonCase;	  

				 cAgility  = cMother.cAgility;
				 cStrength = cMother.cStrength;
				 cLife     = cMother.cLife;
				 cMana     = cMother.cMana;
				 cWeight   = cMother.cWeight;

				 
				 System.arraycopy( cMother.cMovingTab, 0, cMovingTab, 0, PrototypeCase.MAX_CASE );

				 cCostWork = cMother.cCostWork;
				 //				 System.out.println( "Ressourve:" + Ressource.Length() 
				 //														 +" " +cCostRessourceTab.length+" " +cMother.cCostRessourceTab.length 
				 //														 + " " + cRecoltRessourceTab.length + " " + cMother.cRecoltRessourceTab.length 
				 //														 + " " + cProductRessourceTab.length+ " " + cMother.cProductRessourceTab.length);


				 if( cMother.cCostRessourceTab  != null ){
						 cCostRessourceTab = new RessourceTable();
						 cCostRessourceTab.copyFrom( cMother.cCostRessourceTab );						 
				 }
				 if( cMother.cRecoltRessourceTab != null ){
						 cRecoltRessourceTab = new RessourceTable();
						 cRecoltRessourceTab.copyFrom( cMother.cRecoltRessourceTab );
				 }
				 if( cMother.cProductRessourceTab != null ){
						  cProductRessourceTab= new RessourceTable();
							cProductRessourceTab.copyFrom( cMother.cProductRessourceTab );
				 }
				 


				 cRecoltCapacity	  = cMother.cRecoltCapacity;
				 
				 cWork  = cMother.cWork;
				 for( PrototypeUnit lProto: cMother.cConstructUnitTab)
						 cConstructUnitTab.add( lProto );
				 
				 for( PrototypeUnit lProto: cMother.cRepairUnitTab)
						 cRepairUnitTab.add( lProto );

				 cMaxLoading = cMother.cMaxLoading;
		}
		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		// Mettre ces commande dans un fichier XML (inteface)

		final static String sCmdGuardSite    = "Guard";
		final static String sCmdDetroyMySelf = "Destroy";
		final static String sCmdConstruction = "Construction";
		final static String sCmdStop  = "Stop";

		//--------------------------------------
		boolean setMenu( Entity pEntity, JPopupMenu pPopMenu, InterfaceDisplayGamer pDisplayGamer ){

				JMenuItem lItem;

				lItem= new JMenuItem( getName() );
				pPopMenu.add( lItem );
				
				
				lItem= new JMenuItem( sCmdDetroyMySelf );
				pPopMenu.add( lItem );
				lItem.addActionListener(pDisplayGamer);
				

				if(  cConstructUnitTab.size() >0){
						pPopMenu.add( new JSeparator() );
						JMenu lMenuProductUnit = new JMenu( sCmdConstruction );
						for( PrototypeUnit lProdUnit:cConstructUnitTab){
								lItem = new JMenuItem(sCmdConstruction + ":" + lProdUnit.getName() );
								lMenuProductUnit.add( lItem );
								lItem.addActionListener(pDisplayGamer);
						}
								
						pPopMenu.add( lMenuProductUnit );
				}
				return true;
		}
		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		void actionCommand( Gamer pGamer, Entity pEntity, CommandIcon pCommand, Point2D.Double lPos ){
				
				System.out.println( "PrototypeUnit.actionCommand  " + pCommand );
				switch( pCommand.getOrder() ){

				case CONSTRUCT:
						PrototypeUnit lProtoUnit = pCommand.getPrototype();
						if( lProtoUnit.cSelectProductSite && lPos == null ) {
								System.out.println( "PrototypeUnit.actionEvent : Select Site required" );

								// Pas terrible. Normalement le joueur computer ne l'utilise pas
								if( pGamer != null && pGamer.isHuman() )
										((GamerHuman)pGamer).beginSelectionPosition( pEntity, pCommand, lProtoUnit, lProtoUnit.getImg() );
								return;
						}
						if( lPos == null )						
								beginConstructEntity( pEntity, lProtoUnit, pEntity.getConstructPosition() );
						else
								beginConstructEntity( pEntity, lProtoUnit, lPos );
						break;

				case DESTROY:
				case EVOLUTION:
				case GUARD:
				case STOP:
						pEntity.stopCurrentMission();
				}

		}
		//--------------------------------------
		void actionEvent( Gamer pGamer, Entity pEntity, String pAction, Point2D.Double lPos ){
				System.out.println( "PrototypeUnit.actionEvent for " + cName
														+ "<" + pAction +">" );

				int lIndex = pAction.indexOf( ':' );
				if( lIndex == -1 ){
						System.err.println( "PrototypeUnit.actionEvent : error for " + pAction );
						return ;
				}
				
				String lCmd = pAction.substring( 0, lIndex );
				
				System.out.println( "PrototypeUnit.actionEvent " + lCmd  );						
				
				if( lCmd.equals( sCmdGuardSite )){
						///////	pEntity.setAction( new ActionEntityGuardSite(pEntity));
				}
				else if( lCmd.equals( sCmdDetroyMySelf )){
						////				pEntity.setAction( new ActionEntityDestroy(pEntity) );
				}
				else if( lCmd.equals( sCmdConstruction )){
						String lVal = pAction.substring( lIndex+1 );
						PrototypeUnit lProtoUnit = PrototypeUnit.GetPrototypeUnit( lVal );
						if( lProtoUnit == null ) {
								System.err.println( "PrototypeUnit.actionEvent : error PrototypeUnit not found " 
																		+ lVal + " for " + pAction );
								return ;
						}
						if( cConstructUnitTab.contains( lProtoUnit ) == false ){
								System.err.println( "PrototypeUnit.actionEvent : error PrototypeUnit not capacity " 
																		+ lVal + " for " + pAction );
								return ;
						}

						System.out.println( ">>>>>>>>PrototypeUnit.cSelectProductSite : " + lProtoUnit.cSelectProductSite  );

						if( lProtoUnit.cSelectProductSite && lPos == null ) {
								System.out.println( "PrototypeUnit.actionEvent : Select Site required" 
																		+ lVal + " for " + pAction );								
								if( pGamer != null )
										pGamer.beginSelectionPosition( pEntity, pAction, lProtoUnit, lProtoUnit.getImg() );
								return;
						}
						if( lPos == null )						
								beginConstructEntity( pEntity, lProtoUnit, pEntity.getConstructPosition() );
						else
								beginConstructEntity( pEntity, lProtoUnit, lPos );

				}				
		}
		//------------------------------------------------

		void beginConstructEntity( Entity pEntity, PrototypeUnit pProtoUnit, Point2D.Double pPosition ){

				Entity lNewEntity = null;

				if( pProtoUnit.cCostRessourceTab.subTo( pEntity.getGamer().getRessources() ) == false ){
						//	METTRE UNE MESSAGES !!!!
						System.out.println( "PrototypeUnit.beginConstructEntity : no enougth ressources" + pProtoUnit.getName());
						return ;
				}

				if( pProtoUnit.isMobil() )
						lNewEntity = new EntityMobil( pEntity.cGamer, pEntity.getGroupId(), pProtoUnit, 
																		 pPosition.getX()-pProtoUnit.getWidth()/2, 
																		 pPosition.getY()-pProtoUnit.getHeight()/2);
																					//  pPosition.getX(), pPosition.getY());
				else
						lNewEntity = new Entity( pEntity.cGamer, pEntity.getGroupId(), pProtoUnit, 
																		 pPosition.getX()-pProtoUnit.getWidth()/2, 
																		 pPosition.getY()-pProtoUnit.getHeight()/2);	

				if( pEntity.isMobil() ){
						pEntity.setMission( new ActionEntityMobilRepair( ((EntityMobil)pEntity), lNewEntity ));
				}
				else{
						pEntity.setMission( new ActionEntityInternalRepair( pEntity, lNewEntity ));
				}

				lNewEntity.setLife( 0.001 );
				lNewEntity.setInConstruct( true );
				World.sTheWorld.addEntity( lNewEntity );										
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		void addCommandIconAction( Entity pEntity, Gamer pGamer, PanelBox pPanel, ArrayList<CommandIcon> pVect ){
				
		}
		//------------------------------
		void addCommandIconUpgrade( Entity pEntity, Gamer pGamer, PanelBox pPanel, ArrayList<CommandIcon> pVect ){
				
		}
		//------------------------------
		void addCommandIconConstruct( Entity pEntity, Gamer pGamer, PanelBox pPanel, ArrayList<CommandIcon> pVect ){

				for( PrototypeUnit lProdUnit:cConstructUnitTab){
						
						pVect.add( new CommandIcon( pPanel, pEntity, CommandIcon.OrderCommandIcon.CONSTRUCT, lProdUnit, 
																				lProdUnit.getSmallIcon() ));																	
				}
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public boolean addWeapon( String pWeaponName ){
				if( pWeaponName != null ){
						PrototypeWeapon lProtoWeapon = PrototypeWeapon.GetPrototypeWeapon( pWeaponName );
						if( lProtoWeapon == null ){
								System.out.println( "\t\tPrototypeUnit.loadXml weapon not found:"+ pWeaponName + " for " + cName);
								return false;
						}
						else {
								System.out.println( "\t\tPrototypeUnit.loadXml weapon :"+ pWeaponName + " for " + cName);
								if( cWeapon == null )
										cWeapon = new PrototypeWeapon[1];
								else {
										PrototypeWeapon[] lTmpWeapon = new PrototypeWeapon[cWeapon.length+1];
										System.arraycopy( cWeapon, 0, lTmpWeapon, 0, cWeapon.length );
										cWeapon = lTmpWeapon;
								}										
								cWeapon[cWeapon.length-1] = lProtoWeapon;
						}
				}
				return true;
		}
		//------------------------------------------------

		public boolean loadXmlNode( Node pMotherNode, Node pNode, XmlLoader pXmlLoader ){


				System.out.println( "\t\tloadXml for PrototypeUnit " + cName);

				//------------
				String lFile = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_FILE );
				if( lFile != null){
						// Charger l'image ???
				}
				//------------
				 cHeight   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_HEIGHT, cHeight  );
				 cWidth    = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_WIDTH,  cWidth );
				 //				 cRayon = Math.sqrt( cWidth*cWidth + cHeight* cHeight);
				 //		 cRayonCase = (int)((cWidth+cHeight)/(World.sTheWorld.GetCaseSize()*2));				

				 cAgility  = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_AGILITY,  cAgility );
				 cStrength = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_STRENGTH, cStrength  );
				 cLife     = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_LIFE,  cLife );
				 cMana     = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_MANA,  cMana );
				 cWeight   = XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_WEIGHT, cWeight  );

				 //				 = XmlLoader.GetAttributeDouble( pNode, XmlLoader.TAG_XML_,   );

				 cRayonCase = (int)XmlLoader.GetAttributeDouble( null, pNode, XmlLoader.TAG_XML_CASE_OCCUPATION,  cRayonCase );
				 
				 //				 System.out.println( "+++++++++++++ cRayon :" + cRayon + ":" +  cRayonCase );

				 String lWeaponName = XmlLoader.GetAttributeVal( null, pNode, XmlLoader.TAG_XML_WEAPON ); 

				System.out.println( "++++++++ loadXmlNode  REMETTRE addWeapon *************" + cName);
				System.out.println( "++++++++ loadXmlNode  REMETTRE addWeapon *************" + cName);
				System.out.println( "++++++++ loadXmlNode  REMETTRE addWeapon *************" + cName);

				 // A REMETTRE MAIS NE MARCHE PAS Sword1 not define !!!!!
				 //				 if( addWeapon( lWeaponName ) == false )
				 //						 return false;


				//------------

				 ArrayList<Node> lSubNodeArray = XmlLoader.GetSubNode( pNode );
				 if( lSubNodeArray != null )
						 for( Node lSubNode:lSubNodeArray){
								 
								 // --------------------- EQUIPMENT ---------------------
								 if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_EQUIPMENT )) {
										 ArrayList<Node> lLeafNodeArray = XmlLoader.GetSubNode( lSubNode );
										 if( lLeafNodeArray != null )
												 for( Node lLeafNode:lLeafNodeArray){

														 if( lLeafNode.getNodeName().equals( XmlLoader.TAG_XML_WEAPON )){
																 String lName   = XmlLoader.GetAttributeVal( null, lLeafNode, XmlLoader.TAG_XML_NAME );	
																 if( lName != null ){
																		 int lNb =  XmlLoader.GetAttributeInt( null, lLeafNode, XmlLoader.TAG_XML_QUANTITY, 1);
																		 for( int i=0; i< lNb ; lNb++)
																				 if( addWeapon( lName ) )
																						 return false;
																		 
																		 // AJOUTER AUSSI LES MUNITIONS s'il y a lieu !
																 }
														 }// Il est sans doute possible de reduire tous (weapon,armor spell a un seul tpe general
														 // avec des proprietes permettant d'implementer chacun des effets voulues )
														 // (PAS focement evidant pour Weapon qui a un conmportement particulier !)
														 else  if( lLeafNode.getNodeName().equals( XmlLoader.TAG_XML_SHIELD) ){
																 
														 } else  if( lLeafNode.getNodeName().equals( XmlLoader.TAG_XML_SPELL) ){																 
														 }
												 }
								 }		
								 // --------------------- DESIGN ---------------------
								 if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_DESIGN )) {
										 
										 String lStrSprite = XmlLoader.GetAttributeString( null, lSubNode, XmlLoader.TAG_XML_ANIM, null );
										 if( lStrSprite != null ){												 
												 cAnimAll = AnimAll.FindByName( lStrSprite );												 
												 System.out.println( ">>>>>>>>>>>>>>loadXmlNode ANIM ANIM ANIM " 
																						 + lStrSprite + " : " + cAnimAll );
										 }
										 
										 String lStrImg = XmlLoader.GetAttributeString( null, lSubNode, XmlLoader.TAG_XML_ICON, "default.img" );
										 if( World.sFurtifMode )
												 lStrImg = XmlLoader.GetAttributeString( null, lSubNode, XmlLoader.TAG_XML_FURTIF, "furtif.img" );
										 
										 if( cWidth >0 && cHeight >0 ){
												 ImageIcon lTmpImage = new ImageIcon( lStrImg );
												 cImg = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(cWidth*World.sGeneralScale), 
																																											 (int)(cHeight*World.sGeneralScale), 		
																																											 Image.SCALE_SMOOTH ));
												 cImg.getImage().setAccelerationPriority((float)0.5);

												 cIcon  = new ImageIcon( lTmpImage.getImage().getScaledInstance( World.sSizeIcon, 
																																												 World.sSizeIcon, Image.SCALE_SMOOTH ));
												 cIcon.getImage().setAccelerationPriority((float)0.5);

												 cSmallIcon  = new ImageIcon( lTmpImage.getImage().getScaledInstance( World.sSizeSmallIcon,
																																															World.sSizeSmallIcon, Image.SCALE_SMOOTH ));
												 cSmallIcon.getImage().setAccelerationPriority((float)0.5);
										 }				 
								 }
								 // --------------------- MOVING ---------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_MOVING )) {						
										 if( XmlLoader.GetAttributeTerrainVal( lSubNode, XmlLoader.TAG_XML_MOVING, cMovingTab ) == false )
												 return false;
								 }
								 // ---------------- CONSTRUCTION COST ---------------
								 else	 if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_CONSTRUCTION_COST )) {

										 if( XmlLoader.GetAttributeRessource( lSubNode,cCostRessourceTab , XmlLoader.TAG_XML_CONSTRUCTION_COST ) == false )
												 return false;
										 
										 cCostWork  = XmlLoader.GetAttributeDouble( null, lSubNode, XmlLoader.TAG_XML_WORK,  cCostWork );
										 cSelectProductSite = XmlLoader.GetAttributeBoolean( null, lSubNode, XmlLoader.TAG_XML_SELECT_SITE, cSelectProductSite);
								 }
								 // ------- TAG_XML_CONSTRUCTION ---------
								 if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_CONSTRUCT_ON)) {						
										 if( XmlLoader.GetAttributeTerrainVal( lSubNode, XmlLoader.TAG_XML_CONSTRUCT_ON, cMovingTab ) == false )
										 return false;
								 }
								 // --------------------- CONSTRUCT ------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_CONSTRUCT )) {						
										 if( XmlLoader.GetAttributeUnit( lSubNode, cConstructUnitTab, XmlLoader.TAG_XML_CONSTRUCT )==false)
												 return false;

										 cWork  = XmlLoader.GetAttributeDouble( null, lSubNode, XmlLoader.TAG_XML_WORK,  cWork );
								 }					
								 // --------------------- REPAIR ---------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_REPAIR )) {
										 
										 if( XmlLoader.GetAttributeBoolean( null, lSubNode, XmlLoader.TAG_XML_LIKE_CONSTRUCT,  false )){
												 System.out.println( " REPAIR LIKE CONSTRUCT    REPAIR LIKE CONSTRUCT    REPAIR LIKE CONSTRUCT   REPAIR LIKE CONSTRUCT" );
												 cRepairUnitTab.addAll( cConstructUnitTab );
										 }
										 else
												 System.out.println( "NOT NOT NOT  REPAIR LIKE CONSTRUCT    REPAIR LIKE CONSTRUCT    REPAIR LIKE CONSTRUCT   REPAIR LIKE CONSTRUCT" );
		 
										 if( XmlLoader.GetAttributeUnit( lSubNode, cRepairUnitTab, XmlLoader.TAG_XML_REPAIR )==false)
												 return false;
										 
										 cRepairRange  = XmlLoader.GetAttributeDouble( null, lSubNode, XmlLoader.TAG_XML_REPAIR_RANGE,  cRepairRange );
								 }					
								 // --------------------- RECOLT ---------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_RECOLT )) {						

										 RessourceTable lTmpRessource = new RessourceTable();
										 if( XmlLoader.GetAttributeRessource( lSubNode,lTmpRessource , XmlLoader.TAG_XML_RECOLT ) == false )
												 return false;

										 if( lTmpRessource.isVoid() == false )
												 cRecoltRessourceTab = lTmpRessource;

										 cRecoltCapacity =  XmlLoader.GetAttributeDouble( null, lSubNode, XmlLoader.TAG_XML_RECOLT_CAPACITY,  cRecoltCapacity );
								 }					
								 // --------------------- PRODUCT  ---------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_PRODUCT)) {						

										 RessourceTable lTmpRessource = new RessourceTable();
										 
										 if( XmlLoader.GetAttributeRessource( lSubNode, lTmpRessource, XmlLoader.TAG_XML_PRODUCT ) == false )
												 return false;	
										 
										 if( lTmpRessource.isVoid() == false )
												 cProductRessourceTab  = lTmpRessource;
								 }					
								 // ---------------------
								 else	if( lSubNode.getNodeName().equals( XmlLoader.TAG_XML_LOADING )) {						
										 cMaxLoading =  XmlLoader.GetAttributeDouble( null, lSubNode, XmlLoader.TAG_XML_MAX_LOADING,  cMaxLoading );
								 }					
								 // ---------------------
								 //								 else	if( pNode.getNodeName().equals( XmlLoader.TAG_XML_ )) {						
								 ///								 }					
			 
								 // --------------------- EQUIPEMENT -----------------
						 }				 
		return true;
		}
};
//*************************************************
