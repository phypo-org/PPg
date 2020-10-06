package org.phypo.PPg.PPgWin;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.event.InternalFrameListener;


import javax.swing.event.InternalFrameEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import java.awt.Color;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.phypo.PPg.PPgUtils.PPgIniFile;

//***********************************
public abstract class PPgAppli extends JFrame
implements ActionListener, ItemListener, InternalFrameListener {

	public JDesktopPane c_desktop;
	public JMenuBar     cMenubar;
	public JToolBar     cToolbar;

	DateFormat cDateFormat =  DateFormat.getDateTimeInstance();
	DateFormat getDateFormat() { return cDateFormat;}

	public static PPgAppli TheAppli;
	public static PPgAppli GetAppli() { return TheAppli; }
	public static DateFormat GetDateFormat() { return TheAppli.cDateFormat; }

	public static Color sGlobalForeground = null;
	public static Color sGlobalBackground = null;

	public static void SetGlobalForeground( Color pColor ) { 
		sGlobalForeground=pColor;
		if( pColor!= null && TheAppli != null ){
			System.out.println( "setForeground");
			TheAppli.getContentPane().setForeground( pColor );
			TheAppli.setForeground( pColor );
		}
	}
	public static void SetGlobalBackground( Color pColor ) { 
		sGlobalBackground=pColor;
		if( pColor!= null  && TheAppli != null  ){
			System.out.println( "setBackground");
			TheAppli.getContentPane().setBackground( pColor );
			TheAppli.setBackground( pColor );
		}
	}


	//-------------------------------------
	public 	PPgAppli( String p_str, boolean p_desk ){
		super( p_str );
		TheAppli = this;


		// ----- Make the big window be indented 50 pixels from each edge 
		//of the screen.
		int inset = 50;
		Dimension screenSize = new Dimension( 1024, 800 );// Toolkit.getDefaultToolkit().getScreenSize();

		setBounds(inset, inset, 
				screenSize.width - inset*2, 
				screenSize.height-inset*2);


		//---- Quit this app when the big window closes. 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().setLayout( new BorderLayout());
		cToolbar = new JToolBar();

		getContentPane().add( cToolbar, BorderLayout.NORTH );

		if( p_desk ){			
			//Set up the GUI.
			c_desktop = new JDesktopPane(); //a specialized layered pane
			getContentPane().add( c_desktop, BorderLayout.CENTER );
			//Make dragging faster:
			c_desktop.putClientProperty("JDesktopPane.dragMode", "outline");
		}

		//		createFrameDraw(); //Create first window
		//		setContentPane(c_desktop);

		setJMenuBar(cMenubar = new JMenuBar());


	}
	//-------------------------------------
	public String getStringLocation(){
		return PPgWinUtils.GetStringLocation(this);
	}
	//-------------------------------------
	public 	JMenuItem  addItem( JMenu p_menu, String p_str, boolean pActif ){
		JMenuItem lItem =  addItem(  p_menu, p_str );
		lItem.setEnabled(pActif  );

		return lItem;
	}		
	//-------------------------------------

	public 	JMenuItem  addItem( JMenu p_menu, String p_str ){

		JMenuItem l_item = new JMenuItem( p_str);

		l_item.addActionListener( this );	 

		p_menu.add( l_item);
		return l_item;
	}
	//-------------------------------------
	public 	JMenuItem  addAbout( JMenu p_menu ){

		JMenuItem l_item = new JMenuItem( "About");

		l_item.addActionListener( this );	 

		p_menu.add( l_item);
		return l_item;
	}
	//-------------------------------------

	public JMenu getFileMenu() { return null; }
	public JMenu getEditMenu() { return null; }
	public JMenu getViewMenu() { return null; }
	public JMenu getWindowsMenu() { return null; }

	//-------------------------------------
	//-------------------------------------
	//-------------------------------------
	public 	JButton addButtonToToolbar( String p_str ){

		JButton  l_button = new JButton( p_str );

		l_button.setActionCommand( "p_str");
		l_button.addActionListener( this );
		cToolbar.add( l_button );
		return l_button;
	}
	//-------------------------------------
	public void openChild(  PPgFrameChild p_frame ){
		p_frame.setClosable(false );
		try{
			p_frame.setIcon(false);
		}catch(java.beans.PropertyVetoException e ){
		}
		p_frame.setVisible(true); //necessary as of kestrel
		p_frame.show();
	}
	//-------------------------------------
	public void addChild( PPgFrameChild p_frame ){
		p_frame.setVisible(true); //necessary as of kestrel				
		c_desktop.add(p_frame); 
		p_frame.show();
		try{
			p_frame.setSelected(true);
		}
		catch( java.beans.PropertyVetoException e ){
		}
	}
	//-------------------------------------
	public void removeChild( PPgFrameChild p_frame ){
		p_frame.setVisible(false); //necessary as of kestrel				
		c_desktop.remove(p_frame); 
	}

	public void log( String pStr ){
	}
	//-------------------------------------
	public void saveConfigIni( PPgIniFile pIni, String pSection ){
		pIni.set( pSection, "Mother", 
				((int)getLocation().getX())+","+
						((int)getLocation().getY())+","+
						((int)getSize().getWidth())+","+
						((int)getSize().getHeight()) );
	}
	//-------------------------------------
	public void readConfigIni( PPgIniFile pIni, String pSection ){
		Rectangle lRect = PPgIniFile.GetRectangle( pIni.get( pSection, "Mother" ), "," );
		if( lRect != null)	setBounds( lRect);
	}

	public  PPgIniFile  getStdIniFile() { return null; }

	//-------------------------------------
	public void 	internalFrameActivated(InternalFrameEvent e){
		// Invoked when an internal frame is activated.
	}
	public void 	internalFrameClosed(InternalFrameEvent e){
		//Invoked when an internal frame has been closed.
	}
	public void 	internalFrameClosing(InternalFrameEvent e){
		//Invoked when an internal frame is in the process of being closed.
	}
	public void 	internalFrameDeactivated(InternalFrameEvent e){
		//Invoked when an internal frame is de-activated.
	}
	public void 	internalFrameDeiconified(InternalFrameEvent e){
		//Invoked when an internal frame is de-iconified.
	}
	public  void 	internalFrameIconified(InternalFrameEvent e){
		//Invoked when an internal frame is iconified.
	}
	public void 	internalFrameOpened(InternalFrameEvent e){
		//Invoked when a internal frame has been opened.
	}

};
//***********************************
