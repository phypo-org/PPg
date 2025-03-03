package org.phypo.GameSwarm;

import java.util.*;
import java.awt.*;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgSFX.*;

//=====================================
public enum ShipType{

    		VoidShip( Color.white ),
        
    		FieldShip( Color.blue ),        
				LaserShip( Color.red ),     
				MissileShip( Color.orange ),
				SupporShip( Color.green ),
				LeaderShip( Color.yellow );		

		public final Color cColor;
		
		ShipType( Color pColor ) { cColor = pColor; }
};
//=====================================
