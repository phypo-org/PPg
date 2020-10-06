package org.phypo.PPg.PPgWin;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

//***********************************

@SuppressWarnings("serial")
public class PPgTextEditorChild extends PPgFrameChild{

	public PPgTextEditorChild(  String pTitle,String pStr ){

		super( pTitle );

		getContentPane().setLayout( new BorderLayout() );

		JTextArea lText = new JTextArea( pStr );
		JScrollPane lScroll  = new JScrollPane(lText,	
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		getContentPane().add( lScroll, BorderLayout.CENTER );
		setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );		
	}
}
//***********************************
