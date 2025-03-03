package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafEraser extends OpGrafUtilMem{
		
		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char  histoGetCode(){
				return sMyKeyCode;
		}

		public boolean usePickColor() { return false; }

		static int sEraserSize = 6;

		//------------------------------------------------

		public OpGrafEraser(EdImgInst pMyInst){
				super( pMyInst );

				cFlagBarOpacity = true;
				cFlagBarThickness = false;
		}
		public void beginOp(   Point pPoint ){

				//				if( cFieldSize != null)  cMyStrokeSize = cFieldSize.getInt();

				super.beginOp( pPoint );
		}
		//------------------------------------------------
		public void moveOp(  Point pPoint){
				
				cArrayPoint.add( pPoint );

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();

				lG.setStroke(  new BasicStroke( sEraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );

				AlphaComposite lCompositeClear = AlphaComposite.getInstance( AlphaComposite.DST_IN, (float)( 1-sOpacity));
				//				AlphaComposite lCompositeClear = AlphaComposite.getInstance( AlphaComposite.CLEAR, 0.0f);


				lG.setComposite(lCompositeClear);
				lG.setColor( Color.white );


				lG.drawLine( cMemPoint.x, cMemPoint.y, pPoint.x, pPoint.y );

				cMemPoint = pPoint;

				cMyInst.cCanvas.actualize();
	}
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------

		public String histoGetName() { return "Eraser"; }
		//------------------------------------------------
		public String histoTraductToComment( String pData ){
				return "Eraser" ;
		}
		//------------------------------------------------
		public String histoGetData(){

				StringBuilder lStr= new StringBuilder(10 + cArrayPoint.size()*8 );

				lStr.append( "" + sEraserSize + ':' );
				lStr.append( "" +cArrayPoint.size() + ':' );

				for( Point lPoint: cArrayPoint ){
						PPgWinUtils.WritePoint( lPoint, lStr );
				}
				return lStr.toString();
		}		 
		//------------------------------------------------
		public void histoReplay(  String pData ){

				PPgRef<Integer> lRefInt = new PPgRef<Integer>();

				int lPos = PPgWinUtils.ReadInteger( lRefInt, pData, 0, ':' );
				int lSize = lRefInt.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				int lNbPt = lRefInt.get();

				
				//	int lPos = pData.indexOf( ':', lPos );

				//				int lNbPt = Integer.parseInt( pData.substring( 0, lPos++ ));

				System.out.println( "\treplay "+ pData + " ==>" +lPos + " Nb:" + lNbPt);
				

				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();
				//				cMyInst.cOpProps.set(lG);

				
				lG.setStroke(  new BasicStroke( lSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
				
				AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
				lG.setComposite(lCompositeClear);
				lG.setColor( new Color(0, 0, 0, 0));
				
				Point lMemPt = new Point();

				PPgWinUtils.ReadPoint( lMemPt, pData, lPos);

				for(int i=0; i< lNbPt; i++){
						Point lPoint = new Point();

						lPos = PPgWinUtils.ReadPoint( lPoint, pData, lPos);
						//						System.out.println( "\treplay Pt:" + lPoint.x + ',' + lPoint.y  + " Pos:" + lPos );

						lG.drawLine( lMemPt.x, lMemPt.y,  lPoint.x, lPoint.y);

						lMemPt = lPoint;						
				}					
		}

		//------------------------------------------------		
		JSpinner cFieldEraserSize = null;


		public void makeToolBar( JToolBar pBar){ 
				
				super.makeToolBar( pBar );

				System.out.println( "OpGrafEraser:makeToolBar");

				pBar.add( new JLabel( "Thickness" ));
				pBar.add( (cFieldEraserSize = new JSpinner( new SpinnerNumberModel( sEraserSize, 1, 100, 1 ))));
				cFieldEraserSize.addChangeListener(  this );

			 
				
				//				pBar.add( cFieldSize  = new PPgIntField( "Eraser size", cMyStrokeSize, PPgField.HORIZONTAL));

				//	cFieldSize.setColumns( 3 );
				//	cFieldSize.getTextField().addActionListener( this );
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEvent){
				super.stateChanged( pEvent );

				if( pEvent.getSource() == cFieldEraserSize ){
						sEraserSize = (Integer) cFieldEraserSize.getValue();
						System.out.println( "Thickness =" + sEraserSize +  " " + cFieldEraserSize.getValue() );

				} 
		}
		//----------
		public void actionPerformed( ActionEvent pEvent ){		
				super.actionPerformed( pEvent);
				if( pEvent.getSource() == cFieldEraserSize ){
						sEraserSize  = (Integer) cFieldEraserSize.getValue();
				}
				System.out.println("********** actionPerformed for eraser:" + pEvent );
				//cMyStrokeSize = cFieldSize.getInt();
					//		}
		}		
}
//*************************************************
