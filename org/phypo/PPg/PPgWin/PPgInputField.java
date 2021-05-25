package org.phypo.PPg.PPgWin;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;


//**********************************************************

public class PPgInputField extends PPgField{
		
		JTextField cText;

		public JTextField getTextField() { return cText; }
		
		//----------------
		public PPgInputField( String pStr, String pVal, int pSens ){
				
				super( pStr, pSens );
				cText  = new JTextField( pVal  );
			
				add( cText );			
		}
		//----------------
		public PPgInputField( String pStr, String pVal, int pSens, char pPasswordChar ){
				
				super( pStr, pSens );
				cText  = new JPasswordField( pVal  );
				if( pPasswordChar != '\0'){
						((JPasswordField)cText).setEchoChar( pPasswordChar );
				}
				
				add( cText );			
		}
		//----------------
		public void setColumns( int pCol ){

				cText.setColumns( pCol );
		}
		//----------------
		public	void addActionListener( ActionListener pAl ){
				cText.addActionListener( pAl );
		}
		//----------------
		public	boolean isAction( Object pObj){
				if( pObj == cText )
						return true;
				
				return false;
		}
		//----------------
		public String  getString()                  { return cText.getText();}
		public void    setString(String  pVal)      { cText.setText( pVal ); }
		public void    setEditable(boolean pBool )  { cText.setEditable( pBool );}
		public boolean isEditable()                 { return cText.isEditable();}
		
		
}
//**********************************************************


