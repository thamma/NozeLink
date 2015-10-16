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
import me.thamma.nozelink.server.event.PlayerJoinEvent;
import me.thamma.nozelink.server.event.SendModelEvent;
import me.thamma.nozelink.server.event.UpdateModelEvent;
import me.thamma.serverutils.Server;
import me.thamma.serverutils.ServerConnection;
import me.thamma.serverutils.handleres.ServerClientDisconnectHandler;
import me.thamma.serverutils.handleres.ServerClientInputHandler;
import me.thamma.serverutils.handleres.ServerInputHandler;
import me.thamma.serverutils.handleres.ServerNewConnectionHandler;

public class NozeServer extends Server {

	public NozeModel model;
	public int port, size;
	public Logger logger;

	public NozeModel oldModel;

	public NozeServer(int port) throws IOException {
		super(port);
		this.logger = new Logger();
		this.port = port;
		if (model == null) {
			this.model = new NozeModel(42);
		}
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
				nozeserver.model.joinPlayer(connection.getId());
				nozeserver.sendEvent(new SendModelEvent(nozeserver.model), connection.getId());
				nozeserver.sendEvent(new UpdateModelEvent(nozeserver));
			}
		};
	}

	@Override
	public ServerInputHandler getServerInputHandler() {
		return new ServerInputHandler() {

			@Override
			public void handle(Server server, String input) {
				// TODO Auto-generated method stub

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

	@Override
	public ServerClientDisconnectHandler getServerClientDisconnectInputHandler() {
		return new ServerClientDisconnectHandler() {

			@Override
			public void handle(Server server, ServerConnection connection) {
				NozeServer nozeserver = (NozeServer) server;

				nozeserver.model.quitPlayer(connection.getId());
				nozeserver.sendEvent(new UpdateModelEvent(nozeserver));
			}
		};
	}

	@SuppressWarnings("unchecked")
	public JSONObject differences() {
		if (oldModel == null) {
			oldModel = model.clone();
			return model.toJSON();
		}
		JSONObject out = new JSONObject();
		for (int i = 0; i < this.oldModel.getGrid().length; i++) {
			for (int j = 0; j < this.oldModel.getGrid()[i].length; j++) {
				if (!oldModel.getAt(i, j).equals(model.getAt(i, j))) {
					out.put("" + i + "," + j, model.getAt(i, j).toJSON().toJSONString());
				}
			}
		}
		oldModel = model.clone();
		return out;
	}

}
