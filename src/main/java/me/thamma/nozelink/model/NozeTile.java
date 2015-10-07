package me.thamma.nozelink.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import me.thamma.nozelink.gui.client.game.TilePane;
import me.thamma.nozelink.model.entity.EntityFactory;
import me.thamma.nozelink.model.entity.EntityNone;
import me.thamma.nozelink.model.entity.EntityPlayer;
import me.thamma.nozelink.model.entity.TerrainEntity;
import utils.JSONable;

public class NozeTile extends JSONable {

	private TerrainObject terrain;
	private TerrainEntity entity;

	public NozeTile() {
		this(TerrainObject.GRASS);
	}

	public NozeTile(TerrainObject terrain) {
		this(terrain, new EntityNone());
	}

	public NozeTile(TerrainObject terrain, TerrainEntity entity) {
		this.terrain = terrain;
		this.entity = entity;
	}

	public NozeTile(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object o = parser.parse(json);
		JSONObject obj = (JSONObject) o;
		this.terrain = TerrainObject.valueOf((String) obj.get("terrain"));
		this.entity = EntityFactory.parse((String) obj.get("entity"));
	}

	public Pane getDisplayPane() {
		Pane pane = this.terrain.getDisplayPane();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.getId() == 1) {
				pane.getChildren().add(new TilePane("/res/coo/player2.png"));
			} else {
				pane.getChildren().add(new TilePane("/res/coo/player1.png"));
			}
		}
		return pane;
	}

	public TerrainObject getTerrain() {
		return this.terrain;
	}

	public TerrainEntity getEntity() {
		return this.entity;
	}

	public void setTerrain(TerrainObject terrain) {
		this.terrain = terrain;
	}

	public void setEntity(TerrainEntity entity) {
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put("terrain", this.terrain.toString());
		object.put("entity", this.entity.toJSON().toJSONString());
		return object;
	}

}
