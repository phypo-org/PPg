package com.phipo.PPg.PPgG3d;



import java.util.*;



//*************************************************
abstract public class NodeBase{

		TransformBase  cMyTransf;
		Aspect3d       cMyAspect;  // the appareance of the object (color ...)

		//------------------------------------------------
		public void setTransf( TransformBase pTransf ){ cMyTransf = pTransf;}
		public TransformBase getTransf()              { return cMyTransf;}
		public void     setAspect( Aspect3d pAspect ) { cMyAspect = pAspect;}		
		public Aspect3d getAspect()                   { return cMyAspect;}
		//------------------------------------------------

		abstract public void updateTurn( double pTimeDiff );
		abstract public void renderGL();

		public void callEngineUpdateTurn( double pTimeDiff ){; }
		public void callEngineRenderGL(){;}
 
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

						cParent.removeChild( this );						
				}
				cParent = pParent;

				return true;
		}
}
//*************************************************



