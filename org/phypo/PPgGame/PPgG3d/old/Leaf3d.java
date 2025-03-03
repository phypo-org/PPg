package com.phipo.PPg.PPgG3d;

import java.util.*;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 


//*************************************************
public class Leaf3d extends  NodeBase{


		ModelBase       cMyModel;   // the geometric object ( cube, sphere, mesh ... )

		//------------------------------------------------
		void setModel(  ModelBase pModel )      { cMyModel  = pModel;  }
		//------------------------------------------------
		public Leaf3d( Node3d        pParent, 
									 ModelBase     pModel,
									 TransformBase pTransf,
									 Aspect3d      pAspect ){

				cParent   = pParent;
				cMyModel  = pModel;
				cMyTransf = pTransf;
				cMyAspect = pAspect; 
		}
		//------------------------------------------------
		public Leaf3d( ModelBase     pModel,
									 TransformBase pTransf,
									 Aspect3d      pAspect ){
				this( null, pModel, pTransf, pAspect );
		}		
		//------------------------------------------------
		// implementation des methodes de NodeBase

		//------------------------------------------------
		@Override	 public void updateTurn( double pTimeDiff ){

		}
		//------------------------------------------------
		@Override
		public void renderGL(){

				//		System.out.println( "leaf3d.renderGL " );
				callEngineRenderGL();

				if( cMyModel != null ){
				
						if( cMyTransf != null ) {
								GL11.glPushMatrix();
								cMyTransf.renderGL();
						}
						
						boolean lFlagCleanAspect = false;
						if( cMyAspect != null )
								lFlagCleanAspect = cMyAspect.renderGL();

						cMyModel.renderGL( cMyAspect );


						if( lFlagCleanAspect )
								cMyAspect.cleanRenderGL();

						if( cMyTransf != null )
								GL11.glPopMatrix();
				}
		}				
}		
//*************************************************
