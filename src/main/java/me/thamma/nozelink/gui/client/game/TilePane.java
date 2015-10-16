package me.thamma.nozelink.gui.client.game;

import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import me.thamma.nozelink.gui.client.game.tiles.Rock;
import me.thamma.nozelink.model.NozeModel;

public class TilePane extends Pane {

	public static final int SIZE = 600 / NozeModel.SIZE;

	public TilePane(String res) {
		super();
		this.resize(SIZE, SIZE);
		Image img = new Image(res);
		ImageView imgView = new ImageView(img);

		this.setOnMouseEntered((event) -> {
			if (this instanceof Rock)
				Platform.runLater(() -> {
					this.getChildren().remove(imgView);
					ImageView rock = new ImageView(new Image("/res/coo/rock_hover.png"));
					rock.fitWidthProperty().bind(this.widthProperty());
					rock.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(rock);
				});
		});

		this.setOnMouseExited((event) -> {
			if (this instanceof Rock)
				Platform.runLater(() -> {
					this.getChildren().clear();
					this.getChildren().add(imgView);
				});
		});

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
