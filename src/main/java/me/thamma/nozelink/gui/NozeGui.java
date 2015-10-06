package me.thamma.nozelink.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.thamma.nozelink.client.NozeClient;
import me.thamma.nozelink.gui.client.game.ClientGamePane;
import me.thamma.nozelink.gui.client.login.ClientLoginStage;
import me.thamma.nozelink.model.NozeModel;
//import me.thamma.nozelinkclient.commands.Command;
//import me.thamma.nozelinkclient.gui.Grass;
//import me.thamma.nozelinkclient.gui.NozePane;
//import me.thamma.nozelinkclient.gui.Player1;
//import me.thamma.nozelinkclient.gui.Player2;
//import me.thamma.nozelinkclient.gui.Rock;
//import me.thamma.nozelinkclient.gui.Woods;
//import me.thamma.nozelinkserver.NozeServer;
import me.thamma.nozelink.server.NozeServer;

public class NozeGui extends Application {

	public NozeClient client;
	public NozeModel model;
	public Stage stage;
	public ClientGamePane clientView;
	public NozeServer server;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setScene(new ClientLoginStage(this));
		stage.getIcons().add(new Image("/res/icon.png"));
		stage.show();
	}

	public static void main(String... args) throws Exception {
		if (args.length == 0) {
			launch();
		} else {
			// System.out.println("Launching server");
			// Scanner sc = new Scanner(System.in);
			// System.out.println("Please enter your preffed port");
			// int port = sc.nextInt();
			new NozeServer(80, 1);
		}
	}

}
