package org.phypo.PPgEdImg;

import javax.swing.UIManager.*;

import java.awt.BorderLayout;

import java.awt.*;
import java.util.*;
import java.io.IOException;


import javax.swing.ImageIcon;


import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;

import java.util.regex.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.awt.Image;
import javax.imageio.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;

import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgFileImgChooser.*;

//*************************************************

public class PPgMain extends PPgJFrame 		
    implements ActionListener, ItemListener  {


    static public PPgMain sThePPgMain = null;

    public static JFileChooser sFileChooser = null;
    JLabel cFootBar=null;

    BufferedImage sTransparentBrush;



    //=======================
    EdImgInst cCurrentInst = null;

    public void setCurrentInstance( EdImgInst pInst ){

				
        cCurrentInst = pInst;
    }
		
    public EdImgInst getCurrentInstance( ) {
        return cCurrentInst;
    }

    static public EdImgInst GetCurrentInstance( ) {
        if( sThePPgMain != null )
            return sThePPgMain.getCurrentInstance();

        return null;
    }
    //=======================


    static public void ActualizeCurrentCanvas(){
        if( sThePPgMain.cCurrentInst != null )
            sThePPgMain.cCurrentInst.cCanvas.actualize();
    }
		
    //------------------------------------------------
    static public void MajInterface(){

        if( sThePPgMain.cCurrentInst != null ){

            sThePPgMain.menuActualize();
            sThePPgMain.toolbarActualize();
            sThePPgMain.dialogActualize();

            sThePPgMain.cCurrentInst.cCanvas.actualize();
        }
    }
		

    //------------------------------------------------
    public PPgMain(){

        super( "EdImg", true );

        PPgMotif.InitDefaultMotif();
        PPgBrush.InitDefaultBrush();

        cFootBar = new JLabel( "footbar" );

        getContentPane().add( cFootBar, BorderLayout.SOUTH );

        sThePPgMain = this;

        makeToolBar( 0 );
        makeToolBar( 1 );
    }

    //-----------------------------
    public PPgInterfaceAppli getInterfaceAppli(){
        return null;
    }
    //-----------------------------
    public static JLabel  GetFootBar(){
        return sThePPgMain.cFootBar;
    }
    //-----------------------------
    public static void SetFootText( String pStr ){
        sThePPgMain.cFootBar.setText( pStr );
    }


    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------


    public JToolBar cToolBar0 = null;
    public JToolBar cToolBar1 = null;
    public JToolBar cToolBar2 = null;
    public JToolBar cToolBar3 = null;

    public JToggleButton cToggleButtonUtilSelect = null;


    public JButton cButtonColor1 = null;
    public JButton cButtonColor2 = null;
    public JButton cButtonReverseColor = null;




    JMenu cMenuFile;
    public final String cStrNew       = "New ...";
    public final String cStrOpenFile  = "Open ...";

    public final String cStrSaveFile  = "Save";
    public final String cStrSaveAsFile = "Save as ...";
    public final String cStrSavConf     = "Save Configuration";

    public final String cStrUpd       = "Update";
    public final String cStrQuit      = "Quit";
    public final String cStrAbort     = "Abort";
		


    JMenu cMenuEdit;
    public static final String cStrUndo      = "Undo";
    public static final String cStrRedo      = "Redo";

    public final String cStrCut       = "Cut";
    public final String cStrCopy      = "Copy";
    public final String cStrPaste     = "Paste";
    public final String cStrPasteNL   = "Paste in New Layer";
    public final String cStrPasteNI   = "Paste in New Image";

    public final String cStrSelErase  = "Erase Selection";
    public final String cStrSelFill   = "Fill Selection";
    public final String cStrSelInvert = "Invert Selection";
    public final String cStrSelAll    = "Select All";
    public final String cStrDeselect  = "Deselect";
		
    public final String cStrReselect  = "Reselect";





    JMenu cMenuView;
    public final String cStrGrid       = "View Grid";
    JCheckBoxMenuItem   cCheckGrid;

    public final String cStrZoomP       = "Zoom++";
    public final String cStrZoom0       = "Zoom 0";
    public final String cStrZoomM       = "Zoom--";

    public final String cStrAntiAlias   = "Antialiasing";




    JMenu cMenuImage;
    public final String cStrResize    = "Resize ...";
    public final String cStrFlipHor   = "Flip Horizontal";
    public final String cStrFlipVer   = "Flip Vertical";
    public final String cStrRot90C    = "Rotate 90° Clockwise";
    public final String cStrRot90A    = "Rotate 90° Counter-Clockwise";
    public final String cStrRot180    = "Rotate 180°";
    public final String cStrRot       = "Rotate ... ";

    public final String cStrFilterForBW="Black & white filters";
                    
    JMenu cMenuLayers;
    public static final String cStrClearCurrentLayer    = "Clear current layer";
    public static final String cStrClearAll   = "Clear all layers";
    public static final String cStrAddLayer    = "Add new layer";
    public static final String cStrDelLayer    = "Delete layer";
    public static final String cStrDupLayer    = "Duplicate layer";
    public static final String cStrMergeLayerDown    = "Merge layer down";

    JMenu cMenuColors;
    public static final String cStrGrayScaleImg   = "Gray scale";
    public static final String cStrGrayScaleCurrentLayer   = "Gray scale current layer";

    public static final String cStrInvertColorImg          = "Invert all color";
    public static final String cStrInvertColorCurrentLayer = "Invert all color current layer";

    public static final String cStrRescaleOpImg          = "RescaleOp";
    public static final String cStrPosterizeImg          = "Posterize";
    public static final String cStrPosterizeCurrentLayer = "Posterize current layer";

    JMenu cMenuWindows;




    public final String cStrViewLayers       = "View layers";
    JCheckBoxMenuItem   cCheckViewLayers;
	
    public final String cStrViewHisto       = "View historique";
    JCheckBoxMenuItem   cCheckViewHisto;
	
    public final String cStrViewColor       = "View colors";
    JCheckBoxMenuItem   cCheckViewColor;
	

    public final String cStrUtilSelect = "Select";
    public final String cStrUtilSelZone = "SelZone";
    public final String cStrUtilSelectRect = "SelectRect";
    public final String cStrUtilSelectOval = "SelectOval";

    public final String cStrUtils         = "Utils";
    public final String cStrUtilPencil    = "Pencil";
    public final String cStrUtilFillColor = "FillColor";
    public final String cStrUtilBrush     = "Brush";
    public final String cStrUtilEraser    = "Eraser";
    public final String cStrUtilDropper   = "Dropper";
    public final String cStrUtilGrad      = "Gradient";
    public final String cStrUtilGradLine  = "GradLine";
    public final String cStrUtilGradRect  = "GradRect";
    public final String cStrUtilGradRect2  = "GradRect2";
    public final String cStrUtilGradOval  = "GradOval";
    //  	public final String cStrUtilGradCircle  = "GradCircle";
    public final String cStrUtilRectangle = "Rectangle";
    public final String cStrUtilRoundRect = "RoundRectangle";
    public final String cStrUtil3dRect    = "Rectangle 3D";
    public final String cStrUtilOval      = "Oval";
    public final String cStrUtilLine      = "Line";
    //		public final String cStrUtilPolyline = "Polyline";


    public final String cStrColor1 = " 1 ";
    public final String cStrColor2 = " 2 ";		

    public final String cStrReverseColor = "Reverse color";

    ButtonGroup lGroupUtils = new ButtonGroup();

    //------------------------------------------------
    JMenuItem cItemUndo   = null;
    JMenuItem cItemRedo   = null;
    JMenuItem cItemCut    = null;
    JMenuItem cItemCopy   = null;
    JMenuItem cItemPaste  = null;
    JMenuItem cItemPasteNL= null;
    JMenuItem cItemPasteNI= null;
    JMenuItem cItemSelErase= null;
    JMenuItem cItemSelFill= null;
    JMenuItem cItemSelInvert= null;
    JMenuItem cItemSelAll= null;
    JMenuItem cItemDeselect= null;
    JMenuItem cItemReselect= null;

    //------------------------------------------------
    public void majMenu(){

        if( cToggleButtonUtilSelect != null ) {
            if(	GetCurrentInstance().cSelectZone.isActive() ) 
                cToggleButtonUtilSelect.setEnabled(true);
            else 
                cToggleButtonUtilSelect.setEnabled(false);;
        }
        System.out.println(">>>>>majMenu " + cItemUndo != null);
        if( cItemUndo == null )
            return ;
				
        cItemUndo.setEnabled( GetCurrentInstance().cOpControl.testHistoUndo() );
        cItemRedo.setEnabled( GetCurrentInstance().cOpControl.testHistoRedo() );
								
				
        boolean lSelectZonePixel = (GetCurrentInstance().cSelectZone.getSelectPixels() != null);
        cItemPaste.setEnabled( lSelectZonePixel );
        cItemPasteNI.setEnabled( lSelectZonePixel );
        cItemPasteNL.setEnabled( lSelectZonePixel );
				
				
        boolean lSelectZoneActive =(  GetCurrentInstance().cSelectZone.isActive() && GetCurrentInstance().cSelectZone.getActiveArea() != null);					 
        cItemCut.setEnabled( lSelectZoneActive );
        cItemCopy.setEnabled( lSelectZoneActive );
        cItemSelErase.setEnabled( lSelectZoneActive );
        cItemSelFill.setEnabled( lSelectZoneActive );
        cItemSelInvert.setEnabled( lSelectZoneActive );
        cItemSelAll.setEnabled( lSelectZoneActive );
        cItemDeselect.setEnabled( lSelectZoneActive );
		

        cItemReselect.setEnabled( GetCurrentInstance().cSelectZone.memAreaSize() != 0 );			
    }
    //------------------------------------------------
    private void createMenu(){

        JMenuBar lMenuBar = cMenuBar;
        lMenuBar.removeAll();



        JMenu cMenuEdit = new JMenu( "Edit" );;

        //============================

        JMenuItem lItem;

        {
            cMenuFile = new JMenu( "File" );
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrNew,   this );
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrOpenFile,   this );

            cMenuFile.add( new JSeparator() );
            PPgWinUtils.AddMenuItem( cMenuFile, cStrClearAll , this );

            cMenuFile.add( new JSeparator() );
            if( GetCurrentInstance().cFrame.getMyListener() != null){
                lItem = PPgWinUtils.AddMenuItem( cMenuFile,  cStrUpd,   this );
                //		if( GetCurrentInstance().cCanvas.isChanged() == false ) 
                //			lItem.setEnabled( false );
            }
						
            lItem = PPgWinUtils.AddMenuItem( cMenuFile,  cStrSaveFile,   this );
            //						if( GetCurrentInstance().cCanvas.isChanged() == false ) 
            //		lItem.setEnabled( false );
				
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrSaveAsFile,   this );
            cMenuFile.add( new JSeparator() );
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrSavConf,   this );

            cMenuFile.add( new JSeparator() );
						
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrQuit,  this );
            PPgWinUtils.AddMenuItem( cMenuFile,  cStrAbort, this );
            lMenuBar.add( cMenuFile );
        }
        //============
        {
            cMenuEdit = new JMenu( "Edit" );
            cItemUndo = PPgWinUtils.AddMenuItem( cMenuEdit, cStrUndo, this );
            cItemUndo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK ));
						


            cItemRedo = PPgWinUtils.AddMenuItem( cMenuEdit, cStrRedo, this );



            cMenuEdit.add( new JSeparator() );

            cItemCut = PPgWinUtils.AddMenuItem( cMenuEdit, cStrCut, this );
            cItemCut.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK ));
								



            cItemCopy = PPgWinUtils.AddMenuItem( cMenuEdit, cStrCopy, this );
            cItemCopy.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_C, ActionEvent.CTRL_MASK ));





            // Paste
            cItemPaste = PPgWinUtils.AddMenuItem( cMenuEdit, cStrPaste, this );
            cItemPaste.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_V, ActionEvent.CTRL_MASK ));




            cItemPasteNL = PPgWinUtils.AddMenuItem( cMenuEdit, cStrPasteNL, this );
						


            cItemPasteNI = PPgWinUtils.AddMenuItem( cMenuEdit, cStrPasteNI, this );



            cMenuEdit.add( new JSeparator() );


            cItemSelErase = PPgWinUtils.AddMenuItem( cMenuEdit, cStrSelErase, this );

            cItemSelFill =	PPgWinUtils.AddMenuItem( cMenuEdit, cStrSelFill, this );

            cItemSelInvert = PPgWinUtils.AddMenuItem( cMenuEdit, cStrSelInvert, this );

            cItemSelAll = PPgWinUtils.AddMenuItem( cMenuEdit, cStrSelAll, this );

            cItemDeselect = PPgWinUtils.AddMenuItem( cMenuEdit, cStrDeselect, this );

            cItemReselect = PPgWinUtils.AddMenuItem( cMenuEdit, cStrReselect, this );
            lMenuBar.add( cMenuEdit );

													
            lMenuBar.add( cMenuEdit );
					 
        }
        //============
        {
            cMenuView = new JMenu( "View" );


            lItem = PPgWinUtils.AddMenuItem( cMenuView, cStrZoomP , this, "Ressources/Icones/Zoom in.png",  PPgPref.sToolsSz, PPgPref.sToolsSz );
            lItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK ));

            lItem = PPgWinUtils.AddMenuItem( cMenuView, cStrZoomM , this, "Ressources/Icones/Zoom out.png", PPgPref.sToolsSz, PPgPref.sToolsSz) ;
            lItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK ));

            lItem = PPgWinUtils.AddMenuItem( cMenuView, cStrZoom0 , this, "Ressources/Icones/Zoom.png",     PPgPref.sToolsSz, PPgPref.sToolsSz);
            lItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_0, ActionEvent.CTRL_MASK ));

            cMenuView.add( new JSeparator() );
						
            cMenuView.add( (cCheckGrid= new JCheckBoxMenuItem( cStrGrid )));
            cCheckGrid.setSelected( GetCurrentInstance().cCanvas.cFlagGrid );
            cCheckGrid.addItemListener( this );
						

            cCheckViewHisto=PPgWinUtils.AddJMenuCheckBoxMenuItem( cMenuView, cStrAntiAlias, this,  GetCurrentInstance().cOpProps.cAntialiasing  );



            lMenuBar.add( cMenuView );
        }
        //============
        {
            cMenuLayers = new JMenu( "Layers" );
            PPgWinUtils.AddMenuItem( cMenuLayers, cStrClearCurrentLayer , this );
            cMenuLayers.add( new JSeparator() );
            PPgWinUtils.AddMenuItem( cMenuLayers, cStrAddLayer , this );
            cMenuLayers.add( new JSeparator() );

            PPgWinUtils.AddMenuItem( cMenuLayers, cStrGrayScaleCurrentLayer, this );
            PPgWinUtils.AddMenuItem( cMenuLayers, cStrInvertColorCurrentLayer, this );
            //						PPgWinUtils.AddMenuItem( cMenuLayers, cStrPosterizeCurrentLayer, this );

            lMenuBar.add( cMenuLayers );
        }
        //============
        {
            cMenuColors = new JMenu( "Colors" );
            PPgWinUtils.AddMenuItem( cMenuColors,  cStrGrayScaleImg, this );
            PPgWinUtils.AddMenuItem( cMenuColors,  cStrInvertColorImg, this ); 
            PPgWinUtils.AddMenuItem( cMenuColors,  cStrPosterizeImg, this ); // Appeler un dialog !
            PPgWinUtils.AddMenuItem( cMenuColors,  cStrRescaleOpImg, this ); // Appeler un dialog !

            lMenuBar.add( cMenuColors );
        }

	
        //============
        {
            //				cMenuEdit = new JMenu( "Edit" );
            cMenuImage = new JMenu( "Image" );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrResize,  this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrFlipHor, this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrFlipVer, this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrRot90C,  this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrRot90A,  this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrRot180,  this );
            PPgWinUtils.AddMenuItem( cMenuImage, cStrRot,     this );
                                                
            cMenuImage.add( new JSeparator() );

            PPgWinUtils.AddMenuItem( cMenuImage, cStrFilterForBW,  this );
            lMenuBar.add( cMenuImage );
        }
        //============
        {
            cMenuWindows = new JMenu( "Windows" );

            cCheckViewLayers= PPgWinUtils.AddJMenuCheckBoxMenuItem( cMenuWindows, cStrViewLayers, this, DialogLayers.sFlagView, "Ressources/Icones/Layers.png", PPgPref.sToolsSz, PPgPref.sToolsSz);

            //						cMenuWindows.add( (cCheckViewLayers= new JCheckBoxMenuItem( cStrViewLayers,));
            //						cCheckViewLayers.setSelected( DialogLayers.sFlagViewLayers );
            //						cCheckViewLayers.addItemListener( this );

            cCheckViewHisto=PPgWinUtils.AddJMenuCheckBoxMenuItem( cMenuWindows, cStrViewHisto, this, DialogHisto.sFlagView );

            //		cMenuWindows.add( (cCheckViewHisto= new JCheckBoxMenuItem( cStrViewHisto )));
            //		cCheckViewHisto.setSelected( DialogHisto.sFlagView );
            //		cCheckViewHisto.addItemListener( this );
						

            cCheckViewColor= PPgWinUtils.AddJMenuCheckBoxMenuItem( cMenuWindows, cStrViewColor, this, DialogColor.sFlagView, "Ressources/Icones/Color palette.png", PPgPref.sToolsSz, PPgPref.sToolsSz);
            //						cMenuWindows.add( (cCheckViewColor= new JCheckBoxMenuItem( cStrViewColor ));
            //						cCheckViewColor.setSelected( DialogColor.sFlagView );
            //						cCheckViewColor.addItemListener( this );
						
            // AJOUTER LES DOCUMENTS OUVERT AVEC UN SEPARATEUR DEVANT !

            lMenuBar.add( cMenuWindows );
        }
        //=============================


        majMenu();
    }

    //------------------------------------------------
    void createDialog(){

        //=====================================
        if( DialogLayers.sTheDialog == null ) {
            DialogLayers.sTheDialog = new DialogLayers( GetCurrentInstance() );
        } else {
            DialogLayers.sTheDialog.init( GetCurrentInstance() );
        }
	
        //=====================================
        if( DialogHisto.sTheDialog == null ) {
            DialogHisto.sTheDialog = new DialogHisto( GetCurrentInstance().cOpControl );
        } else {
            DialogHisto.sTheDialog.init( GetCurrentInstance().cOpControl );						
        }	
        //=====================================
        if( DialogColor.sTheDialog == null ) {

            DialogColor.sTheDialog = new DialogColor( GetCurrentInstance().cFrame, GetCurrentInstance().cOpProps.getColor() );
        } else {
            DialogColor.sTheDialog.init( GetCurrentInstance().cFrame, GetCurrentInstance().cOpProps.getColor() );						
        }	
    }

    //------------------------------------------------

    ImageIcon cImgNewImage =ImgUtils.LoadImageFromFile( new File("Ressources/Icones/New image.png"), PPgPref.sToolsSz, PPgPref.sToolsSz, false, 1.0);			 
    ImageIcon cImgOpenFile = ImgUtils.LoadImageFromFile( new File("Ressources/Icones/Open v2.png"), PPgPref.sToolsSz, PPgPref.sToolsSz, false, 1.0);
    ImageIcon cImgZoomOut = ImgUtils.LoadImageFromFile( new File("Ressources/Icones/Zoom out.png"), PPgPref.sToolsSz, PPgPref.sToolsSz, false, 1.0);
    ImageIcon cImgZoomIn = ImgUtils.LoadImageFromFile( new File("Ressources/Icones/Zoom in.png"), PPgPref.sToolsSz, PPgPref.sToolsSz, false, 1.0);
    ImageIcon cImgReverse= ImgUtils.LoadImageFromFile( new File("Ressources/Icones/Left-right.png"), PPgPref.sToolsSz-3, PPgPref.sToolsSz-3, false, 1.0);

    public void makeToolBar0(){

        //=====================================
        if( cToolBar0 == null )
            cToolBar0 = makeToolBar( 0 );

        cToolBar0.removeAll();
				
        //	cToolBar0.setLayout( new FlowLayout() );

        cToolBar0.add( PPgWinUtils.MakeButton( cStrNew, this, cImgNewImage ));
        cToolBar0.add( PPgWinUtils.MakeButton( cStrOpenFile,  this, cImgOpenFile ));
        cToolBar0.add( PPgWinUtils.MakeButton( cStrZoomM, this, cImgZoomOut));
        cToolBar0.add( PPgWinUtils.MakeButton( cStrZoomP, this, cImgZoomIn ));

        cToolBar0.addSeparator();

        cToolBar0.add( PPgWinUtils.MakeToggleButton( cStrUtilFillColor, this, false, lGroupUtils, "Ressources/Icones/Fill.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Fill color" ));

        cToolBar0.addSeparator();


        cToolBar0.add( (cButtonColor1 = PPgWinUtils.MakeButton( cStrColor1, this )));
        cToolBar0.add( (cButtonReverseColor = PPgWinUtils.MakeButton( cStrReverseColor, this, cImgReverse )));
        cToolBar0.add( (cButtonColor2 = PPgWinUtils.MakeButton( cStrColor2, this )));

        cButtonColor1.setOpaque(true);
        cButtonColor2.setOpaque(true);

        cToolBar0.addSeparator();
        System.out.println( " MyInst:" + GetCurrentInstance() );
        System.out.println( " MyInstcCurrentGrafUtil:" + GetCurrentInstance().cCurrentGrafUtil );


        GetCurrentInstance().cCurrentGrafUtil.makeToolBar( cToolBar0 );
        drawColorButtons();
				
        cToolBar0.repaint();
    }

    //------------------------------------------------
    public void drawColorButtons(){
        //	Graphics lG = 	cButtonColor1.getGraphics();
        if( GetCurrentInstance() == null || cButtonColor1 == null)
            return ;


        cButtonColor1.setBackground( GetCurrentInstance().cOpProps.cColor1 );
        cButtonColor1.setForeground( GetCurrentInstance().cOpProps.cColor1 );

        cButtonColor2.setBackground( GetCurrentInstance().cOpProps.cColor2 );
        cButtonColor2.setForeground( GetCurrentInstance().cOpProps.cColor2 );
        //		lG.fillRect( 0, 0, 200, 200 );

        //		lG = 	cButtonColor2.getGraphics();
        //				cButtonColor2.setBackground( GetCurrentInstance().cOpProps.cColor2 );
        //				cButtonColor2.setForeground( GetCurrentInstance().cOpProps.cColor2 );
        //		lG.fillRect( 0, 0, 200, 200 );
    }
    //------------------------------------------------

    void createToolBar(){
        //
        makeToolBar0();
        //=====================================

        if( cToolBar1 == null )
            cToolBar1 = makeToolBar( 1 );

				

        cToolBar1.removeAll();

        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilSelectRect, this, false, lGroupUtils, "Ressources/Icones/Rectangle.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Select rectangle" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilSelectOval, this, false, lGroupUtils, "Ressources/Icones/Oval.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Select oval" ));

        //				cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilSelZone, this, false, lGroupUtils, "Ressources/Icones/Selection.png", PPgPref.sToolsSz, PPgPref.sToolsSz ));
        cToolBar1.add( ( cToggleButtonUtilSelect =PPgWinUtils.MakeToggleButton( cStrUtilSelect, this, false, lGroupUtils, "Ressources/Icones/pointer.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Selection"  )));




        //				cToolBar1.add( PPgWinUtils.MakeToggleButton( "", this, false, lGroupUtils, "Ressources/Icones/Wizard.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Magic wand" ));


        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilFillColor, this, false, lGroupUtils, "Ressources/Icones/Fill.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Fill color" ));


        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGrad,  this, false, lGroupUtils, "Ressources/Icones/GradLine.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient line" ));
        /*
          cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGradLine,  this, false, lGroupUtils, "Ressources/Icones/GradLine.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient line" ));

          cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGradRect,  this, false, lGroupUtils, "Ressources/Icones/GradRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient rectangle" ));

          cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGradRect2,  this, false, lGroupUtils, "Ressources/Icones/GradRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient rectangle 2" ));

          cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGradOval,  this, false, lGroupUtils, "Ressources/Icones/GradOval.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient ovale" ));

          cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilGradCircle,  this, false, lGroupUtils, "Ressources/Icones/GradOval.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Gradient ovale" ));
        */


        //				cToolBar1.add( PPgWinUtils.MakeToggleButton( "", this, false, lGroupUtils, "Ressources/Icones/Smooth.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilBrush, this, false, lGroupUtils, "Ressources/Icones/Brush.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Brush" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilEraser,    this, false, lGroupUtils, "Ressources/Icones/Eraser.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Eraser" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilPencil,    this, true, lGroupUtils, "Ressources/Icones/Pencil.png",  PPgPref.sToolsSz, PPgPref.sToolsSz, "Pencil" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilDropper, this, false, lGroupUtils, "Ressources/Icones/Dropper.png",  PPgPref.sToolsSz, PPgPref.sToolsSz, "Dropper" ));


        //				cToolBar1.add( PPgWinUtils.MakeToggleButton( "", this, false, lGroupUtils, "Ressources/Icones/Text tool.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "" ));
        //			cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtils, this, false, lGroupUtils, "Ressources/Icones/Figure.png", PPgPref.sToolsSz, PPgPref.sToolsSz ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilRectangle, this, false, lGroupUtils, "Ressources/Icones/Rectangle.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Rectangle" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilRoundRect, this, false, lGroupUtils, "Ressources/Icones/Rounded rectangle.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Round rectangle" ));
        //				cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtil3dRect, this, false, lGroupUtils, "Ressources/Icones/3dRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "3d rectangle" ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilOval, this, false, lGroupUtils, "Ressources/Icones/Oval.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Oval"  ));
        cToolBar1.add( PPgWinUtils.MakeToggleButton( cStrUtilLine, this, false, lGroupUtils, "Ressources/Icones/Line.png", PPgPref.sToolsSz, PPgPref.sToolsSz, "Line" ));


        //		cToolBar1.add( PPgWinUtils.MakeButton( "", this, "Ressources/Icones/.png" ));

        //=====================================
        /*
          if( cToolBar3 == null ){
          cToolBar3 = cOwner.makeToolBar(3 );
          cToolBar3.add(new JColorChooser( cView.getCurrentProps().getColor1()));
          }
        */

				
        //	cToolBar1.removeAll();
    }


    //------------------------------------------------
    //--------------- ActionListener -----------------
    //------------------------------------------------

    public void actionPerformed( ActionEvent pEv ){		
        System.out.println( "PPgFarmeEdImg.actionPerformed" );	
				
					
        //=====================================
        //=============== FILE ================
        //=====================================
								
        if( pEv.getActionCommand().equals( cStrNew )) {
            new DialogImg( "New image", null );
						
            //==============================
        }if( pEv.getActionCommand().equals( cStrOpenFile )) {

            //		FileNameExtensionFilter lFilterJpg = new FileNameExtensionFilter( "JPEG file", "jpg", "jpeg");
            //		FileNameExtensionFilter lFilterPng = new FileNameExtensionFilter( "PNG file", "png");
            //			FileNameExtensionFilter lFilterGif = new FileNameExtensionFilter( "Gif gif", "gif");
						
            //		lFileChooser.setFileFilter(lFilterJpg);
            //		lFileChooser.setFileFilter(lFilterPng);
            //		lFileChooser.setFileFilter(lFilterGif);
						
            if( PPgMain.sFileChooser == null ) {
                PPgMain.sFileChooser =  new JFileChooser(".");
                PPgMain.sFileChooser.addChoosableFileFilter( new PPgImageFilter());
                PPgMain.sFileChooser.setAcceptAllFileFilterUsed(false);
                PPgMain.sFileChooser.setFileView(new PPgImageFileView());
                PPgMain.sFileChooser.setAccessory(new PPgImagePreview(  PPgMain.sFileChooser));
            }
						
            int lReturnVal = PPgMain.sFileChooser.showOpenDialog(null);
						
            if( lReturnVal== JFileChooser.APPROVE_OPTION) {
                File lImgFile  = PPgMain.sFileChooser.getSelectedFile();
                ImageIcon lImg = new  ImageIcon( lImgFile.getPath() );
                PPgMain.sThePPgMain.addChild( new PPgFrameEdImg( PPgMain.sThePPgMain, null, lImg, lImgFile, 0, 0 ));		

            }
            return;						
            //==============================
        }else if( pEv.getActionCommand().equals( cStrUpd )) {
            SavConf.AutoSav();

            if( GetCurrentInstance().cFrame.getMyListener() != null && GetCurrentInstance().cCanvas.isChanged() ){
                ImageIcon lTmpImg = new ImageIcon(  GetCurrentInstance().cCanvas.getBufferImg() );
                GetCurrentInstance().cFrame.getMyListener().actionImgChanged( lTmpImg );
            }
        }else if( pEv.getActionCommand().equals( cStrSaveFile )) {
            //						if( cView.isChanged() ){
            saveFile(false);
						
            return;								
            //===============
        }else if( pEv.getActionCommand().equals( cStrSaveAsFile )) {
            //						if(  cView.isChanged() ){
            SavConf.AutoSav();

            saveFile(true);
            //===============
        }else 	if( pEv.getActionCommand().equals( cStrQuit )) {

            if( reallyQuit() == false )
                return;

            SavConf.AutoSav();

            if( GetCurrentInstance().cFrame.getMyListener() != null ){
                if(  GetCurrentInstance().cCanvas.isChanged() ) { 
                    ImageIcon lTmpImg= new ImageIcon(  GetCurrentInstance().cCanvas.getBufferImg() );  // A FAIRE une fonction qui renvoit toutes les layers en une seule image
                    GetCurrentInstance().cFrame.getMyListener().actionImgChanged( lTmpImg );
                }								
                GetCurrentInstance().cFrame.getMyListener().actionEditorClose();
                this.dispose();
            }
            dispose();
            //===============
        }else if( pEv.getActionCommand().equals( cStrAbort )) {

            if( reallyQuit() == false )
                return;

            SavConf.AutoSav();

            if( GetCurrentInstance().cFrame.getMyListener() != null )
                GetCurrentInstance().cFrame.getMyListener().actionEditorClose();
            dispose();
            //===============
        }else if( pEv.getActionCommand().equals( cStrSavConf )) {
            SavConf.AutoSav();
        } else
            //=====================================
            //=============== EDIT ================
            //=====================================
            if( pEv.getActionCommand().equals( cStrUndo )) {
                GetCurrentInstance().cOpControl.cmdHistoUndo();								
                //===============						
            }else if( pEv.getActionCommand().equals( cStrRedo )) {
                GetCurrentInstance().cOpControl.cmdHistoRedo();
                //===============						
            }else if( pEv.getActionCommand().equals( cStrCut )) {
								
                if( GetCurrentInstance().cSelectZone.isActive() ){ 

                    // On recupere la zone dans SelectPixels
                    GetCurrentInstance().cSelectZone.setSelectPixels(  GetCurrentInstance().cLayerGroup.getClipAreaImage(  GetCurrentInstance().cSelectZone.getActiveArea()));
                    // A FAIRE HISTO
                    //On efface la zone
                    GetCurrentInstance().cOpLayers.cmdClearAll();
                    GetCurrentInstance().cCanvas.actualize();
                }
                //===============						
            }else if( pEv.getActionCommand().equals( cStrCopy )) {												

                // On recupere la zone dans SelectPixels
                GetCurrentInstance().cSelectZone.setSelectPixels( GetCurrentInstance().cLayerGroup.getClipAreaImage(  GetCurrentInstance().cSelectZone.getActiveArea() ) );
                // A FAIRE HISTO
                GetCurrentInstance().cCanvas.actualize();
		
                //===============						
            }else if( pEv.getActionCommand().equals( cStrPaste )) {
								
                if( GetCurrentInstance().cSelectZone.isActive() &&  GetCurrentInstance().cSelectZone.getSelectPixels() != null ){ 
                    GetCurrentInstance().cOpLayers.cmdPasteSelection();										
                    GetCurrentInstance().cCanvas.actualize();
                }


                //===============						
            }else if( pEv.getActionCommand().equals( cStrPasteNL )) {
                if( GetCurrentInstance().cSelectZone.isActive() &&  GetCurrentInstance().cSelectZone.getSelectPixels() != null ){ 
                    GetCurrentInstance().cOpLayers.cmdAddNewLayer();
                    GetCurrentInstance().cOpLayers.cmdPasteSelection();		
                    GetCurrentInstance().cCanvas.actualize();
                }
	
                //===============						
            }else if( pEv.getActionCommand().equals( cStrPasteNI )) {
                if( GetCurrentInstance().cSelectZone.isActive() ){
										
                    BufferedImage lImg = GetCurrentInstance().cLayerGroup.getClipAreaImage(  GetCurrentInstance().cSelectZone.getActiveArea() );

                    PPgFrameEdImg lNewFrame = new PPgFrameEdImg( PPgMain.sThePPgMain, null, new ImageIcon( lImg ), 
                                                                 new File( "noname"), lImg.getWidth(), lImg.getHeight() );
										
                    PPgMain.sThePPgMain.addChild( lNewFrame );
                }
                //===============						
            }else if( pEv.getActionCommand().equals( cStrSelErase )) {
                if( GetCurrentInstance().cSelectZone.isActive() ){
                    GetCurrentInstance().cOpLayers.cmdClearAll();
                }
                //===============						
            }else if( pEv.getActionCommand().equals( cStrSelFill )) {
                if( GetCurrentInstance().cSelectZone.isActive() ){
                    GetCurrentInstance().cOpLayers.cmdFillAll( GetCurrentInstance().cOpProps.getColor(), GetCurrentInstance().cSelectZone.getActiveArea());
                }
                //===============						
            }else if( pEv.getActionCommand().equals( cStrSelInvert )) {
                //===============											
            }else if( pEv.getActionCommand().equals( cStrDeselect )) {
                GetCurrentInstance().cSelectZone.setActive( false );
                GetCurrentInstance().cFrame.actualize();
                //===============						
            }else if( pEv.getActionCommand().equals( cStrReselect )) {
                GetCurrentInstance().cSelectZone.recalMem();
                GetCurrentInstance().cFrame.actualize();
                //===============						
            }else
                //=====================================
                //=============== VIEW ================
                //=====================================
                if( pEv.getActionCommand().equals( cStrZoomP )) {
                    GetCurrentInstance().cCanvas.changeZoom( GetCurrentInstance().cCanvas.cZoom * 1.30 );
                    //===============						
                }else if( pEv.getActionCommand().equals( cStrZoomM )) {
                    GetCurrentInstance().cCanvas.changeZoom( GetCurrentInstance().cCanvas.cZoom * 0.70);
                    //===============						
                }else if( pEv.getActionCommand().equals( cStrZoom0 )) {
                    GetCurrentInstance().cCanvas.changeZoom(1.0 );
                    //===============						
                }else if( pEv.getActionCommand().equals( cStrResize)) {

                    new DialogImg( "Resize image", GetCurrentInstance() );
												
                    //===============
                }else
                    //=====================================
                    //=============== IMAGE ===============
                    //=====================================
                    if( pEv.getActionCommand().equals( cStrFlipHor)) {
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.FLIP_HORIZONTAL, 0 );								
                        GetCurrentInstance().cCanvas.actualize();
                        //===============
                    }else if( pEv.getActionCommand().equals( cStrFlipVer)) {
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.FLIP_HORIZONTAL, 0 );								
                        GetCurrentInstance().cCanvas.actualize();
                        //===============
                    }else if( pEv.getActionCommand().equals( cStrRot90C)) {
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.ROT_90, 0 );												
                        GetCurrentInstance().cCanvas.actualize();									
                        //===============
                    }else if( pEv.getActionCommand().equals( cStrRot90A)) {
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.ROT_270, 0 );
                        GetCurrentInstance().cCanvas.actualize();
                        //===============
                    }else if( pEv.getActionCommand().equals( cStrRot180)) {
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.ROT_180, 0 );
                        GetCurrentInstance().cCanvas.actualize();
                        //===============
                    }else if( pEv.getActionCommand().equals( cStrRot)){
                        GetCurrentInstance().cGrafRot.makeOp( OpGrafRot.RotCode.ROT, (float)(Math.PI/6.0) );
                        GetCurrentInstance().cCanvas.actualize();
                                                                        
                    }else if( pEv.getActionCommand().equals(cStrFilterForBW)){
                                                            
                        new DialogFilterBW( GetCurrentInstance() );                        
                        GetCurrentInstance().cCanvas.actualize();

                                                                        
                        //===============
                    }	else			
                        //=======================================
                        //=============== COLORS ================				
                        if( pEv.getActionCommand().equals( cStrGrayScaleImg )){
                            GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.GRAYSCALE_IMAGE );	
                            GetCurrentInstance().cCanvas.actualize();
                        }	else if( pEv.getActionCommand().equals( cStrInvertColorImg )){
                            GetCurrentInstance().cGrafCorrect.setParam( 1, 1, 1, 0 );
                            GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.INVERT_COLOR_IMAGE );	
                            GetCurrentInstance().cCanvas.actualize();

                        }else if( pEv.getActionCommand().equals( cStrPosterizeImg)){
							
                            new DialogPosterize( GetCurrentInstance() );							
                            //							GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.POSTERIZE_IMAGE, 0 );	
                            GetCurrentInstance().cCanvas.actualize();
                        }else if( pEv.getActionCommand().equals( cStrRescaleOpImg)){
							
                            new DialogRescaleOp( GetCurrentInstance() );

                            //			METTRE UN DIALOGUE
                            //							GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.POSTERIZE_IMAGE, 0 );	
                            GetCurrentInstance().cCanvas.actualize();
                        }

        //=======================================
        //=============== LAYERS ================				
        //=======================================
                        else  if( pEv.getActionCommand().equals( cStrClearCurrentLayer )) {
                            GetCurrentInstance().cOpLayers.cmdClearCurrentLayer(null);

                            GetCurrentInstance().cFrame.actualize();

                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrClearAll )) {
                            GetCurrentInstance().cOpLayers.cmdClearAll();

                            GetCurrentInstance().cFrame.actualize();

                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrAddLayer )) {

                            GetCurrentInstance().cOpLayers.cmdAddNewLayer();
                            DialogLayers.sTheDialog.changeData( GetCurrentInstance() );
                            GetCurrentInstance().cFrame.actualize();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( PPgMain.cStrDelLayer )) {
                            if( GetCurrentInstance().cOpLayers.cmdDeleteCurrentLayer() ) {
                                DialogLayers.sTheDialog.changeData( GetCurrentInstance() );
                                GetCurrentInstance().cFrame.actualize();
                            }

                            //===============			
                        }	else if( pEv.getActionCommand().equals( PPgMain.cStrDupLayer )) {
                            if( GetCurrentInstance().cOpLayers.cmdDupCurrentLayer() ){
                                DialogLayers.sTheDialog.changeData( GetCurrentInstance() );
                                GetCurrentInstance().cFrame.actualize();
                            }
																				 
                            //===============			
                        }	else if( pEv.getActionCommand().equals(  PPgMain.cStrMergeLayerDown)) {

                            if( GetCurrentInstance().cOpLayers.cmdMergeDownCurrentLayer() ) {
                                DialogLayers.sTheDialog.changeData( GetCurrentInstance() );
                                GetCurrentInstance().cFrame.actualize();
                            }
                            //===============			
                        } 	else if( pEv.getActionCommand().equals( cStrGrayScaleCurrentLayer )){
                            GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.GRAYSCALE_LAYER );	
                            GetCurrentInstance().cCanvas.actualize();
                        } 	else if( pEv.getActionCommand().equals( cStrInvertColorCurrentLayer )){
                            GetCurrentInstance().cGrafCorrect.setParam( 1, 1, 1, 0 );
                            GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.INVERT_COLOR_LAYER );	
                            GetCurrentInstance().cCanvas.actualize();
                        }	else if( pEv.getActionCommand().equals( cStrPosterizeCurrentLayer )){
                            //				METTRE UN DIALOGUE
                            //		GetCurrentInstance().cGrafCorrect.makeOp( OpGrafCorrect.CorrectCode.POSTERIZE_LAYER );	
                            //	GetCurrentInstance().cCanvas.actualize();
                        }

        //else if( pEv.getActionCommand().equals( )) {
        //===============			
	
        //=======================================
        //=============== SELECT  ================				
        //=======================================
                        else if( pEv.getActionCommand().equals( cStrUtilSelect )) {
						
                            GetCurrentInstance().setUtilSelect();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilSelZone )) {
		
                            JPopupMenu lPopmenu = new JPopupMenu();

                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilSelectRect, this, "Ressources/Icones/Rectangle.png",  PPgPref.sToolsSz, PPgPref.sToolsSz);
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilSelectOval, this, "Ressources/Icones/Oval.png", PPgPref.sToolsSz, PPgPref.sToolsSz) ;
                            lPopmenu.add( new JSeparator());
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrSelAll,  this, "Ressources/Icones/Selection.png", PPgPref.sToolsSz, PPgPref.sToolsSz) ;
						
                            JToggleButton lSrcButton = (JToggleButton) pEv.getSource();
                            lPopmenu.show( lSrcButton, 0, 0);
                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrSelAll)) {
						
                            GetCurrentInstance().cSelectZone.addShape( new Area( new Rectangle2D.Float( 0, 0,  GetCurrentInstance().cLayerGroup.getWidth(), GetCurrentInstance().cLayerGroup.getHeight() )));
                            GetCurrentInstance().cSelectZone.setActive( true );
                            GetCurrentInstance().cCanvas.actualize();
                            menuActualize();


                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrUtilSelectRect )) {

                            GetCurrentInstance().setUtilSelZone( OpGrafSelZone.Type.RECTANGLE );
                            makeToolBar0();

                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrUtilSelectOval )) {

                            GetCurrentInstance().setUtilSelZone( OpGrafSelZone.Type.OVAL );
                            makeToolBar0();

                            //===============			
                        }
        //=======================================
        //=============== UTILS  ================				
        //=======================================
                        else if( pEv.getActionCommand().equals( cStrUtils )) {

                            JPopupMenu lPopmenu = new JPopupMenu();

                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilRectangle, this, "Ressources/Icones/Rectangle.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilRoundRect, this, "Ressources/Icones/Rounded rectangle.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtil3dRect, this, "Ressources/Icones/3dRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilOval, this, "Ressources/Icones/Oval.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilLine, this, "Ressources/Icones/Line.png", PPgPref.sToolsSz, PPgPref.sToolsSz );

                            JToggleButton lSrcButton = (JToggleButton) pEv.getSource();
                            lPopmenu.show( lSrcButton, 0, 0 );

                        } else if( pEv.getActionCommand().equals( cStrUtilGrad)) {

                            JPopupMenu lPopmenu = new JPopupMenu();

                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilGradLine, this, "Ressources/Icones/GradLine.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilGradRect, this, "Ressources/Icones/GradRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilGradRect2, this, "Ressources/Icones/GradRect.png", PPgPref.sToolsSz, PPgPref.sToolsSz );
                            PPgWinUtils.AddMenuItem( lPopmenu, cStrUtilGradOval, this, "Ressources/Icones/GradOval.png", PPgPref.sToolsSz, PPgPref.sToolsSz );

                            JToggleButton lSrcButton = (JToggleButton) pEv.getSource();
                            lPopmenu.show( lSrcButton, 0, 0 );

                        }  else if( pEv.getActionCommand().equals( cStrUtilFillColor )) {

                            GetCurrentInstance().setUtilFillColor();
                            makeToolBar0();

                            //===============			
				
                        } else if( pEv.getActionCommand().equals( cStrUtilBrush )) {

                            GetCurrentInstance().setUtilBrush();
                            makeToolBar0();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilPencil )) {

                            GetCurrentInstance().setUtilPencil();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilEraser )) {

                            GetCurrentInstance().setUtilEraser();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilDropper )) {

                            GetCurrentInstance().setUtilDropper();

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilGradLine )) {

                            GetCurrentInstance().setUtilGraduation( OpGrafGraduation.Type.LINE);

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilGradRect )) {

                            GetCurrentInstance().setUtilGraduation( OpGrafGraduation.Type.RECTANGLE);

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilGradRect2 )) {

                            GetCurrentInstance().setUtilGraduation( OpGrafGraduation.Type.ROUND_RECT);

                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilGradOval )) {

                            GetCurrentInstance().setUtilGraduation( OpGrafGraduation.Type.OVAL);

                            //===============			
                            //			}	else if( pEv.getActionCommand().equals( cStrUtilGradCircle )) {
                            //						GetCurrentInstance().setUtilGraduation( OpGrafGraduation.Type.CIRCLE);

                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrUtilRectangle )) {

                            GetCurrentInstance().setUtilFigure( OpGrafFigure.Type.RECTANGLE);
						
                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilRoundRect )) {

                            GetCurrentInstance().setUtilFigure( OpGrafFigure.Type.ROUND_RECT);
						
                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtil3dRect)) {

                            System.out.println( "========== OpGrafFigure.Type.RECTANGLE_3D ========");
                            GetCurrentInstance().setUtilFigure( OpGrafFigure.Type.RECTANGLE_3D);
						
                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilOval )) {
						
                            GetCurrentInstance().setUtilFigure( OpGrafFigure.Type.OVAL);
						
                            //===============			
                        }	else if( pEv.getActionCommand().equals( cStrUtilLine )) {
						
                            GetCurrentInstance().setUtilFigure( OpGrafFigure.Type.LINE);
                            //===============			
                            //===============			
                            //===============			

                        }	else if( pEv.getActionCommand().equals( cStrColor1 )) {
                            DialogColor.sFlagView = true;
                            DialogColor.SetColor1();
                            DialogColor.sTheDialog.setVisible( DialogColor.sFlagView );
                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrColor2 )) {
                            DialogColor.sFlagView = true;
                            DialogColor.SetColor2();
                            DialogColor.sTheDialog.setVisible( DialogColor.sFlagView );
                            //===============			
                        } else if( pEv.getActionCommand().equals( cStrReverseColor)) {
                            Color lCol1 = GetCurrentInstance().cOpProps.getColor();
                            Color lCol2 = GetCurrentInstance().cOpProps.getColor2();

                            GetCurrentInstance().cOpProps.setColor( lCol2 );
                            GetCurrentInstance().cOpProps.setColor2( lCol1 );

                            drawColorButtons();
                            //===============			
                        }
						
						
        //===============			
						
        //===============			
        //				}	else if( pEv.getActionCommand().equals( cStrUtilPolyline )) {
						
        //						cCanvas.setUtilFigure( OpGrafFigure.Type.POLYLINE);
						
        //===============			
							
    }

    //------------------------------------------
    public void itemStateChanged(ItemEvent pEv ){
        Object lSource = pEv.getItemSelectable();


        //=====================================
        //=============== VIEW ================
        //=====================================
        if( lSource == cCheckGrid ){
            if( pEv.getStateChange() == ItemEvent.DESELECTED )
                GetCurrentInstance().cCanvas.cFlagGrid = false;
            else
                GetCurrentInstance().cCanvas.cFlagGrid = true;

            GetCurrentInstance().cCanvas.actualize();
            //============
        }
        //=======================================
        //============== WINDOWS ================
        //=======================================
        else 	if( lSource == cCheckViewLayers ){
						
            if( pEv.getStateChange() == ItemEvent.DESELECTED ) 
                DialogLayers.sFlagView = false;
            else	
                DialogLayers.sFlagView = true;

            DialogLayers.sTheDialog.setVisible( DialogLayers.sFlagView );
            //============
        } else 	if( lSource == cCheckViewHisto ){
						
            if( pEv.getStateChange() == ItemEvent.DESELECTED ) 
                DialogHisto.sFlagView = false;
            else	
                DialogHisto.sFlagView = true;

            DialogHisto.sTheDialog.setVisible( DialogHisto.sFlagView );
            //============
        } else 	if( lSource == cCheckViewColor ){
						
            if( pEv.getStateChange() == ItemEvent.DESELECTED ) 
                DialogColor.sFlagView = false;
            else	
                DialogColor.sFlagView = true;

            DialogColor.sTheDialog.setVisible( DialogColor.sFlagView );
            //============
        }
				
    }

    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    boolean saveFile( boolean pSaveAs ){

        File lImgFile = GetCurrentInstance().cFrame.cFile;
        if( pSaveAs || lImgFile == null ){
						
            if( PPgMain.sFileChooser == null ) {
                PPgMain.sFileChooser =  new JFileChooser(".");
                PPgMain.sFileChooser.addChoosableFileFilter( new PPgImageFilter() );
                PPgMain.sFileChooser.setAcceptAllFileFilterUsed(false);
                PPgMain.sFileChooser.setFileView( new PPgImageFileView());
                PPgMain.sFileChooser.setAccessory(new PPgImagePreview(  PPgMain.sFileChooser));
            }
            int lReturnVal = PPgMain.sFileChooser.showOpenDialog(null);
						
            if( lReturnVal== JFileChooser.APPROVE_OPTION) {
                lImgFile  = PPgMain.sFileChooser.getSelectedFile();
            }
        }				
        try {
            // retrieve image

            BufferedImage lSavImg = ImgUtils.GetSameBufferImage( GetCurrentInstance().cCanvas.getBufferImg() );
						
            Graphics2D lG = (Graphics2D) lSavImg.getGraphics();
            GetCurrentInstance().cLayerGroup.draw( lG );
            lG.dispose();

            String lExtFile  = lImgFile.getName().substring( lImgFile.getName().lastIndexOf('.')+1);
            System.out.println( "Extension:" + lExtFile );
						
            ImageIO.write( lSavImg, lExtFile, lImgFile );
        } catch (IOException e) {	

            JOptionPane.showMessageDialog( this,
                                           "Save failed",
                                           "Save error : " + e.toString(),
                                           JOptionPane.ERROR_MESSAGE);
            return false;
        }
        GetCurrentInstance().cFrame.cFile = lImgFile;

        GetCurrentInstance().cCanvas.setChanged( false );
        return true;
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
		
    static public void MakeImageInstance( InterfaceEdImgListener pListener, String pName, ImageIcon pImg, File pFile ){
				
        boolean lNewInst = false;

        System.out.println( "****************** MakeImageInstance" );

        if( sThePPgMain == null ) {

            lNewInst = true ;
            new PPgMain();

            OpControler.Init();



            System.out.println( "   MakeImageInstance 2" );

        }


        JInternalFrame lFrames[] = sThePPgMain.cDesktop.getAllFrames();

        System.out.println( "   MakeImageInstance 3" );

        for( JInternalFrame lFrame : lFrames ) {
            if( ((PPgFrameEdImg)lFrame).getOriginalImage() == pImg ){
                System.out.println( "   MakeImageInstance 4 found"  );
                ((PPgFrameEdImg)lFrame).front();
                return ;
            }						
        }
				
        System.out.println( "   MakeImageInstance 5 not found"  );
        PPgFrameEdImg lFrame = new PPgFrameEdImg( sThePPgMain, pListener,  pImg,  pFile, 800, 600 );
        sThePPgMain.addChild( lFrame );		
        ((PPgFrameEdImg)lFrame).front();


        sThePPgMain.setVisible(true);

        if( lNewInst ){
            sThePPgMain.createToolBar();
            sThePPgMain.createDialog();
            sThePPgMain.createMenu();						
            SavConf.Load();		
        }
        else {
            int lExtendState = sThePPgMain.getExtendedState();
            if( (lExtendState & Frame.ICONIFIED)  != 0 ) {		
                lExtendState = lExtendState ^ Frame.ICONIFIED;
                sThePPgMain.setExtendedState( lExtendState );
            }
        }

        sThePPgMain.toFront();
    }
    //------------------------------------------------
    boolean reallyQuit(){
        for( PPgFrameChild lChild : getFrameChilds() ){
            if( ((PPgFrameEdImg)lChild).cMyInst.cCanvas.isChanged() ){

                Object[] options = {"Yes quit application",
                    "No "};
                int lRep = JOptionPane.showOptionDialog( null,
                                                         "Modified documents(s) not saved\n do you really want to quit ?",
                                                         "Quit without saving",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE,
                                                         null,
                                                         options,   options[1]);
                if( lRep == 1 )
                    return false;
                break;
            }
        }
        return true;
    }
    //------------------------------------------------
    //------------------------------------------------
    //------------------------------------------------
    static public void main(String[] args) {
			 		
        /*		try{
                        Thread.currentThread().sleep( 2000 );
                        }catch( InterruptedException ex){;}
        */


        //=================== Nimbus ===================
				
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        //=================== Nimbus ===================


        PPgMain lProg = new PPgMain();
				
        lProg.setVisible(true);
				
        ImageIcon lImg = null;

        int lNbEdit = 0;

        OpControler.Init();

        while( true ){
            String lFileImg = PPgParam.GetString( args, "-F", null );
						
						
						
            System.out.println( "File:" + lFileImg );
						
            if( lFileImg != null ){
                File lFile = new File( lFileImg );
								
                lImg = new ImageIcon( lFile.getPath() );
								
								
                lProg.addChild( new PPgFrameEdImg( lProg, null, lImg, lFile, 800, 600 ));		
                lNbEdit++;
            }								
            else
                break;
        }


        String lNewImg = PPgParam.GetString( args, "-N", null );
        if( lNewImg != null ){
            System.out.println( "-N<" + lNewImg +'>' );
            Point lSz =  new Point();
            PPgWinUtils.ReadPoint( lSz, lNewImg, 0 );
            if( lSz.x >0 && lSz.y >0 && lSz.x <= 32000 && lSz.y <= 32000 ){
                lProg.addChild( new PPgFrameEdImg( lProg, null, null, new File( "noname"), lSz.x, lSz.y ));		
                lNbEdit++;
            }
        }
				
        if( lNbEdit == 0 )
            lProg.addChild( new PPgFrameEdImg( lProg, null, null, new File( "noname"), 800, 600));		


        lProg.createToolBar();
        lProg.createDialog();
        lProg.createMenu();

        SavConf.Load();

        if( PPgParam.GetBoolean( args, "-c", false ) ){
            DialogColor.sFlagView = true;	
            DialogColor.sTheDialog.setVisible(true);
						
        }
        if( PPgParam.GetBoolean( args, "-l", false ) ){
            DialogLayers.sFlagView = true;		
            DialogLayers.sTheDialog.setVisible(true);
        }
        if( PPgParam.GetBoolean( args, "-h", false ) ){
            DialogHisto.sFlagView = true;						
            DialogHisto.sTheDialog.setVisible(true);
        }
						
    }	

    //------------------------------------------------
    public void menuActualize(){
        majMenu();
    }
    //------------------------------------------------
    public void dialogActualize(){
    }
    //------------------------------------------------
    public void toolbarActualize(){
    }
		
}
//*************************************************
