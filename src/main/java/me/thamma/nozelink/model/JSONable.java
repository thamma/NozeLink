package me.thamma.nozelink.model;

import org.json.simple.JSONObject;

public abstract class JSONable {
	public abstract JSONObject toJSON();

	@Override
	public String toString() {
		return this.toJSON().toJSONString();
	}
}
