package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;

//*************************************************
public interface TransformBase{
		
		public void renderGL(GL2 pGl);

		public void rotate( Float3 pRot);
		public void rotateX( float pVal);
		public void rotateY( float pVal);
		public void rotateZ( float pVal);

		public void move( Float3 pPos );
		public void moveX( float pVal);
		public void moveY( float pVal);
		public void moveZ( float pVal);

		public void scale( Float3 pScale );
		public void scaleX( float pVal);
		public void scaleY( float pVal);
		public void scaleZ( float pVal);


		public Float3 getRotate();
		public Float3 getTranslat();
		public Float3 getScale();
}
//*************************************************
