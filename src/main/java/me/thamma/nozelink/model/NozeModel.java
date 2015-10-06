package me.thamma.nozelink.model;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.thamma.nozelink.model.entity.EntityNone;
import me.thamma.nozelink.model.entity.EntityPlayer;
import me.thamma.nozelink.model.entity.TerrainEntity;

public class NozeModel extends JSONable {

	private NozeTile[][] grid;
	int seed;

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;

	public NozeModel(int seed) {
		this.seed = seed;
		this.grid = defaultGrid();
	}

	public NozeModel() {
		this(42);
	}

	public NozeTile[][] getGrid() {
		return this.grid;
	}

	public void movePlayer(int direction, int id) {
		Coordinate from = null;
		outermost: for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				if (this.grid[i][j].getEntity() instanceof EntityPlayer)
					if (((EntityPlayer) this.grid[i][j].getEntity()).getId() == id) {
						from = new Coordinate(i, j);
						break outermost;
					}
		if (from == null) {
			System.out.println("No player found to perform move: id == " + id);
			return;
		}
		EntityPlayer player = (EntityPlayer) this.getAt(from).getEntity();
		Coordinate to = new Coordinate(from.x, from.y);
		to.moveById(id);
		this.setEntityAt(to, player);
		this.setEntityAt(from, new EntityNone());
		System.out.println("from: " + from);
		System.out.println("to: " + to);
	}

	public NozeModel(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		Object o = parser.parse(json);
		JSONObject obj = (JSONObject) o;
		this.grid = new NozeTile[NozeModel.WIDTH][NozeModel.HEIGHT];
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				this.grid[i][j] = new NozeTile((String) obj.get("" + i + "," + j));
	}

	public Coordinate randomFreeCoordinate() {
		Random r = new Random();
		Coordinate out = new Coordinate(r.nextInt(WIDTH), r.nextInt(HEIGHT));
		while (!this.getAt(out).getTerrain().getOccupaion().equals(OccupyType.FREE))
			out = new Coordinate(r.nextInt(WIDTH), r.nextInt(HEIGHT));
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
		return grid[x % WIDTH][y % HEIGHT];
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

	private NozeTile[][] defaultGrid() {
		Random r = new Random(seed);
		final int[] terrainRandom = { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 3, 3, 3, 2 };
		NozeTile[][] out = new NozeTile[WIDTH][HEIGHT];
		for (int i = 0; i < out.length; i++) {
			for (int j = 0; j < out[i].length; j++) {
				TerrainObject terrain = TerrainObject.values()[terrainRandom[r.nextInt(terrainRandom.length)]];
				NozeTile tile = new NozeTile(terrain);
				out[i][j] = tile;
			}
		}
		return out;
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
