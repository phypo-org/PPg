package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

//*************************************************
public class PathCarte{

		
		int cW, cH;
		PathCase [] [] cVect = null;
		
		static double sSizeCase = 0; // taille d'une case de terrain en metre
		static double sSizeCaseInv = 0;

		
		//---------------------
		public PathCarte( DecorCarte pDecorCarte ) {	

				sSizeCaseInv = ((double)1)/sSizeCase;

				cW = (int)(pDecorCarte.getMeterWidth()/sSizeCase);
				cH = (int)(pDecorCarte.getMeterHeight()/sSizeCase);
				
				cVect = new PathCase[cW] [cH];	 
				
				for( int y=0; y<cH; y++){
						for( int x=0; x<cW; x++){
								DecorCase lDecorCase = pDecorCarte.getMeter( x*sSizeCase, y*sSizeCase );
								cVect[x][y] = new PathCase( x, y, lDecorCase.getProto() );
						}
				}									
		}
		//---------------------
		final int getWidth()  { return cW; }
		final int getHeight() { return cH; }
		//---------------------	 
		final PathCase get( int pX, int pY ){
				if( isOut( pX, pY ) )
						return null;

				return cVect[pX][pY]; 
		}
		//---------------------
		// recherche de la case avec la position en metres

		final PathCase getMeter( double pX, double pY ){

				int lX = (int)(pX*sSizeCaseInv);
				int lY = (int)(pY*sSizeCaseInv);
				
				if( isOut( lX, lY ) )
						return null;
				
				return cVect[lX][lY]; 
		}
		
		//---------------------
		final boolean isOut( int pX, int pY ) {		
				if( pX < 0 || pY < 0 || pX >= cW || pY >= cH )
						return true;
				return false;
		}			
		//-----------------------------------------------------------
		// On force l'ecriture dans toutes les cases

