package me.thamma.nozelink.gui.client.game;

import javafx.scene.layout.GridPane;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.model.NozeModel;

public class ClientGamePane extends GridPane {

	public NozeGui main;

	public ClientGamePane(NozeGui gui) {
		super();
		this.main = gui;
		gui.clientView = this;
		this.setPrefWidth(500);
		this.setPrefHeight(320);

		// this.setAlignment(Pos.CENTER);
		// this.setHgap(10);
		// this.setVgap(10);
		// this.setPadding(new Insets(0, 0, 0, 0));
		// this.setId("login");
	}

	public void redraw() {
		if (main.model == null)
			main.model = new NozeModel();
		this.getChildren().clear();
		for (int i = 0; i < main.model.getGrid().length; i++)
			for (int j = 0; j < main.model.getGrid()[i].length; j++)
				this.add(main.model.getAt(i, j).getDisplayPane(), i, j);
	}

}
