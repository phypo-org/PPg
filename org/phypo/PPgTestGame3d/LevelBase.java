package org.phypo.PPgTestGame3d;

import org.phypo.PPgGame3d.*;

//*************************************************

public class  LevelBase extends ActorBase {

		protected String    cLevelName = " No Name" ;
		public    String    getLevelName() { return cLevelName; }

		protected float    cLastTime = 0;
		protected  float   cIntervalle=2;

		static public float sXAncor=0;
		static public float sUnit =1f;

		static float cSpeedDefilX = -15.0f;

		static int sMaxRelocation = 5;

		//------------------------------------------------
		public LevelBase( String pLevelName, EnumFaction pFaction, float pTimeOfLife, float pIntervalle  ) {

				super( pFaction );
				cLevelName = pLevelName;
				setTimeOfLife( pTimeOfLife );
				cIntervalle = pIntervalle;


				if( sXAncor == 0 ){
						sXAncor = (World3d.sTheWorld.getWidth()/2)*0.9f;
						sUnit =  World3d.sTheWorld.getWidth()/1000;
				}


				System.out.println( "LevelBase.LevelBase pTimeOfLife:" + pTimeOfLife + " pIntervalle:" +pIntervalle );
		}
    //-----------------------------------------------------------
		@Override public void worldCallClose() {
				
					System.out.println( "LevelBase.worldCallClose" );
			// Pour le niveau suivant

				World3d.Get().worldExternalCallEndLevel( this, false );	
		}	
		//----------------------------------------------------------
		@Override public void worldCallAct( float pTimeDelta) {
								
				//			System.out.println( "LevelBase.worldCallAct " + (World3d.Get().getGameTime() - cLastTime) + " " + cIntervalle );
				if( (World3d.Get().getGameTime() - cLastTime) <  cIntervalle+World3d.sGlobalRandom.nextDouble(cIntervalle*0.5))
						return;				
				
				
			callCreateSprites( pTimeDelta );
		}
		//----------------------------------------------------------

		protected void callCreateSprites( float pTimeDelta ) {
		}
};
//*************************************************



