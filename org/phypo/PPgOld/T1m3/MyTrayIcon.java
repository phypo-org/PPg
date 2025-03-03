package com.phipo.PPg.T1m3;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.text.*;
import javax.swing.ImageIcon;

import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.Ellipse2D; 
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.phipo.PPg.PPgWin.*;

//*************************************************

public class MyTrayIcon extends PPgTrayIcon {


		MyTrayIcon( String pNameIcon ){
				super( pNameIcon, "T1m3" );
		}

		//------------------------------------------------
		public boolean callInitMenu(){
				
				//Add components to popup menu
				
				// Create a popup menu components
				MenuItem lOptionsItem = new MenuItem("Option ...");
				MenuItem lMoveItem    = new MenuItem("Move");
				MenuItem lAboutItem   = new MenuItem("About");
				
				cPopup.add( lOptionsItem );
				lOptionsItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {

												cPopup.setEnabled(false);
												new DialogOptions();
												cPopup.setEnabled(true);
										}
								});
				//													mettre une option deplacer dans le menu qui la rend normale et permet de la deplacer puis une fois le grab fini la rend translucent

				cPopup.add( lMoveItem );
				lMoveItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										
										// On creer une fenetre qui peut bouger
										//		if( sTmpT1m3.cDecorated == true ){
												{
														T1m3.sTheT1m3.cMyTrayIcon.removeMe();
														T1m3.sTheT1m3.setVisible( false );
														T1m3.sTheT1m3.dispose();
														//		System.out.println("Tray dispose !");
														
														new T1m3( true );
														T1m3.sTheT1m3.setVisible(true);
												}
								}
								});
						

				//			sTmpT1m3.setVisible( false );
				//						sTmpT1m3.dispose();
				
				cPopup.add( lAboutItem );
				lAboutItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										cPopup.setEnabled(false);

										URL lURLIcon = getClass().getResource("/com/phipo/PPg/T1m3/T1m3_80x80.png");

										JOptionPane.showConfirmDialog( null,	"T1m3\n1.0\nPhilippe Poupon 2013", "About T1m3", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( lURLIcon ));	

										/*										JOptionPane.showConfirmDialog( null,	"T1m3\n1.0\nPhilippe Poupon 2013", "About T1m3", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( "T1m3_80x80.png" ));	
										*/
										
										cPopup.setEnabled(true);
					}
						});
				 
			 
				return true;
		}
		
}
//*************************************************
