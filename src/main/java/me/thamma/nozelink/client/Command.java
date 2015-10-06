package me.thamma.nozelink.client;

import me.thamma.nozelink.model.JSONable;
import me.thamma.nozelink.model.NozeModel;
import me.thamma.nozelink.server.NozeServer;

public abstract class Command extends JSONable {

	public abstract int getSender();

	public abstract void execute(NozeServer server, NozeModel model);

	public abstract boolean validate(NozeModel model);
}
