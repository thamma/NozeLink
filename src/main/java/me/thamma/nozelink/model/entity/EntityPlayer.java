package me.thamma.nozelink.model.entity;

import org.json.simple.JSONObject;

public class EntityPlayer extends TerrainEntity {

	private int id;

	public EntityPlayer(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = new JSONObject();
		out.put("type", "player");
		out.put("id", this.id);
		return out;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EntityPlayer))
			return false;
		EntityPlayer player = (EntityPlayer) object;
		if (player.getId() != this.getId())
			return false;
		return true;
	}

	@Override
	public TerrainEntity clone() {
		return new EntityPlayer(this.id);
	}

}
