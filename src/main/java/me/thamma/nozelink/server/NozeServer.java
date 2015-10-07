package me.thamma.nozelink.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.thamma.nozelink.client.commands.MoveCommand;
import me.thamma.nozelink.model.Coordinate;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.model.entity.EntityPlayer;
import me.thamma.nozelink.server.event.Event;
import me.thamma.nozelink.server.event.UpdateModelEvent;
import me.thamma.serverutils.Server;
import me.thamma.serverutils.ServerClientInputHandler;
import me.thamma.serverutils.ServerConnection;
import me.thamma.serverutils.ServerInputHandler;
import me.thamma.serverutils.ServerNewConnectionHandler;

public class NozeServer extends Server {

	private NozeModel model;
	public int port, size;

	public NozeServer(int port, int size) throws IOException {
		super(port, size);
		this.port = port;
		this.size = size;
		if (model == null)
			this.model = new NozeModel();
	}

	public void sendEvent(Event event, int id) {
		this.message(event.toJSON().toJSONString(), id);
	}

	public void sendEvent(Event event) {
		this.message(event.toJSON().toJSONString());
	}

	public void sendEvents(List<Event> event) {
		for (Event e : event) {
			sendEvent(e);
		}
	}

	@Override
	public ServerNewConnectionHandler getServerNewConnectionHandler() {
		return new ServerNewConnectionHandler() {
			@Override
			public void handle(Server server, ServerConnection connection) {
				NozeServer nozeserver = (NozeServer) server;
				if (model == null)
					nozeserver.model = new NozeModel();
				Coordinate coord = nozeserver.model.randomFreeCoordinate();
				nozeserver.model.setEntityAt(coord, new EntityPlayer(connection.getId()));
				System.out.println(model.getAt(coord).getEntity().toJSON());
				nozeserver.sendEvent(new UpdateModelEvent(nozeserver.model));
			}
		};
	}

	@Override
	public ServerInputHandler getServerInputHandler() {
		return new ServerInputHandler() {

			@Override
			public void handle(Server server, String input) {
			}

		};
	}

	@Override
	public ServerClientInputHandler getServerClientInputHandler() {
		return new ServerClientInputHandler() {

			@Override
			public void handle(Server server, String input, ServerConnection user) {
				NozeServer nozeserver = (NozeServer) server;
				JSONParser parser = new JSONParser();
				try {
					Object o = parser.parse(input);
					JSONObject object = (JSONObject) o;

					switch ((String) object.get("type")) {
					case "MoveCommand":
						MoveCommand m = new MoveCommand(new BigDecimal((long) object.get("playerId")).intValue(),
								new BigDecimal((long) object.get("direction")).intValue());
						if (m.validate(model)) {
							m.execute(nozeserver, model);
						}
						break;
					default:
						;
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

}
