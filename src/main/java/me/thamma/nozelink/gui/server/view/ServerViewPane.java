package me.thamma.nozelink.gui.server.view;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import me.thamma.nozelink.gui.server.login.ServerLoginStage;
import me.thamma.nozelink.server.NozeServer;

public class ServerViewPane extends GridPane {
	private Button connect;
	private TextField portField;
	private TextField playerField;
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
						main.server.kill();
						main.server = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
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

		TextArea log = new TextArea("test");
		log.setPrefHeight(200);
		this.add(log, 0, 2, 2, 1);

		this.playerField = new TextField("2");
		this.playerField.setOnKeyPressed((event) -> {
			if (event.getCode().equals(KeyCode.ENTER))
				buttonClick();
		});
		setMaxLength(playerField, 3);

		portField = new TextField("80");
		portField.setPrefWidth(50);
		setMaxLength(portField, 5);

		System.out.println(main == null);
		System.out.println(main.stage == null);
		System.out.println(main.stage.getScene() == null);

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
