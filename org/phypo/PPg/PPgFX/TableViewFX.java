package org.phypo.PPg.PPgFX;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.phypo.PPg.PPgUtils.PPgTrace;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

//***********************************
public class TableViewFX<KEY, OBJ extends DataViewObj>  extends DataViewFx <KEY, OBJ > {

	protected TableView<OBJ>    cTable = new TableView<>();
	public ObservableList<OBJ>  cList  = null;

	public TableView<OBJ>      getTableView() { return cTable; }
	public ObservableList<OBJ> getContainer() { return cList; }

	boolean cAutoResize = false;

	// Only use with second constructor
	private FilteredList<OBJ>     cFilteredList = null;
	private SortedList<OBJ>       cSortedList   = null;

	//--------------------------------------------
	@Override
	public void setFilter( Predicate<OBJ> iPred ) {
		cFilteredList.setPredicate( iPred );
		writeSize2Foot();
	}
	//--------------------------------------------
	public TableViewFX(){
		this(null, false, null);
	}
	//--------------------------------------------
	public TableViewFX( ObservableList<OBJ>  iList){
		this(null, false, iList);
	}
	//--------------------------------------------
	public TableViewFX( String iTitle ){
		this( iTitle, false );
	}
	//--------------------------------------------
	public TableViewFX( String iTitle, ObservableList<OBJ>  iList ){
		this( iTitle, false, iList );
	}
	//--------------------------------------------
	public TableViewFX( String iTitle, boolean iFilter ) {
		this( iTitle, iFilter, null);
	}
	//--------------------------------------------
	public TableViewFX( String iTitle, boolean iFilter, ObservableList<OBJ>  iList ) {
		super( iTitle );

		if(  iList == null ) {
			cList  = FXCollections.observableArrayList();
		} else {
			cList = iList;
		}


		cPrimPane.setCenter( cTable );
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
	public void setAutoResize() { setAutoResize(true);}
	public void setAutoResize( boolean iResize ) { cAutoResize = iResize;}
	//--------------------------------------------
	@Override
	public void refreshView() {

		PPgTrace.Dbg( "       TableViewFX.refreshView ");

		if( cAutoResize )
			autoResizeColumns();

		getTableView().refresh();
		writeSize2Foot();
	}
	//--------------------------------------------
	public void autoResizeColumns() {
		TableFxHelper.AutoResizeColumns( getTableView());
	}
	//--------------------------------------------
	public  void setSortable( Boolean iFlagSort ) {
		TableFxHelper.SetSortable( getTableView(), iFlagSort );
	}
	//--------------------------------------------
	@Override
	public void	setMouveEventHandler() {
		cTable.addEventHandler( MouseEvent.MOUSE_PRESSED,

				new EventHandler<MouseEvent>() {
			//=========================
			@Override
			public void handle(MouseEvent iEv) {
				PPgTrace.Dbg("TableFx handle(MouseEvent " + iEv);

				OBJ lItem  = getSelectedItem();
				int lPosItem = getSelectedIndex();

				if(iEv.getButton().equals(MouseButton.PRIMARY)){
					if(iEv.getClickCount() == 2){
						doubleClick( iEv, lItem, lPosItem );
					}
					else {
						simpleClick( iEv, lItem, lPosItem );
					}
				}
				else if( iEv.getButton().equals(MouseButton.SECONDARY)) {
					PPgTrace.Dbg("TableFx handle secondary");

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
	@Override
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
	public List<OBJ> getVisibleList(){
		if( cSortedList != null ) {
			return cSortedList;
		}
		return cList;
	}
	//---------------------------------------------------------------
	@Override
	public void clearLines()        {
		PPgTrace.Dbg( "TableFx.clearLines" );
		cList.clear();
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
	public OBJ addLine2Pos( int iPos, OBJ iObj ) {
		cList.add( iPos, iObj );
		return iObj;
	}
	//--------------------------------------------
	public void addLines2Pos( int iPos, Collection<OBJ> iCollect ) {
		cList.addAll( iPos, iCollect);
		writeSize2Foot("");
	}
	//--------------------------------------------
	public void addLines( Collection<OBJ> iCollect ) {

		cList.addAll( iCollect);
		//for( OBJ lObj :  iCollect ) {
		//			addLine( lObj);
		//	}
		writeSize2Foot("");
	}
	//--------------------------------------------
	public OBJ getSelectedItem() {
		return cTable.getSelectionModel().getSelectedItem();
	}
	//--------------------------------------------
	public ObservableList<TablePosition> getSelectedCell() {
		return cTable.getSelectionModel().getSelectedCells();
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
	@Override
	public void clearSelection() {
		cTable.getSelectionModel().clearSelection();
	}
	//--------------------------------------------

	//--------------------------------------------
	/*public void removeSelectedLine() {
		//	cTablesetEditable(true)
		int lSelectedIndex = cTable.getSelectionModel().getSelectedIndex();
		if (lSelectedIndex >= 0) {
			cTable.getItems().remove(lSelectedIndex);
			writeSize2Foot("");
		}
	}
	 */
	//--------------------------------------------
	/*
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
	 */
	//--------------------------------------------

	public void removeAllSelectedLines() {

		ArrayList<Integer> list = new ArrayList<>( cTable.getSelectionModel().getSelectedIndices());

		Comparator<Integer> comparator = Comparator.comparingInt(Integer::intValue);
		comparator = comparator.reversed();
		list.sort(comparator);

		for(Integer i : list) {
			cTable.getItems().remove(i.intValue()); //    UnsupportedOperationException !!!!!!!!!
		}
		writeSize2Foot("");
	}

	//--------------------------------------------
	public ArrayList<OBJ>  removeSelectedLinesByObject() {

		ArrayList<OBJ> lListObj = new ArrayList<>( getSelectedItems() );
		cList.removeAll( lListObj );
		return lListObj;
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
	@Override
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
	//--------------------------------------------
	public void select( List<OBJ>  iList ) {

		for( OBJ lObj :  iList )
			cTable.getSelectionModel().select( lObj );
	}
	//--------------------------------------------
	public void selectPos( int iPos ) {
		cTable.getSelectionModel().select( iPos );
	}
	//--------------------------------------------
	public void selectPos( List<Integer>  iList ) {

		for( Integer lIndice :  iList )
			cTable.getSelectionModel().select( lIndice );
	}
	//--------------------------------------------
	public String export2CSV( File iFile, String iType, boolean iFlagSelection) {

		PPgTrace.Dbg( "   TableFx export2CSV" );

		List<OBJ> lList = null;
		if( iFlagSelection ) {
			lList = getSelectedItems();
		} else
		{
			lList = getContainer();
		}
		return  writeFile( iType, iFile, lList);
	}
}
//***********************************
