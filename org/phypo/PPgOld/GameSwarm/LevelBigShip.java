package org.phypo.PPg.PPgSwarm;



import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;

import org.phypo.PPg.PPgImg.*;

//*************************************************

public class LevelBigShip extends LevelBase{

		int cNbBoid=1;
		int cNbBoidRand = 0;


		public LevelBigShip( String pLevelName, EnumFaction pFaction, double pTimeOfLife, int pNbBoid, int pNbBoidRand, double pIntervalle ){

				super( pLevelName, pFaction, pTimeOfLife, pIntervalle  );

				cNbBoid     = pNbBoid;
				cNbBoidRand = pNbBoidRand;
		}
		//----------------------------------------------------------
		protected void callCreateSprites(  double pTimeDelta ){


				//			Fleet lFleet =	null;
				ActorMobil lActor = null;

				int lAlea =  World.sGlobalRandom.nextIntPositif( 100 );
				
				PPgImg lImg = null;

				if( lAlea > 90 )      	lImg =  Ressources.sShipBigImg2 ;
				else	if( lAlea > 80  )	lImg =  Ressources.sShipBigImg3;
				else	if( lAlea > 70 )	lImg =  Ressources.sShipBigImg4;
				else	if( lAlea > 60 )	lImg =  Ressources.sShipBigImg5;
				else	if( lAlea > 40 )	lImg =  Ressources.sShipMiddleImg1;
				else	if( lAlea > 20 )	lImg =  Ressources.sShipMiddleImg2;
				else	if( lAlea >=  0 )	lImg =  Ressources.sShipMiddleImg3;

				if( lImg != null )
						// lActor =  FactoryActor.MakeShip( getFaction(), lImg );
						lActor =  FactoryActor.MakeShip1( getFaction() );

				if( lActor != null )
						cLastTime = World.Get().getGameTime();				
		}
};

//*************************************************

