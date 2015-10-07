package utils;

import java.util.Random;

@SuppressWarnings("serial")
public class OrderRandom extends Random {

	private int largestNumber;
	private int[] stack;

	private final int BIGPRIME = 1009;

	public OrderRandom(int seed) {
		this(seed, Integer.MAX_VALUE);
	}

	public OrderRandom(int seed, int cap) {
		super(seed);
		this.largestNumber = cap;
		this.stack = new int[BIGPRIME];
		this.loadStack();
	}

	private void loadStack() {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = this.nextInt(largestNumber);
		}
	}

	public int getNth(int n) {
		n = positiveMod(n);
		return this.stack[n];
	}

	private int positiveMod(int n) {
		while (n < 0)
			n += this.stack.length;
		while (n >= this.stack.length)
			n -= this.stack.length;
		return n;
	}

}
