package org.phypo.PPg.PPgJ3d;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;
import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.nativewindow.util.Point;

import org.phypo.PPg.PPgJ3d.*;

//*************************************************

public class ActorCursor  extends  ActorMobil{

		public Point cLastMousePos = new Point();
		public Point cOldMousePos = new Point();
		
		public float cSensibily=30f;

		//------------------------------------------------
		public ActorCursor( float pX, float pY, float pZ, 
												EnumFaction pFaction, 
												NodeBase pNode3d ) {

				super( pX, pY, pZ, pFaction, pNode3d );
		}
		//------------------------------------------
		public ActorCursor( Float3 pLoc, 
											 EnumFaction pFaction, 
											 NodeBase pNode3d ) {

				super( pLoc, pFaction, pNode3d );
		}
		//------------------------------------------------
		//------------------------------------------------
		@Override public void worldCallAct( float pTimeDelta ) {

				Kamera3d lKam = World3d.sTheWorld.getCurrentKamera();

				lKam.renderGL( Engine.sTheGl );

				Double3 lPosSprite = lKam.project( Engine.sTheGl, cLocation );	

				// On projette la souris dans l'espace objets
				Float3 lMouse = new Float3( cLastMousePos.getX(), cLastMousePos.getY(), lPosSprite.z());
				 Double3 lMouseObj = lKam.unproject(	Engine.sTheGl,  lMouse );	
				 
				 float lDx = (float)(lMouseObj.x() - cLocation.x()) ;
				 float lDy = (float)(-lMouseObj.y() - cLocation.y()) ;
		
				 //	 cSpeed.set( lDx*cSensibily, lDy*cSensibily, 0f );
				 //	 cLocation.set( (float)lMouseObj.x(),(float)lMouseObj.y(), (float) lMouseObj.z() );
				 cSpeed.set( lDx*cSensibily, lDy*cSensibily, 0f );

				super.worldCallAct( pTimeDelta );
		}

};
