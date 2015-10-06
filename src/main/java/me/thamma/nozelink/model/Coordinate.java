package me.thamma.nozelink.model;

public class Coordinate {

	public int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
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
		if (x < 0)
			x += NozeModel.WIDTH;
		if (x >= NozeModel.WIDTH)
			x -= NozeModel.WIDTH;
		if (y < 0)
			y += NozeModel.HEIGHT;
		if (y >= NozeModel.HEIGHT)
			y -= NozeModel.HEIGHT;
	}

	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

}
