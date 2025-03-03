package org.phypo.PPgGame.PPgG3d;



import org.lwjgl.opengl.*;

//*************************************************
public class Object3d extends  ModelBase {


		Facet cFacets[];

		public Facet[] getFacets() { return cFacets; }

		//------------------------------------------------
		public Object3d(){
		}

		//------------------------------------------------
		public Object3d( Facet pFacets[] ) {

				setFacets( pFacets );
		}
		//------------------------------------------------
		protected void cpyFacets( Facet pFacets[] ){

				for( int i = 0; i< pFacets.length; i++ ){
						cFacets[i] = new Facet( pFacets[i] );
				}				
		}
		//------------------------------------------------
		public void setFacets( Facet pFacets[] ){

				cFacets = new Facet[ pFacets.length ];
				
				cpyFacets(  pFacets );
		}
		//------------------------------------------------
		public void addFacets( Facet pFacet ){

				Facet[] cOldFacets  = cFacets;
				cFacets = new Facet[ cFacets.length+1  ];
				
				cpyFacets( cOldFacets );

				cFacets[ cOldFacets.length ] = new Facet( pFacet );
		}
		//------------------------------------------------
		public void addFacets( Facet pFacets[] ){

				Facet [] cOldFacets =cFacets;
				cFacets = new Facet[ cOldFacets.length + pFacets.length ];
				
				cpyFacets( cOldFacets  );

				for( int i = 0; i < pFacets.length; i++){
						cFacets[cOldFacets.length+i] = new Facet( pFacets[i] );
				}
		}
		
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public void renderGL( Aspect3d pAspect ){

				if( pAspect != null ){
				}

				for( int i=0; i< cFacets.length; i++ ){
						cFacets[i].renderGL( pAspect );
				}
		}

}

//*************************************************
