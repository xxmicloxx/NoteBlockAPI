package com.xxmicloxx.NoteBlockAPI.events;

import com.xxmicloxx.NoteBlockAPI.players.SongPlayer;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SongDestroyingEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	@Getter
	private SongPlayer songPlayer;
	private boolean cancelled = false;
	
	public SongDestroyingEvent(SongPlayer songPlayer) {
		this.songPlayer = songPlayer;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean bool) {
		cancelled = bool;
	}
}
