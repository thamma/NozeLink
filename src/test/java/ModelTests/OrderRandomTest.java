package ModelTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import utils.OrderRandom;

public class OrderRandomTest {

	@Test
	public void compareTwoOrderRandoms() {
		int cap = 128;
		int seed = 42;
		OrderRandom r1 = new OrderRandom(seed, cap);
		OrderRandom r2 = new OrderRandom(seed, cap);
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			int m = r.nextInt(cap);
			assertEquals(r1.getNth(m), r2.getNth(m));
		}
	}

	@Test
	public void allBelowCap() {
		int cap = 1337;
		OrderRandom random = new OrderRandom(cap, cap);
		for (int i = 0; i < 100; i++) {
			assertTrue(random.getNth(i) < cap);
		}
	}

}
