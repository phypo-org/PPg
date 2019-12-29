package org.phypo.PPg.Sql;

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
public class SqlLogin extends JDialog
    implements  ActionListener	{
    SqlServer cServer;

    JPanel cPanel;

    PPgInputField cFieldServer;
    PPgInputField cFieldUrl;
    PPgInputField cFieldMach;
    PPgInputField cFieldPort;
    PPgInputField cFieldUser;
    PPgInputField cFieldPass;
    PPgInputField cFieldBase;

    boolean cValidation = false;

    public boolean   getValidation() { return cValidation; }
    public SqlServer getSqlServer()  { return cServer; }

		
    //-----------------------

    public SqlLogin( Frame pOwner, int pX, int pY  ){
	super( pOwner,"Login", true );
	cServer = new SqlServer();
	init( pX, pY);
    }
		
    public SqlLogin(  Frame pOwner, String pName, PPgIniFile pFileIni, String pKeySection, int pX, int pY  ){
	super( pOwner, "Login to " +pName, true );
	cServer = new SqlServer( pFileIni, pKeySection, pName);
	init( pX, pY);
    }

    public SqlLogin(  Frame pOwner, SqlServer pServer,  int pX, int pY  ){
	super( pOwner, "Login to "+pServer.cName, true );
	cServer = pServer;
	init(pX, pY);
    }
    //-----------------------

    void init( int pX, int pY ){

	if( pX != -1 && pY != -1 )
	    setLocation( pX, pY );


	PPgTrace.Traceln( cServer.cMachine ); 
	getContentPane().setLayout( new BorderLayout() );

	cPanel =	new JPanel();
	getContentPane().add( cPanel, BorderLayout.CENTER );

	cPanel.setLayout( new GridLayout( 6, 1 ));

	//				cFieldUrl = new PPgInputField( "Url", cServer.cUrl, PPField.HORIZONTAL );
	//				cPanel.add( cFieldUrl );
	cFieldServer = new PPgInputField( "Server", cServer.cName, PPgField.HORIZONTAL );
	cPanel.add( cFieldServer );
	cFieldMach = new PPgInputField( "Machine", cServer.cMachine, PPgField.HORIZONTAL  );
	cPanel.add( cFieldMach );
	cFieldPort = new PPgInputField( "Port",    cServer.cPort, PPgField.HORIZONTAL  );
	cPanel.add( cFieldPort );
	cFieldUser = new PPgInputField( "User",    cServer.cUser, PPgField.HORIZONTAL  );
	cPanel.add( cFieldUser );
	cFieldPass = new PPgInputField( "Pass",    cServer.cPass, PPgField.HORIZONTAL  );
	cPanel.add( cFieldPass );
	cFieldBase = new PPgInputField( "Base",    cServer.cBase, PPgField.HORIZONTAL  );
	cPanel.add( cFieldBase );	

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
	    cServer.cName = new String( cFieldServer.getString());
	    //						cServer.cUrl = new String( cFieldUrl.getString());
	    cServer.cMachine = new String( cFieldMach.getString());
	    cServer.cPort = new String( cFieldPort.getString());
	    cServer.cUser = new String( cFieldUser.getString());
	    cServer.cPass = new String( cFieldPass.getString());
	    cServer.cBase = new String( cFieldBase.getString());
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
	cValidation = true;					
	setVisible(false);
	dispose();
    }
}
