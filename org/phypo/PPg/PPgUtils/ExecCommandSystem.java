package org.phypo.PPg.PPgUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;


//******************************************
public class ExecCommandSystem{
	//	 public ExecCommandSystem() {;}
	public void execOutLine( String iLine) {}
	public void execErrLine( String iLine)  {}
	public void execErr( String iLine)  { execErrLine(iLine);}
	public void execFinish()  {}
	public String init(String lCmd) {
		return lCmd;
	}

	//-------------------------------------------
	//-------------------------------------------
	//-------------------------------------------
	public boolean execCmd( PPgIniFile iInitFile, String iKeyOs, String iVarCmd ) {
		String lCmd = iInitFile.get( iKeyOs, iVarCmd );
		if( lCmd == null || lCmd == "") {
			String lErr =   "Command value not found in configuration file : " +iKeyOs + "/"  + iVarCmd ;
			PPgTrace.Err( lErr);
			execErr( lErr );
			return false;
		}
		return execCmd(  lCmd.trim());
	}
	//-------------------------------------------
	public boolean execCmd( String lCmd ) {

		try {
			Runtime lRt = Runtime.getRuntime();
		//	Log.Dbg("CallInit " + lCmd );
			lCmd = init( lCmd );
		//	Log.Dbg("->CallInit " + lCmd );

			PPgTrace.Dbg3("ExecCommandSystem.execCmd <<<"+lCmd+">>>");
			Process lProcess = lRt.exec(lCmd);
			int lRetCode = lProcess.waitFor();
			if( lRetCode != 0 ){
				String lErr =  "Process return err code " +  lRetCode;
				PPgTrace.Err( lErr);
				execErr( lErr );
			}

			BufferedReader lReader = new BufferedReader(new InputStreamReader(lProcess.getInputStream()), 1);
			String lLine;
			while ((lLine = lReader.readLine()) != null) {
				PPgTrace.Dbg3(  "ExecCmdLine out:" + lLine );
				execOutLine( lLine );
			}

			BufferedReader lReaderErr = new BufferedReader(new InputStreamReader(lProcess.getErrorStream()), 1);
			String lLineErr;
			while ((lLineErr = lReaderErr.readLine()) != null) {
				PPgTrace.Err( "Error>" + lLineErr );
				execErrLine( lLine );
			}

		}catch(Exception e) {
			String lErr = "Exception - Command failed " + lCmd + e.toString();
			PPgTrace.Err(lErr);
			execErr( lErr );
			return false;
		}
		execFinish();
		return true;
	}
	//-------------------------------------------
	static public boolean ExecCmd(PPgIniFile iInitFile,  String iKeyOs, String iVarCmd, ExecCommandSystem loadCmdExe ) {
		return loadCmdExe.execCmd( iInitFile, iKeyOs, iVarCmd );
	}

	//-------------------------------------------
	static public boolean ExecCmd( String lCmd, ExecCommandSystem iLineExec ) {
		return  iLineExec.execCmd(lCmd ) ;
	}
}
//******************************************

