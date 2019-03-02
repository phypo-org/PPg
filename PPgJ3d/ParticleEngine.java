package org.phypo.PPg.PPgJ3d;



import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
 

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.*;

//*************************************************

public class ParticleEngine extends Node3d{

		
		ArrayList<ParticleInterface> cArrayPart = null;
		ArrayList<ParticleInterface> cArrayPartNew = null;
		

		protected CompilObj    cSharedModel;
		protected Aspect3d     cSharedAspect = new Aspect3d( Color4.White, Aspect3d.DrawStyle.FILL);
		protected Transform3d  cSharedTransform = new Transform3d();


		int     cMaxNbParticle  = 0;
		int     cNbCreateAtOnce = 0;
		boolean cKillZero       = false;

		protected float cUnit = 1.0f;

		ParticleFactoryInterface cParticleFactory = null;

		//------------------------------------------------
		public ParticleEngine( CompilObj pObject, 
													 int pMaxNbParticle, 
													 int pNbCreateAtOnce, 
													 boolean pKillZero ){

				cSharedModel = pObject;

				cMaxNbParticle = pMaxNbParticle;
				cNbCreateAtOnce = pNbCreateAtOnce;

				cArrayPart    = new ArrayList<ParticleInterface>( cMaxNbParticle);
				cArrayPartNew = new ArrayList<ParticleInterface>( pNbCreateAtOnce);

		}
		//------------------------------------------------
		public ParticleEngine(  int pMaxNbParticle, int pNbCreateAtOnce, boolean pKillZero ){
				this( null, pMaxNbParticle, pNbCreateAtOnce, pKillZero );
		}
		//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom lRand, int lNum){
				return null;
		}
		//------------------------------------------------
		@Override
				public void beforeFirstTurn(){

				super.beforeFirstTurn();

				System.out.println( "******* ParticleEngine worldCallInit "  + cNbCreateAtOnce);

				createParticles( cNbCreateAtOnce );
				//				for( int i=0; i< 10; i++ ){
				//						updateTurn( i /10f );
				//	}
		}
		//------------------------------------------------
				void createParticles( int pNb ) {

				PPgRandom lRand = new PPgRandom();

				ParticleInterface lParticle = null;


				//				if( pNb > 0 )
				//						System.out.println( "----- createParticles :" + pNb );

				for( int i=0; i< pNb ; i++) {
						if( cParticleFactory != null )
								lParticle = cParticleFactory.newInstance( this, lRand, i );
						else
								lParticle = createParticle( lRand, i );

						if( lParticle != null )
								cArrayPartNew.add( lParticle );
				}
	
		}
		//------------------------------------------------
		@Override
				public void updateTurn( double pTimeDelta) {


				
				super.updateTurn( pTimeDelta );

				//				System.out.println( "******* ParticleEngine updateTurn **********  : " +  cMaxNbParticle + "   : " +  cArrayPart.size());

			// On ajoute les nouvelles particules
				cArrayPart.addAll( cArrayPartNew );
				cArrayPartNew.clear();
				

				// On les toutes  evoluer 
				double lCurrentTime = World3d.Get().getGameTime();

				for( ParticleInterface lParticle : cArrayPart ) {
						//						System.out.println( "******* ParticleEngine updateTurn lPart:" + (lParticle==null));		
						if( lParticle.testTimeOfLife( lCurrentTime ) ){

								//								System.out.println( "         lParticle.setDeleted()");
								lParticle.setDeleted(); 								
						}
						else
								lParticle.callFactoryAct( (float)pTimeDelta  );						
				}
				
				
				// On detruit celle qui sont morte
				ListIterator<ParticleInterface> lIter = cArrayPart.listIterator() ;				
				while( lIter.hasNext() ){
						ParticleInterface lPart =lIter.next(); 
						if( lPart.isDeleted() ){
								//								System.out.println( "         lParticle.isDeleted()");

								lPart.callFactoryClose();
								lIter.remove();	
						}			 
				}

				// On en cree  de nouvelle s'il y a lieu
				if(  cMaxNbParticle > cArrayPart.size() ) {
						//						System.out.println( "         Create " +  (cMaxNbParticle - cArrayPart.size()) );

						int lNbToCreate = Math.min( cMaxNbParticle - cArrayPart.size(), cNbCreateAtOnce ) ;
						createParticles(  lNbToCreate );								
				}
						
				// A FAIRE AUTO DESTRUCTION !!!!!
				//				if( cKillZero && cArrayPart.size() == 0){
				//						setDeleted(); 
				//				}				
		}
		//------------------------------------------------
		@Override
				 public void renderGL( GL2 pGl ){
				
				//		System.out.println( "******* ParticleEngine renderGL **********" );
				
				if( cMyTransf != null ){
						pGl.glPushMatrix();
						cMyTransf.renderGL(pGl);
				}

				for( ParticleInterface lPart : cArrayPart ) {
						/*						System.out.println( "******* ParticleEngine renderGL lPart:" + (lPart==null) 
																+ " cSharedModel:" +  ( cSharedModel== null)
																+ " cSharedTransform:" + (cSharedTransform==null)
																+ " cSharedAspect:" + (cSharedAspect==null) );
						*/
						lPart.callFactoryDraw( cSharedModel, 
																	 cSharedTransform,
																	 cSharedAspect,
																	 pGl );		
				}
				if( cMyTransf != null )
						pGl.glPopMatrix();					 						
		}	
};

//*************************************************



