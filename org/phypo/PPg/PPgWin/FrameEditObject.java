package org.phypo.PPg.PPgWin;


import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;


//***********************************
/** Affiche les proprietes des objets.
 */

public class FrameEditObject extends JDialog implements ActionListener{

		JScrollPane   cScrollpane;
		JPanel        cPanel;
		JButton       cButtonCancel;
		JButton       cButtonOk;
		
		FrameEditObjectInterface  cMyObject;
		
		public enum InfoMode { TITLE, VALUE, EDITABLE };

	// ------------------------------
		public FrameEditObject( String pName,  FrameEditObjectInterface  pMyObject, boolean pModal, int pX, int pY  ){

		 super( PPgAppli.TheAppli, pName, pModal );
		 setAlwaysOnTop( pModal );
				

		cMyObject  = pMyObject;

		getContentPane().setLayout( new BorderLayout() );
		
		cPanel = new JPanel();
		cPanel.setLayout( new GridLayout( pMyObject.FEOgetFieldCount(), 0 ));
		cScrollpane  = new JScrollPane( cPanel,
																		JScrollPane.VERTICAL_SCROLLBAR_NEVER,
																		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add( cScrollpane, BorderLayout.CENTER );
		
		
		boolean lOk = false;
		for( int i=0; i< pMyObject.FEOgetFieldCount(); i++){
				PPgInputField lField;
				cPanel.add((lField= new PPgInputField( pMyObject.FEOgetFieldInfo(i,InfoMode.TITLE), 
																						 pMyObject.FEOgetFieldInfo(i,InfoMode.VALUE),
																						 PPgField.HORIZONTAL)));

				boolean lEditable = pMyObject.FEOgetFieldInfo( i, InfoMode.EDITABLE )!= null;
				lField.setEditable( lEditable );
				if( lEditable  )
						lOk = true;
		}		
		cButtonCancel = new JButton( "Cancel" );
		cButtonCancel.setActionCommand( "cancel");
		cButtonCancel.addActionListener( this );

		
		if( lOk ){
				cButtonOk = new JButton( "Ok" );
				cButtonOk.setActionCommand( "ok");
				cButtonOk.addActionListener( this );				
				JPanel lPanelButton = new JPanel();
				lPanelButton.setLayout( new GridLayout( 1, 2 ));	
				getContentPane().add( lPanelButton, BorderLayout.SOUTH );	
				
				lPanelButton.add( cButtonCancel);	
				lPanelButton.add( cButtonOk);	
		}
		else											
				getContentPane().add( cButtonCancel,BorderLayout.SOUTH );	

		pack();		

		//		setLocation(pX, pY);
		//		 setLocationRelativeTo( PPAppli.ThePPAppli );

		Rectangle lRect = getBounds();

		pX -= lRect.getWidth()/2;
		pY -= lRect.getHeight()/2;
		if( pX <= 0 ) pX = 0;
		if( pY <= 0 ) pY = 0;
		setLocation( pX, pY );

		setVisible(true);			
	}
	// ------------------------------
	public void actionPerformed( ActionEvent p_e ){
			
			if( p_e.getActionCommand().equals("cancel")){
			}
			else
			if( p_e.getActionCommand().equals("ok")){
					FrameEditObjectInterface lTmpCopy = cMyObject.FEOduplicate();
					if( lTmpCopy == null ){
							JOptionPane.showInternalMessageDialog( this,
																										 "Error duplicate ",
																										 "Error", 
																										 JOptionPane.ERROR_MESSAGE );		 
							return;
					}

					for( int i=0; i< cMyObject.FEOgetFieldCount(); i++){
							PPgInputField lField = (PPgInputField)cPanel.getComponent(i);
							if( lField.isEditable() ){
									if( lTmpCopy.FEOsetField( i, lField.getString() ) == false ){
											JOptionPane.showInternalMessageDialog( this,
																														 "Error for " + lTmpCopy.FEOgetFieldInfo(i, InfoMode.TITLE ) 
																														 + " value :" + lField.getString(),
																														 "Error", 
																														 JOptionPane.ERROR_MESSAGE );		 
											return; 
									}
							}
					}
					cMyObject.FEOinitFrom( lTmpCopy );	
			}
			setVisible(false);
			cMyObject.FEOnotifFinish();
			this.dispose(); 
	}		
}
//***********************************

