package me.thamma.nozelink.gui.server.login;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import me.thamma.nozelink.gui.NozeGui;

public class ServerLoginStage extends Scene {

	public ServerLoginStage(NozeGui mainGui) {
		super(new ServerLoginPane(mainGui));
		this.getRoot().setId("login");
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		mainGui.stage.setTitle("NozeLink server setup");
		mainGui.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					if (mainGui.server != null)
						mainGui.server.kill();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
