package org.phypo.PPgEdImg;




import java.awt.image.*;
import javax.swing.*;


import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;

import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;

import org.phypo.PPg.PPgWin.*;


//*************************************************
class TableHisto extends AbstractTableModel {
		
		OpControler   cOpControler;

		//-------------
		TableHisto( OpControler   pOpControler ){

				cOpControler = pOpControler;

		}
		
		public void set( OpControler   pOpControler  ){

				cOpControler = pOpControler;

				fireTableDataChanged();
		}
		

		public int getRowCount() {
				return cOpControler.getHisto().size();
		}

		public int getColumnCount(){
				return 2;
		}

		public Class getColumnClass( int pCol ){
				switch( pCol ) {
						
				case 0:	return Integer.class;
				case 1:	return String.class;
						
				}
				return null;
		}

		public Object getValueAt(int pRow, int pCol){

				switch( pCol) {
				case 0: return pRow;
				case 1:	{
						return cOpControler.getOpCommentFromPrototype( pRow );
				}
						
				default: return "";
				}
		}
		public boolean isCellEditable( int pRow, int pCol ) { return false; }

		public void  setValueAt( Object pValue, int pRow, int pCol  ) {
		}	
		public String 	getColumnName(int pCol){
				switch( pCol ) {
				case 0: return "Index";
				case 1: return "Commande";
				default: return  "";
				}
		}
		
}
//*************************************************

public class DialogHisto  extends JDialog 

		implements  ActionListener,  ListSelectionListener{
		

		public static DialogHisto sTheDialog = null;
		public static boolean     sFlagView = false;


		JTable        cTable           = null;
		TableHisto    cTableHisto     = null;

		OpControler   cOpControler;

		public final String cStrUndoAll     = "Undo all";
		public final String cStrRedoAll     = "Redo all";

		//------------------------------------------------
		public DialogHisto( OpControler   pOpControler ){

				super( PPgJFrame.sTheTopFrame, "Historique"+ pOpControler.cMyInst.getName() ,  false );
				cOpControler = pOpControler;

				cTableHisto = new TableHisto( cOpControler ) ;
				cTable = new JTable( cTableHisto );
				cTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        cTable.setPreferredScrollableViewportSize(new Dimension(300, 100));

				getContentPane().setLayout( new BorderLayout() );	
				JScrollPane lScrollPane = new JScrollPane( cTable  );

				getContentPane().add( lScrollPane,  BorderLayout.CENTER );


				cTable.getSelectionModel().addListSelectionListener( this );

				


				JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 4 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));			
				getContentPane().add( lSouth, BorderLayout.SOUTH );
				
				
				lSouth.add( PPgWinUtils.MakeButton( cStrUndoAll, this,  "Ressources/Icones/Left.png", 
																			PPgPref.sToolsSz, PPgPref.sToolsSz, "Undo all" ));
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrUndo, this, "Ressources/Icones/Undo.png", 
																	PPgPref.sToolsSz, PPgPref.sToolsSz, "Undo" ));
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrRedo, this, "Ressources/Icones/Redo.png",
																									PPgPref.sToolsSz, PPgPref.sToolsSz, "Redo" ));
				
				lSouth.add( PPgWinUtils.MakeButton( cStrRedoAll, this, "Ressources/Icones/Right.png", 
																									PPgPref.sToolsSz, PPgPref.sToolsSz, "Redo all" ));


				pack();


				sTheDialog = this;

				if( sFlagView )
						setVisible(true);
		}
		//------------------------------------------------
		public void selectRow( int pRow ){
				// BUG !!!!!
				//				if( pRow >= 0 )
				//						cTable.setRowSelectionInterval( pRow, pRow );
		}
		//------------------------------------------------
		public void valueChanged(ListSelectionEvent pEv ) {
				if ( pEv.getValueIsAdjusting()) {
						return;
				}

				int lViewRow = cTable.getSelectedRow();
				if (lViewRow < 0) {
				} else {
						int lModelRow = cTable.convertRowIndexToModel( lViewRow);
						System.out.println("ROW SELECTION EVENT :  " + lModelRow);
						cOpControler.replayHisto(lModelRow);
				}
    }
 
		//------------------------------------------------doses d'aspirine

		public void init(  OpControler   pOpControle ){
				
				System.out.println("***** DialogHisto.init " + pOpControle.cMyInst.getName() );
				cOpControler = pOpControle;

				cTableHisto.set( cOpControler );
				setTitle(  "Historique "+ cOpControler.cMyInst.getName());
				selectRow( cOpControler.getLastIndexHisto() );



				SavConf.AutoSav();
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){		
				
		 if( pEv.getActionCommand().equals( cStrUndoAll ) ) {
				 cOpControler.cmdHistoUndoAll();																		
				} else if( pEv.getActionCommand().equals( PPgMain.cStrUndo) ) {
				 cOpControler.cmdHistoUndo();												
				} else if( pEv.getActionCommand().equals( PPgMain.cStrRedo) ) {
				 cOpControler.cmdHistoRedo();
				} else if( pEv.getActionCommand().equals( cStrRedoAll) ) {
				 cOpControler.cmdHistoRedoAll();
				}	
		}

		//------------------------------------------------
		public void changeData( OpControler pOpControler ){

				if( cOpControler == pOpControler ){
						cTableHisto.fireTableDataChanged();
				}
		}
}

//*************************************************
