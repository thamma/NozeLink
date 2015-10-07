package ModelTests;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.OrderRandom;

public class OrderRandomTest {

	@Test
	public void compareTwoOrderRandoms() {
		OrderRandom r1 = new OrderRandom(42, 128);
		OrderRandom r2 = new OrderRandom(42, 128);
		for (int i = 0; i < 100; i++) {
			assertEquals(r1.getNth(i), r2.getNth(i));
		}
	}

}
