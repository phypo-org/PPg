package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;
import java.lang.ArrayIndexOutOfBoundsException;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.Area;

import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
public class OpGrafFillColor extends OpGrafUtil{


    static char sMyKeyCode = ' ';
    public void setKeyCode( char pKeyCode ){
        sMyKeyCode = pKeyCode;
    }
    public char histoGetCode(){
        return sMyKeyCode;
    }




    // PAS D HISTORIQUE
    public String histoGetName() { return "Fill"; }
    public void   histoReplay(  String pData ){;}
    public String histoTraductToComment(  String pData ){return null;}
    public String histoGetData(){return null;}

    JSpinner cTolField = null;
    static double sFillColorDist = 0.01f;

    static boolean sUseAlpha    = false;
    static boolean sGuardAlpha  = false;
    static boolean sGuardNuance = false;

    JCheckBox cCheckUseAlpha    = null;
    JCheckBox cCheckGuardAlpha  = null;
    JCheckBox cCheckGuardNuance = null;


    //------------------------------------------------
    boolean 
        goodColor( float pColTarget[], float pCol[], int pSz, float pDist ){
				
        float lDistGen=0; 

        for( int i=0 ; i< pSz; i++ ){
            float lDist = pCol[i] - pColTarget[i];
            lDistGen += lDist * lDist;						
        }
        //								System.out.println( "\t\tDistGen " + lDistGen  + " > " +  pDist );
											
        if( lDistGen > pDist ) 
            return false;
				
        return true;
    }

    //------------------------------------------------
    void remplaceColor(  Point pPtBegin, Color pColFill, BufferedImage pBufIn, BufferedImage pBufOut, Area pClip, float pDistance, 
                         boolean pUseAlpha, boolean pGuardAlpha, boolean pGuardNuance ){
			
        System.out.println( "\tOpGrafFillColor remplaceColor " +  pPtBegin + pColFill + pDistance );

        int lNbColorCmp = 3;
        if( pUseAlpha )
            lNbColorCmp = 4;
	
        float lTmpCol[]= new float[4];

        // On recupere la couleur cible
        float lColTarget[]= new float[4];
        try {
            int lPixel = pBufIn.getRGB( pPtBegin.x, pPtBegin.y );

            Color lTmpColor = new Color( lPixel, true );
            lTmpColor.getComponents( lColTarget );						
        }catch( ArrayIndexOutOfBoundsException ex){
            return;
        }	
        System.out.println( "\tOpGrafFillColor target " +  lColTarget);
							
        pDistance = pDistance * pDistance; // Pour eviter le calul de la racine carre



        // On determine une couleur incompatible pour ecrire dans pBufIn
        // et ne pas boucler si pColFill est trop pres de la couleur cible !
        // on essaye le blanc puis le noir 

        int lStopPixel;
        Color.black.getComponents( lTmpCol );
        if( goodColor( lColTarget, lTmpCol, lNbColorCmp,  pDistance ) == false ){
            lStopPixel = 	Color.black.getRGB();
        }
        else {
            Color.white.getComponents( lTmpCol );
            if( goodColor( lColTarget, lTmpCol, 4,  pDistance ) == false ){
                lStopPixel = 	Color.white.getRGB();								
            }
            else return; // le critere de comparaison n'est pas bon !  on va boucler
        }
	
						
				
				
        int lPixFill = pColFill.getRGB();
				
        Stack<Point> lStack = new Stack<Point>();
        float lColFill[]= new float[4];
        pColFill.getComponents( lColFill );
				

        lStack.push( pPtBegin );

        System.out.println( "\tOpGrafFillColor begin boucle");


        //==================================
        while( lStack.empty() == false ){
            try {
                Point lPt = lStack.pop();
								
                //				System.out.println( "\t\tOpGrafFillColor boucle x:" + lPt.x + " y:" +lPt.y );

                // le pixel est il hors du clipping
                if( pClip != null && pClip.contains( lPt )== false ) {
                    //										System.out.println( "\t\tOpGrafFillColor  pclip:" + pClip );
                    continue;
                }
								
                // On recupere le pixel
                int lPixel = pBufIn.getRGB( lPt.x, lPt.y );
								
                //								System.out.println( "\t\tOpGrafFillColor pixels:" + lPixel );
											 																

                // Convertion du pixel en composantes couleurs
                Color lColorPixel = new Color( lPixel,  true );
                lColorPixel.getComponents( lTmpCol );

                //								System.out.println( "\tOpGrafFillColor  pt: "+ lPt  +"  pixels  " + lColorPixel);

								
                if( goodColor( lColTarget, lTmpCol, lNbColorCmp,  pDistance ) == false ) {
                    //										System.out.println( "\tgood color false " );
                    continue;
                }
								
								
                // creation de la couleur de remplacement
								
                // Il faudrait caculer la difference de couleur gloablement (distance ?)
                if( pGuardNuance ) {
                    for( int i=0; i<4; i++ ){
                        lTmpCol[i] = lColFill[i] + (lTmpCol[i] - lColTarget[i]);

                        if( lTmpCol[i] > 1 ) lTmpCol[i]=1;
                        else if( lTmpCol[i] < 0 ) lTmpCol[i] = 0;
                    }										
                    Color lColor = new Color( lTmpCol[0], lTmpCol[1], lTmpCol[2],  lTmpCol[3] );
                    lPixFill = lColor.getRGB();

                } else 	if( pGuardAlpha ){
                    Color lColor = new Color( lColFill[0], lColFill[1], lColFill[2],  lTmpCol[3] );
                    lPixFill = lColor.getRGB();
                }
								
								
                // Ecriture de la couleur de remplacement
                pBufIn.setRGB( lPt.x, lPt.y, lStopPixel );
								
                pBufOut.setRGB( lPt.x, lPt.y, lPixFill );	
								
								
                lStack.push( new Point( lPt.x, lPt.y-1 ));
                lStack.push( new Point( lPt.x+1, lPt.y ));
                lStack.push( new Point( lPt.x, lPt.y+1 ));
                lStack.push( new Point( lPt.x-1, lPt.y ));


            }catch( ArrayIndexOutOfBoundsException ex){
                // hors du buffer !
                System.out.println( "\texeption  " );
                continue;
            }
        }
        //==================================
    }

