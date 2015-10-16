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

	public static final int SIZE = 18;

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

	public Coordinate getPlayerCoordinate(int id) {
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				if (this.getAt(i, j).getEntity() instanceof EntityPlayer)
					if (((EntityPlayer) this.getAt(i, j).getEntity()).getId() == id)
						return new Coordinate(i, j);
		return null;
	}

	public void joinPlayer(int id) {
		if (getPlayerCoordinate(id) != null)
			return;
		Coordinate coord = randomFreeCoordinate();
		setEntityAt(coord, new EntityPlayer(id));
	}

	public void quitPlayer(int id) {
		if (getPlayerCoordinate(id) == null)
			return;
		Coordinate coord = getPlayerCoordinate(id);
		setEntityAt(coord, new EntityNone());
	}

	public void movePlayer(int id, int direction) {
		Coordinate from = getPlayerCoordinate(id);
		if (from == null)
			return;
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

	public void applyDifferences(JSONObject updates) {
		for (Object o : updates.keySet()) {
			if (o instanceof String) {
				String s = (String) o;
				if (s.contains(",")) {
					String[] args = s.split(",");
					int i = Integer.parseInt(args[0]);
					int j = Integer.parseInt(args[1]);
					try {
						this.grid[i][j] = new NozeTile((String) updates.get(s));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
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

	public NozeModel clone() {
		NozeModel out = new NozeModel();
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				out.grid[i][j] = this.getAt(new Coordinate(i, j)).clone();
			}
		}
		return out;
	}

}
