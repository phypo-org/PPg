package org.phypo.PPgTestGame3d;

import java.util.*;

import org.phypo.PPgGame3d.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.opengl.util.texture.Texture;


//*************************************************

public class FactoryLevel {

		public static FactoryLevel sTheFactoryLevel = null;

		int cDifficulty = 0;

		int cLevel = 0;
		LevelBase cCurrentLevel = null;

		static public Texture sTextureMars;
		static public Texture sTextureWater;

		//------------------------------------------------		
		FactoryLevel(  int pDifficulty ) {
				
				init( pDifficulty );

				sTheFactoryLevel = this;
		}
		//------------------------------------------------		
		static public boolean LoadGlobalTextures( GL2  pGL ){

				System.out.println("FactoryLevel.loadGlobalTextures");


				Engine lEngine = World3d.Get().getEngine();

				if( (sTextureMars = lEngine.loadTexturePng( pGL, "Textures/39.png")) == null)
						return false;

				if( (sTextureWater = lEngine.loadTexturePng( pGL, "Textures/48.png")) == null)
						return false;

				return true;
		}
		//------------------------------------------------		
		public void init( int pDifficulty ) { 
				
				cDifficulty = pDifficulty;
		}		
		//------------------------------------------------		
		LevelBase setLevel( int pLevel ) {

				switch( pLevel ) {
						
				case 0 :  cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 60, 0.3f, 0 ); break;
				case 1 :  cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 60, 0.3f, 1 ); break;
				case 2 :  cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 60, 0.3f, 2 ); break;
				case 3 :  cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 60, 0.3f, 3 ); break;
			default : cCurrentLevel = new LevelTest    (	"Divers",      EnumFaction.Green, 60, 0.3f, 0  ); 
									 // Mettre un niveau de fin !!!
				}

				return cCurrentLevel;
		}
		//------------------------------------------------		
		public LevelBase  setBeginLevel(int pBegin) {

				cLevel = pBegin;

				return setLevel( cLevel );
		}
		//------------------------------------------------		
		public LevelBase getCurrentLevel() { 
				if( cCurrentLevel == null )
						return setLevel( cLevel );

				return cCurrentLevel;
		}
		//------------------------------------------------		
		public LevelBase  nextLevel()  { 
				cLevel += 1; 
				
				return setLevel(cLevel);				
		}
}

//*************************************************