    //------------------------------------------------
    public OpGrafFillColor( EdImgInst pMyInst){
        super( pMyInst );
    }
    //------------------------------------------------
    protected void beginOp(  Point pPoint ){
				
        System.out.println( "\tOpGrafFillColor beginOp" );


        // La couleur de remplissage
        Color lColor = cMyInst.cOpProps.getColor(); // witchColor() );

        // On recupere l'image affiché
        BufferedImage lBuf = ImgUtils.GetSameBufferImage( cMyInst.cLayerGroup.getCurrentLayer().getBufferImg());
        Graphics2D lG = lBuf.createGraphics();
        cMyInst.cLayerGroup.draw( lG );

        remplaceColor( pPoint, lColor, lBuf, 
                       cMyInst.cLayerGroup.getCurrentLayer().getBufferImg(), 
                       ( cMyInst.cSelectZone.isActive() ? cMyInst.cSelectZone.getForcedArea() : null ),
                       (float)sFillColorDist,
                       sUseAlpha,  sGuardAlpha, sGuardNuance);									 
        cMyInst.cCanvas.actualize();
    }
    //------------------------------------------------

    protected void moveOp( Point pPoint){
				
    }	
    //------------------------------------------------
    protected void finalizeOp(  Point pPoint ){;}
    //------------------------------------------------
    public void cancelOp(){

    }
    //------------------------------------------------
    public void makeToolBar( JToolBar pBar){ 

        //				pBar.add( (cTolField = 
        //new PPgFloatField( "Tolerance", sFillColorDist, PPgField.HORIZONTAL)));



        pBar.add( new JLabel( "Tolerance" ));
        pBar.add( (cTolField = new JSpinner( new SpinnerNumberModel( sFillColorDist*100, 0, 100, 1 ))));
        cTolField.addChangeListener(  this );



        pBar.add( (cCheckUseAlpha = new JCheckBox( "Use Alpha" )));
        cCheckUseAlpha.setSelected( sUseAlpha );
        cCheckUseAlpha.addItemListener( this );

        pBar.add( (cCheckGuardAlpha = new JCheckBox( "Guard Alpha" )));
        cCheckGuardAlpha.setSelected( sGuardAlpha );
        cCheckGuardAlpha.addItemListener( this );
        pBar.add( (cCheckGuardNuance = new JCheckBox( "Guard Nuance" )));
        cCheckGuardNuance.setSelected( sGuardNuance );
        cCheckGuardNuance.addItemListener( this );
    }
    //---------------------
    public void stateChanged( ChangeEvent  pEvent){

        if( pEvent.getSource() == cTolField){
            sFillColorDist  = ((Double) (cTolField.getValue())/100.0);
            System.out.println( "Tolerance =" +  sFillColorDist+  " "+ cTolField.getValue() );
        }		
    }
    //------------------------------------------------		
    public void itemStateChanged(ItemEvent pEv ){
        super.itemStateChanged( pEv );

        Object lSource = pEv.getItemSelectable();
				
        if( lSource == cCheckUseAlpha ){
            if( pEv.getStateChange() == ItemEvent.DESELECTED )
                sUseAlpha  = false;
            else
                sUseAlpha  = true;
        } else	if( lSource == cCheckGuardAlpha ){
            if( pEv.getStateChange() == ItemEvent.DESELECTED )
                sGuardAlpha = false;
            else
                sGuardAlpha = true;
        } else	if( lSource == cCheckGuardNuance ){
            if( pEv.getStateChange() == ItemEvent.DESELECTED )
                sGuardNuance = false;
            else
                sGuardNuance = true;
        }
    }
}

//*************************************************
