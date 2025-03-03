package com.phipo.PPg.PPgG3d;



//*************************************************
abstract public class  ModelBase{

		Node3d cParent;
		public void setParent(Node3d pParent ) { cParent = pParent ; }

		abstract public void renderGL( Aspect3d pAspect );

		static int sGlCompilListId= 10000;
		public static int GetNewCompilListId() { return sGlCompilListId++; }




}
//*************************************************

