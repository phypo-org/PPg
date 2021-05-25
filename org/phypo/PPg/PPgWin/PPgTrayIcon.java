package org.phypo.PPg.PPgWin;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.Ellipse2D; 
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;



//*************************************************

public abstract class PPgTrayIcon extends TrayIcon {
		
		public PPgTrayIcon sTheTrayIcon = null;
		public SystemTray cSysTray = SystemTray.getSystemTray();
		
		
		protected PopupMenu cPopup = new PopupMenu();
		 
		
		//------------------------------------------------
		public PPgTrayIcon( String pNameIcon, String pName ){
				//		super(  new ImageIcon(pNameIcon).getImage() );
				super( new ImageIcon( PPgTrayIcon.class.getResource(pNameIcon)).getImage(), pName  );

				//				Image image = Toolkit.getDefaultToolkit().getImage("cross2.gif");
				
				
				// Toolkit lToolKit = Toolkit.getDefaultToolkit();
				//  java.net.URL imgURL = TestSystemTrayIcon.class.getResource("cross2.gif");
				
				// Image image = kit.getImage(imgURL);
				
				
				setImageAutoSize( true);
				
				callInitMenu();
				
				
				try {
						cSysTray.add( this );
				} catch (AWTException e) {
						System.out.println("TrayIcon could not be added.");
						return;
				}				
				
				sTheTrayIcon = this;
		}
		//------------------------------------------------
		public void removeMe(){
				cSysTray.remove(sTheTrayIcon);				
		}
		//------------------------------------------------
		public boolean callInitMenu(){
				
				MenuItem lExitItem    = new MenuItem("Exit");
				cPopup.addSeparator();
				
				cPopup.add(lExitItem);				
				lExitItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										cSysTray.remove(sTheTrayIcon);
										System.exit(0);
								}
						});
				setPopupMenu(cPopup);
				return true;
		}
		//------------------------------------------------
		static public boolean IsSupported(){ return  SystemTray.isSupported(); }					
}
//*************************************************
