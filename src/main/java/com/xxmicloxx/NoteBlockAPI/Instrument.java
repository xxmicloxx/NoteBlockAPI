package com.xxmicloxx.NoteBlockAPI;

import org.bukkit.Sound;

public class Instrument {

    public static Sound getInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return Sound.BLOCK_NOTE_HARP;
            case 1:
                return Sound.BLOCK_NOTE_BASS;
            case 2:
                return Sound.BLOCK_NOTE_BASEDRUM;
            case 3:
                return Sound.BLOCK_NOTE_SNARE;
            case 4:
                return Sound.BLOCK_NOTE_HAT;
            case 5:
                return Sound.BLOCK_NOTE_FLUTE;
            case 6:
                return Sound.BLOCK_NOTE_BELL;
            case 7:
                return Sound.BLOCK_NOTE_GUITAR;
            case 8:
                return Sound.BLOCK_NOTE_CHIME;
            case 9:
                return Sound.BLOCK_NOTE_XYLOPHONE;
            default:
                return Sound.BLOCK_NOTE_HARP;
        }
    }

    public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return org.bukkit.Instrument.PIANO;
            case 1:
                return org.bukkit.Instrument.BASS_GUITAR;
            case 2:
                return org.bukkit.Instrument.BASS_DRUM;
            case 3:
                return org.bukkit.Instrument.SNARE_DRUM;
            case 4:
                return org.bukkit.Instrument.STICKS;
            case 5:
                return org.bukkit.Instrument.FLUTE;
            case 6:
                return org.bukkit.Instrument.BELL;
            case 7:
                return org.bukkit.Instrument.GUITAR;
            case 8:
                return org.bukkit.Instrument.CHIME;
            case 9:
                return org.bukkit.Instrument.XYLOPHONE;
            default:
                return org.bukkit.Instrument.PIANO;
        }
    }
}
