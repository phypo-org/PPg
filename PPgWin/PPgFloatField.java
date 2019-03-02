package org.phypo.PPg.PPgWin;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;



//*****************************
public class PPgFloatField extends PPgInputField{

	//----------------
	public	PPgFloatField( String pStr, float pVal, int pSens ){
		super( pStr, Float.toString( pVal ), pSens );
	}	
	//----------------
	public	float getFloat(){
		return  Float.parseFloat( cText.getText() );
	}
	//----------------
	public	void setFloat(float pVal){
		cText.setText( Float.toString( pVal ) );
	}
}
//*****************************
