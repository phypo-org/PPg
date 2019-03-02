package org.phypo.PPg.PPgJ3d;



import com.jogamp.opengl.*;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.awt.TextRenderer;

import com.jogamp.opengl.glu.gl2.*;

import com.jogamp.nativewindow.util.Point;

import java.nio.*;
import java.util.*;
import java.awt.Font;
import java.text.DecimalFormat;

import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgMath.*;

//*************************************************

public class PPgJ3dTest  extends DefaultUserControl3d {
		
		PPgJ3d cDemo;

		//------------------------------------------------
		PPgJ3dTest( PPgJ3d pDemo ){
				cDemo = pDemo;
		}
		//------------------------------------------------
		@Override 
				public void keyPressed(KeyEvent e) {
			       

				int lKeyCode    = e.getKeyCode();
				char lKey       = e.getKeyChar();
				boolean lAction = e.isActionKey();

				System.out.println( "PPgJ3dTest.key " + lKeyCode + " " + lKey + " "+ lAction );

		//		if( e.isModifierKey() ){
	//					return; 
	//			}

				if ( lKey == '0') {
						cDemo.DemoGlu( 2, 0.3f ); 
						cDemo.DemoGlu( 2, 0.5f ); 

				}	else      if ( lKey == '1' )  {
						cDemo.Demo( 1);
				} else      if ( lKey == '2' ) {
						cDemo.Demo( 2 );
						cDemo.cFlagDemo2 = true;
				} else     	if ( lKey == '3' ) {
						cDemo.Demo(3 );
				} else      if ( lKey == '4') {
						cDemo.Demo( 4);
				} else 			if ( lKey == '5') {
						cDemo.Demo( 5 );
				} else 			if ( lKey == '6') {
						cDemo.Demo( 6 );
				} else 			if ( lKey == '7') {
						cDemo.Demo( 7 );
				} else 			if ( lKey == '8') {
						cDemo.Demo( 8 );
				} else 			if ( lKey == '9') {
						cDemo.Demo( 9);
				} else 			if ( lKey == 'A' || lKey == 'a') {
						cDemo.Demo( 10 );
				} else 			if ( lKey == 'B' || lKey == 'b') {
						cDemo.Demo( 11);
				} else 			if ( lKey == 'E' || lKey == 'c') {
						cDemo.DemoEngine( 1 );
				} else 	    if ( lKey == 'X' || lKey == 'x') {	

					for( ActorBase lActor: cDemo.cDemoActor )
							lActor.setDeleted();
				}	else  if ( lKey == 'G' || lKey == 'g') {
						System.out.println( "Grid");
						cDemo.setViewGrid();
				}   if ( lKey == 'R' || lKey == 'r') {
						System.out.println( "Repere");
						cDemo.setViewRepere();
				} else super.keyPressed( e );
		}
}
//*************************************************
