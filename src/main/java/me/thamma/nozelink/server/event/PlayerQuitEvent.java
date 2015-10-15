package me.thamma.nozelink.server.event;

import org.json.simple.JSONObject;

public class PlayerQuitEvent extends Event {
	@SuppressWarnings("unused")
	private int id;

	public PlayerQuitEvent(int id) {
		this.id = id;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}
