package me.thamma.nozelink.model;

import utils.Cantor;

public class Coordinate {

	public int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate clone() {
		return new Coordinate(this.x, this.y);
	}
	
	public void moveById(int id) {
		switch (id) {
		case 0:
			y--;
			break;
		case 1:
			x++;
			break;
		case 2:
			y++;
			break;
		default:
			x--;
			break;
		}
		assertPositive();
	}

	public void assertPositive() {
		while (x < 0)
			x += NozeModel.SIZE;
		while (x >= NozeModel.SIZE)
			x -= NozeModel.SIZE;
		while (y < 0)
			y += NozeModel.SIZE;
		while (y >= NozeModel.SIZE)
			y -= NozeModel.SIZE;
	}

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	public static Coordinate decode(int i) {
		return new Coordinate(Cantor.computeX(i), Cantor.computeY(i));
	}

	public int encode() {
		return encode(this);
	}

	public static int encode(Coordinate coord) {
		return Cantor.compute(coord.x, coord.y);
	}
}
