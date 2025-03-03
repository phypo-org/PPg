import java.io.File;
import java.util.Random;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiEvent;
 
/* Un player MIDI simple
 *
 * 2007 - Sylvain Tournois
 *
 * www.anadoncamille.com
 */


public class MidiPlayer implements MetaEventListener {
 
  public static void main(String[] args) {
    if (args.length < 1) {
      Random random = new Random();
      while (true)
//        playMidiNote(random.nextInt(16), random.nextInt(128), random.nextInt(64) + 64);
        playMidiNote(9, random.nextInt(128), random.nextInt(64) + 64);
    }
    else
      playMidiFile(args[0]);
  }
 
  // Service : Joue une note MIDI
  public static void playMidiNote(int instrument, int note, int velocity) {
    System.out.println("Playing MIDI note (" + instrument + ", " + note + ", " + velocity + ") :");
    MidiPlayer mp = new MidiPlayer();
    mp.playNote(instrument, note, velocity);
    System.out.println("OK");
  }
 
  // Service : Joue un fichier au format MIDI (*.mid)
  public static void playMidiFile(String fileName) {
    System.out.println("Playing MIDI file : " + fileName);
    MidiPlayer mp = new MidiPlayer();
    mp.playFile(fileName);
    System.out.println("OK");
  }
 
  //========================================================
 
  // Constantes
  private static final int END = 47; // Fin de séquence 
  private static final int PROGRAM = 192; // Programmation de piste
  private static final int NOTEON = 144; // Début de note
  private static final int NOTEOFF = 128; // Fin de note
  private static final int TRACK_LENGTH = 2; // Longueur de la piste
  private static final int TRACK_START = 0; // Début de la piste
  private static final int TRACK_END = TRACK_LENGTH - 1; // Fin de la piste
  private static final int PERCUSSION = 9; // Instrument : percussions (chaque note est un des 47 instruments différents)
 
  // Le sequenceur permet de jouer une séquence midi
  private static Sequencer sequencer;
 
  // Constructeur du player
  public MidiPlayer() {
    if (sequencer == null) {
      try {
        sequencer = MidiSystem.getSequencer(); // Séquenceur MIDI par défaut
        sequencer.addMetaEventListener(this); // Réception des événements MIDI
      }
      catch (Exception e) {
        e.printStackTrace();
        sequencer = null;
      }
    }
  }
 
  // Joue une note de piano ou une des 47 percussions, si instrument == 9
  public void playNote(int instrument, int note, int velocity) {
    playSequence(getSequence(instrument, note, velocity));
  }
 
  // Crée une séquence MIDI pour une note/percussion
  public static Sequence getSequence(int instrument, int note, int velocity) {
    Sequence sequence;
    Track track;
    if (instrument == PERCUSSION)
      note = note % 47 + 35;
    try {
      sequence = new Sequence(Sequence.PPQ, 2); // 2 = longueur de la séquence (pour une note c'est suffisant)
      track = sequence.createTrack(); // Création d'une piste
      createEvent(track, PROGRAM, instrument, 1, 0, TRACK_START); // Programmation de la piste
      createEvent(track, NOTEON, instrument, note, velocity, TRACK_START); // Une note
      createEvent(track, NOTEOFF, instrument, note, velocity, TRACK_END); // Fin de la note
      createEvent(track, PROGRAM, instrument, 1, 0, TRACK_END); // Fin de la programmation
    }
    catch (Exception e) {
      e.printStackTrace(); 
      sequence = null;
    }
    return sequence;
  }
 
  // Envoie un message sur une piste
  private static void createEvent(Track track, int type, int chan, int num, int velocity, long tick) throws Exception {
    ShortMessage message = new ShortMessage();
    message.setMessage(type, chan, num, velocity); 
    MidiEvent event = new MidiEvent(message, tick);
    track.add(event);
  }
 
  // Joue un fichier au format MIDI (*.mid)
  public void playFile(String fileName) {
    playSequence(getSequence(fileName));
  }
 
  // Crée une séquence MIDI à partir du fichier fileName
  public static Sequence getSequence(String fileName) {
    File file = new File(fileName);
    if (!file.exists())
      return null;
    Sequence sequence;
    try {
      sequence = MidiSystem.getSequence(file);
    }
    catch (Exception e) {
      e.printStackTrace();
      sequence = null;
    }
    return sequence;
  }
 
  // Joue une séquence
  public void playSequence(Sequence sequence) {
    if ((sequence == null) || (sequencer == null))
      return;
    try {
      sequencer.setSequence(sequence); // Indique au séquenceur quelle séquence il va jouer
      if (!sequencer.isOpen()) // Si le séquenceur n'est pas ouvert
        sequencer.open(); // Ouverture du séquenceur
      sequencer.start(); // Lecture de la séquence
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    while (isPlaying());
  }
 
  // Indique si le player est actif
  public boolean isPlaying() {
    if (sequencer == null)
      return false;
    return sequencer.isOpen();
  }
 
  // Réception des messages, traitement du message de fin de lecture
  public void meta(javax.sound.midi.MetaMessage metaMessage) {
    if (metaMessage.getType() == END) // Si fin de séquence
      sequencer.close(); // Fermeture du séquenceur
  }
 
}