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
