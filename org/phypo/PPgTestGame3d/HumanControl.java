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

public class HumanControl  extends DefaultUserControl3d {

		static HumanControl sTheHumanControl;

		PPgTestGame3d	 cGame;

		public SpritePilot  cActorGamer;
		public void setSpritePilot( SpritePilot pPilot ) { cActorGamer = pPilot;
 };
		//------------------------------------------------
		static public SpritePilot GetSpritePilot(){
				return sTheHumanControl.cActorGamer;
		}
		//------------------------------------------------

		public boolean cButtonPressed[] = new boolean[16];
		public double cTimeAutoFire[] = new double[16];

		//------------------------------------------------
		HumanControl( PPgTestGame3d  pGame ){

				sTheHumanControl = this;

				cGame = pGame;

		}
		//------------------------------------------------
		@Override 
				public void keyPressed(KeyEvent e) {
				
				if( cActorGamer == null )
						return ;

				super.keyPressed( e );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		@Override public void 	mouseDragged(MouseEvent e) {

				if( cActorGamer == null )
						return ;

				cActorGamer.cLastMousePos.setX( e.getX() );
				cActorGamer.cLastMousePos.setY( e.getY() );
						
		}

		//------------------------------------------------
		@Override public void 	mouseMoved(MouseEvent e) {
 
				//				System.out.println( "mouseMoved " );

				// stock mouse position in the gamer's sprite

				if( cActorGamer == null )
						return ;

				cActorGamer.cLastMousePos.setX( e.getX() );
				cActorGamer.cLastMousePos.setY( e.getY() );
						

				//	cActorGamer.setAccelaration( new Float3( lDx*20, lDy*20, 0 ));
				//	setAccelaration
		}		
		//------------------------------------------------
		@Override 	public void 	mouseClicked(MouseEvent e) {
				//		System.out.println( "HumanControl.mouseClicked" );
				if( cActorGamer == null )
						return ;
		}	
		//------------------------------------------------
		@Override public void 	mousePressed(MouseEvent e) {
				//				System.out.println( "HumanControl.mousePressed" );
				cButtonPressed[ e.getButton() ] = true;
				//	cTimeAutoFire[ e.getButton() ] = World.Get().getGameTime();
		}
		//------------------------------------------------
			@Override public void 	mouseReleased(MouseEvent e) {
					//				System.out.println( "HumanControl.mouseReleased" );

				cButtonPressed[ e.getButton() ] = false;
				cTimeAutoFire[ e.getButton() ] = 0;
		}		
};

//*************************************************
