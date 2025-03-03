
package com.phipo.PPg.M0k4.TimAlarm;




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
		implements  ActionListener, ChangeListener, ItemListener{

		JComboBox        cComboBoxBell ;
		String           cBellMode[]={ "no bell", "Hour bell", "15 mn bell",  };

		TimAlarm        cTimAlarm;

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
		DialogOptions( TimAlarm lTimAlarm ) {
				super( lTimAlarm, "TimAlarm Options", true );
				
				cTimAlarm = lTimAlarm;
				setLocation( cLocation );

				cTabbedPane = new JTabbedPane();
				cTabbedPane.addTab( "Aspect", initAspect() );
				cTabbedPane.addTab( "Alarm", 	initAlarm() );
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
		//---------------------		
		JCheckBox       cCheckUseSecond ; 


		JPanel initAlarm(){
				JPanel lPanel = new JPanel();
				try{
						lPanel.setLayout( new BorderLayout( ));
						JPanel lPanelHour = new JPanel();
						lPanelHour.setLayout( new FlowLayout() );	
						lPanel.add( lPanelHour, BorderLayout.NORTH );
						
						lPanelHour.add( ( cCheckUseSecond = new JCheckBox( "View seconds") ));
						cCheckUseSecond.setSelected( cTimAlarm.sUseSecond );
						cCheckUseSecond.addItemListener( this );

						final MaskFormatter lFormat= new MaskFormatter("##:##");
						
						lFormat.setPlaceholderCharacter('_');
						cFieldHour = new JFormattedTextField(lFormat);
						lPanelHour.add( new JLabel("New alarm" ) );
						lPanelHour.add( cFieldHour );
						
						cFieldHour.setValue( cTimAlarm.sStrTxt.substring( 0, 5 ));
						
						
						lPanelHour.add( (cFieldIndicAlarm = new PPgInputField( "  Alarm Indicator", ""+cTimAlarm.sIndicAlarm, PPgField.HORIZONTAL )));
						cFieldIndicAlarm.getTextField().addActionListener( this );
												
						
						//==============================
						TreeSet<String> lTreeSet = new TreeSet<String>( cTimAlarm.sAlarm);
						final JList<String> lListAlarm = new JList<String>(  new Vector<String>(lTreeSet ));
						
						lListAlarm.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
						lListAlarm.setVisibleRowCount(-1);
						
						JScrollPane lListScroller = new JScrollPane(lListAlarm);
						lListScroller.setPreferredSize(new Dimension(80, 180));
						
						JPanel lPanelList = new JPanel();
						lPanelList.setLayout( new FlowLayout() );	
						lPanelList.add( lListScroller );
						
						lPanel.add( lPanelList , BorderLayout.CENTER );
						
						cFieldHour.addPropertyChangeListener("value", new PropertyChangeListener() {					
								@Override public void propertyChange(PropertyChangeEvent evt) {
										
										cTimAlarm.sAlarm.add( (String)cFieldHour.getValue() );
										TreeSet<String> lTreeSet = new 	TreeSet<String>( cTimAlarm.sAlarm );
										lListAlarm.setListData(new Vector<String>(lTreeSet ));
								}
								});
						
						cButtonDelSel= new JButton( "Delete" );
						cButtonDelSel.addActionListener( this );
						cButtonDelSel.addActionListener( new ActionListener(){
										@Override  public void actionPerformed( ActionEvent pEv ){												
												
												int lMin = lListAlarm.getMinSelectionIndex();
												if( lMin == -1 )
														return ;

										int lMax = lListAlarm.getMaxSelectionIndex();
										java.util.List<String> lSel = 	lListAlarm.getSelectedValuesList();

										cTimAlarm.sAlarm.remove( lSel.get(0) );																				
										TreeSet<String> lTreeSet = new 	TreeSet<String>( cTimAlarm.sAlarm );
										lListAlarm.setListData(new Vector<String>(lTreeSet ));

								}
						});
				lPanelList.add( cButtonDelSel );

				//==============================


				JPanel lPanelCarillon = new JPanel();
				lPanelCarillon.setLayout( new FlowLayout() );	

				cComboBoxBell =new JComboBox( cBellMode );
				cComboBoxBell.addActionListener( this );
				if( cTimAlarm.sFlagCarillonHeure )
						cComboBoxBell.setSelectedIndex(1);
				else 
						if( cTimAlarm.sFlagCarillon )
								cComboBoxBell.setSelectedIndex(2);
						else 
								cComboBoxBell.setSelectedIndex(0);

				lPanelCarillon.add( cComboBoxBell  );

				String [] cInst = new String[128 ];
				for( int i=0; i< 128; i++ )
						cInst[i] = ""+i;

				String [] cNote = new String[128 ];
				for( int i=10; i< 110; i++ )
						cNote[i] = ""+i;

				
				String [] cSpeed= new String[64];
				for( int i=0; i< 64; i++ )
						cSpeed[i] = ""+i;



				lPanel.add( lPanelCarillon , BorderLayout.SOUTH );


				}catch (Exception ex) {
						ex.printStackTrace();
				}

				return lPanel;
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

			lPanelView.add( cOpaqueSlider = new PPgSliderField( "Opacity", 10, 1000, (int)(cTimAlarm.sOpacity*1000f), this ));


			lPanelGrid.add( lPanelView );

			//=================================
			JPanel lPanelFont = new JPanel();
			lPanelFont.setLayout( new FlowLayout() );	
				
			lPanelFont.add( ( cFontSizeSlider = new PPgSliderField( "Font size", 10, 500, (int)cTimAlarm.sFontSize, this )) );
			lPanelFont.add((cFontComboBox =	new FontComboBox( cTimAlarm.sText ))); 

			cFontComboBox.select(cTimAlarm.sText.getFont());


			lPanelGrid.add(lPanelFont); 
			//=================================
			JPanel lPanelPref = new JPanel();
			lPanelPref.setLayout( new FlowLayout() );	
				

			lPanelPref.add( ( cCheckAlwaysOnTop = new JCheckBox( "Always on top") ));
			cCheckAlwaysOnTop.setSelected( cTimAlarm.sAlwaysOnTop );
			//==========================
			cCheckAlwaysOnTop.addItemListener( new ItemListener() {
							
							public void itemStateChanged(ItemEvent pEv){
									
									if( pEv.getStateChange() == ItemEvent.DESELECTED )
											cTimAlarm.sAlwaysOnTop  = false;
									else	
											cTimAlarm.sAlwaysOnTop = true;
							}
					});
			//==========================

			lPanelGrid.add(lPanelPref);

			//=================================
			lPanel.add( (cChooserColor =	new JColorChooser( cTimAlarm.sColor)),  BorderLayout.CENTER); 
			cChooserColor.getSelectionModel().addChangeListener(this);
			cChooserColor.setPreviewPanel(new JPanel() );
			 
			//=================================

			return lPanel;
		}
		//------------------------------------------------		
		public void itemStateChanged(ItemEvent pEv ){
				if( pEv.getItemSelectable() == cCheckUseSecond ) {
						if( pEv.getStateChange() == ItemEvent.DESELECTED )
								 cTimAlarm.sUseSecond  = false;
						else	
								cTimAlarm.sUseSecond = true;
				} 
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEv ){
								
				if( pEv.getSource() == cOpaqueSlider ){

						cTimAlarm.sOpacity = cOpaqueSlider.getValue()/1000f;
				} 
				else if(  pEv.getSource() == cFontSizeSlider ){						cTimAlarm.sFontSize = cFontSizeSlider.getValue();					 						
				}

				cTimAlarm.sColor = cChooserColor.getColor();

				cTimAlarm.executeOptions();
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){
				

				if( cFieldIndicAlarm != null && pEv.getSource() == cFieldIndicAlarm.getTextField() ) {
						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								cTimAlarm.sIndicAlarm = lStr.charAt(0);
						}
				}
				else if( pEv.getSource() == cButtonClose ) {

						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								cTimAlarm.sIndicAlarm = lStr.charAt(0);
						}
						cLocation =  getLocation();						
						dispose();

						cTimAlarm.savePref( cTimAlarm.cFilePref );
				} else if(  pEv.getSource() ==cComboBoxBell ){
						
						cTimAlarm.sFlagCarillonHeure = cTimAlarm.sFlagCarillon = false;
						switch( cComboBoxBell.getSelectedIndex() ){
						case 0: break;
						case 1: cTimAlarm.sFlagCarillonHeure = true; 
								break;
						case 2: cTimAlarm.sFlagCarillon = true ;
								break;
						}
				} 	
				cTimAlarm.executeOptions();
		}

		//---------------------
};