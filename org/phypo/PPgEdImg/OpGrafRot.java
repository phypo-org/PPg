package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import org.phypo.PPg.PPgImg.*;

//*************************************************
public class OpGrafRot extends  OpGraf{


    static char sMyKeyCode = ' ';
    public void setKeyCode( char pKeyCode ){
        sMyKeyCode = pKeyCode;
    }
    public char histoGetCode(){
        return sMyKeyCode;
    }




    //=====================================

    public enum RotCode{ 
        FLIP_HORIZONTAL  ( "Flip horizontal", '-'),
        FLIP_VERTICAL( "Flip vertical",   '|'),
        ROT_90       ( "Rotation 90°",    '>'),
        ROT_180      ( "Rotation 180°",   'v'),
        ROT_270      ( "Rotation 270°",   '<'),
        ROT          ( "Rotation",        'r')
        ;
        private final String  cComment;
        private final char    cSubCode;
	
        RotCode( String pStr, char pSubCode ){

            cComment = pStr; 
            cSubCode = pSubCode;
        }
        String getComment() { return cComment; }
        char   getSubCode() { return cSubCode; }


        public static final HashMap<Character,RotCode> sHashRotCode = new HashMap<Character,RotCode>();
        static {
            for( RotCode lRot: RotCode.values() ){
                sHashRotCode.	put(lRot.cSubCode, lRot);
            }
        }

        public static RotCode GetRotCode( char pSubCode ){
            return sHashRotCode.get( pSubCode );
        }
    };
    //=====================================
	
    PPgLayerEdImg cLayer ;
    RotCode       cSubCode;
    float         cVal=0f;
    int           cLayerId=-1;


    //------------------------------------------------
    public OpGrafRot(EdImgInst pMyInst){
        super( pMyInst );
    }
    //------------------------------------------------
    public int makeOp(  RotCode pSubCode, float pVal ){
								
        for( PPgLayerEdImg lLayer : cMyInst.cLayerGroup.getLayers() ) {
            if( lLayer.isVisible() )
                makeOpLayer(  lLayer, pSubCode, pVal );
        }
        return 0;
    }
    //------------------------------------------------
    public int makeOpLayer(  PPgLayerEdImg pLayer, RotCode pSubCode, float pVal){

        cSubCode = pSubCode;
        cVal     = pVal;
        cLayerId = pLayer.getMyId();
				

        switch( pSubCode ){

        case FLIP_HORIZONTAL:
            pLayer.setBufferImg( ImgUtils.FlipHor( pLayer.getBufferImg())); 
            break;
						
        case FLIP_VERTICAL:
            pLayer.setBufferImg( ImgUtils.FlipVer( pLayer.getBufferImg())); 
            break;
						
        case ROT_90:
            pLayer.setBufferImg( ImgUtils.RotQuadrant( pLayer.getBufferImg(), 1)); 
            break;
						
        case ROT_180:
            pLayer.setBufferImg( ImgUtils.RotQuadrant( pLayer.getBufferImg(), 2)); 
            break;
						
        case ROT_270:
            pLayer.setBufferImg( ImgUtils.RotQuadrant( pLayer.getBufferImg(), 3)); 
            break;
						
        case ROT:
            pLayer.setBufferImg( ImgUtils.Rot( pLayer.getBufferImg(), Math.PI/6.0 ) ); 
            break;
						
        default : 
            return 1;
        }
				
        cMyInst.cOpControl.save( this );

        return 0;
    }		
    //------------------------------------------------
    //------------------ HISTO -----------------------
    //------------------------------------------------
		
    public String  histoGetName() { return "Rot"; }
		
    //------------------------------------------------
			
    public String histoGetData(){
        return ""+ cSubCode.getSubCode() + ' ' + cLayerId + ' ' +cVal;
    }		
    //------------------------------------------------
    public String histoTraductToComment( String pData ){
        char lSubCode   = pData.charAt( 0 );
        String lStrData = null;

        if( pData.length() > 1){
            lStrData = pData.substring( 2 );
        }

        RotCode lRot = RotCode.GetRotCode( lSubCode );
        return lRot.getComment() + (lStrData==null?"":lStrData);
    }
    //------------------------------------------------
    public void histoReplay(  String pData ){
				
    }
}
//*************************************************
