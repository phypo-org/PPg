package com.phipo.PPg.T1m3;


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

import com.phipo.PPg.PPgWin.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


import java.io.IOException;

import com.phipo.PPg.PPgUtils.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;

import com.phipo.PPg.PPgSound.*;
import com.phipo.PPg.PPgWin.*;

//*************************************************

//public class T1m3 extends JFrame implements ActionListener {
public class T1m3 extends JDialog 
		implements ActionListener, WindowListener, MouseListener{

		static public T1m3 sTheT1m3 = null;

		MyTrayIcon    cMyTrayIcon = null;

		static boolean sUseSecond = false;
    static final SimpleDateFormat lSdfSec = new SimpleDateFormat("HH:mm:ss");
    static final SimpleDateFormat lSdf = new SimpleDateFormat("HH:mm");

    static  final public Date   sNow   = new Date();

    static final public JLabel sText  = new JLabel();
		final  public javax.swing.Timer  sTimer = new javax.swing.Timer(1000, this);

		static public Color sColor = Color.red;
		//		static public Font  sFont = sText.getFont();;



		static public boolean sTranslucent = false;
		static String sStrTxt ="";
		public boolean cInteractif=false;

		static boolean sFlagInit = true;

		//------------------------------------------------

		public static float sOpacity  = 0.65f;
		public static float sFontSize = 40f;

		public static Point sLocation = new Point( 50, 40 );
		public static Dimension sDimension  = new Dimension( 250, 50 );
		public static boolean sAlwaysOnTop = false;
		
		static public char sIndicAlarm = '°';
		static public HashSet<String> sAlarm = new HashSet<>();
		static String sFilePref = "T1m3.ini";
		


		static public Sequence	sMidiSeqAlarm= null;
		static public Sequence	sMidiSeqHour= null;
		static public Sequence	sMidiSeqQuarter= null;

		static public boolean sFlagCarillonHeure = false;
		static public boolean sFlagCarillon = false;

		//------------------------------------------------

    public T1m3( boolean pInteractif) {
        super();

				if( sFlagInit ){
						readPref( T1m3.sFilePref );
						sFlagInit = false;
				}
				
				cInteractif = pInteractif;
				
				//	AJOUTER UN  FICHER DE PREFERENCE  SAUVEGARER DANS DialogOption
				
				getContentPane().setLayout( new BorderLayout() );		
				
				if( cInteractif == false ){
						setUndecorated( true );
						setBackground(new Color(0,0,0,0));
				}
				
 				setLocation(sLocation);
				setSize( sDimension );
				
				
			
				//g				sText.setFont( sFont.deriveFont(sFontSize));
				//				else
				sText.setFont( sText.getFont().deriveFont(sFontSize));

				sText.setForeground( sColor );
				
				getContentPane().add( sText, BorderLayout.CENTER  );
				setAlwaysOnTop(sAlwaysOnTop);

				
				
				if( cInteractif == true ){
						addWindowListener(this );

						addMouseListener( this );		
						sText.addMouseListener( this );		
				} 
				else {
						sText.removeMouseListener(this);	
						removeMouseListener(this);
						if( SystemTray.isSupported() ) {
								cMyTrayIcon = new MyTrayIcon( "/com/phipo/PPg/T1m3/T1m3_20x20.png" );//"MyIcon.png" );
						} 
				}
						
				setDefaultCloseOperation( DISPOSE_ON_CLOSE  );			

			sTheT1m3 = this;
			
			if( sTranslucent && cInteractif == false )
					setOpacity(sOpacity);
			
			setTime();

			sTimer.start();

			setVisible(true);
			executeOptions();

		}
	
		//------------------------------------------------
		String cOldDate ="00:00:00";

		void setTime(){


        sNow.setTime(System.currentTimeMillis());
				
				//			cText.setText(String.format("<html><body><font size='50'>%s</font></body></html>", sdf.format(now)));
				if( sUseSecond )
						sStrTxt = lSdfSec.format( sNow);
				else
						sStrTxt = lSdf.format( sNow);

				// pas de changement
				if( sStrTxt.compareTo( cOldDate ) == 0)
						return ;



				//				Graphics2D lCG = (Graphics2D)sText.getGraphics();
				if( sAlarm.size() !=0 )
						 sStrTxt = sStrTxt+sIndicAlarm;
				
				sText.setText( sStrTxt );

				// si il y a des alarlmes et si le chiffre des minute a changé
				//			System.out.println("A " + sStrTxt.charAt(4) + '=' + cOldDate.charAt(4) 
				//													 + " " + sFlagCarillonHeure+ " " +sFlagCarillon + " " +PPgMidi.IsPlaying() );
				

				if( sAlarm.size() !=0 && sStrTxt.charAt(4) != cOldDate.charAt(4) ){ // changement de minute 						
						
						//						System.out.println("Alarm " + sStrTxt.substring( 0, 5 ) );
						
						if(	sAlarm.contains( sStrTxt.substring( 0, 5 ) )){
								AgentAlarm.Put( sMidiSeqAlarm );
								//=======================================
								SwingUtilities.invokeLater( new Runnable(){
												public void run(){
														//=====														
														JOptionPane.showMessageDialog( T1m3.sTheT1m3,	"Alarm !");
														AgentAlarm.StopCurrentPlay();
														//														System.out.println( "STOP !");
														
														//=====
												}	
										} );
								//=======================================
						}
						else {
								if( (sFlagCarillonHeure ||sFlagCarillon ) && PPgMidi.IsPlaying() == false ){
										if( sStrTxt.charAt(3)=='0' && sStrTxt.charAt(4)=='0') {

												AgentAlarm.Put( sMidiSeqHour);

										} else if( sFlagCarillon  && PPgMidi.IsPlaying() == false 
															 && ( ( sStrTxt.charAt(3)=='1'  && sStrTxt.charAt(4)=='5') 
																		|| ( sStrTxt.charAt(3)=='3' && sStrTxt.charAt(4)=='0')
																		|| ( sStrTxt.charAt(3)=='4' && sStrTxt.charAt(4)=='5')))
												{
														AgentAlarm.Put( sMidiSeqQuarter );
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
		//------------------------------------------------		
    public static void main(String[] args) {
				
				
				if( PPgWinUtils.IsTranslucentSupported() == false ){
						System.err.println("Shaped windows are not supported");
						// System.exit();
				} else sTranslucent = true;
				
				new T1m3( false );

				new PPgMidi();
				new AgentAlarm();
				AgentAlarm.sTheAgentAlarm.start();


				sMidiSeqAlarm   = PPgMidi.CreateSequenceFile( "Alarm.mid" );
				sMidiSeqHour    = PPgMidi.CreateSequenceFile( "Hour.mid" );
				sMidiSeqQuarter = PPgMidi.CreateSequenceFile( "Quarter.mid" );

    }

		//---------------------
		// Called by DialogOptions

		void executeOptions(){	
				
				//				sFont = sText.getFont();
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

				if( T1m3.sTranslucent && cInteractif == false )
						T1m3.sTheT1m3.setOpacity( T1m3.sOpacity );
				
			setAlwaysOnTop(sAlwaysOnTop);
		}
		//------------------------------------------
		public void mousePressed( MouseEvent pEv ) {
				
				JPopupMenu lPopup = new JPopupMenu();
				JMenuItem lOptionsItem = new JMenuItem("Option ...");
				JMenuItem lAboutItem   = new JMenuItem("About");
				JMenuItem lCloseItem   = new JMenuItem("Close");

				lPopup.add( lOptionsItem );
				lOptionsItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {

												new DialogOptions();
										}
								});
				
				lPopup.add( lAboutItem );
				lAboutItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										
										URL lURLIcon = getClass().getResource("/com/phipo/PPg/T1m3/T1m3_80x80.png");

										JOptionPane.showConfirmDialog( null,	"T1m3\n1.0\nPhilippe Poupon 2013", "About T1m3", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( lURLIcon ));	
										/*
										JOptionPane.showConfirmDialog( null,	"T1m3\n1.0\nPhilippe Poupon 2013", "About T1m3", 
																									 JOptionPane.DEFAULT_OPTION, 
																									 JOptionPane.INFORMATION_MESSAGE, new ImageIcon( "T1m3_80x80.png" ));	
										*/
										
					}
						});
				 
				lPopup.add(lCloseItem  );
				lCloseItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
												
												T1m3.sTheT1m3.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
												dispose();
										}
								});


				lPopup.show( pEv.getComponent(),
										 pEv.getX(),
										 pEv.getY() );
		}
		//---------------------
		public void mouseReleased( MouseEvent pEv ) {;}
		public void mouseClicked( MouseEvent pEv ) {;}
    public void mouseEntered( MouseEvent e){;}
		public void mouseExited( MouseEvent e){;}
		//------------------------------------------
		public void 	windowClosing(WindowEvent e){	
		
		}
		//------------------------------------------------
		public void 	windowActivated(WindowEvent e){;}
		//------------------------------------------------
		public void windowClosed(WindowEvent e){
				// Pour ne pas multiplier les icones dans le TrayIcon
						sText.removeMouseListener(this);	
						//		System.out.println( "windowClosed" );

						if( cMyTrayIcon != null ){
							cMyTrayIcon.removeMe();
						}

					if( cInteractif ){
							//			System.out.println( "cInteractif" );
							sLocation =  T1m3.sTheT1m3.getLocation();
							T1m3.sTheT1m3.savePref( T1m3.sFilePref );
							cInteractif = false;
							new T1m3( false );
							executeOptions();	
							
					}
		}									
		//------------------------------------------------
		public void 	windowDeactivated(WindowEvent e){;}
		public void 	windowDeiconified(WindowEvent e){;}
		public void 	windowIconified(WindowEvent e){;}
		public void 	windowOpened(WindowEvent e){;}
		

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
										JOptionPane.showMessageDialog( T1m3.sTheT1m3,
																									 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																									 "T1m3 error", JOptionPane.ERROR_MESSAGE);
										
										return;
								}
						}
				} catch( IOException ex ){
						JOptionPane.showMessageDialog( T1m3.sTheT1m3,
																					 "Save option in file "+ lFile.getAbsolutePath() + " failed", 
																					 "T1m3 error", JOptionPane.ERROR_MESSAGE);
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
		//------------------------------------------------
	
}
//*************************************************
