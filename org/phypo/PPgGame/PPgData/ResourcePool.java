package org.phypo.PPgGame.PPgData;





import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

//****************************************************
// Permet de regrouper des Ressources et les manipuler
//***************************************************
public class ResourcePool {
		
		double [] cVal  = null;

		//---------------------------------------
		public ResourcePool( ){

				cVal = new double[ Resource.Length() ];
			
				for( int i=0; i< cVal.length; i++){
						cVal[i] = 0;
				}
		}		
		//---------------------------------------
		public int length() { return cVal.length ;}
		//---------------------------------------

		public boolean isVoid() {
				if( cVal == null || cVal.length == 0)
						return true;
				return false;
		}
		//---------------------------------------
		public void copyFrom( ResourcePool pSrc){

				if( pSrc.cVal != null ) {
						
						cVal   = new double[ pSrc.cVal.length ];
						
						for( int i=0; i< pSrc.cVal.length; i++){
								cVal[i] = pSrc.cVal[i];
						}
				}
				else {
						// pas vraiment utile 
						cVal = null;
				}
		}
		
		//---------------------------------------
		public double get( int pResource ){

				if( cVal == null )
						return 0;

				return cVal[pResource];
		}
		//---------------------------------------
		public boolean addResourceToTable( int pResource, ResourcePool pToAdd ){
				
				if( pToAdd.cVal== null )
						return true;

				if( cVal == null )
						return false;

				cVal[pResource] += pToAdd.cVal[pResource];
				return true;
		}
		//---------------------------------------
		public boolean subResourceToTable( int pResource, ResourcePool pToAdd ){

				if( pToAdd.cVal== null )
						return true;

				if( cVal == null )
						return false;

				cVal[pResource] -= pToAdd.cVal[pResource];
				return true;

		}
		//---------------------------------------
		public boolean addTable( ResourcePool pToAdd ){

				if( pToAdd.cVal== null )
						return true;

				if( cVal == null )
						return false;

				for( int i=0; i< pToAdd.cVal.length; i++ ){						
						cVal[i] += pToAdd.cVal[i];
				}
				return true;
		}
		//---------------------------------------
		public void addTo( double [] pDest,  double pTimeDiff){
				
				if( cVal== null )
						return ;

				for( int i=0; i< cVal.length; i++ ){					 						
						pDest[i] += cVal[i]*pTimeDiff;
				}
		}
		//---------------------------------------
		public void addTo( double [] pDest){
				
				if( cVal == null )
						return ;
				
				for( int i=0; i< cVal.length; i++ ){
						
						pDest[i] += cVal[i];
				}
		}
		//---------------------------------------
		boolean testSubTo( double [] pDest ){
				
				if( cVal == null )
						return false;
				
				for( int i=0; i< cVal.length; i++ ){
						
						if( 	pDest[i] < cVal[i] ){

								return false;
						}
				}
				return true;
		}
		//---------------------------------------
		boolean subTo( double [] pDest ){

				if( testSubTo( pDest ) == false )
						return false;
	
				for( int i=0; i< cVal.length; i++ ){
						
						pDest[i] -= cVal[i];
				}
				return true;
		}
		//---------------------------------------
		public boolean testSubTable( ResourcePool pToAdd ){

				if( pToAdd.cVal== null )
						return true;
				
				if( cVal== null )
						return false;

				for( int i=0; i< pToAdd.cVal.length; i++ ){
												
						if( cVal[i] < pToAdd.cVal[i])
								return false;
				}
				return true;
		}
		//---------------------------------------
		public boolean subTable( ResourcePool pToAdd ){

				if( pToAdd.cVal == null )
						return true;
				
				if( testSubTable( pToAdd) == false )
						return false;
				
				for( int i=0; i< pToAdd.cVal.length; i++ ){
						cVal[i] -= pToAdd.cVal[i];
				}
				return true;
		}
}
//*************************************************
