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
		spawnRidge();
	}

	private final int RIDGESIZE = NozeModel.SIZE;
	private final int LAKESIZE = NozeModel.SIZE * 2;

	private void spawnRidge() {
		int x = random.getNth(seed * (size + RIDGESIZE * 3));
		int y = random.getNth((seed + LAKESIZE * 13) * size + 1);
		Coordinate ridgeAt = new Coordinate(x, y);
		Set<Coordinate> ridge = new HashSet<Coordinate>();
		ridge.add(ridgeAt);
		for (int i = 0; i < RIDGESIZE; i++) {
			ridgeAt = ridgeAt.clone();
			ridgeAt.moveById(random.nextInt(4));
			ridge.add(ridgeAt.clone());
		}
		for (Coordinate coord : ridge) {
			// grid[coord.x][coord.y] = new NozeTile(TerrainObject.WATER);
			if (grid[coord.x][coord.y].getTerrain().equals(TerrainObject.GRASS))
				grid[coord.x][coord.y].setTerrain(TerrainObject.ROCK);
		}
	}

	private void spawnLake() {
		int x = random.getNth(seed * (size + LAKESIZE));
		int y = random.getNth((seed + LAKESIZE) * size + 1);
		Coordinate lakeAt = new Coordinate(x, y);
		Set<Coordinate> lake = new HashSet<Coordinate>();
		lake.add(lakeAt);
		for (int i = 0; i < LAKESIZE; i++) {
			lakeAt.moveById(random.nextInt(4));
			lake.add(lakeAt.clone());
		}
		for (Coordinate coord : lake) {
			// grid[coord.x][coord.y] = new NozeTile(TerrainObject.WATER);
			if (grid[coord.x][coord.y].getTerrain().equals(TerrainObject.GRASS))
				grid[coord.x][coord.y].setTerrain(TerrainObject.WATER);
		}
	}

	@SuppressWarnings("unused")
	private void terraform() {
		//TODO
	}

	public NozeTile[][] getGrid() {
		return this.grid;
	}

}
