package org.phypo.PPg.PPgFX;

import java.util.function.Predicate;

import org.phypo.PPg.PPgUtils.Log;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


//************************************************
public class TreeViewFX<KEY, OBJ extends DataViewObj>   extends DataViewFx <KEY, OBJ > {
	
	private TreeView<DataViewObj>   cTree;
	public TreeItem<DataViewObj>    cRootItem = new TreeItem<DataViewObj>();
	OBJ                             cPrototype;
//	TableViewFX<KEY, OBJ>           cTable;
//	SplitPane                       cSplit; 
	
	public TreeView<DataViewObj>  getTree() { return cTree; }
//	public TableViewFX<KEY, OBJ>                  getTable() { return cTable; }
	//--------------------------------------

	public TreeViewFX(String iTitle, OBJ iPrototype, DataViewObj iRoot ) {
		super( iTitle);
		cPrototype = iPrototype;
		cRootItem = new TreeItem<DataViewObj>(iRoot);
		cRootItem.setExpanded(true);	
		cTree = new TreeView<DataViewObj>(cRootItem);	
//		cTable = new TableViewFX<>();
//		cSplit =  new SplitPane();
//		cSplit.setOrientation(Orientation.HORIZONTAL);

//		cSplit.getItems().add( cTree );
//		cSplit.getItems().add( cTable.getPane() );
		
//		cPrimPane.setCenter( cSplit );
		cPrimPane.setCenter( cTree );
	}
	//--------------------------------------

	
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		cTree.refresh();
	}
	@Override
	public void setFilter(Predicate<OBJ> iPred) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMouveEventHandler() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clearSelection() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clearLines() {
		Log.Dbg( "TreeViewFX - clearLines" );
		cRootItem.getChildren().clear();
		// TODO Auto-generated method stub

	}
	/*
	@Override
	protected void writeSize2Foot() {
		// TODO Auto-generated method stub
		
	}
	*/
	public OBJ addLine(OBJ iObj) {
		// TODO Auto-generated method stub
		return iObj;
	}
	public void removeObject(OBJ iObj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	void writeSize2Foot() {
		// TODO Auto-generated method stub	
		Log.Err( "**************** TreeViewFX writeSize2Foot not implemented ****************");
	}

}
