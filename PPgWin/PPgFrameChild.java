package org.phypo.PPg.PPgWin;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.phypo.PPg.PPgUtils.PPgIniFile;


//***********************************
public class PPgFrameChild extends JInternalFrame implements InternalFrameListener {

    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    String cName;
    public String getName() { return cName; }

    //-------------------------------------
    public PPgFrameChild( String pName ) {
        super( pName + "#" + (++openFrameCount), 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

	init( pName );
    }
    //-------------------------------------
    public PPgFrameChild( String pName, boolean pClosable ) {
        super( pName + "#" + (++openFrameCount), 
              true, //resizable
              pClosable, //closable
              true, //maximizable
              true);//iconifiable

	init( pName );
    }
    //-------------------------------------
    protected void init(  String p_name ){

	cName = p_name;
        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(400,400);

        //Set the  window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
				if( openFrameCount > 10 ) openFrameCount=1;

				addInternalFrameListener( this );
					
				if( PPgAppli.sGlobalForeground != null )
						setForeground(PPgAppli.sGlobalForeground );
				
				if( PPgAppli.sGlobalBackground != null  )
						setBackground( PPgAppli.sGlobalBackground );			 
    }
	//-------------------------------------
		public void front(){
				try{
					setIcon( false );
					show();
					toFront();	
				}
				catch( PropertyVetoException pv ){
				}
		}
		//-------------------------------------
		//-------------------------------------
		public void saveConfigIni( PPgIniFile pIni, String pSection ){
				pIni.set( pSection, getName(),
									((int)getLocation().getX())+","+
									((int)getLocation().getY())+","+
									((int)getSize().getWidth())+","+
									((int)getSize().getHeight()) );
		}
		//-------------------------------------
		public void readConfigIni( PPgIniFile pIni, String pSection ){
				Rectangle lRect = PPgIniFile.GetRectangle( pIni.get( pSection,  getName() ), "," );
				if( lRect != null)	setBounds( lRect);
		}
		//-------------------------------------
		//-------------------------------------
		// implements internalFrameListenery

		//-------------------------------------
		//-------------------------------------

		public void internalFrameOpened( InternalFrameEvent pEv){
				//				System.out.println( "internalFrameOpened");
		}
		//-------------------------------------
		public void internalFrameClosing( InternalFrameEvent pEv){
				//				System.out.println( "internalFrameClosing");
		}
		//-------------------------------------
		public void internalFrameClosed(InternalFrameEvent pEv){
				//				System.out.println( "internalFrameClosed");
		}
		//-------------------------------------
		public void internalFrameIconified( InternalFrameEvent pEv){
				//			System.out.println( "internalFrameIconified");
		}
		//-------------------------------------
		public void internalFrameDeiconified( InternalFrameEvent pEv){
				//			System.out.println( "internalFrameDeiconified");
		}
		//-------------------------------------
		public void internalFrameActivated( InternalFrameEvent pEv){
				//			System.out.println( "internalFrameActivated");
		}
		//-------------------------------------
		public void internalFrameDeactivated( InternalFrameEvent pEv){
				//			System.out.println( "internalFrameDeactivated");
		}	
}
//***********************************
