package me.thamma.nozelink.client.commands;

import org.json.simple.JSONObject;

import me.thamma.nozelink.client.Command;
import me.thamma.nozelink.model.Coordinate;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.model.OccupyType;
import me.thamma.nozelink.model.entity.EntityPlayer;
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
		model.movePlayer(this.playerId, this.direction);
		server.sendEvent(new UpdateModelEvent(model));
	}

	@Override
	public boolean validate(NozeModel model) {
		Coordinate from = null;
		outermost: for (int i = 0; i < model.getGrid().length; i++)
			for (int j = 0; j < model.getGrid()[i].length; j++)
				if (model.getGrid()[i][j].getEntity() instanceof EntityPlayer)
					if (((EntityPlayer) model.getGrid()[i][j].getEntity()).getId() == this.playerId) {
						from = new Coordinate(i, j);
						break outermost;
					}
		if (from == null) {
			System.out.println("no player found");
			return false;
		}
		from.moveById(this.direction);
		if (model.getAt(from).getTerrain().getOccupaion().equals(OccupyType.FREE)) {
			return !(model.getAt(from).getEntity() instanceof EntityPlayer);
		}
		return false;
	}

}
