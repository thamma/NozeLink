package utils;

import java.util.Random;

import me.thamma.nozelink.model.NozeModel;

@SuppressWarnings("serial")
public class OrderRandom extends Random {

	private int seed;
	private int cap;
	private int[] stack;

	private final int BIGPRIME = 1009;

	public OrderRandom(int seed) {
		this(seed, Integer.MAX_VALUE);
	}

	public OrderRandom(int seed, int cap) {
		super(seed);
		this.cap = cap;
		this.seed = seed;
		this.stack = new int[BIGPRIME];
		this.loadStack();
	}

	private void loadStack() {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = this.nextInt(cap);
		}
	}

	public int getNth(int n) {
		positiveMod(n);
		return this.stack[n];
	}

	private void positiveMod(int n) {

		while (n < 0)
			n += this.BIGPRIME;
		while (n >= this.BIGPRIME)
			n -= this.BIGPRIME;

	}

}
