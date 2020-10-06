package org.phypo.PPg.PPgWin;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.phypo.PPg.PPgWin.PPgField;
import org.phypo.PPg.PPgWin.PPgInputField;

import org.phypo.PPg.PPgUtils.*;

// **********************************
public class PPgLogin extends JDialog
implements  ActionListener	{

	JPanel cPanel;

	PPgInputField cFieldUser;
	PPgInputField cFieldPass;

	String cUser="";
	String cPass="";

	public String getUser()     { return cUser;}
	public String getPassword() { return cPass; }

	boolean cValidation = false;

	public boolean   getValidation() { return cValidation; }


	//-----------------------

	public PPgLogin( Frame pOwner  ){
		super( pOwner,"Login", true );
		init( -1, -1);
	}

	public PPgLogin(  Frame pOwner, String pName, PPgIniFile pFileIni, String pSection, String pKey, int pX, int pY  ){
		super( pOwner, pName, true );
		
		String  lStr = pFileIni.get( pSection, pKey );
		if( lStr != null ){
			PPgToken lTok = new PPgToken( lStr, "", "," );
			cUser =lTok.nextTokenStringTrim();	
			cPass =lTok.nextTokenStringTrim();
		}
		init( pX, pY);
	}

	public PPgLogin(  Frame pOwner,  int pX, int pY  ){
		super( pOwner, "Login to ", true );
		init(pX, pY);
	}
	//-----------------------

	void init( int pX, int pY ){

		if( pX != -1 && pY != -1 )
			setLocation( pX, pY );


		getContentPane().setLayout( new BorderLayout() );

		cPanel =	new JPanel();
		getContentPane().add( cPanel, BorderLayout.CENTER );

		cPanel.setLayout( new GridLayout( 2, 1 ));

		//				cFieldUrl = new PPgInputField( "Url", cServer.cUrl, PPField.HORIZONTAL );
		//				cPanel.add( cFieldUrl );
		cFieldUser = new PPgInputField( "User",    cUser, PPgField.HORIZONTAL  );
		cPanel.add( cFieldUser );
		cFieldPass = new PPgInputField( "Pass",    cPass, PPgField.HORIZONTAL, '*'  );
		cPanel.add( cFieldPass );

		//===============
		JPanel lSouth = new JPanel();
		lSouth.setLayout( new GridLayout( 1, 0 ));
		lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

		JButton lButtonOk = new JButton( "Ok" );
		lButtonOk.setActionCommand( "ok");
		lButtonOk.addActionListener( this );

		JButton  lButtonCancel = new JButton( "Cancel" );
		lButtonCancel.setActionCommand( "cancel");
		lButtonCancel.addActionListener( this );

		lSouth.add( lButtonOk );
		lSouth.add( lButtonCancel );
		//===============

		getContentPane().add( lSouth, BorderLayout.SOUTH );

		pack();
		setVisible(true);
	}
	//---------------------
	public void actionPerformed( ActionEvent p_e ){

		if( p_e.getActionCommand().equals("ok")) {						
			setVisible(false);
			//						cServer.cUrl = new String( cFieldUrl.getString());
			cUser = new String( cFieldUser.getString());
			cPass = new String( cFieldPass.getString());
			cValidation = true;
			ok();
		}
		else if( p_e.getActionCommand().equals("cancel")) {
			setVisible(false);
			cValidation = false;
			dispose();
		}
	}

	//---------------------
	public void ok(){	
		if( cUser.length() > 0 && cPass.length() > 0 ) {
			cValidation = true;
		}		
		setVisible(false);
		dispose();
	}
}
