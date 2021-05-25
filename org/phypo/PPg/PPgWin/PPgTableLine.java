package org.phypo.PPg.PPgWin;

import java.lang.Object;
import java.io.*;

//**********************************************************
public interface PPgTableLine {

		public String  getColumnName( int pCol);
		public int     getColumnCount();
		public Object  getValueAt( int pCol );
		public boolean isCellEditable( int pCol );
		public void    setValueAt( Object pValue, int pCol );
		public Class   getColumnClass( int pCol );

		public boolean     write( PrintStream pOut );
		public PPgTableLine createNewLine( String pVal);
};
//**********************************************************
