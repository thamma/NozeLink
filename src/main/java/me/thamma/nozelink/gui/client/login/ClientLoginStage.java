package me.thamma.nozelink.gui.client.login;

import javafx.scene.Scene;
import me.thamma.nozelink.gui.NozeGui;

public class ClientLoginStage extends Scene {

	public ClientLoginStage(NozeGui main) {
		super(new ClientLoginPane(main));
		this.getRoot().setId("login");
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		main.stage.setTitle("NozeLink login");
	}

}
