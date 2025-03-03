package org.phypo.PPgEdImg;


import java.awt.*;
import java.util.*;


import javax.swing.ImageIcon;


import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.util.regex.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Toolkit;

//*************************************************

class EdImgInst{

		public String cName = null;
		public String getName() { return cName; }
		public void   setName( String pName ) { cName = pName; }


		public PPgFrameEdImg  cFrame      = null;
		public PPgLayerGroup  cLayerGroup = null;
		public OpControler    cOpControl  = null;
		public OpLayers       cOpLayers   = null;
		public PPgCanvasEdImg cCanvas     = null;
		public OpProps        cOpProps    = null;

		//------------------------------------------------
		public boolean cInReplay=false;
		public void    setInReplay( boolean pReplay ) { cInReplay = pReplay; }
		public boolean isInReplay() { return cInReplay; }

		//------------------------------------------------
		static public OpGrafUtil       cCurrentGrafUtil = null;

		static public OpGrafSelect     cSelect     = null;
		static public OpGrafSelZone    cZoneSelect = null;
		static public OpGrafPencil     cPencil     = null;
		static public OpGrafEraser     cEraser     = null;
		static public OpGrafDropper    cDropper    = null;
		static public OpGrafGraduation cGradation  = null;
		static public OpGrafBrush      cBrush      = null;
		static public OpGrafFigure     cFigure     = null;
		static public OpGrafRot        cGrafRot    = null;
		static public OpGrafCorrect    cGrafCorrect= null;
		static public OpGrafFillColor  cFillColor  = null;

		static public SelectZone cSelectZone = new SelectZone( null );
		//public SelectZone cSelectZone = new SelectZone( this );

