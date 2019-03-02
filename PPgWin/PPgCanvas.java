package org.phypo.PPg.PPgWin;




import java.awt.*;
import java.beans.PropertyVetoException;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


//***********************************
public class PPgCanvas extends JPanel { //Canvas { //
		
		public	PPgCanvas() { 		
				super();

				setFocusable(true);
				requestFocus(); 
		}
		//-------------------------------------
		public void actualize(){
				repaint(); // new Rectangle( 0, 0, getWidth(), getHeight() ));
		}																								
}
//***********************************
