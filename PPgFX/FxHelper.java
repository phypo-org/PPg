package org.phypo.PPg.PPgFX;

import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.phypo.PPg.PPgUtils.Log;
import org.phypo.PPg.PPgUtils.PPgIniFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


//*********************************************************

public class FxHelper {
	
	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ, TYPE> addColumn( TableView<OBJ> iTable, String iLabel, String iVarName ) {

		TableColumn<OBJ, TYPE> lCol = new TableColumn<OBJ, TYPE>(iLabel);
		lCol.setCellValueFactory(new PropertyValueFactory<OBJ,TYPE>((String) iVarName));
		iTable.getColumns().add(lCol);
		return lCol;
	}	
	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ, TYPE> addTopColumn( TableView<OBJ> iTable, String iLabel ){
		TableColumn<OBJ, TYPE> lCol   = new TableColumn<OBJ, TYPE>(iLabel);
		iTable.getColumns().add(lCol);
		return lCol;
	}	
	//--------------------------------------------
	public static <OBJ,TYPE>  TableColumn<OBJ,TYPE> addSubColumn(  TableColumn<OBJ,TYPE> iCol, String iLabel, String iVarName  ){
		TableColumn<OBJ, TYPE> lCol = new TableColumn<OBJ, TYPE>(iLabel);
		lCol.setCellValueFactory(new PropertyValueFactory<OBJ,TYPE>((String) iVarName));
		iCol.getColumns().add(lCol);
		return lCol;
	}
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	public static MenuItem AddMenuItem( Menu iMenu, String iLabel, EventHandler<ActionEvent> iAction ) {
		MenuItem lItem = new MenuItem( iLabel); 
		iMenu.getItems().add(lItem); 
		lItem.setOnAction( iAction );
		return lItem;
	}
	public static MenuItem AddMenuItem( Menu iMenu, String iLabel) {
		MenuItem lItem = new MenuItem( iLabel); 
		iMenu.getItems().add(lItem); 
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
	public static MenuItem AddMenuSeparator( Menu iMenu) {
		 iMenu.getItems().add(new SeparatorMenuItem());
		return iMenu;
	}
	//-------------------------------------------
	public static CheckMenuItem AddMenuCheckBox( Menu iMenu, String iLabel ) {
		CheckMenuItem lItem = new  CheckMenuItem( iLabel );
		iMenu.getItems().add(lItem);
		return lItem;
	}

	
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	//------------------------------------------------------------------
	
	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip ) {
		return CreateToggle( iLabel, iGroup, iTip, null );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel,  String iTip, Image iImage  ) {
		return CreateToggle( iLabel, null, iTip, iImage );
	}
	//-------------------------------------------
	public static ToggleButton CreateToggle( String iLabel, ToggleGroup iGroup, String iTip, Image iImage ) {

		ToggleButton lToggle   = new ToggleButton( iLabel);
		
		if( iGroup != null )
			lToggle.setToggleGroup(iGroup);
		
		if( iTip != null )
			lToggle.setTooltip( new Tooltip( iTip ));
		
		if( iImage != null ) {
			lToggle.setGraphic( new ImageView( iImage) );
		}
		
		return lToggle;
	}
	//-------------------------------------------
	//-------------------------------------------
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
	public static void SetButtonImage( Button iButton, Image iImg ) {
		iButton.setGraphic( new ImageView( iImg) );
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
				Log.Dbg( "V "+ " Flip:" + lFlipH + " " + lFlipV);
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
				lWidth = lHeight = 0;	
				lScale = 1;
			}				

			//						Log.Dbg( "ReadIcon Str:" + lStr + " Size:" + lSize  
			//																+ " lWidth:" + lWidth + " lHeight:" + lHeight + " lScale:" + lScale );

		}

		if( lStr == null || lStr.length() == 0 ){
			Log.Err( "PPIniFile.Image return null .size error for " 	+ pSection + ":" + pKey );
			return null;
		}

		Log.Dbg("Name:" + lStr );

		Image lImage = null;

		
		if( lWidth >0 && lHeight >0 ){																
			lImage = new Image( lStr, lWidth, lHeight, true, true, false );								
		}
		else 
			lImage = new Image( lStr  );								

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
}
//*********************************************************
