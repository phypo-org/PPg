package org.phypo.PPgEdImg;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


import java.util.*;



//*************************************************
public class OpHisto extends ArrayList<String>{

		public OpHisto(){
		}

		int cCurrent=-1;

		public int  getCurrent()  { return cCurrent; }
		public void setCurrent( int pCurrent ) { cCurrent = pCurrent; }

		//----------------------------------------------
		public boolean addOp( char pCode, String pData ){

				System.out.println( ">>>>>>>>>>>>>>> HISTO  OpHisto.addOp <<<<<<<<<<<<<<<" + pCode +"> <" + pData + ">" );

				if( cCurrent != size()-1 ){
						removeRange( cCurrent+1, size() );
				}
				cCurrent = size();

				add( "" +pCode + ':' + pData );

				return true;
		}
		//----------------------------------------------
}
//*************************************************
