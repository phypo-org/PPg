package org.phypo.PPg.PPgFX;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.phypo.PPg.PPgUtils.PPgIniFile;
import org.phypo.PPg.PPgUtils.PPgTrace;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


//*********************************************************

public class FxHelper {

	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ, TYPE> addColumn( TableView<OBJ> iTable, String iLabel, String iVarName ) {

		TableColumn<OBJ, TYPE> lCol = new TableColumn<>(iLabel);
		lCol.setCellValueFactory(new PropertyValueFactory<OBJ,TYPE>(iVarName));
		iTable.getColumns().add(lCol);
		return lCol;
	}
	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ, TYPE> addTopColumn( TableView<OBJ> iTable, String iLabel ){
		TableColumn<OBJ, TYPE> lCol   = new TableColumn<>(iLabel);
		iTable.getColumns().add(lCol);
		return lCol;
	}
	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ,TYPE> addSubColumn(  TableColumn<OBJ,TYPE> iCol, String iLabel, String iVarName  ){
		TableColumn<OBJ, TYPE> lCol = new TableColumn<>(iLabel);
		lCol.setCellValueFactory(new PropertyValueFactory<OBJ,TYPE>(iVarName));
		iCol.getColumns().add(lCol);
		return lCol;
	}
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	// https://stackoverflow.com/questions/10315774/javafx-2-0-activating-a-menu-like-a-menuitem

