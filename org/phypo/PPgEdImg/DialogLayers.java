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
class TableLayers extends AbstractTableModel {
		
		PPgLayerGroup cLayers = null;

		//-------------
		TableLayers( PPgLayerGroup pLayers ){

				cLayers = pLayers;
		}
		
		public void setLayerGrp(  PPgLayerGroup pLayers ){

				cLayers = pLayers;
				fireTableDataChanged();
		}
		

		public int getRowCount() {
				return cLayers.getLayers().size();
		}
		public int getColumnCount(){
				return 3;
		}
		public Class getColumnClass( int pCol ){
				switch( pCol ) {
						
				case 0:	return String.class;						
				case 1: return Boolean.class;
				case 2: return Boolean.class;
				}
				return null;
		}

		public Object getValueAt(int pRow, int pCol){
				switch( pCol) {
						
				case 0:	return cLayers.getLayers().get( pRow ).getName();
				case 1: return cLayers.getCurrentLayerRow() == pRow;
				case 2: return cLayers.getLayers().get( pRow ).isVisible();					
				default: return "";
				}
		}
		public boolean isCellEditable( int pRow, int pCol ) { return true; }

		public void  setValueAt( Object pValue, int pRow, int pCol  ) {

				//				System.out.println( "TableLayers.setValueAt row:" +  pRow + " col:" + pCol + " -> " + pValue );

				switch( pCol) {						
				case 0 : cLayers.getLayers().get( pRow ).setName( (String)pValue);
						break;
				case 1 : 
						cLayers.cMyInst.cOpLayers.cmdSetCurrentLayer( pRow, true );
						break;
				case 2 : cLayers.getLayers().get( pRow ).setVisible( (Boolean)pValue );
						cLayers.cMyInst.cFrame.actualize();
						break;
				}
		}	
		public String 	getColumnName(int pCol){
				switch( pCol ) {
				case 0: return "Name";
				case 1: return "Current";
				case 2: return "Visible";
				default: return  "";
				}
		}
		
}
//*************************************************

public class DialogLayers  extends JDialog 
		implements  ActionListener,  ListSelectionListener{
		
		public static DialogLayers sTheDialog= null;
		public static boolean      sFlagView = false;


		JTable        cTable           = null;
		TableLayers   cTableLayers     = null;
		EdImgInst     cMyInst = null;

		public final String cStrLayerDown  = "Move layer down";
		public final String cStrLayerUp    = "Move layer up";

		//------------------------------------------------
		public DialogLayers( EdImgInst pInst ){

				super( PPgJFrame.sTheTopFrame, "Layers "+ pInst.cLayerGroup.cMyInst.getName() ,  false );

				cMyInst = pInst;



				cTableLayers = new TableLayers( pInst.cLayerGroup ) ;
				cTable = new JTable( cTableLayers );
				cTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        cTable.setPreferredScrollableViewportSize(new Dimension(300, 100));

				getContentPane().setLayout( new BorderLayout() );	
				JScrollPane lScrollPane = new JScrollPane( cTable  );

				getContentPane().add( lScrollPane,  BorderLayout.CENTER );


				cTable.getSelectionModel().addListSelectionListener( this );

			JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 6 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));			
				getContentPane().add( lSouth, BorderLayout.SOUTH );
				
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrAddLayer, this, "Ressources/Icones/Create.png", 
																			PPgPref.sToolsSz, PPgPref.sToolsSz, "Add new layer" ));
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrDelLayer, this, "Ressources/Icones/Delete.png", 
																	PPgPref.sToolsSz, PPgPref.sToolsSz, "Delete layer" ));
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrDupLayer, this,  "Ressources/Icones/Layers.png",
																									PPgPref.sToolsSz, PPgPref.sToolsSz, "Duplicate layer" ));
				
				lSouth.add( PPgWinUtils.MakeButton( PPgMain.cStrMergeLayerDown, this,  "Ressources/Icones/MergeDown.png", 
																									PPgPref.sToolsSz, PPgPref.sToolsSz, "Merge layer down" ));

				lSouth.add( PPgWinUtils.MakeButton( cStrLayerUp, this,  "Ressources/Icones/Go up.png", 
																									PPgPref.sToolsSz, PPgPref.sToolsSz, "Move layer up" ));

				lSouth.add( PPgWinUtils.MakeButton( cStrLayerDown, this,  "Ressources/Icones/Go down.png", 
															PPgPref.sToolsSz, PPgPref.sToolsSz, "Move layer down" ));

				pack();


				sTheDialog = this;

				if( sFlagView)
						setVisible(true);
		}
		//------------------------------------------------
		public void selectRow( int pRow ){
				//	if( pRow >= 0 )
				//			cTable.setRowSelectionInterval( pRow, pRow );
				cTableLayers.fireTableDataChanged();
		}
		//------------------------------------------------
		public void valueChanged(ListSelectionEvent pEv ) {
				if ( pEv.getValueIsAdjusting()) {
						return;
				}
				//			int lViewRow = cTable.getSelectedRow();
				//			pInst.cLayerGroup.setCurrentLayer( lViewRow, false );// pEv.getFirstIndex()
				
				//			System.out.println("ROW SELECTION EVENT. ");
    }
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){		
				
				
				if( pEv.getActionCommand().equals( PPgMain.cStrAddLayer )) {						
						cMyInst.cOpLayers.cmdAddNewLayer();
						cMyInst.cFrame.actualize();

				} else if( pEv.getActionCommand().equals( PPgMain.cStrDelLayer) ) {						
						if( cMyInst.cOpLayers.cmdDeleteCurrentLayer() )
												cMyInst.cFrame.actualize();
		
				} else if( pEv.getActionCommand().equals( PPgMain.cStrDupLayer) ) {
						
						if( cMyInst.cOpLayers.cmdDupCurrentLayer() )
								cMyInst.cFrame.actualize();

				} else if( pEv.getActionCommand().equals( PPgMain.cStrMergeLayerDown) ) {
						if( cMyInst.cOpLayers.cmdMergeDownCurrentLayer() )
								cMyInst.cFrame.actualize();

				} else if( pEv.getActionCommand().equals( cStrLayerUp) ) {
						if(	cMyInst.cOpLayers.cmdCurrentLayerUp() )
								cMyInst.cFrame.actualize();

				} else if( pEv.getActionCommand().equals( cStrLayerDown) ) {
						if(	cMyInst.cOpLayers.cmdCurrentLayerDown() )
								cMyInst.cFrame.actualize();
				}

				cTableLayers.fireTableDataChanged();
		}
		//------------------------------------------------

		public void init( EdImgInst pInst   ){
				
				cMyInst = pInst;
				System.out.println("***** DialogLayers.init " + cMyInst.getName() );

				cTableLayers.setLayerGrp( cMyInst.cLayerGroup );
				setTitle(  "Layers "+ pInst.cLayerGroup.cMyInst.getName());
				selectRow( pInst.cLayerGroup.getCurrentLayerRow() );
		}
		//------------------------------------------------
		public void changeData( EdImgInst pInst     ){
				if( pInst == cMyInst ){
						cTableLayers.fireTableDataChanged();
				}
		}
		//------------------------------------------------
		public void redraw(){
				cTableLayers.fireTableDataChanged();
		}
}

//*************************************************
