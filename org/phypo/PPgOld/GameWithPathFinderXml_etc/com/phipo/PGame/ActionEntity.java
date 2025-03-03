package com.phipo.GLib;



import java.io.*;
import java.util.*;
import java.lang.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************
class ActionEntity{

		//------------------------------------------------
		public enum ActionType{
				WAIT("Wait",0),
				WALK("Walk",1),
				RUN("Run",2),
				REPAIR("Repair",3),
				GROWING("Grow",4),
				ATTACK("Attack",5),
				DYING("Dying",6),
				DEAD("Dead",7);
		
				
				String cName=null;
				int    cIndex;
				
				final public String getName()  { return cName; }
				final public int    getIndex() { return cIndex;}
		
				ActionType( String pName, int pIndex ){	cName = pName; cIndex=pIndex;}

				
				
				public static final HashMap<String,ActionType > sHashActionType = new HashMap<String,ActionType>();
				public static final ActionType [] sTab = new ActionType[Size()];

				static {
						for( ActionType  lAction: ActionType.values() ) {
								sHashActionType.put( lAction.cName, lAction );
								sTab[lAction.cIndex] = lAction;
						}
				}

				static public ActionType Get( int pIndex ) { return sTab[pIndex]; }
				static public ActionType FindByName( String pName ) { return sHashActionType.get( pName ); }
				static public int Size() { return 8;} // PAS TERRIBLE !!!

		};
		//------------------------------------------------
		Entity cEntity = null;

		ActionEntity  cSubAction = null; // Sous action eventuielle !

		boolean exec( double pTimediff ) { return false;}

		//------------------------------------------------
		ActionEntity( Entity pEntity ){
				cEntity = pEntity;
		}
		//------------------------------------------------		
		public void finish(){
		}
		//------------------------------
		public void addSubAction( ActionEntity pAction){

				cSubAction = pAction;
		}
		//--------------------------------------
		String getStringInfo(){
				return "Undefined action";
		}
}
//***********************************
