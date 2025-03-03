package org.phypo.SqlTools;



import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Font;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ArrayList;

import java.io.PrintStream;

import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.Sql.*;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;


//***********************************
public class FrameSqlTerm extends PPgFrameChild
    implements MouseListener, ActionListener, MenuListener{
    
    
    //		JTextArea   cTextCmd;
    JEditorPane cTextCmd;
    JTextArea   cTextResult;

    JTabbedPane cTabbedPane = null;

    JMenuBar    cMenubar;
    JMenu 	    cMenuEdit;
    
    
    public JEditorPane getTextCmd()   { return cTextCmd; }
    public JTextArea getTextResult() { return cTextResult; }
    public JMenuBar  getJMenuBar()   { return cMenubar;}
    public JMenu     getMenuEdit()   { return cMenuEdit;}
    
    
    
    
    public enum SendingMode{
	SendSimple(1),
	SendSelect(2),
	SendSelectWin(4),
	SendSimpleAndSelect( 3 ),
	SendSimpleAndWin( 5 ),
	SendSelectAndWin( 6 ),
	SendSimpleAndSelectAndWin( 7 );
	
	
	private final int cVal;
	public int getVal() { return cVal;}
	
	SendingMode( int pVal ) { cVal = pVal; }
    };
    
    SendingMode cSendingMode;
    
    
    JPanel       cSudPanel;
    JLabel       cStatus;
    
		
    SqlServer    cServer;
    SqlServer    cServerBackup;
    SqlConnex    cSqlConnex;
    PrintStream  cPrintStream=null;
    

    JMenu  cMenuSql = null;
    
    
    //------------------------------------------------
    
    final String sMenuEdit = "Edit";
    
    
    public final String cStrCut          = "Cut";
    public final String cStrCopy         = "Copy";
    public final String cStrPaste        = "Paste";
    public final String cStrClearAll     = "Clear all";

    //------------------------------------------------
    
    
    
    //-------------------------------------
    public FrameSqlTerm( SqlServer pServer, SqlServer pServerBackup,  String pText, SendingMode pSendingMode ){				
	super( pServer.cName+"."+pServer.cUser );
	
	cSendingMode = pSendingMode;
	
	cServer = pServer;
	
	
	getContentPane().setLayout( new BorderLayout() );
	
	cTextCmd   = new JEditorPane( "text/plain", ""); //JTextArea( pText );
	Font police = new Font( "Monospaced", Font.PLAIN, 14 );
	cTextCmd.setFont(police);
	cTextCmd.addMouseListener( this );		
	
	cTextResult = new JTextArea( "" );
	cTextResult.setFont(police);
	

	//==============								
	JSplitPane lSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
					    new JScrollPane(cTextCmd,
							    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
							    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ),	
					    new JScrollPane(cTextResult,
							    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
							    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ));		
	lSplit.setDividerLocation( 500 );	
	getContentPane().add( lSplit, BorderLayout.CENTER );
				
	//==============								
				
	
	cPrintStream = new PrintStream( new PPgTextStream(cTextResult), true); 
	
	
	cSudPanel = new JPanel();		
	cSudPanel.setLayout( new GridLayout( 1, 0 )); 
	cStatus  = new JLabel( cServer.cName+"."+cServer.cUser);
	
	//				setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
	
	cSudPanel.add( cStatus );
	
	getContentPane().add( cSudPanel, BorderLayout.SOUTH );				
				
	connect(false);
	
	
	setJMenuBar((cMenubar = new JMenuBar()));
	// Edit
	cMenuEdit = new JMenu( sMenuEdit );
	cMenuEdit.addMenuListener( this );
	cMenubar.add(cMenuEdit);	
	
	cMenuSql = new JMenu( "sql server" );
	cMenuSql.addMenuListener( this );
	cMenubar.add( cMenuSql );
    }
    //-----------------------
    void connect( boolean pLogin ){

	while( true ) {
	    if( pLogin == false ){
		if( cServer.isComplete() 
		    && (cSqlConnex = new SqlConnex( cServer, cServerBackup, cPrintStream )).connectOrLogin(PPgAppli.TheAppli,-1,-1) ) {
		    cPrintStream.println(  "Connected to " + cServer.cName );
		    //								cTextResult.append( "Connected to " + cServer.cName );
		    break;
		}		
	    }
	    pLogin = false;
						
	    SqlLogin lSqlLogin = new SqlLogin( PPgAppli.TheAppli, cServer, 300, 200  );
	    if( lSqlLogin.getValidation() == false ){
		cSqlConnex.disconnect();
		cSqlConnex = null;
		cTextResult.append( "Not connected");
		break;
	    }
						
	}
    }
    //----------------------
    static  Rectangle sRect = new Rectangle(0,1999999999, 1, 1);
    
    public void gotoEnd(){
	cTextResult.scrollRectToVisible(sRect);
    }
    //------------------------------------------------
    public void select( int pBegin , int pEnd) {
	cTextCmd.select( pBegin, pEnd);
    }
    //------------------------------------------------
    public void clearAll() {
	cTextCmd.setText("");
	cTextResult.setText("");
    }
    // ------------------------------------------------
    // ------------------------------------------------
    // ------------------------------------------------
    final String cStrConnect   = "Connect ";
    final String cStrSendSelect   = "Send selection ";
    final String cStrSendSelectWin   = "Send selection / window";
    final String cStrSend   = "Send selection / insert, update";
    
    protected JPopupMenu initPopup(JPopupMenu pPopmenu){
	
	
	for( int i=1; i< cMenuSql.getItemCount(); i++ ){
	    
	    if( cTextCmd.getSelectedText() == null )
		cMenuSql.getItem(i).setEnabled(false);
	    else
		cMenuSql.getItem(i).setEnabled(true);
	}

	JMenuItem lItem;
	
	pPopmenu.add( (lItem = new JMenuItem( cStrCut )));
	lItem.addActionListener(this);
	if( cTextCmd.getSelectedText() == null )
	    lItem.setEnabled( false );
	
	pPopmenu.add( (lItem = new JMenuItem( cStrCopy )));
	lItem.addActionListener(this);
	if( cTextCmd.getSelectedText() == null )
	    lItem.setEnabled( false );
	
	pPopmenu.add( (lItem = new JMenuItem( cStrPaste )));
	lItem.addActionListener(this);
	if( Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable( DataFlavor.stringFlavor) == false
	    || cTextCmd.isEditable() == false )
	    lItem.setEnabled( false );
	
	pPopmenu.add( (lItem = new JMenuItem( cStrClearAll )));
	lItem.addActionListener(this);
	
	pPopmenu.add( new JSeparator() );
	
	if( (cSendingMode.getVal() & SendingMode.SendSelect.getVal()) != 0 ){
	    pPopmenu.add( (lItem = new JMenuItem( cStrSendSelect )));
	    lItem.addActionListener(this);
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
	
	if( (cSendingMode.getVal() & SendingMode.SendSelectWin.getVal()) != 0 ){
	    pPopmenu.add( (lItem = new JMenuItem( cStrSendSelectWin )));
	    lItem.addActionListener(this);
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
	
	if( (cSendingMode.getVal() & SendingMode.SendSimple.getVal()) != 0 ){
	    pPopmenu.add( (lItem = new JMenuItem( cStrSend )));
	    lItem.addActionListener(this);
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
	return pPopmenu;
    }
    //-------------------------------------------
    //-------------------------------------------
    //		implements MouseListener
    //-------------------------------------------
    //-------------------------------------------
    public void mousePressed( MouseEvent pE ) {
	
	if( SwingUtilities.isRightMouseButton( pE ) == true 
	    && pE.getClickCount() == 1 ) {
	    
	    JPopupMenu lPopmenu = new JPopupMenu();
	    initPopup( lPopmenu );
	    
	    lPopmenu.show( pE.getComponent(),
			   pE.getX(),
			   pE.getY() );
	}					
    }
    //-------------------------- 
    public void mouseReleased( MouseEvent pEv )    {;}
    public void mouseEntered( MouseEvent pEv )    {;}
    public void mouseExited( MouseEvent pEv )     {;}
    public void mouseClicked( MouseEvent pEv )     {;}	
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    public void menuSelected( MenuEvent pEv ){
	
	JMenuItem lItem;
	
	cMenuEdit.add( (lItem = new JMenuItem( cStrCut )));
	lItem.setMnemonic( KeyEvent.VK_X );
	lItem.addActionListener(this);
	if( cTextCmd.getSelectedText() == null )
	    lItem.setEnabled( false );
	
	cMenuEdit.add( (lItem = new JMenuItem( cStrCopy )));
	lItem.addActionListener(this);
	lItem.setMnemonic( KeyEvent.VK_C );
	if( cTextCmd.getSelectedText() == null )
	    lItem.setEnabled( false );
	
	cMenuEdit.add( (lItem = new JMenuItem( cStrPaste )));
	lItem.setMnemonic( KeyEvent.VK_P );
	lItem.addActionListener(this);
	if( Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable( DataFlavor.stringFlavor) == false 
	    || cTextCmd.isEditable() == false )
	    lItem.setEnabled( false );
	
	cMenuEdit.add( (lItem = new JMenuItem( cStrClearAll )));
	lItem.addActionListener(this);				
	
	//================================
	cMenuSql.add( lItem  = new JMenuItem( cStrConnect ));
	lItem.addActionListener(this);
	

	if( (cSendingMode.getVal() & SendingMode.SendSelect.getVal()) != 0 ){
	    cMenuSql.add((lItem = new JMenuItem( cStrSendSelect )));
	    lItem.addActionListener(this);
	    lItem.setMnemonic( KeyEvent.VK_S );
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
	
	if( (cSendingMode.getVal() & SendingMode.SendSelectWin.getVal()) != 0 ){
	    cMenuSql.add( (lItem = new JMenuItem( cStrSendSelectWin )));
	    lItem.addActionListener(this);
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
	
	if( (cSendingMode.getVal() & SendingMode.SendSimple.getVal()) != 0 ){
	    cMenuSql.add( (lItem = new JMenuItem( cStrSend )));
	    lItem.addActionListener(this);
	    if( cTextCmd.getSelectedText() == null )
		lItem.setEnabled( false );
	}
    }
    //------------------------------------------------
    public void menuDeselected( MenuEvent pEv ){
	cMenuEdit.removeAll();
	cMenuSql.removeAll();
    }
    //------------------------------------------------
    public void menuCanceled( MenuEvent pEv ){
	cMenuEdit.removeAll();
	cMenuSql.removeAll();
    }
    //-------------------------------------------
    //-------------------------------------------
    //		implements ActionListener
    //-------------------------------------------
    //-------------------------------------------
    //------------------------------------------------
    public void actionPerformed(ActionEvent pEv ){
		    
	if( pEv.getActionCommand().equals( cStrCut )){
	    cTextCmd.cut();
	}
	else
	    if( pEv.getActionCommand().equals( cStrCopy )){
		cTextCmd.copy();
	    }
	    else
		if( pEv.getActionCommand().equals( cStrPaste )){
		    cTextCmd.paste();
		}
		else
		    if( pEv.getActionCommand().equals( cStrClearAll )){
			clearAll();
		    }
		    else
			if( pEv.getActionCommand().equals(cStrConnect)) {
			    
			    connect( true );
			}
	if( pEv.getActionCommand().equals( cStrSendSelect )){
	    String lOrder;
	    if( (lOrder=cTextCmd.getSelectedText()) != null ){
		boolean lRes = cSqlConnex.sendCommandAndPrintResult( lOrder );
		gotoEnd();
	    }
	}
	else
	    if( pEv.getActionCommand().equals( cStrSendSelectWin )){
		String lOrder;
		if( (lOrder=cTextCmd.getSelectedText()) != null ){
		    boolean lRes = cSqlConnex.sendCommandAndSetResult( lOrder, new SqlDataResult( cServer,  cServer.cName, lOrder, null) );
		    
		    gotoEnd();
		}
	    }
	    else
		if( pEv.getActionCommand().equals( cStrSend )){
		    String lOrder;
		    if( (lOrder=cTextCmd.getSelectedText()) != null ){
			System.out.println(  "Send SQL:" );
			boolean lRes = cSqlConnex.sendCommandWithGo( lOrder );
			gotoEnd();
		    }
		}
    }	 		
};
//***********************************
