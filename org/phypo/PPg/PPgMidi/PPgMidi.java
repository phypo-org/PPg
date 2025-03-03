package com.phipo.PPg.PPgMidi;

import java.io.File;
import java.util.Random;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;


//*************************************************

public class PPgMidi implements MetaEventListener {

  private static final int END = 47; // Fin de séquence 
  private static final int PROGRAM = 192; // Programmation de piste
  private static final int NOTEON = 144; // Début de note
  private static final int NOTEOFF = 128; // Fin de note
  private static final int TRACK_LENGTH = 2; // Longueur de la piste
  private static final int TRACK_START = 0; // Début de la piste
  private static final int TRACK_END = TRACK_LENGTH - 1; // Fin de la piste
  private static final int PERCUSSION = 9; // Instrument : percussions (chaque note est un des 47 instruments différents)


		static PPgMidi sThePPgMidi = null;

		static Sequencer sSequencer = null;

		//------------------------------------------------
		public PPgMidi(){

				sThePPgMidi = this;		

				if( sSequencer == null) {
						try {
								sSequencer = MidiSystem.getSequencer(); 
								sSequencer.addMetaEventListener(this); 
						}
						catch (Exception ex) {
								ex.printStackTrace();
								sSequencer = null;
						}
				}

		}		
		//------------------------------------------------
		public void play( int pInst, int pNote, int pSpeed ){
				play( CreateSequenceNote( pInst, pNote, pSpeed ));
		}
		//------------------------------------------------
		public void play( String pFile ){
				play( CreateSequenceFile( pFile ));
		}
		//------------------------------------------------
		public void play(Sequence lSequence) {
				
				if (( lSequence == null) || ( sSequencer == null))
						return;
				
				
				try {
						sSequencer.setSequence( lSequence); 
						
						if( sSequencer.isOpen() == false) 
								sSequencer.open();
						
						sSequencer.start(); //
				}
				catch (Exception ex) {
						ex.printStackTrace();
				}
		}
		//------------------------------------------------
		
		//		public void waitPlaySequenceEnding(){
		//				while (isPlaying()) System.wait( 1 );
		//		}

 		//------------------------------------------------
		public boolean isPlaying() {
				if( sSequencer == null)
						return false;

				return sSequencer.isOpen();
		}
 		//------------------------------------------------
		public void meta( javax.sound.midi.MetaMessage pMsg ) {
				if( pMsg.getType() == END)
						sSequencer.close();
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
			
		 public static Sequence CreateSequenceNote(int pInst, int pNote, int pSpeed) {

    Sequence lSequence;

    Track lTrack;

    if( pInst == PERCUSSION )
				pNote = pNote % 47 + 35; // L'instrument de percusion

    try {
				lSequence = new Sequence(Sequence.PPQ, 2); 

				lTrack = lSequence.createTrack();

				AddEvent( lTrack, PROGRAM, pInst, 1, 0, TRACK_START); // Programmation de la piste
				AddEvent( lTrack, NOTEON,  pInst, pNote, pSpeed, TRACK_START); // Une note
				AddEvent( lTrack, NOTEOFF, pInst, pNote, pSpeed, TRACK_END); // Fin de la note
				AddEvent( lTrack, PROGRAM, pInst, 1, 0, TRACK_END); // Fin de la programmation
    }
    catch (Exception ex) {
      ex.printStackTrace(); 
      lSequence = null;
    }
    return lSequence;
  }
 		//------------------------------------------------			
  public static Sequence CreateSequenceFile(String pFileName ) {

    File lFile = new File(pFileName);

    if (lFile.exists() == false )
				return null;

    Sequence lSequence;
    try {
				lSequence = MidiSystem.getSequence( lFile);
    }
    catch (Exception ex) {
				ex.printStackTrace();
				lSequence = null;
    }
    return lSequence;
  }
 		//------------------------------------------------			
		static void AddEvent(Track pTrack, int pType, int pChan, int pNum, int pSpeed, long pTick) throws Exception {

				ShortMessage lMsg = new ShortMessage();
				lMsg.setMessage( pType, pChan, pNum, pSpeed); 
				MidiEvent lEvent = new MidiEvent( lMsg, pTick);
				pTrack.add( lEvent);
		}
		
		//------------------------------------------------	
		//------------------------------------------------	
		//------------------------------------------------	
		public static boolean IsPlaying() {
				if( sThePPgMidi== null || sSequencer == null)
						return false;

				return sThePPgMidi.sSequencer.isOpen();
		}
 		//------------------------------------------------
		public static void Play( int pInst, int pNote, int pSpeed ){
				
				if( sThePPgMidi == null )
						new PPgMidi();

				sThePPgMidi.play( pInst, pNote, pSpeed);
		}
		//------------------------------------------------
		public static void Play( Sequence pSeq ) {
				if( sThePPgMidi == null )
						new PPgMidi();
				
				sThePPgMidi.play(pSeq);
		}
		//------------------------------------------------
		public static void Play( String pMidiFile ) {
				
				if( sThePPgMidi == null )
						new PPgMidi();
				
				sThePPgMidi.play( pMidiFile );
		}
		//------------------------------------------------
		
		public static void main(String[] args) {
				
				if (args.length == 1) {
				
						PPgMidi.Play(  args[0] );
				}
		}
}
//*************************************************
