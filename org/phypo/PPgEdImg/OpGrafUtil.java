package org.phypo.PPgEdImg;

import java.util.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.PPgUtils.*;

//*************************************************
abstract public class OpGrafUtil extends OpGraf
		implements ActionListener, ItemListener, ChangeListener {


		boolean cInWork = false;

		OpGrafUtil(){;}
		OpGrafUtil(EdImgInst pMyInst ){ super( pMyInst);}

		
		public boolean usePickColor() { return true; }

		//============================
		enum Type{
     				LINE('l', "Line"),
						RECTANGLE('R', "Rectangle" ),
						ROUND_RECT('r',"Round rectangle" ),
						RECTANGLE_3D('3',"3d Rectangle" ),
						OVAL('o', "Oval"),
						CIRCLE( 'c', "Circle" ),
						UNKNOWN('?', "Figure unknown");
						

				public char cVal;
				public String cStr;
						
				Type( char pVal, String pStr ){
						cVal = pVal;
						cStr = pStr;
				}					 

				public static Type Get( char c) {
						switch( c ){
						case'l' : return LINE;
						case'R' : return RECTANGLE;
						case'r' : return ROUND_RECT;
						case'3' : return RECTANGLE_3D;
						case'o' : return OVAL;
						case'c' : return CIRCLE;
						}
						return UNKNOWN;								
				}
		};
		//============================
		Type cType = Type.UNKNOWN;


		//------------------------------------------------

		public void cmdMenuOp(  MouseEvent pEv  ){

				//	if( cInWork == true )
				//		return ;

				JPopupMenu lPopmenu = menuOp ( pEv );
				if( lPopmenu != null ){						
						lPopmenu.show( pEv.getComponent(),
													 pEv.getX(),
													 pEv.getY() );
				}
		}
		//------------------------------------------------

		public void cmdBeginOp(    Point pPoint ){
				cInWork = true;
				cMyInst.cLayerGroup.saveCurrentLayer();


				Color lColor = cMyInst.cOpProps.getColor();// witchColor() );
				float lCol[]= new float[4];
				lColor.getComponents( lCol );

				//				if(cSliderOpacity!=null)
				//						cSliderOpacity.setValue( lCol[3] );


				beginOp( pPoint );
		}
		//------------------------------------------------

		public void cmdMoveOp(     Point pPoint ){
				if( cInWork == false )
						return;

				moveOp( pPoint );
		}
		//------------------------------------------------
		public void cmdFinalizeOp( Point pPoint ){

				System.out.println( "OpGrafUtil.cmdFinalizeOp 0");

				if( cInWork == false )
						return;

				System.out.println( "OpGrafUtil.cmdFinalizeOp  1");

				finalizeOp( pPoint );	
				cInWork = false;

				System.out.println( "OpGrafUtil.cmdFinalizeOp  2");

				cMyInst.cOpControl.save( this );
				System.out.println( "OpGrafUtil.cmdFinalizeOp  3");

				cMyInst.cCanvas.actualize();
				PPgMain.MajInterface();	 //maj des menu/dialogues s'il a lieu	

				cleanOp();
		}
		//------------------------------------------------

		public void cmdCancelOp(){
				if( cInWork == false )
						return;

				cInWork = false;
				cancelOp();

				cMyInst.cLayerGroup.restoreCurrentLayer();
				cMyInst.cCanvas.actualize();

				cleanOp();
		}

		//------------------------------------------------

		abstract protected void beginOp(    Point pPoint );
		abstract protected void moveOp(     Point pPoint );
		abstract protected void finalizeOp( Point pPoint );
		abstract protected void cancelOp();
		         protected void cleanOp() {;}



		protected JPopupMenu menuOp(MouseEvent pEv){

				return menuColor( pEv );
				// return null;
		}

		final static String sDarkerColor   = "Darker";
		final static String sBrighterColor = "Brighter";
		final static String sTransparent = "Transparent";

		//------------------------------------------------
		protected  JPopupMenu menuColor( MouseEvent pEv) {

				ArrayList<Color> lLastColor = cMyInst.cOpProps.getMemLastColor();
				JPopupMenu lPopMenu = new JPopupMenu();
				JMenuItem lItem;
				PPgMenuGrid lMenuGrid;
		

				lItem = PPgWinUtils.AddMenuItem( lPopMenu, sDarkerColor , this );						
				lItem = PPgWinUtils.AddMenuItem( lPopMenu, sBrighterColor , this );
				
				if( lLastColor.size() > 0 ){
				

						lMenuGrid = new PPgMenuGrid ( "Last colors", lLastColor.size());
						lPopMenu.add( lMenuGrid );


						for( int i = 0; i< lLastColor.size(); i++ ){

								Color lColor = lLastColor.get( i );
								
								lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Col_"+i , this );
								lItem.setOpaque(true);

								lItem.setBackground( lColor );
								lItem.setForeground( lColor );
						}
				}
				
				lMenuGrid = new PPgMenuGrid ( "Basics colors", 13);
				lPopMenu.add( lMenuGrid );
				
				String lStr = "blue";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				Color lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="cyan";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="green";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="yellow";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="orange";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="red";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="magenta";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="pink";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="white";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="lightGray";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );

				lStr ="gray";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="darkGray";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				
				lStr ="black";
				lItem = PPgWinUtils.AddMenuItem( lMenuGrid, "Basic "+lStr , this );
				lItem.setOpaque(true);						
				lColor = PPgWinUtils.GetColor(lStr);
				lItem.setBackground( lColor );
				lItem.setForeground( lColor );
				

				lItem = PPgWinUtils.AddMenuItem( lPopMenu, sTransparent , this );



				lPopMenu.add( lMenuGrid );
				
				
				return lPopMenu;
		}
		//------------------------------------------------
		static public int WitchColor(){

				if( PPgWinUtils.sMemLastButton == 1){
								System.out.println(  "BUTTON1" );
						return 1;
				}	
				return 2;
		}
		//------------------------------------------------
		public int witchColor(){
				return WitchColor();
		}
		//------------------------------------------------

		static protected int     sStrokeSize =3;
		public int       getStrokeSize() { return sStrokeSize; }
		//	protected PPgIntField    cFieldSize = null;
		protected JSpinner  cFieldSize = null;
		
		static protected boolean sFilling =false;
		JCheckBox        cCheckFilling = null;
		
		static protected boolean sRaised =false;
		JCheckBox        cCheckRaised = null;

		static protected double sOpacity = 1;
		JSlider          cSliderOpacity = null;

		boolean cFlagBarOpacity = false;
		boolean cFlagBarThickness = true;

		//------------------------------------------------		
			public void makeToolBar( JToolBar pBar){
					
					makeToolBarBase( pBar);					
			}
		//------------------------------------------------		
		public void makeToolBarBase( JToolBar pBar){ 
				
				System.out.println( "OpGrafEraser:makeToolBar");

				if( cFlagBarThickness ){
						pBar.add( new JLabel( "Thickness" ));
						pBar.add( (cFieldSize = new JSpinner( new SpinnerNumberModel( sStrokeSize, 1, 100, 1 ))));
						
						//	pBar.add( (cFieldSize  = new PPgIntField( "Size", sStrokeSize, PPgField.HORIZONTAL)));
						//		cFieldSize.setColumns( 3 );
						//		cFieldSize.getTextField().addActionListener( this );
						cFieldSize.addChangeListener(  this );
				}

				if( cFlagBarOpacity ){
						pBar.add( new JLabel("Opacity", JLabel.CENTER));
						cSliderOpacity = new JSlider( 0, 1000, (int)(sOpacity*1000) );
						cSliderOpacity.addChangeListener(this);				
						cSliderOpacity.setPaintLabels(false);
						pBar.add( cSliderOpacity ); 
				}
		}		
		//---------------------
		public void stateChanged( ChangeEvent  pEvent){
			
				if( pEvent.getSource() == cFieldSize ){
						sStrokeSize = (Integer) cFieldSize.getValue();
						System.out.println( "Thickness =" +  sStrokeSize+  " " + cFieldSize.getValue() );

				} else 	if( pEvent.getSource() == cSliderOpacity ){
						sOpacity = cSliderOpacity.getValue()/1000.0;
							System.out.println( " Opacity=" + sOpacity +  " " + cSliderOpacity.getValue() );
				}
		}
		//------------------------------------------------		
		public void actionPerformed( ActionEvent pEv ){		
				
				if( pEv.getActionCommand().startsWith( "Col_") ){

						//		System.out.println(  " >>>>>>" + pEv.getActionCommand().substring( 4 ) );
						
						Color lColor = cMyInst.cOpProps.getMemLastColor().get( Integer.parseInt( pEv.getActionCommand().substring( 4 ) ));			
						cMyInst.cOpProps.setColor( lColor );
				} else if( pEv.getActionCommand().startsWith( "Basic ") ){
						System.out.println(  " >>>>>>" + pEv.getActionCommand().substring( 6 ) );
						Color lColor = PPgWinUtils.GetColor( pEv.getActionCommand().substring( 6 ) );	
						cMyInst.cOpProps.setColor( lColor );
				}else if( pEv.getActionCommand().equals( sDarkerColor )){
						cMyInst.cOpProps.setColor( cMyInst.cOpProps.getColor().darker());
}	else if( pEv.getActionCommand().equals( sBrighterColor )){
						cMyInst.cOpProps.setColor( cMyInst.cOpProps.getColor().brighter());
				}	else if( pEv.getActionCommand().equals(	sTransparent )){
								cMyInst.cOpProps.setColor( new Color( 0, 0, 0, 0 ));
			
						}
						
						//			System.out.println("********** actionPerformed for pencil:" + pEv );
				
				//				if( pEv.getSource() == cFieldSize ){
						//		System.out.println("========== actionPerformed for pencil" );
				
				//		if( cFieldSize != null )
				//					sStrokeSize = cFieldSize.getInt();
		}
		//------------------------------------------------		
		public void itemStateChanged(ItemEvent pEv ){
				Object lSource = pEv.getItemSelectable();
				
				if( lSource == cCheckFilling ){
					if( pEv.getStateChange() == ItemEvent.DESELECTED )
								  sFilling = false;
						else
								  sFilling = true;
				} else	if( lSource == cCheckRaised ){
					if( pEv.getStateChange() == ItemEvent.DESELECTED )
								  sRaised = false;
						else
								  sRaised = true;
				}
		}
}
//*************************************************
