package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;

import javax.swing.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************
// Classe de base de tout ce qui va vivre
// et dans le programme

class Entity extends Rectangle2D.Double {

		static long sEntityId=1;

		long cMyId=sEntityId++;
		long getMyId() { return cMyId; }

		public enum ModeAddMission{
				REMPLACE_ALL,
				ADD_FIRST,
				ADD_END
		};
		
		public Arsenal cArsenal = null;


		Util.Orientation cOrientation = Util.Orientation.POS_SOUTH;
		Util.Orientation getOrientation() { return cOrientation; }
		void setOrientation ( Util.Orientation pOrientation ) { cOrientation = pOrientation; }
		
	 
		public double getMX() { return getX() + getWidth()*0.5; }
		public double getMY() { return getY() + getHeight()*0.5; }
		void   moveTo( Point2D.Double pPointDest ) { 
				setRect( pPointDest.getX() - getWidth()*0.5,  pPointDest.getY()- getHeight()*0.5,
								 getWidth(), getHeight());
		}
		void   moveTo( double pX, double pY ) { 
				setRect( pX - getWidth()*0.5,  pY- getHeight()*0.5,
								 getWidth(), getHeight());
		}
		
		Point2D.Double getPosition() { 
				return new Point2D.Double( getX() + getWidth()*0.5,  getY()+ getHeight()*0.5 );
		}
		Point2D.Double getRightBottomPosition() { 
				return new Point2D.Double( getX() + getWidth(), getY() + getHeight());
		}

		int cGamerId;
		Gamer cGamer=null;

		final int getGamerId() { return cGamerId; }
		final Gamer getGamer() { return cGamer; }

		int cGroupId;
		final int getGroupId() { return cGroupId; }


		PrototypeUnit cPrototype;
		PrototypeUnit getPrototype() { return cPrototype; }

		public boolean isMobil() { return cPrototype.isMobil(); }


		ImageIcon getImg() { return cPrototype.getImg(); }
		ImageIcon getIcon() { return cPrototype.getIcon(); }
		AnimAll   getAnimAll() { return cPrototype.getAnimAll(); }

		ArrayList<ActionEntity> cVectMission = new ArrayList<ActionEntity>() ;

		public void addMissionEnd(ActionEntity pAction) { cVectMission.add(pAction); }
		public void addMissionFirst(ActionEntity pAction) { cVectMission.add( 0, pAction); }
		public void setMission(ActionEntity pAction) { cVectMission.clear(); cVectMission.add(pAction); }
		
		public void addMission( ActionEntity pAction, ModeAddMission pMode){
				switch( pMode ){
				case REMPLACE_ALL: setMission(pAction); break;						
				case ADD_FIRST:    addMissionFirst(pAction);break;
				case ADD_END:      addMissionEnd(pAction); break;
				}
		}

		PathCase cCurrentCase = null;
		void setCurrentCase( PathCase pCase ) { cCurrentCase = pCase; }
		PathCase getCurrentCase()             { return cCurrentCase; }


		boolean cIsSelect = false;
		final boolean isSelect()          { return cIsSelect; }
		boolean setSelect( boolean pSel ) { cIsSelect = pSel; return true; }


		public double cLife=0;
		public void setLife( double pLife ) { cLife=pLife; }
		public double cMana=0;
		
		boolean cFreeze = false;
		public boolean getFreeze() { return cFreeze; }
		public void setFreeze( boolean pFreeze ) { cFreeze= pFreeze;}

		boolean cInConstruct = false;
		public boolean getInConstruct()                    { return cInConstruct; }
		public void setInConstruct( boolean pInConstruct ) { cInConstruct= pInConstruct;}

		boolean cWorkingConstruct = false;
		public boolean getWorkingConstruct() { return cWorkingConstruct; }
		public void setWorkingConstruct( boolean pWorkingConstruct ) { cWorkingConstruct= pWorkingConstruct;}


		public RessourceTable cRecoltRessource = null;
		
