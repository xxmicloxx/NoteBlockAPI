package com.xxmicloxx.NoteBlockAPI;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;

@Getter
public class Song {
	
	private HashMap<Integer, Layer> layerHashMap = Maps.newHashMap();
	
	private short songHeight, length;
	private String title;
	private File path;
	private String author, description;
	private float speed, delay;
	
	public Song(Song otherSong) {
		this.speed = otherSong.getSpeed();
		delay = 20 / speed;
		this.layerHashMap = otherSong.getLayerHashMap();
		this.songHeight = otherSong.getSongHeight();
		this.length = otherSong.getLength();
		this.title = otherSong.getTitle();
		this.author = otherSong.getAuthor();
		this.description = otherSong.getDescription();
		this.path = otherSong.getPath();
	}
	
	public Song(float speed, HashMap<Integer, Layer> layerHashMap,
	            short songHeight, final short length, String title, String author,
	            String description, File path) {
		this.speed = speed;
		delay = 20 / speed;
		this.layerHashMap = layerHashMap;
		this.songHeight = songHeight;
		this.length = length;
		this.title = title;
		this.author = author;
		this.description = description;
		this.path = path;
	}
}
