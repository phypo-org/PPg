package org.phypo.PPg.PPgWin;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.util.regex.*;

//***********************************
public class PPgFrameTable extends PPgFrameChild 
    implements MouseListener, ActionListener{
		
    PPgTable cTable=null;
    public PPgTable getTable() { return cTable;}

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
    void filterBase(){
	String lText = cFilterText.getText();
	if (lText == null || lText.length() == 0) {
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
    public PPgFrameTable( String pName, PPgTable pTable, boolean pFilterFlag, boolean pClosable ){				
	super( pName,  pClosable);
				
	cTable = pTable;

	getContentPane().setLayout( new BorderLayout() );

	//==================
	if( pFilterFlag ){
	    cNorthPanel  = new JPanel();		
	    cNorthPanel.setLayout( new BorderLayout()); 
	    cFilterButton = new JButton( "Filter" );
	    cNorthPanel.add( cFilterButton, BorderLayout.WEST );				
	    cFilterText =  new JTextField("");
	    cNorthPanel.add( cFilterText, BorderLayout.CENTER );				
	    getContentPane().add( cNorthPanel, BorderLayout.NORTH );				
						
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
	cScrollpane = new JScrollPane( cTable.getJTable(),
				       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,	
				       JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );								
				
	getContentPane().add( cScrollpane, BorderLayout.CENTER );
	//==================
				
	cSudPanel = new JPanel();		
	cSudPanel.setLayout( new GridLayout( 1, 0 )); 
	cStatus   = new JLabel( "");

	cSudPanel.add( cStatus );				

	getContentPane().add( cSudPanel, BorderLayout.SOUTH );				
	//==================

	setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );

	cTable.getJTable().addMouseListener( this );		

	cTable.getJTable().setSelectionMode( ListSelectionModel.SINGLE_SELECTION);
    }
    //-------------------------------------------	
    public void setSelectionMode(  int pModel ){
	cTable.getJTable().setSelectionMode( pModel );
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
    //		final String cStrNewCompte  = "New compte";
    //		JCheckBoxMenuItem cCheckViewJuxta;
		
    //-------------------------------------------
    //-------------------------------------------
    //		implements MouseListener
    //-------------------------------------------
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
	    getTable().getList().remove( iPos );
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
//***********************************
