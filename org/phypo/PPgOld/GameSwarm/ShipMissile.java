package org.phypo.PPg.PPgSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;
import org.phypo.PPg.PPgImg.*;



//*************************************************

public class ShipMissile extends Ship {

		
		public static Color sColorMissile = new Color( 0.5f, 0.5f, 0f, 1f );

		//------------------------------------------------
		public ShipMissile( PPgImg pImg, double pX, double pY ){
				super( pImg, pX, pY, ShipType.MissileShip.cColor );
	
				cShipType = ShipType.MissileShip;	
		}
		//------------------------------------------------
		public void fleetCallFire( ShipType pTypeFire ){
				
				if( pTypeFire != cShipType )
						return;
				
				Weapon lMissile =  new Weapon( WeaponType.WeaponMissile, getFaction(), 5.0,
																			 cLocation.x, cLocation.y,
																			 sColorMissile );
		
				if( getFaction() == EnumFaction.Blue )
						lMissile.setSpeed( 300, 0 );
				else
						lMissile.setSpeed( -300, 0 );
	
				lMissile.setFaction( getFaction() );		
				lMissile.setTimeOfLife( 3 );
				lMissile.setBoundingSphere( 5	);
										
				
				World.Get().addActor(lMissile);						
		}

};

//*************************************************
