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
public class Turret extends Node3d{

		Weapon.FireMode    cFireMode  =  Weapon.FireMode.FireStopped;
		Weapon.WeaponType   cWeapon    = Weapon.WeaponType.NoWeapon ;

		RectFloat3   cFireBox;

		//	int cMaskObj;
		//		int cMakInteract;

		ActorLocation cMyActor;
		float cDelay;

		double cTimeLastFire;

		//------------------------------------------------
		public Turret(){
		}  		
		//------------------------------------------------
		public Turret( ActorLocation pMyActor, 
									 float pDelay,  
									 Weapon.FireMode   pFireMode, 
									 Weapon.WeaponType pWeapon, 
									 RectFloat3        pFireBox ){
				
				set( pMyActor, pDelay, pFireMode, pWeapon, pFireBox );
		}
		//------------------------------------------------
		public void set( ActorLocation pMyActor, 
										 float pDelay,  
										 Weapon.FireMode   pFireMode, 
										 Weapon.WeaponType pWeapon, 
										 RectFloat3        pFireBox ){
				
				cMyActor   = pMyActor;
				cDelay     = pDelay;
				cFireMode  = pFireMode;
				cWeapon    = pWeapon;

				cFireBox = pFireBox;		
				//		cMaskObj = pMaskObj;
				//		cMakInteract = pMakInteract;
				
		}
		//------------------------------------------------
		@Override	 public void updateTurn( double pTimeDiff ){

				//		System.out.println( "Turret.updatTurn" );
				super.updateTurn( pTimeDiff );

				if( cFireMode == Weapon.FireMode.FireStopped  )
						return;
				
				if(  (World3d.Get().getGameTime() - cTimeLastFire) < cDelay )
						return;
	
			SpritePilot lGamer =  HumanControl.GetSpritePilot();

				if( lGamer == null )
						return;


				Float3 lRelativPosGamer = Float3.Diff( lGamer.getLocation(), cMyActor.getLocation());

				//			System.out.println( "Turret.updatTurn " + cFireBox + " Pos:" + lRelativPosGamer );
 
				//  dans la zone de tir ?
				if( cFireBox.contains( lRelativPosGamer ) == false )
						return ;
		

				// Fire !
				cTimeLastFire = World3d.Get().getGameTime();


				Float3 lLocation = null;

				if( getTransf() == null || getTransf().getTranslat() == null )
						lLocation = new Float3( cMyActor.getLocation() );
				else {
						lLocation = getTransf().getTranslat();
						//						System.out.println( "Turret.updatTurn location:"  + lLocation + " + "  + cMyActor.getLocation()  );
	
						lLocation = Float3.Add( cMyActor.getLocation(), lLocation );
						//						System.out.println( "                                               location2 ="  + lLocation );
				}


				//			System.out.println( "Turret.updatTurn 5 Location:" + lLocation );

				Weapon lWeapon = Weapon.CreateWeapon( cWeapon, cMyActor.getFaction(), lLocation );

				if( cFireMode == Weapon.FireMode.FireDirect ) {
						
						// la direction du tir depend de la position du vaisseau
						lRelativPosGamer.normalize();
						lRelativPosGamer.inverse();
						
						
						Float3 lSpeed = new Float3( lRelativPosGamer.x()*cWeapon.cSpeed,
																				lRelativPosGamer.y()*cWeapon.cSpeed,
																				0f );//	lRelativPosGamer.z()*lSpeed);
						
						lWeapon.setSpeed( lSpeed );

						// ajout pour les missiles
						double lAngle = Calcul3d.GetAngle( lRelativPosGamer.x(), lRelativPosGamer.y() );
						lWeapon.setRotation( new Float3( 0, 0,  lAngle) );		 			
				}				
	
				World3d.Get().addActor( lWeapon );		
		}
}
//*************************************************

