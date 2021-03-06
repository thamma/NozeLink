package ModelTests;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import me.thamma.nozelink.model.Coordinate;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.model.TerrainObject;
import me.thamma.nozelink.model.entity.EntityPlayer;

public class NozeModelTest {

	/**
	 * Creates an empty NozeModel, builds the JSONString and compares the
	 * original NozeModel to a new instance using the JSONString as constructor
	 * 
	 * @throws ParseException
	 */
	@Test
	public void storeLoadEmptyModel() throws ParseException {
		NozeModel model = new NozeModel();
		String res = model.toJSON().toJSONString();
		NozeModel clone = new NozeModel(res);
		assertTrue("The JSON-recreated model does not match the original one.", clone.equals(model));
	}

	@Test
	public void loadMultiplePlayers() throws ParseException {
		NozeModel model = new NozeModel();
		model.setEntityAt(new Coordinate(0, 3), new EntityPlayer(0));
		model.setEntityAt(new Coordinate(0, 4), new EntityPlayer(1));
		NozeModel clone = new NozeModel(model.toJSON().toJSONString());
		assertTrue("Multiple players could not be properly stored on a model", clone.equals(model));
	}

	@Test
	public void movePlayer() {
		Coordinate coord = new Coordinate(1, 1);
		NozeModel model = new NozeModel();
		EntityPlayer entity = new EntityPlayer(0);
		model.setEntityAt(coord, entity);
		model.movePlayer(0, 0);
		model.movePlayer(0, 3);
		model.movePlayer(0, 1);
		Coordinate newCoord = new Coordinate(1, 0);
		System.out.println(model.getAt(newCoord));
		assertTrue("Player not found at expected location after two moves",
				model.getAt(newCoord).getEntity() instanceof EntityPlayer);
	}

	@Test
	public void initBoardAssumeWaterLake() {
		int count = 0;
		NozeModel model = new NozeModel(42);
		for (int i = 0; i < model.getGrid().length; i++) {
			for (int j = 0; j < model.getGrid()[i].length; j++) {
				if (model.getAt(i, j).getTerrain().equals(TerrainObject.WATER)) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

	@Test
	public void initBoardNonNull() {
		NozeModel model = new NozeModel();
		model.getAt(0, 0);
	}

	@SuppressWarnings("unchecked")
	public JSONObject differences(NozeModel oldModel, NozeModel model) {
		model.setEntityAt(new Coordinate(3, 3), new EntityPlayer(0));
		JSONObject out = new JSONObject();
		for (int i = 0; i < oldModel.getGrid().length; i++) {
			for (int j = 0; j < oldModel.getGrid()[i].length; j++) {
				if (!oldModel.getAt(i, j).equals(model.getAt(i, j))) {
					out.put("" + i + "," + j, model.getAt(i, j).toJSON().toJSONString());
				}
			}
		}
		oldModel = model;
		return out;
	}

}
