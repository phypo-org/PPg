package org.phypo.PPg.PPgWin;


import java.util.*;


import java.awt.*;


import java.awt.event.*;
import javax.swing.*;

import java.text.DateFormat;

import org.phypo.PPg.PPgUtils.*;


//***********************************

public abstract class PPgJFrame extends JFrame
		implements ActionListener, ItemListener,
							 PPgInterfaceAppli{
		
		public static PPgJFrame sTheTopFrame=null;

		ArrayList<PPgFrameChild> cListFrameChild = new ArrayList<PPgFrameChild>();

		public ArrayList<PPgFrameChild> getFrameChilds() { return cListFrameChild; }

		//-------------------------

		public JDesktopPane cDesktop = null;

		public JMenuBar     cMenuBar = null;
		public JToolBar      cToolBar[] = new JToolBar[4];

		//	public JMenuBar getMenuBar() { return cMenubar;}

		//-------------------------

		DateFormat cDateFormat =  DateFormat.getDateTimeInstance();
		public DateFormat getDateFormat() { return cDateFormat;}
		
		//-------------------------
		public void makeToolBar( boolean p0,  boolean p1, boolean p2, boolean p3 ){
				if(  p0 ){
						makeToolBar( 0 );
				}				
				if( p1 ){
							makeToolBar( 1 );
				}
				if(  p2 ){
						makeToolBar( 2 );
				}
				if( p3 ){
							makeToolBar( 3 );
				}
					
		}
		//-------------------------
		public JToolBar makeToolBar( int pNum){

				if( cToolBar[pNum] == null ){
						cToolBar[pNum] =new JToolBar("Still draggable");
						//					cToolBar[pNum].setFloatable(true);
						switch( pNum ){
						case 0 :	add( cToolBar[pNum], BorderLayout.PAGE_START);
								break;
						case 1 :	add( cToolBar[pNum], BorderLayout.LINE_START);
								cToolBar[pNum].setOrientation( JToolBar.VERTICAL );
								break;
						case 2 :	add( cToolBar[pNum], BorderLayout.PAGE_END);
								break;
						case 3 :	add( cToolBar[pNum], BorderLayout.LINE_END);
								cToolBar[pNum].setOrientation( JToolBar.VERTICAL );
								break;
						}
				}
				return cToolBar[pNum];
		}
		//-------------------------
		public JToolBar getToolBar( int pNum){
				return  cToolBar[pNum] ;
		}
		//-------------------------
		public static Color sGlobalForeground = null;
		public static Color sGlobalBackground = null;

		public static void SetGlobalForeground( Color pColor ) { 
				sGlobalForeground=pColor;
				if( pColor!= null && sTheTopFrame != null )
						sTheTopFrame.setForeground( pColor );
		}
		public static void SetGlobalBackground( Color pColor ) { 
				sGlobalBackground=pColor;
				if( pColor!= null  && sTheTopFrame != null  )
						sTheTopFrame.setBackground( pColor );
		}
		//-------------------------

		static public PPgInterfaceAppli GetInterfaceAppl() { return sTheTopFrame; }

	//-------------------------------------
		public 	PPgJFrame( String pStr, boolean pDesk ){
				super( pStr );
				if( sTheTopFrame == null )
						sTheTopFrame = this;


		// ----- Make the big window be indented 50 pixels from each edge 
		//of the screen.
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
		//    cToolbar = new JToolBar();

		//		getContentPane().add( cToolbar, BorderLayout.NORTH );

		if( pDesk ){			
			//Set up the GUI.
			cDesktop = new JDesktopPane(); //a specialized layered pane
			getContentPane().add( cDesktop, BorderLayout.CENTER );

			//Make dragging faster:
			cDesktop.putClientProperty("JDesktopPane.dragMode", "outline");
		}

		//		createFrameDraw(); //Create first window
		//		setContentPane(cdesktop);

		setJMenuBar(cMenuBar = new JMenuBar());


	}
	//-------------------------------------
		public	JMenuItem  addItem( JMenu pMenu, String pStr ){

		JMenuItem lItem = new JMenuItem( pStr);

		lItem.addActionListener( this );	 

		pMenu.add( lItem);
		return lItem;
	}
	//-------------------------------------
		public	JMenuItem  addItem( JMenu pMenu, String pStr, ActionListener pListener ){

		JMenuItem lItem = new JMenuItem( pStr);

		lItem.addActionListener( pListener);	 

		pMenu.add( lItem);
		return lItem;
	}
	//-------------------------------------
		public 	JMenuItem  addAbout( JMenu pMenu ){
				
				JMenuItem lItem = new JMenuItem( "About");

				lItem.addActionListener( this );	 

				pMenu.add( lItem);
				return lItem;
	}
		//-------------------------------------

		public JMenu getFileMenu() { return null; }
		public JMenu getEditMenu() { return null; }
		public JMenu getViewMenu() { return null; }
		public JMenu getWindowsMenu() { return null; }

		//-------------------------------------
		//------------------------------------- 
		//-------------------------------------
		/*
		public 	JButton addButtonToToolbar( String pstr ){
				
				JButton  l_button = new JButton( pstr );
				
				l_button.setActionCommand( pstr);
				l_button.addActionListener( this );
				cToolbar.add( l_button );
		return l_button;
		}
		*/
	//-------------------------------------
		public void openChild(  PPgFrameChild pframe ){
				pframe.setClosable(false );
				try{
						pframe.setIcon(false);
				}catch(java.beans.PropertyVetoException e ){
				}
				pframe.setVisible(true); //necessary as of kestrel
				pframe.show();
		}
		//-------------------------------------
		public void addChild( PPgFrameChild pframe ){
				pframe.setVisible(true); //necessary as of kestrel				
				cDesktop.add(pframe); 
				pframe.show();
				try{
						pframe.setSelected(true);
				}
				catch( java.beans.PropertyVetoException e ){
				}
				cListFrameChild.add( pframe );
		}
		//-------------------------------------
		public void removeChild( PPgFrameChild pframe ){
				pframe.setVisible(false); //necessary as of kestrel				
				cListFrameChild.remove( pframe );
				cDesktop.remove(pframe); 
		}
		
		public void log( String pStr ){
		}
		//-------------------------------------
		//-------------------------------------
		//-------------------------------------
		public static PPgIniFile sTheIniFile = null;

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
}
//***********************************
