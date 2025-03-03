package org.phypo.PPg.PPgFX;


import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;


//********************************************
public class SliderLongField {
	GridPane cGrid = new GridPane();
	Slider   cSlider;

	//---------------------------------------
	public abstract class ChangeValue {
		 abstract boolean change( long iValue );
	}
	//---------------------------------------
	public SliderLongField( String iLabel, long iMinValue, long iMaxValue, long iValue, ChangeValue iChange) {

		LongFieldFx lText;
		int lCol=0, lRow =0;

		cGrid.add( new Label( "Periode for testing probe" ), lCol, lRow++ );

		cGrid.add( cSlider = new Slider( iMinValue, iMaxValue,  iValue),      lCol++, lRow );
		cGrid.add( lText = new LongFieldFx( iMinValue, iMaxValue,  iValue), lCol++, lRow );
		lText.setPrefColumnCount(4);
		lText.setPadding( new Insets( 5, lRow, lRow, lRow ));
		cGrid.add( new Label( " min " ), lCol++, lRow );

		cSlider.setShowTickMarks(true);
		cSlider.setShowTickLabels(true);
		cSlider.setMajorTickUnit(60);
		cSlider.setBlockIncrement(1);

		cSlider.valueProperty().addListener((Observable ov)-> {
			if (cSlider.isPressed()) {
				String lNewVal ;
					lNewVal = Long.toString( (long) cSlider.getValue() );

					if( !lText.getText().equals(lNewVal) ) {
					lText.setText( lNewVal );

					if( iChange != null ) {
						iChange.change((long) cSlider.getValue());
					}
				}
			}
		});

		lText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String iNewValue) {
					if( iNewValue.length() > 0 ) {

					Long lDval = Long.parseLong(iNewValue);

					cSlider.setValue( lDval);
					if( iChange != null ) {
						iChange.change(lDval );
					}
				}
			}
		});
	}

}
//********************************************
