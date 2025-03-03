package org.phypo.PPg.PPgSwarm;


import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;



//*************************************************

public class  LevelBase extends ActorBase {

		protected String    cLevelName = " No Name" ;
		public    String    getLevelName() { return cLevelName; }

		protected double    cLastTime = 0;
		protected double    cIntervalle=2;


		//------------------------------------------------
		public LevelBase( String pLevelName, EnumFaction pFaction, double pTimeOfLife, double pIntervalle  ) {

				super( pFaction );
				cLevelName = pLevelName;
				setTimeOfLife( pTimeOfLife );
				cIntervalle = pIntervalle;
		}
    //-----------------------------------------------------------
		public void worldCallClose() {
				
				// Pour le niveau suivant
				World.Get().worldExternalCallEndLevel( this, false );	
		}	


		//----------------------------------------------------------
		public void worldCallAct( double pTimeDelta) {
								
				if( (World.Get().getGameTime() - cLastTime) <  cIntervalle+World.sGlobalRandom.nextDouble(cIntervalle*0.5))
						return;				
				
			callCreateSprites( pTimeDelta );
		}

		//----------------------------------------------------------

		protected void callCreateSprites(  double pTimeDelta ) {
		}
};
//*************************************************

