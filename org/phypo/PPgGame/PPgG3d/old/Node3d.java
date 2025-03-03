package com.phipo.PPg.PPgG3d;

import java.util.*;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 


//*************************************************
public class Node3d extends  NodeBase{

		ArrayList<NodeBase> cChilds = new ArrayList();

		public ArrayList<NodeBase> getChilds() { return cChilds; }

		//------------------------------------------------
		//------------------------------------------------
		public void addChild( NodeBase pNode ) {

				if( pNode.setParent( this ) )
						cChilds.add( pNode );
		} 		
		//------------------------------------------------
		public void removeChild( NodeBase pChild ){
				
				if( pChild.getParent() == this){
						
						cChilds.remove( this );						
						pChild.setParent( null );
				}
		}
		//------------------------------------------------
		//------------------------------------------------

		public Node3d(){
		}
		//------------------------------------------------
		@Override
		public void updateTurn( double pTimeDiff ){

				//				System.out.println( "Node3d.updateTurn  pTimeDiff : " + pTimeDiff );
				
				callEngineUpdateTurn( pTimeDiff );
				
				if( cChilds != null ){
						for( NodeBase lNode: cChilds ){
								lNode.updateTurn( pTimeDiff );
						}
				}
		}
		//------------------------------------------------
		@Override
		public void renderGL(){

				//				System.out.println( "Node3d.renderGL " + (cChilds==null? " null" : cChilds.size()) );

				callEngineRenderGL();

				if( cChilds != null && cChilds.size() > 0 ){
				
						if( cMyTransf != null ){
								GL11.glPushMatrix();
								cMyTransf.renderGL();
						}

						for( NodeBase lNode: cChilds ){
								lNode.renderGL();								
						}

						if( cMyTransf != null )
								GL11.glPopMatrix();					 						
				}
		}

}
//*************************************************
