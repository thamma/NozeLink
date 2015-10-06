package me.thamma.nozelink.gui.client.game;

import javafx.scene.Scene;
import me.thamma.nozelink.gui.NozeGui;

public class ClientGameStage extends Scene {

	public ClientGameStage(NozeGui main) {
		super(new ClientGamePane(main));
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		main.stage.setTitle("NozeLink");
	}

}
