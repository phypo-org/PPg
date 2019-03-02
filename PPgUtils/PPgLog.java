package org.phypo.PPg.PPgUtils;


import java.util.*;
import java.io.*;
import java.lang.*;


//*************************************************

public class PPgLog extends PrintStream{

		public static PPgLog Out = new PPgLog( System.out );
		public static PPgLog Err = new PPgLog( System.err );;


		PPgLog( OutputStream pOut ){
				super( pOut );
		}
}
//*************************************************
