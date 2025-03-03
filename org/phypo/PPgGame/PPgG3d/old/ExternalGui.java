package com.phipo.PPg.PPgG3d;



//*************************************************

public interface ExternalGui{

		public boolean setting( int pW, int pH, boolean pFullScreen );
		public boolean init( World pWorld  );
		public void    beginTurn();
		public boolean stop();
		public boolean isOk();
}

//*************************************************
