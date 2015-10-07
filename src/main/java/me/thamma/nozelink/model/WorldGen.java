package me.thamma.nozelink.model;

public class WorldGen {

	private int seed;
	private NozeTile[][] grid;
	private int size;

	public WorldGen(int seed, int size) {
		this.seed = seed;
		this.size = size;
		this.grid = new NozeTile[size][size];
	}

	public int getSeed() {
		return this.seed;
	}

	private void popluate() {

	}

	private void terraform() {

	}

	public NozeTile[][] getGrid() {
		return null;
	}

}
