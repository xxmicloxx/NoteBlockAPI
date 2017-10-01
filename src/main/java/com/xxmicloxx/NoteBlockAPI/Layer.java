package com.xxmicloxx.NoteBlockAPI;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Layer {
	
	private HashMap<Integer, Note> hashMap = Maps.newHashMap();
	private byte volume = 100;
	private String name = "";
	
	public void setNote(int tick, Note note) {
		hashMap.put(tick, note);
	}
}