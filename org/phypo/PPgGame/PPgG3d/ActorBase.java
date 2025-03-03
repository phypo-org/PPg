package org.phypo.PPgGame.PPgG3d;



import java.util.*;

//*************************************************
// La classe de base de tout les elements actifs
//*************************************************

public class ActorBase{

		//=====================================
		ArrayList<Behavior> cListBehavior;

		public void addBehavior( Behavior pBehavior){
				if( cListBehavior == null)
						cListBehavior = new ArrayList();
				
				cListBehavior.add( pBehavior );
		}

		public void removeBehavior( Behavior pBehavior){
				if( cListBehavior == null)
						return;
				
				cListBehavior.remove( pBehavior );
		}

		//=====================================
		

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

		// A chaque cycle de vie de World
		public void worldCallAct( float pTimeDelta) {
				if( cListBehavior != null ){
						for( Behavior lBehavior : cListBehavior	)
								lBehavior.exec( this );
				}		   
		}
		public void worldCallClose() {;}                       	// A la destruction

		public void worldCallDraw( Engine pGraf ) {;}	    





};

//*************************************************
