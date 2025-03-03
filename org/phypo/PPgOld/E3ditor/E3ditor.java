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
                                Engine lEngine = new Engine(  "Test JOGL", 1600, 1280,  false, lSmooth, false  );
                                WorldEd lTest  = new WorldEd( new DimFloat3( 20, 20, 20), lEngine, lSmooth  );                
                                 
                                E3ditor lProg = new E3ditor();

                                lProg.addChild( new FrameE3d(lEngine, 30 ) );                

                                //                                lTest.setUserControl( new DefaultUserControl3dt( lTest) );

                                lProg.setVisible(true);

                        
                }
};
//*************************************************


