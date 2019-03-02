package org.phypo.PPg.PPgWin;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

//**********************************************************
public class PPgField extends JPanel{

		static public final int VERTICAL=0;
		static public final int HORIZONTAL =1;
		static public final int FLOW_LAYOUT =2;
		
		protected JLabel     cLabel;
	 
		//----------------
		public PPgField( String pStr, int pSens ){
				
				setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ));
				
				if( pSens == VERTICAL )
						setLayout( new GridLayout( 0, 1 ));
				else
						if( pSens == HORIZONTAL )
								setLayout( new GridLayout( 1, 0 ));
						else
								setLayout( new FlowLayout());
				
				cLabel = new JLabel( pStr );
				
				add( cLabel );
				
		}
		//----------------
		public	void addActionListener( ActionListener p_al ){
		}
		//----------------
		public	boolean isAction( Object p_o){
				return false;
		}
		//----------------
		public String getString() { return null;}
		public void setString(String  pVal) {  }
		public void setEditable(boolean pBool ) { }
}
//**********************************************************
