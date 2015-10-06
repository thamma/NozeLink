package me.thamma.nozelink.gui.server;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import me.thamma.nozelink.gui.NozeGui;

public class ServerLoginStage extends Scene {

	public ServerLoginStage(NozeGui main) {
		super(new ServerLoginPane(main));
		this.getRoot().setId("login");
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		main.stage.setTitle("NozeLink server setup");
		main.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					if (main.server != null)
						main.server.kill();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
