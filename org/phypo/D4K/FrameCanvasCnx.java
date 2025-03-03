package org.phypo.D4K;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.event.InternalFrameEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.util.*;

import org.phypo.PPg.PPgWin.*;

import org.phypo.PPg.PPgUtils.*;

//***********************************
public class FrameCanvasCnx extends PPgFrameChild  implements ActionListener, ItemListener {

    InterfaceCanvas cCanvas = null;

    JPanel      cSud_panel;
    JLabel      cStatus;
    JScrollPane cScrollpane;
    DataCanvasCnx  cDataAppli;


    InterfaceCanvas getCanvas()    { return cCanvas; }
    DataCanvasCnx      getDataAppli() { return cDataAppli;}

    boolean cToBeRedraw=false;
    public void setToBeRedraw() { cToBeRedraw = true; }

    int cCptShowMode=1;
    public int getCptShowMode() { return cCptShowMode; }

		


    //-------------------------------------
    public FrameCanvasCnx(  DKMap iDkMap ){
	super( "Machines" );
	
	cDataAppli = new DataCanvasCnx( iDkMap, this ) ;
	
	    
	getContentPane().setLayout( new BorderLayout() );
 

	cCanvas = new CanvasMachines(this); // METTRE UN CHARGEMENT DINAMYQUE
	    
	cScrollpane  = new JScrollPane(cCanvas.getComponent(),	
				       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				       JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
	getContentPane().add( cScrollpane, BorderLayout.CENTER );
				
	cSud_panel = new JPanel();		
	cSud_panel.setLayout( new GridLayout( 1, 0 )); 
	cStatus  = new JLabel( "Application" );

	setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );

	cSud_panel.add( cStatus );
				
	getContentPane().add( cSud_panel, BorderLayout.SOUTH );	
    }	
    //-------------------------------------
    void reload(){
	cDataAppli.reload();
    }
    //-------------------------------------	
    Hashtable<String,Sprite>  getSpriteTable() { return cDataAppli.getSpriteTable();}
    ArrayList<Sprite>         getSelection()   { return cDataAppli.getSelection();}

    //-------------------------------------	
    public void readIni( PPgIniFile pIni ){
	Rectangle lRect = PPgIniFile.GetRectangle( pIni.get( "Frame", "Application" ), "," );
	if( lRect != null)	setBounds( lRect);

	cDataAppli.readIni( pIni );				
    }
    //-----------------------
    public void saveIni( PPgIniFile pIni ){
				
	pIni.set( "Frame", "Application",
		  ((int)getLocation().getX())+","+
		  ((int)getLocation().getY())+","+
		  ((int)getSize().getWidth())+","+
		  ((int)getSize().getHeight()) );
									
	cDataAppli.saveIni(pIni);			
    }
    //-------------------------------------	
    private void resizeScrolling(){
	Dimension lDim = new Dimension( (int)(cCanvas.getRealSize().getWidth()  * SurvParam.sGeneralScale),
					(int)(cCanvas.getRealSize().getHeight() * SurvParam.sGeneralScale) );

	cCanvas.setPreferredSize( lDim );
	cScrollpane.revalidate();
	//		cScrollpane.repaint();
    }
    //-------------------------------------
    public void forceRepaint(){
	resizeScrolling();
	cCanvas.repaintCanvas();
	cToBeRedraw = false;
    }
    //-------------------------------------
    public void conditionalRepaint(){
	if( cToBeRedraw ){
	    forceRepaint();
	}
    }

    //-------------------------------------
    //-------------------------------------
    //-------------------------------------

    final String cStrDeleteAll      = "Delete all";
    final String cStrDelSelection   = "Delete selection";
    final String cStrDeleteGhosts   = "Delete ghosts";
    final String cStrReloadConfig   = "Reload from config";
    final String cStrReloadSybase   = "Reload from Sybase";

    // ---------------------

    final String cStrArrangeCircle  = "Arrange in Circle";
    final String cStrArrangeSpirale  = "Arrange in Spirale";
    final String cStrArrange2Circle = "Arrange in Double Circle";

    // ---------------------

    final String cStrViewConnection = "View live connection";
    final String cStrViewExternalApplication = "View external application";
    final String cStrDisabledIcone = "Use grayed icone";
    final String cStrShowNbClient      = "View connected clients";
    final String cStrShowFluxTime      = "View transaction flux (transaction/second)";
    final String cStrShowTransacLength = "View transaction length (milliseconds)";

