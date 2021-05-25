package org.phypo.PPg.PPgFX;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


//************************************************
public abstract class TreeFX<KEY, OBJ extends TreeFXObjInterface<KEY>>   extends DataViewFx <KEY, OBJ > {
	
	TreeView<TreeFXObjInterface<KEY>>           cTree;
	public TreeItem<TreeFXObjInterface<KEY>>    cRootItem = new TreeItem<TreeFXObjInterface<KEY>>();
	OBJ                                         cPrototype;
	
	//--------------------------------------

	public TreeFX(String iTitle, OBJ iPrototype, TreeFXObjInterface<KEY> iRoot, boolean iFilter) {
		super( iTitle);
		cPrototype = iPrototype;
		cRootItem = new TreeItem<TreeFXObjInterface<KEY>>(iRoot);
		cRootItem.setExpanded(true);	
		cTree = new TreeView<TreeFXObjInterface<KEY>>(cRootItem);		
		cPrimPane.setCenter( cTree );
	}
	//--------------------------------------
	
	protected abstract void writeSize2Foot();

	
	@Override
	protected void refreshView() {
		// TODO Auto-generated method stub
		cTree.refresh();
	}
	@Override
	protected void setFilter(Predicate<OBJ> iPred) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void setMouveEventHandler() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void selectAll() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void clearSelection() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void clearLines() {
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

}
