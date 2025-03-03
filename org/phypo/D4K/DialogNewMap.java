package org.phypo.D4K;


import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgImg.*;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.JButton;

//*************************************************

public class DialogNewMap  extends JDialog 
    implements  ActionListener	{
    static int sNumMap=1;
    
    JButton cButtonCancel = null;
    JButton cButtonOk     = null;
        
    PPgInputField cFieldName    = null;
    PPgInputField cFieldKeyBase = null;
   
    //------------------------------------------------
    public	DialogNewMap(){

	super( PPgJFrame.sTheTopFrame, "New Map",  true );

				
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
	getContentPane().add( lNorth, BorderLayout.NORTH );
	lNorth.setLayout( new GridLayout( 2, 2 ));
	lNorth.add( ( cFieldName= new PPgInputField( "Name", "M"+sNumMap++, PPgField.HORIZONTAL)));
	lNorth.add( ( cFieldKeyBase= new PPgInputField( "Database Key", "",     PPgField.HORIZONTAL)));

		
	pack();
	setVisible(true);	
	
    }
 
    //---------------------
    public void actionPerformed( ActionEvent pEv ){
	
	if( pEv.getActionCommand().equals("Cancel")) {
	    dispose();
	} else if( pEv.getActionCommand().equals("Ok")) {
	    
	    String lName = cFieldName.getString();
	    String lKey  = cFieldKeyBase.getString();

	    if( lName.length() >0  && lKey.length() >0 )
		{
		  D4K.Instance.addNewMap( lName, lKey );
		}
	    else {
		JOptionPane.showMessageDialog(null, "Field is void, abort creation", "Error",
						JOptionPane.ERROR_MESSAGE);
	    }
	    
	    dispose();								
	}
    }
}

//*************************************************
