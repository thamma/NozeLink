package me.thamma.nozelink.client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.gui.client.login.ClientLoginStage;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.serverutils.Client;
import me.thamma.serverutils.ServerConnection;
import me.thamma.serverutils.handleres.ClientInputHandler;
import me.thamma.serverutils.handleres.ClientServerInputHandler;

public class NozeClient extends Client {

	private NozeGui main;

	public NozeClient(NozeGui gui, String ip, int port) throws UnknownHostException, IOException {
		super(ip, port, false);
		this.main = gui;
	}

	public void sendCommand(Command command) {
		try {
			this.sendMessage(command.toJSON().toJSONString());
		} catch (IOException e) {
			super.kill();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.setHeaderText("Connection Lost!");
			alert.setContentText("Looks like the connection to the server's been lost.\nPlease re-check your connection!");
			alert.showAndWait();
			main.client = null;
			main.stage.setScene(new ClientLoginStage(main));
		}
	}

	@Override
	public ClientInputHandler getClientInputHandler() {
		return new ClientInputHandler() {

			@Override
			public void handle(ServerConnection arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	public ClientServerInputHandler getClientServerInputHandler() {
		return new ClientServerInputHandler() {

			@Override
			public void handle(ServerConnection arg0, String input) {
				Platform.runLater(() -> {
					JSONParser parser = new JSONParser();
					try {
						Object o = parser.parse(input);
						JSONObject object = (JSONObject) o;

						if (((String) object.get("type")).equals("UpdateModelEvent")) {
							main.model = new NozeModel(input);
							main.clientView.redraw();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}

		};
	}

}
