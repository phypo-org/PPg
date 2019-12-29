package org.phypo.PPg.PPgWin;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

//**********************************************************
public class PPgTable extends AbstractTableModel {
	 		
		
    protected PPgTableLine cPrototype = null;
    protected List<PPgTableLine>        cTableObj    = new ArrayList<PPgTableLine>();
    protected JTable      cTable       = null;
    protected boolean     cIsEditable = true;
    protected TableRowSorter cRowFilter = null;

    public List<PPgTableLine> getList()     { return cTableObj; }
    public void setList(ArrayList<PPgTableLine> pList) {  cTableObj = pList; }
    public JTable getJTable() { return cTable; }

    public TableRowSorter getRowFilter() {  return cRowFilter; }

    public void setTableEditable( boolean pBool ) { cIsEditable = pBool; }
    //-------------
    public void clear() { cTableObj.clear(); }
    //-------------
    public PPgTable(PPgTableLine pPrototype, List<PPgTableLine> pList){
				
	cPrototype = pPrototype;
	if( pList == null )
	    cTableObj = new ArrayList<PPgTableLine>();
	else
	    cTableObj = pList;

	cTable = new JTable( this );	
	cTable.setRowSorter( (cRowFilter = new TableRowSorter( this) ) );


	cTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
    }			
    //-------------
    public void add( PPgTableLine pLine ){
	cTableObj.add( pLine );
    }
    //-------------
    public Iterator<PPgTableLine> iterator()      { return cTableObj.iterator(); }
    //-------------
    public int getRowCount() { return cTableObj.size(); }
    public int getFilterRowCount() { return cTable.getRowCount(); }
    //-------------
    public String getColumnName( int p_col ){		
	return  cPrototype.getColumnName(p_col);
    }
    //-------------
    public int  getColumnCount(){
	return cPrototype.getColumnCount();
    }
    //-------------
    public Class getColumnClass( int pCol ){				
	return cPrototype.getColumnClass( pCol );
    }
    //-------------
    public Object getValueAt( int pRow, int pCol ){
	if( pRow < cTableObj.size() )
	    {
		return (cTableObj.get(pRow)).getValueAt(pCol);
	    }
	return null;
    }
    //-------------	
    public boolean isCellEditable( int pRow, int col ){
	if( cIsEditable == true
	    && pRow < cTableObj.size() )
	    {
		return cTableObj.get(pRow).isCellEditable(col);
	    }
	return false;
    }
    //-------------
    public void   setValueAt( Object value, int pRow, int pCol ){
	if( pRow < cTableObj.size() )
	    {
		cTableObj.get(pRow).setValueAt(value, pCol);
	    }
    }

    // ------------------------------
    public void selectLine( int pLine ){
	if( pLine > -1 ){
	    cTable.changeSelection( pLine, 0, false, false );
	    cTable.changeSelection( pLine, 2, false, true );						
	}
    }
    //-------------------------------------------
    //-------------------------------------------

    public boolean write( PrintStream pOut ){
	try {			  
	    Iterator<PPgTableLine> lIter = iterator();
	    while( lIter.hasNext()){
		lIter.next().write( pOut ); 
	    }
	}
	catch(Exception e ) { System.err.println( e ); return false; }
	return true;
    }
    // ------------------------------
    public boolean writeFile( File pFile  ){
				
	try {
	    PrintStream lFout = new PrintStream( new FileOutputStream( pFile ) );
	    return write( lFout );
	}
	catch(Exception e ) { System.err.println( e ); return false;}
    }
    // ------------------------------
    public boolean writeFile( String pFileName  ){
	File lFile= new File( pFileName );
	return writeFile( lFile );						
    }	 		
    //-------------------------------
    public boolean readFile(  File pFile  ){
	try{
	    FileReader lFread = new FileReader( pFile );
	    BufferedReader lBufread = new BufferedReader(lFread);
	    String lSbuf;
	    int lNumline = 0;


	    while( (lSbuf=lBufread.readLine()) != null) {
		try{
		    lNumline++;

		    if(  lSbuf.length() == 0  || lSbuf.charAt(0) == '\n'
			 || lSbuf.trim().length() == 0  )
			continue;
										

		    PPgTableLine lLine= cPrototype.createNewLine(lSbuf);
		    if( lLine != null  ){												
			cTableObj.add( lLine );	
		    }
		    else{
			return false;
		    }										
		}						
		catch(Exception e ) { System.err.println( e ); return false;}
	    }	
	}
	catch( Exception e){
	    System.err.println("catch " + e + " in PPTable read file " );
	    return false;
	}
				
	return true;
    }
    //-------------------------------
    public boolean readFile(  String pFileName  ){
	File lFile= new File( pFileName );
	return readFile( lFile );						
    }	 			
    //-------------
    // phipo 20090323
    public void forbidLinesSort(){
	getJTable().setAutoCreateRowSorter(false);
	getJTable().setRowSorter(null);
    }
    //--------------------------
    public int getRow( Point pt   ){

	int lLine = getJTable().rowAtPoint(pt);

	return getJTable().convertRowIndexToModel( lLine );
    }
    //--------------------------
    public int getRow( MouseEvent pEv  ){
	if( pEv.getSource() == getJTable() )
	    return getRow( pEv.getPoint() );		
	else
	    return -1;
    }
}
//**********************************************************
