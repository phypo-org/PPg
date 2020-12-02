package org.phypo.PPg.PPgFX;

import java.io.File;
import java.util.Collection;
import org.phypo.PPg.PPgUtils.Log;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

//***********************************
public class TableFX<OBJ> extends BorderPane{

	protected TableView<OBJ>       cTable = new TableView<OBJ>();
	protected ObservableList<OBJ>  cList  = FXCollections.observableArrayList();
	protected Label                cFootLabel = new Label("no row");

	protected TableView<OBJ>      getTableView() { return cTable; }
	protected ObservableList<OBJ> getContainer() { return cList; }

	static File sDirCSV=null;

	long cFlagAutoMenu = 0;
	public static final long MENU_SELECTION = 1;
	public static final long MENU_CSV=2;

	public void addAutoMenu( long iFlag ) { cFlagAutoMenu |= iFlag; }

	//--------------------------------------------	
	protected String cStrSelectAll         = "Select All";
	protected String cStrUnselectAll       = "Unselect All";
	protected String cStrRemoveSelection   = "Remove selection";	 
	//--------------------------------------------	
	public void addSelectMenuItems( ContextMenu iMenu, MouseEvent iEv) {

		FxHelper.AddMenuItem( iMenu, cStrSelectAll,  new EventHandler<ActionEvent>() {
			//=========================
			public void handle( ActionEvent iEv) {
				selectAll();
			}		    
			//=========================
		});
		FxHelper.AddMenuItem( iMenu, cStrUnselectAll,  new EventHandler<ActionEvent>() {
			//=========================
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

	public void addCSVMenuItems( ContextMenu iMenu, MouseEvent iEv) {

		{
			MenuItem lItem = new MenuItem( cStrExport2FileCSV );
			iMenu.getItems().add( lItem );

			lItem.setOnAction( new EventHandler<ActionEvent>() {
				//=========================
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
	public boolean addPopupMenuItems( ContextMenu iMenu, MouseEvent iEv ) {
		return true;
	}
	//--------------------------------------------
	public void doubleClick( MouseEvent iEv, OBJ lItem, int iPosItem  ) {	
	}
	//--------------------------------------------
	public TableFX( ){
		cTable.setItems(cList);
		setCenter( cTable );
		setBottom( cFootLabel);
		Log.Dbg("TableFX");

		cTable.addEventHandler( MouseEvent.MOUSE_PRESSED,

				new EventHandler<MouseEvent>() {
			//=========================				
			@Override
			public void handle(MouseEvent iEv) {
				Log.Dbg("TableFx handle(MouseEvent " + iEv);


				if(iEv.getButton().equals(MouseButton.PRIMARY)){
					if(iEv.getClickCount() == 2){
						OBJ lItem  = getSelectedItem();
						int lPosItem = getSelectedIndex();
						doubleClick( iEv, lItem, lPosItem );				           
						System.out.println("Double clicked");
					}
				}
				else if( iEv.getButton().equals(MouseButton.SECONDARY)) { 
					Log.Dbg("TableFx handle secondary");

					ContextMenu lMenu = new ContextMenu();
					lMenu.setOnAutoHide(null);

					if( addPopupMenuItems( lMenu, iEv) ){	
						lMenu.show(cTable.getScene().getWindow(),iEv.getScreenX(), iEv.getScreenY());
					}
					if( (cFlagAutoMenu & MENU_SELECTION) != 0 ) addSelectMenuItems( lMenu, iEv );
					if( (cFlagAutoMenu & MENU_CSV) != 0 )       addCSVMenuItems( lMenu, iEv );					
				}
				//=========================
			}
			});
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
	//---------------------------------------------------------------
	//---------------------------------------------------------------
	//---------------------------------------------------------------
	public void clearLines()        { cTable.getItems().clear();}
	public int  getSize()           { return cList.size(); }
	//--------------------------------------------
	public OBJ addLine( OBJ iObj ) {
		cList.add( iObj);
		return iObj;
	}
	//--------------------------------------------
	public void addLines( Collection<OBJ> iCollect ) {
		for( OBJ lObj :  iCollect ) {
			addLine( lObj); 
		}	
		writeSize2Foot("");
	}
	//--------------------------------------------
	public OBJ getSelectedItem() {
		return cTable.getSelectionModel().getSelectedItem();
	}
	//--------------------------------------------
	public int getSelectedIndex() {
		return cTable.getSelectionModel().getSelectedIndex();
	}
	//--------------------------------------------
	public OBJ getItem( int iItemPos ) {

		ObservableList<OBJ> lList = cTable.getItems();
		 
		 try {
			 return lList.get(iItemPos);
		 } catch( IndexOutOfBoundsException ex)
		 {
		 
		 }
		 return null;
	}
	//--------------------------------------------
	public int size() { return cTable.getItems().size(); }
	//--------------------------------------------
	public int getIndexOf( OBJ iObj) {
		return  cTable.getItems().indexOf(iObj);
	}
	//--------------------------------------------
	public void clearSelection() {
		cTable.getSelectionModel().clearSelection();
	}
	//--------------------------------------------
	public void removeSelectedLine() {
		//	cTablesetEditable(true)		
		int lSelectedIndex = cTable.getSelectionModel().getSelectedIndex();
		if (lSelectedIndex >= 0) {
			cTable.getItems().remove(lSelectedIndex);	
		}
	}
	//--------------------------------------------
	public OBJ removeSelectedLineObject() {
		//	cTablesetEditable(true)		
		int lSelectedIndex = cTable.getSelectionModel().getSelectedIndex();
		if (lSelectedIndex >= 0) {
			OBJ lTmp = cTable.getItems().get( lSelectedIndex );			
			cTable.getItems().remove(lSelectedIndex);	
			return lTmp;
		}
		return null;
	}
	//--------------------------------------------
	public void removeObject( OBJ iObj ) {
		cTable.getItems().remove(iObj);	
	}
	//--------------------------------------------
	public void setSelectionMode( SelectionMode value) { 
		cTable.getSelectionModel().setSelectionMode(value );

		// SelectionMode.MULTIPLE);
	}
	//--------------------------------------------
	public void setSelectItem( OBJ iObj  ) {
		
		int lPos = getIndexOf( iObj );
		setSelectIndex( lPos );	
	}
	//--------------------------------------------
	public void scrollToIndex( int iIndex ) {
		cTable.scrollTo(iIndex);
	}
	//--------------------------------------------
	public void setSelectIndex( int iIndex ) {
		if( iIndex > 0 ) {
		Platform.runLater(() ->
		  {
		      cTable.requestFocus();
		      cTable.getSelectionModel().select(iIndex);
		      cTable.scrollTo(iIndex);
		  });
		}
	}
	//--------------------------------------------
	public void clearAndSelect(int iIndex) {
		cTable.getSelectionModel().clearAndSelect( iIndex);
	}
 	//--------------------------------------------
	public void selectAll() { 
		cTable.getSelectionModel().selectAll();		
		// SelectionMode.MULTIPLE);
	}
	//--------------------------------------------
	public ObservableList<OBJ> getSelectedItems(){ 
		return cTable.getSelectionModel().getSelectedItems();		
	}
	//--------------------------------------------
	public ObservableList<Integer> getSelectedIndexs(){
		return cTable.getSelectionModel().getSelectedIndices();
	}

}
//***********************************
