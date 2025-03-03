package org.phypo.PPgGame.PPgGame;


import java.awt.Graphics2D;




import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;



import org.phypo.PPg.PPgMath.*;


//*************************************************
public class ActorMobil extends ActorLocation 
{
		public PPgVector cSpeed        = new PPgVector();
		public PPgVector cAcceleration = new PPgVector();
		
		double cRotation;

		public void setRotationDegree( double pRot ){
				cRotation = Math.toRadians( pRot );
		}
		public void setRotation( double pRot ){
				cRotation = pRot;
		}

    private double cMinSpeed = -1;
    private double cMaxSpeed = -1; 
    


		//------------------------------------------------
		public ActorMobil( double pX, double pY, EnumFaction pFaction) {
				super( pX, pY, pFaction );
		}


		//------------------------------------------------
    public void worldCallAct( double pTimeDelta ) {
					
					move(pTimeDelta);
    }        
   

		//------------------------------------------------
    public void move(double pTimeDelta) 
    {      
        cSpeed.add(cAcceleration, pTimeDelta);

        limitSpeed();

				
				cLocation.x += cSpeed.x*pTimeDelta;
				cLocation.y += cSpeed.y*pTimeDelta;
								

        setRotation( cSpeed.getDirection() );
    }
		//------------------------------------------------
    public void simpleMove(double pTimeDelta) 
    {      
        cSpeed.x += cAcceleration.x*pTimeDelta;
        cSpeed.y += cAcceleration.y*pTimeDelta;

       
				cLocation.x += cSpeed.x*pTimeDelta;
				cLocation.y += cSpeed.y*pTimeDelta;								
    }
		//------------------------------------------------
    public void verySimpleMove(double pTimeDelta) 
    {      
				cLocation.x += cSpeed.x*pTimeDelta;
				cLocation.y += cSpeed.y*pTimeDelta;								
    }
		//------------------------------------------------
    public void limitSpeed() {
        if(cMinSpeed >= 0 && cSpeed.getLength() < cMinSpeed) {
           cSpeed.setLength(cMinSpeed);
        }
        if(cMaxSpeed >= 0 && cSpeed.getLength() > cMaxSpeed) {
            cSpeed.setLength(cMaxSpeed);
        }
    }
 		//------------------------------------------------
  
    public PPgVector getAcceleration()
    {
        return cAcceleration.copy();
    }
		//------------------------------------------------

    public void setAccelaration(PPgVector newAcceleration) 
    {        
        cAcceleration.set(newAcceleration);
    }
		//------------------------------------------------

    public PPgVector getSpeed()
    {
        return cSpeed.copy();
    }
		//------------------------------------------------
    
    public void setSpeed(PPgVector pSpeed) 
    {
        cSpeed.set( pSpeed );
    } 
		//------------------------------------------------
    
    public void setSpeed( double pVx, double pVy) 
    {
        cSpeed.set( pVx, pVy );
    } 
		//------------------------------------------------
 
    public void setMinSpeed(double pSpeed) 
    {
        cMinSpeed = pSpeed;
    }
 		//------------------------------------------------
  
    public void setMaxSpeed(double pSpeed) 
    {
        cMaxSpeed = pSpeed;
    }

}

//*************************************************
