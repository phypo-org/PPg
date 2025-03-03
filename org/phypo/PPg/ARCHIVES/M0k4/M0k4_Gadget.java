package com.phipo.PPg.M0k4;

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

import java.util.jar.*;


//**************************************************
// Using JDialog rather JFrame avoid icon in taskbar

abstract public class  M0k4_Gadget extends JDialog { 
 
		protected MyTrayIcon cMyOwner;

		public void gadgetSetOwner( MyTrayIcon pOwner ) { cMyOwner = pOwner;}
		
		protected boolean   cInteractif ;

		protected Manifest  cManifest;
		public void     gadgetSetManifest( Manifest  pManifest ) { cManifest = pManifest; }	 
		public Manifest gadgetGetManifest()                      { return cManifest; }
		public String   gadgetGetName()                    { 
				Attributes lMainAttrib = cManifest.getMainAttributes();
				return (String)lMainAttrib.get( Attributes.Name.IMPLEMENTATION_TITLE);
		}

		protected URL cMyURL;
		public void gadgetSetURL( URL pURL ){ cMyURL = pURL;}
		public URL  gadgetGetURL()          { return cMyURL;}

		protected String    cName = "no name";
		public void     gadgetSetInstanceName(String pName ) { cName=pName; }
		public String   gadgetGetInstanceName()              { return cName; }
		
		//------------------------------------------------
		public M0k4_Gadget(){
				
		}
		//------------------------------------------------

		public M0k4_Gadget( boolean pInteractif ){

				cInteractif = pInteractif;
				getContentPane().setLayout( new BorderLayout() );		

				if( cInteractif == false ){
						setUndecorated( true );
						setBackground(new Color(0,0,0,0));
				}

		}
		
		//------------------------------------------------
		public void     gadgetInitialize() {
				setVisible(true);
		}
		public void gadgetGetMenu( Menu lMenu) {;}
		abstract public void gadgetOpenDialogOption();	
		public void     gadgetDestroy() {;}
		public boolean  gadgetUniqueInstance() { return false; } 
}

//*************************************************



