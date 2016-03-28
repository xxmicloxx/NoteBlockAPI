package com.xxmicloxx.NoteBlockAPI;

import org.bukkit.Sound;

public class Instrument {

    public static Sound getInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return Sound.NOTE_PIANO;
            case 1:
                return Sound.NOTE_BASS_GUITAR;
            case 2:
                return Sound.NOTE_BASS_DRUM;
            case 3:
                return Sound.NOTE_SNARE_DRUM;
            case 4:
                return Sound.NOTE_STICKS;
            case 5:
                return Sound.NOTE_PLING;
            default:
                return Sound.NOTE_PIANO;
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
                return org.bukkit.Instrument.PLING;
            default:
                return org.bukkit.Instrument.PIANO;
        }
    }
}
