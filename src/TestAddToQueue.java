import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAddToQueue {
	
	@Test
	public void testBasics() {
		Elevator testElevator = new Elevator();
		Elevator testElevator2 = new Elevator(2);
		
		assertTrue(1 == testElevator.floor);
		assertTrue(2 == testElevator2.floor);
		assertTrue(0 == testElevator.queue.size());
		assertTrue(State.HALTED == testElevator.state);
		
		testElevator.addToQueue(2, 0);
		
		assertTrue(1 == testElevator.queue.size());
	}
	
	@Test
	public void testInternal() {
		Elevator testElevator1 = new Elevator();
		Elevator testElevator2 = new Elevator(3);
		Elevator testElevator3 = new Elevator(2);
		
		testElevator1.addToQueue(3, 0);
		testElevator1.addToQueue(2, 0);
		testElevator1.addToQueue(3, 0);
		testElevator1.addToQueue(1, 0);
		
		testElevator2.addToQueue(2, 0);
		testElevator2.addToQueue(4, 0);
		testElevator2.addToQueue(1, 0);
		
		testElevator3.addToQueue(3, 0);
		testElevator3.addToQueue(1, 0);
		testElevator3.addToQueue(4, 0);
		
		assertTrue(testElevator1.queue.toString().equals("[1, 2, 3]"));
		assertTrue(testElevator2.queue.toString().equals("[2, 1, 4]"));
		assertTrue(testElevator3.queue.toString().equals("[3, 4, 1]"));
	}
	
	// This test needs to be more intensive. 
	@Test
	public void testUpDownButtons() {
		Elevator testElevator1 = new Elevator();
		Elevator testElevator2 = new Elevator(4);
		Elevator testElevator3 = new Elevator(2);
		Elevator testElevator4 = new Elevator(3);
		
		testElevator1.addToQueue(4, 0);
		testElevator1.addToQueue(3, 1);
		testElevator1.addToQueue(2, 2);
		testElevator1.addToQueue(3, 2);
		System.out.println("Elevator 1: " + testElevator1.queue.toString());
		
		testElevator2.addToQueue(1, 0);
		testElevator2.addToQueue(2, 2);
		testElevator2.addToQueue(3, 1);
		testElevator2.addToQueue(2, 1);
		testElevator2.addToQueue(2, 1);
		System.out.println("Elevator 2: " + testElevator2.queue.toString());
		
		testElevator3.addToQueue(1, 0);
		testElevator3.addToQueue(4, 0);
		testElevator3.addToQueue(3, 2);
		System.out.println("Elevator 3: " + testElevator3.queue.toString());
		
		testElevator4.addToQueue(4, 0);
		testElevator4.addToQueue(1, 0);
		testElevator4.addToQueue(2, 1);
		System.out.println("Elevator 4: " + testElevator4.queue.toString());
		
		assertTrue(testElevator1.queue.toString().equals("[3, 4, 3, 2]"));
		assertTrue(testElevator2.queue.toString().equals("[2, 1, 2, 3]"));
		assertTrue(testElevator3.queue.toString().equals("[1, 4, 3]"));
		assertTrue(testElevator4.queue.toString().equals("[4, 1, 2]"));
	}
	
}
