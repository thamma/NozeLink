package me.thamma.nozelink.gui.client.game;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import me.thamma.nozelink.gui.NozeGui;
import me.thamma.nozelink.model.NozeModel;

public class ClientGamePane extends GridPane {

	public NozeGui main;
	private NozeModel oldModel;
	private Pane[][] grid;

	public ClientGamePane(NozeGui gui) {
		super();
		oldModel = new NozeModel();
		this.grid = new Pane[NozeModel.SIZE][NozeModel.SIZE];
		this.main = gui;
		gui.clientView = this;
		this.setPrefWidth(NozeModel.SIZE * TilePane.SIZE);
		this.setPrefHeight(NozeModel.SIZE * TilePane.SIZE);
	}

	public void redraw() {
		if (main.model == null)
			main.model = new NozeModel();
		Platform.runLater(() -> {
			draw();
		});
	}

	private void draw() {
		for (int i = 0; i < main.model.getGrid().length; i++)
			for (int j = 0; j < main.model.getGrid()[i].length; j++)
				if (!main.model.getAt(i, j).equals(oldModel.getAt(i, j)) || grid[i][j] == null) {
					setAt(i, j, main.model.getAt(i, j).getDisplayPane());
					oldModel.getGrid()[i][j] = main.model.getAt(i, j).clone();
				}
	}

	private void setAt(int i, int j, Pane displayPane) {
		if (grid[i][j] != null) {
			this.getChildren().remove(grid[i][j]);
		}
		grid[i][j] = displayPane;
		this.add(displayPane, i, j);
	}

}
