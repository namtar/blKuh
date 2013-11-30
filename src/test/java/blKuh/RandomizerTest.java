package blKuh;

import java.util.Random;

import org.junit.Test;

public class RandomizerTest {

	@Test
	public void testRandoms() {

		int numberOfColors = 5;
		Random rand = new Random();
		
		for(int i = 0; i < 10; i++) {
			System.out.println("Zufallszahl: " + rand.nextInt(numberOfColors));
		}
	}
}
