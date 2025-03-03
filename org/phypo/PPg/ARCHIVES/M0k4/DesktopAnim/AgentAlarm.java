package com.phipo.PPg.M0k4.DesktopAnim;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;

import com.phipo.PPg.PPgSound.*;

//*************************************************

public class AgentAlarm extends Thread{

	  java.util.List<Sequence>    cInputQueue = Collections.synchronizedList( new ArrayList<Sequence>());

		//	static AgentAlarm sTheAgentAlarm;
		
		public AgentAlarm(){
				//sTheAgentAlarm = this;
		}

		public void run(){	
				//				System.out.println( "AgentAlarm ---> RUN") ;
	 	

				while( true ) {
						try {
								if( cInputQueue.isEmpty() ){
										mySleep( 10000 );
										//										System.out.println( "AgentAlarm ---> Wakeup") ;
								}
								else {
										//										System.out.println( "AgentAlarm ---> PLAY") ;
										Sequence lSeq = cInputQueue.remove(0);
										PPgMidi.Play( lSeq );
								}
						}							
						catch(Exception e){		
								//								System.out.print( "R" );
						}
				}
		}
		//-------------------------
		void mySleep( int pToSleep ){

				try{
						synchronized(  this ) {
								this.wait(  pToSleep );								
						}	
				}
				catch(Exception e){		
								e.printStackTrace();	
				}
		}
		//------------------------------------------------				
		public  void wakeup(){
				synchronized(  this ) {
						//						System.out.println( "AgentAlarm Wakeup") ;
						notify();
				}
		}
		//-------------------------
		//-------------------------
		//-------------------------
		public void put( Sequence pSeq){				 

				//				System.out.println( "AgentAlarm Put") ;
				cInputQueue.add( pSeq );
				wakeup();
		 }
		//-------------------------
		public  void stopCurrentPlay(){				 
				PPgMidi.Stop();
		}

}
//*************************************************
