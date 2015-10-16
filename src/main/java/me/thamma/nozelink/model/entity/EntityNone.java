package me.thamma.nozelink.model.entity;

import org.json.simple.JSONObject;

public class EntityNone extends TerrainEntity  {

	public EntityNone() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = new JSONObject();
		out.put("type", "none");
		return out;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EntityNone))
			return false;
		return true;
	}

	@Override
	public TerrainEntity clone() {
		return new EntityNone();
	}
}
