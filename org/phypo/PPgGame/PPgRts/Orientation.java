package org.phypo.PPgGame.PPgRts;


import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;

//A METTRE AU POINT


//*************************************************
class Orientations {
		
		static double sTan30  = Math.tan( Math.PI/6 );
		static double sTan60  = Math.tan( 2*Math.PI/6 );
		static double sTan120 = Math.tan( 4*Math.PI/6 );
		static double sTan150 = Math.tan( 5*Math.PI/6 );
		
		static double sTan210 = Math.tan( 7*Math.PI/6 );
		static double sTan240 = Math.tan( 8*Math.PI/6 );
		static double sTan300 = Math.tan( 10*Math.PI/6 );
		static double sTan330 = Math.tan( 11*Math.PI/6 );
		
		
		enum Orientation {
				POS_NORTH("NORTH", 0, 0.0),
				POS_NE   ("NE",    1, 45.0),
				POS_EAST ("EAST",  2, 90.0),
				POS_SE   ("SE",    3, 135.0),
				POS_SOUTH("SOUTH", 4, 180.0),
				POS_SW   ("SW",    5, 225.0),
				POS_WEST ("WEST",  6, 270.0),
				POS_NW   ("NW",    7, 315.0),
				POS_NONE ("NONE",  8, 0);
				
				String cName=null;
				int    cCode=-1;
				double cAngle=0;
				
				public String getName() { return cName; }
				public int    getCode() { return cCode; }
				public double getAngle() { return cAngle; }
				
				Orientation( String pName, int pCode, double pAngle ) { 
						cName = pName;
						cCode = pCode;
						cAngle = pAngle;
				}								
		}
		
		//------------------------------------------------
		// On veut aller a une certaine distance d'une entity ( renvoit aussi l'orientation)
		
		static Orientation GetNewPos( Point2D.Double lPos,  Point2D.Double lTargetPos, double lDist, Point2D.Double lDestPos ){
				
				Orientation lDir = GetOrientation( lPos.getX(), lPos.getY(), lTargetPos.getX(), lTargetPos.getY() );
				
				double lX = lTargetPos.getX();
				double lY = lTargetPos.getY();
				
				switch( lDir ){
				case    POS_NORTH :
						lY -= lDist;
						break;
				case 		POS_NE :
						lX += lDist;
						lY -= lDist;
						break;
				case 		POS_EAST:
						lX += lDist;
						break;
				case 		POS_SE  :
						lX += lDist;
						lY -= lDist;
						break;
				case 		POS_SOUTH:
						lY += lDist;
						break;
				case 		POS_SW:   
						lX -= lDist;
						lY += lDist;
						break;
				case 		POS_WEST :
						lX -= lDist;
						break;
				case 		POS_NW:
						lX -= lDist;
						lY -= lDist;
						break;
				default:						
				}
				lDestPos.setLocation( lX, lY );
				
				return lDir;
		}
		//------------------------------------------------
		//  renvoit aussi l'orientation entre deux point
		static Orientation GetOrientation(  double ACenterX, double ACenterY, double BCenterX,  double BCenterY ){
				
				double lDistanceSqr = 0;
				double lDiffX;
				double lDiffY;
				Orientation lPosition = null;
				
				if( ACenterX < BCenterX ){
						// a gauche
						lDiffX = BCenterX - ACenterX;
						
						if( ACenterY < BCenterY ){						
								// en haut
								
								lDiffY = BCenterY - ACenterY;
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_WEST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_NORTH;
								else 
										lPosition = Orientation.POS_NW;								
						} else {
								// en bas
								lDiffY = ACenterY - BCenterY;					 			
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_WEST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_SOUTH;
								else 									
										lPosition = Orientation.POS_SW;								
						}
				}
				else {
						// a droite
						lDiffX = ACenterX - BCenterX;
						
						if( ACenterY < BCenterY ){						
								// en haut
								lDiffY = BCenterY - ACenterY;

								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_EAST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_NORTH;
								else 
										lPosition = Orientation.POS_NE;								
						} else {
								// en bas
								lDiffY = ACenterY - BCenterY;					 			
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_EAST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_SOUTH;
								else 
										lPosition = Orientation.POS_SE;								
						}
				}
				return lPosition;
		}
};
//*************************************************
