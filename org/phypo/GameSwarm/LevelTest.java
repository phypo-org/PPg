package org.phypo.GameSwarm;



import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPgGame.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;


//*************************************************

public class LevelTest extends LevelBase{

		int cNbBoid=1;
		int cNbBoidRand = 0;


		public LevelTest( String pLevelName, EnumFaction pFaction, double pTimeOfLife, int pNbBoid, int pNbBoidRand, double pIntervalle ){

				super( pLevelName, pFaction, pTimeOfLife, pIntervalle  );

				cNbBoid     = pNbBoid;
				cNbBoidRand = pNbBoidRand;
		}
		//----------------------------------------------------------
		protected void callCreateSprites(  double pTimeDelta ){


				//			Fleet lFleet =	null;
				ActorMobil lActor = null;

				int lAlea =  World.sGlobalRandom.nextIntPositif( 100 );


				if( lAlea > 80 )
						lActor = FactoryActor.MakeEnemyFleet( getFaction(), cNbBoid, cNbBoidRand );
				else 
						if( lAlea > 50 ) 								
						lActor = FactoryActor.MakeShip1( getFaction() );
				 else  
						 if( lAlea > 40 )							
								 lActor	 = FactoryActor.MakeContainer();
				 else  
						 if( lAlea > 20 )
								 lActor	 = FactoryActor.MakeAsteroid11( getFaction() );
				 else  
						 if( lAlea > 10 )
								 lActor	 = FactoryActor.MakeAsteroid12( getFaction() );
				 else  
						 if( lAlea > 0 )
								 lActor	 = FactoryActor.MakeAsteroid2( getFaction() );
	
				if( lActor != null )
						cLastTime = World.Get().getGameTime();				
		}
};

//*************************************************

