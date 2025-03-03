package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.PPgRandom;

//*************************************************

public interface ParticleFactoryInterface {

	public 	ParticleInterface newInstance( ParticleEngine pMyEngine, PPgRandom pRand, int lNum );

};

//*************************************************
