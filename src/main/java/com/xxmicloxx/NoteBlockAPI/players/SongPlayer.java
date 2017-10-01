package com.xxmicloxx.NoteBlockAPI.players;

import com.google.common.collect.Lists;
import com.xxmicloxx.NoteBlockAPI.enums.FadeType;
import com.xxmicloxx.NoteBlockAPI.Interpolator;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import com.xxmicloxx.NoteBlockAPI.objects.Song;
import com.xxmicloxx.NoteBlockAPI.events.SongDestroyingEvent;
import com.xxmicloxx.NoteBlockAPI.events.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.events.SongStoppedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public abstract class SongPlayer {
	
	@Setter(AccessLevel.NONE)
	protected Song song;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected boolean playing = false;
	
	protected short tick = -1;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected ArrayList<String> playerList = new ArrayList<String>();
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected boolean autoDestroy = false, destroyed = false;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected Thread playerThread;
	
	protected byte fadeTarget = 100, volume = 100, fadeStart = volume;
	protected int fadeDuration = 60, fadeDone = 0;
	
	protected FadeType fadeType = FadeType.FADE_LINEAR;
	
	public SongPlayer(Song song) {
		this.song = song;
		createThread();
	}
	
	/**
	 * a list of players which are hearing the song.
	 * @return a list with player names.
	 */
	public List<String> getPlayerList() {
		return Collections.unmodifiableList(playerList);
	}
	
	/**
	 * add a player to the song.
	 * @param player means a player.
	 */
	public void addPlayer(Player player) {
		synchronized(this) {
			if(!playerList.contains(player.getName())) {
				playerList.add(player.getName());
				ArrayList<SongPlayer> songs = NoteBlockPlayerMain.plugin.playingSongs
						.get(player.getName());
				if(songs == null) {
					songs = Lists.newArrayList();
				}
				songs.add(this);
				NoteBlockPlayerMain.plugin.playingSongs.put(player.getName(), songs);
			}
		}
	}
	
	/**
	 * remove a player from this song!
	 *
	 * @param player means a player.
	 */
	public void removePlayer(Player player) {
		synchronized(this) {
			playerList.remove(player.getName());
			if(NoteBlockPlayerMain.plugin.playingSongs.get(player.getName()) == null) {
				return;
			}
			ArrayList<SongPlayer> songs = Lists.newArrayList(
					NoteBlockPlayerMain.plugin.playingSongs.get(player.getName()));
			songs.remove(this);
			NoteBlockPlayerMain.plugin.playingSongs.put(player.getName(), songs);
			if(playerList.isEmpty() && autoDestroy) {
				SongEndEvent event = new SongEndEvent(this);
				Bukkit.getPluginManager().callEvent(event);
				destroy();
			}
		}
	}
	
	protected void calculateFade() {
		if(fadeDone == fadeDuration) {
			return; // no fade today
		}
		double targetVolume = Interpolator.interpLinear(new double[]{0, fadeStart, fadeDuration, fadeTarget}, fadeDone);
		setVolume((byte) targetVolume);
		fadeDone++;
	}
	
	protected void createThread() {
		playerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!destroyed) {
					long startTime = System.currentTimeMillis();
					synchronized(SongPlayer.this) {
						if(playing) {
							calculateFade();
							tick++;
							if(tick > song.getLength()) {
								playing = false;
								tick = -1;
								SongEndEvent event = new SongEndEvent(SongPlayer.this);
								Bukkit.getPluginManager().callEvent(event);
								if(autoDestroy) {
									destroy();
									return;
								}
							}
							for(String s : playerList) {
								Player p = Bukkit.getPlayerExact(s);
								if(p == null) {
									// offline...
									continue;
								}
								playTick(p, tick);
							}
						}
					}
					long duration = System.currentTimeMillis() - startTime;
					float delayMillis = song.getDelay() * 50;
					if(duration < delayMillis) {
						try {
							Thread.sleep((long) (delayMillis - duration));
						} catch (InterruptedException e) {
							// do nothing
						}
					}
				}
			}
		});
		playerThread.setPriority(Thread.MAX_PRIORITY);
		playerThread.start();
	}
	
	public void destroy() {
		synchronized(this) {
			SongDestroyingEvent event = new SongDestroyingEvent(this);
			Bukkit.getPluginManager().callEvent(event);
			//Bukkit.getScheduler().cancelTask(threadId);
			if(event.isCancelled()) {
				return;
			}
			destroyed = true;
			playing = false;
			setTick((short) -1);
		}
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
		if(!playing) {
			SongStoppedEvent event = new SongStoppedEvent(this);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	
	public boolean getAutoDestroy() {
		synchronized(this) {
			return autoDestroy;
		}
	}
	
	public void setAutoDestroy(boolean value) {
		synchronized(this) {
			autoDestroy = value;
		}
	}
	
	public abstract void playTick(Player p, int tick);
}
