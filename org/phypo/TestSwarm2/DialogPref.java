package org.phypo.TestSwarm2;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;

import javax.swing.event.ChangeListener;


import java.io.PrintStream;

import java.io.*;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.*;

import org.phypo.PPgGame.PPgGame.*;


//*************************************************
class DoubleSlider extends JSlider {
		
		double cScale;

		public DoubleSlider( double pScale, ChangeListener pListener,  double pMin, double pMax, double pVal, double pMajorTick, double pMinoTick  ){
				super( 0, 100, 10 );
				System.out.println( "min:" + pMin*pScale + " max:" + pMax*pScale + " val:" + pVal*pScale );
				//	super( (int)(pMin*pScale),(int)(pMax*pScale),(int)(pVal*pScale));
				cScale = pScale;

				setPaintTicks( true );
				setPaintTrack(true);
				setPaintLabels(true);
				setMajorTickSpacing( (int)(pMajorTick));
				setMinorTickSpacing( (int)(pMinoTick));
				addChangeListener(pListener);
		}
				
		public void setDoubleValue( double pVal ) {
				super.setValue( (int)(pVal*cScale));
		}

		public double getDoubleValue() {
				return ((double)getValue())/cScale;
		}		
};
//*************************************************
class MySlider extends JSlider {
		

		public MySlider(   ChangeListener pListener, double pMin, double pMax, double pVal, double pMajorTick, double pMinoTick ){
				super( (int)(pMin),(int)(pMax),(int)(pVal));

				setPaintTicks( true );
				setPaintTrack(true);
				setPaintLabels(true);

				setMajorTickSpacing((int)pMajorTick);
				setMinorTickSpacing((int)pMinoTick);
				addChangeListener( pListener);
		}
				
		public void setDoubleValue( double pVal ) {
				super.setValue( (int)(pVal));
		}

		public double getDoubleValue() {
				return ((double)getValue());
		}		
};
//*************************************************

