
package com.phipo.PPg.T1m3;;




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


//*************************************************

public  class DialogOptions extends JDialog
		implements   ActionListener, ChangeListener, ItemListener	{

		JComboBox        cComboBoxBell = null;
		String           cBellMode[]={ "no bell", "Hour bell", "15 mn bell",  };


		PPgSliderField cOpaqueSlider   = null;

		PPgSliderField cFontSizeSlider = null;

		//		JCheckBox cCheckDecored = null; 
		JCheckBox cCheckUseSecond = null; 
		JCheckBox cCheckAlwaysOnTop = null; 

		protected JButton   cButtonClose     = null;

		JTabbedPane         cTabbedPane = null;


		protected BufferedImage cSavImg = null;
		protected BufferedImage cTmpImg = null;

		static float cDefaultOpacity;
		static float cDefaultFontSize;

		float cTmpOpacity;
		float cTmpFontSize;

		JColorChooser        cChooserColor;
		FontComboBox         cFontComboBox;

		static boolean cFirstTime = true;

		static Point sLocation = new Point(200, 200);


		JSpinner  cSpinAlarm = null;
		JFormattedTextField cFieldHour = null;
		protected JButton   cButtonDelSel     = null;

		PPgInputField cFieldIndicAlarm = null;

		//------------------------------------------
		DialogOptions() {
				super(	T1m3.sTheT1m3, "T1m3 Options", true );

				if( cFirstTime ){
						cDefaultOpacity  = T1m3.sOpacity;
						cDefaultFontSize = T1m3.sFontSize;	
				}
				cFirstTime = false;
				setLocation( sLocation );

				//				initTabbed();
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
		JPanel initAlarm(){
				JPanel lPanel = new JPanel();
				try{
 						lPanel.setLayout( new BorderLayout( ));
						JPanel lPanelHour = new JPanel();
						lPanelHour.setLayout( new FlowLayout() );	
						lPanel.add( lPanelHour, BorderLayout.NORTH );


						final MaskFormatter lFormat= new MaskFormatter("##:##");
						
						lFormat.setPlaceholderCharacter('_');
						cFieldHour = new JFormattedTextField(lFormat);
						lPanelHour.add( new JLabel("New alarm" ) );
						lPanelHour.add( cFieldHour );
				
						cFieldHour.setValue( T1m3.sStrTxt.substring( 0, 5 )); //"12:00" );

				
						lPanelHour.add( (cFieldIndicAlarm = new PPgInputField( "  Alarm Indicator", ""+T1m3.sIndicAlarm, PPgField.HORIZONTAL )));
						cFieldIndicAlarm.getTextField().addActionListener( this );



				//==============================
				TreeSet<String> lTreeSet = new 	TreeSet<String>( T1m3.sAlarm);
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
										//										System.out.println("propertyChange:" + cFieldHour.getValue());

										T1m3.sAlarm.add( (String)cFieldHour.getValue() );
										TreeSet<String> lTreeSet = new 	TreeSet<String>( T1m3.sAlarm );
										lListAlarm.setListData(new Vector<String>(lTreeSet ));
								}
						});
				
				cButtonDelSel= new JButton( "Delete" );
				cButtonDelSel.addActionListener( this );
				cButtonDelSel.addActionListener( new ActionListener(){
								@Override  public void actionPerformed( ActionEvent pEv ){
										//
										//			Toolkit.getDefaultToolkit().beep();



												int lMin = lListAlarm.getMinSelectionIndex();
												if( lMin == -1 )
														return ;

										int lMax = lListAlarm.getMaxSelectionIndex();
										//										System.out.println( "delete " + lMin + " " +lMax );
										java.util.List<String> lSel = 	lListAlarm.getSelectedValuesList();

										T1m3.sAlarm.remove( lSel.get(0) );																				
										TreeSet<String> lTreeSet = new 	TreeSet<String>( T1m3.sAlarm );
										lListAlarm.setListData(new Vector<String>(lTreeSet ));

								}
						});
				lPanelList.add( cButtonDelSel );

				//==============================


				JPanel lPanelCarillon = new JPanel();
				lPanelCarillon.setLayout( new FlowLayout() );	

				cComboBoxBell =new JComboBox( cBellMode );
				cComboBoxBell.addActionListener( this );
				if( T1m3.sFlagCarillonHeure )
						cComboBoxBell.setSelectedIndex(1);
				else 
						if( T1m3.sFlagCarillon )
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
		//---------------------


		//---------------------
		JPanel initAspect(){

				JPanel lPanel = new JPanel();
				lPanel.setLayout( new BorderLayout() );	
				
				
			cTmpOpacity  =T1m3.sOpacity;
			cTmpFontSize =T1m3.sFontSize;


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

			lPanelView.add( cOpaqueSlider = new PPgSliderField( "Opacity", 10, 1000, (int)(T1m3.sOpacity*1000f), this ));


			lPanelGrid.add( lPanelView );

			//=================================
			JPanel lPanelFont = new JPanel();
			lPanelFont.setLayout( new FlowLayout() );	
				
			lPanelFont.add( ( cFontSizeSlider = new PPgSliderField( "Font size", 10, 500, (int)T1m3.sFontSize, this )) );
			lPanelFont.add((cFontComboBox =	new FontComboBox( T1m3.sText ))); 

			cFontComboBox.select(T1m3.sText.getFont());


			lPanelGrid.add(lPanelFont); 
			//=================================
			JPanel lPanelPref = new JPanel();
			lPanelPref.setLayout( new FlowLayout() );	
				
			lPanelPref.add( ( cCheckUseSecond = new JCheckBox( "View seconds") ));
			cCheckUseSecond.setSelected( T1m3.sUseSecond );
			cCheckUseSecond.addItemListener( this );


			lPanelPref.add( ( cCheckAlwaysOnTop = new JCheckBox( "Always on top") ));
			cCheckAlwaysOnTop.setSelected( T1m3.sAlwaysOnTop );
			cCheckAlwaysOnTop.addItemListener( this );

			lPanelGrid.add(lPanelPref);

			//=================================
			lPanel.add( (cChooserColor =	new JColorChooser( T1m3.sColor)),  BorderLayout.CENTER); 
			cChooserColor.getSelectionModel().addChangeListener(this);
			cChooserColor.setPreviewPanel(new JPanel() );
			 
			//=================================

			return lPanel;
		}
		//------------------------------------------------		
		public void itemStateChanged(ItemEvent pEv ){
				if( pEv.getItemSelectable() == cCheckUseSecond ) {
						if( pEv.getStateChange() == ItemEvent.DESELECTED )
								 T1m3.sUseSecond  = false;
						else	
								T1m3.sUseSecond = true;
				} else	if( pEv.getItemSelectable() == cCheckAlwaysOnTop ){
						if( pEv.getStateChange() == ItemEvent.DESELECTED )
								T1m3.sAlwaysOnTop  = false;
						else	
								T1m3.sAlwaysOnTop = true;
				}
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEv ){
								
				if( pEv.getSource() == cOpaqueSlider ){

						T1m3.sOpacity = cOpaqueSlider.getValue()/1000f;
				} 
				else if(  pEv.getSource() == cFontSizeSlider ){						T1m3.sFontSize = cFontSizeSlider.getValue();					 						
				}


				T1m3.sColor = cChooserColor.getColor();

				T1m3.sTheT1m3.executeOptions();
		}
		static 	  int pInst = 10;
		static		int pNote = 10;
		static		int pSpeed = 10;
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){
				

				if( cFieldIndicAlarm != null && pEv.getSource() == cFieldIndicAlarm.getTextField() ) {
						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								T1m3.sIndicAlarm = lStr.charAt(0);
						}
				}
				else if( pEv.getSource() == cButtonClose ) {

						String lStr =  cFieldIndicAlarm.getString() ;
						if( lStr != null && lStr.length() > 0 ){
								T1m3.sIndicAlarm = lStr.charAt(0);
						}
						sLocation =  getLocation();						
						dispose();

						T1m3.sTheT1m3.savePref( T1m3.sFilePref );
				} else if(  pEv.getSource() ==cComboBoxBell ){
						
						T1m3.sFlagCarillonHeure = T1m3.sFlagCarillon = false;
						switch( cComboBoxBell.getSelectedIndex() ){
						case 0: break;
						case 1: T1m3.sFlagCarillonHeure = true; 
								break;
						case 2: T1m3.sFlagCarillon = true ;
								break;
						}
				} 	
				T1m3.sTheT1m3.executeOptions();
		}

		//---------------------
};