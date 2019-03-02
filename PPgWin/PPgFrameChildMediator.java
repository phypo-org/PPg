package org.phypo.PPg.PPgWin;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.*;

import java.util.Hashtable;

//***********************************
class PPgFrameChildMediatorData{

		public JMenuBar cMenuBar = null;
		public JToolBar cToolBar = null;
};

//***********************************
public class PPgFrameChildMediator implements ActionListener{

		PPgFrameChild              cCurrentFrameChild      = null;
		PPgFrameChildMediatorData  cCurrentFrameChildData = null;
		
		Hashtable<String, PPgFrameChildMediatorData> cFrameChildTab = new Hashtable<String,PPgFrameChild>();
		
		
		PPgFrameChildMediator sTheChildMediator = null;
		
		//------------------------------------------------
		PPgFrameChildMediator() {
				sTheChildMediator = this;
		}
		
		//------------------------------------------------
		public void addFrameChildData( String pKey, JMenuBar pMenuBar, JToolBar pToolBar ){

				// On cherche la data
				PPgFrameChildMediatorData lData = cFrameChildTab.get( pKey );
				if( lData == null ){
						lData = new PPgFrameChildMediatorData();
						cFrameChildTab.add( pKey, lData );
				}
				if( pMenuBar != null )
						lData.cMenuBar = pMenuBar;
				
				if( pMenuBar != null )
						lData.cMenuBar = pMenuBar;
				
		}
		//------------------------------------------------
		
		public void setFrameCurrentChild( PPgFrameChild pChild ){
				
				cCurrentFrameChild 	= pChild;				
				PPgFrameChildMediatorData cCurrentFrameChildData = cFrameChildTab.get( pChild.getChildType() );
		}
		//--------------------------------
		//--------------------------------
		//--------------------------------

		public void actionPerformed( ActionEvent pEv ){
				
				if( cCurrentFrameChild != null ){
						cCurrentFrameChild.actionPerformed( pEv );
				}
		}
		
}
//***********************************
