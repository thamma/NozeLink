package me.thamma.nozelink.server.event;

import org.json.simple.JSONObject;

public class PlayerJoinEvent extends Event {
	
	@SuppressWarnings("unused")
	private int id;

	public PlayerJoinEvent(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
