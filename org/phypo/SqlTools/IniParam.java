package org.phypo.SqlTools;

import java.awt.Color;

import org.phypo.PPg.PPgUtils.PPgIniFile;
import org.phypo.PPg.Sql.SqlServer;

import javax.swing.ImageIcon;
import javax.swing.GrayFilter;

//***************************************
class IniParam{


    // -----------------------------------
    static ImageIcon ReadIcon( PPgIniFile pIni, String pSection, String pKey ){
	if( pSection == null || pKey == null )
	    return null;
	
	String lStr  = pIni.get( pSection, pKey );
	if( lStr == null )
	    return null;
	
	ImageIcon lIcon  = new ImageIcon( lStr ); 
	return lIcon;
    }
    // -----------------------------------
    
    public static ImageIcon sBackGroundImage=null;
    
    public static ImageIcon sIconeSqlTools=null;
    
    
    public static String sSelectServer;
    public static String sSelectConnex;
    
    
    // SqlTools
    public static int sDialogX=300;
    public static int	sDialogY=200;
    
    
    
    //------------------------------------------------
    public static void ReadIni( PPgIniFile pIni){
	
	
	//======== SqlTools Section =======
	
	sIconeSqlTools   = ReadIcon( pIni, "SqlTools", "Icone" );
	//	sDialogX  = pIni.getint( "SqlTools", "DialogX", sDialogX );
	//	sDialogY  = pIni.getint( "SqlTools", "DialogY", sDialogY );					 
    }		
    // -----------------------------------		
    public static void SaveIni( PPgIniFile pIni) {
    }
}
//***************************************
