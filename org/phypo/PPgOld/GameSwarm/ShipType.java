package org.phypo.PPg.PPgSwarm;

import java.util.*;
import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;

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
