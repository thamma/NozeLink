package me.thamma.nozelink.client.commands;

import org.json.simple.JSONObject;

import me.thamma.nozelink.client.Command;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.server.NozeServer;
import me.thamma.nozelink.server.event.UpdateModelEvent;

public class MoveCommand extends Command {

	private int playerId;
	private int direction;

	public MoveCommand(int playerId, int direction) {
		this.playerId = playerId;
		this.direction = direction;
	}

	@Override
	public int getSender() {
		return playerId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON() {
		JSONObject out = new JSONObject();
		out.put("type", "MoveCommand");
		out.put("playerId", playerId);
		out.put("direction", direction);
		return out;
	}

	@Override
	public void execute(NozeServer server, NozeModel model) {
		model.movePlayer(this.direction, this.playerId);
		server.sendEvent(new UpdateModelEvent(model));
	}

	@Override
	public boolean validate(NozeServer server, NozeModel model) {
		return true;
	}

}
