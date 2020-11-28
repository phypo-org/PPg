package org.phypo.PPg.PPgWin;

import java.io.File;

import javax.swing.JFileChooser;


public class PPgFileChooser {
	//-------------
	public static File GetFileName(String iLabel, String iCurrentPath ) {
		
		JFileChooser lFc = new JFileChooser(  );
		lFc.setMultiSelectionEnabled(false);
		lFc.setFileSelectionMode( JFileChooser.FILES_ONLY );
		lFc.setDialogType(JFileChooser.OPEN_DIALOG);
		lFc.setDialogTitle(iLabel);
		int lReturnVal = lFc.showOpenDialog(PPgAppli.TheAppli);	
		if( lReturnVal  == JFileChooser.APPROVE_OPTION) {
			iCurrentPath = new String( lFc.getCurrentDirectory().getAbsolutePath());								
			File lFile = lFc.getSelectedFile();
			return lFile ;
		}
		return null;
	}
}
