package org.phypo.PPg.PPgWin;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.util.ArrayList;
import java.util.regex.*;


//***********************************
public class PPgPanelTable extends JPanel
    implements MouseListener, ActionListener{
		
    protected PPgTable cTable=null;
    public PPgTable getTable() { return cTable;}
    public JTable getJTable() { return cTable.getJTable();}
    public int[] getSelectedRows() { return getJTable().getSelectedRows(); }

    public ArrayList<PPgTableLine> getSelectedRowsArray() { return cTable.getSelectedRowsArray(); }
    	

    JPanel      cSudPanel;
    JPanel      cNorthPanel = null;;
    JLabel      cStatus;
    JScrollPane cScrollpane;

    JTextField cFilterText  = null;
    JButton    cFilterButton= null;

    public JLabel getStatus() { return cStatus; }
    //-------------------------------------
    void filterRegex(){
	String lText = cFilterText.getText();
	if (lText.length() == 0) {
	    cTable.getRowFilter().setRowFilter(null);
	} 
	else 
	    {
		try {
		    cTable.getRowFilter().setRowFilter(RowFilter.regexFilter(lText));
		} catch (PatternSyntaxException pse) {
		    System.err.println("FrameObj.filter : Bad regex pattern :" + lText );
		}
	    }				
    }
    //-------------------------------------

    public void setBackground( Color bg ) {
    	super.setBackground(bg);
    	
    	if( cSudPanel != null )    cSudPanel.setBackground(bg);
        
    	if( cNorthPanel != null )  cNorthPanel.setBackground(bg);
    	if( cStatus != null )      cStatus.setBackground(bg);
    	if( cScrollpane != null )  cScrollpane.setBackground(bg);
    	if( cFilterText != null )  cFilterText.setBackground(bg);
    	if( cFilterButton != null )cFilterButton.setBackground(bg);
    	
    //	cTable.setBackground(bg);
    }
    //-------------------------------------------	
    public void forceRedraw() {
    	cTable.fireTableDataChanged();
    	updateStatusRowCount();
    }
    //-------------------------------------------		
   public void addLineAndRedraw( PPgTableLine pLine ){	
	cTable.add( pLine );
	forceRedraw();
   }
   //-------------------------------------------		
   public void addLine( PPgTableLine pLine ){	
	cTable.add( pLine );
   }
   //-------------------------------------
    void filterBase(){
	String lText = cFilterText.getText();
	if (lText.length() == 0) {
	    cTable.getRowFilter().setRowFilter(null);
	} 
	else 
	    {
		try {
		    cTable.getRowFilter().setRowFilter(RowFilter.regexFilter(lText));
		} catch (PatternSyntaxException pse) {
		    System.err.println("PPFrameTable : Bad regex pattern :" + lText );
		}
	    }				
    }
    //-------------------------------------
    public PPgPanelTable( String pName, PPgTable pTable, boolean pFilterFlag ){				
				
	cTable = pTable;

	setLayout( new BorderLayout() );

	//==================
	if( pFilterFlag ){
	    cNorthPanel  = new JPanel();		
	    cNorthPanel.setLayout( new BorderLayout()); 
	    cFilterButton = new JButton( "Filter" );
	    cNorthPanel.add( cFilterButton, BorderLayout.WEST );				
	    cFilterText =  new JTextField("");
	    cNorthPanel.add( cFilterText, BorderLayout.CENTER );				
	    add( cNorthPanel, BorderLayout.NORTH );				
						
	    cFilterButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			filterBase();
			updateStatusRowCount();
		    }
		});
						
	    cFilterText.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			filterBase();
			updateStatusRowCount();
		    }
		});						
	}
	//==================
	cScrollpane = 	new JScrollPane( cTable.getJTable(),
					 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
					 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );								
				
	add( cScrollpane, BorderLayout.CENTER );
	//==================
				
	cSudPanel = new JPanel();		
	cSudPanel.setLayout( new GridLayout( 1, 0 )); 
	cStatus   = new JLabel( "");

	cSudPanel.add( cStatus );				

	add( cSudPanel, BorderLayout.SOUTH );				

	cTable.getJTable().addMouseListener( this );		

	cTable.getJTable().setSelectionMode( ListSelectionModel.SINGLE_SELECTION);
    }
    //-------------------------------------------	
    public void setSelectionMode(  int pModel ){
	cTable.getJTable().setSelectionMode( pModel );
    }
    //-------------------------------------------		
    public void simpleAdd( PPgTableLine pLine ){	
    	cTable.add( pLine );
    }
   //-------------------------------------------		
    public void add( PPgTableLine pLine ){	
	cTable.add( pLine );
	cTable.fireTableDataChanged();
	updateStatusRowCount();
    }
    //-------------------------------------------		
    public void updateStatusRowCount()
    {
	if( cFilterButton != null ) {
	    cStatus.setText( "row count : " + cTable.getFilterRowCount()
			     + "/" +cTable.getRowCount());
	}
	else {	
	    cStatus.setText( "row count : "+cTable.getRowCount());
	}
    }

    //-------------------------------------------
    public void mousePressed( MouseEvent pEv )  { 			
    }
    //-------------------------- 
    public void mouseReleased( MouseEvent pEv )    {;}
    public void mouseEntered( MouseEvent pEv )    {;}
    public void mouseExited( MouseEvent pEv )     {;}
    public void mouseClicked( MouseEvent pEv )     {;}	
    //-------------------------------------------
    //-------------------------------------------
    //		implements ActionListener
    //-------------------------------------------
    //-------------------------------------------
    public void actionPerformed( ActionEvent pEv ){ 
    }
    //-------------------------------------
    public int getMouseLineNumber( MouseEvent iEv ){
	
	if( iEv.getSource() == getTable().getJTable() ){
	    int lLine = getTable().getJTable().rowAtPoint(iEv.getPoint());
	    lLine = getTable().getJTable().convertRowIndexToModel( lLine ); // converts the row index in the view to the appropriate index in the model
	    return lLine;
	}
	return -1;
    }
    //-------------------------------------
    public PPgTableLine getLine( int lPos ) {
	if( lPos > -1 ) {	    
	return getTable().getList().get( lPos);
	}
	return null;
    }
    //-------------------------------------
    public PPgTableLine getMouseLine(  MouseEvent iEv ) {
	return getLine( getMouseLineNumber( iEv ) );
    }   
    //-------------------------------------
    public boolean removeLine( int iPos ) {
	if( iPos > -1 ) {	    
	    getTable().getList().remove( iPos);
	    return true;
	}
	return false;
    }
    //-------------------------------------
    public void fireTableDataChanged(){
	getTable().fireTableDataChanged();
    }
    //-------------------------------------
    public void removeLineAndRedraw( int iPos ) {
	if( removeLine( iPos ) ) {
	    fireTableDataChanged();
	    updateStatusRowCount();
	}
    }
    //-------------------------------------
    public void clear() { getTable().clear();}
};
