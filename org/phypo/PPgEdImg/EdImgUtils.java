
package org.phypo.PPgEdImg;


import java.awt.image.*;
import javax.swing.*;
import java.io.File;

import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;
import javax.swing.ImageIcon;

import java.beans.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.*;


import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgImg.*;





//*************************************************

class EdImgUtils{



		static public ImageIcon sImgUnlock =  EdImgUtils.GetStdSzImg("Icones/Unlock.png" );
		static public ImageIcon sImgLock   =  EdImgUtils.GetStdSzImg("Icones/Lock.png" );

		static public ImageIcon []  sBulletIconRGB = { EdImgUtils.GetStdSzImg( "Icones/BulletRed32x32.png"),
																 EdImgUtils.GetStdSzImg( "Icones/BulletGreen32x32.png"),
																	EdImgUtils.GetStdSzImg( "Icones/BulletBlue32x32.png")}; // new ImageIcon [3] ; 



		static public ImageIcon GetStdSzImg( String pName ) {
				return ImgUtils.LoadImageFromFile( new File( PPgPref.sRessourcesPath +'/'+pName ), PPgPref.sToolsSz, PPgPref.sToolsSz , false, 1.0 ); }




}

//*************************************************
