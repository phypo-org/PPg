package com.phipo.GLib;

import org.w3c.dom.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.lang.*;

//*************************************************
public class RessourceTable{
		
		int    [] cRessourceIndex = null;
		double [] cRessourceVal  = null;

		//---------------------------------------
		RessourceTable(){
		}		
		//---------------------------------------
		RessourceTable( RessourceTable pInit){

				if( pInit.cRessourceIndex != null ) {
						
						cRessourceIndex = new int[ pInit.cRessourceIndex.length ];
						cRessourceVal   = new double[ pInit.cRessourceIndex.length ];

						for( int i=0; i< pInit.cRessourceIndex.length; i++){
								cRessourceIndex[i] = pInit.cRessourceIndex[i];
								cRessourceVal[i] = 0;
						}
				}		
		}
		//---------------------------------------
		boolean isVoid() {
				if( cRessourceIndex == null || cRessourceIndex.length == 0)
						return true;
				return false;
		}
		//---------------------------------------
		void copyFrom( RessourceTable pSrc){

				if( pSrc.cRessourceIndex != null ) {
						
						cRessourceIndex = new int[ pSrc.cRessourceIndex.length ];
						cRessourceVal   = new double[ pSrc.cRessourceIndex.length ];

						for( int i=0; i< pSrc.cRessourceIndex.length; i++){
								cRessourceIndex[i] = pSrc.cRessourceIndex[i];
								cRessourceVal[i] = pSrc.cRessourceVal[i];
						}
				}
				else {
						// pas vraiment utile 
						if( cRessourceIndex != null ){
								cRessourceIndex = null;
								cRessourceVal = null;
						}
				}
		}
		//---------------------------------------
		int getIndex( int pRessource ){

				if( cRessourceIndex == null )
						return -1;
				
				for( int i=0; i<cRessourceIndex.length;i++)
						if( pRessource == cRessourceIndex[i] )
								return i;
				return -1;
		}
		
		//---------------------------------------
		public double getRessourceVal( int pRessource ){

				if( cRessourceIndex == null )
						return 0;

				for( int i=0; i<cRessourceIndex.length;i++)
						if( pRessource == cRessourceIndex[i] )
								return cRessourceVal[i];
				return 0;
		}
		//---------------------------------------
		public boolean exist( int pRessource ){

				if( cRessourceIndex == null )
						return false;
				
				for( int i=0; i<cRessourceIndex.length;i++)
						if( pRessource == cRessourceIndex[i] )
								return true;
				return false;
		}
		//---------------------------------------
		public void addNewRessource( int pIndex, double pVal ){
				
				int lNb = 0;
				
				if( cRessourceIndex != null )
						lNb = cRessourceIndex.length;
				
				int    [] lTmpIndex = new int[lNb+1];
				double [] lTmpVal = new double[lNb+1];
				
				for( int i=0; i<lNb; i++){
						lTmpIndex[i] = cRessourceIndex[i];
						lTmpVal[i]   = cRessourceVal[i];
				}
				
				lTmpIndex[lNb] = pIndex;
				lTmpVal[lNb]   = pVal;
				
				cRessourceIndex = lTmpIndex;
				cRessourceVal   = lTmpVal;

		}		
		//---------------------------------------
		boolean addRessourceToTable( int pRessource, RessourceTable pToAdd ){
				
				if( pToAdd.cRessourceIndex == null )
						return true;

				if( cRessourceIndex == null )
						return false;

				int lIndexLocal = getIndex( pRessource );
				if( lIndexLocal == -1 )
						return false;
				
				int lIndexToAdd = pToAdd.getIndex( pRessource );
				if( lIndexToAdd == -1 )
						return false;
				
				cRessourceVal[lIndexLocal] += pToAdd.cRessourceVal[lIndexToAdd];
				return true;
		}
		//---------------------------------------
		boolean subRessourceToTable( int pRessource, RessourceTable pToAdd ){

				if( pToAdd.cRessourceIndex == null )
						return true;

				if( cRessourceIndex == null )
						return false;

				int lIndexLocal = getIndex( pRessource );
				if( lIndexLocal == -1 )
						return false;
				
				int lIndexToAdd = pToAdd.getIndex( pRessource );
				if( lIndexToAdd == -1 )
						return false;

				if( cRessourceVal[lIndexLocal]  < pToAdd.cRessourceVal[lIndexToAdd])
						return false;

				cRessourceVal[lIndexLocal] -= pToAdd.cRessourceVal[lIndexToAdd];
				return true;
		}
		//---------------------------------------
		boolean addTable( RessourceTable pToAdd ){

				if( pToAdd.cRessourceIndex == null )
						return true;
				
				if( cRessourceIndex == null )
						return false;

				for( int i=0; i< pToAdd.cRessourceIndex.length; i++ ){
						int lRessource =  pToAdd.cRessourceIndex[i];
						
						int lIndexLocal = getIndex( lRessource );
						if( lIndexLocal == -1 )
								continue;
						
						cRessourceVal[lIndexLocal] += pToAdd.cRessourceVal[i];
				}
				return true;
		}
		//---------------------------------------
		void addTo( double [] pDest,  double pTimeDiff){
				
				if( cRessourceIndex == null )
						return ;

				for( int i=0; i< cRessourceIndex.length; i++ ){
						
						int lRessource =  cRessourceIndex[i];
						
						pDest[lRessource] += cRessourceVal[i]*pTimeDiff;
				}
		}
		//---------------------------------------
		void addTo( double [] pDest){
				
				if( cRessourceIndex == null )
						return ;
				
				for( int i=0; i< cRessourceIndex.length; i++ ){
						
						pDest[cRessourceIndex[i]] += cRessourceVal[i];
				}
		}
		//---------------------------------------
		boolean testSubTo( double [] pDest ){
				
				if( cRessourceIndex == null )
						return true;
				
				for( int i=0; i< cRessourceIndex.length; i++ ){
						
						if( 	pDest[cRessourceIndex[i]] < cRessourceVal[i] ){

								return false;
						}
				}
				return true;
		}
		//---------------------------------------
		boolean subTo( double [] pDest ){

				if( testSubTo( pDest ) == false )
						return false;

				if( cRessourceIndex == null )
						return true;
				
				for( int i=0; i< cRessourceIndex.length; i++ ){
						
						pDest[cRessourceIndex[i]] -= cRessourceVal[i];
				}
				return true;
		}
		//---------------------------------------
		boolean testSubTable( RessourceTable pToAdd ){

				if( pToAdd.cRessourceIndex == null )
						return true;
				
				if( cRessourceIndex == null )
						return false;

				for( int i=0; i< pToAdd.cRessourceIndex.length; i++ ){
						int lRessource =  pToAdd.cRessourceIndex[i];
						
						int lIndexLocal = getIndex( lRessource );
						if( lIndexLocal == -1 )
								return false;
						
						if( cRessourceVal[lIndexLocal] < pToAdd.cRessourceVal[i])
								return false;
				}
				return true;
		}
		//---------------------------------------
		boolean subTable( RessourceTable pToAdd ){

				if( pToAdd.cRessourceIndex == null )
						return true;
				
				if( testSubTable( pToAdd) == false )
						return false;
				
				for( int i=0; i< pToAdd.cRessourceIndex.length; i++ ){
						int lRessource =  pToAdd.cRessourceIndex[i];
						
						int lIndexLocal = getIndex( lRessource );
						
						cRessourceVal[lIndexLocal] -= pToAdd.cRessourceVal[i];
				}
				return true;
		}
}
//*************************************************
