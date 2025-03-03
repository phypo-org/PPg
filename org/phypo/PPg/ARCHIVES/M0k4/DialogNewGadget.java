package com.phipo.PPg.M0k4;


import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.border.BevelBorder;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;


import java.net.*;

import com.phipo.PPg.PPgWin.*;


//************************************************


public  class DialogNewGadget extends JDialog {
																			//implements   ActionListener { //, ChangeListener, ItemListener	{

		PPgInputField cFieldURL;
		PPgInputField cFieldName;
		
		//------------------------------------------------

		DialogNewGadget(){

				setLayout( new BorderLayout( ));
				JPanel lPanelN= new JPanel();
				lPanelN.setLayout( new FlowLayout( ));
				getContentPane().add( lPanelN, BorderLayout.NORTH );

				cFieldURL =  new PPgInputField( "Path URL", "file://./Gadget", PPgField.HORIZONTAL);
				lPanelN.add( cFieldURL );


				cFieldName =  new PPgInputField( "Gadget name", "com.phipo.PPg.M0k4.TestGadget.TestGadget", PPgField.HORIZONTAL);
				lPanelN.add( cFieldName );
				
				cFieldName.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										
										try { 
												String lName = cFieldName.getString();
												System.out.println( "Start "+ cFieldURL.getString() + " ->  " +lName);
												URL [] lURLs = {  new URL( cFieldURL.getString() )};
												
												URLClassLoader lLoader = new URLClassLoader( lURLs );
												
												Class lClass = null;   
												
												String lClassName = cFieldName.getString();
												try {        
														System.out.println( "findClass" );
														lClass =  lLoader.loadClass( lClassName ); 
												} 
												catch(ClassNotFoundException ex) {
														System.err.println("Classe " + lClassName + " not found");
														ex.printStackTrace();   
												}    
												if( lClass != null ) {
														try {   
																System.out.println( "newInstance" );
																M0k4_Gadget  lGadget = (M0k4_Gadget)lClass.newInstance();  
																
																//												M0k4_Gadget lNewGadget	= (M0k4_Gadget)lObj;
																//	IL FAUT STOCKER L4INSTANCE QQ PART
														} 
														catch(InstantiationException ex) {  
																System.err.println( "Error in instantiation of class " + lClassName);
																ex.printStackTrace();
														}    
														catch(IllegalAccessException ex) {  
																System.err.println( "Error in instantiation of class " + lClassName +" : IllegalAccess");
																ex.printStackTrace();
														}    
												}									
										}catch( SecurityException ex ){ 
												System.err.println( "SecurityException ");
												ex.printStackTrace();
										}  catch( MalformedURLException ex ){	
												System.err.println( "MalformedURLException ");
												ex.printStackTrace();
										}
								}
						});

				pack();
				setAlwaysOnTop(true);
				setVisible(true);
		}
}

//************************************************

