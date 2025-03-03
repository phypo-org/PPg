package org.phypo.PPgGame3d;

import java.util.*;


import com.jogamp.opengl.*;


//*************************************************
public class Leaf3d extends  NodeBase{


		ModelBase       cMyModel;   // the geometric object ( cube, sphere, mesh ... )

		//------------------------------------------------
		public TransformBase getNewTransf() { cMyTransf = new Transform3d(); return cMyTransf;}
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
		public Leaf3d( Node3d        pParent, 
									 ModelBase     pModel,
									 TransformBase pTransf,
									 Aspect3d      pAspect,
									 boolean pCompilFree ){

				this( pParent, new CompilObj( pModel, pCompilFree ), pTransf, pAspect );	
		}
		//------------------------------------------------
		public Leaf3d( ModelBase     pModel,
									 TransformBase pTransf,
									 Aspect3d      pAspect ){
				this( null, pModel, pTransf, pAspect );
		}		
		//------------------------------------------------
		public Leaf3d( ModelBase     pModel,
									 TransformBase pTransf,
									 Aspect3d      pAspect,
									 boolean pCompilFree ){
				this( null,  new CompilObj( pModel, pCompilFree ), pTransf, pAspect );
		}		
		//------------------------------------------------
		public void destroy(){
				if( cSharedNode )
						return ;

				cMyModel.destroy();		
		}
		//------------------------------------------------
		public void compilModel(){
				cMyModel = new CompilObj( cMyModel);
		}
		//------------------------------------------------
		// implementation des methodes de NodeBase

		//------------------------------------------------
		//------------------------------------------------
		@Override  public void beforeFirstTurn(){}
		@Override	 public void updateTurn( double pTimeDiff ){}
		//------------------------------------------------
		@Override
		public void renderGL( GL2 pGl ){

				//		System.out.println( "leaf3d.renderGL " );

				if( cMyModel == null )
						return ;
				

				if( cMyTransf != null ) {
						pGl.glPushMatrix();

						cMyTransf.renderGL(pGl);
				}
				
				boolean lFlagCleanAspect = false;
				if( cMyAspect != null )
						lFlagCleanAspect = cMyAspect.renderGL(pGl);
				
				cMyModel.renderGL( pGl, cMyAspect );
				
				
				if( lFlagCleanAspect )
						cMyAspect.cleanRenderGL(pGl);
				
				if( cMyTransf != null )
						pGl.glPopMatrix();
		}				
}		
//*************************************************
