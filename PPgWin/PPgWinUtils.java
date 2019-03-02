package org.phypo.PPg.PPgWin;

import static java.awt.GraphicsDevice.WindowTranslucency.*;



import java.awt.*;
import java.io.File;

import java.awt.event.*;
import javax.swing.*;

import java.text.DateFormat;
import java.net.*;
import java.awt.image.*;
import java.awt.geom.Point2D;


import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgImg.*;

//***********************************
public class PPgWinUtils {

		static public boolean sFlagCtrl  = false;
		static public boolean sFlagShift = false;
		static public boolean sFlagAlt   = false;

		static public Point sMemPoint  = new Point();
		static public Point2D.Double sMemPointD = new Point2D.Double();
		static public Point sMemPoint2 = new Point();
		static public int sMemLastButton  = 0;
		static public int sMemLastButton2  = 0;
	//-------------------------------------
		public static boolean IsTranslucentSupported() {

				GraphicsEnvironment ge = 	GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);
				
				
        //If shaped windows aren't supported, exit.
        if (! gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
						return false;
        }

				return isTranslucencySupported;
		}
	//-------------------------------------
		public static 	JMenuItem  AddMenuItem( Container pMenu, String pStr, ActionListener pListen ){

				JMenuItem lItem = new JMenuItem( pStr);
				
				lItem.addActionListener( pListen );	 
				
				pMenu.add( lItem);
				return lItem;
		}
	//-------------------------------------
		public static 	JMenuItem  AddMenuItem( Container pMenu, String pStr, ActionListener pListen, int  pShortcut ){

				JMenuItem lItem = new JMenuItem( pStr, pShortcut );
				
				lItem.addActionListener( pListen );	 
				
				pMenu.add( lItem);
				return lItem;
		}
	//-------------------------------------
		public static 	JMenuItem  AddMenuItem( Container pMenu, String pStr, ActionListener pListen,
																						String pImageName, int pWidth, int pHeight ){

				ImageIcon lImg = null;

				if( pWidth != 0 && pHeight !=0 )
						lImg = ImgUtils.LoadImageFromFile( new File(pImageName), pWidth, pHeight, false, 1.0 );
				else
						lImg = new ImageIcon( pImageName );

				JMenuItem lItem = new JMenuItem( pStr, lImg);
				
				lItem.addActionListener( pListen );	 
				
				pMenu.add( lItem);
				return lItem;
		}
	//-------------------------------------
		public static JCheckBoxMenuItem AddJMenuCheckBoxMenuItem( JMenu pMenu, String pStr, ItemListener pListen, boolean pVal ){

				JCheckBoxMenuItem lItem =  new JCheckBoxMenuItem( pStr );
				lItem.setSelected( pVal );
				lItem.addItemListener( pListen );

				pMenu.add( lItem );
				return lItem;
		}
	//-------------------------------------
		public static JCheckBoxMenuItem AddJMenuCheckBoxMenuItem( JMenu pMenu, String pStr, ItemListener pListen, boolean pVal,
																															String pImageName, int pWidth, int pHeight ){

					ImageIcon lImg = ImgUtils.LoadImageFromFile( new File(pImageName), pWidth, pHeight, false, 1.0 );

					JCheckBoxMenuItem lItem =  new JCheckBoxMenuItem( pStr, lImg );
				lItem.setSelected( pVal );
				lItem.addItemListener( pListen );

				pMenu.add( lItem );
				return lItem;
		}
		//------------------------------------------------------------
		//------------------------------------------------------------
		//------------------------------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction) {
				
				//Create and initialize the button.
				JButton lButton = new JButton();
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				lButton.setText(pStr);
						
				return lButton;
		}
		//------------------------------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction, Icon pIcon) {
				
				//Create and initialize the button.
				JButton lButton = new JButton();
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				lButton.setIcon( pIcon );
				return lButton;
		}
		//------------------------------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction, String pImageName, String pHelp) {
				
				//Create and initialize the button.
				JButton lButton = new JButton();
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				ImageIcon lImg = new ImageIcon( pImageName);	

				if (lImg != null) {                      //image found
						lButton.setIcon( lImg );
				} else {                                     //no image found
						lButton.setText(pStr);
						//						System.err.println("Resource not found: " + pImageName );
				}
				if( pHelp != null )
						lButton.setToolTipText( pHelp );
		
				return lButton;
		}
		//------------------------------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction, String pImageName) {
				
				
				return MakeButton( pStr, pAction, pImageName, null) ;
		}
		//-------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction, String pImageName, int pWidth, int pHeight, String pHelp ) {
				
				//Create and initialize the button.
				JButton lButton = new JButton();
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				ImageIcon lImg = ImgUtils.LoadImageFromFile( new File(pImageName), pWidth, pHeight, false, 1.0 );


				if (lImg != null) {                      //image found
						lButton.setIcon( lImg );
				} else {                                     //no image found
						lButton.setText(pStr);
						//						System.err.println("Resource not found: " + pImageName );
				}
				if( pHelp != null )
						lButton.setToolTipText( pHelp );
				
	
				return lButton;
		}
		//-------------------------------------
		public static JButton MakeButton( String pStr, ActionListener pAction, String pImageName, int pWidth, int pHeight) {

				return MakeButton( pStr, pAction, pImageName, pWidth, pHeight, null) ;
		}
		//-------------------------------------
		public static JCheckBox MakeCheckBox( String pStr, ItemListener pListen, boolean pVal, String pImageName, int pWidth, int pHeight) {
				
				//Create and initialize the button.
				JCheckBox lButton = new JCheckBox(pStr);
				lButton.setSelected( pVal );
				lButton.addItemListener( pListen );

				ImageIcon lImg = ImgUtils.LoadImageFromFile( new File(pImageName), pWidth, pHeight, false, 1.0 );


				if (lImg != null) {                      //image found
						lButton.setIcon( lImg );
				} else {                                     //no image found
						lButton.setText(pStr);
						System.err.println("Resource not found: " + pImageName );
				}
				
				return lButton;
		}
		//----------------------------------------------------------
		public static JRadioButton MakeRadioButton( String pStr, ActionListener pAction, boolean pSelect, ButtonGroup pGroup) {
				
				//Create and initialize the button.
				JRadioButton lButton ;

				lButton = new JRadioButton( pStr, pSelect );
	
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				if( pGroup != null )
						pGroup.add( lButton );
				
				return lButton;
		}
		//----------------------------------------------------------
		public static JToggleButton MakeToggleButton( String pStr, ActionListener pAction, boolean pSelect, ButtonGroup pGroup, String pImageName, int pWidth, int pHeight, String pHelp) {
				
				ImageIcon lImg = null;
				if( pImageName != null )
						lImg = ImgUtils.LoadImageFromFile( new File(pImageName), pWidth, pHeight, false, 1.0 );

				//Create and initialize the button.
				JToggleButton lButton ;

				if( lImg == null )
						lButton = new JToggleButton( pStr, pSelect );
				else 
						lButton = new JToggleButton( lImg, pSelect );
	
				lButton.setActionCommand(pStr);
				lButton.addActionListener(pAction);

				if( pGroup != null )
						pGroup.add( lButton );

				if( pHelp != null )
						lButton.setToolTipText( pHelp );
				
				return lButton;
		}
		//------------------------------------
		public static JToggleButton MakeToggleButton( String pStr, ActionListener pAction, boolean pSelect, ButtonGroup pGroup, String pHelp ) {
				return MakeToggleButton( pStr, pAction, pSelect, pGroup, null, 0, 0, pHelp );
		}
		//-------------------------------------
		//-------------------------------------
		//-------------------------------------

		static public void ReadModifier( InputEvent pEv ) {

				int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
				int lShift = InputEvent.SHIFT_DOWN_MASK;
				int lAlt   = InputEvent.ALT_DOWN_MASK;

				sFlagCtrl  = false;
				sFlagShift = false;
				sFlagAlt   = false;

				if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
						sFlagCtrl = true;
						//						System.out.println( "CTRL" );
				}
				if( (pEv.getModifiersEx() & lShift) == lShift ){
						sFlagShift = true;
						//						System.out.println( "SHIFT" );
				}
				if( (pEv.getModifiersEx() & lAlt) == lAlt ){
						sFlagAlt = true;
						//						System.out.println( "ALT" );
				}						
		}

		//------------------------------------------------
		static public void SetMemMousePos( int pX, int pY ){

				sMemPoint2.setLocation( sMemPoint.x, sMemPoint.y );
				sMemPoint.setLocation( pX, pY );		
				sMemPointD.setLocation( pX, pY );
		}
		//------------------------------------------------
		static public void SetMemMousePos( MouseEvent pEv ){

				SetMemMousePos( pEv.getX(), pEv.getY() );
		}
		//------------------------------------------------
		static public void SetMemMousePos( MouseEvent pEv, double pZoom ){
			 
				double lX = ((double)pEv.getX())/pZoom;
				double lY = ((double)pEv.getY())/pZoom;

				SetMemMousePos( (int)lX, (int)lY );

				sMemPointD.setLocation( lX, lY );
		}
		//------------------------------------------------

		static public void SetMemMouseButton( MouseEvent pEv ){


				if( SwingUtilities.isLeftMouseButton( pEv ) ){
						sMemLastButton2= sMemLastButton;
						sMemLastButton = 1;
				}
				else if( SwingUtilities.isMiddleMouseButton( pEv ) ){
						sMemLastButton2= sMemLastButton;
						sMemLastButton = 2;
				}
				else if( SwingUtilities.isRightMouseButton( pEv ) ) {
						sMemLastButton2= sMemLastButton;
						sMemLastButton = 3;
				}
		}
		//------------------------------------------------
		static public void SetMemMouse( MouseEvent pEv ){
				ReadModifier( pEv );
				SetMemMousePos( pEv  );
				SetMemMouseButton( pEv );
		}
		//------------------------------------------------
		static public void SetMemMouse( MouseEvent pEv,  double pZoom ){
				ReadModifier( pEv );
				SetMemMousePos( pEv,  pZoom );
				SetMemMouseButton( pEv );
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		// A VERIFIER  pouquoi utiliser pRefBool ? je ne pense que l'on envoit une copie de l'objet ?

		public static int ReadBoolean( PPgRef<Boolean> pRefBool, String pData, int pPos, char cSep ){
				int lNewPos = pData.indexOf( cSep, pPos );
				
				if( lNewPos == -1 )
						pRefBool.set( Boolean.parseBoolean(  pData.substring( pPos )));
				else
						pRefBool.set( Boolean.parseBoolean(  pData.substring( pPos, lNewPos )));

				return ++lNewPos;
		}
		//------------------------------------------------			
		public static int ReadInteger( PPgRef<Integer> pRefInt, String pData, int pPos, char cSep ){
				
				int lNewPos = pData.indexOf( cSep, pPos );

				if( lNewPos == -1 )
						pRefInt.set( Integer.decode(  pData.substring( pPos )));
				else
						pRefInt.set( Integer.decode(  pData.substring( pPos, lNewPos )));

				return ++lNewPos;
		}
		//------------------------------------------------
		public static int ReadFloat( PPgRef<Float> pRefFloat, String pData, int pPos, char cSep ){
				
				int lNewPos = pData.indexOf( cSep, pPos );

				if( lNewPos == -1 )
						pRefFloat.set( Float.valueOf(  pData.substring( pPos  )));
				else																																		
						pRefFloat.set( Float.valueOf(  pData.substring( pPos, lNewPos )));
				return ++lNewPos;
		}
		//------------------------------------------------
		public static int ReadDouble( PPgRef<Double> pRefDouble, String pData, int pPos, char cSep ){
				
				int lNewPos = pData.indexOf( cSep, pPos );

				if( lNewPos == -1 )
						pRefDouble.set( Double.valueOf(  pData.substring( pPos  )));
				else																																		
						pRefDouble.set( Double.valueOf(  pData.substring( pPos, lNewPos )));
				return ++lNewPos;
		}

		//------------------------------------------------
		public static void WritePoint( Point pPoint, StringBuilder pData ){
				pData.append( ""+ pPoint.x + ',' + pPoint.y + ' ' );
		}
		//------------------------------------------------
		public static int ReadPoint( Point pPoint, String pData, int pPos ){
				/*
				int lNewPos = pData.indexOf(',', pPos );
				int lX = Integer.parseInt( pData.substring( pPos, lNewPos ));
				lNewPos++;
				pPos = pData.indexOf(' ', lNewPos );
				int lY = Integer.parseInt( pData.substring(lNewPos, pPos ));
				pPoint.setLocation( lX, lY );
				return ++pPos;
				*/
				PPgRef<Integer> lRefA = new PPgRef<Integer>();
				pPos = ReadInteger( lRefA, pData, pPos, ',' );

				PPgRef<Integer> lRefB = new PPgRef<Integer>();
				pPos = ReadInteger( lRefB, pData, pPos, ' ' );

				pPoint.setLocation( lRefA.get(), lRefB.get() );
				return pPos;
		}
		//------------------------------------------------
		public static void WriteColor( Color pColor, StringBuilder pData ){

				float lCol[]= new float[4];
				pColor.getComponents( lCol );
				pData.append( "" +lCol[0] +','+lCol[1] +','+lCol[2] +','+lCol[3] + ' ' );
		}
		//------------------------------------------------

		public static int ReadColorRGBA( PPgRef<Color> pRefColor, String pData, int pPos  ){

				
				PPgRef<Float> lRefR = new PPgRef<Float>();
				pPos = ReadFloat( lRefR, pData, pPos, ',' );
				PPgRef<Float> lRefG = new PPgRef<Float>();
				pPos = ReadFloat( lRefG, pData, pPos, ',' );
				PPgRef<Float> lRefB = new PPgRef<Float>();
				pPos = ReadFloat( lRefB, pData, pPos, ',' );
				PPgRef<Float> lRefA = new PPgRef<Float>();
				pPos = ReadFloat( lRefA, pData, pPos, ' ' );
				
				pRefColor.set( new Color( lRefR.get(), lRefG.get(), lRefB.get(), lRefA.get() ));

				return pPos;		
		}
		
	
		// ------------------------------------------------
		public static Color GetColor( String pStr ){
				
				if( pStr.compareToIgnoreCase("black")==0)
						return Color.black ;
				else 		if( pStr.compareToIgnoreCase("blue")==0)
						return Color.blue ;
				else 		if( pStr.compareToIgnoreCase("cyan")==0)
						return Color.cyan ;
				else 		if( pStr.compareToIgnoreCase("darkgray")==0)
						return Color.darkGray ;
				else 		if( pStr.compareToIgnoreCase("gray")==0)
						return Color.gray ;
				else 		if( pStr.compareToIgnoreCase("green")==0)
						return Color.green ;
				else 		if( pStr.compareToIgnoreCase("lightgray")==0)
						return Color.lightGray ;
				else 		if( pStr.compareToIgnoreCase("magenta")==0)
						return Color.magenta ;
				else 		if( pStr.compareToIgnoreCase("orange")==0)
						return Color.orange ;
				else 		if( pStr.compareToIgnoreCase("pink")==0)
						return Color.pink ;
				else 		if( pStr.compareToIgnoreCase("red")==0)
						return Color.red ;
				else 		if( pStr.compareToIgnoreCase("white")==0)
						return Color.white ;
				else 		if( pStr.compareToIgnoreCase("yellow")==0)
						return Color.yellow ;
				
				return Color.darkGray;
		}
		//------------------------------------------------
		public static String GetFontStyle( Font lFont ) {
				/*				System.out.println( "GetFontStyle " +  lFont.getStyle() 
														+ " bold:" +  Font.BOLD
														+ " italic:" + Font.ITALIC
														+ " bold italic:" + Font.BOLD+Font.ITALIC
														+ " plain:" +  Font.PLAIN);
				*/
				switch( lFont.getStyle() ) {
				case Font.BOLD:
						return "BOLD";
				case Font.ITALIC:
						return "ITALIC";
				case Font.BOLD+Font.ITALIC:
						return "BOLDITALIC";
				case Font.PLAIN:
				default:
						return "PLAIN";
				}
		}
		//------------------------------------------------
		// Compatible with Font.decode( String )

		public static String CodeFont( Font lFont ) {
				//			return lFont.getFamily() + '-' + GetFontStyle( lFont ) + '-' + lFont.getSize();
				//		return lFont.getName() + '-' + GetFontStyle( lFont ) + '-' + lFont.getSize();
					return lFont.getName() ;
	}

}
//***********************************
