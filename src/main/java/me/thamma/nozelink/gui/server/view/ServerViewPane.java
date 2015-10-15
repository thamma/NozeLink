package me.thamma.nozelink.gui.server.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.gui.server.login.ServerLoginStage;
import me.thamma.nozelink.server.Logger;

public class ServerViewPane extends GridPane {

	private NozeGui main;

	public ServerViewPane(NozeGui gui) {
		super();
		this.main = gui;
		this.setPrefWidth(600);
		this.setPrefHeight(400);
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(25);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setId("login");

		Text scenetitle = new Text("NozeLink Server manager");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.add(scenetitle, 0, 0, 1, 2);

		Button shutdownButton = new Button("Shutdown");
		shutdownButton.setOnAction((event) -> {

			Platform.runLater(() -> {
				main.stage.setScene(new ServerLoginStage(main));
				if (main.server != null) {
					try {
						System.out.println("shutting down");
						main.server.kill();
						main.server = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		});
		
		this.add(shutdownButton, 1, 0, 1, 2);

		Label portLabel = new Label("" + 80);
		this.add(portLabel, 2, 0);
		Label countLabel = new Label("" + 2);
		this.add(countLabel, 2, 1);

		TextArea log = new TextArea("Hallo\nWelt.");
		log.setFont(Font.font("Courier New", 14));
		log.setEditable(false);
		log.setPrefHeight(200);
		
		this.add(log, 0, 2, 2, 1);
		registerLogger(log);

	}
	private void registerLogger(TextArea log) {
		main.server.logger = new Logger(log);
	}

}
