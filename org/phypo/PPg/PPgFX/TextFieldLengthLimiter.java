package org.phypo.PPg.PPgFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

//********************************************
public class TextFieldLengthLimiter implements ChangeListener<String>{
	
	TextField cName;
	int       cMaxLength;
	
	public TextFieldLengthLimiter( TextField iName, int iMaxLength) {
		cName = iName;
		cMaxLength = iMaxLength;
	}
	
	static public void Apply( TextField iName, int iMaxLength ) {
		iName.textProperty().addListener(new TextFieldLengthLimiter( iName,  iMaxLength ));		
	}
	
    @Override
    public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
        try {
            if(newValue.length() > cMaxLength )
            	cName.setText(oldValue);
        } catch (Exception e) {
            cName.setText(oldValue);
        }
    }
}
//********************************************
