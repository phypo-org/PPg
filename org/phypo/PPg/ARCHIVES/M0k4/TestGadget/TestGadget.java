package com.phipo.PPg.M0k4.TestGadget;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import java.text.*;
import java.io.File;
import java.util.*;

import java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.Ellipse2D; 
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.awt.font.FontRenderContext;


import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


import java.io.IOException;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;

import com.phipo.PPg.PPgUtils.*;
import com.phipo.PPg.PPgSound.*;
import com.phipo.PPg.PPgWin.*;


import com.phipo.PPg.M0k4.*;



//*************************************************

public class TestGadget extends M0k4_Gadget{


		PPgField cFieldName;

		
		public TestGadget(){
				super();				
		}

		//------------------------------------------------

		public void gadgetInitialize(){
				setLayout( new FlowLayout( ));

				cFieldName =  new PPgField( "TestGadget :" + gadgetGetName()+ ':' + gadgetGetInstanceName(),  PPgField.HORIZONTAL);
				
				getContentPane().add( cFieldName );


				pack();
				setAlwaysOnTop(true);

				setVisible(true);
				TestClass lTestClass = new TestClass( "Hello"+ gadgetGetName()+ ':' + gadgetGetInstanceName() );
		}
		//------------------------------------------------
		public void  gadgetGetMenu( Menu lMenu ) { 
				MenuItem lItem = new MenuItem("Test menu");
				lItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
											JOptionPane.showMessageDialog( null,
																										 getName(),
																										 "Hello",
																										 JOptionPane.PLAIN_MESSAGE);
								}
						});	
				lMenu.add( lItem );
		}
		//------------------------------------------------
	

		public void    gadgetOpenDialogOption() {;};		
		public void    gadgetDestroy(){;}


}
//*************************************************
