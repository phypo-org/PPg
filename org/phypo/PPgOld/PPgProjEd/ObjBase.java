package org.phypo.PPg.PPgProjEd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

import javax.swing.*;
import javax.swing.tree.*;

import java.util.Properties;
import java.util.*;




import org.w3c.dom.*;


//*************************************************
public class ObjBase  
	implements ActionListener{

		String cName;
		public ObjBase( String pName ){
				cName = pName;
				cMyNode= new DefaultMutableTreeNode( this);
		}


		public void copy( ObjBase pSrc, boolean pInNode ){
				cName = pSrc.cName;
				cMyNode = pSrc.cMyNode;

				if( pInNode ) {
						cMyNode.setUserObject( this  );
				}
		}

		public String toString()            { return cName; }
		public void   setName( String pName ) { cName = pName; }



		public boolean isValid()            { return true; }


		DefaultMutableTreeNode cMyNode = null;
		public DefaultMutableTreeNode getMyNode() { return cMyNode; }
				
	  public ImageIcon getIcon()    { return null; }
		public String    getToolTipText() { return null ;}
		//------------------------------------------------		
		public TreePath getPath(){
				if( getMyNode() != null )
						return new TreePath( getMyNode().getPath() );
				
				return null;
		}
		//------------------------------------------------		
		public DefaultMutableTreeNode addToParentNode( DefaultMutableTreeNode pParent ) {
				
				FrameProject.GetTreeModel().insertNodeInto( cMyNode, pParent, 0 );
				cMyNode.setUserObject( this );
				return cMyNode;
		}

		public DefaultMutableTreeNode addToParentNode( ObjBase pParent ) {
				
				FrameProject.GetTreeModel().insertNodeInto( cMyNode, pParent.cMyNode, 0 );

				cMyNode.setUserObject( this );
				return cMyNode;
		}

		// ---------------------
		public boolean initMenu(JComponent pMenu){ return false;}

		public	JPopupMenu initPopup(JPopupMenu pPopup){		
				if( initMenu( pPopup  ) ) {
						pPopup.pack();
						return pPopup ;
				}
				return null;
		}
		//---------------------
		public void actionPerformed( ActionEvent p_e ){		
		}
	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public boolean saveChild( Properties pProp, String pFather ) {
								
				Enumeration<DefaultMutableTreeNode> lChilds =  getMyNode().children();
				
				while( lChilds.hasMoreElements() ){
						
						DefaultMutableTreeNode lChild = lChilds.nextElement();
						if( pFather == null )
								((ObjBase)lChild.getUserObject()).saveChild( pProp,  toString() );
						else
								((ObjBase)lChild.getUserObject()).saveChild( pProp, pFather + '.' + toString() );
				}
				return true;
		}
		//------------------------------------------------
		public boolean loadChild( Properties pProps, ObjBase pFather ) {
								
				Enumeration<DefaultMutableTreeNode> lChilds =  getMyNode().children();				
				while( lChilds.hasMoreElements() ){						
						DefaultMutableTreeNode lChild = lChilds.nextElement();
						((ObjBase)lChild.getUserObject()).loadChild( pProps, pFather );
				}
				return true;
		}
		//------------------------------------------------
		public boolean saveChildsXml( Document pDoc, Element pMother ){
				
				Enumeration<DefaultMutableTreeNode> lChilds =  getMyNode().children();
				
				while( lChilds.hasMoreElements() ){
						
						DefaultMutableTreeNode lChild = lChilds.nextElement();
						if( ((ObjBase)lChild.getUserObject()).saveChildsXml( pDoc, pMother ) == false )
								return false;
				}
				return true;				
		}
}			

//*************************************************
