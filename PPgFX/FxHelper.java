package org.phypo.PPg.PPgFX;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
}
//*********************************************************
