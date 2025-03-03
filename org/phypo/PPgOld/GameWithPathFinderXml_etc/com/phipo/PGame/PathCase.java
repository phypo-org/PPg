package com.phipo.GLib;


import java.io.*;
import java.util.*;
import java.lang.*;


//*************************************************
public class PathCase {

		
		// Tableau des entite presente dans la case !

		Entity cCurrentEntity;
		Entity cCurrentEntityZone;

		final public Entity getCurrentEntity() { return cCurrentEntity;}
		final public Entity getCurrentEntityZone() { return cCurrentEntityZone;}
		

		int cX, cY;
		double cMx;
		double cMy;

		int getX() { return cX;}
		int getY() { return cY;}

		double getMiddleMeterX() { return cMx; }
		double getMiddleMeterY() { return cMy; }

		PrototypeCase cPrototypeCase;

		public  PrototypeCase getProto() { return cPrototypeCase;}


		public	 double    cPathFinderMemo = 0;
		public   double cPathFinderDist = 0;
		public   double    cPathFinderMemo2 = 0;
		public String  getPathInfo() {
				if( cPathFinderMemo == 9999 )
						return ""+ cPathFinderMemo2 + "/" + (int)cPathFinderDist*10; 
				else
						return ""+ cPathFinderMemo + "/" +  (int)cPathFinderDist*10; 
		}
		// pppppppppppppppppppppppppppppppppppppppppppppppppppp

		//------------------
		public PathCase( int pX, int pY, PrototypeCase pProtoCase ) {
				
				cX = pX;
				cY = pY;

				cMx = (pX+0.5) * PathCarte.sSizeCase ;
				cMy = (pY+0.5) * PathCarte.sSizeCase ;

				cPrototypeCase = pProtoCase;
		}		
		//-----------------------------------------------------------
		public void setEntity( Entity pEntity){

				cCurrentEntity       = pEntity;
				cCurrentEntityZone   = pEntity;
		}
		//-----------------------------------------------------------
		public void setEntityZone( Entity pEntity){

				cCurrentEntityZone  = pEntity;
		}
		//--------------------------
		public Entity getEntityZone() { 
				
				if( cCurrentEntity != null )
						return cCurrentEntity;
				
				return cCurrentEntityZone;
		}
		//--------------------------
		public boolean isEmpty() {
				return ( cCurrentEntityZone == null && cCurrentEntity == null );
		}
		//--------------------------
		public boolean isEmpty( Entity pEntity) {
				return ( cCurrentEntityZone == null 
								 || cCurrentEntityZone == pEntity )
						&&( cCurrentEntity == null 
								|| cCurrentEntity == pEntity );
		}
};
//*************************************************
