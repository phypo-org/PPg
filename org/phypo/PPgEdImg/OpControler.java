package org.phypo.PPgEdImg;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;



import org.phypo.PPg.PPgUtils.PPgLog;


//*************************************************
public class OpControler{

		EdImgInst cMyInst = null;



		OpHisto cOpHisto = new OpHisto();
		OpHisto getHisto() { return cOpHisto; }
		
		//------------------------------------------------
		OpControler( EdImgInst pMyInst ){
				cMyInst = 	pMyInst;
		}
		//------------------------------------------------
		static HashMap< Character, OpGraf> sHashOp = new HashMap< Character, OpGraf >();

		//------------------------------------------------
		static void RegisterOpPrototype( OpGraf pOp, char pKey ){

				pOp.setKeyCode( pKey );
				sHashOp.put( pKey, pOp );
		}		
		//------------------------------------------------
		static public void Init(){
				RegisterOpPrototype( new OpGrafRot(null),    'R');
				RegisterOpPrototype( new OpGrafCorrect(null),'C');
				RegisterOpPrototype( new OpLayers(null) ,    'L');  
				RegisterOpPrototype( new OpGrafSelect(null), 'S');
				RegisterOpPrototype( new OpGrafPencil(null), 'P');
				RegisterOpPrototype( new OpProps(null),      'p');
				RegisterOpPrototype( new OpGrafFigure(null), 'F');
				RegisterOpPrototype( new OpGrafEraser(null), 'E');
				RegisterOpPrototype( new OpGrafSelZone(null),'Z');
				RegisterOpPrototype( new OpGrafBrush(null),  'B');
				RegisterOpPrototype( new OpGrafGraduation(null),  'G');
		}		
		//------------------------------------------------
		private OpGraf getOpFromHisto( String pStr ){

				OpGraf pOpProto = sHashOp.get( pStr.charAt(0) );
				if( pOpProto != null ){
						pOpProto.setMyInst( cMyInst ); // on met la meme instance !  c'est static mais on n'est pas threadé
				}
				return pOpProto;
		}
		//------------------------------------------------
		public String getOpCommentFromPrototype( int pIndexHisto ){
				String lStr = cOpHisto.get( pIndexHisto );
				OpGraf pOpProto = getOpFromHisto( lStr );
				
				if( pOpProto != null ){
						return pOpProto.histoTraductToComment(lStr.substring(2));
				}
				return "not found";
		}
		//------------------------------------------------
		public int save( OpGraf pOp ) {

				if( cMyInst.isInReplay() ) 
						return 0;
 
				cOpHisto.addOp( pOp.histoGetCode(), pOp.histoGetData() );

				if( DialogHisto.sTheDialog != null )
						DialogHisto.sTheDialog.changeData( this );

				if( cMyInst.cCanvas != null )
						cMyInst.cCanvas.setChanged( true ); 

				return 0;
		}
		//------------------------------------------------
		public int save( char pCode, String pData ) {

				if( cMyInst.isInReplay() ) 
						return 0;
 
				cOpHisto.addOp( pCode, pData );
				DialogHisto.sTheDialog.changeData( this );

				cMyInst.cCanvas.setChanged( true );
				return 0;
		}
		//------------------------------------------------
		// Rejouer une commande de l'historique

		int replayHisto(int pIndexHisto ){

				System.out.println( "\treplayHisto " + pIndexHisto );
				cMyInst.setInReplay( true );

				try{

				int lBegin = -1; 
				int lEnd = -1;
				if( pIndexHisto == cOpHisto.getCurrent() )
						return 0;

				if( pIndexHisto < cOpHisto.getCurrent()) {
						lBegin = 0;
						lEnd = pIndexHisto;
				}	else {
						lBegin = cOpHisto.getCurrent();
						lEnd =  pIndexHisto;
				}
				
				System.out.println( "\treplayHisto " + pIndexHisto + " begin:" + lBegin + " end:" + lEnd +" current:" + cOpHisto.getCurrent());
				
				
				for( int i=lBegin; i<=lEnd;  i++){
						String lStr = cOpHisto.get( i );

						System.out.println( "\treplayHisto "+ i + " " + lStr);
						
						System.out.println( "\treplayHisto "+ ">" + lStr.substring(2));

						OpGraf pOpProto = getOpFromHisto( lStr );

						if( pOpProto != null ){
								pOpProto.histoReplay( lStr.substring(2));
						}else{
								System.err.println("Histo : Unknow operation :" + lStr );
						}
						
						System.out.println( "\tcOpHisto.setCurrent "+ i);

						cOpHisto.setCurrent(i);
						DialogHisto.sTheDialog.selectRow( i );
				}
				}catch( Exception pEx){
				}
				
				cMyInst.setInReplay( false );

				cMyInst.cFrame.actualize();
				DialogLayers.sTheDialog.redraw();

				return 0;
		}
		//------------------------------------------------
		public boolean testHistoUndo(){ return cOpHisto.getCurrent() > 0;}
		//------------------------------------------------
		public void cmdHistoUndo(){
				int lCurrent = cOpHisto.getCurrent();
				replayHisto( lCurrent -1 );
		}
		//------------------------------------------------
		public boolean testHistoRedo(){ 
				/*
		System.out.println( ">>>>>>>>>>>>>>>>>> testHistoRedo:" + cOpHisto.getCurrent() +" " + (cOpHisto.size()< cOpHisto
.size()) 
														+"  " +   ((cOpHisto.getCurrent() < cOpHisto.size()) && cOpHisto.size()>0)
														+ " current:" + cOpHisto.getCurrent() + " size:" + cOpHisto.size() );
				*/
				return ((cOpHisto.getCurrent() < cOpHisto.size()) && cOpHisto.size()>0) ;	 
		}		
		//------------------------------------------------
		public void cmdHistoRedo(){
				int lCurrent = cOpHisto.getCurrent();
				replayHisto( lCurrent+1  );
		}
		//------------------------------------------------
		public void cmdHistoUndoAll(){
				int lCurrent = cOpHisto.getCurrent();
				replayHisto( 0  );
		}
	
		//------------------------------------------------
		public void cmdHistoRedoAll(){
				replayHisto( cOpHisto.size()-1 );
		}
		//------------------------------------------------
		// Renvoit l'index de la dernier commande de l'historique

		public int getLastIndexHisto(){
				return cOpHisto.size() -1;
		}
	
}
//*************************************************
