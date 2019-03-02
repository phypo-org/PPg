package org.phypo.PPg.PPgJ3d;



import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
//import java.awt.event.*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;



//*************************************************

abstract public class UserControl3d 
		implements  KeyListener, MouseListener {


		UserControl3d(){ };

		//------------------------------------------------
		abstract public void initFromKamera( Kamera3d pKam );


		//------------------------------------------------
    @Override  public void keyPressed(KeyEvent e) {System.out.println("keyPresses");}
		@Override  public void keyReleased(KeyEvent e){System.out.println("keyReleased");}
		
		@Override  public void 	mouseClicked(MouseEvent e) {;}	
		@Override public void 	mouseDragged(MouseEvent e) {;}		
		@Override public void 	mouseEntered(MouseEvent e) {;}		
		@Override public void 	mouseExited(MouseEvent e) {;}		

		@Override public void 	mousePressed(MouseEvent e) {;	}		
		//------------------------------------------------

		@Override public void 	mouseMoved(MouseEvent e) {
				System.out.println( "UserControl3d.mouseMoved" );
		}		
		//------------------------------------------------
		@Override public void 	mouseReleased(MouseEvent e) {;}		

		//------------------------------------------------
		@Override public void 	mouseWheelMoved(MouseEvent e)  {;}
};
