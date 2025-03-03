package com.phipo.PPg.PPgG3d;


//*************************************************
public interface TransformBase{
		
		public void renderGL();

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
}
//*************************************************
