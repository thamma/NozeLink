package me.thamma.nozelink.gui.client.login;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import me.thamma.nozelink.gui.NozeGui;

public class ClientLoginStage extends Scene {

	public ClientLoginStage(NozeGui mainGui) {
		super(new ClientLoginPane(mainGui));
		this.getRoot().setId("login");
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		mainGui.stage.setTitle("NozeLink login");
		mainGui.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.exit(0);
			}
		});
	}

}
