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