    final static String sStrMenuCpt = "Data display/s";
    final static String sStrLastCpt = "Last Data";
    final static String sStr60sCpt  = "60s average";
    final static String sStrMME     = "Exponantial Average";

    // ---------------------
    JCheckBoxMenuItem cCheckViewConnection;
    JCheckBoxMenuItem cCheckViewExternalApplication;
    JCheckBoxMenuItem cCheckDisabledIcone;
    JCheckBoxMenuItem cCheckShowNbClient;
    JCheckBoxMenuItem cCheckShowFluxTime;
    JCheckBoxMenuItem cCheckShowTransacLength;


    JRadioButtonMenuItem cRadioLastData;
    JRadioButtonMenuItem cRadioAverage60;
    JRadioButtonMenuItem cRadioExponantialAverage;

    //-------------------------------------
    public 	JMenuItem  addItem( JMenu pMenu, String pStr ){
				
	JMenuItem lItem = new JMenuItem( pStr);
				
	lItem.addActionListener( this );	 
				
	pMenu.add( lItem);
	return lItem;
    }

    //------------------------------------------------
		
    public void internalFrameDeactivated( InternalFrameEvent pEv){
	//			JMenu lMenu = PPAppli.ThePPAppli.getEditMenu();
	//				lMenu.removeAll();

	//		lMenu = PPAppli.ThePPAppli.getViewMenu();	
	//				lMenu.removeAll();
    }	

    // ---------------------
		
    public void internalFrameActivated( InternalFrameEvent pEv){

	initMenu();
    }
		
    void initMenu(){
	JMenu lMenu = D4K.Instance.getEditMenu();
	if( lMenu == null )
	    return ;

	lMenu.removeAll();
				
	addItem( lMenu,  cStrDeleteGhosts );				
	addItem( lMenu,  cStrDelSelection );
	addItem( lMenu,  cStrDeleteAll );				
	lMenu.add( new JSeparator() );
	addItem( lMenu,  cStrReloadConfig );
	addItem( lMenu,  cStrReloadSybase );
				
				
	//========================
				
	lMenu = D4K.Instance.getViewMenu();	
	lMenu.removeAll();
				
				


	addItem( lMenu, cStrArrangeCircle );
	addItem( lMenu, cStrArrangeSpirale );
	addItem( lMenu, cStrArrange2Circle );
				
	lMenu.add( new JSeparator() );

	//				lMenu.add( (cCheckViewExternalApplication= new JCheckBoxMenuItem( cStrViewExternalApplication )));
	//				cCheckViewExternalApplication.setSelected( SurvParam.sShowExternalApplication );
	//				cCheckViewExternalApplication.addItemListener( this );

	lMenu.add( (cCheckViewConnection= new JCheckBoxMenuItem( cStrViewConnection )));
	cCheckViewConnection.setSelected( SurvParam.sShowGoodCnx );
	cCheckViewConnection.addItemListener( this );

	lMenu.add( (cCheckDisabledIcone= new JCheckBoxMenuItem( cStrDisabledIcone )));
	cCheckDisabledIcone.setSelected( SurvParam.sDisabledIcone);
	cCheckDisabledIcone.addItemListener( this );



	lMenu.add( new JSeparator() );

	lMenu.add( (cCheckShowNbClient= new JCheckBoxMenuItem( cStrShowNbClient )));
	cCheckShowNbClient.setSelected( SurvParam.sShowNbClient);
	cCheckShowNbClient.addItemListener( this );

	lMenu.add( (cCheckShowFluxTime= new JCheckBoxMenuItem( cStrShowFluxTime )));
	cCheckShowFluxTime.setSelected( SurvParam.sShowFluxTime);
	cCheckShowFluxTime.addItemListener( this );

	lMenu.add( (cCheckShowTransacLength= new JCheckBoxMenuItem( cStrShowTransacLength )));
	cCheckShowTransacLength.setSelected( SurvParam.sShowTransacLength);
	cCheckShowTransacLength.addItemListener( this );

	JMenuItem lItem;
	JMenu lMenuCpt = new JMenu(sStrMenuCpt);


	ButtonGroup lGroup = new ButtonGroup();
	lMenuCpt.add(  ( cRadioLastData= new JRadioButtonMenuItem( sStrLastCpt )));
	cRadioLastData.addActionListener( this );	 				
	lGroup.add( cRadioLastData );

	lMenuCpt.add(  (cRadioAverage60  = new JRadioButtonMenuItem( sStr60sCpt )));
	cRadioAverage60.addActionListener( this );	 
	lGroup.add( cRadioAverage60 );

	lMenuCpt.add(  (cRadioExponantialAverage = new JRadioButtonMenuItem( sStrMME )));
	cRadioExponantialAverage.addActionListener( this );	 
	lGroup.add( cRadioExponantialAverage );
	cRadioLastData.setSelected(true);

	lMenu.add( lMenuCpt );


	//	lMenu.add( new JSeparator() );
	//	lMenu.add(  OrsMonitor.TheOrsMonitor.setViewTheme() );				
    }


