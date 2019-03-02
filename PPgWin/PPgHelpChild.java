package org.phypo.PPg.PPgWin;


import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


//***********************************

public class PPgHelpChild extends PPgFrameChild{
		
		public PPgHelpChild(  String pPage ){

				super( pPage );

				getContentPane().setLayout( new BorderLayout() );
	
				PPHtml lHtml = new PPHtml( pPage );
				//JTextArea lHtml = new JTextArea( pPage );
				JScrollPane lScroll  = new JScrollPane(lHtml,	
																							 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
																							 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				getContentPane().add( lScroll, BorderLayout.CENTER );
				setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );		
		}

}
//***********************************
