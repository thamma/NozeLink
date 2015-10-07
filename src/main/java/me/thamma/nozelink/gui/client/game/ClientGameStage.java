package me.thamma.nozelink.gui.client.game;

import java.io.IOException;

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
			// TODO Check if this works, else restore comment
			KeyDownListener.onKeyDown(mainGui, event);
		});
		mainGui.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					if (mainGui.client != null)
						mainGui.client.kill();
					System.exit(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
