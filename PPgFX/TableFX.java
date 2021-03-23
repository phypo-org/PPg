package org.phypo.PPg.PPgFX;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.phypo.PPg.PPgUtils.Log;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

//***********************************
public class TableFX<OBJ> extends BorderPane{
	
	ToolBar cToolbar = null ;	
	//-------------------------------------
	public ToolBar getToolbar(){
		if( cToolbar == null ) {
			cToolbar = new ToolBar();
			if( cTitle!= null ){
				cToolbar.getItems().add( cTitle );
			}

			setTop( cToolbar );
		}
		return cToolbar;	
	}
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
		ComboBox<String> lFilter = new ComboBox<String>();
		getToolbar().getItems().add(lFilter);
		return lFilter;
	}
	//-------------------------------------
	public ComboBox<Long> addComboLongFilterToolbar(){
		ComboBox<Long> lFilter = new ComboBox<Long>();
		getToolbar().getItems().add(lFilter);
		return lFilter;
	}
	//-------------------------------------
	
	protected TableView<OBJ>       cTable = new TableView<OBJ>();
	protected ObservableList<OBJ>  cList  = FXCollections.observableArrayList();
	protected Label                cFootLabel = new Label("no row");
	protected String               cFootLastMsg = "";
	
	protected Label                cTitle = null;

	public TableView<OBJ>      getTableView() { return cTable; }
	public ObservableList<OBJ> getContainer() { return cList; }

	static File sDirCSV=null;

	long cFlagAutoMenu = 0;
	public static final long MENU_SELECTION = 1;
	public static final long MENU_CSV=2;
	
	
	// Only use with second constructor
	private FilteredList<OBJ>     cFilteredList = null;
	private SortedList<OBJ>       cSortedList   = null;
	
	
	
	//public FilteredList<OBJ> getFilteredList() { return cFilteredList; }
	public void setFilter( Predicate<OBJ> iPred ) { 
		cFilteredList.setPredicate( iPred );
		writeSize2Foot();
	}
	//--------------------------------------------
	
	public TableFX( String iTitle ){
		
		if( iTitle != null ){
			setTop( cTitle = new Label(iTitle) );
		}
		
		cTable.setItems(cList);
		setCenter( cTable );
		setBottom( cFootLabel);
		Log.Dbg("TableFX");
		
		setMouveEventHandler();
		
		setListenerSelectWriteFoot();
	}	
	//--------------------------------------------
	public TableFX( String iTitle, boolean iFilter ) {
			
		if( iTitle != null ){
			setTop( cTitle = new Label(iTitle) );
		}
			
		setCenter( cTable );
		setBottom( cFootLabel);
//		Log.Dbg("TableFX");

		setMouveEventHandler();
		
		
		if( iFilter ) {			
			cFilteredList = new FilteredList<>( cList, p -> true);
			cSortedList   = new SortedList<>(cFilteredList);
			cSortedList.comparatorProperty().bind(cTable.comparatorProperty());
			cTable.setItems( cSortedList );		
		}
		else {
			cTable.setItems(cList);
		}		
		setListenerSelectWriteFoot();
	}
	//--------------------------------------------
	protected void setListenerSelectWriteFoot() {
			cTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends OBJ> c) -> {
		
				// Platform.runLater( () -> { 
			writeSize2Foot(); 	
		});
	}
	//--------------------------------------------
	public void addAutoMenu( long iFlag ) { cFlagAutoMenu |= iFlag; }

	//--------------------------------------------	
	protected String cStrSelectAll         = "Select All";
	protected String cStrUnselectAll       = "Unselect All";
	protected String cStrRemoveSelection   = "Remove selection";	 
	//--------------------------------------------	
	public void addSelectMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ lItem, int iPosItem ) {

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

	public void addCSVMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ iObj, int iPosObj ) {

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
	public void refreshTable() {
		getTableView().refresh();
		writeSize2Foot();
	}
	//--------------------------------------------
	public void autoResizeColumns() {
		TableFxHelper.AutoResizeColumns( getTableView());
	}
	//--------------------------------------------
	public boolean addPopupMenuItems( ContextMenu iMenu, MouseEvent iEv, OBJ lItem, int iPosItem  ) {
		return true;
	}
	//--------------------------------------------
	public void doubleClick( MouseEvent iEv, OBJ lItem, int iPosItem  ) {	
		System.out.println("Double clicked");
	}
	public TableFX(){
		this(null);
	}
	//--------------------------------------------
	public void	setMouveEventHandler() {
		cTable.addEventHandler( MouseEvent.MOUSE_PRESSED,

				new EventHandler<MouseEvent>() {
			//=========================				
			@Override
			public void handle(MouseEvent iEv) {
				Log.Dbg("TableFx handle(MouseEvent " + iEv);

				OBJ lItem  = getSelectedItem();
				int lPosItem = getSelectedIndex();

				if(iEv.getButton().equals(MouseButton.PRIMARY)){
					if(iEv.getClickCount() == 2){
						System.out.println("Double clicked before");
						doubleClick( iEv, lItem, lPosItem );				           
						System.out.println("Double clicked after");
					}
				}
				else if( iEv.getButton().equals(MouseButton.SECONDARY)) { 
					Log.Dbg("TableFx handle secondary");

					ContextMenu lMenu = new ContextMenu();
					lMenu.setOnAutoHide(null);

					if( addPopupMenuItems( lMenu, iEv, lItem, lPosItem) ){	
						lMenu.show(cTable.getScene().getWindow(),iEv.getScreenX(), iEv.getScreenY());
					}
					if( (cFlagAutoMenu & MENU_SELECTION) != 0 ) addSelectMenuItems( lMenu, iEv, lItem,  lPosItem  );
					if( (cFlagAutoMenu & MENU_CSV) != 0 )       addCSVMenuItems   ( lMenu, iEv, lItem,  lPosItem );					
				}
				//=========================
			}
			});
	}
	//--------------------------------------------
	//--------------------------------------------
	public void setMultipleSelection( boolean iFlagMulti ) {
		if( iFlagMulti)
			cTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		else
			cTable.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
	
	}
	//--------------------------------------------
	public void writeSize2Foot() {		
		if( totalSize() == 0 ) {
			writeFoot( cFootLastMsg + " no row");
		}
		else {
		       //  Log.Info("*** TableFx.setListenerSelectWriteFoot - Selected items: "+);
			
			String lStrSelect = "";
			
			int lNbSelect = cTable.getSelectionModel().getSelectedItems().size();
			if( lNbSelect > 0 ) {
				lStrSelect = "["+lNbSelect +']';	
			}
				
					
			if( totalSize() != filterSize() ) {
				writeFoot( cFootLastMsg + filterSize() +'/'+ totalSize() + lStrSelect +" rows");
			}
			else
				writeFoot( cFootLastMsg + totalSize() + lStrSelect +" rows");
		}
	}
	//--------------------------------------------
	public void writeSize2Foot( String iLabel ) {
		cFootLastMsg = iLabel;
		writeSize2Foot();
	}
	//--------------------------------------------
	protected void writeFoot( String iLabel ) {
		Platform.runLater( () -> {cFootLabel.setText( iLabel);});				
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
	protected List<OBJ> getVisibleList(){
		if( cSortedList != null ) {
			return cSortedList;
		}
		return cList;		
	}
	//---------------------------------------------------------------
//	public void clearLines()        { }
	public void clearLines()        { 
	//	if( cFilteredList	== null )
	//		cTable.getItems().clear();
	//	else {
			cList.clear();
			//cFilteredList.getSource().remove(1,cList.size());
	//	}
	}
	public int  totalSize()           { return cList.size(); }
	//--------------------------------------------
	public int filterSize() { 
		if( cSortedList != null ) {
			return cSortedList.size() ;		
		} 
		return totalSize(); 
	}
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
	public int getIndexOf( OBJ iObj) {
		return  cTable.getItems().indexOf(iObj);
	}
	//--------------------------------------------
	public void clearSelection() {
		cTable.getSelectionModel().clearSelection();
	}
	//--------------------------------------------
	
	//--------------------------------------------
	public void removeSelectedLine() {
		//	cTablesetEditable(true)		
		int lSelectedIndex = cTable.getSelectionModel().getSelectedIndex();
		if (lSelectedIndex >= 0) {
			cTable.getItems().remove(lSelectedIndex);	
			writeSize2Foot("");
		}
	}
	//--------------------------------------------
	public OBJ removeSelectedLineObject() {
		//	cTablesetEditable(true)		
		int lSelectedIndex = cTable.getSelectionModel().getSelectedIndex();
		if (lSelectedIndex >= 0) {
			OBJ lTmp = cTable.getItems().get( lSelectedIndex );			
			cTable.getItems().remove(lSelectedIndex);	
			writeSize2Foot("");
			return lTmp;
		}
		return null;
	}
	//--------------------------------------------
	public void removeAllSelectedLines() {
	
	    ArrayList<Integer> list = new ArrayList<>( cTable.getSelectionModel().getSelectedIndices());

	    Comparator<Integer> comparator = Comparator.comparingInt(Integer::intValue);
	    comparator = comparator.reversed();
	    list.sort(comparator);

	    for(Integer i : list) {
	    	cTable.getItems().remove(i.intValue());
	    }
		writeSize2Foot("");
	}	
	//--------------------------------------------
	public void removeSelectedLinesByObject() {
		
		 ArrayList<OBJ> lListObj = new ArrayList<>( getSelectedItems() );
		 cList.removeAll( lListObj );
	}		
	//--------------------------------------------
	public ArrayList<OBJ>removeAndGetAllSelectedLines() {
		
		 ArrayList<OBJ> lListObj = new ArrayList<>( getSelectedItems() );
		 removeAllSelectedLines();
		 return lListObj;
	}		
	//--------------------------------------------
	public void removeObject( OBJ iObj ) {
		cTable.getItems().remove(iObj);	
		writeSize2Foot("");
	}
	//--------------------------------------------
	public void removeIndex( int iPos ) {
		cTable.getItems().remove(iPos);	
		writeSize2Foot("");
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
	public void scrollToItem( OBJ iObj  ) {
		int lPos = getIndexOf( iObj );
		cTable.scrollTo(lPos);
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