		void forceEntity( Entity pEntity, double pX, double pY ){
				
				PathCase lCase = getMeter( pX, pY );
				if( lCase == null )	return;


				lCase.setEntity( pEntity );
				pEntity.setCurrentCase( lCase	);
				

				int lRayonCase = pEntity.getPrototype().getRayonCase();


				//				System.out.println( ">>>>>>>>>>>>>>>>>>>> PathCarte::forceEntity " + pX + ":" + pY + " Rayon:" +  lRayonCase);

				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){
								PathCase lCurCase = get( x, y );

								if( lCurCase != null ){
										lCurCase.setEntityZone( pEntity );
								}										
						}							
		}
		//-----------------------------------------------------------
		// On n'ecrase pas les cases si elles appartiennent a qq d'autre

		void putEntity( Entity pEntity, double pX, double pY ){
				
				PathCase lCase = getMeter( pX, pY );
				if( lCase == null )	return;


				if( lCase.getCurrentEntity() == null ){
						lCase.setEntity( pEntity );
						pEntity.setCurrentCase( lCase	);
				}

				int lRayonCase = pEntity.getPrototype().getRayonCase();

				//				System.out.println( ">>>>>>>>>>>>>>>>>>>> PathCarte::putEntity " + pX + ":" + pY + " Rayon:" +  lRayonCase);

				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){
								PathCase lCurCase = get( x, y );
								
								if( lCurCase != null  && lCurCase.getCurrentEntityZone() == null ){
										lCurCase.setEntityZone( pEntity );
								}										
						}							
		}
		//-----------------------------------------------------------
		// On n'efface que les notres

		void clearEntity( Entity pEntity, double pX, double pY ){			 
				
				PathCase lCase = getMeter( pX, pY );
				if( lCase == null )	return;

				if( lCase.getCurrentEntity() == pEntity )
						lCase.setEntity( null );

				pEntity.setCurrentCase( null	);
				
				int lRayonCase = pEntity.getPrototype().getRayonCase();
				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){
								PathCase lCurCase = get( x, y );
								if( lCurCase != null && lCurCase.getCurrentEntityZone() == pEntity )
												lCurCase.setEntityZone( null );
						}										
		}							
		
		//-----------------------------------------------------------
		// On teste toutes les cases

		boolean testEntity( Entity pEntity, double pX, double pY  ){
				
				PathCase lCase = getMeter(  pX, pY );
				if( lCase == null || lCase.isEmpty( pEntity ) == false )
						return false;
				
				int lRayonCase = pEntity.getPrototype().getRayonCase();

				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){
								PathCase lCurCase = get( x, y );
								if( lCurCase != null && lCurCase.isEmpty( pEntity ) == false ){
										return false;
								}
						}				
				return true;
		}
		//-----------------------------------------------------------
		boolean testEntityAndTerrain( Entity pEntity, double pX, double pY  ){


				PathCase lCase = getMeter(  pX, pY );
				if( lCase == null || lCase.isEmpty( pEntity ) == false ) {						
						return false;
				}
				
				int lRayonCase = pEntity.getPrototype().getRayonCase();

				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){
								PathCase lCurCase = get( x, y );
								if( lCurCase != null ||  lCurCase.isEmpty( pEntity ) == false
										|| 	pEntity.getPrototype().getMoving(lCurCase ) == 0)
										return false;
						}
				return true;
		}
		//-----------------------------------------------------------
		boolean testEntityAndTerrain( PrototypeUnit pProtoUnit, double pX, double pY  ){


				PathCase lCase = getMeter(  pX, pY );
				if( lCase == null || lCase.isEmpty() == false ) {						
						return false;
				}
				
				int lRayonCase = pProtoUnit.getRayonCase();

				for( int x=(int)((lCase.getX()-lRayonCase)+1); x< lCase.getX()+lRayonCase; x++)
						for( int y=(int)((lCase.getY()-lRayonCase)+1); y< lCase.getY()+lRayonCase; y++){

								PathCase lCurCase = get( x, y );

								if( lCurCase == null ||  lCurCase.isEmpty( ) == false
									 	|| 	pProtoUnit.getMoving(lCurCase ) == 0)
										return false;
						}
				return true;
		}
		//-----------------------------------------------------------
		boolean setEntity( Entity pEntity, double pX, double pY  ){
				
				/////				if( testEntity( pEntity, pX, pY )	== false )
				////						return false;
				
				//				System.out.println( ">>>>>>>>>>>>>>>>>>>> PathCarte::setEntity " + pX + ":" + pY );

				forceEntity( pEntity, pX, pY );

				return true;
		}
		//-------------------------------------------------------------
		// On teste toutes les cases (Blocages possibles,
		// mais tient compte de la taille
		
		boolean moveEntityOld( Entity pEntity, double pX, double pY ) {		//	pEntity.getMX(), pEntity.getMY())	
				
				if( testEntity( pEntity, pX, pY )	== false )
						return false;
				
				clearEntity( pEntity, pEntity.getMX(), pEntity.getMY() );				
				forceEntity( pEntity, pX, pY );

				return true;
		}
		//-----------------------------------------------------------
		// On teste seulement la case centrale !

		boolean testMoveEntity( Entity pEntity, double pX, double pY  ){
				
				PathCase lCase = getMeter(  pX, pY );
				if( lCase == null || lCase.isEmpty( pEntity ) == false )
						return false;

				return true;				
		}
		//--------------------------
		boolean moveEntity( Entity pEntity, double pX, double pY ) {		//	pEntity.getMX(), pEntity.getMY())	
				
				if( testMoveEntity( pEntity, pX, pY )	== false )
						return false;
				
				clearEntity( pEntity, pEntity.getMX(), pEntity.getMY() );				
				putEntity( pEntity, pX, pY );

				return true;
		}
		//-----------------------------------------------------------
		Entity searchEntityAtPosition( Point2D.Double pPoint ){

				PathCase lCase = getMeter( pPoint.getX(), pPoint.getY());
				if( lCase != null &&  lCase.getCurrentEntityZone() != null ){
						if( lCase.getCurrentEntityZone().contains( pPoint ) ){
								return lCase.getCurrentEntityZone();
						}
				}
				return null;
		}
		//-----------------------------------------------------------
		ArrayList<Entity>  searchEntityInRect( Rectangle2D.Double pRect ){
				
				//				System.out.println( "searchEntityInRect " + pRect );

				ArrayList<Entity> lListEntity = null;
				
				int lMinX = (int)((pRect.getX()-0.9999)*sSizeCaseInv);
				int lMinY = (int)((pRect.getY()-0.9999)*sSizeCaseInv);

				int lMaxX = (int)((pRect.getX()+pRect.getWidth()  + 0.9999)*sSizeCaseInv);
				int lMaxY = (int)((pRect.getY()+pRect.getHeight() + 0.9999)*sSizeCaseInv);
				
				for( int x = lMinX; x < lMaxX; x++ )
						for( int y = lMinY; y < lMaxY; y++ ) {
								
								PathCase lCase = get( x, y );
								if( lCase != null  && lCase.getCurrentEntityZone() != null){

										Entity lEntity = lCase.getCurrentEntityZone();
										if( pRect.contains( lEntity ) ){
												
												if( lListEntity == null )
														lListEntity = new ArrayList<Entity>();
												
												lListEntity.add( lEntity );
										}
								}					
						}	
				return lListEntity;
		}
		//-----------------------------------------------------------
		//-----------------------------------------------------------
		//-----------------------------------------------------------

		final int CHEMIN = 999999;

		void razPath() {
				for( int x=0; x < cW; x++ )
						for( int y=0; y < cH; y++) {
								cVect[x][y].cPathFinderMemo = 0;
								cVect[x][y].cPathFinderMemo2 = 0;
								cVect[x][y].cPathFinderDist = 0;								
						}
		}
		//-----------------------------------------------------------
		//-----------------------------------------------------------
		//-----------------------------------------------------------

}
//*************************************************
