package org.phypo.PPg.PPgProjEd;




import java.awt.image.*;
import javax.swing.*;
import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.phypo.PPg.PPgWin.*;

//*************************************************

public class DialogDessin  extends JDialog 
		implements  ActionListener	{
		

		Dessin cDessin = null;

		JButton cButtonCancel = null;
		JButton cButtonOk     = null;

		public static ImageIcon sTmpIcon = null;

		PPgInputField cPathField = null;

		//--------------------------------
		DialogDessin( Dessin pDessin ){

				super( PPgJFrame.sTheTopFrame, "Properties " + pDessin.toString(),  true );
				cDessin = pDessin;

				sTmpIcon = cDessin.getImage(); // ARGHH !!!!

				if ( (sTmpIcon.getIconWidth() >512 ) || (sTmpIcon.getIconHeight() > 512) ) {
						if( (sTmpIcon.getIconWidth() /512 ) > (sTmpIcon.getIconHeight() / 512) ) {
								sTmpIcon= new ImageIcon(sTmpIcon.getImage().getScaledInstance(512, -1,
																																							Image.SCALE_FAST));
						}
						else {												
								sTmpIcon = new ImageIcon(sTmpIcon.getImage().getScaledInstance( -1, 512,
																																								Image.SCALE_FAST));
						}										
				}


				getContentPane().setLayout( new BorderLayout() );	

				//=================
				
				JPanel lView  = new JPanel() { 
								public void paint( Graphics pG ){
									pG.drawImage( DialogDessin.sTmpIcon.getImage(), 0, 0, null );	
								}
						};


				lView.setPreferredSize( new Dimension( sTmpIcon.getIconWidth(), sTmpIcon.getIconHeight() ));

				getContentPane().add( lView, BorderLayout.NORTH );
				
				//==================


				JPanel lMiddle = new JPanel();
				lMiddle.setLayout( new GridLayout( 2, 1 ));
				lMiddle.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

				cPathField = new PPgInputField( "Path",  cDessin.getFile().getPath(), PPgField.VERTICAL);

				lMiddle.add( cPathField  );

				PPgField cFieldSize = new PPgField( "size : " + sTmpIcon.getIconWidth() + "x" + sTmpIcon.getIconHeight(), PPgField.HORIZONTAL  );
				lMiddle.add(cFieldSize  );

				getContentPane().add( lMiddle, BorderLayout.CENTER );

				//===============



				//===============

				JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 4 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));
				
				cButtonOk= new JButton( "Ok" );
				cButtonOk.setActionCommand( "Ok");
				cButtonOk.addActionListener( this );
				lSouth.add( cButtonOk );

				cButtonCancel = new JButton( "Cancel" );
				cButtonCancel.setActionCommand( "Cancel");
				cButtonCancel.addActionListener( this );
				lSouth.add( cButtonCancel );
				getContentPane().add( lSouth, BorderLayout.SOUTH );

				//===============
				
				pack();
				setVisible(true);
		}
		
		//--------------------------------
		
		public void actionPerformed( ActionEvent p_e ){
				
				if( p_e.getActionCommand().equals("Cancel")) {
						dispose();
				}			
				else
						if( p_e.getActionCommand().equals("Ok")) {
								//cValid = true ;
								// Mettre a jour Dessin
								// cProject.setName( cStringField.getString() );
								dispose();
						}
		}
		
}
