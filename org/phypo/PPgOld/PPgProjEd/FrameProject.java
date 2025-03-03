package org.phypo.PPg.PPgProjEd;


import java.awt.BorderLayout;

import java.awt.*;
import java.util.*;


import javax.swing.ImageIcon;


import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.util.regex.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.awt.event.*;

import org.phypo.PPg.PPgWin.*;

//*************************************************
class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		
    Icon cProjectIcon = null;
		Icon cFolderPixmapIcon  = null;
		Icon cFolderAnimIcon = null;
		Icon cPixmapIcon  = null;
		Icon cAnimIcon    = null;
		
    public MyTreeCellRenderer() {
				
				 cProjectIcon       = new ImageIcon("icones/Project.png");
				 cFolderPixmapIcon  = new ImageIcon ("icones/DossierImg.png");
				 cFolderAnimIcon    = new ImageIcon("icones/DossierAnim.png");
				 cPixmapIcon        = new ImageIcon("icones/Dessin.png");
				 cAnimIcon          = new ImageIcon("icones/Anim.png");
		}
		
		
		//------------------------------------------------
		
    public Component getTreeCellRendererComponent(	JTree tree,
																										Object pValue,
																										boolean sel,
																										boolean expanded,
																										boolean leaf,
																										int row,
																										boolean hasFocus) {
        super.getTreeCellRendererComponent( tree, pValue, sel,
																						expanded, leaf, row,
																						hasFocus);
				
				ObjBase lObjBase = (ObjBase)((DefaultMutableTreeNode)pValue).getUserObject();
				
				if( lObjBase.getClass() == GrpAnim.class ) {
						setIcon(cFolderAnimIcon);
						setToolTipText("Animation folder");
				}
				else if( lObjBase.getClass() == GrpImg.class ) {						
						setIcon(cFolderPixmapIcon );
						setToolTipText("images folder");
				}				
				else 	if( lObjBase.getClass() == Project.class ) {
						setIcon(cProjectIcon );
						setToolTipText("Project");
				}
				else {
						System.out.println( "*** getTreeCellRendererComponent autre");
						
						if( lObjBase.getIcon() != null )
								setIcon( lObjBase.getIcon());
						if( lObjBase.getToolTipText() != null )
								setToolTipText(lObjBase.getToolTipText());		
						else
								setToolTipText(null); //no tool tip        
				}
				
        return this;
    }
}				


//*************************************************
public class FrameProject extends PPgFrameChild  
		implements MouseListener, TreeModelListener {

	
		JTree       cTree;
		public JTree getTree() { return cTree; }
		
		JScrollPane cTreeView;

		static DefaultTreeModel       sTreeModel = null;
		static public DefaultTreeModel GetTreeModel() { return sTreeModel; }

		static public DefaultMutableTreeNode cRoot = new DefaultMutableTreeNode( new ObjBase( "Root" )   );
		//------------------------------------------------
		static public void NodeChanged( ObjBase pObj ){
				sTreeModel.nodeChanged( pObj.getMyNode() );
		}
		//------------------------------------------------

		public FrameProject() {
				super( "Projects" );

				setSize( 600, 1000 );
				setLocation( 0, 0 );

				sTreeModel = new DefaultTreeModel( cRoot );
				sTreeModel.addTreeModelListener( this );

				cTree = new JTree( sTreeModel );
				//			cTree.setRootVisible( false );
        cTree.setCellRenderer(new MyTreeCellRenderer());

				JScrollPane cTreeView = new JScrollPane(cTree);

				getContentPane().add( cTreeView,  BorderLayout.CENTER );

				pack();
				setVisible(true);		

				cTree.addMouseListener( this );
		}

		//------------------------------------------------

		public boolean addProject( Project pProject ){
				
				//	pProject.addToParentNode(cRoot );
								
			//				cTree.setSelectionPath( new TreePath(new Object[] {cRoot.toString(), pProject.toString() } ) );

				pProject.attachToTree();

				System.out.println( "addProject end " );
				return true;
		}
		//------------------------------------------------
		public void deleteProject( Project pProject ){

				sTreeModel.removeNodeFromParent( pProject.getMyNode() );
		}
		//------------------------------------------------
		//--------- TreeModelListener --------------------
		//------------------------------------------------
		//Invoked after a node (or a set of siblings) has changed in some way.
		public void 	treeNodesChanged(TreeModelEvent e){;}
		//Invoked after nodes have been inserted into the tree.
		public void 	treeNodesInserted(TreeModelEvent e){;}
		//Invoked after nodes have been removed from the tree.
		public void 	treeNodesRemoved(TreeModelEvent e){;}
		//Invoked after the tree has drastically changed structure from a given node down.
		public void 	treeStructureChanged(TreeModelEvent e){;}

		//------------------------------------------------
		//----------- MouseListener ----------------------
		//------------------------------------------------

		public void 	mouseClicked(MouseEvent e) {
				

				int selRow = cTree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = cTree.getPathForLocation(e.getX(), e.getY());

				System.out.println( "mouseClicked on tree row :" + selRow );
				
				if( SwingUtilities.isRightMouseButton( e ) == true 
						&& selRow != -1 
						&& e.getClickCount() == 1) {
						
						System.out.println( "mouseClicked on tree menu "  );
						
						ObjBase lObjEd = (ObjBase)((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject();

						if( lObjEd != null ) {
								System.out.println( "mouseClicked on tree lObjEd " + toString() );
										JPopupMenu lPopmenu = new JPopupMenu();							

								if( lObjEd.initPopup( lPopmenu ) != null){															
										lPopmenu.show( e.getComponent(), e.getX(), e.getY() );												
								}															
						}	
						/*
							else if(e.getClickCount() == 2) {
							myDoubleClick(selRow, selPath);
							}
						*/
				}
		}
		public void 	mouseEntered (MouseEvent e){;}
		public void 	mouseExited  (MouseEvent e){;}
		public void 	mousePressed (MouseEvent e){;}
		public void 	mouseReleased(MouseEvent e){;}
		//------------------------------------------------
}
//*************************************************
