/*
 * Copyright 2015 Denison.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package game.snake.gui;

import java.io.IOException;
import java.net.URL;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author Denison
 */
public class Sounds {

    private final Sequencer fundo;
    private final Sequencer comer;
    private final Sequencer bater;
    private final Sequencer fim;
    private final Sequencer novo;

    public Sounds() {
        fundo = newSequencer(Sounds.class.getResource("snd/fundo.mid"), 40);
        comer = newSequencer(Sounds.class.getResource("snd/comer.mid"));
        bater = newSequencer(Sounds.class.getResource("snd/bater.mid"));
        novo = newSequencer(Sounds.class.getResource("snd/novo.mid"));
        fim = newSequencer(Sounds.class.getResource("snd/fim.mid"));
    }

    public void start() {
        fundo.start();
    }

    public void stop() {
        fundo.stop();
    }

    public void comer() {
        comer.setMicrosecondPosition(0);
        comer.start();
    }

    public void bater() {
        bater.setMicrosecondPosition(0);
        bater.start();
    }
    
    public void fimJogo() {
        fim.setMicrosecondPosition(0);
        fim.start();
    }

    public void novoNivel() {
        novo.setMicrosecondPosition(0);
        novo.start();
    }

    private Sequencer newSequencer(URL url, int volume) {
        try {
            Sequence sec = MidiSystem.getSequence(url);
            for (Track track : sec.getTracks()) {
                for (int channel = 0; channel < 10; channel++) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 7, volume), 0));
                }
            }
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(sec);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.open();
            ShortMessage volumeMessage = new ShortMessage();
            volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, 0, 7, 10);
            return sequencer;
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) {
            return null;
        }
    }

    private Sequencer newSequencer(URL url) {
        try {
            Sequence sec = MidiSystem.getSequence(url);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(sec);
            sequencer.open();
            return sequencer;
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) {
            return null;
        }
    }

}
