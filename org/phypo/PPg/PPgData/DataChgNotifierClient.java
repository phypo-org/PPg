package org.phypo.PPg.PPgData;

public abstract interface DataChgNotifierClient {

	abstract public boolean     getNeedReload();
	abstract public void        setNeedReload(boolean iVal );
	abstract public boolean     getForceReload()                       ;
	abstract public void        setForceReload(boolean iVal);
	abstract public long        getLastTimeStamp()                     ;
	abstract public void        setLastTimeStamp( long iLastTimeStamp) ;
	abstract public void        razLastTimeStamp()                     ;
	abstract public void        setTmpTimeStamp( long iLastTimeStamp) ;
	abstract public long        getTmpTimeStamp();

	abstract public boolean     callSyncLoad();
}
