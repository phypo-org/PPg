package org.phypo.GameSwarm;


import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgSFX.*;


//**************************************************

public class LevelAsteroid2 extends  LevelBase{

		int    cNbBoid=1;
		int    cNbBoidRand = 0;
		
		//----------------------------------------------------------
		public LevelAsteroid2( String pLevelName, EnumFaction pFaction, double pTimeOfLife, int pNbBoid, int NbBoidRand, double pIntervalle ){
						
				super( pLevelName, pFaction, pTimeOfLife, pIntervalle  );
				
				cNbBoid = pNbBoid;
				cNbBoidRand = NbBoidRand;
		}
		//----------------------------------------------------------
		protected void callCreateSprites(  double pTimeDelta ){
				
				ActorMobil lActor = null;
				int lAlea = World.sGlobalRandom.nextIntPositif( 100 );

				if( lAlea > 90 ) 
						lActor = FactoryActor.MakeShip1( getFaction() );
				else						
						if( lAlea > 80 )
							lActor	 = FactoryActor.MakeContainer();
						else  
								if( lAlea > 40 )
									lActor	 = FactoryActor.MakeAsteroid2( getFaction() );
				
				if( lActor != null )
						cLastTime = World.Get().getGameTime();				
			}
};

//*************************************************

