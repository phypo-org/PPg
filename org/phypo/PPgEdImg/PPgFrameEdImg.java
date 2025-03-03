package org.phypo.PPgEdImg;


import java.awt.BorderLayout;

import java.awt.*;
import java.util.*;
import java.io.IOException;

import javax.swing.ImageIcon;


import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;


import java.util.regex.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.imageio.*;

import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;

import org.phypo.PPg.PPgWin.*;


//*************************************************

public class PPgFrameEdImg extends PPgFrameChild{

		
		public EdImgInst cMyInst = null;
		

		JScrollPane    cScrollPane= null;;
		
		PPgMain        cOwner = null;

		ImageIcon cOriginalImg  = null;
		public ImageIcon getOriginalImage() { return cOriginalImg; }

		File      cFile = null;
		public File getFile() { return cFile;}

		

		InterfaceEdImgListener cListener = null;
		public InterfaceEdImgListener getMyListener() { return cListener;}



		//------------------------------------------------

		public 	PPgFrameEdImg( PPgMain pOwner, InterfaceEdImgListener pListener, ImageIcon pImg, File pFile, int pW, int pH ){

				super( "Edit " + pFile.getName() );

				cMyInst = new EdImgInst( this );


				cMyInst.setName( new String( pFile.getName() ));

				cOwner = pOwner;

				cOriginalImg  = pImg;				
				cFile = pFile;


				cListener = pListener;

				//	setLocation( 100, 100 );
				//		setPreferredSize( new Dimension( 800, 600) );				

				getContentPane().setLayout( new BorderLayout()); 

				new PPgLayerGroup( cMyInst );


				if( cOriginalImg != null ){
						cMyInst.cLayerGroup.loadFirstImg( cOriginalImg );
				} else {
						cMyInst.cOpLayers.cmdFirstLayer( null, pW, pH );

				}				

				new PPgCanvasEdImg( cMyInst );



				JScrollPane cScrollPane = new JScrollPane( cMyInst.cCanvas );

				getContentPane().add( cScrollPane,  BorderLayout.CENTER );


				//============================


				pack();
				setVisible(true);		

		}	
		//------------------------------------------------
		public void actualize() {
				cOwner.menuActualize();
				cMyInst.cCanvas.actualize();
		}
		//------------------------------------------------
		public void internalFrameActivated( InternalFrameEvent pEv){
							System.out.println( "XXXXXXXXXXXXXXXXXXXXXXXX internalFrameActivated" );

				cOwner.setCurrentInstance ( cMyInst );
				
				cMyInst.initSharedObject();

				actualize();
		}
		//-------------------------------------
		public void internalFrameDeactivated( InternalFrameEvent pEv){
				
				System.out.println( "XXXXXXXXXXXXXXXXXXXXXXXX internalFrameDeactivated" );

				
				cMyInst.cCurrentGrafUtil.cmdCancelOp();

				cMyInst.cSelectZone.chgInstance( null ); 
				cMyInst.cCanvas.actualize();

		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
}
//*************************************************