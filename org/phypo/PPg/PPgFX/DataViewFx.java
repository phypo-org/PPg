package org.phypo.PPg.PPgFX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;

import org.phypo.PPg.PPgUtils.PPgTrace;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

//***********************************

public abstract class DataViewFx<KEY, OBJ extends DataViewObj> {
	BorderPane cPrimPane = new BorderPane();
	ToolBar    cToolbar = null ;
	protected Label                cFootLabel = new Label("no row");
	protected String               cFootLastMsg = "";

	protected Label                cTitle = null;

	static File sDirCSV=null;

	boolean cAutoResizeWhenRefresh = false;

	long cFlagAutoMenu = 0;
	public static final long MENU_SELECTION = 1;
	public static final long MENU_CSV=2;

	//-------------------------------------
	protected DataViewFx( String iTitle ){
		if( iTitle != null ){
			cPrimPane.setTop( cTitle = new Label(iTitle) );
		}
		cPrimPane.setBottom( cFootLabel);


	}
	//-------------------------------------
	public ToolBar getToolbar(){
		if( cToolbar == null ) {
			cToolbar = new ToolBar();
			if( cTitle!= null ){
				cToolbar.getItems().add( cTitle );
			}

			cPrimPane.setTop( cToolbar );
		}
		return cToolbar;
	}
	//-------------------------------------
	public BorderPane getPane() { return cPrimPane; }
	//-------------------------------------
	public TextField addTextFilterToolbar( String iPromptText ) {
		TextField lFilterField = new TextField();
		if( iPromptText!=null && iPromptText.length() > 0 ) {
			lFilterField.setPromptText(iPromptText );
		}
		getToolbar().getItems().add( lFilterField );
		return lFilterField;
	}
	//-------------------------------------
	public TextField addTextFilterToolbar() {
		return addTextFilterToolbar( null);
	}
	//-------------------------------------
	public Label addLabelToolbar( String iStr ) {
		Label lLabel = new Label(iStr);
		getToolbar().getItems().add( lLabel );
		return lLabel;
	}
	//-------------------------------------
	public ComboBox<String> addComboTextFilterToolbar(){
		ComboBox<String> lFilter = new ComboBox<>();
		getToolbar().getItems().add(lFilter);
		return lFilter;
	}
	//-------------------------------------
	public ComboBox<Long> addComboLongFilterToolbar(){
		ComboBox<Long> lFilter = new ComboBox<>();
		getToolbar().getItems().add(lFilter);
		return lFilter;
	}
	//-------------------------------------
	public void addAutoMenu( long iFlag ) { cFlagAutoMenu |= iFlag; }
	//-------------------------------------
	protected String cStrSelectAll         = "Select All";
	protected String cStrUnselectAll       = "Unselect All";
	protected String cStrRemoveSelection   = "Remove selection";
	//--------------------------------------------
	public void addSelectMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ lItem, int iPosItem ) {

		FxHelper.AddMenuItem( iMenu, cStrSelectAll,  new EventHandler<ActionEvent>() {
			//=========================
			@Override
			public void handle( ActionEvent iEv) {
				selectAll();
			}
			//=========================
		});
		FxHelper.AddMenuItem( iMenu, cStrUnselectAll,  new EventHandler<ActionEvent>() {
			//=========================
			@Override
			public void handle( ActionEvent iEv) {
				clearSelection();
			}
			//=========================
		});
		/*
		FxHelper.AddMenuItem( iMenu, cStrRemoveSelection,  new EventHandler<ActionEvent>() {
			//=========================
			public void handle( ActionEvent iEv) {

			}
			//=========================
		});
		 */
	}
	//--------------------------------------------
	//Ajouter copy to Clipbaord
	//--------------------------------------------
	protected String cStrExport2FileCSV       = "Export all lines to csv file";
	protected String cStrExportSelect2FileCSV = "Export selected lines to csv file";

	public void addCSVMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ iObj, int iPosObj ) {

		{
			MenuItem lItem = new MenuItem( cStrExport2FileCSV );
			iMenu.getItems().add( lItem );

			lItem.setOnAction( new EventHandler<ActionEvent>() {
				//=========================
				@Override
				public void handle( ActionEvent iEv) {
					final FileChooser lFileChooser = new FileChooser();
					lFileChooser.setInitialDirectory(sDirCSV);
					lFileChooser.setTitle( cStrExport2FileCSV );
					File lFile = lFileChooser.showSaveDialog(iMenu);
					sDirCSV = lFileChooser.getInitialDirectory();
					//ObservableList<OBJ> getSelectedItems
					//Export2CSV( "export csv", , sDirCSV, cList);
				}
				//=========================
			});
		}

		{
			MenuItem lItem = new MenuItem( cStrExportSelect2FileCSV);
			iMenu.getItems().add( lItem );

			lItem.setOnAction( new EventHandler<ActionEvent>() {
				//=========================
				@Override
				public void handle( ActionEvent iEv) {
					final FileChooser lFileChooser = new FileChooser();
					lFileChooser.setInitialDirectory(sDirCSV);
					lFileChooser.setTitle( cStrExportSelect2FileCSV);
					File lFile = lFileChooser.showSaveDialog(iMenu);
					sDirCSV = lFileChooser.getInitialDirectory();
					//ObservableList<OBJ> getSelectedItems
					//	Export2CSV( "export csv", PPgFileChooser.GetFileName(, cCurrentPath, cList);
				}
				//=========================
			});
		}
	}
	//--------------------------------------------
	abstract void refreshView();
	abstract void setFilter( Predicate<OBJ> iPred );
	abstract void setMouveEventHandler();
	//--------------------------------------------
	abstract void selectAll();
	abstract void clearSelection();
	abstract void clearLines();

	abstract void writeSize2Foot();
	//--------------------------------------------
	//--------------------------------------------
	public boolean addPopupMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ lItem, int iPosItem  ) {
		return true;
	}
	//--------------------------------------------
	public void doubleClick( MouseEvent iEv, OBJ lItem, int iPosItem  ) {
	    //	System.out.println("Double clicked");
	}
	//--------------------------------------------
	public void simpleClick( MouseEvent iEv, OBJ lItem, int iPosItem  ) {
	    //		System.out.println("simple clicked");
	}
	//--------------------------------------------
	public void writeSize2Foot( String iLabel ) {
		cFootLastMsg = iLabel;
		writeSize2Foot();
	}
	//--------------------------------------------
	public void writeFoot( String iLabel ) {
		Platform.runLater( () -> {cFootLabel.setText( iLabel);});
	}
	//--------------------------------------------
	public String writeFile( String iTypeFile, File iFile, List<OBJ> iList ){
		PPgTrace.Dbg( "     DataView export2CSV" );

		try {
			FileOutputStream lOs = new FileOutputStream( iFile );
			PrintStream lFout = new PrintStream( lOs );
			int i = 0;
			for( OBJ lObj : iList) {
				String lErr = lObj.writeFile( iTypeFile, i, lFout );
				 PPgTrace.Warn(lErr );
			}
			lFout.close();
			lOs.close();

		}	catch(Exception e ) { PPgTrace.Err( e.toString() );
			 e.printStackTrace( PPgTrace.GetErr() );
			return e.getMessage();
			}
		return null;
	}
	//--------------------------------------------
	public Object getDataView() {
		// TODO Auto-generated method stub
		return null;
	}



}
