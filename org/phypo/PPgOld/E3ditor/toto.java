FrameE3d
package com.phipo.PPg.E3ditor;


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

import com.phipo.PPg.PPgWin.*;
import com.jogamp.opengl.awt.*;
import com.phipo.PPg.PPgJ3d.*;


//*************************************************

public class FrameE3d extends PPgFrameChild{


                public         FrameE3d( Engine pEngine, int pFps ){
                                
                                super( "Edit 3d");
                                
                                getContentPane().setLayout( new BorderLayout()); 
                                GLCanvas lCanvas = pEngine.getGLCanvas();
                                JScrollPane cScrollPane = new JScrollPane( lCanvas );
                                getContentPane().add( cScrollPane,  BorderLayout.CENTER );

                                pack();
                                setVisible(true);                

                                pEngine.start( pFps );
                }
};
//*************************************************

WorldEd


package com.phipo.PPg.E3ditor;


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

import com.phipo.PPg.PPgWin.*;
import com.jogamp.opengl.awt.*;
import com.phipo.PPg.PPgJ3d.*;


//*************************************************

public class FrameE3d extends PPgFrameChild{


                public         FrameE3d( Engine pEngine, int pFps ){
                                
                                super( "Edit 3d");
                                
                                getContentPane().setLayout( new BorderLayout()); 
                                GLCanvas lCanvas = pEngine.getGLCanvas();
                                JScrollPane cScrollPane = new JScrollPane( lCanvas );
                                getContentPane().add( cScrollPane,  BorderLayout.CENTER );

                                pack();
                                setVisible(true);                

                                pEngine.start( pFps );
                }
};
//*************************************************

import com.phipo.PPg.PPgJ3d.*;


import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//*************************************************

public class WorldEd  extends World3d {

        
                PPgRandom cRand = new PPgRandom(666);

                static FloatBuffer sFBuf = ByteBuffer.allocateDirect(256).asFloatBuffer();

                static float sGreyAmbient[]  =   { 0.5f,  0.5f, 0.5f, 1.0f };
                static float sWhiteDiffuse[]  =  { 1.0f,  1.0f, 1.0f, 1.0f };

                static float sPosition[] =       { 1.0f, 1.0f, 0.0f, 0.0f }; //  a l'infini
                protected ArrayList<ActorBase>   cDemoActor = new ArrayList();

                boolean cFlagDemo2 = false;

                static boolean sFlagXY = false;
                static boolean sViewGrid3d = true;

                static boolean sViewRepere = true;
                static float sGenSize=1f;


                TextRenderer cTextRender;
                TextRenderer cTextRender2;
                //------------------------------------------------
                public  WorldEd(  DimFloat3 pWorldBox, Engine pEngine, boolean pSmooth){
                                super( pWorldBox, pEngine );
                }
                                
                                //------------------------------------------------
                @Override
                public void callEngineInit( GL2 pGl ){

                }                
                //------------------------------------------------
                @Override
                public void callEngineDrawBegin(GL2 pGl){                

                }        
        //------------------------------------------------
                        @Override
                public void worldCallEndSceneDraw(GL2 pGl) {
                                }
        //------------------------------------------------
        
};
//*************************************************
                
package com.phipo.PPg.E3ditor;


import com.jogamp.opengl.*;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.awt.TextRenderer;

import com.jogamp.opengl.glu.gl2.*;

import com.jogamp.nativewindow.util.Point;

import java.nio.*;
import java.util.*;
import java.awt.Font;
import java.text.DecimalFormat;

import com.phipo.PPg.PPgUtils.*;
import com.phipo.PPg.PPgMath.*;


import com.jogamp.opengl.awt.*;



import com.phipo.PPg.PPgJ3d.*;



import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import java.awt.event.*;


import com.phipo.PPg.PPgWin.*;




//*************************************************

public class E3ditor  extends PPgJFrame
                implements ActionListener, ItemListener{

                static public E3ditor sTheE3ditor = null;
                JLabel cFootBar=null;



                public E3ditor(){
                                super( "E3ditor", true );
                                 cFootBar = new JLabel( "footbar" );

                                getContentPane().add( cFootBar, BorderLayout.SOUTH );

                                sTheE3ditor = this;
                }
                //-----------------------------
                public PPgInterfaceAppli getInterfaceAppli(){
                                return null;
                }
                //-----------------------------
                public static JLabel  GetFootBar(){
                                return sTheE3ditor.cFootBar;
                }
                //-----------------------------
                public static void SetFootText( String pStr ){
                                sTheE3ditor.cFootBar.setText( pStr );
                }


                //------------------------------------------------
                //--------------- ActionListener -----------------
                //------------------------------------------------

                public void actionPerformed( ActionEvent pEv ){                
                                System.out.println( "PPgFarmeEdImg.actionPerformed" );        
                                
                }                
                //------------------------------------------
                public void itemStateChanged(ItemEvent pEv ){
                                Object lSource = pEv.getItemSelectable();
                }

                //------------------------------------------------
                
                public static void main( String [] args ) {


                                boolean lSmooth=false;
                                Engine lEngine = new Engine( false,  "Test JOGL", 1600, 1280,  false, lSmooth  );
                                WorldEd lTest  = new WorldEd( new DimFloat3( 20, 20, 20), lEngine, lSmooth  );                
                                 
                                E3ditor lProg = new E3ditor();

                                lProg.addChild( new FrameE3d(lEngine, 30 ) );                

                                //                                lTest.setUserControl( new DefaultUserControl3dt( lTest) );

                                lProg.setVisible(true);

                        
                }
};
//*************************************************


