package org.phypo.SqlTools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


//**********************************************************
public class MyTableRenderer extends DefaultTableCellRenderer{

    //   ClientTable cMyClientTable;
    Color cColorDark =null;
    
    public MyTableRenderer() { // ClientTable pMyClientTable  ){
	
	//	cMyClientTable = pMyClientTable;
    }
    
    public Component getTableCellRendererComponent( JTable pTable, java.lang.Object pValue,
						    boolean pIsSelected,
						    boolean pHasFocus,
						    int pRow, int pColumn ){


	System.out.println("********** getTableCellRendererComponent ");

	if( !pIsSelected ){
	    
	    if( cColorDark== null ) {
		Color c = pTable.getBackground();
		
		if(c.getRed()>10 && c.getGreen() > 10 && c.getBlue() >10 )
		    cColorDark =  new Color( c.getRed()-10, c.getGreen() -10, c.getBlue() -10 );				
		else
		    cColorDark =  new Color( c.getRed()+10, c.getGreen() +10, c.getBlue() +10 );														
	    }
	    
	    //						if( cMyClientTable.isRecentModif( pRow, pColumn) ) {
	    //								setBackground(pTable.getForeground());									
	    //								setForeground(pTable.getBackground());									
	    //						}
	    //						else {								
	    
	    if( pRow %2 ==0  )
		setBackground( cColorDark );
	    else
		setBackground( pTable.getBackground() );
	    //}
	}
	return super.getTableCellRendererComponent( pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn );
    }
   

};

//**********************************************************
