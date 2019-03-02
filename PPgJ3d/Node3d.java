package org.phypo.PPg.PPgJ3d;

import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.gl2.*;


//*************************************************
public class Node3d extends  NodeBase{

		ArrayList<NodeBase> cChilds = new ArrayList();

		public ArrayList<NodeBase> getChilds() { return cChilds; }

		//------------------------------------------------
		public TransformBase getNewTransf() { cMyTransf = new Transform3d(); return cMyTransf;}
		//------------------------------------------------
		public void addChild( NodeBase pNode ) {

				if( pNode.setParent( this ) )
						cChilds.add( pNode );
		} 		
		//------------------------------------------------
		public void addChild( NodeBase p1, NodeBase p2 ) {
				addChild( p1 );
				addChild( p2 );
		} 		
		//------------------------------------------------
		public void addChild( NodeBase p1, NodeBase p2, NodeBase p3 ) {
				addChild( p1 );
				addChild( p2 );
				addChild( p3 );
		} 				
		//------------------------------------------------
		public void removeChild( NodeBase pChild ){
				
				if( pChild.getParent() == this){
						
						cChilds.remove( pChild );	
						pChild.setParent(  null );
			}
		}
		//------------------------------------------------
		public void removeAnDestroyChild( NodeBase pChild ){
				
				if( pChild.getParent() == this){
						
						cChilds.remove( pChild );	
						pChild.setParent(  null );
						pChild.destroy();
				}
		}
		//------------------------------------------------
		public void destroy(){
				if( cSharedNode )
						return ;

				for( NodeBase lNode: cChilds ){
						lNode.destroy();
				}				
		}

		//------------------------------------------------

		public Node3d(){
		}
		public Node3d(NodeBase pNode){
				addChild( pNode );
		}
		public Node3d(NodeBase p1, NodeBase p2){
				addChild( p1 );
				addChild( p2 );
		}
		public Node3d(NodeBase p1, NodeBase p2, NodeBase p3 ){
				addChild( p1 );
				addChild( p2 );
				addChild( p3 );
		}
		//------------------------------------------------
		@Override
				public void beforeFirstTurn(  ){

				if( cChilds != null ){
						for( NodeBase lNode: cChilds ){
								lNode.beforeFirstTurn();
						}
				}
		}
		//------------------------------------------------
		@Override
				public void updateTurn( double pTimeDiff ){
				
				//				System.out.println( "Node3d.updateTurn  pTimeDiff : " + pTimeDiff );
				
				if( cChilds != null ){
						for( NodeBase lNode: cChilds ){
								lNode.updateTurn( pTimeDiff );
						}
				}
		}
		//------------------------------------------------
		@Override
		public void renderGL( GL2 pGl ){

				//				System.out.println( "Node3d.renderGL " + (cChilds==null? " null" : cChilds.size()) );

				if( cChilds != null && cChilds.size() > 0 ){
				
						if( cMyTransf != null ){
								pGl.glPushMatrix();
								cMyTransf.renderGL(pGl);
						}

						for( NodeBase lNode: cChilds ){
								lNode.renderGL(pGl);								
						}

						if( cMyTransf != null )
								pGl.glPopMatrix();					 						
				}
		}
		//------------------------------------------------
		@Override	 public void userCall( double lVal ){
				if( cChilds != null ){
						for( NodeBase lNode: cChilds ){
								lNode.userCall( lVal );
						}
				}
		}
}
//*************************************************
