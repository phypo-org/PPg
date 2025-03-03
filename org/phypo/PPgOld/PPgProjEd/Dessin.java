package org.phypo.PPg.PPgProjEd;





import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import java.util.Properties;

import org.w3c.dom.*;





import org.phypo.PPg.PPgUtils.*;

import org.phypo.PPg.PPgEdImg.*;


//*************************************************
public class Dessin  extends ObjBase implements InterfaceEdImgListener {

		Project     cMyProject = null;
		File        cFile = null;
		public  File   getFile() { return cFile; }

		ImageIcon  cImg = null;
		ImageIcon  cIcon = null;

		//		PPgImgEd cMyEdit = null; // l'editeur d image s il y a lieu

		public ImageIcon getImage() { return cImg;}
	  public ImageIcon getIcon()  { return cIcon; }
		public String    getToolTipText() { return "Image" ;}

		public Project getMyProject() { return cMyProject; }


				
		// ---------------------
		public 	Dessin( String pName, File pFile, Project pMyProject ){
				super( pName );

				
				cFile = pFile;
				cMyProject = pMyProject;
				
				cImg = new ImageIcon( cFile.getPath() );	
				makeIcon();
		}

		// ---------------------
	
		void makeIcon() {

				int lWidth  = cImg.getIconWidth();
				int lHeight = cImg.getIconHeight();
				
				if(  lWidth > 32  || lHeight > 32 ) {
						if( lWidth >  lHeight )
								cIcon = new ImageIcon( cImg.getImage().getScaledInstance( 32, -1,
																																					Image.SCALE_FAST));
						else
								cIcon = new ImageIcon( cImg.getImage().getScaledInstance( -1, 32,
																																					Image.SCALE_FAST));										
				}
				else 
						cIcon =  cImg; 						
		}
		// ---------------------
		public 	Dessin( Dessin pSrc, boolean pInNode ){
				super( "copy" );

				this.copyDessin( pSrc, pInNode );
		}
		// ---------------------
		public void 	copyDessin( Dessin pSrc, boolean pInNode ){
				setName( "copy" );

				super.copy( pSrc, pInNode );


				cFile = pSrc.cFile;
				cMyProject = pSrc.cMyProject;
				
				cImg =  pSrc.cImg;	
				cIcon = pSrc.cIcon;						
		}
		 
		// ---------------------
		final String cStrProperties  = "Properties" ;
		final String cStrEdit        = "Edit";
		final String cStrDelete      = "Delete" ;
		// ---------------------

		public boolean initMenu(JComponent pMenu){
				
				super.initMenu( pMenu );

				JMenuItem lItem;
				pMenu.add( (lItem=new JMenuItem( cStrProperties )));
				lItem.addActionListener(this);

				pMenu.add( (lItem=new JMenuItem( cStrEdit  )));
				lItem.addActionListener(this);

				pMenu.add( new JSeparator() );

				pMenu.add( (lItem=new JMenuItem( cStrDelete )));
				lItem.addActionListener(this);

				return true;
		}
		//---------------------
		public void actionPerformed( ActionEvent pEv ){		

				if( pEv.getActionCommand().equals( cStrDelete )) {
						cMyNode.removeFromParent();
				} else if(  pEv.getActionCommand().equals( cStrProperties )) {
						Dessin lCopy = new Dessin( this, false );
						DialogDessin lDiagDessin = new DialogDessin( lCopy );				 
						if( lDiagDessin.isValid()  ){
								this.copy( lCopy, true );
								FrameProject.NodeChanged( this );
						} 
				} else if(  pEv.getActionCommand().equals( cStrEdit )) {

						System.out.println( "call PPgFrameEdImg " );
						
						//			if( cMyEdit == null ) {

						PPgMain.MakeImageInstance(  this, toString(), cImg, cFile );

								//		}
						// else
						//cMyEdit.toFront();
				}										
		}

		//------------------------------------------------
		// Si l'image a changé via l'editeur

		public void  actionImgChanged( ImageIcon pNewImg ) {

				cImg =  pNewImg;
				
				makeIcon();
				FrameProject.sTreeModel.nodeChanged( getMyNode() );

				// Notifier le tree d'un changement !
		}
		//---------------------
		public void actionEditorClose(){
				//	cMyEdit = null;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public boolean saveChild( Properties pProp, String pFather ) {

				String lName = pFather + '.' +toString();

				pProp.setProperty( lName, cFile.getPath());

				return true;
		}
		//------------------------------------------------
		public final static String TAG_XML_IMG     = "Image";

		public boolean saveChildsXml( Document pDoc, Element pMother  ){
				

				Element lElt = pDoc.createElement( TAG_XML_IMG );
				pMother.appendChild( lElt );
				
				lElt.setAttribute( PPgXmlTag.TAG_XML_NAME,  toString() );
				lElt.setAttribute( PPgXmlTag.TAG_XML_PATH,  cFile.getPath() );


				return super.saveChildsXml( pDoc, pMother );	
		}


}
//*************************************************
