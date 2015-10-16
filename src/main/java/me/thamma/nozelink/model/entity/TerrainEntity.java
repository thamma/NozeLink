package me.thamma.nozelink.model.entity;

import utils.JSONable;

public abstract class TerrainEntity extends JSONable implements Cloneable {

	public abstract boolean equals(Object object);

	public abstract TerrainEntity clone();
}
