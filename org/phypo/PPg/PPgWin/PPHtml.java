package org.phypo.PPg.PPgWin;


import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;


//***********************************
public class PPHtml extends JEditorPane{

		PPHtml( String pPage ){

				setEditable(false);

				//		URL lURL = this.getClass().getRessource( pPage );
				try{
						URL lURL = new  URL (pPage );
						
						try{
								setPage( lURL );
						}
						catch( IOException e){
								System.err.println( e + "Attemped to read a bad URL : " + lURL);								
						}						
				}
				catch( Exception e ){
						System.err.println( e );								
				}
		}
}
//***********************************
