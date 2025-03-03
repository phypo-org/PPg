package org.phypo.PPg.PPgProjEd;




import java.awt.event.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


//*************************************************


public class GrpAnim extends ObjBase {


		Project cMyProject = null;

		public Project getMyProject() { return cMyProject; }


		// ---------------------
		public 	GrpAnim( String pName, Project pMyProject ){
				super( pName );

				cMyProject = pMyProject;
		}
	 
		// ---------------------
		final String cStrNewAnim      = "New anim" ;


		// ---------------------

		public boolean initMenu(JComponent pMenu){
				
				super.initMenu( pMenu );

				JMenuItem lItem;

				pMenu.add( (lItem=new JMenuItem( cStrNewAnim )));
				lItem.addActionListener(this);


				return true;
		}

		//---------------------
		public void actionPerformed( ActionEvent pEv ){		

				if( pEv.getActionCommand().equals(cStrNewAnim )) {
						/*
						PPgAnim lAnim = new PPgAnim( " no name", cMyProject );
						DialogAnim lDiagAnim= new DialogAnim( lAnim  );	
						*/					
						// prendre en compte les modifs ou non
				}
		}

}


//*************************************************
