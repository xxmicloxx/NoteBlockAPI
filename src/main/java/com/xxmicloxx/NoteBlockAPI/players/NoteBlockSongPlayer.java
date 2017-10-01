package com.xxmicloxx.NoteBlockAPI.players;

import com.xxmicloxx.NoteBlockAPI.*;
import com.xxmicloxx.NoteBlockAPI.enums.NotePitch;
import com.xxmicloxx.NoteBlockAPI.objects.Layer;
import com.xxmicloxx.NoteBlockAPI.objects.Note;
import com.xxmicloxx.NoteBlockAPI.objects.Song;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: ml
 * Date: 07.12.13
 * Time: 12:56
 */
public class NoteBlockSongPlayer extends SongPlayer {
    
    @Getter
    @Setter
    private Block noteBlock;

    public NoteBlockSongPlayer(Song song) {
        super(song);
    }

    @Override
    public void playTick(Player p, int tick) {
        if (noteBlock.getType() != Material.NOTE_BLOCK) {
            return;
        }
        if (!p.getWorld().getName().equals(noteBlock.getWorld().getName())) {
            // not in same world
            return;
        }
        byte playerVolume = NoteBlockPlayerMain.getPlayerVolume(p);

        for (Layer l : song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note == null) {
                continue;
            }
            p.playNote(noteBlock.getLocation(), Instrument.getBukkitInstrument(note.getInstrument()),
                    new org.bukkit.Note(note.getKey() - 33));
            p.playSound(noteBlock.getLocation(),
                    Instrument.getInstrument(note.getInstrument()),
                    (l.getVolume() * (int) volume * (int) playerVolume) / 1000000f,
                    NotePitch.getPitch(note.getKey() - 33));
        }
    }
}
