package me.thamma.nozelink.gui.server.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;
import me.thamma.nozelink.gui.NozeGui;

public class ServerViewStage extends Scene {

	public ServerViewStage(NozeGui mainGui) throws IOException {
		super(new ServerViewPane(mainGui));
		this.getRoot().setId("login");
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		mainGui.stage.setTitle("NozeLink server view");
		mainGui.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					if (mainGui.server != null)
						mainGui.server.kill();
					System.exit(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}