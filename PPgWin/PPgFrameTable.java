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
import javax.swing.table.TableColumnModel;

import org.phypo.PPg.PPgUtils.Log;

import java.util.ArrayList;
import java.util.regex.*;

//***********************************
@SuppressWarnings("serial")
public class PPgFrameTable extends PPgFrameChild 
    implements MouseListener, ActionListener{
		
    PPgTable cTable=null;
    
    public PPgTable getTable() { return cTable;}
    public JTable getJTable() { return cTable.getJTable();}

    JPanel      cSudPanel = null;
    protected JPanel      cNorthPanel = null;
    JLabel      cStatus =null;
    JScrollPane cScrollpane=null;

    JTextField cFilterText  = null;
    JButton    cFilterButton= null;
    
   public ArrayList<PPgTableLine> getList()     { return cTable.cTableObj; }
   public ArrayList<PPgTableLine> getSelectedRowsArray() { return cTable.getSelectedRowsArray(); }
 
    public TableColumnModel getColumnModel() { return cTable.getJTable().getColumnModel(); }
    public JLabel getStatus() { return cStatus; }
    
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
    //-------------------------------------
    protected JPanel getButtonPanel() { return cNorthPanel;}
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
    //----------------------TableColumnModel---------------
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
    public void clearFilter() {
    	cFilterText.setText("");
		filterBase();
		forceRedraw();
    }
    public void actionFilterChange( ActionEvent e){
    	
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
			forceRedraw();
		    }
		});
						
	    cFilterText.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    actionFilterChange(e);
			filterBase();
			forceRedraw();
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
    public void mouseReleased( MouseEvent pEv )    {;}
    public void mouseEntered( MouseEvent pEv )    {;}
    public void mouseExited( MouseEvent pEv )     {;} 
    public void mouseClicked( MouseEvent pEv )     {;}	
    
    
    
    
    //-------------------------------------------
    //public void mousePressed( MouseEvent pEv )  {}
    //-------------------------- 
//-------------
	public void addPopupMenuItems( JPopupMenu lMenu, PPgTableLine lLine, int lPos) {;}
	public void eventOnLine( PPgTableLine lLine , int lPos) {;}

	final String cStrSelectAll   = "Select All";
	final String cStrExport2FileCSV = "Export all lines to csv file";
	final String cStrExportSelect2FileCSV = "Export selected lines to csv file";
	//-------------

	static String cCurrentPath=null;
	protected int cLastMenuLine = -1;

	//-------------
	public void mousePressed( MouseEvent pEv )  { 

		Log.Dbg( "************* FrameTable.mousePressed **********" );

		cLastMenuLine = getMouseLineNumber( pEv );

		if(  SwingUtilities.isLeftMouseButton( pEv ) == true &&  pEv.getClickCount() == 2 ){
			PPgTableLine lCurrent = getLine( cLastMenuLine );
			if( lCurrent != null ){
				eventOnLine( lCurrent , cLastMenuLine);
			}
		}
		else
			if( SwingUtilities.isRightMouseButton( pEv ) == true 
			&& pEv.getClickCount() == 1 ) {
				Log.Dbg("  RightMouseButton ");

				JPopupMenu lPopmenu = new JPopupMenu();
				
				PPgTableLine lLine  = null;
				if( cLastMenuLine != -1 ) {
					lLine  = getLine( cLastMenuLine );
				}

				addPopupMenuItems( lPopmenu, lLine, cLastMenuLine );

				if( lPopmenu.getSubElements().length >0 ) {
					lPopmenu.add( new JSeparator() );	
				}

				JMenuItem lItem;
				/*
				//==================
				lItem = new JMenuItem( cStrSelectAll );
				lItem.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getTable().selectAll();
						getTable().fireTableDataChanged();
					}});
				lPopmenu.add( lItem );
				*/
				if( getJTable().getSelectedRowCount()> 0){
					lItem= new JMenuItem( cStrExportSelect2FileCSV);
					lItem.addActionListener( new ActionListener() {
						public void actionPerformed(ActionEvent e) {												
								ArrayList<PPgTableLine>  lLines = getTable().getSelectedRowsArray();
								PPgTable.Export2CSV( PPgFileChooser.GetFileName("export csv", cCurrentPath)	, lLines);							
						}});											
				}
				//==================
				lItem= new JMenuItem( cStrExport2FileCSV );
				lItem.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {							
							ArrayList<PPgTableLine>  lLines = getTable().getList(); 
							PPgTable.Export2CSV( PPgFileChooser.GetFileName("export csv", cCurrentPath)	, lLines);														
					}});					
				lPopmenu.add( lItem );
				//	==================			

				lPopmenu.show( pEv.getComponent(),
						pEv.getX(),
						pEv.getY() );												
			}			
	}    
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
    // obsolete !!!!!!!!!!
  //  public void fireTableDataChanged(){
//	getTable().fireTableDataChanged();
   // }
     //-------------------------------------
    public void removeLineAndRedraw( int iPos ) {
	if( removeLine( iPos ) ) {
		forceRedraw();
		}
    }
    //-------------------------------------
    public void clear() { getTable().clear();}
};
//***********************************
