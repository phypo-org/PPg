package org.phypo.PPg.PPgWin;

import java.util.*;

//*************************************************
public interface FrameEditObjectInterface  {

		public FrameEditObjectInterface FEOduplicate();
		public void     FEOinitFrom( FrameEditObjectInterface pObj);
		public int      FEOgetFieldCount();
		public String   FEOgetFieldInfo(int pInd, FrameEditObject.InfoMode pMode );
		public boolean  FEOsetField( int pInd, String pVal );
		public void     FEOnotifFinish();		
}
//*************************************************
