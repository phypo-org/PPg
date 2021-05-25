package org.phypo.PPg.PPgFX;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.StringTokenizer;

import org.phypo.PPg.PPgUtils.Log;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

// https://gist.github.com/Roland09/6fb31781a64d9cb62179

//**********************************************************

public class TableFxHelper {

	private static NumberFormat numberFormatter = NumberFormat.getNumberInstance();

	//--------------------------------------
	public static void installCopyPasteHandler(TableView<?> iTable) {

		// install copy/paste keyboard handler
		iTable.setOnKeyPressed(new TableKeyEventHandler());
	}
	//--------------------------------------
	public static class TableKeyEventHandler implements EventHandler<KeyEvent> {

		KeyCodeCombination copyKeyCodeCompination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
		KeyCodeCombination pasteKeyCodeCompination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_ANY);

		public void handle(final KeyEvent keyEvent) {

			if (copyKeyCodeCompination.match(keyEvent)) {

				if( keyEvent.getSource() instanceof TableView) {
					copySelectionToClipboard( (TableView<?>) keyEvent.getSource());// copy to clipboard
					keyEvent.consume(); // event is handled, consume it
				}
			} 
			else if (pasteKeyCodeCompination.match(keyEvent)) {

				if( keyEvent.getSource() instanceof TableView) {					
					pasteFromClipboard( (TableView<?>) keyEvent.getSource());// copy to clipboard					
					keyEvent.consume();// event is handled, consume it
				}
			} 
		}
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public static void copySelectionToClipboard(TableView<?> table) {

		StringBuilder clipboardString = new StringBuilder();

		ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();

		int prevRow = -1;

		for (TablePosition position : positionList) {

			int row = position.getRow();
			int col = position.getColumn();

			// determine whether we advance in a row (tab) or a column
			// (newline).
			if (prevRow == row) {
				clipboardString.append('\t');
			} else if (prevRow != -1) {
				clipboardString.append('\n');
			}

			// create string from cell
			String text = "";
			Object observableValue = (Object) table.getColumns().get(col).getCellObservableValue( row);

			// null-check: provide empty string for nulls
			if (observableValue == null) {
				text = "";
			}
			else if( observableValue instanceof DoubleProperty) { // TODO: handle boolean etc
				text = numberFormatter.format( ((DoubleProperty) observableValue).get());
			}
			else if( observableValue instanceof IntegerProperty) { 
				text = numberFormatter.format( ((IntegerProperty) observableValue).get());
			}			    	
			else if( observableValue instanceof StringProperty) { 
				text = ((StringProperty) observableValue).get();
			}
			else {
				Log.Err("copySelectionToClipboard _ Unsupported observable value: " + observableValue);
			}

			clipboardString.append(text);	// add new item to clipboard

			prevRow = row;			// remember previous
		}

		// create clipboard content
		final ClipboardContent clipboardContent = new ClipboardContent();
		clipboardContent.putString(clipboardString.toString());

		// set clipboard content
		Clipboard.getSystemClipboard().setContent(clipboardContent);
	}
	//--------------------------------------
	public static void pasteFromClipboard( TableView<?> table) {

		if( table.getSelectionModel().getSelectedCells().size() == 0) {// abort if there's not cell selected to start with
			return;
		}
		//--------------------------------------
		// get the cell position to start with
		TablePosition pasteCellPosition = table.getSelectionModel().getSelectedCells().get(0);

		Log.Dbg("Pasting into cell " + pasteCellPosition);

		String pasteString = Clipboard.getSystemClipboard().getString();

		Log.Dbg(pasteString);

		int rowClipboard = -1;

		StringTokenizer rowTokenizer = new StringTokenizer( pasteString, "\n");
		while( rowTokenizer.hasMoreTokens()) {

			rowClipboard++;

			String rowString = rowTokenizer.nextToken();
			StringTokenizer columnTokenizer = new StringTokenizer( rowString, "\t");
			int colClipboard = -1;

			while( columnTokenizer.hasMoreTokens()) {
				colClipboard++;				
				String clipboardCellContent = columnTokenizer.nextToken();// get next cell data from clipboard

				// calculate the position in the table cell
				int rowTable = pasteCellPosition.getRow() + rowClipboard;
				int colTable = pasteCellPosition.getColumn() + colClipboard;


				if( rowTable >= table.getItems().size()) { // skip if we reached the end of the table
					continue;
				}
				if( colTable >= table.getColumns().size()) {
					continue;
				}

				// get cell
				TableColumn tableColumn = table.getColumns().get(colTable);
				ObservableValue observableValue = tableColumn.getCellObservableValue(rowTable);

				Log.Dbg( rowTable + "/" + colTable + ": " +observableValue);

				// TODO: handle boolean, etc
				if( observableValue instanceof DoubleProperty) { 

					try {
						double value = numberFormatter.parse(clipboardCellContent).doubleValue();
						((DoubleProperty) observableValue).set(value);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if( observableValue instanceof IntegerProperty) { 

					try {
						int value = NumberFormat.getInstance().parse(clipboardCellContent).intValue();
						((IntegerProperty) observableValue).set(value);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}			    	
				else if( observableValue instanceof StringProperty) { 

					((StringProperty) observableValue).set(clipboardCellContent);

				} else {

					Log.Err("pasteFromClipboard - Unsupported observable value: " + observableValue);
				}
				Log.Dbg(rowTable + "/" + colTable);
			}
		}
	}


	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
/*
	private static Method columnToFitMethod;
	static {
		try {
			columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
			columnToFitMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static <OBJ> void AutoFitTable(TableView<OBJ> iTableView) {
		
		iTableView.getItems().addListener( new ListChangeListener<OBJ>() {
			
			@Override
			public void onChanged(Change<? extends OBJ> iArg) {
				for (Object column : iTableView.getColumns()) {
					try {
						columnToFitMethod.invoke(iTableView.getSkin(), column, -1);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}			
			}
		});
	}
	*/
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	
	public static void AutoResizeColumns( TableView<?> table )
	{
	    //Set the right policy
	    table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
	    table.getColumns().stream().forEach( (column) ->
	    {
        	Log.Dbg( column.getText().toString() );
	        //Minimal width = columnheader
	        Text t = new Text( column.getText() );
	        double max = t.getLayoutBounds().getWidth();
	        for ( int i = 0; i < table.getItems().size(); i++ )
	        {
	            //cell must not be empty
	            if ( column.getCellData( i ) != null ){
	                t = new Text( column.getCellData( i ).toString() );
	                double calcwidth = t.getLayoutBounds().getWidth();
	                
	   //         	if( calcwidth > column.getPrefWidth() )
	     //       		calcwidth = column.getPrefWidth();
	                //remember new max-width
	                if ( calcwidth > max ) {	                
		                    max = calcwidth;
	                }
	            }
	        }
	        //set the new max-widht with some extra space
	        column.setPrefWidth( max + 10.0d );
	    } );
	}

	//--------------------------------------
	public static void SetSortable( TableView<?> table, Boolean iFlagSort )
	{
	    table.getColumns().stream().forEach( (iColumn) ->
	    {
	        iColumn.setSortable(false);	
	    } );
	}
}
//********************************************

