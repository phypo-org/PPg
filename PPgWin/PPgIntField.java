package org.phypo.PPg.PPgWin;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

//*****************************
public class PPgIntField extends PPgInputField{

	//----------------
public	PPgIntField( String pStr, int pVal, int pSens ){
	super( pStr, Integer.toString( pVal ), pSens );

	}	
	//----------------
public	int  getInt(){
		return Integer.parseInt(  cText.getText() );	 
	}
	//----------------
public	void setInt(int pVal){
		cText.setText( Integer.toString( pVal ) );		
	}
}
//*****************************
