package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;

import javax.swing.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************


class Weapon {

		public double cTimeToWait=0;
		public double cMunition=-1;
		
		public PrototypeWeapon cProto;

		public Weapon( PrototypeWeapon pProto ){
				cProto = pProto;
		}
		//-----------------------------
		void setTime( double pTimediff) {

				if( cTimeToWait != 0 ){
						cTimeToWait -= pTimediff;
						if( cTimeToWait < 0 )
								cTimeToWait = 0;
				}
		}
		//-----------------------------
		public void attack(  Entity pUser, Util.Orientation pOrient, double pDistance, Entity pTarget ) {
				
				if( cProto.cRange >= pDistance
						|| cTimeToWait > 0 
						// || mauvais type d'arme pour la cible !!!!
						)
						return ;
		
				this.setAttackEffect( pUser, pOrient, pDistance ); // son, animation ... 
				pTarget.setAttackBy( pUser, this, pOrient, pDistance );				
		}

		//-----------------------------
		void setAttackEffect( Entity pUser, Util.Orientation pOrient, double pRange  ){
				// FAIRE QQE CHOSE !!!
		}
		//-----------------------------
		void setTouchEffect( Entity pUser, Util.Orientation pOrient, double pRange, Entity pTarget ){
				// FAIRE QQE CHOSE !!!
		}
}
//***********************************
