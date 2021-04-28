package org.phypo.PPg.PPgFX;

import org.phypo.PPg.PPgUtils.Log;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

//**********************************************************
public class LongFieldFx  extends TextField {
	final private SimpleLongProperty cValue;
	final private long               cMinValue;
	final private long               cMaxValue;

	//---------------------------------------
	// expose an integer value property for the text field.
	public long  getValue()                 { return cValue.getValue(); }
	public void  setValue(int newValue)      { cValue.setValue(newValue); }
	public LongProperty valueProperty()     { return cValue; }
	
	long getMin() { return cMinValue; }
	long getMax() { return cMaxValue; }

	//---------------------------------------
	public LongFieldFx (long cMinValue, long cMaxValue, long initialValue) {
		if (cMinValue > cMaxValue) 
			throw new IllegalArgumentException(
					"IntField min value " + cMinValue + " greater than max value " + cMaxValue
					);
		if (cMaxValue < cMinValue) 			
			throw new IllegalArgumentException(
					"IntField max value " + cMinValue + " less than min value " + cMaxValue
					);
		if ( initialValue < cMinValue  ) {
			Log.Err("IntField initialValue " + initialValue + " not between " + cMinValue + " and " + cMaxValue);
			initialValue = cMinValue; 
		} else 
		if( initialValue > cMaxValue)  {
			Log.Err("IntField initialValue " + initialValue + " not between " + cMinValue + " and " + cMaxValue);
			initialValue = cMaxValue; 
		}

		// initialize the field values.
		this.cMinValue = cMinValue;
		this.cMaxValue = cMaxValue;
		cValue = new SimpleLongProperty(initialValue);
		setText(initialValue + "");

		final LongFieldFx intField = this;

		// make sure the value property is clamped to the required range
		// and update the field's text to be in sync with the value.
		cValue.addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				if (newValue == null) {
					intField.setText("");
				} else {
					if (newValue.intValue() < intField.cMinValue) {
						cValue.setValue(intField.cMinValue);
						return;
					}

					if (newValue.intValue() > intField.cMaxValue) {
						cValue.setValue(intField.cMaxValue);
						return;
					}

					if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
						// no action required, text property is already blank, we don't need to set it to 0.
					} else {
						intField.setText(newValue.toString());
					}
				}
			}
		});

		// restrict key input to numerals.
		this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override public void handle(KeyEvent keyEvent) {
				if(intField.cMinValue<0) {
					if (!"-0123456789".contains(keyEvent.getCharacter())) {
						keyEvent.consume();
					}
				}
				else {
					if (!"0123456789".contains(keyEvent.getCharacter())) {
						keyEvent.consume();
					}
				}
			}
		});

		// ensure any entered values lie inside the required range.
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				if (newValue == null || "".equals(newValue) || (intField.cMinValue<0 && "-".equals(newValue))) {
					cValue.setValue(0);
					return;
				}

				final int intValue = Integer.parseInt(newValue);

				if (intField.cMinValue > intValue || intValue > intField.cMaxValue) {
					textProperty().setValue(oldValue);
				}

				cValue.set(Integer.parseInt(textProperty().get()));
			}
		});
	}
}
//**********************************************************

