package org.phypo.PPg.PPgFX;

import java.util.Optional;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


//https://code.makery.ch/blog/javafx-dialogs-official/

//*******************************************************
public class Login extends Dialog<Pair<String, String>> {
	String cUser="";
	String cPass="";

	public String getUser()     { return cUser;}
	public String getPassword() { return cPass; }

	boolean cValidation = false;

	public boolean   getValidation() { return cValidation; }

	//-----------------------

	public Login( String iLabel, PPgIniFile iIni, String pSection, String pKey){
		
		String  lStr = iIni.get( pSection, pKey );
		if( lStr != null ){
			PPgToken lTok = new PPgToken( lStr, "", "," );
			cUser =lTok.nextTokenStringTrim();	
			cPass =lTok.nextTokenStringTrim();
		}
		
		setTitle("Login");

		setHeaderText(iLabel);

		// Set the icon (must be included in the project).
//		setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane lGrid = new GridPane();
		lGrid.setHgap(10);
		lGrid.setVgap(10);
		lGrid.setPadding(new Insets(20, 150, 10, 10));

		TextField lUserName = new TextField();
		lUserName.setText( cUser );
		lUserName.setPromptText("Username");
		PasswordField lPasswordField = new PasswordField();
		lPasswordField.setPromptText("Password");
		lPasswordField.setText(cPass);

		lGrid.add(new Label("Username:"), 0, 0);
		lGrid.add(lUserName, 1, 0);
		lGrid.add(new Label("Password:"), 0, 1);
		lGrid.add(lPasswordField, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).

		getDialogPane().setContent(lGrid);

		// Request focus on the username field by default.
		Platform.runLater(() -> lUserName.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(lUserName.getText(), lPasswordField.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}
}