		//-----------------------
		public	Entity( Gamer pGamer, int pGroupId, PrototypeUnit pProto,
										double pX, double pY  ){
				
				super( pX, pY, pProto.getWidth(), pProto.getHeight() );
							 
				cPrototype = pProto;
				cGamer     = pGamer;
				cGamerId   = cGamer.getGamerId();
				cGroupId   = pGroupId;

				cLife = cPrototype.cLife;
				cMana = cPrototype.cMana;

				if( cPrototype.cRecoltRessourceTab != null ){
						cRecoltRessource = new RessourceTable(cPrototype.cRecoltRessourceTab);						
				}				

				if( cPrototype.cWeapon != null ) {
						cArsenal = new Arsenal ( cPrototype.cWeapon );
				}						
		}	
		/*
		//-----------------------
		public boolean equals( Object pObj){
				return cName.equals(pObj);
		}
		//-----------------------
		void destroy(boolean pAutoExtract){
		}
		//-----------------------
		boolean isDependantOf( Entity pEntity){
				return false;
		}
		*/
		//-----------------------
		Rectangle2D.Double getRect(){
				return this;
		}
		//-----------------------
		void draw( Graphics g){
		}
		//--------------------------------------
		ArrayList<CommandIcon>  getCommandIcon( Gamer pGamer, PanelBox pPanel ){
				
				ArrayList<CommandIcon> lVectCommandIcon = new ArrayList<CommandIcon>();

				lVectCommandIcon.add( new CommandIcon( pPanel, this, CommandIcon.OrderCommandIcon.DESTROY, null,
																							 World.sSmallIconDestroy ));
				
				lVectCommandIcon.add( new CommandIcon( pPanel, this, CommandIcon.OrderCommandIcon.STOP, null,
																							 World.sSmallIconStop ));

				if( getInConstruct() || getFreeze() ){
						return lVectCommandIcon;
				}
				
				if( isMobil() ){
						lVectCommandIcon.add( new CommandIcon( pPanel, this, CommandIcon.OrderCommandIcon.GUARD, null,
																									 World.sSmallIconGuard ));
				}
				
				getPrototype().addCommandIconAction( this, pGamer, pPanel, lVectCommandIcon );						
				getPrototype().addCommandIconUpgrade( this, pGamer, pPanel, lVectCommandIcon );
				getPrototype().addCommandIconConstruct( this, pGamer, pPanel, lVectCommandIcon );
				
				return lVectCommandIcon;
		}
		// ------------------------
		JPopupMenu initPopup(JPopupMenu p_popup){		
				
				return null;
		}
		// -------------------------
		String    getTypeProps() { return "Unknown";}
		// -------------------------
		int    getNbProps()     { return 1; }
		// -------------------------
		String getProps(int i, boolean p_title ) {
				switch( i) {
				case 0:	
						if( p_title ) 
								return "Type";
						else 
								return cPrototype.getName();
						
				default : ;
				}
				return "Unknown props";
		}
		//--------------------------------------
		// A MODIFIER POUR RENVOYER UN POINT DE DESTINATION !

