package org.phypo.PPg.PPgWin;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//***********************************
public class PPgTerm extends JPanel implements ActionListener, MouseListener{

	protected JTextArea      cHisto;
	protected JTextField     cSaisie;
	protected ActionListener cListener;
	protected String         cLastCmd;

	protected PrintStream    cPrintStream=null;
	

	//-----------------------
		public void setResult( String pStr ){

		}
	//-----------------------
public 	String getLastCmd() { return cLastCmd; }
	//-----------------------
public 	PPgTerm( String pCmd, String pResult ){
		
		cHisto = new JTextArea(pResult);
		cHisto.setEditable( false );
		cHisto.addMouseListener( this );		
		JScrollPane cScrollpane = new JScrollPane( cHisto,
																								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
																								JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		cSaisie = new JTextField(pCmd);

		GridBagLayout lGridBag = new GridBagLayout();
		setLayout( lGridBag);
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		lGridBag.setConstraints(cSaisie, c);
		add( cScrollpane );

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		lGridBag.setConstraints(cScrollpane, c);
		add( cSaisie );
		
		cSaisie.addActionListener( this );

		Font police = new Font( "Monospaced", Font.PLAIN, 14 );
		cHisto.setFont(police);

		cPrintStream = new PrintStream( new PPgTextStream(cHisto), true); 
	}
	//----------------------
public 	void addActionListener( ActionListener pListener ){
		cListener = pListener;
	}
	//----------------------
public 	void append( String pStr ){
		cHisto.append( pStr );
	}
	//----------------------
public 	void appendln( String pStr ){
		cHisto.append( pStr );
		cHisto.append( "\n" );
	}
	//----------------------
public void actionPerformed(ActionEvent pEv ){

		if( actionPopupPerformed( pEv )==false){
				
				cLastCmd = cSaisie.getText();
				cHisto.append( cLastCmd + "\n" );
				cSaisie.selectAll();
				
				if( cListener != null )
						cListener.actionPerformed( pEv );
		}
	}
 //----------------------
		static  Rectangle sRect = new Rectangle(0,1999999999, 1, 1);

		public void gotoEnd(){
				cHisto.scrollRectToVisible(sRect);
		}
 //----------------------
		public PrintStream stream() { return cPrintStream; }
 //----------------------
		public JTextArea getLog( ) { return cHisto;}
 //----------------------
		public JTextField getSaisie( ) { return cSaisie;}
	//-------------------------- 
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
		void setSaisie(String pStr){
				
		}
	//-------------------------- 
	public void mouseReleased( MouseEvent pE ) {
	}
	
	public void mouseEntered( MouseEvent pE ) {
	}

	public void mouseExited( MouseEvent pE ) {
	}
	// -----------------------------
	public void mouseClicked( MouseEvent pE ) {

	}
	//-------------------------------------
	public void mouseDragged( MouseEvent pE ){

	}
	//-------------------------------------
	public void mouseMoved( MouseEvent pE ){
		
	}
	// ------------------------
	protected JPopupMenu initPopup(JPopupMenu p_popmenu){
		
		JMenuItem l_mitem;
		p_popmenu.add( (l_mitem = new JMenuItem( "Clear" )));
		l_mitem.addActionListener(this);
		return p_popmenu;
	}
	// ---------------------
	protected boolean actionPopupPerformed( ActionEvent pEv ){		
			
			if( pEv.getActionCommand().equals(  "Clear" )){
					cHisto.setText("");
					return true;
			}
			return false;
	}

};
