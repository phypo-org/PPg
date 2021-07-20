package org.phypo.PPg.PPgFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Correction of DatePicker bug when when use textfield
//  https://stackoverflow.com/questions/32346893/javafx-datepicker-not-updating-value
	
//**********************************
public class MyDatePicker  extends DatePicker  {

	public boolean harmonizeValues(){
		String lStr = getEditor().getText();
		if( lStr == null ) {			
			return false;
		}
			
		setValue(getConverter().fromString(lStr));
		
		if( getValue() == null )
			return false;
		
		return true;
	}
	
	public MyDatePicker(){
		
		setPromptText("yyyy/MM/dd");
		
		setConverter(new StringConverter<LocalDate>() {
	        private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        @Override
	        public String toString(LocalDate localDate) {
	            if(localDate==null)
	                return "";
	            return dateTimeFormatter.format(localDate);
	        }
	        @Override
	        public LocalDate fromString(String dateString) {
	            if(dateString==null || dateString.trim().isEmpty())
	                return null;
	            try{
	                return LocalDate.parse(dateString,dateTimeFormatter);
	            }
	            catch(Exception e){
	                //Bad date value entered
	                return null;
	            }
	        }
	    });
		//--------------------------------------------------------------
		focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            if (!newValue){
	            	harmonizeValues();
	            }
	        }
	    });
	}
}
//**********************************
