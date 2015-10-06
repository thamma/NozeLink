package me.thamma.nozelink.model.entity;

import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EntityFactory {

	public static TerrainEntity parse(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object o = parser.parse(json);
		JSONObject obj = (JSONObject) o;
		switch ((String) obj.get("type")) {
		case "player":
			return new EntityPlayer(new BigDecimal((long) obj.get("id")).intValue());
		default:
			return new EntityNone();
		}
	}

}
