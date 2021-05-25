package org.phypo.PPg.PPgWin;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//**********************************************************
public class PPgMenuGrid  extends JMenu  implements MenuListener {
		

		LayoutManager cOld = null;
		int cMaxLine = 30;

		
		//------------------------------------------------

		public PPgMenuGrid( String pName, int pMaxLine ) {				

				super( pName );
				cMaxLine = pMaxLine;

				addMenuListener( this );				
				//			System.out.println( "************************** PPMenuGrid  " +  pName + " " + pMaxLine);
		}
		//------------------------------------------------

		public void 	menuCanceled(MenuEvent e){
				grid(e);
		}
		public void 	menuDeselected(MenuEvent e){
				grid(e);
		}				
		public void 	menuSelected(MenuEvent e){
				grid(e);
		}
		//------------------------------------------------
		public void grid( MenuEvent e ) {

				//			System.out.println( "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA Action :" + e );
				
				int lSz = getPopupMenu().getComponentCount();
				if( lSz > cMaxLine ) {

						if( cOld == null ) {
								cOld = getPopupMenu().getLayout();

								int lColumn = (lSz / cMaxLine)+1;
								int lLine = lSz/lColumn;
								if( lSz%lLine != 0 )
										lLine++;

								getPopupMenu().setLayout( new GridLayout( lLine, lColumn ));
						}
				}
				else {
						if( cOld != null ) {
								getPopupMenu().setLayout( cOld );
								cOld = null;
						}								
				}
		}
}
//**********************************************************
