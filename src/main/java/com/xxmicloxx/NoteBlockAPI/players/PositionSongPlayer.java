package com.xxmicloxx.NoteBlockAPI.players;

import com.xxmicloxx.NoteBlockAPI.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PositionSongPlayer extends SongPlayer {
	
	@Getter
	@Setter
	private Location targetLocation;
	
	public PositionSongPlayer(Song song) {
		super(song);
	}
	
	@Override
	public void playTick(Player p, int tick) {
		if(!p.getWorld().getName().equals(targetLocation.getWorld().getName())) {
			// not in same world
			return;
		}
		byte playerVolume = NoteBlockPlayerMain.getPlayerVolume(p);
		
		for(Layer l : song.getLayerHashMap().values()) {
			Note note = l.getNote(tick);
			if(note == null) {
				continue;
			}
			p.playSound(targetLocation,
					Instrument.getInstrument(note.getInstrument()),
					(l.getVolume() * (int) volume * (int) playerVolume) / 1000000f,
					NotePitch.getPitch(note.getKey() - 33));
		}
	}
}
