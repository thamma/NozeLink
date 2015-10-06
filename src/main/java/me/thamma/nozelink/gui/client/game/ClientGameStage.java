package me.thamma.nozelink.gui.client.game;

import javafx.scene.Scene;
import me.thamma.nozelink.client.commands.MoveCommand;
import me.thamma.nozelink.gui.NozeGui;

public class ClientGameStage extends Scene {

	public ClientGameStage(NozeGui main) {
		super(new ClientGamePane(main));
		this.getStylesheets().addAll(this.getClass().getResource("/res/style.css").toExternalForm());
		main.stage.setTitle("NozeLink");
		this.setOnKeyPressed((event) -> {
			switch (event.getCode()) {
			case UP:
				main.client.sendCommand(new MoveCommand(main.client.getId(), 0));
				break;
			case RIGHT:
				main.client.sendCommand(new MoveCommand(main.client.getId(), 1));
				break;
			case DOWN:
				main.client.sendCommand(new MoveCommand(main.client.getId(), 2));
				break;
			case LEFT:
				main.client.sendCommand(new MoveCommand(main.client.getId(), 3));
				break;
			default:
				;
			}
		});
	}

}