		Point2D.Double getConstructPosition() { return getRightBottomPosition(); }

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
		final boolean isSameGamer( Entity pEntity )  { 
				return pEntity.getGamerId() == cGamerId;
		} 
		//--------------------------------------
		//--------------------------------------
		//--------------------------------------
		public void sendOrderTerrain( Point2D.Double pPoint, ModeAddMission pModeAdd ){
		}
		//--------------------------------------
		public void sendOrderAttackEnemy( Point2D.Double pPoint, Entity pEntity ){;}
		//--------------------------------------
		public void sendOrderHelpFriend( Point2D.Double pPoint, Entity pEntity ){

				System.out.println( "Entity.sendOrderHelpFriend");

				if( isSameGamer( pEntity ) ){
						// Meme joueur					 						

						System.out.println( "SameGamer");
	
						if( getPrototype().isRepairUnit() ) {
								// c'est un reparateur !
								System.out.println( "Reparateur");

								if( getPrototype().isRepairUnit( pEntity.getPrototype())){
										// et il repare justement ce type d'unite

										System.out.println( "Good Reparateur");
										sendOrderRepair( pPoint, pEntity );
										return ;
								}
						}
				}							
				// Dans tout les autres cas On va suivre l'unite et on verra
				sendOrderFollow( pPoint, pEntity );
		}
		//--------------------------------------
		public void sendOrderRepair( Point2D.Double pPoint, Entity pEntity ){
				System.out.println( "Entity.sendOrderRepair");

				setMission( new ActionEntityMobilRepair( ((EntityMobil)this), pEntity ));	
		}
		//--------------------------------------
		public void sendOrderFollow( Point2D.Double pPoint, Entity pEntity ){
				System.out.println( "Entity.sendOrderFolow");
		}
		//--------------------------------------
		public void sendOrderUsing( Point2D.Double pPoint, Entity pEntity ){
				System.out.println( "Entity.sendOrderUsing");
		}
		//--------------------------------------
		public void sendOrderAttack( Point2D.Double pPoint ){
				
		}
		//--------------------------------------
		public void sendOrderDefend( Point2D.Double pPoint ){;}
		public void sendOrderDefend( Entity pEntity ){;}
		//--------------------------------------
		ArrayList<PathCase> getPath() { return null;}
		//--------------------------------------
		ArrayList<PathCase> getPath2() { return null;}
		//--------------------------------------
		void stopCurrentMission(){

				cVectMission.remove(0);
		}
		//--------------------------------------
		void run( double pTimeDiff ) {

				if( cInConstruct )
						return;

				// SI PRODUCTION AJOUTER LA PRODUCTION*pTimeDiff AU GAMER 


				try{
						if( execReflex( pTimeDiff ) == false ) // excution des action normale attaque/defense ...
								return;

						// On fait toutes actions en parralele en enlevent celle qui sont finit !

						Iterator<ActionEntity> lIterMission = cVectMission.iterator();
						
						while( lIterMission.hasNext() ) {
								if( lIterMission.next().exec( pTimeDiff ) == false ){
										lIterMission.next().finish();
										lIterMission.remove();
								}
						}						
								//				System.out.print(".");
				}
				catch( Exception e ){
						/////////					System.out.println( "Entity.run Exception for " + getPrototype().getName() );
						//////						e.printStackTrace();
				}				
		}
		//--------------------------------------
		boolean execReflex( double pTimeDiff ) {
				
				// Production !
				if( cPrototype.cProductRessourceTab != null ){
				    //	System.out.println( " >>>>>> execReflex for " + cPrototype.getName() );
						cPrototype.cProductRessourceTab.addTo(	cGamer.getRessources(), pTimeDiff );
				}
				if( cArsenal != null )
						cArsenal.setTime( pTimeDiff ); // pour la frequence de tir

				return true;
		}
		//--------------------------------------
		String getStringCurrentMissionInfo(){
				
				if( cVectMission.isEmpty() )
						return "No action to do";
				
				return ""+cVectMission.size() 
						+ " " + cVectMission.get(0).getStringInfo();
		}		
		//--------------------------------------
		//--------------------------------------
		//--------------------------------------

		public void setAttackBy( Entity pAggressor, Weapon pWeapon, Util.Orientation pOrient, double pDistance ) {
			
				// Faire les calcul en fonction des defenses !
				// En cas de coup au but faire les animations pour l'arme
				// Faire anim pour la cible
				// En cas de destruction faire ce qu'il faut !				

				// POUR LE MOMENT ON FAIT SIMPLE !!!
				double lDamage = pWeapon.cProto.cDamage; 
				
				cLife -= lDamage;

				if( cLife <= 0 ){
						kill();
				}
						
		}
		//------------------------------------------------
		void kill(){
				cLife = 0;
				// 
				setMission( new ActionEntityDeath( this, 500 ) );
		}
}
//***********************************
