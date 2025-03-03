package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
abstract public class OpGrafUtilMem extends OpGrafUtil {

		ArrayList<Point> cArrayPoint = new ArrayList<Point>();

		Point cMemPoint;


		public OpGrafUtilMem(EdImgInst pMyInst){	super( pMyInst );	}
		//------------------------------------------------
		JComboBox        cComboBoxJoinMode = null;
		String           cJoinMode[]={ "Join bevel", "Join miter", "Join round" };
		int cJoin = BasicStroke.JOIN_ROUND;
		
		JComboBox        cComboBoxCapMode = null;
		String           cCapMode[]={ "Cap butt", "Cap round", "Cap Square" };
		int cCap = BasicStroke.CAP_ROUND;

		//------------------------------------------------

		public void makeToolBar( JToolBar pBar){ 
				super.makeToolBar( pBar );
				
				cComboBoxJoinMode = new JComboBox( cJoinMode );
				cComboBoxJoinMode.setSelectedIndex( 2 );  
				pBar.add( cComboBoxJoinMode );
				cComboBoxJoinMode.addActionListener( this );
				
				cComboBoxCapMode = new JComboBox( cCapMode );
				cComboBoxCapMode.setSelectedIndex( 1 );  
				pBar.add( cComboBoxCapMode );
				cComboBoxCapMode.addActionListener( this );
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){		
				
				super.actionPerformed( pEv );

				if( pEv.getSource() == cComboBoxJoinMode ){
						switch( cComboBoxJoinMode.getSelectedIndex() ){
						case 0: cJoin = BasicStroke.JOIN_BEVEL; break;
						case 1: cJoin = BasicStroke.JOIN_MITER; break;
						case 2: cJoin = BasicStroke.JOIN_ROUND; break;
						}
				} else if( pEv.getSource() == cComboBoxCapMode ){
						switch( cComboBoxCapMode.getSelectedIndex() ){
						case 0: cCap = BasicStroke.CAP_BUTT; break;
						case 1: cCap = BasicStroke.CAP_ROUND; break;
						case 2: cCap = BasicStroke.CAP_SQUARE; break;
						}
				}
		}

		//------------------------------------------------
		public void beginOp(   Point pPoint ){

				//			if( cFieldSize != null)  sStrokeSize = cFieldSize.getInt();

				cArrayPoint.clear();

				cMemPoint =  pPoint ;
				
				moveOp(  pPoint );
		}

		//------------------------------------------------

		public void finalizeOp(   Point pPoint ){

				System.out.println( "***  ******** OpGrafUtilMem.finalizeOp 1 " + cArrayPoint.size() );

				moveOp(  pPoint );
				cMyInst.cCanvas.actualize();
				System.out.println( "*********** OpGrafUtilMem.finalizeOp 2 " + cArrayPoint.size() );
		}

		//------------------------------------------------
		public void cancelOp( ){
				cArrayPoint.clear();			
		}
		//------------------------------------------------
		public void cleanOp() {
				System.out.println("===== cleanOp ======== "  +cArrayPoint.size() );
				cArrayPoint.clear();
		}
};

//*************************************************
