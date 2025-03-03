package org.phypo.PPg.PPgFX;

import java.text.DateFormat;

import org.phypo.PPg.PPgUtils.OsValidator;
import org.phypo.PPg.PPgUtils.PPgIniFile;
import org.phypo.PPg.PPgUtils.PPgParam;
import org.phypo.PPg.PPgUtils.PPgTrace;

import javafx.stage.Stage;


//*********************************************************
//public abstract class AppliFx<TYPE extends AppliFx> extends javafx.application.Application{
public abstract class AppliFx extends javafx.application.Application{

	static public AppliFx sInstance=null;
	static public AppliFx Instance() { return sInstance; }

    protected static String sAppliName   = "";
	public static final String GetAppliName ()  { return sAppliName; }

	static String sVersion     = "";
	public static final String GetVersion()     { return sVersion; }

	static String sSocietyName = "";
	public static final String GetSocietyName() { return sSocietyName; }

	static String sDate        = "";
	public static final String GetDate()        { return sDate; }

	static String sEmail= "";
	public static final String GetEmail()       { return sEmail; }

	static  String sJavaVersions ="";
	static final String GetJavaVersions()       { return sJavaVersions; }

	static String sOsSystem="";
	public static final String GetOs()          { return sOsSystem; }

	//---------------------
	public abstract Stage getPrimStage();

	//---------------------
	static String     sStrIniFile ="Config.ini";
	protected static PPgIniFile sIniFile = null;
    public static PPgIniFile  GetIniFile()       { return sIniFile;}
    public        PPgIniFile  getStdIniFile()    { return sIniFile; }
	//---------------------

	DateFormat cDateFormat =  DateFormat.getDateTimeInstance();
	DateFormat getDateFormat() { return cDateFormat;}
	public static DateFormat GetDateFormat() { return sInstance.cDateFormat; }

	//----------------------------------------------------
	public AppliFx(){
		sInstance = this;
	}
	//----------------------------------------------------
	public boolean init( String iAppliName, String iVersion, String iSocietyName, String iDate, String iEmail){

		sAppliName     = new String( iAppliName );
		sVersion       = new String( iVersion );
		sSocietyName   = new String( iSocietyName );
		sDate          = new String( iDate );
		sEmail         = new String( iEmail);

		sInstance = this;
		sOsSystem = OsValidator.getOS();
		sJavaVersions = System.getProperty("java.version") + ";" + System.getProperty("javafx.version");

		if( GetOs() == null ) {
			String lErr = "Cannot determine the operating system - Exit";

			FxHelper.MsgErrWait( lErr );

			PPgTrace.Fatal( lErr);
			System.exit(1);
		}

		return true;
	}
	//----------------------------------------------------
	public static String GetStringInfosApplication(String iSep) {
		return sAppliName      + iSep
				+ sVersion     + iSep
				+ sDate + iSep
				+ sSocietyName + iSep
				+ sEmail + iSep
				+ sOsSystem + iSep
				+ sJavaVersions + iSep;
		}
	//----------------------------------------------------
	public static String GetStringInfosApplication(String iSep, String iSep2) {
		return sAppliName      + iSep
				+ sVersion     + iSep
				+ sDate + iSep2
				+ sSocietyName + iSep
				+ sEmail + iSep2
				+ sOsSystem + iSep
				+ sJavaVersions;
		}
	//----------------------------------------------------
	public static String GetStringInfosApplicationFormat(String iSep) {
		return  "Application:" + sAppliName      + iSep
				+"Version:" + sVersion     + iSep
				+"Date:" + sDate + iSep
				+"Society:" + sSocietyName + iSep
				+"Email:" + sEmail + iSep
				+"Os:" + sOsSystem + iSep
				+"Java:" + sJavaVersions + iSep;
		}
	//----------------------------------------------------
	public static boolean InitGenericParams( String iArgv[] ) {

		PPgTrace.SetVerbose( PPgParam.GetInt( iArgv, "-v=", 0));
		PPgTrace.SetDebug(   PPgParam.GetInt( iArgv, "-g=", 0));


		if( (sStrIniFile = PPgParam.GetString( iArgv,  "-I=", sStrIniFile )) != null ) {
			sIniFile = new PPgIniFile( sStrIniFile );
		}

		return true;
	}


}
