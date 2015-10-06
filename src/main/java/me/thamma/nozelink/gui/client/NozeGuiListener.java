package me.thamma.nozelink.gui.client;

import javafx.scene.input.KeyEvent;
import me.thamma.nozelink.client.commands.MoveCommand;
import me.thamma.nozelink.gui.NozeGui;

public class NozeGuiListener {

	public static void onKeyDown(NozeGui nozeClient, KeyEvent event) {
		int id = nozeClient.client.getId();
		switch (event.getCode()) {
		case UP:
//			nozeClient.client.sendCommand(new MoveCommand(id, 0));
			nozeClient.model.movePlayer(nozeClient.client.getId(), 0);
			break;
		case RIGHT:
//			nozeClient.client.sendCommand(new MoveCommand(id, 1));
			nozeClient.model.movePlayer(nozeClient.client.getId(), 1);
			break;
		case DOWN:
//			nozeClient.client.sendCommand(new MoveCommand(id, 2));
			nozeClient.model.movePlayer(nozeClient.client.getId(), 3);
			break;
		case LEFT:
//			nozeClient.client.sendCommand(new MoveCommand(id, 3));
			nozeClient.model.movePlayer(nozeClient.client.getId(), 4);
			break;
		default:
			break;
		}
	}

}
