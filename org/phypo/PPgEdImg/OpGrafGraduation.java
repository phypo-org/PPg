package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Rectangle2D;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.MultipleGradientPaint.CycleMethod;

import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafGraduation extends OpGrafUtil{

		static char sMyKeyCode = ' ';
		public void setKeyCode( char pKeyCode ){
				sMyKeyCode = pKeyCode;
		}
		public char histoGetCode(){
				return sMyKeyCode;
		}


		public String histoGetName() { return "Graduation"; }


		static float sSlideX =0;
		static float sSlideY =0;

		float cSlideFactor =10f;


		Point cMemPoint1;
		Point cMemPoint2;


		//------------------------------------------------
		public OpGrafGraduation( EdImgInst pMyInst){

				super( pMyInst );
		}
		//------------------------------------------------
		public void setType(  Type pType ){ cType =pType; System.out.println( "Graduation set " + pType.toString()); }


		//------------------------------------------------
		 void drawGraduation( boolean pCFlagClip, Graphics2D pG, Type pSubCode, Point pA, Point pB ){
				

				 cMyInst.cOpProps.set(pG);//, witchColor() );
			 
				 if( pCFlagClip ) cMyInst.cSelectZone.clip( pG );
				 
				 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
	 
				 int lNbCycling = cNbCycling;
				 if( cCycling == MultipleGradientPaint.CycleMethod.NO_CYCLE ){
						 lNbCycling =1;
				 }
				 

				 float[] lFractions = new float[lNbCycling+1];
				 Color[] lColors    = new Color[lNbCycling+1];

				 
				 float lInc = 1.0f/lNbCycling;
				 
				 boolean lFlagColor = false;


				 for( int i= 0; i< lNbCycling+1; i++ ){

						 lFractions[i] = i*lInc ;

						 if( lFlagColor )
								 lColors[i] = cMyInst.cOpProps.getColor2();
						 else
								 lColors[i] = cMyInst.cOpProps.getColor();
						 
						 lFlagColor = !lFlagColor;
				 }
				 lFractions[lNbCycling] = 1.0f;
				 /*
				 for( int i= 0; i< lNbCycling+1; i++ ){
						 System.out.println( " CYCLING: " + i + " : " + lFractions[i]  + " -> " + lColors[i]  );
				 }
				 */
	 
				 System.out.println( " CYCLING:" +  cCycling );


				 switch( pSubCode ){
				 case LINE:					
						 break;
						 
				 case RECTANGLE: 
						 break;

				 case ROUND_RECT: 
						 {								
								 LinearGradientPaint lPaint = new LinearGradientPaint( cMemPoint1.x, cMemPoint1.y,  cMemPoint2.x, cMemPoint2.y, 
																																			 lFractions, lColors, cCycling );					 
								 pG.setPaint( lPaint );
								 lGH.drawRect( cMemPoint1, cMemPoint2, true );
								 return ;
						 }

				 case OVAL: 
						 {		
								 Rectangle lRect = PPgGraphicsHelper.NewRect( cMemPoint1, cMemPoint2);

								 RadialGradientPaint lPaint = new RadialGradientPaint( lRect, lFractions, lColors,   cCycling );

								 pG.setPaint( lPaint );

								 lGH.drawOval( cMemPoint1, cMemPoint2, true  );
								 return ;
						 }
				 }
				 

				 double lDx = cMemPoint2.x - cMemPoint1.x;
				 double lDy = cMemPoint2.y - cMemPoint1.y;

				 double lD = Math.sqrt( lDx*lDx+lDy*lDy );

				 //	 if( lD > 0.01 )
				 //			 return;
				 
				 
				 double lStepX = lDx / lD;
				 double lStepY = lDy / lD;

				 // Pb possible avec le passage double -> int 

				 int lNbStep = (int) lD;
				 if( lNbStep == 0 )
						 return ;


				 
				 // On calcule les valeur de la droite en parametriques
				 double bx = cMemPoint1.x;
				 double ax = cMemPoint2.x-cMemPoint1.x;
				
				 double by = cMemPoint1.y;
				 double ay = cMemPoint2.y-cMemPoint1.y;

				 double lPente = Math.acos( lStepX );
 			 
				 //				 lNbStep *= 30;
				 lStepX = lDx / lNbStep;
				 lStepY = lDy / lNbStep;


				 if( lDy < 0 )
						 lPente =  2*Math.PI-lPente;

				 double lAngle90 = Math.PI/2;


					
				 //======== Calcul des couleurs ========
				 Color lCol1 = cMyInst.cOpProps.getColor();
				 Color lCol2 = cMyInst.cOpProps.getColor2();

				 float lColRGB1[]= new float[4];
				 lCol1.getComponents( lColRGB1 );

				 float lColRGB2[]= new float[4];
				 lCol2.getComponents( lColRGB2 );


				 double lColStep[]= new double[4];

				 for( int i =0; i< 4;i++)
						 lColStep[i] = (lColRGB2[i] - lColRGB1[i])/lNbStep;

				 //=====================================


				 int cSlideStroke = (int)Math.sqrt( sSlideX*sSlideX +sSlideY*sSlideY );
	 
				 Point.Double lPt = new Point.Double( cMemPoint1.x, cMemPoint1.y );

					 switch( pSubCode ){
						 case LINE:						
								 pG.setStroke(  new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
								 break;
						 case RECTANGLE: 
						 case ROUND_RECT: 
								 pG.setStroke(  new BasicStroke( 1+cSlideStroke*2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
								 break;
						 case OVAL: 
								 pG.setStroke(  new BasicStroke( 3+cSlideStroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
								 break;
					 }

				 Point l1 = null;
				 Point l2 = null;
				 for( int i=0; i< lNbStep; i++ ){
						 
					 for( int c =0; c< 4;c++) lColRGB1[c] += lColStep[c];
						 
					 Color lColor = new Color( lColRGB1[0],  lColRGB1[1], lColRGB1[2], lColRGB1[3] );
					 pG.setColor( lColor );	
					 

					 switch( pSubCode ){
						 case LINE:						
								 l1 = new Point( (int)(lPt.x-Math.cos( lPente+lAngle90)*10000), (int)(lPt.y-Math.sin( lPente+lAngle90)*10000));
								 l2 = new Point( (int)(lPt.x-Math.cos( lPente+lAngle90)*-10000), (int) (lPt.y-Math.sin( lPente+lAngle90)*-10000));
		 
								 lPt.x += lStepX;
								 lPt.y += lStepY;										
								 break;
						 
						 case ROUND_RECT: 
						 case RECTANGLE: 
						 case OVAL: 
								 l1 = new Point( (int)(lPt.x-(lStepX*i)), (int)(lPt.y-(lStepY*i)) );								 
								 l2 = new Point( (int)(lPt.x+lStepX*i), (int)(lPt.y+lStepY*i) );


								 lPt.x += lStepX*sSlideX;
								 lPt.y += lStepY*sSlideY;										
								 
								 break;
					 }
					 draw( pG, pSubCode, l1, l2 );
							 
				 }

		 }
		//------------------------------------------------
		 void draw( Graphics2D pG, Type pSubCode, Point pA, Point pB ){

				 switch( pSubCode ){
				 case LINE:						
						 pG.drawLine( pA.x, pA.y, pB.x, pB.y  );	
						 break;						
						 
				 case RECTANGLE: {						
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 //		 pG.drawRect( pA.x, pA.y, pB.x, pB.y );
						 lGH.drawRect( pA, pB , false );
						 break;
				 }
				 case ROUND_RECT: {	
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.drawRoundRect ( pA, pB, false, 0.2f,  0.2f );
						 break;
				 }					
						 
				 case OVAL: {
						 PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );
						 lGH.drawOval (  pA, pB, false  );
					 break;
				 }
				}				
		}
		//------------------------------------------------
		protected void beginOp(  Point pPoint ){
				
				System.out.println( "\tbeginOp" );

				cMemPoint1 =   pPoint ;

				moveOp(  pPoint );
		}

		//------------------------------------------------

		protected void moveOp( Point pPoint){

				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !

				System.out.println( "\tmoveOp" );
				
				//				pLayerGroup.actualizeFrame(); // pour effacer l'ancien tracé

				cMyInst.cLayerGroup.restoreCurrentLayer();

				cMemPoint2 = pPoint;

				Graphics2D lG = cMyInst.cLayerGroup.getGraphics();
				cMyInst.cOpProps.set(lG);//, witchColor() );

				
				
				drawGraduation( cMyInst.cSelectZone.isActive(), lG, cType, cMemPoint1, cMemPoint2 );

				cMyInst.cCanvas.actualize();
		}
		//------------------------------------------------

		protected void finalizeOp(  Point pPoint ){
				System.out.println( "\tOpGrafGraduation finalizeOp" );
				
				moveOp( pPoint );
	}
		//------------------------------------------------
		public void cancelOp(){

				cMyInst.cCanvas.actualize();				
		}
		//------------------------------------------------		
		//------------------------------------------------
		//------------------------------------------------
		JSlider          cSliderX = null;
		JSlider          cSliderY = null;
		JComboBox        cComboBoxMode = null;
		String           cCyclingMode[]={ "No cycle", "Reflect", "Repeat" };
		JSpinner         cNbCyclingSpinner = null;
		int              cNbCycling = 1;

		MultipleGradientPaint.CycleMethod cCycling = MultipleGradientPaint.CycleMethod.NO_CYCLE;
			
		public void makeToolBar( JToolBar pBar){ 

				// super.makeToolBar( pBar );
				if( cType == Type.OVAL || cType == Type.ROUND_RECT ){
						
						cComboBoxMode = new JComboBox( cCyclingMode );
						cComboBoxMode.setSelectedIndex( 0 );  
						pBar.add(cComboBoxMode );
						cComboBoxMode.addActionListener( this );

						cNbCyclingSpinner  = new JSpinner(  new SpinnerNumberModel( cNbCycling, 1, 256, 1));
						cNbCyclingSpinner.addChangeListener(  this );
						pBar.add(cNbCyclingSpinner);
						cNbCyclingSpinner.setEnabled( false );
				}
				else if( cType == Type.RECTANGLE ){
						
						pBar.add( new JLabel("Slide X", JLabel.CENTER));
						cSliderX = new JSlider( (int)0, (int)cSlideFactor, (int)(sSlideX*cSlideFactor));
						cSliderX.addChangeListener(this);				
						cSliderX.setPaintLabels(false);
						pBar.add( cSliderX ); 
						
						pBar.add( new JLabel("Slide Y", JLabel.CENTER));
						cSliderY = new JSlider( (int)0, (int)cSlideFactor, (int)(sSlideY*cSlideFactor));
						cSliderY.addChangeListener(this);				
						cSliderY.setPaintLabels(false);
						pBar.add( cSliderY );
				}
												
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEvent){
				
				//	super.stateChanged( pEvent );

				if( pEvent.getSource() == cSliderX ){
						sSlideX = cSliderX.getValue()/cSlideFactor;
				}	else if( pEvent.getSource() == cSliderY ){
						sSlideY = cSliderY.getValue()/cSlideFactor;
				}

				if(  pEvent.getSource() == cNbCyclingSpinner ){
					cNbCycling = (int)cNbCyclingSpinner.getValue();	
				}
		}
		//------------------------------------------------
		public void actionPerformed( ActionEvent pEv ){		
				
				super.actionPerformed( pEv );

				if( pEv.getSource() == cComboBoxMode ){
						if( cComboBoxMode.getSelectedIndex() == 0 ){
								cCycling = MultipleGradientPaint.CycleMethod.NO_CYCLE;
								cNbCyclingSpinner.setEnabled( false );

						} else if( cComboBoxMode.getSelectedIndex() == 1 ){	
								cCycling = MultipleGradientPaint.CycleMethod.REFLECT;
								cNbCyclingSpinner.setEnabled( true );

								cNbCycling = (int)cNbCyclingSpinner.getValue();

						} else if( cComboBoxMode.getSelectedIndex() == 2 ){
								cCycling = MultipleGradientPaint.CycleMethod.REPEAT;
								cNbCyclingSpinner.setEnabled( true );

								cNbCycling = (int)cNbCyclingSpinner.getValue();
						}

				}
		}
		//------------------------------------------------
		//------------------ HISTO -----------------------
		//------------------------------------------------
		public String histoTraductToComment(  String pData ){

				char lSubCode   = pData.charAt( 0 );
				return Type.Get( pData.charAt( 0 )).cStr;				
		}
		//------------------------------------------------
		public String histoGetData(){

				StringBuilder lStr= new StringBuilder(10 + 2*8 );
				
				lStr.append(  "" + cType.cVal + ":"+ sSlideX +":"+sSlideY+":" );

				PPgWinUtils.WritePoint( cMemPoint1, lStr );
				
				PPgWinUtils.WritePoint( cMemPoint2, lStr );
				
				return lStr.toString();
		}
		//------------------------------------------------
		public void histoReplay(  String pData ){
				Type lSubCode = Type.Get( pData.charAt( 0 ) );
				
				PPgRef<Integer> lRefInt = new PPgRef<Integer>();
				int lPos = 2;

				//			lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				//			int lColorNum = lRefInt.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				sSlideX = lRefInt.get();

				lPos = PPgWinUtils.ReadInteger( lRefInt, pData, lPos, ':' );
				sSlideY = lRefInt.get();


				Graphics2D lG= cMyInst.cLayerGroup.getGraphics();

				Point lA = new Point();

				lPos =	PPgWinUtils.ReadPoint( lA, pData, lPos);
				
				Point lB = new Point();
				lPos =	PPgWinUtils.ReadPoint( lB, pData, lPos);


				drawGraduation( cMyInst.cSelectZone.isActive(), lG, lSubCode, lA, lB );
		}			
}

//*************************************************
