package me.thamma.nozelink.gui.client.game;

import javafx.scene.input.KeyEvent;
import me.thamma.nozelink.client.commands.MoveCommand;
import me.thamma.nozelink.gui.NozeGui;

public class KeyDownListener {

	public static void onKeyDown(NozeGui nozeClient, KeyEvent event) {
		int id = nozeClient.client.getId();
		switch (event.getCode()) {
		case UP:
			nozeClient.client.sendCommand(new MoveCommand(id, 0));
			break;
		case RIGHT:
			nozeClient.client.sendCommand(new MoveCommand(id, 1));
			break;
		case DOWN:
			nozeClient.client.sendCommand(new MoveCommand(id, 2));
			break;
		case LEFT:
			nozeClient.client.sendCommand(new MoveCommand(id, 3));
			break;
		default:
			break;
		}
	}

}
