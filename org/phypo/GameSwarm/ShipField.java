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

public class ShipField extends  Ship{

		
		static public double  sFieldPowerMax = 90;

		double cFieldPowerMax = 10;

		public void setFieldPowerMax( double pMax ) {
				cFieldPowerMax = pMax;
		}
		double cFieldPowerFill = 0.2;

		public void setFieldPowerFill( double pFill ){
				cFieldPowerFill = pFill;
		}

		//------------------------------------------------
		public ShipField( PPgImg pImg, double pX, double pY ){
				super( pImg, pX, pY, ShipType.FieldShip.cColor );
	
				cShipType = ShipType.FieldShip;	
				setBoundingSphere( 9 );
		}
		//------------------------------------------------
		public double fleetCallGetPowerMax(){

				return cFieldPowerMax;
		}
		//------------------------------------------------
		public double fleetCallGetPower(double pTimeDelta){

				return cFieldPowerMax*cFieldPowerFill*pTimeDelta;
		}

		//------------------------------------------------

			public void worldCallDraw( Graphics2D pG ){

					super.worldCallDraw( pG );
					/*
					Point2D.Double lPos = getLocation();
					

					int lSize = (int)getBoundingSphere()*3;
					
					double lSz =(( sFieldStroke.length-1)*((Fleet)cMySwarm).getFieldPower())/((Fleet)cMySwarm).getFieldPowerMax();

					pG.setColor( sFieldColor[ World.sGlobalRandom.nextIntPositif(sFieldColor.length-1) ] );
					pG.setStroke( sFieldStroke[(int)lSz] );
					
					pG.drawOval( (int)(lPos.getX()-lSize), 
											 (int)(lPos.getY()-lSize),
											 lSize*2,lSize*2);
					pG.setStroke( sFieldStroke[0] );
					*/
							/*							
							pG.setColor( sColorField2 );
							pG.drawOval( (int)(lPos.getX()-lSize), 
													 (int)(lPos.getY()-lSize),
													 lSize*2,lSize*2);
							
							lSize--;
							
							pG.setColor( sColorField3 );
							pG.drawOval( (int)(lPos.getX()-lSize), 
													 (int)(lPos.getY()-lSize),
													 lSize*2,lSize*2);
							*/
										 //						 lSize, lSize );
		}
};

//*************************************************
