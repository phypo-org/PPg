package org.phypo.PPgEdImg;




import java.awt.image.*;
import javax.swing.*;
import java.io.File;

import javax.swing.*;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;

import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgImg.*;

//*************************************************

public class DialogImg  extends JDialog 
		implements  ActionListener, ChangeListener	{


		JButton cButtonCancel = null;
		JButton cButtonOk     = null;

		JSpinner  cFieldWidth;
		JSpinner  cFieldHeight;

		JComboBox cResizeMode = null;

		int sDefaultWidth  = 800;
		int sDefaultHeight = 600;

		EdImgInst cInst = null;

		double cRapportWH = 1;

		final String sLockSize = "Lock";
		boolean cLockSize = false;
		static ImageIcon cImgUnlock = EdImgUtils.GetStdSzImg( "Icones/Unlock.png" );
		static ImageIcon cImgLock =   EdImgUtils.GetStdSzImg( "Icones/Lock.png" );
		JToggleButton cLockToggle = null;


		//------------------------------------------------
		public	DialogImg( String pName, EdImgInst pInst ){

				super( PPgJFrame.sTheTopFrame, pName,  true );


				cInst = pInst;
				
				//		setLocation( SurvParam.sDialogX, SurvParam.sDialogY );				
				getContentPane().setLayout( new BorderLayout() );		

				JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 4 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

	
				cButtonOk= new JButton( "Ok" );
				cButtonOk.setActionCommand( "Ok");
				cButtonOk.addActionListener( this );
				lSouth.add( cButtonOk );
				getContentPane().add( lSouth, BorderLayout.SOUTH );

				cButtonCancel = new JButton( "Cancel" );
				cButtonCancel.setActionCommand( "Cancel");
				cButtonCancel.addActionListener( this );
				lSouth.add( cButtonCancel );

				
				JPanel lNorth  = new JPanel();

				JPanel lCenter = new JPanel();

				if( cInst != null ){
						System.out.println( " ***************************** RESIZE *************************" );

						cRapportWH = (double)cInst.cLayerGroup.getWidth() / (double)cInst.cLayerGroup.getHeight();
						lNorth.add( new JLabel( "Width" ));
						lNorth.add( cFieldWidth  = new JSpinner(  new SpinnerNumberModel(cInst.cLayerGroup.getWidth(), 1, 10000, 1)));

						lNorth.add( cLockToggle = new JToggleButton( cImgUnlock ));
						cLockToggle.setActionCommand( sLockSize );
						cLockToggle.addActionListener( this );

						lNorth.add( new JLabel( "Height"));
						lNorth.add( cFieldHeight = new JSpinner( new SpinnerNumberModel(cInst.cLayerGroup.getHeight(), 1, 10000, 1 )));

						cFieldWidth.addChangeListener(  this );
						cFieldHeight.addChangeListener(  this );


						String lResizeMode[]={ "No Strech", "Strech" };
						cResizeMode = new JComboBox( lResizeMode );
						cResizeMode.setSelectedIndex( 0 );  
						lCenter.add( cResizeMode );
						getContentPane().add( lCenter, BorderLayout.CENTER );

						//	cResizeMode.addActionListener( this );
						
				}
				else {
						lNorth.add( new JLabel( "Width" ));
						lNorth.add( cFieldWidth  = new JSpinner( new SpinnerNumberModel( sDefaultWidth,  1, 10000, 1)));
						lNorth.add( new JLabel( "Height"));
						lNorth.add( cFieldHeight = new JSpinner( new SpinnerNumberModel( sDefaultHeight, 1, 10000, 1)));
				}

				getContentPane().add( lNorth, BorderLayout.NORTH );

				pack();
				setVisible(true);			

		}
		//---------------------
		boolean finishOk(){

				System.out.println( "*************** W:" + cFieldWidth.getValue() + " H:" + cFieldHeight.getValue() );
				
				sDefaultWidth = (Integer)cFieldWidth.getValue();
				sDefaultHeight= (Integer) cFieldHeight.getValue();

				if( cInst != null ){
						// TESTER SI SCALE ou pas 

						if( cResizeMode.getSelectedIndex() == 0 )
								cInst.cLayerGroup.resize( (int)cFieldWidth.getValue(),  (int)cFieldHeight.getValue(), -1 );
						else
								cInst.cLayerGroup.resize(  (int)cFieldWidth.getValue(),  (int)cFieldHeight.getValue(), Image.SCALE_SMOOTH );

						cInst.cCanvas.saySize();
						cInst.cFrame.actualize();
				}else  {
						
						PPgFrameEdImg lFrame =  new PPgFrameEdImg( PPgMain.sThePPgMain, null, null, 
																											 new File( "noname"), 
																											 (int)cFieldWidth.getValue(),  (int)cFieldHeight.getValue());
						cInst = lFrame.cMyInst;
	
						PPgMain.sThePPgMain.addChild( lFrame );

						cInst.cFrame.pack();
				}
				return true;
		}
		//---------------------
		public void actionPerformed( ActionEvent pEv ){
				
				if( pEv.getActionCommand().equals("Cancel")) {
						dispose();
				}			
				else
						if( pEv.getActionCommand().equals("Ok")) {
								finishOk();
								dispose();								
						}
						else if( pEv.getActionCommand().equals( sLockSize )) {
								if( cLockSize == true ) {
										cLockToggle.setIcon( cImgUnlock );
										cLockSize = false;
								}
								else {
										cLockToggle.setIcon( cImgLock );
										cLockSize = true;
								}
						}
		}

		// PROBLEME DE LOCK ENTRE W ET H METTRE  UNE ICONE CADEBNAS OUVERTE 
		//---------------------
		public void stateChanged( ChangeEvent  pEvent){
				if( pEvent.getSource() ==  cFieldWidth ){

						int cValW = (Integer) cFieldWidth.getValue();
						if( cLockSize ){
								cFieldHeight.setValue( new Integer( (int)(cValW/cRapportWH )));
						}						
				}else if( pEvent.getSource() ==  cFieldHeight ){
						int cValH = (Integer) cFieldHeight.getValue();
						if( cLockSize ){
								cFieldWidth.setValue( new Integer( (int)(cValH*cRapportWH )));
						}						
						
				}
		}
}

//*************************************************
