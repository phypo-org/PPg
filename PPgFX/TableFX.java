package org.phypo.PPg.PPgFX;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.phypo.PPg.PPgUtils.Log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

//***********************************
public class TableFX<OBJ> extends BorderPane{

	TableView<OBJ>       cTable = new TableView<OBJ>();
	ObservableList<OBJ>  cList  = FXCollections.observableArrayList();
	Label                cFootLabel = new Label("no row");

	public TableView<OBJ>      getTableView() { return cTable; }
	public ObservableList<OBJ> getContainer() { return cList; }

	static File sDirCSV=null;

	//--------------------------------------------	
	final String cStrSelectAll   = "Select All";
	final String cStrExport2FileCSV = "Export all lines to csv file";
	final String cStrExportSelect2FileCSV = "Export selected lines to csv file";

	public boolean addPopupMenuItems( ContextMenu iMenu, MouseEvent iEv) {

		{
			MenuItem lItem = new MenuItem( cStrSelectAll );
			iMenu.getItems().add( lItem );			
		}


		{
			MenuItem lItem = new MenuItem( cStrSelectAll );
			iMenu.getItems().add( lItem );

			lItem.setOnAction( new EventHandler<ActionEvent>() {
				//=========================
				public void handle( ActionEvent iEv) {
					final FileChooser lFileChooser = new FileChooser();
					lFileChooser.setInitialDirectory(sDirCSV);
					lFileChooser.setTitle( "Save all lines to CSV file");
					File lFile = lFileChooser.showSaveDialog(iMenu);
					sDirCSV = lFileChooser.getInitialDirectory();		        
					//	Export2CSV( "export csv", PPgFileChooser.GetFileName(, cCurrentPath, cList);															
				}		    
				//=========================
			});
		} 


		{
			MenuItem lItem = new MenuItem( cStrExport2FileCSV);
			iMenu.getItems().add( lItem );

			lItem.setOnAction( new EventHandler<ActionEvent>() {
				//=========================
				public void handle( ActionEvent iEv) {
					final FileChooser lFileChooser = new FileChooser();
					lFileChooser.setInitialDirectory(sDirCSV);
					lFileChooser.setTitle( "Save all lines to CSV file");
					File lFile = lFileChooser.showSaveDialog(iMenu);
					sDirCSV = lFileChooser.getInitialDirectory();		        
					//	Export2CSV( "export csv", PPgFileChooser.GetFileName(, cCurrentPath, cList);															
				}		    
				//=========================
			});
		}

		return true;
	}
	//--------------------------------------------

	//--------------------------------------------
	public TableFX( ){
		cTable.setItems(cList);
		setCenter( cTable );
		setBottom( cFootLabel);
		Log.Dbg("TableFX");

		/*
		cTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent iEv) {
				Log.Dbg("handle(ContextMenuEvent)  " + iEv );

	//			event.consume();
			}
		});
		 */
		//	 SeparatorMenuItem 
		{      
			cTable.addEventHandler( MouseEvent.MOUSE_PRESSED,
				
					new EventHandler<MouseEvent>() {
				//=========================				
				@Override
				public void handle(MouseEvent iEv) {

					Log.Dbg("TableFx handle(MouseEvent " + iEv);

					if( iEv.getButton() == MouseButton.SECONDARY) {
						Log.Dbg("TableFx handle secondary");

						ContextMenu lMenu = new ContextMenu();
						lMenu.setOnAutoHide(null);
						if( addPopupMenuItems( lMenu, iEv) ){	
							lMenu.show(cTable.getScene().getWindow(),iEv.getScreenX(), iEv.getScreenY());
						}
					}
				}
				//=========================
			}
					);
		}
	}
	//--------------------------------------------
	void setMultipleSelection( boolean iFlagMulti ) {
		if( iFlagMulti)
			cTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		else
			cTable.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
	}
	//--------------------------------------------
	public void writeSize2Foot( String iLabel ) {
		if( getSize() == 0 )
			write2Foot( iLabel + "no row");
		else
			write2Foot( iLabel + getSize() + " rows");
	}
	//--------------------------------------------
	public void write2Foot( String iLabel ) {
		cFootLabel.setText( iLabel);
	}
	//--------------------------------------------
	public <TYPE>  TableColumn<OBJ, TYPE> addColumn(  String iLabel, String iVarName ){
		return  FxHelper.addColumn( cTable, iLabel, iVarName );
	}
	//--------------------------------------------
	public <TYPE>  TableColumn<OBJ, TYPE> addTopColumn( String iLabel ){
		return  FxHelper.addTopColumn( cTable, iLabel );
	}	
	//--------------------------------------------
	public <TYPE>  TableColumn<OBJ,TYPE> addSubColumn( TableColumn<OBJ,TYPE> iCol, String iLabel, String iVarName  ){
		return FxHelper.addSubColumn( iCol, iLabel, iVarName);
	}
	//--------------------------------------------
	public void clearLines()        { cTable.getItems().clear();}
	public int  getSize()           { return cList.size(); }
	public void addLine( OBJ iObj ) {
		cList.add( iObj); 
	}
	public void addLines( Collection<OBJ> iCollect ) {
		for( OBJ lObj :  iCollect ) {
			cList.add( lObj); 
		}	
		writeSize2Foot("");
	}
	//--------------------------------------------
}
//***********************************
