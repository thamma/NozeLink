package me.thamma.nozelink.server.event;

import org.json.simple.JSONObject;

import me.thamma.nozelink.model.NozeModel;

public class SendModelEvent extends Event {

	private NozeModel model;

	public SendModelEvent(NozeModel model) {
		this.model = model;
	}

	public NozeModel getModel() {
		return this.model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = model.toJSON();
		out.put("type", "SendModelEvent");
		return out;
	}

}
