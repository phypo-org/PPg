package org.phypo.PPg.PPgProjEd;



import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgEdImg.*;

//*************************************************

public class DialogProject  extends JDialog 
		implements  ActionListener	{

		Project cProject = null; 

		JButton cButtonCancel = null;
		JButton cButtonOk     = null;

		PPgInputField cStringField = null;


		boolean cValid = false;
		public boolean isValid() { return cValid; }


		//--------------------------------

		DialogProject(	Project pProject ){


				super( PPgJFrame.sTheTopFrame, "New project",  true );

				cProject = pProject;
				
				//			setLocation( SurvParam.sDialogX, SurvParam.sDialogY );				
				getContentPane().setLayout( new BorderLayout() );	

				cStringField = new PPgInputField( "Name",  cProject.toString(), PPgField.HORIZONTAL);
				getContentPane().add( cStringField, BorderLayout.CENTER );

				//===============
				JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 4 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));
				
				cButtonOk= new JButton( "Ok" );
				cButtonOk.setActionCommand( "Ok");
				cButtonOk.addActionListener( this );
				lSouth.add( cButtonOk );

				cButtonCancel = new JButton( "Cancel" );
				cButtonCancel.setActionCommand( "Cancel");
				cButtonCancel.addActionListener( this );
				lSouth.add( cButtonCancel );
				getContentPane().add( lSouth, BorderLayout.SOUTH );
				//===============
				
				pack();
				setVisible(true);
		}
		//--------------------------------

		public void actionPerformed( ActionEvent p_e ){
				
				if( p_e.getActionCommand().equals("Cancel")) {
						dispose();
				}			
				else
						if( p_e.getActionCommand().equals("Ok")) {
								cValid = true ;
								cProject.setName( cStringField.getString() );
								dispose();
						}
		}

}
//*************************************************
