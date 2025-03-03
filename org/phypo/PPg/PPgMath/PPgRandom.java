package org.phypo.PPg.PPgMath;




import java.util.Random;



//*************************************************
public class PPgRandom extends Random {


		public PPgRandom() {
				super();
		}

		public PPgRandom( long pSeed ) {
				super( pSeed );
		}


		//------------------------------------------------
		final public double nextDouble( double pMax ) {

				return (super.nextDouble()*pMax*2) - pMax;
		}
		//------------------------------------------------
		final public double nextDoublePositif( double pMax ) {

				return super.nextDouble()*pMax;
		}

		//------------------------------------------------
		final public float nextFloat( float pMax ) {

				return (float)((super.nextDouble()*pMax*2) - pMax);
		}
		//------------------------------------------------
		final public float nextFloatPositif( float pMax ) {

				return (float)(super.nextDouble()*pMax);
		}
		//------------------------------------------------
		@Override
		final public int nextInt( int pMax ) {

				return super.nextInt(pMax*2) - pMax;
		}
		//------------------------------------------------
		final public int nextIntPositif( int pMax ) {

				return super.nextInt(pMax);
		}

}

//*************************************************
