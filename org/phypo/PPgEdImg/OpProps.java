package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import  java.awt.event.*;

import org.phypo.PPg.PPgWin.PPgWinUtils;
import org.phypo.PPg.PPgUtils.PPgRef;

//*************************************************
// Maintient les proprietes courante comme la couleur ...

class OpProps  extends OpGraf{

		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}

		
		ArrayList<Color> cMemLastColor = new 	ArrayList<Color>();
		public 	ArrayList<Color> getMemLastColor()  { return cMemLastColor; }

	
		Color cColor1 = Color.black;
		public Color cColor2 = Color.white;

		int   cSize = 1;
		
		boolean cAntialiasing = false;

		void set( Graphics2D pG ){//, int pIdxColor ){
				
				// Lissage du texte et des dessins

				if( cAntialiasing ){
						System.out.println("<<<<<<<<<<<<<<<<<<<<<ANTIALIASING>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						pG.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON ); 
						pG.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
				}

				//				if( pIdxColor == 1 )
				pG.setColor( cColor1 );
				//				else 
				//						pG.setColor( cColor2 );
								
				pG.setStroke(  new BasicStroke( cSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
				
		}		
		/*
			void setColor1(  Color pColor ){
			cColor1 = pColor;
			
			PPgMain.sThePPgMain.drawColorButtons();
			
			}
		*/

		void setColor2( Color pColor ){
			cColor2 = pColor;
			
			PPgMain.sThePPgMain.drawColorButtons();
			
			}
		
		void setColor(  Color pColor ) { //, int pIdxColor ){
				//				if( pIdxColor == 1 )

				if( pColor == cColor1 )
						return ;


				//===================================
				int lIndex = cMemLastColor.indexOf( pColor );

				if( lIndex != -1 ){
						cMemLastColor.remove( lIndex );
				}

				
				lIndex = cMemLastColor.indexOf( cColor1 );
				if( lIndex != -1 ){
						cMemLastColor.remove( lIndex );
				}
				

				cMemLastColor.add( 0, cColor1 ); // tester les valeurs aussi
				if( cMemLastColor.size() > 16 ){
						// VERIFIER SI LA COULEUR EST DEJA DANS LA LIST, DANS CE CAS
						// ENLEVER L ANCIEN 
						cMemLastColor.remove( cMemLastColor.size() -1 );
				}
				//===================================


				cColor1 =  pColor;
				//				else 
				//						setColor2( pColor );

				PPgMain.sThePPgMain.drawColorButtons();				
		}
		
		
		

		//Color getColor1(){ return cColor1;}
		Color getColor2(){ return cColor2;}

		Color getColor(){ //int pIdxColor){ 
				//			if( pIdxColor == 1 )
						return cColor1;
				//				return cColor2;
		}

		//-----------------------------------------------
		public OpProps(EdImgInst pMyInst){
				cMyInst = pMyInst;
		}
		

		//-----------------------------------------------
		//--------------- INTERFACE ---------------------
		//-----------------------------------------------
		public void cmdSetColor( Color pColor ){
			
				setColor( pColor );

				StringBuilder lSavStr = new StringBuilder(32);
				lSavStr.append( "C:" );
				
				PPgWinUtils.WriteColor( cColor1, lSavStr );
				cMyInst.cOpControl.save( histoGetCode(), lSavStr.toString());				
		}


		void cmdSetAntialiasing( boolean pFlag ){
				cAntialiasing = pFlag;

				StringBuilder lSavStr = new StringBuilder(32);
				lSavStr.append( "A:" + pFlag );
				
				cMyInst.cOpControl.save( histoGetCode(), lSavStr.toString());								
		}
		//-----------------------------------------------
		/*
		public void cmdSetColor2( Color pColor ){
			
				//		setColor2( pColor );

				StringBuilder lSavStr = new StringBuilder(32);
				lSavStr.append( "c:" );
				
				PPgWinUtils.WriteColor( cColor2, lSavStr );
				cMyInst.cOpControl.save( histoGetCode(), lSavStr.toString());				
		}
		
		public void cmdSetColor( Color pColor, int pIdxColor){
				if( pIdxColor == 1 )
						cmdSetColor1( pColor );
				else 
						cmdSetColor2( pColor );

		}
*/
		

		//-----------------------------------------------
		//------------------ HISTO-----------------------
		//-----------------------------------------------
		public String histoGetName() { return "Props"; }
		public String histoGetData() { return "";}	 // SERT ARIEN 		
		public String histoTraductToComment( String pData ){

				char c = pData.charAt(0);
				switch( c ){
				case 'C': return "Color 1"; 
				case 'c': return "Color 2"; 
				case 'A': return "Antialiasing ";
				}
				return "unknown";
		}

		//-----------------------------------------------

		public void histoReplay(  String pData ){
				char c = pData.charAt(0);
				switch( c ){
				case 'C': {
						
						PPgRef<Color> lRefColor = new PPgRef<Color>();	
					
						PPgWinUtils.ReadColorRGBA( lRefColor, pData, 2 );
						cMyInst.cOpProps.setColor( lRefColor.get() );
				}	break;

				case 'A': {
						PPgRef<Boolean> lRefBool = new PPgRef<Boolean>();
						PPgWinUtils.ReadBoolean( lRefBool,  pData, 2, ':' );
						cAntialiasing = lRefBool.get();
				} break;

						
						/*
				case 'c': {
						
						PPgRef<Color> lRefColor = new PPgRef<Color>();	
					
						PPgWinUtils.ReadColorRGBA( lRefColor, pData, 2 );
						cMyInst.cOpProps.setColor2( lRefColor.get() );
				}	break;
						*/
				}

				DialogLayers.sTheDialog.redraw();	
		}

}
//*************************************************
