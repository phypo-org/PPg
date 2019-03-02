package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;


//*************************************************
public class CompilObj extends  ModelBase {


		int cMyGlCompilListId = -1; // On pourrait avoir un object specialise avec un compteur d'utilisateur !

		ModelBase cModel = null;
		public void forgetModel(){ cModel = null;}
		boolean cFreeOnDestruction = true;

		public static int sNbCompilObjectInUse=0;
		//------------------------------------------------
		public CompilObj(){
		}

		//------------------------------------------------
		/*
		public CompilObj( ModelBase pModel ){

				cModel =pModel;

				cFreeOnDestruction = true;
		}
		*/
		//------------------------------------------------
		public CompilObj( ModelBase pModel ){

				cModel = pModel;

				// bug		if( Engine.sTheGl != null )
				// bug				compilInGlListe( Engine.sTheGl, pModel, pAspect );

				cFreeOnDestruction = true;
		}
		//------------------------------------------------
		public CompilObj( ModelBase pModel, boolean pFree ){

				cModel = pModel;

				// bug		if( Engine.sTheGl != null )
				// bug				compilInGlListe( Engine.sTheGl, pModel, pAspect );

				cFreeOnDestruction = pFree;
		}
		//------------------------------------------------
		public CompilObj( int pGlCompilId, boolean pFreeOnDestruction ){
				cMyGlCompilListId = pGlCompilId;
				cFreeOnDestruction = pFreeOnDestruction;
		}
		//------------------------------------------------
		public CompilObj( int pGlCompilId  ){
				cMyGlCompilListId = pGlCompilId;				
				cFreeOnDestruction = false;
		}
		//------------------------------------------------
		public void destroy(GL2 pGl){
				if( cFreeOnDestruction ){
						freeGlList(pGl);
				}
		}
		//------------------------------------------------
		public void destroy(){
				// Probleme on a pas de GL2 pour appeler le vrai destroy()
				if( cFreeOnDestruction ){
						World3d.sTheWorld.addModelToDeleteList( this );
				}
		}
		//------------------------------------------------
		public void freeBeginRecord(GL2 pGl ){
				cMyGlCompilListId = GetNewCompilListId();
				pGl.glNewList( cMyGlCompilListId, GL2.GL_COMPILE );

		}
		//------------------------------------------------
		public void freeEndRecord( GL2 pGl){

				pGl.glEndList( );			 						
		}
		//------------------------------------------------
		public void compilInGlListe( GL2 pGl, ModelBase pModel, Aspect3d pAspect ){

				
				if( cMyGlCompilListId != -1 )
						freeGlList( pGl);

				cMyGlCompilListId = GetNewCompilListId();

				pGl.glNewList( cMyGlCompilListId, GL2.GL_COMPILE );			 
				
				pModel.renderGL( pGl, pAspect );

				pGl.glEndList( );			 	

				
				sNbCompilObjectInUse++;
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
		void freeGlList(GL2 pGl ){
				if( cMyGlCompilListId != -1 )	// phipo 20130930 == -1
						{
								sNbCompilObjectInUse--;
								pGl.glDeleteLists( cMyGlCompilListId, 1);
						}

				cMyGlCompilListId = -1;
		}	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public void renderGL(  GL2 pGl, Aspect3d pAspect ){


				if( cMyGlCompilListId == -1 ){
						if(  cModel != null ) {
								compilInGlListe( pGl, cModel, pAspect );
						} else {								
						}
				}
				
				if( cMyGlCompilListId != -1 ){
						pGl.glCallList( cMyGlCompilListId );
				}
				//				Color4.Black.glEmission( pGl );			

		}
		//------------------------------------------------
}
