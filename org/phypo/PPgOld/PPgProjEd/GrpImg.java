package org.phypo.PPg.PPgProjEd;




import java.awt.event.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


import java.io.*;
import java.util.Properties;

import org.w3c.dom.*;

import org.phypo.PPg.PPgFileImgChooser.*;


//*************************************************


public class GrpImg extends ObjBase {

		Project cMyProject = null;

		public Project getMyProject() { return cMyProject; }

		JFileChooser cFileChooser = null;

		// ---------------------
		public 	GrpImg( String pName, Project pMyProject ){
				super( pName );

				cMyProject = pMyProject;
		}
	 
		// ---------------------
		final String cStrNewImg      = "New image" ;



		// ---------------------

		public boolean initMenu(JComponent pMenu){
				
				super.initMenu( pMenu );

				JMenuItem lItem;

				pMenu.add( (lItem=new JMenuItem( cStrNewImg )));
				lItem.addActionListener(this);
				

				return true;
		}

		//---------------------
		public void actionPerformed( ActionEvent pEv ){		

				if( pEv.getActionCommand().equals(cStrNewImg )) {

						if( cFileChooser == null ) {
								cFileChooser =  new JFileChooser(".");
								cFileChooser.addChoosableFileFilter(new PPgImageFilter());
								cFileChooser.setAcceptAllFileFilterUsed(false);
								cFileChooser.setFileView(new PPgImageFileView());
								cFileChooser.setAccessory(new PPgImagePreview( cFileChooser));
						}
								
						int lReturnVal = cFileChooser.showDialog( PPgProjEd.sThePPgProjEd,
																						"Import image");

						if( lReturnVal == JFileChooser.APPROVE_OPTION) {

								File file = cFileChooser.getSelectedFile();

								Dessin lDessin = new Dessin( file.getName(), file, cMyProject  );
								lDessin.addToParentNode( this );								
						} 
        }

        //Reset the file chooser for the next time it's shown.
				//        cFileChooser.setSelectedFile(null);
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------


		public boolean saveChild( Properties pProps, String pFather ) {

				
				super.saveChild( pProps, pFather + '.' + "GrpImage" );

				return true;
		}
		//------------------------------------------------
		public final static String TAG_XML_GRP_IMG     = "GrpImg";

		public boolean saveChildsXml( Document pDoc, Element pMother  ){
				

				Element lElt = pDoc.createElement( TAG_XML_GRP_IMG );
				pMother.appendChild( lElt );

				return super.saveChildsXml( pDoc, pMother );	
		}
}


//*************************************************
