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

import com.phipo.PPg.PPgWin.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


import java.io.IOException;

import com.phipo.PPg.PPgUtils.*;
import com.phipo.PPg.PPgWin.*;


//*************************************************
public class M0k4  extends JDialog 

//		implements ActionListener, WindowListener, MouseListener
{

		static public M0k4 sTheM0k4= null;

		MyTrayIcon    cMyTrayIcon = null;

		static boolean sUseSecond = false;


		//------------------------------------------------
		
		public M0k4( ) {
				
				if( SystemTray.isSupported() ) {
						cMyTrayIcon = new MyTrayIcon( "/com/phipo/PPg/M0k4/M0k4_20x20.png", "M0k4"); 
				} else
						System.err.println("Tray icon not supported -> exit" );
				
		
				setVisible(false);	
		}		

		//------------------------------------------------		
    public static void main(String[] args) {
				
				
				new M0k4();
		}
}
