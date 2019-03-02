package org.phypo.PPg.PPgJ3d;


import com.jogamp.opengl.*;

//*************************************************
abstract public class  ModelBase{

		Node3d cParent; //NE SERT SANS DOUTE A RIEN - A VIRER
		public void setParent(Node3d pParent ) { cParent = pParent ; }

		abstract public void renderGL( GL2 pGl, Aspect3d pAspect );

		static int sGlCompilListId= 10000;
		public static int GetNewCompilListId() { return sGlCompilListId++; }
		//------------------------------------------------
		public void destroy(){;}
		public void destroy(GL2 pGl){;}

}
//*************************************************

