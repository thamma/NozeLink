package me.thamma.nozelink.model;

import javafx.scene.layout.Pane;
import me.thamma.nozelink.gui.client.game.tiles.Grass;
import me.thamma.nozelink.gui.client.game.tiles.Rock;
import me.thamma.nozelink.gui.client.game.tiles.Woods;

public enum TerrainObject {

	GRASS(OccupyType.FREE), WOODS(OccupyType.FREE), ROCK(OccupyType.BUILT), WATER(OccupyType.WATER);

	private OccupyType occupy;

	TerrainObject(OccupyType occupy) {
		this.occupy = occupy;
	}

	public OccupyType getOccupaion() {
		return this.occupy;
	}

	public Pane getDisplayPane() {
		switch (this) {
		case WOODS:
			return new Woods();
		case ROCK:
			return new Rock();
		case WATER:
			return new Rock();
		default:
			return new Grass();
		}
	}

}
