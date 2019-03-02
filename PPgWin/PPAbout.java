

package org.phypo.PPg.PPgWin;


import javax.swing.JOptionPane;


//***********************************

public class PPAbout{

	public PPAbout( String p_progname, 
									String p_version,
									String p_copyright,
									String p_licence,
									String p_mail ){

			JOptionPane.showMessageDialog( PPgAppli.TheAppli,
																		 p_progname + " "+  p_version +"\n"
																		 + ( p_copyright != null ? p_copyright+"\n" : "" )
																		 + ( p_licence   != null ? p_licence+ "\n" : "")
																		 + ( p_mail      != null ? p_mail : ""),
																		 "About" + p_progname, 
																						 JOptionPane.INFORMATION_MESSAGE);
			
		}
}
//***********************************
