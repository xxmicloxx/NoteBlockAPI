package com.xxmicloxx.NoteBlockAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NoteBlockPlayerMain extends JavaPlugin {

	public static NoteBlockPlayerMain plugin;
	public HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<String, ArrayList<SongPlayer>>();
    public HashMap<String, Byte> playerVolume = new HashMap<String, Byte>();
	
	public static boolean isReceivingSong(Player p) {
		return ((plugin.playingSongs.get(p.getName()) != null) && (!plugin.playingSongs.get(p.getName()).isEmpty()));
	}
	
	public static void stopPlaying(Player p) {
		if (plugin.playingSongs.get(p.getName()) == null) {
			return;
		}
		for (SongPlayer s : plugin.playingSongs.get(p.getName())) {
			s.removePlayer(p);
		}
	}

    public static void setPlayerVolume(Player p, byte volume) {
        plugin.playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = plugin.playerVolume.get(p.getName());
        if (b == null) {
            b = 100;
            plugin.playerVolume.put(p.getName(), b);
        }
        return b;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        stopPlaying(p);
        Song s = NBSDecoder.parse(new File(getDataFolder().getParentFile().getParentFile(), "music/HesAPirate.nbs"));
        NoteBlockSongPlayer nbsp = new NoteBlockSongPlayer(s);
        nbsp.setAutoDestroy(true);
        nbsp.setNoteBlock(p.getTargetBlock(null, 1000));
        nbsp.addPlayer(p);
        nbsp.setPlaying(true);
        return true;
    }

    @Override
	public void onEnable() {
		plugin = this;
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
}
