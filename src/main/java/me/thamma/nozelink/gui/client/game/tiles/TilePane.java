package me.thamma.nozelink.gui.client.game.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TilePane extends Pane {

	public TilePane(String res) {
		super();
		this.resize(50, 50);
		Image img = new Image(res);
		ImageView imgView = new ImageView(img);
		this.getChildren().add(imgView);
	}
}
