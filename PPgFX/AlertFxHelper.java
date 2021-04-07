package org.phypo.PPg.PPgFX;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertFxHelper {
	
	public static Alert MakeAlert( Stage iOwner, Alert.AlertType iType, String iStrText ) {
		Alert lAlert = new Alert( iType, iStrText );
		lAlert.initOwner( iOwner );
		//lAlert.setX();
		//lAlert.setY(0);
		return lAlert;
	}
	public static Optional<ButtonType> MakeAlertShowAndWait( Stage iOwner, Alert.AlertType iType, String iStrText ) {
		return MakeAlert( iOwner, iType, iStrText).showAndWait();
	}

}