		static public Cursor sCurrentCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR );



		static public Cursor sFillColorCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/FillColor-20x20.png").getImage(),
																																														new Point( 4, 4), "FillColor" );
			
		static public Cursor sEraserCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/Eraser-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );
			

		static public Cursor sBrushCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/Brush-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );
			
		static public Cursor sCrossCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/Cross-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );

		static public Cursor sDrawLineCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/DrawLine-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );

		static public Cursor sDrawOvalCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/DrawOval-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );

		static public Cursor sDrawRectCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/DrawRectangle-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );

		static public Cursor sDrawRoundRectCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/DrawRoundRect-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );
	
		static public Cursor sDropperCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/Dropper-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );
	
		static public Cursor sGradLineCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/GradLine-20x20.png").getImage(),
																																														new Point( 4, 4), "Graduation" );
		static public Cursor sGradRectCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/GradRect-20x20.png").getImage(),
																																														new Point( 4, 4), "Graduation" );
		static public Cursor sGradOvalCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/GradOval-20x20.png").getImage(),
																																														new Point( 4, 4), "Graduation" );
	
		static public Cursor sMagicWandCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/MagicWand-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );
	
		static public Cursor sPencilCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/Pencil-20x20.png").getImage(),
																																														new Point( 4, 4), "Eraser" );



		static public Cursor sSelectRectCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectRectangle-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );

		static public Cursor sSelectRectAddCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectRectangleAdd-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );

		static public Cursor sSelectRectSubCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectRectangleSub-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );

		static public Cursor sSelectOvalCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectOval-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );

		static public Cursor sSelectOvalAddCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectOvalAdd-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );

		static public Cursor sSelectOvalSubCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/SelectOvalSub-20x20.png").getImage(),
																																									 new Point( 4, 4), "Eraser" );




		//	static public Cursor sCursor = Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Ressources/Cursors/-20x20.png").getImage(),	new Point( 4, 4), "Eraser" );
		//																																													
	

		//------------------------------------------------
		public void setCurrentCursor(){
				if( cCanvas != null ) {
						cCanvas.setCursor( sCurrentCursor );						
				}
		}
		//------------------------------------------------
	
		void setCurrentUtil(OpGrafUtil pGrafUtil) {
		
				cCurrentGrafUtil = pGrafUtil;

				if( PPgMain.sThePPgMain.cToolBar0 != null )
						PPgMain.sThePPgMain.makeToolBar0( );
				
				if( PPgMain.GetCurrentInstance() != null
						&&PPgMain.GetCurrentInstance().cCanvas != null ) {

						PPgMain.GetCurrentInstance().cCanvas.setCursor( sCurrentCursor );						
						PPgMain.GetCurrentInstance().cCanvas.actualize();
				}
		}
		
		//------------------------------------------------
		public OpGrafUtil getCurrentUtil()  { 

				return cCurrentGrafUtil;
		}
		
		//--------------------------------------------------------------
		public void setUtilPencil()    { 
				sCurrentCursor = sPencilCursor;

				setCurrentUtil( cPencil );
		}
		//------------------------------------------------
		public void setUtilEraser()    { 
				sCurrentCursor = sEraserCursor;

				setCurrentUtil( cEraser );
		}
		//------------------------------------------------
		public void setUtilDropper() { 
				sCurrentCursor = sDropperCursor;

				setCurrentUtil( cDropper); 
		}
		//------------------------------------------------
		public void setUtilGraduation(OpGrafGraduation.Type pType) { 

				switch( pType ){
				case LINE:         sCurrentCursor = sGradLineCursor; break;
				case RECTANGLE:    sCurrentCursor = sGradRectCursor; break;
				case ROUND_RECT:   sCurrentCursor = sGradRectCursor ; break;
				case OVAL:         sCurrentCursor = sGradOvalCursor; break;
				case CIRCLE:       sCurrentCursor = sGradOvalCursor; break;
				case UNKNOWN:      sCurrentCursor = sCrossCursor; break;						
				}


				cGradation.setType( pType  ); 
				setCurrentUtil( cGradation ); 
		}
		//------------------------------------------------
		public void setUtilBrush()     { 
				sCurrentCursor = sBrushCursor;

				setCurrentUtil( cBrush  ); 
		}
		//------------------------------------------------
		public void setUtilFillColor() { 
				sCurrentCursor = sFillColorCursor;

				setCurrentUtil( cFillColor); 
		}

		//------------------------------------------------
		public void       setUtilFigure( OpGrafFigure.Type pType) { 
				switch( pType ){
				case LINE:         sCurrentCursor = sDrawLineCursor; break;
				case RECTANGLE:    sCurrentCursor = sDrawRectCursor; break;
				case ROUND_RECT:   sCurrentCursor = sDrawRoundRectCursor ; break;
				case RECTANGLE_3D: sCurrentCursor = sCrossCursor; break;
				case OVAL:         sCurrentCursor = sDrawOvalCursor; break;
				case UNKNOWN:      sCurrentCursor = sCrossCursor; break;						
				}

				cFigure.setType(pType ); 
				setCurrentUtil( cFigure );
		}

		//------------------------------------------------
		public void       setUtilSelect()  {
				sCurrentCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

				setCurrentUtil( cSelect);
		}
		//------------------------------------------------
		public void       setUtilSelZone(OpGrafSelZone.Type pType)  {
				
				cZoneSelect.setType( pType );
				sCurrentCursor = cZoneSelect.getCursor( false );
				setCurrentUtil( cZoneSelect);
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public EdImgInst( PPgFrameEdImg pFrame ){

				cFrame     = pFrame;

				cOpControl   = new OpControler( this );

				cLayerGroup  = new PPgLayerGroup( this );
				cOpLayers    = new OpLayers( this );
			  cOpProps     = new OpProps( this ); 

				//				cCanvas s'enregistre tout seul !

				cSelect    =  new OpGrafSelect( this );
				cZoneSelect=  new OpGrafSelZone( this );
				cPencil    =  new OpGrafPencil( this );
				cEraser    =  new OpGrafEraser( this );
				cDropper   =  new OpGrafDropper( this );
				cGradation =  new OpGrafGraduation( this );
				cBrush     =  new OpGrafBrush( this );
				cFigure    =  new OpGrafFigure(this);
				cGrafRot   =  new OpGrafRot(this);		
				cGrafCorrect = new OpGrafCorrect( this );
				cFillColor =  new OpGrafFillColor( this );
				setUtilPencil();
		}
		//------------------------------------------------
		public void setDefault(){
				cOpProps = new OpProps( this );
				setUtilPencil();
		}
		//------------------------------------------------
		public void initSharedObject(){

				cCurrentGrafUtil.setMyInst ( this ) ; 

				cSelectZone.chgInstance( this ) ;
				cSelect.setMyInst ( this ) ; 
				cZoneSelect.setMyInst ( this ) ; 
				cPencil.setMyInst ( this ) ;    
				cEraser.setMyInst ( this ) ;     
				cDropper.setMyInst ( this ) ;    
				cGradation.setMyInst ( this ) ;    
				cBrush.setMyInst ( this ) ;      
				cFigure.setMyInst ( this ) ;    
				cGrafRot.setMyInst ( this ) ;   
				cGrafCorrect.setMyInst ( this ) ;   
				cFillColor.setMyInst ( this ) ;   

				if( cCanvas != null )
						cCanvas.setCursor( sCurrentCursor );
			
				if( DialogHisto.sTheDialog != null ){
						DialogHisto.sTheDialog.init( PPgMain.GetCurrentInstance().cOpControl );						
						DialogLayers.sTheDialog.init( PPgMain.GetCurrentInstance() );
						DialogColor.sTheDialog.init( PPgMain.GetCurrentInstance().cFrame, PPgMain.GetCurrentInstance().cOpProps.getColor() );	
				}
		}

	
		//------------------------------------------------

		public void setLayerGroup ( PPgLayerGroup pGroup )    { cLayerGroup = pGroup;}
		public void setOpControler( OpControler  pControl ) { cOpControl = pControl;}
		public void setCanvas( PPgCanvasEdImg pCanvas  )      { cCanvas = pCanvas;}
		public void setOpLayers( OpLayers     pOpLayers)      { cOpLayers = pOpLayers; }
		public void setFrame( PPgFrameEdImg  pFrame  ) { cFrame = pFrame; }

		public void setOpProps( OpProps pProps ) { cOpProps = pProps; }
}

//*************************************************
