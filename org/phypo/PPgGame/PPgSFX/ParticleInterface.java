package org.phypo.PPgGame.PPgSFX;


import java.awt.Graphics2D;

import java.awt.geom.Point2D;


import org.phypo.PPg.PPgMath.*;


//*************************************************

interface ParticleInterface {


		
		public enum EnumParticleForm {
				POINT,
						SQUARE,
						CIRCLE,
						CIRCLE_FILL
		};
		public void setParticleForm( ParticleInterface.EnumParticleForm pForm );

		public boolean isDeleted();
		public void    setDeleted();

		public void    setTimeOfLife( double lTime);
		public boolean testTimeOfLife(double lTime);
		
		public Point2D.Double getLocation();
    public void           setLocation( double x, double y );
		public void           setLocation( Point2D.Double pPos);
		public double         getX();
		public double         getY();

		public Point2D.Double getSpeed();
		public void           setSpeed( Point2D.Double pSpeed);
		public void           setSpeed(double pX, double pY);
		//		public void           setSpeedRand( Point2D.Double pSpeedRand);

		public Point2D.Double getAcceleration();
		public void           setAcceleration( Point2D.Double pAcc);
		public void           setAcceleration(double pX, double pY);
		//	public void           setAccelerationRand( Point2D.Double pAccRand);


		public double         getAngle();
		public void           setAngle( double pAngle, double pSpin, double pSpinAcc );


		public double         getSize();
		public void           setSize( double pAngle, double pGrow, double pGrowAcc );

		//		public void           setGrowRand( double pGrowRand);


		public float getRed();
		public float getGreen();
		public float getBlue();
		public float getAlpha();

		public void   setColor( float pR, float pG, float pB, float pA );
		public void   setColorFade( float pR, float pG, float pB, float pA );
		

		/*
		public double get();
		public void   set( double );


		public Point2D.Double get();
		public void  set( Point2D.Double );

		public Point2D.Double get();
		public void  set( Point2D.Double );
		*/


		public void callFactoryDraw( Graphics2D pG );
		public void callFactoryAct( double pTimeDelta );
		public void callFactoryClose();
};

//*************************************************
