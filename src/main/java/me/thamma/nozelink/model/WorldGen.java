package me.thamma.nozelink.model;

import java.util.HashSet;
import java.util.Set;

import utils.OrderRandom;

public class WorldGen {

	private int seed;
	private NozeTile[][] grid;
	private int size;
	private OrderRandom random;

	public WorldGen(int seed, int size) {
		this.random = new OrderRandom(seed, size);
		this.seed = seed;
		this.size = size;
		this.grid = new NozeTile[size][size];
		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				grid[i][j] = new NozeTile(TerrainObject.GRASS);

		this.popluate();
	}

	public int getSeed() {
		return this.seed;
	}

	private void popluate() {
		spawnLake();
		// for (int i = 0; i < this.grid.length; i++) {
		// for (int j = 0; j < this.grid[i].length; j++) {
		// if (grid[i][j] != null &&
		// !grid[i][j].getTerrain().equals(TerrainObject.WATER)) {
		// grid[i][j] = new NozeTile(TerrainObject.GRASS);
		// }
		// }
		// }
	}

	private final int LAKESIZE = 20;

	private void spawnLake() {
		int x = random.getNth(seed * (size + LAKESIZE));
		int y = random.getNth((seed +LAKESIZE)* size + 1);
		Coordinate lakeAt = new Coordinate(x, y);
		Set<Coordinate> lake = new HashSet<Coordinate>();
		lake.add(lakeAt);
		for (int i = 0; i < LAKESIZE; i++) {
			Coordinate newLake = lakeAt.clone();
			lakeAt.moveById(random.nextInt(4));
			System.out.println(lakeAt);
			lake.add(lakeAt.clone());
		}
		for (Coordinate coord : lake) {
			// grid[coord.x][coord.y] = new NozeTile(TerrainObject.WATER);
			grid[coord.x][coord.y].setTerrain(TerrainObject.WATER);
		}
	}

	private void terraform() {

	}

	public NozeTile[][] getGrid() {
		return this.grid;
	}

}
