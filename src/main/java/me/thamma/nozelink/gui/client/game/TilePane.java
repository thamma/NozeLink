package me.thamma.nozelink.gui.client.game;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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

		ColorAdjust brightLight = new ColorAdjust(0, 0, .25, 0.25);
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-135.0);
		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(4.0);
		brightLight.setInput(lighting);
		imgView.setEffect(brightLight);
		imgView.fitWidthProperty().bind(this.widthProperty());
		imgView.fitHeightProperty().bind(this.heightProperty());
		this.getChildren().add(imgView);
	}
}
