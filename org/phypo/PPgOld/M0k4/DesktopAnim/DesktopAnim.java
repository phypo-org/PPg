package com.phipo.PPg.M0k4.DesktopAnim;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import java.text.*;
import java.io.File;
import java.util.*;

import java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.Ellipse2D; 
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.awt.font.FontRenderContext;


import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


import java.io.IOException;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;

import com.phipo.PPg.PPgImg.*;

import com.phipo.PPg.PPgUtils.*;
import com.phipo.PPg.PPgSound.*;
import com.phipo.PPg.PPgWin.*;
import com.phipo.PPg.M0k4.*;

//*************************************************

public class DesktopAnim extends M0k4_Gadget 
		implements ActionListener, MouseListener, MouseMotionListener, Runnable{

		public JLabel sText;
		final public javax.swing.Timer  sTimer = new javax.swing.Timer( 40, this);

		String cFilePref = "DesktopAlarm.ini";  


		public  String sStrTxt ="";

		
		public Color sColor = Color.red;
		
		public boolean cAlwaysOnTop = false;
		public boolean cTranslucent = false;
				
		public float   cOpacity  = 0.65f;
		public float   cFontSize = 40f;
		
		public Point     cLocation = new Point( 50, 40 );
		public Dimension cDimension  = new Dimension( 250, 50 );
		public Dimension cScreenSize;

		
		// Alarm & Carillon
		public boolean sUseSecond = false;
		public char             sIndicAlarm = '°';
		public HashSet<String> sAlarm = new HashSet<>();
		
		public boolean  sFlagCarillonHeure = false;
		public boolean  sFlagCarillon = false;

		public Sequence	sMidiSeqAlarm= null;
		public Sequence	sMidiSeqHour= null;
		public Sequence	sMidiSeqQuarter= null;

		AgentAlarm cTheAgentAlarm;




		public final double sNanoSec = ((double)1)/1000000000;
		double cCurrentTime=0;
		double cLastTurnTime = System.nanoTime()*sNanoSec;
		double cTimeDiff=0;

		HashMap<String, Sprite> cSpriteTab = new HashMap();

		//------------------------------------------------
    public DesktopAnim() {
				super();
		}
		//------------------------------------------------
		//----------------- Call by W0k4 -----------------
		//------------------------------------------------
		public void gadgetInitialize() {			 				
				
				if( PPgWinUtils.IsTranslucentSupported() == false ){
						System.err.println("Shaped windows are not supported");
						// System.exit();
				} else cTranslucent = true;
				

				cScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

				cFilePref = gadgetGetName() + ".ini"; //gadgetGetInstanceName() + ".ini";

				readPref( cFilePref );

				cDimension = new Dimension( 200,  50 );
																		

				setSize( cDimension );

				
				
				getContentPane().setLayout( new BorderLayout() );		
				
				setUndecorated( true );
				setAlwaysOnTop(cAlwaysOnTop);
				
 				setLocation(cLocation);
				setSize( cDimension );


				sText  = new JLabel();

				sText.setPreferredSize(cDimension ); 

				//	sText.setFont( sText.getFont().deriveFont(sFontSize));
				sText.setForeground( sColor );
				
				getContentPane().add( sText, BorderLayout.CENTER  );
				
				//				sTheDesktopAlarm = this;
				
				if( cTranslucent ){
						setBackground(new Color(0,0,0,0));
						setOpacity(cOpacity);
				}
			
				setVisible(true);
				executeOptions();
				
				sTimer.start();


				SwingUtilities.invokeLater( this );				

    }
		//---------------------
		public void run() {
				String lConfig= gadgetGetName() + "_Sprite.ini"; 
				readSprite( lConfig  );
		}
		//---------------------
		public void gadgetGetMenu( Menu lMenu) { 
				
				//====================== Move the Clock with mouse ==========================
				final DesktopAnim lTheDesktopAnim = this;
				MenuItem lItem = new MenuItem("Move");
				lItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

										if( cTranslucent  ) {
												lTheDesktopAnim.setBackground(new Color(0.5f, 0.5f, 0.5f, 1));
												setOpacity(1);
										}

										addMouseListener( lTheDesktopAnim );		
										addMouseMotionListener( lTheDesktopAnim );		
								}
						});	

				lMenu.add( lItem );	
				//======================
				lItem = new MenuItem("About");
				lItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										URL lURLIcon = getClass().getResource("/com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim_80x80.png");

										JOptionPane.showConfirmDialog( null,	"DesktopAnim\n1.0\nPhilippe Poupon 2013", "About DesktopAnim", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( lURLIcon ));				
								}
						});	
				lMenu.add( lItem );	
				//======================
		}
		//---------------------
		public void     gadgetOpenDialogOption() { new DialogOptions( this  ); }
		//---------------------
		public void   gadgetDestroy() {
				sTimer.stop();
		}
		//---------------------
		public boolean  gadgetUniqueInstance()   { return false; } 

		//------------------------------------------------
		//------------- Time & Anim   -------------------
		//------------------------------------------------

    @Override
				public void actionPerformed(ActionEvent pEv ) {
				cCurrentTime = System.nanoTime()*sNanoSec;       // resultat en seconde 

				cTimeDiff = cCurrentTime - cLastTurnTime; // temps passe depuis la dernier fois en seconde !

				
				for( Sprite lSprite: cSpriteTab.values() )
						lSprite.animate( cTimeDiff );


				cLastTurnTime = cCurrentTime;	
		}
		//-------------------------------------------------------
		// Called by DialogOptions for validate the modification
		//-------------------------------------------------------

		void executeOptions(){	
				

				for( Sprite lSprite: cSpriteTab.values() ){
						lSprite.setOpacity( cOpacity );
						lSprite.setAlwaysOnTop(cAlwaysOnTop);
				}

		}
		//------------------------------------------
		//---------- Change position of window -----
		//------------------------------------------
		Point cMemPoint;

		public void mousePressed( MouseEvent pEv ) {
				
				if( SwingUtilities.isLeftMouseButton( pEv ) ){
						cMemPoint = 	pEv.getLocationOnScreen();
				}
		}
		//---------------------
		public void mouseReleased( MouseEvent pEv ) {

				if( cTranslucent ) {
						setBackground(new Color(0.f, 0.f, 0.f, 0.F));
						setOpacity(cOpacity);
				}

				removeMouseListener(this);	
				removeMouseMotionListener(this);

				savePref( cFilePref );
		}
		//---------------------
		public void mouseClicked( MouseEvent pEv ) {;}
    public void mouseEntered( MouseEvent e){;}
		public void mouseExited( MouseEvent e){;}
		public void mouseMoved( MouseEvent pEv ){;}

		public void mouseDragged( MouseEvent pEv ){
				
				if( SwingUtilities.isLeftMouseButton( pEv ) ){
						
						Point lNewPoint = 	pEv.getLocationOnScreen();
						
						int lDiffX = lNewPoint.x-cMemPoint.x; 
						int lDiffY = lNewPoint.y-cMemPoint.y; 
						
						cLocation.setLocation( cLocation.x +lDiffX, 
																	 cLocation.y +lDiffY);						
						
						setLocation( cLocation );
						cMemPoint = lNewPoint;			
				}				
		}

		//------------------------------------------------
		//-------- load an save  Preference---------------
		//------------------------------------------------

		HashMap<String,Image> cImgChache = new HashMap();



		Sprite readSprite( PPgIniFile pIni, String pSection, String pName  ){

				 System.out.println( "++++++++++ readSprite " + pName );
				String lStrSprite = pIni.get( pSection, pName );

				String []lStr = lStrSprite.split( "," );
				float lVx = Float.parseFloat( lStr[0] );
				float lVy = Float.parseFloat( lStr[1] );
				PPgImgBase lAnim = PPgImg.ReadAnim( pIni, pSection, lStr[2], cImgChache );
				if( lAnim != null ){
						return new Sprite( this, pName, lVx, lVy, lAnim );
				}		

				return null;
		}
		//------------------------------------------------
		PPgIniFile readPref( String pStrFile ){

				System.out.println( "+++++++++++++++++ readPref +++++++++++++++++>" +  pStrFile); 

				

				File lFile = new File( pStrFile );
				if( ! lFile.exists() || ! lFile.canRead() ){
						return null;
				}
		
				PPgIniFile lIni = new PPgIniFile( pStrFile );
				if( lIni == null )
						return null;

				cLocation.x = lIni.getInteger( "WINDOWS", "X" );
				cLocation.y = lIni.getInteger( "WINDOWS", "Y" );

				sColor = lIni.getColor( "WINDOWS",  "COLOR", Color.black );

				cFontSize = lIni.getfloat( "WINDOWS",  "FONT_SIZE", cFontSize );
				String lTmpStr = lIni.get("WINDOWS",  "FONT");

			

				cOpacity  = lIni.getfloat( "WINDOWS",  "OPAQUE", cOpacity );
				cAlwaysOnTop = lIni.getboolean( "WINDOWS",  "ALWAYS_ON_TOP",  cAlwaysOnTop);


				return lIni;
		}

		//------------------------------------------------
		void readSprite( PPgIniFile pIni ){
				System.out.println( "lAnimBase load " ); 


				String lSprites = pIni.get( "ANIM", "SPRITES" );
				String [] lSprite = lSprites.split("," );

				for( int i=0; i< lSprite.length; i++){
						Sprite lMySprite = readSprite( pIni, "ANIM", lSprite[i] );
						cSpriteTab.put( lMySprite.getName(), lMySprite );
				}
		}
		//------------------------------------------------
		void readSprite( String pStrFile ){
			 
				File lFile = new File( pStrFile );
				if( ! lFile.exists() || ! lFile.canRead() ){
						return ;
				}
		
				PPgIniFile lIni = new PPgIniFile( pStrFile );
				if( lIni == null )
						return ;

				readSprite( lIni );
		}	
		//------------------------------------------------
		public static String GetStyle( Font lFont ) {
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
		void savePref( String pStrFile ){				
				
				File lFile = new File( pStrFile );
				try{
						
						if( ! lFile.exists() || ! lFile.canWrite() ){
								if(  lFile.createNewFile( ) == false ){
										JOptionPane.showMessageDialog( this,
																									 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																									 "DesktopAlarm error", JOptionPane.ERROR_MESSAGE);										
										return;
								}
						}
				} catch( IOException ex ){
						JOptionPane.showMessageDialog( this,
																					 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																					 "DesktopAlarm error", JOptionPane.ERROR_MESSAGE);
						return ;
				}


				PPgIniFile lIni = new PPgIniFile();
				lIni.set( "WINDOWS", "X",           ""+cLocation.x );
				lIni.set( "WINDOWS", "Y",           ""+cLocation.y );

				float [] lRgb = new float[3];
				sColor.getRGBColorComponents(lRgb );

				lIni.set( "WINDOWS",  "COLOR",      "rgb(" + lRgb[0] +','+ lRgb[1] +',' + + lRgb[2] +')'  );
				//		System.out.println( "Font save <" +sText.getFont().getFamily()  +'-' + GetStyle( sText.getFont() ) + '-' + sText.getFont().getSize()+ ">" ); 

				lIni.set( "WINDOWS",  "FONT",      PPgWinUtils.CodeFont( sText.getFont()));

				lIni.set( "WINDOWS",  "FONT_SIZE",  ""+cFontSize );

				lIni.set( "WINDOWS",  "OPAQUE",     ""+ cOpacity );
				lIni.set( "WINDOWS",  "ALWAYS_ON_TOP",     ""+ cAlwaysOnTop);

				
				lIni.writeIni( pStrFile );
		}
}
//*************************************************
