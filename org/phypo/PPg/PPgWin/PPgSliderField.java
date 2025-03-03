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
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;



//*************************************************

public class PPgSliderField extends JPanel implements ChangeListener	{
		
    //		public JPanel   cPanel = null;
    public JLabel   cLabel = null;
    public JSlider  cSlider = null;
    public JSpinner cSpinner = null;

    ChangeListener  cListener = null;

    public PPgSliderField( String pName, int pMin, int pMax, int pVal, ChangeListener pListener ) {

        if( pListener != null )
            cListener  = pListener;
        else
            cListener = this;
				
        //		cPanel = new JPanel(  );
        this.setLayout( new FlowLayout() );
				
        this.add( (cLabel  = new JLabel( pName ) ) );
        this.add( (cSlider = new JSlider( pMin, pMax, pVal )));
        this.add( (cSpinner = new JSpinner( new SpinnerNumberModel( pVal, pMin, pMax, 1 ))));

       

           cSlider.addChangeListener( cListener );
           cSpinner.addChangeListener(cListener );
           }


        public void stateChanged( ChangeEvent  pEvent){
            /* AFAIRE 
               if( pEvent.getSource() ==  cSlider ){
               if( cSpinner.getValue() != cSlider.getValue() )
               cSpinner.setValue( cSlider.getValue() );
		    

               if( cListener != null )
               cListener.stateChanged( new ChangeEvent( this ) );

               } else if( pEvent.getSource() == cSpinner  ){
               if( cSpinner.getValue() != cSlider.getValue() )
               cSlider.setValue(  (Integer) cSpinner.getValue() );
               } 
            */
        }

        public int  getValue()           { return cSlider.getValue(); }
        public void setValue( int pVal ) { cSlider.setValue( pVal ); cSpinner.setValue( pVal); }
    };
    //*************************************************
