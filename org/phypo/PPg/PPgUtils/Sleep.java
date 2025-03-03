package org.phypo.PPg.PPgUtils;


public class Sleep {

	// ========== just for avoid useless try catch in the code ===============
	public static void Millisleep( long iMilli ) {
		try {
			Thread.sleep(iMilli);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
