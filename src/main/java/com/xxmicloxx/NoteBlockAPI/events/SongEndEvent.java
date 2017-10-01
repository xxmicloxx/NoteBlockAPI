package com.xxmicloxx.NoteBlockAPI.events;

import com.xxmicloxx.NoteBlockAPI.players.SongPlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SongEndEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	@Getter
	private SongPlayer songPlayer;
	
	public SongEndEvent(SongPlayer songPlayer) {
		this.songPlayer = songPlayer;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
}
