package org.phypo.PPg.PPgProjEd;


import java.awt.event.*;


import javax.swing.tree.*;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.*;


import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgWin.*;

//*************************************************

public class PPgProjEd extends PPgJFrame {

		static File sStrProjectFile=null;

		FrameProject cFrameProject;

		public static JFileChooser sFileChooser= new JFileChooser(".");

		public static PPgProjEd sThePPgProjEd = null; 
		//---------------------

		public PPgProjEd( boolean pDesk ){
				super( "PPgProjEd",  pDesk );
				sThePPgProjEd = this;
		}


		final String cStrNewProject       = "New project" ;
		final String cStrOpenProject      = "Open project";

		//---------------------
		public PPgInterfaceAppli getInterfaceAppli() { return this; }
		//---------------------
		protected void createMenuBar() {	

				JMenu lMenu;

				lMenu = new JMenu("File");
				addItem( lMenu, cStrNewProject );
				addItem( lMenu, cStrOpenProject );
				lMenu.add( new JSeparator() );
				cMenuBar.add(lMenu);	
		}
		//---------------------
		public void actionPerformed( ActionEvent pEv ){

				//===============
				if( pEv.getActionCommand().equals(cStrNewProject )) {

						System.out.println( "new project"  ); 
						
						Project lProject = new Project( "no_name" );
						if( lProject != null){
								DialogProject lDiagProj = new DialogProject( lProject );
								if( lDiagProj.isValid() && lProject.isValid() ){
										System.out.println( "new project ok " + lProject.toString()  ); 

											cFrameProject.addProject( lProject );
								}
						}
				}	//===============
				else 	if( pEv.getActionCommand().equals(cStrOpenProject )) {
						if( chooseProject() ){
								
								System.out.println( "open project : " + sStrProjectFile.getName() ); 
								openProject( sStrProjectFile );
						}								
				}
		}

		//---------------------		
		public Project openProject( File pFile ){
				
				
				Project lProject = Project.LoadProjectXml( pFile ) ;
				
				if( lProject.isValid() ) {
						cFrameProject.addProject( lProject );
						TreePath lPath = lProject.getPath();
						cFrameProject.getTree().expandPath( lPath );
					 
				}
				return lProject;
		}
		//---------------------
		public Project openProject( Project pProject ){
				if( pProject.isValid() ) {
						cFrameProject.addProject( pProject );
						return pProject;
				}
				return null;
		}
		//---------------------
		public void deleteProject( Project pProject ){

				cFrameProject.deleteProject( pProject );				
		}
		//---------------------
	
		//---------------------
		public void itemStateChanged(ItemEvent pEv ){
		}

		//-----------------------------
		//-----------------------------
		//-----------------------------
		 public boolean chooseProject() {

				JFileChooser   lFileChooser = new JFileChooser(".");
				FileNameExtensionFilter lFilter = new FileNameExtensionFilter( "PPgProjEd", ".ppg");
				lFileChooser.setFileFilter(lFilter);
				int lReturnVal = lFileChooser.showOpenDialog(null);
				
				if( lReturnVal== JFileChooser.APPROVE_OPTION) {
						sStrProjectFile = lFileChooser.getSelectedFile();
						return true;
				}
				else
						return false;
		}
		

		//-----------------------------
		//-----------------------------
		//-----------------------------
		public static void main(String[] args) {

				
				
				System.out.println( "main 1"  ); 
				PPgProjEd lEditor = new PPgProjEd( true );

				System.out.println( "main 2"  ); 
				lEditor.setVisible(true);
				lEditor.createMenuBar();


				lEditor.addChild( (lEditor.cFrameProject = new FrameProject()));


				Project lProject = new Project( "Test1" );

				
				lEditor.openProject( lProject );	

				lEditor.openProject( new File("Test1.xml") );			 		
		}

};



//*************************************************

