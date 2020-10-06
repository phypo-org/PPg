package org.phypo.PPg.PPgData;


//*************************************************

public class AttributPool {

		public AttributProto [] cAttributProto = null; // Vers un modele
		public Attribut  [] cAttributInit  = null; // vers les valeur par defaut


		
		public Attribut []  cAttribut = new Attribut [AttributProto.MAX_ATTRIBUT];

		
		AttributPool( AttributProto [] pVectProto,  Attribut  [] pVectInit ){
				cAttributProto = pVectProto;
				cAttributInit  = pVectInit;
				
				for( int i=0; i< AttributProto.sNbAttribut; i++ ) {
						if( cAttributProto[i] != null )
								cAttribut[i] = new Attribut( cAttributInit[i] );
						else
								cAttribut[i] = null;	
				}
		}
}

//*************************************************
