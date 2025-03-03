package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


//*************************************************
abstract class  OpGraf {
		
		abstract public void setKeyCode( char pKeyCode );

		public EdImgInst cMyInst = null;

		OpGraf(){;}
		OpGraf(EdImgInst pMyInst){ cMyInst = pMyInst;}
		public void setMyInst( EdImgInst pMyInst) { cMyInst = pMyInst; }

		abstract public char   histoGetCode();
	  abstract public String histoGetName();
		abstract public String histoGetData();				
		abstract public String histoTraductToComment( String pData );

		abstract public void histoReplay( String pData );
}
//*************************************************

