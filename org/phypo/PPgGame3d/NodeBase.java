package org.phypo.PPgGame3d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.*;


import java.util.*;



//*************************************************
abstract public class NodeBase{

		TransformBase  cMyTransf;
		Aspect3d       cMyAspect;  // the appareance of the object (color ...)

		//------------------------------------------------
		public void setTransf( TransformBase pTransf ){ cMyTransf = pTransf;}
		public TransformBase getTransf()              { return cMyTransf;}
		abstract public TransformBase getNewTransf();
		public void     setAspect( Aspect3d pAspect ) { cMyAspect = pAspect;}		
		public Aspect3d getAspect()                   { return cMyAspect;}
		//------------------------------------------------

		abstract public void beforeFirstTurn(  );
		abstract public void updateTurn( double pTimeDiff );
		abstract public void renderGL( GL2 pGl );
 		         public void userCall( double lVal ){}

		//------------------------------------------------
		Node3d         cParent;
		//------------------------------------------------
		public Node3d getParent() { return cParent; };
		//------------------------------------------------
		public boolean setParent( Node3d pParent ){

				if( cParent != null ){

						if( cParent == pParent ){
								return false;
						}
						Node3d lTmpParent = cParent;
						cParent = null;
						lTmpParent.removeChild( this );						
				}
				cParent = pParent;

				return true;
		}
		//------------------------------------------------
		
		//------------------------------------------------

		boolean cSharedNode = false;
		public boolean isSharedNode() { return cSharedNode; }
		public void setSharedNode( boolean pShared ) { cSharedNode = pShared;}
		
		public void destroy(){;}
}
//*************************************************



