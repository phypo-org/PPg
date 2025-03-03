package org.phypo.PPg.PPgFX;



import org.phypo.PPg.PPgUtils.PPgTrace;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Window;


//*********************************************
public class SimpleEdit extends PPgDialog  {
	protected TextArea cText ;
	protected Button   cButtonClose;
	//-------------------------------------------------------------------------------------
	protected SimpleEdit( Window iOwner, String iTitle, String iStrText, String iFoot, boolean iIsEditable, boolean iModal , boolean iFlagClose) {
		super( iOwner, iTitle, iModal, iFlagClose   );

		PPgTrace.Dbg( "SimpleEdit size : "+iStrText.length());

		cText = new TextArea( iStrText);
		getPrimPane().setCenter(cText);
		cText.setEditable(iIsEditable);
		cText.setFont( Font.font ("monospaced", 12));

		PPgTrace.Dbg( "SimpleEdit TextArea");

		if( iFoot != null )
			setFootText( iFoot );

		if( iModal ) {
			cButtonClose = addToBottomRight( FxHelper.CreateButton( "Close", null, ev -> {
				cOk = true;
				close();
			}));
		}
		PPgTrace.Dbg( "SimpleEdit medium");
		this.setWidth(1024);
		this.setHeight(800);

		PPgTrace.Dbg( "SimpleEdit end creation");
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
