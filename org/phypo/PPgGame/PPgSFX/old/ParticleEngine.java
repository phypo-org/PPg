package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.geom.Point2D;

import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgGame.*;
 

//*************************************************

public class ParticleEngine extends ActorLocation{

		
		ArrayList<ParticleInterface> cArrayPart = null;
		ArrayList<ParticleInterface> cArrayPartNew = null;
		
		int cMaxNbParticle = 0;
		int cNbCreateAtOnce = 0;
		boolean cKillZero = false;

		ParticleFactoryInterface cParticleFactory = null;

		//------------------------------------------------
		public ParticleEngine( double pX, double pY, int pMaxNbParticle, int pNbCreateAtOnce, boolean pKillZero ){
				super( pX, pY,  EnumFaction.Neutral );
				cMaxNbParticle = pMaxNbParticle;
				cNbCreateAtOnce = pNbCreateAtOnce;

				cArrayPart    = new ArrayList<ParticleInterface>( cMaxNbParticle);
				cArrayPartNew = new ArrayList<ParticleInterface>( pNbCreateAtOnce);

		}
		//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom lRand, int lNum){
				return null;
		}
		//------------------------------------------------
		public void worldCallInit() {

				createParticles( cNbCreateAtOnce );
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
		public void worldCallAct( double pTimeDelta) {

				// On ajoute les nouvelles particules
				cArrayPart.addAll( cArrayPartNew );
				cArrayPartNew.clear();
				

				// On les toutes  evoluer 
				double lCurrentTime = World.Get().getGameTime();

				for( ParticleInterface lParticle : cArrayPart ) {
						if( lParticle.testTimeOfLife( lCurrentTime ) ){
								lParticle.setDeleted();								
						}
						else
								lParticle.callFactoryAct( pTimeDelta  );						
				}
				
				
				// On detruit celle qui sont morte
				ListIterator<ParticleInterface> lIter = cArrayPart.listIterator() ;
				while( lIter.hasNext() ){
						ParticleInterface lPart =lIter.next(); 
						if( lPart.isDeleted() ){
								lPart.callFactoryClose();
								lIter.remove();	
						}			 
				}

				// On en cree  de nouvelle s'il y a lieu
				if(  cMaxNbParticle > cArrayPart.size() ) {

						int lNbToCreate = Math.min( cMaxNbParticle - cArrayPart.size(), cNbCreateAtOnce ) ;
						createParticles(  lNbToCreate );								
				}
						

				// Si le Swarm est vide il est detruit
				if( cKillZero && cArrayPart.size() == 0){
						setDeleted(); 
				}				
		}
		//------------------------------------------------
		public void worldCallClose() {
		}
		//------------------------------------------------
		public void worldCallDraw( Graphics2D pGraph ) {

				for( ParticleInterface lPart : cArrayPart ) {
								lPart.callFactoryDraw( pGraph  );						
						}
		}	

};

//*************************************************



