package ModelTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import me.thamma.nozelink.model.Coordinate;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.model.entity.EntityPlayer;
import me.thamma.nozelink.model.entity.TerrainEntity;
import me.thamma.serverutils.Client;
import me.thamma.serverutils.ClientInputHandler;
import me.thamma.serverutils.ClientServerInputHandler;
import me.thamma.serverutils.Server;
import me.thamma.serverutils.ServerClientInputHandler;
import me.thamma.serverutils.ServerConnection;
import me.thamma.serverutils.ServerInputHandler;

public class NozeModelTest {

	/**
	 * Creates an empty NozeModel, builds the JSONString and compares the
	 * original NozeModel to a new instance using the JSONString as constructor
	 * 
	 * @throws ParseException
	 */
	@Test
	public void storeLoadEmptyModel() throws ParseException {
		NozeModel model = new NozeModel();
		String res = model.toJSON().toJSONString();
		NozeModel clone = new NozeModel(res);
		assertTrue("The JSON-recreated model does not match the original one.", clone.equals(model));
	}

	@Test
	public void movePlayer() {
		Coordinate coord = new Coordinate(1, 1);
		NozeModel model = new NozeModel();
		EntityPlayer entity = new EntityPlayer(0);
		model.setEntityAt(coord, entity);
		model.movePlayer(0, 0);
		Coordinate newCoord = new Coordinate(1, 0);
		System.out.println(model.getAt(newCoord));
		assertTrue("Player not found at expected location after two moves",
				model.getAt(newCoord).getEntity() instanceof EntityPlayer);
	}

	@Test
	public void serverTest() throws IOException, InterruptedException {
		new Thread(() -> {
			try {
				Server server = new Server(80, 1) {

					@Override
					public ServerClientInputHandler getServerClientInputHandler() {
						return new ServerClientInputHandler() {

							@Override
							public void handle(Server server, String s, ServerConnection serverconnection) {
								System.out.println("input: " + s);

							}

						};
					}

					@Override
					public ServerInputHandler getServerInputHandler() {
						// TODO Auto-generated method stub
						return null;
					}
				};
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

		System.out.println("HI");
		Client client = new Client("localhost", 80) {

			@Override
			public ClientInputHandler getClientInputHandler() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ClientServerInputHandler getClientServerInputHandler() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		client.pushMessage("hi");
	}

}