	// Create a menu like a menuitem in the menubar
	public static Menu AddMenuBarItem( MenuBar iMenubar, String iLabel, EventHandler<MouseEvent> iAction ) {
		Label menuLabel = new Label(iLabel);
		menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				iAction.handle(event);
			}});

		Menu lMenu = new Menu();
		lMenu.setGraphic(menuLabel);
		iMenubar.getMenus().add( lMenu);
		return lMenu;
	}
	//-------------------------------------------
	public static MenuItem AddMenuItem( Menu iMenu, String iLabel, EventHandler<ActionEvent> iAction) {
		MenuItem lItem = new MenuItem( iLabel);
		iMenu.getItems().add(lItem);
		lItem.setOnAction( iAction );
		return lItem;
	}
	//-------------------------------------------
	public static MenuItem AddMenuItem( ContextMenu iMenu, String iLabel, EventHandler<ActionEvent> iAction ) {
		MenuItem lItem = new MenuItem( iLabel);
		iMenu.getItems().add(lItem);
		lItem.setOnAction( iAction );
		return lItem;
	}
	//-------------------------------------------
	public static MenuItem AddMenuItem( MenuButton iMenu, String iLabel, EventHandler<ActionEvent> iAction ) {
		MenuItem lItem = new MenuItem( iLabel);
		iMenu.getItems().add(lItem);
		lItem.setOnAction( iAction );
		return lItem;
	}
	//-------------------------------------------
	public static Menu AddMenuSeparator( Menu iMenu) {
		iMenu.getItems().add(new SeparatorMenuItem());
		return iMenu;
	}
	//-------------------------------------------
	public static ContextMenu AddMenuSeparator( ContextMenu iMenu) {
		iMenu.getItems().add(new SeparatorMenuItem());
		return iMenu;
	}
	//-------------------------------------------
	public static MenuButton AddMenuSeparator(MenuButton iMenu) {
		iMenu.getItems().add(new SeparatorMenuItem());
		return iMenu;
	}
	//-------------------------------------------
	public static CheckMenuItem AddMenuCheckBox( Menu iMenu, String iLabel ) {
		CheckMenuItem lItem = new  CheckMenuItem( iLabel );
		iMenu.getItems().add(lItem);
		return lItem;
	}
	//-------------------------------------------
	public static CheckMenuItem AddMenuCheckBox( Menu iMenu, String iLabel, EventHandler<ActionEvent> iAction  ) {
		CheckMenuItem lItem = new  CheckMenuItem( iLabel );
		iMenu.getItems().add(lItem);
		lItem.setOnAction( iAction );
		return lItem;
	}
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	//------------------------------------------------------------------

	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip ) {
		return CreateToggle( iLabel, iGroup, iTip, null, null );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel,  String iTip, Image iImage  ) {
		return CreateToggle( iLabel, null, iTip, iImage );
	}
	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip, Image iImage ) {
		return CreateToggle( iLabel, iGroup, iTip, iImage, null );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip, EventHandler<ActionEvent> iHdl ) {
		return CreateToggle( iLabel, iGroup, iTip, null, iHdl );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel, String iTip, EventHandler<ActionEvent> iHdl ) {
		return CreateToggle( iLabel, null, iTip, null, iHdl );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel, String iTip  ) {
		return CreateToggle( iLabel, null, iTip, null, null );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip, Image iImage, EventHandler<ActionEvent> iHdl ) {

		ToggleButton lToggle   = new ToggleButton( iLabel);

		if( iGroup != null )
			lToggle.setToggleGroup(iGroup);

		if( iTip != null )
			lToggle.setTooltip( new Tooltip( iTip ));

		if( iImage != null ) {
			lToggle.setGraphic( new ImageView( iImage) );
		}

		if( iHdl != null ) {
			PPgTrace.Info("*** CreateToggle");

			lToggle.setOnAction(iHdl);

		}

		return lToggle;
	}
	//-------------------------------------------
	//-------------------------------------------
	//-------------------------------------------
	public static MenuButton CreateMenuButton( String iLabel, String iTip) {
		return CreateMenuButton( iLabel, iTip, null );
	}
	//-------------------------------------------
	public static MenuButton CreateMenuButton( String iLabel, String iTip, Image iImg ) {

		MenuButton lButton  = new MenuButton( iLabel);

		if( iTip != null )
			lButton.setTooltip( new Tooltip( iTip ));

		if( iImg != null ) {
			lButton.setGraphic( new ImageView( iImg ) );
		}

		return lButton;
	}
	//------------------------------------------------------------------
	public static void SetButtonImage( Button iButton, Image iImg ) {
		iButton.setGraphic( new ImageView( iImg) );
	}
	//------------------------------------------------------------------
	public static FlowPane CreateFlowPane() {

		FlowPane lFlow = new FlowPane();
		lFlow.setVgap(8);
		lFlow.setHgap(8);
		lFlow.setPrefWrapLength(300); // preferred width = 300

		return lFlow;
	}
	//-------------------------------------------
	public static Button CreateButton( String iLabel, String iTip, EventHandler<ActionEvent> iHdl) {
		return CreateButton( iLabel, iTip, iHdl, null );
	}
	//-------------------------------------------
	public static Button CreateButton( String iLabel, String iTip, EventHandler<ActionEvent> iHdl, Image iImg ) {

		Button lButton  = new Button( iLabel);

		if( iHdl != null )
			lButton.setOnAction(iHdl);

		if( iTip != null )
			lButton.setTooltip( new Tooltip( iTip ));

		if( iImg != null ) {
			lButton.setGraphic( new ImageView( iImg ) );
		}

		return lButton;
	}
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	public static Image ReadIcon( PPgIniFile pIni, String pSection, String pKey, String pDefault ){

		if( pSection == null || pKey == null )
			return null;

		String lStr  = pIni.get( pSection, pKey );

		//	Log.Dbg(  "****** " +pSection + ":" + pKey +"="+lStr +   "  (" + pDefault +")" );

		if( lStr == null )
			lStr = pDefault;

		if( lStr == null || lStr.length() == 0 )
			return null;


		int lPosSize = lStr.lastIndexOf( ';' );

		//		Log.Dbg( "possize:" + lPosSize );
		double lFlipH = 1;
		double lFlipV = 1;
		//	Log.Dbg( "Icon possize:" + lPosSize + " Flip:" + lFlipH + " " + lFlipV);

		if( lPosSize == -1 ) {
			lPosSize = lStr.lastIndexOf( '>' );
			//		Log.Dbg( "Icon > possize:" + lPosSize + " Flip:" + lFlipH + " " + lFlipV);
			if(lPosSize != -1 ) {
				lFlipH = -1;
			}
		}
		if( lPosSize == -1 ) {
			lPosSize = lStr.lastIndexOf( 'V' );
			//		Log.Dbg( "Icon V possize:" + lPosSize + " Flip:" + lFlipH + " " + lFlipV);
			if(lPosSize != -1 ) {
				lFlipV = -1;
				PPgTrace.Dbg( "V "+ " Flip:" + lFlipH + " " + lFlipV);
			}
		}
		if( lPosSize == -1 ) {
			//		Log.Dbg( "Icon X possize:" + lPosSize + " Flip:" + lFlipH + " " + lFlipV);
			lPosSize = lStr.lastIndexOf( 'X' );
			if(lPosSize != -1 ) {
				lFlipH = -1;
				lFlipV = -1;
			}
		}

		//	Log.Dbg( "Icon Flip:" + lFlipH + " " + lFlipV);

		int lWidth  = 0;
		int lHeight = 0;
		double lScale = 1;

		if( lPosSize != -1 ) {

			String lSize = lStr.substring( lPosSize+1 );
			lStr = lStr.substring( 0, lPosSize );


			lPosSize = lSize.indexOf( 'x' );
			if( lPosSize == -1 )
				lPosSize = lSize.indexOf( 'X' );

			try{
				if( lPosSize == -1 ){
					lScale = Float.parseFloat(lSize );
				}
				else {
					lWidth  = Integer.parseInt( lSize.substring( 0, lPosSize ) );
					lHeight = Integer.parseInt( lSize.substring( lPosSize+1 ) );
				}
			}catch(NumberFormatException ex){
				PPgTrace.Dbg( "ReadIcon NumberFormatException for " + lSize );
				lWidth = lHeight = 0;
				lScale = 1;
				return null;
			}

			//						Log.Dbg( "ReadIcon Str:" + lStr + " Size:" + lSize
			//																+ " lWidth:" + lWidth + " lHeight:" + lHeight + " lScale:" + lScale );

		}

		if( lStr == null || lStr.length() == 0 ){
			PPgTrace.Err( "PPIniFile.Image return null .size error for " 	+ pSection + ":" + pKey );
			return null;
		}


		Image lImage = null;

		if( lScale != 1) {
			lImage = new Image( lStr );
			lWidth = (int) (lImage.getWidth()*lScale);
			lHeight = (int) (lImage.getHeight()*lScale);
			lImage = null;
		}

		if( lWidth >0 && lHeight >0 ){
			PPgTrace.Dbg("FxHelper ReadIcon Name:" + lStr + " w:" + lWidth +" h:" + lHeight + " s:" + lScale);
			lImage = new Image( lStr, lWidth, lHeight, true, true, false );
		}
		else {

			lImage = new Image( lStr  );
			PPgTrace.Dbg("FxHelper ReadIcon Name:" + lStr + " s:" + lScale +" w="	+	lImage.getWidth());
			if( lImage.isError() ||lImage.getWidth() == 0 ) {
				PPgTrace.Dbg( "FxHelper ReadIcon error:" + lImage.errorProperty() + " ex:" + lImage.getException());
				return null;
			}
		}

		return lImage;
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public static void SetIcon( Stage iStage, Image iImage ) {
		if( iImage != null && iStage != null) {
			iStage.getIcons().add( iImage );
		}
	}
	//--------------------------------------
	public static void SetIcon( Dialog iStage, Image iImage ) {
		if( iImage != null && iStage != null) {
			iStage.setGraphic(new ImageView( iImage ));
		}
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public enum DialogMsgType { INFO(AlertType.INFORMATION), WARN(AlertType.WARNING), ERR(AlertType.ERROR), OK_CANCEL( AlertType.CONFIRMATION)
		; AlertType cType;
		DialogMsgType(AlertType iType ){ cType = iType; }
	}
	//--------------------------------------
	public static void MsgErrWait( String iTxt ) {
		MsgAlertWait( DialogMsgType.ERR, iTxt);
	}
	//--------------------------------------
	public static void MsgWarnWait(  String iTxt ) {
		MsgAlertWait( DialogMsgType.WARN, iTxt);
	}
	//--------------------------------------
	public static void MsgInfoWait( String iTxt ) {
		MsgAlertWait( DialogMsgType.INFO, iTxt);
	}
	//--------------------------------------
	public static void MsgAlertWait( DialogMsgType iType, String iTxt ) {
		MsgAlertWait( AppliFx.sInstance.getPrimStage(), iType, iTxt  );
	}
	//--------------------------------------
	public static void MsgErrWait(  Stage iOwner, String iTxt ) {
	    MsgAlertWait( iOwner,DialogMsgType.ERR, iTxt);
	}
	//--------------------------------------
	public static void MsgWarnWait(   Stage iOwner, String iTxt ) {
	    MsgAlertWait( iOwner,DialogMsgType.WARN, iTxt);
	}
	//--------------------------------------
	public static void MsgInfoWait(  Stage iOwner, String iTxt ) {
	    MsgAlertWait( iOwner, DialogMsgType.INFO, iTxt);
	}
	//--------------------------------------
	public static void MsgAlertWait(  Stage iOwner, DialogMsgType iType, String iTxt ) {
		Alert lAlert = new javafx.scene.control.Alert( iType.cType, iTxt );
		if( iOwner != null ) {
			lAlert.initOwner( iOwner );
		}
		lAlert.showAndWait();
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public static Alert MsgAlert( DialogMsgType iType, String iTxt ) {
	    return MsgAlert(  AppliFx.sInstance.getPrimStage(), DialogMsgType.INFO, iTxt);
	}
	//--------------------------------------
	public static Alert MsgAlert(  Stage iOwner, DialogMsgType iType, String iTxt ) {
		Alert lAlert = new javafx.scene.control.Alert( iType.cType, iTxt );
		lAlert.initOwner( iOwner );
		return lAlert;
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public static boolean MsgAlertWaitConfirm( String iTxt ) {

	    return MsgAlertWaitConfirm(  AppliFx.sInstance.getPrimStage(), iTxt );
	}
	//--------------------------------------
	public static boolean MsgAlertWaitConfirm(  Stage iOwner, String iTxt ) {
		Alert lAlert = new javafx.scene.control.Alert( AlertType.CONFIRMATION, iTxt );
		lAlert.initOwner( iOwner );
		Optional<ButtonType> lResult = lAlert.showAndWait();
		return (lResult.get() == ButtonType.OK);
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public static TextArea AddText2Alert( Alert iAlert, String iLabel, String iStr, boolean iIsEditable ) {

		TextArea lTextArea = new TextArea( iStr );

		lTextArea.setEditable(iIsEditable);
		lTextArea.setWrapText(true);

		lTextArea.setMaxWidth(Double.MAX_VALUE);
		lTextArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(lTextArea, Priority.ALWAYS);
		GridPane.setHgrow(lTextArea, Priority.ALWAYS);

		GridPane lContent = new GridPane();
		lContent.setMaxWidth(Double.MAX_VALUE);
		if( iLabel != null ) {
			Label label = new Label( iLabel );
			lContent.add( label, 0, 0);
		}
		lContent.add( lTextArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		iAlert.getDialogPane().setContent(lContent);
		return lTextArea;
	}
	//--------------------------------------
	public static String DialogEdit( String iTitle, String iLabel, String iStr, boolean iIsEditable ) {

		Alert lAlert = MsgAlert( (iIsEditable ? DialogMsgType.OK_CANCEL : DialogMsgType.INFO), iLabel );
		TextArea lText = AddText2Alert( lAlert, iLabel, iStr, iIsEditable  );
		Optional<ButtonType> lResult = lAlert.showAndWait();
		if( lResult != null && lResult.isPresent() && lResult.get() == ButtonType.OK ) {
			return lAlert.getContentText();
		}
		return null;
	}
	//--------------------------------------
	// https://news.kynosarges.org/2014/05/01/simulating-platform-runandwait/
	public static void RunAndWait(Runnable action) {

		// run synchronously on JavaFX thread
	    if (Platform.isFxApplicationThread()) {
	        action.run();
	        return;
	    }

	    // queue on JavaFX thread and wait for completion
	    final CountDownLatch doneLatch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        try {
	            action.run();
	        } finally {
	            doneLatch.countDown();
	        }
	    });

	    try {
	        doneLatch.await();
	    } catch (InterruptedException e) {
	        // ignore exception
	    }
	}
	//--------------------------------------
}

//*********************************************************
