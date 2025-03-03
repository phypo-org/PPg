package org.phypo.PPg.PPgSwarm;



import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;
import java.util.*;

import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;


//*************************************************

public class FactoryLevel {

		public static FactoryLevel sTheFactoryLevel = null;

		int cDifficulty = 0;

		int cLevel = 0;
		LevelBase cCurrentLevel = null;

		//------------------------------------------------		
		FactoryLevel(  int pDifficulty ) {
				
				init( pDifficulty );

				sTheFactoryLevel = this;
		}
		//------------------------------------------------		
		public void init( int pDifficulty ) { 
				
				cDifficulty = pDifficulty;
		}
		//------------------------------------------------		
		LevelBase setLevel( int pLevel ) {

				switch( pLevel ) {
						
				case 0 :  cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 20, 3,  2+cDifficulty, 2.0  ); break;
				case 1 :  cCurrentLevel = new LevelAsteroid2(	"Asteroids 2", EnumFaction.Green, 20, 3,  2+cDifficulty, 1.5  ); break;
				case 2 :  cCurrentLevel = new LevelAsteroid(	"Asteroids 2", EnumFaction.Green, 20, 3,  2+cDifficulty, 2.0  ); break;
				case 3 :  cCurrentLevel = new LevelBigShip(	"Bigs ships", EnumFaction.Green, 120, 3,  5+cDifficulty, 4.0  ); break;
				default : cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 20, 3,  2+cDifficulty, 2.0  ); 
									 // Mettre un niveau de fin !!!
				}

				return cCurrentLevel;
		}
		//------------------------------------------------		
		public LevelBase  setBeginLevel(int pBegin) {

				cLevel = pBegin;

				return setLevel( cLevel );
		}
		//------------------------------------------------		
		public LevelBase getCurrentLevel() { 
				if( cCurrentLevel == null )
						return setLevel( cLevel );

				return cCurrentLevel;
		}
		//------------------------------------------------		
		public LevelBase  nextLevel()  { 
				cLevel += 1; 
				
				return setLevel(cLevel);				
		}
}

//*************************************************
