package org.phypo.PPg.PPgProjEd;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class Project extends ObjBase implements PPgInterfaceXmlNodeHandler{


		public 	Project( String pName ){
				super( pName );
				
				init();
		}
		// ---------------------
		public 	Project( String pName, File pFile ){
				super( pName );
				cFile = pFile;
				init();
		}
		// ---------------------


		public GrpImg  cGrpImg = null;
		public GrpAnim cGrpAnim = null;


		void init(){


				cGrpImg = new GrpImg( "lmages", this );
				
				cGrpAnim = new GrpAnim( "Animations", this ) ;
		}
		//------------------------------------------------
		public void attachToTree(){

				addToParentNode(FrameProject.cRoot );
				cGrpImg.addToParentNode( this );
				cGrpAnim.addToParentNode( this );

			}
		//------------------------------------------------

		final String cStrSave         = "Save" ;
		final String cStrSaveAs       = "Save as ..." ;
		final String cStrDelete       = "Delete" ;
		final String cStrProperties   = "Properties" ;

		File  cFile = null;

		// ---------------------

		public boolean initMenu(JComponent pMenu){
				
				super.initMenu( pMenu );

				JMenuItem lItem;

				pMenu.add( (lItem=new JMenuItem( cStrSave )));
				lItem.addActionListener(this);
				pMenu.add( (lItem=new JMenuItem( cStrSaveAs )));
				lItem.addActionListener(this);
				
				pMenu.add( (lItem=new JMenuItem( cStrDelete )));
				lItem.addActionListener(this);
				pMenu.add( new JSeparator() );

				pMenu.add( (lItem=new JMenuItem( cStrProperties )));
				lItem.addActionListener(this);

				return true;
		}
		
		//---------------------
		public void actionPerformed( ActionEvent pEv ){		
				
				if( pEv.getActionCommand().equals( cStrDelete )) {
						
						PPgProjEd.sThePPgProjEd.deleteProject( this );
				} else if( pEv.getActionCommand().equals( cStrProperties )) {
						DialogProject lDiagProj = new DialogProject( this );						
						// prendre en compte les modifs ou non
				} else if( pEv.getActionCommand().equals( cStrSave )) {
						if( cFile == null ){
								saveAs();
						} else saveXml( cFile );
				} else if( pEv.getActionCommand().equals( cStrSaveAs )) {
						saveAs();
				}							 
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public boolean saveAs() {
				int lReturnVal = PPgProjEd.sFileChooser.showDialog( PPgProjEd.sThePPgProjEd,
																					 "Save project " + toString() );
				if( lReturnVal == JFileChooser.APPROVE_OPTION) {
						
						cFile = PPgProjEd.sFileChooser.getSelectedFile();
						return saveXml( cFile );
				} 
				return false;
		}
	

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		static final String cProjectName = "Project.Name";
	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static Project LoadProjectXml( File pFile ){
				
				PPgXml lXml = new PPgXml();

				Project lProject=new Project("");
				if( lXml.processFile( pFile, lProject ) ) {
						return lProject;
				}
				return null;
		}		
		//------------------------------------------------
		public boolean processNode( PPgXml pXml, Node pMotherNode, Node pNode, PPgInterfaceXmlNodeHandler pHandler) {
				
				if( pNode.getNodeName().equals( PPgXmlTag.TAG_XML_PROJECT) ) {

						// recuperation du nom du projet
						String lName = PPgXml.GetAttributeVal( null, pNode,  PPgXmlTag.TAG_XML_NAME );
						if( lName != null )
								setName( lName );
						else
								return false;
				} else if( pNode.getNodeName().equals( GrpImg.TAG_XML_GRP_IMG) ) {
						// rien a faire
				} else if( pNode.getNodeName().equals( Dessin.TAG_XML_IMG) ) {
						String lName = PPgXml.GetAttributeVal( null, pNode,  PPgXmlTag.TAG_XML_NAME );				
						String lPath = PPgXml.GetAttributeVal( null, pNode,  PPgXmlTag.TAG_XML_PATH );

						if( lName == null || lPath == null ) {
								return false;
						}
						
						Dessin lDessin = new Dessin( lName, new File(lPath), this );
						lDessin.addToParentNode( cGrpImg  );								
				}
				// meme chose pour GrpAnim
				

				return pXml.processNodeChilds( pNode, pHandler ); // on pourrait aussi appeler PPgXml.processNode( pXml, pNode, pHandler ); plus couteux	
		}
	//------------------------------------------------

		public boolean saveXml( File pFile ) {
				

				System.out.println( "saveXml " + pFile.getPath() );

				PPgXml lXml = new PPgXml();
				Document lDoc = lXml.getNewDoc();
				

				Element lEltProj = lDoc.createElement( PPgXmlTag.TAG_XML_PROJECT );
				lDoc.appendChild( lEltProj );

				lEltProj.setAttribute( PPgXmlTag.TAG_XML_NAME,  toString() );

				if( super.saveChildsXml( lDoc, lEltProj ) ){
						return lXml.writeDocToFile( lDoc, pFile );
				}
				return false;
		}
}
//*************************************************
