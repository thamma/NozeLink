package me.thamma.nozelink.gui.client.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TilePane extends Pane {

	public static final int SIZE = 64;

	public TilePane(String res) {
		super();
		this.resize(SIZE, SIZE);
		Image img = new Image(res);
		ImageView imgView = new ImageView(img);
		imgView.fitWidthProperty().bind(this.widthProperty());
		imgView.fitHeightProperty().bind(this.heightProperty());
		this.getChildren().add(imgView);
	}
}
