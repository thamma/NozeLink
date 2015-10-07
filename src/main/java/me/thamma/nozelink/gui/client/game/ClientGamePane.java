package me.thamma.nozelink.gui.client.game;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.model.NozeModel;

public class ClientGamePane extends GridPane {

	public NozeGui main;

	public ClientGamePane(NozeGui gui) {
		super();
		this.main = gui;
		gui.clientView = this;
		this.setPrefWidth(NozeModel.SIZE * TilePane.SIZE);
		this.setPrefHeight(NozeModel.SIZE * TilePane.SIZE);
	}

	public void redraw() {
		if (main.model == null)
			main.model = new NozeModel();
		Platform.runLater(() -> {
			this.getChildren().clear();
			for (int i = 0; i < main.model.getGrid().length; i++)
				for (int j = 0; j < main.model.getGrid()[i].length; j++)
					this.add(main.model.getAt(i, j).getDisplayPane(), i, j);
		});
	}

}
