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
										}
								});
						
						cFilterText.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
												filterBase();
										}
								});						
				}
				//==================
				cScrollpane = 	new JScrollPane( cTable.getJTable(),
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
				cStatus.setText( "row count : " + cTable.getRowCount());
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
};
//***********************************
