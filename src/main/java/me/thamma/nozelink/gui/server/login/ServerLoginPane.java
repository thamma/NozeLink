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
	private TextField playerField;
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
		Label playerLabel = new Label("max Players:");
		this.add(playerLabel, 0, 1);

		this.playerField = new TextField("2");
		this.playerField.setPrefWidth(50);
		this.playerField.setOnKeyPressed((event) -> {
			// handler.handle(event);
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});
		this.add(playerField, 1, 1);
		setMaxLength(playerField, 3);

		Label portLabel = new Label("at Port:");
		this.add(portLabel, 2, 1);

		portField = new TextField("80");
		portField.setPrefWidth(50);
		setMaxLength(portField, 5);
		this.add(portField, 3, 1);

		main.stage.getScene().setOnKeyTyped((event) -> {
			handler.handle(event);
		});

		portField.setOnKeyPressed((event) -> {
			// handler.handle(event);
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});

		connect = new Button("Launch Server");
		connect.setOnAction((event) -> {
			buttonClick();
		});
		this.add(connect, 1, 2, 4, 1);
	}

	EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
		final KeyCombination combo = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN,
				KeyCombination.SHIFT_DOWN);

		public void handle(KeyEvent t) {
			System.out.println("switchting scene");
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
		final int count;
		try {
			port = Integer.valueOf(portField.getText());
			count = Integer.valueOf(playerField.getText());
		} catch (NumberFormatException e) {
			return;
		}
		connect.setText("Launching . . .");
		portField.setDisable(true);
		connect.setDisable(true);
		TimerTask run = new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> connect(port, count));
			}

		};
		Timer t = new Timer();
		t.schedule(run, 40);
	}

	private void connect(int port, int count) {
		if (main.server == null) {
			try {
				Platform.runLater(() -> {
					connect.setDisable(true);
					connect.setText("Launched");
				});
				new Thread(() -> {
					try {
						main.server = new NozeServer(port, count);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				main.stage.setScene(new ServerViewStage(main, port, count));
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
