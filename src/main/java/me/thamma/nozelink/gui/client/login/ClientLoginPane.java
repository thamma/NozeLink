package me.thamma.nozelink.gui.client.login;

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
import me.thamma.nozelink.client.NozeClient;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.gui.client.game.ClientGameStage;
import me.thamma.nozelink.gui.server.ServerLoginStage;

public class ClientLoginPane extends GridPane {
	private Button connect;
	private TextField portField;
	private TextField ipField;
	private NozeGui main;

	public ClientLoginPane(NozeGui gui) {
		super();
		this.main = gui;
		this.setPrefWidth(500);
		this.setPrefHeight(320);
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(0, 0, 0, 0));
		this.setId("login");
		Text scenetitle = new Text("NozeLink Login");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.add(scenetitle, 1, 0);
		// main.scene.getStylesheets()
		// .addAll(this.getClass().getResource("../resources/style.css").toExternalForm());
		Label ipLabel = new Label("Ip:");
		this.add(ipLabel, 0, 1);

		ipField = new TextField("localhost");
		this.add(ipField, 1, 1);
		setMaxLength(ipField, 20);
		ipField.setOnKeyPressed((event) -> {
			handler.handle(event);
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});

		Label portLabel = new Label("@");
		this.add(portLabel, 2, 1);

		portField = new TextField("80");
		portField.setPrefWidth(50);
		setMaxLength(portField, 5);
		this.add(portField, 3, 1);
		portField.setOnKeyPressed((event) -> {
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});

		connect = new Button("Connect");
		connect.setOnAction((event) -> {
			buttonClick();
		});
		this.add(connect, 1, 2);
	}

	private void setMaxLength(TextField field, int limit) {
		field.lengthProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() > oldValue.intValue())
				if (field.getText().length() >= limit)
					field.setText(field.getText().substring(0, limit));
		});
	}

	EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
		final KeyCombination combo = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN,
				KeyCombination.SHIFT_DOWN);

		public void handle(KeyEvent t) {
			if (combo.match(t))
				Platform.runLater(() -> {
					main.stage.setScene(new ServerLoginStage(main));
				});
		}
	};

	private void buttonClick() {
		if (connect.isDisabled())
			return;
		final int port;
		try {
			port = Integer.valueOf(portField.getText());
		} catch (NumberFormatException e) {
			return;
		}
		String ip = ipField.getText();
		connect.setText("Connecting . . .");
		ipField.setDisable(true);
		portField.setDisable(true);
		connect.setDisable(true);
		TimerTask run = new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> connect(port, ip));
			}

		};
		Timer t = new Timer();
		t.schedule(run, 40);
	}

	private void connect(int port, String ip) {
		if (main.client == null) {
			try {
				// establish connection
				main.client = new NozeClient(main, ip, port);
				Platform.runLater(() -> {
					main.stage.setScene(new ClientGameStage(main));
					main.clientView.redraw();
				});
			} catch (Exception e) {
				main.client = null;
				connect.setText("Error connecting");
				ipField.setDisable(false);
				portField.setDisable(false);
				e.printStackTrace();
				TimerTask run = new TimerTask() {

					@Override
					public void run() {
						Platform.runLater(() -> {
							connect.setText("Connect");
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
