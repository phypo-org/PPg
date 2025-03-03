package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;

import javax.swing.*;


import java.awt.geom.*;
import java.awt.geom.Rectangle2D.*;

//***********************************


class Arsenal {

		Weapon cWeapon[] = null;
		

		public Arsenal( PrototypeWeapon pProto[] ){
				cWeapon = new Weapon[pProto.length];
				for( int i=0; i<pProto.length; i++ ){
						cWeapon[i] = new Weapon( pProto[i] );
				}												
		}

		//-------------------
		// C'est la premiere !!!
		public double getMainWeaponRange() {
				if( cWeapon == null ){
						return -1;
				}
				return cWeapon[0].cProto.cRange;
		}
		//-------------------
		void setTime( double pTimediff) {

				for( int i=0; i< cWeapon.length; i++ ){
						cWeapon[i].setTime( pTimediff );
				}
		}
		//-------------------
		public void attack( Entity pUser, Util.Orientation pOrient, double pDistance, Entity pTarget ) {
				
				for( int i=0; i< cWeapon.length; i++ ){
						cWeapon[i].attack( pUser, pOrient, pDistance, pTarget );
				}
		}
}


//***********************************
