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
import com.jogamp.opengl.*;
import com.phipo.PPg.PPgJ3d.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

//*************************************************

public class WorldEd  extends World3d {

        
             
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
                
