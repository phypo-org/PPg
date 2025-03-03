package org.phypo.PPgTestGame3d;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.nativewindow.util.Point;

import org.phypo.PPgGame3d.*;

//*************************************************

public class Explosion  extends  Sprite{
				
		
		static ModelBase  sBasicForm0 = null;
		static ModelBase  sBasicForm1 = null;
		static ModelBase  sBasicForm2 = null;
		static ModelBase  sBasicForm3 = null;
		static ModelBase  sBasicForm4 = null;
		static ModelBase  sBasicForm5 = null;

		static ModelBase GetForm( float pSize ){
				if( pSize <= 0.5f )	return  sBasicForm0;
				else if( pSize <= 1.5f )	return  sBasicForm1;
				else if( pSize <= 2.5f )	return  sBasicForm2;
				else if( pSize <= 5f )	return  sBasicForm3;
				// TROP COUTEUX ???? 
				//	else if( pSize <= 7f )	return  sBasicForm4;
				// return sBasicForm5; 
				return  sBasicForm4;
		}

		//------------------------------------------------
		Color4  cColorBegin;
		Color4  cColorEnd;
		Color4  cCurrentColor;
		Color4  cDeltaColor;


		public enum ExploMode{ Implosion, Explosion, ExploImplosion };

		ExploMode cExploMode;

		float   cRadius;
		float   cCurrentRadius;

		
		float cDuration;
		float cHalfLife;
		float cInc;

		public int getActorType() { return FactoryActor.ActorType.ActorExplosionType.cCode;}


		public boolean cUniformPlane = false;

