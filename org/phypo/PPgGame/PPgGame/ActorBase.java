package org.phypo.PPgGame.PPgGame;



import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;




//*************************************************
// La classe de base de tout les elements actifs
//*************************************************

public class ActorBase{


		boolean cIsDeleted;

		final public boolean isDeleted()  { return cIsDeleted;}
		final public void    setDeleted() { cIsDeleted = true;  }



		public String getName()   { return "";}
		int cActorType = 0;
		public int getActorType() { return cActorType;}
		public void setActorType( int pType ) { cActorType = pType; }
		int cActorId =0;
		public int getActorId()   { return cActorId;}
		public void setActorId( int pId ) {  cActorId = pId; }
		
		

		//--------- TimeOfLife -------------
		double  cTimeOfLife = -1;

		public void setTimeOfLife( double lTime) {
				if( lTime == -1)
						cTimeOfLife = -1;
				else
						cTimeOfLife = World.Get().getGameTime() + lTime; 
		}

		public boolean testTimeOfLife(double lTime) { 

				if(  cTimeOfLife == -1 )
						return false ;

				if( lTime > cTimeOfLife )
						return true;

				return false ;				
		}
		//--------- TimeOfLife -------------



		//--------------- Faction ------------------
		EnumFaction cFaction;
		public EnumFaction getFaction()                       { return cFaction; }
		public void        setFaction( EnumFaction pFaction ) { cFaction = pFaction; }

		public 	boolean isSameFaction( ActorBase pActorBase ){
				return cFaction == pActorBase.cFaction;
		}

		boolean filterFaction( byte lFilter ){
				return (lFilter & cFaction.getCode()) != 0;
		}
		//--------------- Faction ------------------



		

		public ActorBase( EnumFaction pFaction ){
				cFaction = pFaction;
				cIsDeleted = false;
		}




		// Fonctions a derivé

		public void worldCallInit() {;}                         // A la creation
		public void worldCallAct( double pTimeDelta) {;}		    // A chaque cycle de vie de World
		public void worldCallClose() {;}                       	// A la destruction
		public void worldCallDraw( Graphics2D pGraph ) {;}	    





};

//*************************************************
