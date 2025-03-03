package org.phypo.PPgGame3d;



import org.phypo.PPg.PPgMath.*;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.*;

//*************************************************

public interface ParticleInterface  {


		public boolean isDeleted();
		public void    setDeleted();

		public void    setTimeOfLife( double lTime);
		public boolean testTimeOfLife(double lTime);
		

		public void callFactoryDraw( ModelBase pSharedModel, 
																		 Transform3d pSharedTransform, 
																		 Aspect3d pSharedAspect, 
																		 GL2 pGl );
		public void callFactoryAct( float pTimeDelta );
		public void callFactoryClose();
};

//*************************************************