		//------------------------------------------------
		protected Explosion(  EnumFaction pFaction, 
													NodeBase pNode3d,	
													Float3   pLocation,

													float pSize,
													float pDamage,

													Color4 pColorBegin,
													ExploMode pExploMode,
													float pDuration ) {

				super( pLocation, pFaction, pNode3d );

				cLocation.setZ(1 );  // Pour voir les explosion !

				cDuration = pDuration;
				cRadius = pSize;

				cDamage = pDamage;
				cColorBegin = pColorBegin;

				//==========================
				cExploMode = pExploMode;
				switch( cExploMode ){

				case Implosion:
						cHalfLife = cDuration;
						cCurrentRadius =cRadius;
						break;
				case Explosion:
						cHalfLife = cDuration;
						cCurrentRadius =0;
				break;

				case ExploImplosion:
						cCurrentRadius =0;
						cHalfLife = cDuration*0.5f;
						cRadius *= 2f;
				break;
				}
				//==========================

				//	cInc = 1.0f/cHalfLife;
				cInc = 1.0f/cDuration;

				setTimeOfLife( pDuration );


				cCurrentColor = new Color4( pColorBegin );

				cDeltaColor = new Color4( 0f, 0f, 0f, -pColorBegin.a() );

				//	System.out.println( "\t\tExplosion " + pSize + " " + pDuration + " cInc:"  + cInc );
		}
		//------------------------------------------------
		protected Explosion(  EnumFaction pFaction, 
													NodeBase pNode3d,	
													Float3   pLocation,

													float pSize,
													float pDamage,

													Color4 pColorBegin,
													Color4 pColorEnd, 
													ExploMode pExploMode,
													float pDuration ) {

				this( pFaction, pNode3d,  pLocation, pSize, pDamage, pColorBegin, pExploMode, pDuration );

				cDeltaColor = Color4.Diff( pColorEnd, pColorBegin );

				//		System.out.println( "\t\tExplosion2 " + pSize + " " + pDuration );
		}
		//------------------------------------------------		
		static public Explosion CreateExplosion( EnumFaction pFaction, 
																						 Float3 pLocation,
																						 float pSize,
																						 float pDamage,

																						 Color4 pColorBegin,
																						 Color4 pColorEnd, 
																						 ExploMode cExploMode,
																						 float pDuration ){


				//				System.out.println( "\tCreateExplosion\tExplosion " + pSize + " " + pDuration );
			if( sBasicForm0 == null){	

					System.out.println( "*** CREATE EXPLOSION ***");
			
					Aspect3d lAspect           = new Aspect3d( Color4.Red,	Aspect3d.DrawStyle.FILL );																						

					Primitiv3d.SubParamObject3d lParam = Primitiv3d.GetSubParamObject3d( 2, 1f,  true, Primitiv3d.SubNormalizeType.NORMALIZE);
					
					//	BUG AVEC LES CompilObj ???? les exlosions ne marche qu'une fois !!!

						sBasicForm0   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());
						//	lParam.cDepth++;
						sBasicForm1   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());	
						lParam.cDepth++;
						sBasicForm2   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());	
						//					lParam.cDepth++;
						sBasicForm3   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());	
						lParam.cDepth++;
						sBasicForm4   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());	
						//					lParam.cDepth++;
						//					sBasicForm5   = new CompilObj( Primitiv3d.Odron( lParam).getObject3d());	
					
						/*
						sBasicForm0   = Primitiv3d.Odron( lParam).getObject3d();	//Primitiv3d.Odron( lParam).getObject3d(); //
						sBasicForm1   = Primitiv3d.Odron( lParam).getObject3d();	
						lParam.cDepth++;
		//			lParam.cDepth++;
						sBasicForm2   = Primitiv3d.Odron( lParam).getObject3d();	
						sBasicForm3   = Primitiv3d.Odron( lParam).getObject3d();	
						lParam.cDepth++;
						sBasicForm4   = Primitiv3d.Odron( lParam).getObject3d();	
						*/
						//					sBasicForm5   = Primitiv3d.Odron( lParam).getObject3d();	
				}
					// ESSAYER AVEC AUTRE CHOSE QU UN ODRON !!!
				Aspect3d lAspect           = new Aspect3d( pColorBegin,	Aspect3d.DrawStyle.FILL );																						


				Leaf3d lLeaf        = new Leaf3d( GetForm( pSize ), null, lAspect );		// pas de ", true" ce sont deja des proto !			

				
				Explosion lExplosion;

				if( pColorEnd == null )
						lExplosion = new Explosion( pFaction, lLeaf, pLocation, pSize, pDamage,  pColorBegin,  cExploMode, pDuration);
				else {
						lExplosion = new Explosion( pFaction, lLeaf, pLocation, pSize, pDamage,  pColorBegin,  pColorEnd, cExploMode, pDuration);
				}
				lExplosion.cScale = new Float3( 1f,1f,5f);
				//				lExplosion.setSpin( Float3.GetRandom( World3d.sGlobalRandom, 900f) );

				lExplosion.setSpin( Float3.GetRandom( World3d.sGlobalRandom, 9000f) );

				return lExplosion;
		}
		//------------------------------------------------*
    public void worldCallAct( float pTimeDelta)  {				 


				double lCurrent = cInc*pTimeDelta;
		
				switch( cExploMode ){

				case Implosion:
						cCurrentRadius -= cRadius*lCurrent;
						break;
				case Explosion:
						cCurrentRadius += cRadius*lCurrent;
					break;

				case ExploImplosion:
						if( lCurrent > cHalfLife )
								cCurrentRadius -= cRadius*lCurrent;
						else
								cCurrentRadius += cRadius*lCurrent;
				break;
				}

				cCurrentColor.addDelta( cDeltaColor, (float)lCurrent );
				cCurrentColor.limitVal( 0f, 1f );


				getNode().getAspect().setColorMaterial( cCurrentColor );
				getNode().getAspect().setColorEmission( cCurrentColor );

				if( cUniformPlane ) 					
						setScale( new Float3( cCurrentRadius*(1+World3d.sGlobalRandom.nextFloatPositif(3)), cCurrentRadius, cCurrentRadius	));
				else
						setScale( new Float3(cCurrentRadius, cCurrentRadius, cCurrentRadius) );

				if( cDamage > 0 )	setBoundingSphere( cCurrentRadius );

				//	System.out.println( "\t\tExplosion worldCallAct " +  pTimeDelta + " cInc:" + cInc  +" current:" + lCurrent  );

				super.worldCallAct( pTimeDelta );
		}
}
//*************************************************
