package me.thamma.nozelink.gui.client.game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import me.thamma.nozelink.gui.NozeGui;

public class ClientGameStage extends Scene {

	public ClientGameStage(NozeGui mainGui) {
		super(new ClientGamePane(mainGui));
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		mainGui.stage.setTitle("NozeLink");
		this.setOnKeyPressed((event) -> {
			KeyDownListener.onKeyDown(mainGui, event);
		});
		mainGui.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if (mainGui.client != null)
					mainGui.client.kill();
				System.exit(0);
			}
		});
	}

}
