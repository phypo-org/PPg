
package com.phipo.PPg.M0k4.DesktopAnim;




import java.awt.image.*;
import javax.swing.*;
import java.io.File;
import java.util.Date;
import java.util.Calendar;
import java.util.*;

import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;

import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;

import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;
import javax.swing.text.*;
import java.text.SimpleDateFormat;
																																														
import com.phipo.PPg.PPgWin.*;
import com.phipo.PPg.PPgSound.*;
import com.phipo.PPg.M0k4.*;


//*************************************************

public  class DialogOptions extends JDialog
		implements  ActionListener, ChangeListener{

		JComboBox        cComboBoxBell ;

		DesktopAnim        cDesktopAnim;

    JButton         cButtonClose;

		PPgSliderField  cOpaqueSlider ;

		PPgSliderField  cFontSizeSlider ;

		JCheckBox       cCheckAlwaysOnTop ; 


		JTabbedPane     cTabbedPane;

		//		float cTmpOpacity;
		//		float cTmpFontSize;

		JColorChooser   cChooserColor;
		FontComboBox    cFontComboBox;



		JSpinner             cSpinAlarm ;
		JFormattedTextField  cFieldHour ;
		protected JButton    cButtonDelSel;

		PPgInputField        cFieldIndicAlarm ;

		Point cLocation = new Point(200, 200);

		//------------------------------------------
		DialogOptions( DesktopAnim lDesktopAnim ) {
				super( lDesktopAnim, "DesktopAnim Options", true );
				
				cDesktopAnim = lDesktopAnim;
				setLocation( cLocation );

				cTabbedPane = new JTabbedPane();
				cTabbedPane.addTab( "Aspect", initAspect() );
				//				cTabbedPane.addTab( "Alarm", 	initAlarm() );
				getContentPane().add( cTabbedPane, BorderLayout.CENTER );

			//=================================
				JPanel lPanelSouth = new JPanel();
				lPanelSouth.setLayout( new FlowLayout( ));
				lPanelSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

	
				cButtonClose= new JButton( "Close" );
				cButtonClose.addActionListener( this );
				lPanelSouth.add( cButtonClose );

				getContentPane().add( lPanelSouth, BorderLayout.SOUTH );
				//=================================

				pack();
				setAlwaysOnTop(true);
				setVisible(true);
		}
 
		//------------------------------------------------
		JPanel initAspect(){

				JPanel lPanel = new JPanel();
				lPanel.setLayout( new BorderLayout() );	
				
				
			//=================================
			JPanel lPanelGrid = new JPanel();
			lPanelGrid.setLayout(  new GridLayout( 3, 1 ));

			//=================================

			Border lBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED);
			lPanelGrid.setBorder( BorderFactory.createTitledBorder( lBorder, " ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ));
			lPanel.add( lPanelGrid, BorderLayout.NORTH );

			//=================================
			JPanel lPanelView = new JPanel();
			lPanelView.setLayout( new FlowLayout() );	

			lPanelView.add( cOpaqueSlider = new PPgSliderField( "Opacity", 10, 1000, (int)(cDesktopAnim.cOpacity*1000f), this ));


			lPanelGrid.add( lPanelView );

			//=================================
			JPanel lPanelFont = new JPanel();
			lPanelFont.setLayout( new FlowLayout() );	
				
			lPanelFont.add( ( cFontSizeSlider = new PPgSliderField( "Font size", 10, 500, (int)cDesktopAnim.cFontSize, this )) );
			lPanelFont.add((cFontComboBox =	new FontComboBox( cDesktopAnim.sText ))); 

			cFontComboBox.select(cDesktopAnim.sText.getFont());


			lPanelGrid.add(lPanelFont); 
			//=================================
			JPanel lPanelPref = new JPanel();
			lPanelPref.setLayout( new FlowLayout() );	
				

			lPanelPref.add( ( cCheckAlwaysOnTop = new JCheckBox( "Always on top") ));
			cCheckAlwaysOnTop.setSelected( cDesktopAnim.cAlwaysOnTop );
			//==========================
			cCheckAlwaysOnTop.addItemListener( new ItemListener() {
							
							public void itemStateChanged(ItemEvent pEv){
									
									if( pEv.getStateChange() == ItemEvent.DESELECTED )
											cDesktopAnim.cAlwaysOnTop  = false;
									else	
											cDesktopAnim.cAlwaysOnTop = true;
							}
					});
			//==========================

			lPanelGrid.add(lPanelPref);

			//=================================
			lPanel.add( (cChooserColor =	new JColorChooser( cDesktopAnim.sColor)),  BorderLayout.CENTER); 
			cChooserColor.getSelectionModel().addChangeListener(this);
			cChooserColor.setPreviewPanel(new JPanel() );
			 
			//=================================

			return lPanel;
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEv ){
								
				if( pEv.getSource() == cOpaqueSlider ){

						cDesktopAnim.cOpacity = cOpaqueSlider.getValue()/1000f;
				} 
				else if(  pEv.getSource() == cFontSizeSlider ){						cDesktopAnim.cFontSize = cFontSizeSlider.getValue();					 						
				}

				cDesktopAnim.sColor = cChooserColor.getColor();

				cDesktopAnim.executeOptions();
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){
				

				if( cFieldIndicAlarm != null && pEv.getSource() == cFieldIndicAlarm.getTextField() ) {
						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								cDesktopAnim.sIndicAlarm = lStr.charAt(0);
						}
				}
				else if( pEv.getSource() == cButtonClose ) {

						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								cDesktopAnim.sIndicAlarm = lStr.charAt(0);
						}
						cDesktopAnim.savePref( cDesktopAnim.cFilePref );

						cLocation =  getLocation();						
						dispose();

				} else if(  pEv.getSource() ==cComboBoxBell ){
						
						cDesktopAnim.sFlagCarillonHeure = cDesktopAnim.sFlagCarillon = false;
						switch( cComboBoxBell.getSelectedIndex() ){
						case 0: break;
						case 1: cDesktopAnim.sFlagCarillonHeure = true; 
								break;
						case 2: cDesktopAnim.sFlagCarillon = true ;
								break;
						}
				} 	
				cDesktopAnim.executeOptions();
		}

		//---------------------
};