package com.xxmicloxx.NoteBlockAPI;

/**
 * Created with IntelliJ IDEA.
 * User: ml
 * Date: 26.03.13
 * Time: 14:17
 */
public class Note {
    private byte instrument;
    private byte key;

    public Note(byte instrument, byte key) {
        this.instrument = instrument;
        this.key = key;
    }

    public byte getInstrument() {
        return instrument;
    }

    public void setInstrument(byte instrument) {
        this.instrument = instrument;
    }

    public byte getKey() {
        return key;
    }

    public void setKey(byte key) {
        this.key = key;
    }
}
