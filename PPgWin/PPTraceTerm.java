package org.phypo.PPg.PPgWin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

// **********************************

public class PPTraceTerm extends PPgFrameChild implements ActionListener,  MouseListener{
	protected JTextArea      cHisto;
	protected PrintStream    cPrintStream=null;

		//		JMenuBar   cMenuBar;

		//-----------------------
		public 	PPTraceTerm( String pName) {
				super( pName );

				cHisto = new JTextArea();
		 		cHisto.setEditable( false );
				cHisto.addMouseListener( this );		

				JScrollPane lScrollpane = new JScrollPane( cHisto,
																								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
																								JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
				getContentPane().add( lScrollpane );

				Font police = new Font( "Monospaced", Font.PLAIN, 14 );
				cHisto.setFont(police);

				cPrintStream = new PrintStream( new PPgTextStream(cHisto), true); 
				setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );

				PPgAppli.TheAppli.addChild( this );	
		}

		//-----------------------
		public void mousePressed( MouseEvent pEv ) {
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {
		}
		
		public void mouseEntered( MouseEvent pEv ) {
		}
		
		public void mouseExited( MouseEvent pEv ) {
		}
		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {
				
				if( SwingUtilities.isRightMouseButton( pEv ) == true 
						&& pEv.getClickCount() == 1 ) {
						
						JPopupMenu lPopmenu = new JPopupMenu();
						initPopup( lPopmenu );
						
						lPopmenu.show( pEv.getComponent(),
													 pEv.getX(),
													 pEv.getY() );
				}					
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){
				
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){
				
		}
		// ------------------------
		JPopupMenu initPopup(JPopupMenu pPopmenu){
				
				JMenuItem lMitem;
				pPopmenu.add( (lMitem = new JMenuItem( "Clear" )));
				lMitem.addActionListener(this);
				return pPopmenu;
		}
		//----------------------
		public void actionPerformed(ActionEvent pEv ){
				if( pEv.getActionCommand().equals(  "Clear" )){
						cHisto.setText("");
				}
		}
		//----------------------
		public PrintStream stream() { return cPrintStream; }
	  //----------------------
		public JTextArea getLog( ) { return cHisto;}

};
//***********************************
