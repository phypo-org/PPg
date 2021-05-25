package org.phypo.PPg.PPgFX;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Pair;


@SuppressWarnings("serial")
//*******************************************************

public class DialogEdit  extends  Dialog<Pair<Boolean, String>>{

	Button   cButtonClose        = null;
	Button   cButtonSave         = null;
	Button   cButtonCancel       = null;
	TextArea cText               = null;
	
	static final String sClose  = "Close";
	// AJOUTER Hexdump ?
	
	public DialogEdit( String iTitle, String iHeader, String iText, boolean iPermitEdit, String iStyle) {

		setTitle(iTitle);
		
		if( iHeader != null)
			setHeaderText( iHeader );

		if( iPermitEdit == false ) {
			getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
		}
		else {
				getDialogPane().getButtonTypes().addAll(ButtonType.FINISH); // METTRE un handler pour le return
				getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
		}

		BorderPane lPane =  new BorderPane();
		getDialogPane().setContent( lPane);
				
		lPane.setCenter ( (cText = new TextArea()) );
	 
		if( iStyle != null )cText.setStyle( iStyle );
		if( iPermitEdit == false ) {
			cText.editableProperty().set(false);
		}
		cText.setFont(Font.font ("monospaced", 12));
		cText.setPrefSize(800, 600 );
		cText.setWrapText(true);
		cText.setText( iText);
	}
	//-----------------------------------------------	
}	
//*******************************************************

