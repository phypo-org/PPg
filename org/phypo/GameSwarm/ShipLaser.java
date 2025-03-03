package org.phypo.GameSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgSFX.*;
import org.phypo.PPg.PPgImg.*;



//*************************************************

public class ShipLaser extends Ship {

		
		public static Color sColorLaser = new Color( 0f, 0.5f, 1f, 0.8f );

		//------------------------------------------------
		public ShipLaser( PPgImg pImg, double pX, double pY ){
				super( pImg, pX, pY, ShipType.LaserShip.cColor );

	
				cShipType = ShipType.LaserShip;	
		}
		//------------------------------------------------
		public void fleetCallFire( ShipType pTypeFire ){
				
				if( pTypeFire != cShipType )
						return;
				
				Weapon lMissile =  new Weapon( WeaponType.WeaponLaser, getFaction(), 1.0, 
																			 cLocation.x, cLocation.y, 
																			 sColorLaser );
		

				if( getFaction() == EnumFaction.Blue )
						lMissile.setSpeed( 500, 0 );
				else
						lMissile.setSpeed( -500, 0 );

				lMissile.setFaction( getFaction() );		
				lMissile.setTimeOfLife( 2.5 );
				lMissile.setBoundingSphere( 4	);
							
				World.Get().addActor(lMissile);						
		}


};

//*************************************************
