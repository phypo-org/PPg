package org.phypo.PPgGame.PPgG3d;



import org.lwjgl.opengl.*;

//*************************************************
public class CompilObj extends  ModelBase {


		int cMyGlCompilListId = -1; // On pourrait avoir un object specialise avec un compteur d'utilisateur !

		ModelBase cModel;
		public void forgetModel(){ cModel = null;}
		boolean cFreeOnDestruction = false;
		//------------------------------------------------
		public CompilObj(){
		}
		//------------------------------------------------
		public CompilObj( ModelBase pModel, boolean lCompil, Aspect3d pAspect ){

				cModel =pModel;
				if( lCompil )
						compilInGlListe( pModel,	pAspect );

				cFreeOnDestruction = true;
		}
		//------------------------------------------------
		public CompilObj( int pGlCompilId, boolean pFreeOnDestruction ){
				cMyGlCompilListId = pGlCompilId;
				cFreeOnDestruction = pFreeOnDestruction;
		}
		//------------------------------------------------
		public CompilObj( int pGlCompilId  ){
				cMyGlCompilListId = pGlCompilId;				
		}
		//------------------------------------------------
		public void destroy(){
				if( cFreeOnDestruction )
						freeGlList();
		}
		//------------------------------------------------
		public void freeBeginRecord(){
				cMyGlCompilListId = GetNewCompilListId();
				GL11.glNewList( cMyGlCompilListId, GL11.GL_COMPILE );

		}
		//------------------------------------------------
		public void freeEndRecord(){

				GL11.glEndList( );			 						
		}
		//------------------------------------------------
		public void compilInGlListe( ModelBase pModel, Aspect3d pAspect ){

				if( cMyGlCompilListId != -1 )
						freeGlList();
				else 
						cMyGlCompilListId = GetNewCompilListId();

				GL11.glNewList( cMyGlCompilListId, GL11.GL_COMPILE );			 
				
				pModel.renderGL( pAspect );

				GL11.glEndList( );			 						
		}
		//------------------------------------------------
		public int shareGlListId(){
				cFreeOnDestruction = false;

				return cMyGlCompilListId;
		}
		//------------------------------------------------
		public int unshareGlListId(){
				cFreeOnDestruction = true;

				return cMyGlCompilListId;
		}
		//------------------------------------------------
		public int getGlListId(){
				return cMyGlCompilListId;
		}
		//------------------------------------------------
		void freeGlList(){
				if( cMyGlCompilListId == -1 )	
						GL11.glDeleteLists( cMyGlCompilListId, 1);

				cMyGlCompilListId = -1;
		}	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public void renderGL( Aspect3d pAspect ){
								
				if( cMyGlCompilListId == -1  
						&& cModel != null ) {
						compilInGlListe( cModel, pAspect );
				} else {
						
						if( pAspect != null ){
						}
				}


				if( cMyGlCompilListId != -1 ){
						
						GL11.glCallList( cMyGlCompilListId );
				}
		}
		//------------------------------------------------
}
