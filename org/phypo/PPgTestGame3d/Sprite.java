package org.phypo.PPgTestGame3d;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;


import org.phypo.PPgGame3d.*;
//*************************************************
public class Sprite extends ActorMobil{

		//------------------------------------------------

		float cFieldPower = 0f;
		public float  getFieldPower() { return cFieldPower; }

		static public float  sFieldPowerMax = 10f;
		float cFieldPowerMax = 0;

		public void setFieldPowerMax( float pMax ) {
				cFieldPowerMax = pMax;
		}
		float cFieldPowerFill = 0f;

		public void setFieldPowerFill( float pFill ){
				cFieldPowerFill = pFill;
		}





		float cDamage = 0.5f;
		public float  getDamage() { return cDamage;}
		//		public float  getDamage()     { return getBoundingSphere()/10f+1f;}

		float cResistance = 0.5f;
		public float  getResistance() { return cResistance ;}

		//------------------------------------------------
		
		protected Sprite( Float3   pLocation,
											EnumFaction pFaction, 
											NodeBase pNode3d ) {
				super( pLocation, pFaction, pNode3d );
		}
		//------------------------------------------------
		protected Sprite( 	EnumFaction pFaction ) {
				super(pFaction);
		}
		//------------------------------------------------
		protected Sprite( Float3   pLocation,
											EnumFaction pFaction, 
											NodeBase pNode3d,
											
											float   pResistance,	
											float   pDamage,
											float   pFieldPower ) {
				super( pLocation, pFaction, pNode3d );
				cResistance = pResistance;
				cDamage     = pDamage;
				cFieldPower = pFieldPower;		
		}
		//------------------------------------------------
		protected Sprite( EnumFaction pFaction, 
											
											float   pResistance,	
											float   pDamage,
											float   pFieldPower ) {
				super(  pFaction );
				cResistance = pResistance;
				cDamage     = pDamage;
				cFieldPower = pFieldPower;		
		}
		//------------------------------------------------
		public void setSpriteProperties( 	float   pResistance,	
																float   pDamage,
																float   pFieldPower ) {	
			cResistance = pResistance;
			cDamage     = pDamage;
			cFieldPower = pFieldPower;		
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		@Override public void worldCallAct( float pTimeDelta ) {

				cFieldPower    += cFieldPowerFill*pTimeDelta;
				if( cFieldPower > cFieldPowerMax )
						cFieldPower = cFieldPowerMax;

				 super.worldCallAct( pTimeDelta );
		}
		//------------------------------------------------
		public float	callApplyDamage( float pDamage){

				float lResul = cResistance- pDamage;

				if( lResul < 0 ) {						
						cResistance = 0;
						return -lResul;
				}
				cResistance = lResul;
				return 0;
		}
		//------------------------------------------------
		public float	callApplyFieldDamage( float pDamage){

				float lResul = cFieldPower- pDamage;

				if( lResul < 0 ) {						
						 cFieldPower= 0;
						return -lResul;
				}
				 cFieldPower = lResul;
				return 0;
		}
		//------------------------------------------------
		//  Il y a eu collision du avec qq chose
		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

				//				if( pFirst ){
				//						System.out.println( "XXXX Collision" );
				//			}
				//		if( cResistance <= 0 ) return ; //Pour eviter d'appeler plusieurs fois callDestruction
				// si le sprite reste quelque temps

				float lDamage = ((Sprite)pActor).getDamage();

			
				
				if( lDamage >0 ) {

						lDamage = callApplyFieldDamage( lDamage);
						if( lDamage > 0 )
								lDamage = callApplyDamage( lDamage );

						if( lDamage > 0 ) {
								callDestruction();
						}
				}							 																			 		 				
		}

		//------------------------------------------------
		public void callDestruction(){	

				setDeleted();
		}				
 		//------------------------------------------------
		/*	@Override
		public void worldCallDraw( Engine  pGraf ) {
				
				super.worldCallDraw( pGraf );
			
				}	*/
    
 };

//*************************************************


