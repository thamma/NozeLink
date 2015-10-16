package me.thamma.nozelink.server.event;

import org.json.simple.JSONObject;

import me.thamma.nozelink.server.NozeServer;

public class UpdateModelEvent extends Event {

	private NozeServer server;
	public UpdateModelEvent(NozeServer server) {
		this.server = server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = server.differences();
		out.put("type", "UpdateModelEvent");
		return out;
	}

}
