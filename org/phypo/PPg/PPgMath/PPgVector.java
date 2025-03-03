
package org.phypo.PPg.PPgMath;


import java.awt.geom.Point2D;



/**
 * A 2D vector.
 *
 * Orignal version by @author Poul Henriksen
 * @version 2.0
 *
 * Modification by Philippe Poupon
 *
 * - Derive from Point2D.Double
 * - Use Radian for speed
 * - Add set function
 */

//*************************************************

public class PPgVector extends Point2D.Double {

		//------------------------------------------------
		//    double cDirection = 0; //in degrees
    public double cDirection = 0; // in radian
    public double cLength = 0;

    /**
     * Creates a vector with length=0
     */
    public PPgVector()    {
    }

    /**
     * Creates a vector with the given x and y-components.
     */
    public PPgVector(double pX, double pY) {
				super( pX, pY );
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
    }
    public PPgVector(Double pVal ) {
        super( pVal.getX(), pVal.getY());	//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
    }

    //----------------------------------
    public PPgVector(PPgVector pVect) {
        
        super( pVect.x, pVect.y );
        
        this.cDirection = pVect.cDirection;
        this.cLength = pVect.cLength;
    }

		//----------------------------------
   /**
     * Set the cDirection of this vector.
     */
    public PPgVector setDirection(double cDirection) {
        this.cDirection = cDirection;
				//        x = cLength * Math.cos(Math.toRadians(cDirection));
				//        y = cLength * Math.sin(Math.toRadians(cDirection));

				if( EcoMath.sEco ) {
						x = cLength * EcoMath.GetSin(cDirection);
						y = cLength * EcoMath.GetCos(cDirection);
				}
				else {
						x = cLength * Math.cos(cDirection);
						y = cLength * Math.sin(cDirection);
				}
        return this;
    }



 		//----------------------------------
   /**
     * Set the length of this vector.
     */
    public PPgVector setLength(double l)
    {
        this.cLength = l;

				if( EcoMath.sEco ) {
						x = cLength * EcoMath.GetCos(cDirection);
						y = cLength * EcoMath.GetSin(cDirection);
				}
				else {
						x = cLength * Math.cos(cDirection);
						y = cLength * Math.sin(cDirection);
				}
        return this;
    }

		//----------------------------------
    /**
     * Add other vector to this vector.
     */
    public PPgVector add(Double double1) {
        x += double1.x;
        y += double1.y;
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
        return this;
    }
		//----------------------------------
    /**
     * Add other vector to this vector.
     */
    	public PPgVector add(PPgVector other, double pFactor) {
        x += other.x*pFactor;
        y += other.y*pFactor;
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
        return this;
    }

	//----------------------------------
    /**
     * Subtract other vector to this vector.
     */
    public PPgVector subtract(PPgVector other) {
        x -= other.x;
        y -= other.y;
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
        return this;
    }

    public PPgVector subtract(Double location) {
    	// TODO Auto-generated method stub
        x -= location.x;
        y -= location.y;

        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
    	return null;
    }

    //----------------------------------
    /**
     * Get the x-component of this vector.
     */
    @Override
	public double getX() {
        return x;
    }

    /**
     * Get the y-component of this vector.
     */
    @Override
	public double getY() {
        return  y;
    }

	//----------------------------------
		public void set(double pX, double pY)
    {
        x = pX;
        y = pY;
				//       this.cDirection = Math.toDegrees(Math.atan2(y, x));
				this.cDirection = Math.atan2(y, x);
				this.cLength = Math.sqrt(x*x+y*y);
    }
	//----------------------------------
		public void recompute()
    {
				//       this.cDirection = Math.toDegrees(Math.atan2(y, x));
				this.cDirection = Math.atan2(y, x);
				this.cLength = Math.sqrt(x*x+y*y);
    }
	//----------------------------------
		public void set(PPgVector pVect)
    {
        x = pVect.x;
        y = pVect.y;
				this.cDirection = pVect.cDirection;
				this.cLength = pVect.cLength;
    }


	//----------------------------------
    /**
     * Set the x-component of this vector.
     */
    public void setX(double pX) {
        x = pX;
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
        this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
    }

	//----------------------------------
    /**
     * Set the y-component of this vector.
     */
    public void setY(double pY) {
        y = pY;
				//        this.cDirection = Math.toDegrees(Math.atan2(y, x));
				this.cDirection = Math.atan2(y, x);
        this.cLength = Math.sqrt(x*x+y*y);
    }

	//----------------------------------
    /**
     * Get the cDirection of this vector
     */
    public double getDirection() {
        return cDirection;
    }
	//----------------------------------
    /**
     * Get the cLength of this vector.
     */
    public double getLength() {
        return cLength;
    }

	//----------------------------------
    /**
     * Divide the length of thes vector with the given value.
     */
    public PPgVector divide(double v) {
        if(v != 0) {
            x = x / v;
            y = y / v;
            cLength = cLength / v;
        }
        return this;
    }

	//----------------------------------
    /**
     * Multiply the cLength of thes vector with the given value.
     */
    public PPgVector multiply(double v) {
        x = x * v;
        y = y * v;
        cLength = cLength * v;
        return this;
    }

	//----------------------------------
    /**
     * Create a copy of this vector.
     */
    public PPgVector copy() {
        PPgVector copy = new PPgVector();
        copy.x = x;
        copy.y = y;
        copy.cDirection = cDirection;
        copy.cLength = cLength;
        return copy;
    }

	//----------------------------------
    @Override
	public String toString() {
        return "" + x + "," + y + ":" + Math.toDegrees( cDirection ) +'d';
    }

	//----------------------------------
 	//----------------------------------
	//----------------------------------
  public  Point2D.Double transformDirection( Point2D.Double pPoint ){

			double lSin ;
			double lCos ;
			if( EcoMath.sEco ) {
					lSin = EcoMath.GetSin(cDirection);
					lCos = EcoMath.GetCos(cDirection);
			}
			else {
					lSin = Math.sin( cDirection );
					lCos = Math.cos( cDirection );
			}

				return new Point2D.Double( (x+pPoint.x * lCos +pPoint.y * lSin),
				   (int)(y+pPoint.x * lSin - pPoint.y * lCos));

	//				   (int)(y+pPoint.x * lSin[c_angle] + pPoint.y - lCos[c_angle]));
    }
	//----------------------------------
  public   void transformDirection( double []pVx, double[]pVy, int nb ){
			double lSin ;
			double lCos ;
			if( EcoMath.sEco ) {
					lSin = EcoMath.GetSin(cDirection);
					lCos = EcoMath.GetCos(cDirection);
			}
			else {
					lSin = Math.sin( cDirection );
					lCos = Math.cos( cDirection );
			}
				for( int i=0; i< nb; i++ ) {
						double lx = pVx[i];
						double ly = pVy[i];

						pVx[i] = (int)(  lx * lCos + ly * lSin);
						pVy[i] = (int)(  lx * lSin - ly * lCos);
				}
    }
	//----------------------------------
  public void transformDirection( int []pVx, int[]pVy, int nb ){

			double lSin ;
			double lCos ;

			if( EcoMath.sEco ) {
					lSin = EcoMath.GetSin(cDirection);
					lCos = EcoMath.GetCos(cDirection);
			}
			else {
					lSin = Math.sin( cDirection );
					lCos = Math.cos( cDirection );
			}

				for( int i=0; i< nb; i++ ) {
						double lx = pVx[i];
						double ly = pVy[i];

						pVx[i] = (int)( lx * lCos + ly * lSin);
						pVy[i] = (int)( lx * lSin - ly * lCos);
				}
    }
	//----------------------------------

}
