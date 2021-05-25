package org.phypo.PPg.PPgWin;


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



//*************************************************

public class PPgSliderDouble extends JSlider {
		
		double cScale;

		public PPgSliderDouble( double pScale, ChangeListener pListener, double pMin, double pMax, double pVal, double pMajorTick, double pMinorTick  ){
				super( (int)(pMin*pScale), (int)(pMax*pScale), (int)(pScale*pVal) );
				//	super( (int)(pMin*pScale),(int)(pMax*pScale),(int)(pVal*pScale));
				cScale = pScale;
				/*
				if( pMinorTick != 0 )
						setPaintTicks( true ); 
				else
						setPaintTicks( false );

				if(  pMajorTick != 0 )
						setPaintTrack(true);
				else
						setPaintTrack(false);
				*/

				//			setPaintLabels(true);
				setPaintLabels(false);

				
				if(pMinorTick != 0 )
						setMajorTickSpacing( (int)(pMajorTick));
				if(pMinorTick != 0 )
						setMinorTickSpacing( (int)(pMinorTick));


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

