package me.thamma.nozelink.model;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.thamma.nozelink.model.entity.EntityNone;
import me.thamma.nozelink.model.entity.EntityPlayer;
import me.thamma.nozelink.model.entity.TerrainEntity;
import utils.JSONable;

public class NozeModel extends JSONable {

	private NozeTile[][] grid;

	public static final int SIZE = 12;

	public NozeModel() {
		this.grid = new NozeTile[SIZE][SIZE];
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				grid[i][j] = new NozeTile(TerrainObject.GRASS);
	}

	public NozeModel(int seed) {
		this.grid = new WorldGen(seed, SIZE).getGrid();
	}

	public NozeTile[][] getGrid() {
		return this.grid;
	}

	public void movePlayer(int id, int direction) {
		Coordinate from = null;
		outermost: for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				if (this.grid[i][j].getEntity() instanceof EntityPlayer)
					if (((EntityPlayer) this.grid[i][j].getEntity()).getId() == id) {
						from = new Coordinate(i, j);
						break outermost;
					}
		if (from == null) {
			return;
		}
		EntityPlayer player = (EntityPlayer) this.getAt(from).getEntity();
		Coordinate to = new Coordinate(from.x, from.y);
		to.moveById(direction);
		this.setEntityAt(to, player);
		this.setEntityAt(from, new EntityNone());
	}

	public NozeModel(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object o = parser.parse(json);
		JSONObject obj = (JSONObject) o;
		this.grid = new NozeTile[NozeModel.SIZE][NozeModel.SIZE];
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				this.grid[i][j] = new NozeTile((String) obj.get("" + i + "," + j));
	}

	public Coordinate randomFreeCoordinate() {
		Random r = new Random();
		Coordinate out = new Coordinate(r.nextInt(SIZE), r.nextInt(SIZE));
		while (!this.getAt(out).getTerrain().getOccupaion().equals(OccupyType.FREE))
			out = new Coordinate(r.nextInt(SIZE), r.nextInt(SIZE));
		return out;
	}

	public void setEntityAt(Coordinate coord, TerrainEntity entity) {
		coord.assertPositive();
		this.grid[coord.x][coord.y].setEntity(entity);
	}

	public NozeTile getAt(Coordinate coord) {
		return this.getAt(coord.x, coord.y);
	}

	public NozeTile getAt(int x, int y) {
		return grid[x % SIZE][y % SIZE];
	}

	public boolean equals(Object object) {
		if (!(object instanceof NozeModel))
			return false;
		NozeModel remote = (NozeModel) object;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (!this.getAt(i, j).getTerrain().equals(remote.getAt(i, j).getTerrain())) {
					return false;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				object.put("" + i + "," + j, (String) this.getAt(i, j).toJSON().toJSONString());
		return object;
	}

}
