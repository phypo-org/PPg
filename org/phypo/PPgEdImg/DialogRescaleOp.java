package org.phypo.PPgEdImg;




import java.awt.image.*;
import javax.swing.*;
import java.io.File;

import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;

import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;

import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgImg.*;



//*************************************************

public class DialogRescaleOp extends DialogBase
		implements  ActionListener, ChangeListener	{
	
	

		PPgSliderField []  cSliderTScale   = { null, null,null };
		PPgSliderField []  cSliderTOffset  = { null, null,null }; //= new PPgSliderField[3];

		JToggleButton cToggleLock = null;
		boolean       cLock= false;


		int   cMaxScale = 1024;
		float cMulScale = 128;
		int   cMaxOffset = 256;

		float [] cScaleOp = new float[4];
		float [] cOffsetOp = new float[4];
		
		public static DialogRescaleOp sTheDialog = null;




		//------------------------------------------------
		public	DialogRescaleOp(  EdImgInst pInst ){

				super(  pInst, "Rescale Op",  true );

				superInitInterface();

				sTheDialog = this;
		}

		//------------------------------------------------
		protected void callInitDefault(){
				
				for( int i=0; i< 4; i++ ){
						cScaleOp[i] = 1.0f;
						cOffsetOp[i] = 0.0f;
				}

		}
		//------------------------------------------------
		protected JPanel callInitInterface(){
				
		JPanel lPanel = new JPanel();
		
		lPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED));
		
		lPanel.setLayout(  new BorderLayout());
		
		JPanel lPanelScale = new JPanel();

		Border lBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED);
		lPanelScale.setBorder( BorderFactory.createTitledBorder( lBorder, "Color scale", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ));

		lPanel.add( lPanelScale, BorderLayout.NORTH );
		lPanelScale.setLayout(  new GridLayout( 3, 1 ));


		//		JLabel lTitleLabel;
		//		lPanelScale.add( (lTitleLabel=new JLabel("Color scale" )));
		//lTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

		for( int i = 0; i< 3; i++ ){		
				lPanelScale.add( (cSliderTScale[i] = new PPgSliderField( "", -cMaxScale, cMaxScale, (int)(cScaleOp[i]), this )));
				setDeco( cSliderTScale[i], cMaxScale );				
				cSliderTScale[i].cLabel.setIcon( EdImgUtils.sBulletIconRGB[i] );
		}


		JPanel lPanelOffset = new JPanel();
		lBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED);
		lPanelOffset.setBorder( BorderFactory.createTitledBorder( lBorder, "Color offset", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ));

		lPanel.add( lPanelOffset, BorderLayout.CENTER );
		lPanelOffset.setLayout(  new GridLayout( 3, 1 ));
		
		for( int i = 0; i< 3; i++ ){		
				lPanelOffset.add( (cSliderTOffset[i] = new PPgSliderField( "",  -cMaxOffset, cMaxOffset, (int)(cOffsetOp[i]), this )));

				setDeco( cSliderTOffset[i], cMaxOffset );				
				cSliderTOffset[i].cLabel.setIcon( EdImgUtils.sBulletIconRGB[i] );
		}



		JPanel lPanelButton = new JPanel();
		lPanelButton.setLayout( new FlowLayout() );

		lPanelButton.add( (	cButtonDefault= new JButton( "Default" )));

		cButtonDefault.addActionListener( this );

		lPanelButton.add(( cToggleLock= new JToggleButton( EdImgUtils.sImgUnlock )));
		cToggleLock.addActionListener( this );

		lPanel.add(lPanelButton, BorderLayout.SOUTH);
		
		return lPanel;
		}
		//------------------------------------------------
		void updateInterfaceValues(){

				for( int i=0; i< 3; i ++ ){
						cSliderTScale[i].setValue( (int)cScaleOp[i] );
						cSliderTOffset[i].setValue( (int)cOffsetOp[i] );
				}
		}
		//------------------------------------------------
		void setDeco( PPgSliderField pSlider, int pSz ){

				Dimension lDim = pSlider.cSlider.getPreferredSize();
				pSlider.cSlider.setPreferredSize( new Dimension( 400, 40 ));	
				pSlider.cSlider.setMaximumSize(   new Dimension( 400, 60 ));		
				//	pSlider.setPaintLabels(false);
				
				pSlider.cSlider.setPaintTicks( true );
				pSlider.cSlider.setPaintTrack(true);
				pSlider.cSlider.setPaintLabels(true);
				pSlider.cSlider.setMajorTickSpacing( (int)(pSz/2));
				pSlider.cSlider.setMinorTickSpacing( (int)(pSz/8));
		}

		//------------------------------------------------
		protected void callMakePreview(){			

				for( int i=0; i< 3; i++ ){
						System.out.print( cScaleOp[i] + " " +	cOffsetOp[i]  + ",  " );
				}
				System.out.println("");
				
				cTmpImg = ImgUtils.RescaleOp( cSavImg, cScaleOp, cOffsetOp );	
				cCanvas.actualize();
		}
		//---------------------
		protected void callButtonOk(){
				cInst.cGrafCorrect.setParam( cScaleOp, cOffsetOp ); 
				cInst.cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.RESCALE_OP_IMAGE );	
				
		}
		//---------------------
		protected void callButtonDefault(){
				
				updateInterfaceValues();			
		}
		//---------------------
		public void actionPerformed( ActionEvent pEv ){
				
				super.actionPerformed( pEv );

				if( pEv.getSource() == cToggleLock ) {
						
						if( cLock == true ) {
								cToggleLock.setIcon( EdImgUtils.sImgUnlock );								
								cLock = false;
						}
						else {
								cToggleLock.setIcon( EdImgUtils.sImgLock );
								cLock = true;
						}
				}	
		}
		//---------------------
		public void stateChanged( ChangeEvent  pEvent){
				
				super.stateChanged( pEvent );

				if( cLock ){
						for( int i=0; i< 3 ; i++ ){
								if( pEvent.getSource() == cSliderTScale[i] ){
										for( int j=0; j< 3; j++ )  {
												if( j != i )
														cSliderTScale[j].setValue( cSliderTScale[i].getValue() );
										}
								}
								else
										if( pEvent.getSource() == cSliderTOffset[i] ){
												for( int j=0; j< 3; j++ )  {
														if( j != i )
																cSliderTOffset[j].setValue( cSliderTOffset[i].getValue() );
												}
										}
						}						
				}
				
				// On le fait de toutes facon
				for( int i=0; i< 3 ; i++ ){
						if( cSliderTScale[i].getValue()/cMulScale > 0 ){
								cScaleOp[i] =  (cSliderTScale[i].getValue()+cMulScale)/cMulScale;
								
						}else if( cSliderTScale[i].getValue()/cMulScale < 0 ){
								cScaleOp[i] =  1.0f/(((-cSliderTScale[i].getValue())+cMulScale)/cMulScale);
						}else	cScaleOp[i] = 1;		
						
						cOffsetOp[i] = cSliderTOffset[i].getValue();
				}


				callMakePreview();
		}
}

//*************************************************