    //---------------------
    public void actionPerformed( ActionEvent pEv ){
	if( pEv.getActionCommand().equals( cStrDeleteAll)) {
	    cDataAppli.deleteAll();
	    cCanvas.repaintCanvas();
	}
	else  if( pEv.getActionCommand().equals(cStrDelSelection )) {
	    cDataAppli.deleteSelection();
	    forceRepaint();
	}
	else
	    if( pEv.getActionCommand().equals( cStrDeleteGhosts)) {
		cDataAppli.deleteGhosts();
		cCanvas.repaintCanvas();
	    }
	    else if( pEv.getActionCommand().equals( cStrArrangeCircle )) {
		cDataAppli.arrangeCircle();
	    }
	    else if( pEv.getActionCommand().equals( cStrArrangeSpirale )) {
		cDataAppli.arrangeSpirale();
	    }
	    else if( pEv.getActionCommand().equals( cStrArrange2Circle )) {
		cDataAppli.arrangeDoubleCircle();
	    }
	    else 	if( pEv.getActionCommand().equals( cStrReloadConfig )){
							
		cDataAppli.deleteAll();
		cDataAppli.readIni( D4K.GetIniFile());
	    }
	    else if( pEv.getActionCommand().equals(sStrLastCpt )){
		cCptShowMode = 1;
	    }
	    else if( pEv.getActionCommand().equals(sStr60sCpt )){
		cCptShowMode = 60;
	    }
	    else if( pEv.getActionCommand().equals(sStrMME )){
		cCptShowMode = -1;
	    }
    }
    //---------------------
    public void itemStateChanged(ItemEvent pEv ){

	Object lSource = pEv.getItemSelectable();

	if( lSource == cCheckViewConnection ){
	    if( pEv.getStateChange() == ItemEvent.DESELECTED )
		SurvParam.sShowGoodCnx = false;
	    else
		SurvParam.sShowGoodCnx = true;

	    cCanvas.repaintCanvas();
	}
	else
	    if( lSource == cCheckViewExternalApplication){
		if( pEv.getStateChange() == ItemEvent.DESELECTED )
		    SurvParam.sShowExternalApplication = false;
		else
		    SurvParam.sShowExternalApplication = true;

		cCanvas.repaintCanvas();
	    }
	    else
		if( lSource == cCheckDisabledIcone){
		    if( pEv.getStateChange() == ItemEvent.DESELECTED )
			SurvParam.sDisabledIcone = false;
		    else
			SurvParam.sDisabledIcone = true;

		    cCanvas.repaintCanvas();
		}
		else
		    if( lSource == cCheckShowNbClient){
			if( pEv.getStateChange() == ItemEvent.DESELECTED )
			    SurvParam.sShowNbClient = false;
			else
			    SurvParam.sShowNbClient = true;

			cCanvas.repaintCanvas();
		    }
		    else
			if( lSource == cCheckShowFluxTime){
			    if( pEv.getStateChange() == ItemEvent.DESELECTED )
				SurvParam.sShowFluxTime = false;
			    else
				SurvParam.sShowFluxTime = true;

			    cCanvas.repaintCanvas();
			}
			else
			    if( lSource == cCheckShowTransacLength){
				if( pEv.getStateChange() == ItemEvent.DESELECTED )
				    SurvParam.sShowTransacLength = false;
				else
				    SurvParam.sShowTransacLength = true;

				cCanvas.repaintCanvas();
			    }
    }

}
