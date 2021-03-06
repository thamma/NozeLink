package me.thamma.nozelink.gui.server.login;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.gui.client.login.ClientLoginStage;
import me.thamma.nozelink.gui.server.view.ServerViewStage;
import me.thamma.nozelink.server.NozeServer;

public class ServerLoginPane extends GridPane {
	private Button connect;
	private TextField portField;
	private NozeGui main;

	public ServerLoginPane(NozeGui gui) {
		super();
		this.main = gui;
		this.setPrefWidth(500);
		this.setPrefHeight(320);
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(25);
		this.setPadding(new Insets(0, 0, 0, 0));
		this.setId("login");

		Text scenetitle = new Text("NozeLink Server Wizard");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.add(scenetitle, 0, 0, 3, 1);

		Label portLabel = new Label("at Port:");
		this.add(portLabel, 1, 1);

		portField = new TextField("80");
		portField.setPrefWidth(50);
		setMaxLength(portField, 5);
		this.add(portField, 2, 1);

		main.stage.getScene().setOnKeyTyped((event) -> {
			handler.handle(event);
		});

		portField.setOnKeyPressed((event) -> {
			handler.handle(event);
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});

		connect = new Button("Launch Server");
		connect.setOnAction((event) -> {
			buttonClick();
		});
		this.add(connect, 0, 1);
	}

	EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
		final KeyCombination combo = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN,
				KeyCombination.SHIFT_DOWN);

		public void handle(KeyEvent t) {
			if (main.server != null)
				return;
			if (combo.match(t))
				Platform.runLater(() -> {
					main.stage.setScene(new ClientLoginStage(main));
				});
		}
	};

	private void setMaxLength(TextField field, int limit) {
		field.lengthProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() > oldValue.intValue())
				if (field.getText().length() >= limit)
					field.setText(field.getText().substring(0, limit));
		});
	}

	private void buttonClick() {
		if (connect.isDisabled())
			return;
		final int port;
		try {
			port = Integer.valueOf(portField.getText());
		} catch (NumberFormatException e) {
			return;
		}
		connect.setText("Launching . . .");
		portField.setDisable(true);
		connect.setDisable(true);
		TimerTask run = new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> connect(port));
			}

		};
		Timer t = new Timer();
		t.schedule(run, 40);
	}

	private void connect(int port) {
		if (main.server == null) {
			try {
				Platform.runLater(() -> {
					connect.setDisable(true);
					connect.setText("Launched");
				});
				main.server = new NozeServer(port);
				main.stage.setScene(new ServerViewStage(main));
			} catch (Exception e) {
				connect.setText("Error launching");
				portField.setDisable(false);
				e.printStackTrace();
				TimerTask run = new TimerTask() {
					@Override
					public void run() {
						Platform.runLater(() -> {
							connect.setText("Launch Server");
							connect.setDisable(false);
						});
					}

				};
				Timer t = new Timer();
				t.schedule(run, 2000);
			}
		}
	}

}
