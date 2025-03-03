package org.phypo.PPg.PPgFX;

import org.phypo.PPg.PPgUtils.PPgIniFile;
import org.phypo.PPg.PPgUtils.PPgToken;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


//https://code.makery.ch/blog/javafx-dialogs-official/

//*******************************************************
public class Login extends Dialog<Pair<String, String>> {

	//-----------------------

	public Login( String iLabel, PPgIniFile iIni, Image iIcon,  String pSection, String pKey){

		setTitle("Login");

		setHeaderText(iLabel);

		FxHelper.SetIcon( this, iIcon );

		// Set the button types.
		ButtonType lLoginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(lLoginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane lGrid = new GridPane();
		lGrid.setHgap(10);
		lGrid.setVgap(10);
		lGrid.setPadding(new Insets(20, 150, 10, 10));

		TextField lUserName = new TextField();
		lUserName.setPromptText("Username");


		PasswordField lPasswordField = new PasswordField();
		lPasswordField.setPromptText("Password");

		lGrid.add(new Label("Username:"), 0, 0);
		lGrid.add(lUserName, 1, 0);
		lGrid.add(new Label("Password:"), 0, 1);
		lGrid.add(lPasswordField, 1, 1);


		String  lStr = iIni.get( pSection, pKey );
		if( lStr != null ){
			PPgToken lTok = new PPgToken( lStr, "", "," );
			lUserName.setText( lTok.nextTokenStringTrim());
			lPasswordField.setText(lTok.nextTokenStringTrim());
		}


		// Enable/Disable login button depending on whether a username was entered.

		Node lLoginButton = getDialogPane().lookupButton(lLoginButtonType);
		if( lUserName.getText().trim().isEmpty()) {
			lLoginButton.setDisable(true);
		}
		lUserName.textProperty().addListener((observable, oldValue, newValue) -> {
		    lLoginButton.setDisable(newValue.trim().isEmpty());
		});

		getDialogPane().setContent(lGrid);

		// Request focus on the username field by default.
		Platform.runLater(() -> lUserName.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		setResultConverter(dialogButton -> {
			if (dialogButton == lLoginButtonType) {
				return new Pair<>(lUserName.getText(), lPasswordField.getText());
			}
			return null;
		});
	}

}