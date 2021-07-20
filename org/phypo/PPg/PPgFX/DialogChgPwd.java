package org.phypo.PPg.PPgFX;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.phypo.PPg.PPgUtils.Password;

//*************************************************
public abstract class DialogChgPwd extends PPgDialog {

	
	FlowPane          cFlowPane = FxHelper.CreateFlowPane();	
	protected PasswordField     cOldPasswordField     = new PasswordField();
	public    PasswordField     cNewPasswordField     = new PasswordField();
	protected PasswordField     cConfirmPasswordField = new PasswordField();

	//-------------------------------------------------------------------
	public boolean verifyPass() {
		
		if( cOldPasswordField.getText().length() > 0 
			&& (cOldPasswordField.getText().equals(cNewPasswordField.getText()))) {
			FxHelper.MsgErrWait( "New password cannot be the same as old");
			return false;				
		}

		String lError = Password.VerifyPass( cNewPasswordField.getText(), cConfirmPasswordField.getText() );
		if( lError != null ) {
			FxHelper.MsgErrWait( lError );
			return false;
		}
		return true;
	}

	public abstract boolean validPass();  

	//-------------------------------------------------------------------

	public DialogChgPwd( Window iOwner, String iTitle, boolean iCancel ) {
		super(iOwner, iTitle);


		GridPane lGrid = new GridPane();
		lGrid.setHgap(10);
		lGrid.setVgap(10);
		lGrid.setPadding(new Insets(20, 150, 10, 10));

		
		String lTip = Password.GetValidityConditions();
		

		cOldPasswordField.setPromptText("Old password");
		
		cNewPasswordField.setPromptText("New password");		
		cNewPasswordField.setTooltip( new Tooltip( lTip) );
		
		cConfirmPasswordField.setPromptText("Confirm password");
		cConfirmPasswordField.setTooltip(new Tooltip( "Confirm password") );
		
		lGrid.add(new Label("Old password:"), 0, 0);
		lGrid.add(cOldPasswordField, 1, 0);
		lGrid.add(new Label("New password:"), 0, 1);
		lGrid.add(cNewPasswordField, 1, 1);
		lGrid.add(new Label("Confirm password:"), 0, 2);
		lGrid.add(cConfirmPasswordField, 1, 2);
		
		
		lGrid.add(new Label(""), 0, 3); // just for vertical spacing

		getPrimPane().setTop( new Label( lTip) );
		getPrimPane().setCenter( lGrid );

		if( iCancel ) {
			addToBottomRight( FxHelper.CreateButton( "  Cancel   ", "Cancel dialogue", (ActionEvent )->{
				close();
			}));
		}

		addToBottomRight( FxHelper.CreateButton( "  Ok   ", "Valid dialogue", (ActionEvent )->{
			if( verifyPass() && validPass()) {	
				setOk();
				close();
			}
		}));

	}
	//-------------------------------------------------------------------
	public DialogChgPwd(  boolean iCance) {
		this(AppliFx.Instance().getPrimStage(), "Change password", iCance);

	}
}
//*************************************************