public class DialogPref extends JDialog 
		implements  ActionListener, ChangeListener	{

		JComboBox cCurrentSwarmBox = null; 
		JCheckBox cSqrtCheckBox    = null;


		MySlider  cDistMaxSlider   = null; 
		MySlider  cEvitementSlider = null; 
		DoubleSlider  cSpeedMaxSlider  = null; 

		MySlider cMaxAttractSlider  = null; 

		DoubleSlider cTargetAttractSlider = null;
		DoubleSlider cCohesionAttractSlider = null;
		DoubleSlider cAlignAttractSlider = null;
		DoubleSlider cSeparationRepulsSlider = null;
		DoubleSlider cWantedFpsSlider = null;

		JButton cButtonCancel = null;
		JButton cButtonOk     = null;


		JTabbedPane cTabbedPane = null;

		int cCurrentSwarm = 0;
		int cNbSwarm = 0;
		Swarm [] cTabSwarm;
		String[] cTabString ;




		DialogPref(Swarm[] pTabSwarm, int pNbSwarm ){
				super( FrameGamer.Get().getJFrame(), "Preferences",  false );

				setLocation( 1010, 100 );				

				cTabString = new String[pNbSwarm];
				for( int i=0; i< pNbSwarm; i++ )
						cTabString[i] = pTabSwarm[i].cName;



				cNbSwarm = pNbSwarm;
				cTabSwarm = pTabSwarm;


				getContentPane().setLayout( new BorderLayout() );		

				//==============
				

				//===============
				JPanel lSouth = new JPanel();
				lSouth.setLayout( new GridLayout( 1, 4 ));
				lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));
				
				cButtonOk= new JButton( "Ok" );
				cButtonOk.setActionCommand( "Ok");
				cButtonOk.addActionListener( this );
				lSouth.add( cButtonOk );

				cButtonCancel = new JButton( "Cancel" );
				cButtonCancel.setActionCommand( "Cancel");
				cButtonCancel.addActionListener( this );
				lSouth.add( cButtonCancel );


				//		JPanel lCenter = new JPanel();

				cTabbedPane = new JTabbedPane();
				cTabbedPane.addTab( "Swarm", initPanelSwarm() );
				getContentPane().add( cTabbedPane, BorderLayout.CENTER );

				getContentPane().add( lSouth, BorderLayout.SOUTH );

				
				pack();
				setVisible(true);			
		}	
		//---------------------
		JPanel initPanelSwarm()
		{
				JPanel lPanel = new JPanel();
				lPanel.setFont (new Font("SansSerif", Font.BOLD, 34));
				lPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED));
				lPanel.setLayout( new GridLayout( 20, 0 ));

				lPanel.add( cCurrentSwarmBox = new JComboBox( cTabString ) );
				cCurrentSwarmBox.addActionListener(this);
				
				lPanel.add( cSqrtCheckBox  = new JCheckBox( "Sqrt for distance calcul", cTabSwarm[cCurrentSwarm].cUseSqrt) );
				cSqrtCheckBox.addActionListener(this);


				lPanel.add( new JLabel("Vitesse Max", JLabel.CENTER));
				lPanel.add( cSpeedMaxSlider = new DoubleSlider( 1, this, 0, 50,  cTabSwarm[cCurrentSwarm].cSpeedMax, 50, 5 ));

				lPanel.add( new JLabel("Distance d'evitement", JLabel.CENTER));
				lPanel.add( cEvitementSlider = new MySlider( this, 0, 200, 
																										 Math.sqrt( cTabSwarm[cCurrentSwarm].cSquareDistanceEvit), 50, 10 ));

				lPanel.add( new JLabel("Distance Max groupe", JLabel.CENTER));
				lPanel.add( cDistMaxSlider = new MySlider( this, 0, 500,
																									 Math.sqrt( cTabSwarm[cCurrentSwarm].cSquareDistanceMax), 100, 50 ));


				lPanel.add( new JLabel("MaxDistanceAttract mouse", JLabel.CENTER));
				lPanel.add( cMaxAttractSlider = new MySlider( this, 100, 500,
																											cTabSwarm[cCurrentSwarm].cMaxAttract, 200, 50 ));




				lPanel.add( new JLabel("Target attract Coef", JLabel.CENTER));
				lPanel.add( cTargetAttractSlider = new DoubleSlider( 10000, this, 0.001, 1,
																													cTabSwarm[cCurrentSwarm].cTargetAttract, 100, 50 ));

				lPanel.add( new JLabel("Cohesion attract Coef", JLabel.CENTER));
				lPanel.add( cCohesionAttractSlider = new DoubleSlider( 10000, this, 0.001, 1,
																													cTabSwarm[cCurrentSwarm].cCohesionAttract, 100, 50 ));


				lPanel.add( new JLabel("Align attract Coef", JLabel.CENTER));
				lPanel.add( cAlignAttractSlider = new DoubleSlider( 10000, this, 0.001, 1,
																													cTabSwarm[cCurrentSwarm].cAlignAttract, 100, 50 ));

				lPanel.add( new JLabel("Separation repuls Coef", JLabel.CENTER));
				lPanel.add( cSeparationRepulsSlider = new DoubleSlider( 100, this, 0.01, 5,
																													cTabSwarm[cCurrentSwarm].cSeparationRepuls, 100, 50 ));


				lPanel.add( new JLabel("Wanted FPS", JLabel.CENTER));
				lPanel.add( cWantedFpsSlider  = new DoubleSlider( 1000, this, 0.005, 0.1,
																									World.Get().cWantedFrameDuration	, 100, 30 ));



				return lPanel;
		}
		//---------------------
		void resetFields(){
				cSqrtCheckBox.setSelected( cTabSwarm[cCurrentSwarm].cUseSqrt);	

				cEvitementSlider.setDoubleValue(  Math.sqrt( cTabSwarm[cCurrentSwarm].cSquareDistanceEvit) );
				cDistMaxSlider.setDoubleValue(    Math.sqrt( cTabSwarm[cCurrentSwarm].cSquareDistanceMax) );
				cSpeedMaxSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cSpeedMax );
				cMaxAttractSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cMaxAttract );


				cTargetAttractSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cTargetAttract );
				cCohesionAttractSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cCohesionAttract );
				cAlignAttractSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cAlignAttract );

				cSeparationRepulsSlider.setDoubleValue(   cTabSwarm[cCurrentSwarm].cSeparationRepuls );

				cWantedFpsSlider.setDoubleValue(  World.Get().cWantedFrameDuration  );

		}				
		//---------------------
		public void stateChanged(ChangeEvent  pEvent){

				if( pEvent.getSource() == cEvitementSlider ){
						cTabSwarm[cCurrentSwarm].cSquareDistanceEvit = cEvitementSlider.getDoubleValue()*cEvitementSlider.getDoubleValue();
				}
				if( pEvent.getSource() ==  cDistMaxSlider){
						cTabSwarm[cCurrentSwarm].cSquareDistanceMax = cDistMaxSlider.getDoubleValue()*cDistMaxSlider.getDoubleValue();
				}
				if( pEvent.getSource() == cSpeedMaxSlider ){
						cTabSwarm[cCurrentSwarm].cSpeedMax = cSpeedMaxSlider.getDoubleValue();
				}
				if( pEvent.getSource() == cMaxAttractSlider ){
						cTabSwarm[cCurrentSwarm].cMaxAttract = cMaxAttractSlider.getDoubleValue();
				}


				if( pEvent.getSource() == cTargetAttractSlider ){
						cTabSwarm[cCurrentSwarm].cTargetAttract = cTargetAttractSlider.getDoubleValue();
				}
				if( pEvent.getSource() == cCohesionAttractSlider ){
						cTabSwarm[cCurrentSwarm].cCohesionAttract = cCohesionAttractSlider.getDoubleValue();
				}
				if( pEvent.getSource() == cAlignAttractSlider ){
						cTabSwarm[cCurrentSwarm].cAlignAttract = cAlignAttractSlider.getDoubleValue();
				}

				if( pEvent.getSource() == cSeparationRepulsSlider ){
						cTabSwarm[cCurrentSwarm].cSeparationRepuls = cSeparationRepulsSlider.getDoubleValue();
				}

				if( pEvent.getSource() == cWantedFpsSlider ){
						World.Get().cWantedFrameDuration = cWantedFpsSlider.getDoubleValue();
				}

		}
		//---------------------
		boolean finishOk(){
				return true;
		}
		//---------------------
		boolean finishCancel(){
				return true;
		}
		//---------------------
		public void actionPerformed( ActionEvent pEvent ){

				System.out.println( "actionPerformed " + pEvent.getActionCommand() );

				if( pEvent.getSource() == cCurrentSwarmBox )
						{
								System.out.println( "Change current");
								cCurrentSwarm = cCurrentSwarmBox.getSelectedIndex();
								System.out.println( "Change current = " + cCurrentSwarm );
								resetFields();
						}

				if( pEvent.getSource() == cSqrtCheckBox ){
						cTabSwarm[cCurrentSwarm].cUseSqrt = cSqrtCheckBox.isSelected();
				}
				
				
				
				if( pEvent.getActionCommand().equals("Cancel")) {
						finishCancel();
						//	dispose();
				}			
				else
						if( pEvent.getActionCommand().equals("Ok")) {
								finishOk();
										//	dispose();
						}				
		}
}

//*************************************************
