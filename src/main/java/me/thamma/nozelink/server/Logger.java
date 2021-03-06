package me.thamma.nozelink.server;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Logger {

	private TextArea textArea;

	public Logger() {

	}

	public Logger(TextArea textArea) {
		this.textArea = textArea;
	}

	public void info(String message) {
		out("[Info] " + message);
	}

	public void warning(String message) {
		out("[Warning] " + message);
	}

	public void error(String message) {
		out("[Error] " + message);
	}

	private void out(String message) {
		if (textArea == null) {
			System.out.println(message);
		} else {
			Platform.runLater(() -> {
				textArea.setText(message + "\n" + textArea.getText());
			});
		}
	}

}
