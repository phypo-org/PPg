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
public class DialogColor  extends JDialog 
    implements ChangeListener  {


		public static DialogColor sTheDialog = null;
		public static boolean     sFlagView = false;

		PPgFrameEdImg cFrame;

		JColorChooser cChooser;
		static int sColorIdx = 0;

		//------------------------------------------------
		public DialogColor( PPgFrameEdImg pFrame, Color pColor ){

				super( PPgJFrame.sTheTopFrame, "Color"+ pFrame.getName(), false );
	
				cFrame = pFrame;

				cChooser =	new JColorChooser( pColor );
				cChooser.setPreviewPanel(new JPanel() );


				cChooser.getSelectionModel().addChangeListener(this);
				getContentPane().add( cChooser, BorderLayout.CENTER );

				cChooser.setColor( pColor );


				pack();

				sTheDialog = this;

				if( sFlagView )
						setVisible(true);
		}
		//------------------------------------------------
		static public void SetColor1() {
				sColorIdx = 1;
		}
		//------------------------------------------------
		static public void SetColor2() {
				sColorIdx = 2;
		}
		//------------------------------------------------
		public void init( PPgFrameEdImg pFrame, Color pColor){
				cFrame = pFrame;
				cChooser.setColor( pColor );
		}
		//------------------------------------------------
		public void changeData( PPgFrameEdImg pFrame, Color pColor  ){
		}
		//----------------------------------------------
		public void stateChanged( ChangeEvent e) {
				
				PPgMain.GetCurrentInstance().cOpProps.cmdSetColor( cChooser.getColor() ); // , OpGrafUtil.WitchColor() );
		}
		//------------------------------------------------
        
}
//*************************************************
