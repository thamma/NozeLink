package me.thamma.nozelink.server.event;

import org.json.simple.JSONObject;

import me.thamma.nozelink.model.NozeModel;

public class UpdateModelEvent extends Event {

	private NozeModel model;

	public UpdateModelEvent(NozeModel model) {
		this.model = model;
	}

	public NozeModel getModel() {
		return this.model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = model.toJSON();
		out.put("type", "UpdateModelEvent");
		System.out.println("sending updatemodel event");
		return out;
	}

}
