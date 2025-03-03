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

public class DialogPosterize extends DialogBase
    implements  ActionListener, ChangeListener	{

	
    PPgSliderField  [] cSliderFactor = { null, null,null };
    PPgSliderField  [] cSliderOffset = { null, null,null };


    int [] cFactPoster   = new int[3]; 
    int [] cOffsetPoster = new int[3];

    JToggleButton cToggleLock = null;
    boolean       cLock= false;

    public static DialogPosterize sTheDialog = null;


    //------------------------------------------------
    public	DialogPosterize( EdImgInst pInst ){

        super( pInst, "Polarize",  true );

        superInitInterface();
        sTheDialog = this;
    }		
    //------------------------------------------------
    protected void callInitDefault(){
        for( int i=0; i< 3; i++ ){
            cFactPoster[i] = 1; 
            cOffsetPoster[i]=0;
        }
    }
    //------------------------------------------------
    protected JPanel callInitInterface() {

        JPanel lPanel = new JPanel();
        lPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED));
				
        lPanel.setLayout(  new BorderLayout());
        JPanel lPanelFact = new JPanel();
				
        Border lBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED);
        lPanelFact.setBorder( BorderFactory.createTitledBorder( lBorder, "Factor", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ));
        lPanel.add( lPanelFact, BorderLayout.NORTH );
        lPanelFact.setLayout(  new GridLayout( 3, 1 ));

        for( int i = 0; i< 3; i++ ){		
            lPanelFact.add( (cSliderFactor[i] = new PPgSliderField( "", 1, 255, (int)cFactPoster[i] , this )));
            setDeco( cSliderFactor[i], 255 );				
            cSliderFactor[i].cLabel.setIcon( EdImgUtils.sBulletIconRGB[i] );
        }
				
        JPanel lPanelOffset = new JPanel();
        lBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED);
        lPanelOffset.setBorder( BorderFactory.createTitledBorder( lBorder, "Offset", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION ));
				
        lPanel.add( lPanelOffset, BorderLayout.CENTER );
        lPanelOffset.setLayout(  new GridLayout( 3, 1 ));
				
        for( int i = 0; i< 3; i++ ){		
            lPanelOffset.add( (cSliderOffset[i] = new PPgSliderField( "", 0, 255, (int)cOffsetPoster[i] , this )));
            setDeco( cSliderOffset[i], 255 );				
            cSliderOffset[i].cLabel.setIcon( EdImgUtils.sBulletIconRGB[i] );
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
            cSliderFactor[i].setValue( (int)cFactPoster[i] );
            cSliderOffset[i].setValue( (int)cOffsetPoster[i] );
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

        cTmpImg = ImgUtils.PosterizeRGB( cSavImg, cFactPoster, cOffsetPoster );
        cCanvas.actualize();
    }
    //---------------------
    protected void callButtonOk(){
        cInst.cGrafCorrect.setParam( cFactPoster, cOffsetPoster );
        cInst.cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.POSTERIZE_IMAGE );
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
    //------------------------------------------------
    public void stateChanged( ChangeEvent  pEvent){

        
        if( cLock ){
                    System.out.println( "lock" );

            for( int i=0; i< 3 ; i++ ){
                if( pEvent.getSource() == cSliderFactor[i] ){
                    for( int j=0; j< 3; j++ )  {
                        if( j != i )
                            cSliderFactor[j].setValue( cSliderFactor[i].getValue() );
                    }
                }
                else
                    if( pEvent.getSource() == cSliderOffset[i] ){
                        for( int j=0; j< 3; j++ )  {
                            if( j != i )
                                cSliderOffset[j].setValue( cSliderOffset[i].getValue() );
                        }
                    }
            }						
        }
				
        // On le fait de toutes facon
        for( int i=0; i< 3 ; i++ ){
            cFactPoster[i] =  cSliderFactor[i].getValue();				     		
            cOffsetPoster[i] = cSliderOffset[i].getValue();
        } 


        callMakePreview();
    }
}

//*************************************************
  
