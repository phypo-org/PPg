package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafBrush extends OpGrafUtilMem{
		
		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		Color cCurrentColor;

		boolean cFlagOver = false;

		PPgMotif cMotif = null;
		PPgBrush cBrush = null;

		JCheckBoxMenuItem cCheckOver = null;
		
		public final String cStrMenuBrush = "Brush";
		public final String cStrNoBrush = "No brush";

		public final String cStrMenuMotif = "Motif";
		public final String cStrNoMotif = "No motif";

		public final String cStrTypeBrush = "MenuBrush";
		public final String cStrBrush = "Brush";


		JComboBox        cComboBoxMotif = null;


		JComboBox        cComboBoxComposite = null;
		String           cCompositeMode[]={ "SRC_OVER",  "SRC_IN", "DST_ATOP",  "XOR"  };
		//		String           cCompositeMode[]={ "CLEAR", "DST", "DST_ATOP", "DST_IN", "DST_OUT", "DST_OVER", "SRC_ATOP", "SRC_IN", "SRC_OUT", "SRC_OVER", "XOR"  };
		int cComposite = AlphaComposite.SRC_ATOP;
		


		//------------------------------------------------

		public OpGrafBrush(EdImgInst pMyInst){
				super( pMyInst );
		
				cFlagBarOpacity = true;
		}
		//------------------------------------------------
		
		public void makeToolBarBase( JToolBar pBar){ 
				
				super.makeToolBarBase( pBar );
				
				pBar.add( ( cCheckOver= new JCheckBoxMenuItem( "Overwrite" )));
				cCheckOver.setSelected( cFlagOver );
				cCheckOver.addItemListener( this );

				cComboBoxComposite =  new JComboBox(cCompositeMode);
				cComboBoxComposite.setSelectedIndex( 0 );  
				pBar.add( cComboBoxComposite );
				cComboBoxComposite.addActionListener( this );


				pBar.add( PPgWinUtils.MakeButton( cStrMenuBrush, this,  "Ressources/Icones/Brush.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Brush" ));



				Vector<ImageIcon> lVectMotif = new Vector<ImageIcon> ();
				lVectMotif.add( new ImageIcon( "Ressources/Motif/Void.jpg" ));
				lVectMotif.add( new ImageIcon( "Ressources/Motif/Brush1.jpg" ));
				lVectMotif.add( new ImageIcon( "Ressources/Motif/BrushCircleGrad32x32.jpg" ));

				cComboBoxMotif= new JComboBox( lVectMotif );
				cComboBoxMotif.setSelectedIndex( 0 );  
				pBar.add( cComboBoxMotif );
				cComboBoxMotif.addActionListener( this );

				//	pBar.add( PPgWinUtils.MakeButton( cStrMenuMotif , this));

		}
		//------------------------------------------------
		public void itemStateChanged(ItemEvent pEv ){
				super.itemStateChanged( pEv );
				
				if( pEv.getItemSelectable() == cCheckOver ){
						
						if( pEv.getStateChange() == ItemEvent.DESELECTED ){
								cFlagOver = false;
						}
						else {								
								cFlagOver = true;	
						}
				}
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){	

				super.actionPerformed( pEv );

				//=================== COMPOSITE ==================
				if( pEv.getSource() == cComboBoxComposite ){
						switch( cComboBoxComposite.getSelectedIndex() ){
						case 0: cComposite = AlphaComposite.SRC_OVER; break;
						case 1: cComposite = AlphaComposite.SRC_IN; break;
						case 2: cComposite = AlphaComposite.DST_ATOP; break;
						case 3: cComposite = AlphaComposite.XOR; break;
						/*
						case 0: cComposite = AlphaComposite.CLEAR; break;
						case 1: cComposite = AlphaComposite.DST; break;
						case 2: cComposite = AlphaComposite.DST_ATOP; break;
						case 3: cComposite = AlphaComposite.DST_IN; break;
						case 4: cComposite = AlphaComposite.DST_OUT; break;
						case 5: cComposite = AlphaComposite.DST_OVER; break;
						case 6: cComposite = AlphaComposite.SRC_ATOP; break;
						case 7: cComposite = AlphaComposite.SRC_IN; break;
						case 8: cComposite = AlphaComposite.SRC_OUT; break;
						case 9: cComposite = AlphaComposite.SRC_OVER; break;
						case 10: cComposite = AlphaComposite.XOR; break;
						*/
					}
				}
				//=================== COMPOSITE ==================


				//=================== MOTIF ==================
				if( pEv.getSource() == cComboBoxMotif ){
						switch( cComboBoxMotif.getSelectedIndex() ){
						case 0: cMotif = null; break;
						case 1: cMotif = PPgMotif.CreateMotif( new File( "Ressources/Brush/Brush1.jpg" )); break;
						case 2: cMotif = PPgMotif.CreateMotif( new File( "Ressources/Brush/BrushCircleGrad32x32.jpg" )); break;
						}
				}
				//=================== MOTIF ==================

				

			
				if(  pEv.getActionCommand().equals(cStrMenuBrush)){
						System.out.println( "BRUSH''''''''''''''" );
						JPopupMenu lPopmenu = new JPopupMenu();
						PPgWinUtils.AddMenuItem( lPopmenu, cStrNoBrush,   this );
						PPgWinUtils.AddMenuItem( lPopmenu, cStrBrush+"1", this, "Ressources/Brush/Brush1.jpg", 0, 0);
						PPgWinUtils.AddMenuItem( lPopmenu, cStrBrush+"2", this, "Ressources/Brush/BrushCircleGrad32x32.jpg",  0, 0);

						lPopmenu.show( (JButton) pEv.getSource(), 0, 0);

				} else if(pEv.getActionCommand().equals( cStrNoBrush) ){
						cBrush = null;
				} else if( pEv.getActionCommand().equals( cStrBrush+"1")){
						cBrush = PPgBrush.CreateBrush( new File( "Ressources/Brush/Brush1.jpg" ) );
				} else if( pEv.getActionCommand().equals( cStrBrush+"2")){

						System.out.println( "      BRUSH Ressources/Brush/BrushCircleGrad32x32.jpg"   );
						cBrush = PPgBrush.CreateBrush( new File( "Ressources/Brush/BrushCircleGrad32x32.jpg" ) );
				}
								
	}
		//------------------------------------------------
		void draw( Graphics2D pG ){

				cMyInst.cSelectZone.clip( pG );


				//				AlphaComposite lComposite = AlphaComposite.getInstance( AlphaComposite.SRC_IN, (float)( sOpacity));
				AlphaComposite lComposite = AlphaComposite.getInstance( cComposite, (float)( sOpacity));
				pG.setComposite(lComposite);
				if( cBrush == null ){
						if( cFlagOver ) {
								
								Point lPoint = cArrayPoint.get(0);
								cMemPoint.x = lPoint.x ;
								cMemPoint.y = lPoint.y ;
								
								
								for( int i=0; i< cArrayPoint.size(); i++ ){
										
										lPoint = cArrayPoint.get(i);
										
										pG.drawLine( cMemPoint.x, cMemPoint.y,  lPoint.x, lPoint.y );
										
										cMemPoint.x = lPoint.x ;
										cMemPoint.y = lPoint.y ;
								}
						}
						else {
								int [] lX = new int[cArrayPoint.size()];
								int [] lY = new int[cArrayPoint.size()];
								for( int i=0; i< cArrayPoint.size(); i++){
										lX[i] = cArrayPoint.get(i).x;
										lY[i] = cArrayPoint.get(i).y;
								}	
								pG.drawPolyline( lX, lY,  cArrayPoint.size() );
						}
				}	else {
								for( int i=0; i< cArrayPoint.size(); i++){
										Point lPt = cArrayPoint.get(i);
										pG.drawImage( cBrush.getImage(), lPt.x, lPt.y, getStrokeSize(), getStrokeSize(), cMyInst.cOpProps.getColor(), null );
								}
				}
				
		}


		//------------------------------------------------


		public void moveOp(  Point pPoint ){

				
				cArrayPoint.add( new Point( pPoint ) );

	
				cMyInst.cLayerGroup.restoreCurrentLayer();

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics(); // Pointe sur le layer courant
			

				//				cMyInst.cOpProps.set(lG, witchColor() );
				Color lColor = cMyInst.cOpProps.getColor();// witchColor() );

				float lCol[]= new float[4];
				lColor.getComponents( lCol );

				

				cCurrentColor = new Color(  lCol[0], lCol[1], lCol[2], (float)(lCol[3]*sOpacity) );
				lG.setColor( cCurrentColor );

				lG.setColor( cMyInst.cOpProps.getColor() );

				
				lG.setStroke(  new BasicStroke( getStrokeSize(), cCap, cJoin ) );

				if( cMotif != null ){
						lG.setPaint( cMotif.getPaint() );
				}

				draw( lG );

						//	cMemPoint = pPoint ;

				cMyInst.cCanvas.actualize();
		}

		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------

		public String histoGetName() { return "Brush"; }
		//------------------------------------------------
		public String histoTraductToComment( String pData ){
				return "Brush" ;
		}
		//------------------------------------------------
		public String histoGetData(){

				StringBuilder lStr= new StringBuilder(10 + cArrayPoint.size()*8 );

				//
		    //PPgWinUtils.WriteColor( cCurrentColor, lStr){

				lStr.append( "" + getStrokeSize() + ':' );
				lStr.append( "" + sOpacity + ':' );

				lStr.append( "" + cArrayPoint.size() + ':' );

				for( Point lPoint: cArrayPoint ){
						PPgWinUtils.WritePoint( lPoint, lStr );
				}
				return lStr.toString();
		}		 
		//------------------------------------------------
		public void histoReplay(  String pData ){

				PPgRef<Integer> lRefInt = new PPgRef<Integer>();
				PPgRef<Double> lRefDouble = new PPgRef<Double>();

				int lPos = 0;

				//		lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				//int lColorNum = lRefInt.get();
				
				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lSize = lRefInt.get();
				
				lPos = PPgWinUtils.ReadDouble( lRefDouble, pData, lPos, ':' );
				sOpacity = lRefDouble.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lNbPt = lRefInt.get();

				
				//	int lPos = pData.indexOf( ':', lPos );

				//				int lNbPt = Integer.parseInt( pData.substring( 0, lPos++ ));

				System.out.println( "\treplay "+ pData + " ==>" +lPos + " Nb:" + lNbPt);
				

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();
				cMyInst.cOpProps.set( lG );//, lColorNum );

				Color lColor = cMyInst.cOpProps.getColor();// witchColor() );

				float lCol[]= new float[4];
				lColor.getComponents( lCol );

				

				cCurrentColor = new Color(  lCol[0], lCol[1], lCol[2], (float)(lCol[3]*sOpacity) );
				lG.setColor( cCurrentColor );

		
				//		lG.setStroke(  new BasicStroke( lSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
				lG.setStroke(  new BasicStroke( lSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ) );


				Point lMemPt = new Point();

				//	PPgWinUtils.ReadPoint( lMemPt, pData, lPos);
				cArrayPoint.clear();
				for(int i=0; i< lNbPt; i++){
						Point lPoint = new Point();
						
						lPos = PPgWinUtils.ReadPoint( lPoint, pData, lPos);
						cArrayPoint.add( lPoint );

						//						System.out.println( "\treplay Pt:" + lPoint.x + ',' + lPoint.y  + " Pos:" + lPos );
						//		lG.drawLine( lMemPt.x, lMemPt.y,  lPoint.x, lPoint.y);
				
						// lMemPt = lPoint;						
				}					
				draw( lG );
				cArrayPoint.clear();
		}

		//------------------------------------------------		

}
//*************************************************
