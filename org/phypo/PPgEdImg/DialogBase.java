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

public abstract class DialogBase extends JDialog
    implements  ActionListener, ChangeListener	{


    protected EdImgInst                  cInst = null;

    protected JButton cButtonCancel = null;
    protected JButton cButtonOk     = null;
    protected JButton cButtonDefault = null;


    protected PPgCanvas        cCanvas = null;



    protected BufferedImage cSavImg = null;
    protected BufferedImage cTmpImg = null;



    protected int cPreviewMaxWidth = 500;
    protected int cPreviewMaxHeight = 500;
    protected int cPreviewLastWidth = 0;
    protected int cPreviewLastHeight= 0;

    protected float cFactorSz = 1.0f;

    protected int cLastWidth;
    protected int cLastHeight;
    protected int cMaxWidth  = 640;
    protected int cMaxHeight = 480;

    protected boolean cFlagPreview = false;

    //------------------------------------------------
		
    DialogBase(  EdImgInst pInst, String pName, boolean pFlagPreview ){
        super( PPgJFrame.sTheTopFrame, pName,  true );

        cFlagPreview = pFlagPreview;
        cInst = pInst;
    }

    //------------------------------------------------
    protected void superInitInterface(){

        callInitDefault();

        //=================================
        getContentPane().setLayout( new BorderLayout() );	
	
        JPanel lSouth = new JPanel();
        //		lSouth.setLayout( new GridLayout( 1, 2 ));
        lSouth.setLayout( new FlowLayout( ));
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
        //=================================


        getContentPane().add( callInitInterface(), BorderLayout.NORTH );


        if( cFlagPreview ){

            cSavImg = ImgUtils.GetSameBufferImage( cInst.cCanvas.getBufferImg() );
						
            //=============================
            cLastWidth  = cInst.cCanvas.getBufferImg().getWidth();
            cLastHeight = cInst.cCanvas.getBufferImg().getHeight();
						
            cFactorSz =1;
            float lFactorSzW = cMaxWidth / (float)cLastWidth;
            float lFactorSzH = cMaxHeight/ (float)cLastHeight;
						
            if( lFactorSzW < 1f )
                cFactorSz = lFactorSzW;
						
            if( lFactorSzH < 1f && lFactorSzH < lFactorSzW ){
                cFactorSz = lFactorSzH;
            }
            cLastWidth  *= cFactorSz;
            cLastHeight *= cFactorSz;
            //=============================

						
            Graphics2D lG = (Graphics2D) cSavImg.getGraphics();
            cInst.cLayerGroup.draw( lG );
            lG.dispose();
						
            BufferedImage lTmpImg = ImgUtils.GetSameBufferImage( cInst.cCanvas.getBufferImg(), cLastWidth, cLastHeight );
            lG =  (Graphics2D)lTmpImg.getGraphics();
            lG.drawImage( cSavImg, 0, 0, cLastWidth, cLastHeight, null );	
            lG.dispose();
            cSavImg = lTmpImg;

            cCanvas = new PPgCanvas() {
										
                    public void paint(  Graphics pG ){
                        if( cTmpImg != null )
                            pG.drawImage( cTmpImg, 0, 0, cLastWidth, cLastHeight, null );									 										
                    }
                };
						
            cCanvas.setPreferredSize( new Dimension( cLastWidth, cLastHeight ));	
            cCanvas.setMaximumSize(   new Dimension( cLastWidth, cLastHeight ));		
            cTmpImg = cSavImg;

            //						JPanel lCenter = new JPanel();

            //		lCenter.add( cCanvas );

            System.out.println("**************** CANVAS PREVIEW *************** " + cLastWidth + " " + cLastHeight);
            getContentPane().add( cCanvas, BorderLayout.CENTER );
        }

        pack();
        setVisible(true);
    }
    //------------------------------------------------

    protected void   callInitDefault() {; }
    abstract  protected JPanel callInitInterface(); 
    protected void   callMakePreview() {;}		
    abstract  protected void	 callButtonOk();
    protected void   callButtonCancel()  {;}		 
    protected void   callButtonDefault() {;}
    
    //------------------------------------------------
    public void stateChanged( ChangeEvent  pEvent){
    }
    //------------------------------------------------
    public void actionPerformed( ActionEvent pEv ){
				
        if( pEv.getSource() == cButtonCancel ) {

            callButtonCancel();
            dispose();

        }	else if( pEv.getSource() == cButtonDefault ) {
            callInitDefault();
            callButtonDefault();
            callMakePreview();
        }
        else if( pEv.getSource() == cButtonOk ) {

            callButtonOk();
            dispose();
        }
    }
}
//*************************************************
