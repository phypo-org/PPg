package org.phypo.PPg.PPgUtils;


public class PPgApplication<TYPE extends PPgApplication>{
	
      static PPgApplication sInstance=null;
      static public PPgApplication Instance() { return sInstance; }
  	
	 //public T Instance();	 interface

	 
	static String sAppliName="" ;
	static public String GetAppliName () { return sAppliName; }
	
	static String sVersion     = "";
	static public String GetVersion() { return sVersion; }
	
	static String sSocietyName = "";
	static public String GetSocietyName() { return sSocietyName; }
	static String sDate        = "";
	static public String GetDate() { return sDate; }
	static String sEmail= "";
	static public String GetEmail() { return sEmail; }

	static String sOsSystem="";
	static public String GetOs() { return sOsSystem; }
	
	//----------------------------------------------------
	public  void PPgApplication( String iAppliName, String iVersion, String iSocietyName, String iDate, String iEmail){
		sAppliName     = new String(iAppliName);
		sVersion       = new String( iVersion );
		sSocietyName   = new String( iSocietyName );
		sDate          = new String( iDate );
		sEmail         = new String( iEmail);		
		
		sInstance      = this;
	}
}
