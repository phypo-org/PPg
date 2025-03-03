package com.phipo.PPg.M0k4.TimAlarm;

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

import com.phipo.PPg.PPgUtils.*;
import com.phipo.PPg.PPgSound.*;
import com.phipo.PPg.PPgWin.*;
import com.phipo.PPg.M0k4.*;

//*************************************************

public class TimAlarm extends M0k4_Gadget 
		implements ActionListener, MouseListener, MouseMotionListener{

		//		static public TimAlarm sTheTimAlarm = null;

		static final SimpleDateFormat lSdfSec = new SimpleDateFormat("HH:mm:ss");
		static final SimpleDateFormat lSdf    = new SimpleDateFormat("HH:mm");

		final public Date   sNow   = new Date();

		final public JLabel sText  = new JLabel();
		final public javax.swing.Timer  sTimer = new javax.swing.Timer(1000, this);

		String cFilePref = "TimAlarm.ini";  


		public  String sStrTxt ="";

		
		public Color sColor = Color.red;
		
		public boolean sAlwaysOnTop = false;
		public boolean sTranslucent = false;
				
		public float   sOpacity  = 0.65f;
		public float   sFontSize = 40f;
		
		public Point     sLocation = new Point( 50, 40 );
		public Dimension sDimension  = new Dimension( 250, 50 );
		
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

		//------------------------------------------------
    public TimAlarm() {
				super();
		}
		//------------------------------------------------
		//----------------- Call by W0k4 -----------------
		//------------------------------------------------

		public void gadgetInitialize() {			 				
				
				if( PPgWinUtils.IsTranslucentSupported() == false ){
						System.err.println("Shaped windows are not supported");
						// System.exit();
				} else sTranslucent = true;
				

				new PPgMidi();
				cTheAgentAlarm = new AgentAlarm();
				cTheAgentAlarm.start();
				
				sMidiSeqAlarm   = PPgMidi.CreateSequenceFile( "Alarm.mid" );
				sMidiSeqHour    = PPgMidi.CreateSequenceFile( "Hour.mid" );
				sMidiSeqQuarter = PPgMidi.CreateSequenceFile( "Quarter.mid" );

				cFilePref = gadgetGetInstanceName() + ".ini";

				readPref( cFilePref );
				
				getContentPane().setLayout( new BorderLayout() );		
				
				setUndecorated( true );
				setAlwaysOnTop(sAlwaysOnTop);
				
 				setLocation(sLocation);
				setSize( sDimension );
				
				
				sText.setFont( sText.getFont().deriveFont(sFontSize));
				sText.setForeground( sColor );
				
				getContentPane().add( sText, BorderLayout.CENTER  );
				
				//				sTheTimAlarm = this;
				
				if( sTranslucent ){
						setBackground(new Color(0,0,0,0));
						setOpacity(sOpacity);
				}
			
				setTime();				
				sTimer.start();
				
				setVisible(true);
				executeOptions();
    }
		//---------------------
		public void gadgetGetMenu( Menu lMenu) { 
				
				//====================== Move the Clock with mouse ==========================
				final TimAlarm lTheTimAlarm = this;
				MenuItem lItem = new MenuItem("Move");
				lItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

										if( sTranslucent  ) {
												lTheTimAlarm.setBackground(new Color(0.5f, 0.5f, 0.5f, 1));
												setOpacity(1);
										}

										addMouseListener( lTheTimAlarm );		
										addMouseMotionListener( lTheTimAlarm );		
								}
						});	

				lMenu.add( lItem );	
				//======================
				lItem = new MenuItem("About");
				lItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										URL lURLIcon = getClass().getResource("/com/phipo/PPg/M0k4/TimAlarm/TimAlarm_80x80.png");

										JOptionPane.showConfirmDialog( null,	"TimAlarm\n1.0\nPhilippe Poupon 2013", "About TimAlarm", 
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
		public boolean  gadgetUniqueInstance()   { return true; } 

		//------------------------------------------------
		//------------- Time & Alarm   -------------------
		//------------------------------------------------

		String cOldDate ="00:00:00";

		void setTime(){

        sNow.setTime(System.currentTimeMillis());
				
				if( sUseSecond )
						sStrTxt = lSdfSec.format( sNow);
				else
						sStrTxt = lSdf.format( sNow);

				// no change
				if( sStrTxt.compareTo( cOldDate ) == 0)
						return ;

				if( sAlarm.size() !=0 )
						sStrTxt = sStrTxt+sIndicAlarm;
				
				sText.setText( sStrTxt );

				if( sAlarm.size() !=0 && sStrTxt.charAt(4) != cOldDate.charAt(4) ){ // minute change ! 						
												
						if(	sAlarm.contains( sStrTxt.substring( 0, 5 ) )){
								cTheAgentAlarm.put( sMidiSeqAlarm );
								final TimAlarm lTheAlarm = this;
								//=======================================
								SwingUtilities.invokeLater( new Runnable(){
												public void run(){
														//=====														
														JOptionPane.showMessageDialog( lTheAlarm,	"Alarm !");
														cTheAgentAlarm.stopCurrentPlay();
														//=====
												}	
										} );
								//=======================================
						}
						else {
								if( (sFlagCarillonHeure ||sFlagCarillon ) && PPgMidi.IsPlaying() == false ){
										if( sStrTxt.charAt(3)=='0' && sStrTxt.charAt(4)=='0') {
												
												cTheAgentAlarm.put( sMidiSeqHour);
												
										} else if( sFlagCarillon  && PPgMidi.IsPlaying() == false 
															 && ( ( sStrTxt.charAt(3)=='1'  && sStrTxt.charAt(4)=='5') 
																		|| ( sStrTxt.charAt(3)=='3' && sStrTxt.charAt(4)=='0')
																		|| ( sStrTxt.charAt(3)=='4' && sStrTxt.charAt(4)=='5')))
												{
														cTheAgentAlarm.put( sMidiSeqQuarter );
												}
								}
						}						
				}
				
				cOldDate = sStrTxt;
		}
		//------------------------------------------------

    @Override
				public void actionPerformed(ActionEvent pEv ) {
				setTime();
		}
		//-------------------------------------------------------
		// Called by DialogOptions for validate the modification
		//-------------------------------------------------------

		void executeOptions(){	
				
				Font lNewFont =  sText.getFont().deriveFont(sFontSize);
				sText.setFont(lNewFont ); 

				Graphics2D lG2d = (Graphics2D)sText.getGraphics();
				FontRenderContext lFrc = 	lG2d.getFontRenderContext();

				Rectangle2D lRect;
				if( sUseSecond )
						lRect = lNewFont.getStringBounds("00:00:00.9", lFrc);
				else
						lRect = lNewFont.getStringBounds("00:00.9", lFrc);

				setSize( ((int)lRect.getWidth()+10), ((int)lRect.getHeight())+10 );
				sText.setSize( ((int)lRect.getWidth()), ((int)lRect.getHeight()) );	
				sText.setForeground( sColor );
				sDimension = getSize();

				if( sTranslucent  )
						setOpacity( sOpacity );
				
				setAlwaysOnTop(sAlwaysOnTop);
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

				if( sTranslucent ) {
						setBackground(new Color(0.f, 0.f, 0.f, 0.F));
						setOpacity(sOpacity);
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
						
						sLocation.setLocation( sLocation.x +lDiffX, 
																	 sLocation.y +lDiffY);						
						
						setLocation( sLocation );
						cMemPoint = lNewPoint;			
				}				
		}

		//------------------------------------------------
		//-------- load an save  Preference---------------
		//------------------------------------------------

		void readPref( String pStrFile ){

				File lFile = new File( pStrFile );
				if( ! lFile.exists() || ! lFile.canRead() ){
						return;
				}
		
				PPgIniFile lIni = new PPgIniFile( pStrFile );
				if( lIni == null )
						return ;

				sLocation.x = lIni.getInteger( "WINDOWS", "X" );
				sLocation.y = lIni.getInteger( "WINDOWS", "Y" );

				sColor = lIni.getColor( "WINDOWS",  "COLOR", Color.black );

				sFontSize = lIni.getfloat( "WINDOWS",  "FONT_SIZE", sFontSize );
				String lTmpStr = lIni.get("WINDOWS",  "FONT");

				System.out.println( "Font load <" +lTmpStr + ">" ); 


				Font lTmpFont = Font.decode( lTmpStr );
				if( lTmpFont != null ){
						sText.setFont(lTmpFont ); 
				}else System.out.println("Font unknown" + lTmpFont );
				

				sOpacity  = lIni.getfloat( "WINDOWS",  "OPAQUE", sOpacity );
				sAlwaysOnTop = lIni.getboolean( "WINDOWS",  "ALWAYS_ON_TOP",  sAlwaysOnTop);

				sUseSecond  = lIni.getboolean(  "PRESENTATION",  "SECOND",    sUseSecond );

				lTmpStr = lIni.get( "PRESENTATION",  "CAR_ALARM",     ""+ sIndicAlarm );


				if( lTmpStr != null && lTmpStr.length() > 0 )
						sIndicAlarm = lTmpStr.charAt( 0 );


				for( int i=0; i<256; i++ ){
						String lHour = lIni.get( "ALARM",  ""+i, null );
						if( lHour != null )
								sAlarm.add( lHour );
						else
								break;
				}

				sFlagCarillon = lIni.getboolean( "ALARM",  "CARILLON", false );
				sFlagCarillonHeure = lIni.getboolean( "ALARM",  "CARILLON_HEURE", false );
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
																									 "TimAlarm error", JOptionPane.ERROR_MESSAGE);										
										return;
								}
						}
				} catch( IOException ex ){
						JOptionPane.showMessageDialog( this,
																					 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																					 "TimAlarm error", JOptionPane.ERROR_MESSAGE);
						return ;
				}


				PPgIniFile lIni = new PPgIniFile();
				lIni.set( "WINDOWS", "X",           ""+sLocation.x );
				lIni.set( "WINDOWS", "Y",           ""+sLocation.y );

				float [] lRgb = new float[3];
				sColor.getRGBColorComponents(lRgb );

				lIni.set( "WINDOWS",  "COLOR",      "rgb(" + lRgb[0] +','+ lRgb[1] +',' + + lRgb[2] +')'  );
				//		System.out.println( "Font save <" +sText.getFont().getFamily()  +'-' + GetStyle( sText.getFont() ) + '-' + sText.getFont().getSize()+ ">" ); 

				lIni.set( "WINDOWS",  "FONT",      PPgWinUtils.CodeFont( sText.getFont()));

				lIni.set( "WINDOWS",  "FONT_SIZE",  ""+sFontSize );

				lIni.set( "WINDOWS",  "OPAQUE",     ""+ sOpacity );
				lIni.set( "WINDOWS",  "ALWAYS_ON_TOP",     ""+ sAlwaysOnTop);

				lIni.set( "PRESENTATION",  "SECOND",     ""+ sUseSecond );
				lIni.set( "PRESENTATION",  "CAR_ALARM",     ""+ sIndicAlarm );

				int i=0;
				for( String lStr: sAlarm )
						lIni.set( "ALARM",  ""+i++,   lStr  );

				if( sFlagCarillon )
						lIni.set( "ALARM",  "CARILLON",   ""+sFlagCarillon  );
				if(  sFlagCarillonHeure )
						lIni.set( "ALARM",  "CARILLON_HEURE",   ""+sFlagCarillonHeure  );

				
				lIni.writeIni( pStrFile );
		}
}
//*************************************************
