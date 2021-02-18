package org.phypo.PPg.PPgFX;


import org.phypo.PPg.PPgUtils.Log;

import javafx.scene.control.TextArea;
import javafx.stage.Window;


//*********************************************
public class SimpleEdit extends PPgDialog  {
	
	//-------------------------------------------------------------------------------------
	protected SimpleEdit( Window iOwner, String iTitle, String iStrText, String iFoot, boolean iIsEditable, boolean iModal , boolean iFlagClose) {
		super( iOwner, iTitle, iModal, iFlagClose   );
	
		Log.Dbg( "SimpleEdit size : "+iStrText.length());
	
		TextArea lText = new TextArea( iStrText);
		getPrimPane().setCenter(lText);
		lText.setEditable(iIsEditable);
		Log.Dbg( "SimpleEdit TextArea");
		
		if( iFoot != null )
			setFootText( iFoot );
		
		if( iModal ) {
			addToBottomRight( FxHelper.CreateButton( "Close", null, ev -> {
				cOk = true;			
				close();
			}));
		}
		Log.Dbg( "SimpleEdit medium");
		this.setWidth(1024);
		this.setHeight(800);
		
		Log.Dbg( "SimpleEdit end creation");
	}
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	static public SimpleEdit CreateModal( Window iOwner, String iTitle, String iStrText  ) {
		return new SimpleEdit( null, iTitle, iStrText, null, true, true, false);
	}
	//-------------------------------------------------------------------------------------
	static public SimpleEdit CreateModalNotEditable( Window iOwner, String iTitle, String iStrText ) {
		return new SimpleEdit( null, iTitle, iStrText, null, false, true, false);
	}
	
	//-------------------------------------------------------------------------------------
	static public SimpleEdit Create( Window iOwner,String iTitle, String iStrText ) {
		return new SimpleEdit( null, iTitle, iStrText, null, true, false, true );
	}
	//-------------------------------------------------------------------------------------
	static public SimpleEdit CreateNotEditable( Window iOwner, String iTitle, String iStrText ) {
		return new SimpleEdit( null, iTitle, iStrText, null, false, false, true );
	}
}
//*********************************************
