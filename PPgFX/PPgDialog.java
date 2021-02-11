package org.phypo.PPg.PPgFX;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

//*********************************************
public class PPgDialog extends Stage {
	
	boolean cOk= false ;
	public boolean isOk( ) { return cOk; }
	protected void setOk( ) { cOk = true;}

	private Scene      cPrimScene;
	
	private BorderPane cPrimPane;
	private BorderPane cFootPane;
	
	private FlowPane   cFlowButtonRight;
	private Label      cFootText;
	
	public BorderPane getPrimPane() { return cPrimPane; }
	public BorderPane getFootPane() { return cFootPane; }
	public void setFootText( String iStr ) { cFootText.setText( iStr); };
	//---------------------------------------------------------
	public PPgDialog( Window iOwner, String iTitle) {
		this( iOwner, iTitle, true, true );
	}
	//---------------------------------------------------------
	public PPgDialog( Window iOwner, String iTitle, boolean iModal, boolean iFlagClose ) {

		if( iTitle != null )
			setTitle( iTitle );
		
		if( iOwner != null )
			initOwner( iOwner );
		else {
			initOwner( AppliFx.Instance().getPrimStage());
		}

		if( iModal ) {
			initModality(Modality.WINDOW_MODAL);
			//setAlwaysOnTop(true);
		}
		

		if( iFlagClose == false ) {
			setOnCloseRequest(e->e.consume());
		}
	
		setScene( (cPrimScene = new Scene( (cPrimPane = new BorderPane()))));  
		
		cPrimPane.setBottom( (cFootPane = new BorderPane()) );	
		cFootPane.setBottom( (cFootText = new Label( "" )));		
		
		cFootPane.setRight((cFlowButtonRight = FxHelper.CreateFlowPane()));
	}
	//---------------------------------------------------------
	//---------------------------------------------------------
	protected void addToBottomRight( Button iButton) {
		cFlowButtonRight.getChildren().add(iButton); 
	}
	
	//*********************************************
}
