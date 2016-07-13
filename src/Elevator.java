import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;



public class Elevator implements Runnable {

	// The current state of the elevator, uses the State enum
	public State state;
	// the current floor the elevator is on
	public int floor;
	// The list of floors the elevator needs to visit in order.
	public LinkedList<Integer> queue;

	public boolean stop = false;

	public JPanel elevatorPanel;
	private JLabel label[] = new JLabel[4];

	private static Image nothing, closed, open;

	public Elevator(JPanel ePanel) {
		floor = 1; 
		state = State.HALTED;
		queue = new LinkedList<Integer>();

		elevatorPanel = ePanel;

		try {
			URL url = new URL("http://vicarious.cs.umd.edu/nothing.jpg");
			nothing = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			URL url = new URL("http://vicarious.cs.umd.edu/open.jpg");
			open = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			URL url = new URL("http://vicarious.cs.umd.edu/closed.jpg");
			closed = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i<4; i++)
		{
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		ePanel.add(label[0]);
		ePanel.add(label[1]);
		ePanel.add(label[2]);
		ePanel.add(label[3]);

		changeImage();

	}

	@Override
	public void run() {
		while (true) {
			while (!MainControl.emergency && !stop) {
				if (!queue.isEmpty()) {
					changeFloor(queue.getFirst());
					System.out.println(queue.toString());
					System.out.println(state);
					try{
						Thread.sleep(1000);
					}catch(Exception e) {
						System.out.println("Sleep Exception caught");
					}
				}else {
					state = State.HALTED;
					try{
						Thread.sleep(2500);
					}catch(Exception e) {
						System.out.println("Sleep Exception caught");
					}
					if(queue.isEmpty()) 
						MainControl.resetElevators();
				}
			}
		}
	}

	/**
	 * Move the elevator from one floor to another
	 * @param going The floor that the elevator needs to go to.
	 */
	public void changeFloor(int going) {
		if  (going < floor) {
			this.state = State.MOVING_DOWN;
			moveDown(going);
		} else if (going > floor) {
			this.state = State.MOVING_UP;
			moveUp(going);
		} else {
			queue.removeFirst();
			openDoors();
			closeDoors();
		}
	}

	/**
	 * Moves the elevator downwards
	 * @param going The floor that the elevator needs to go to. 
	 */
	private void moveDown(int going) {
		if(floor > 1){
			try{
				Thread.sleep(1000);
			}catch(Exception e) {
				System.out.println("Sleep Exception caught");
			}
			floor = floor -1;
			changeImage();
			//update gui
		} else 
			throw new IndexOutOfBoundsException("Trying to move below ground floor");
	}

	/**
	 * Moves the elevator upwards
	 * @param going The floor that the elevator needs to go to.
	 */
	private void moveUp(int going) {
		if(floor < 4){
			floor = floor +1;
			changeImage();
			//update gui
		} else 
			throw new IndexOutOfBoundsException("Trying to move past floor 4");

	}

	/**
	 * Opens the doors of the elevator
	 */
	public void openDoors() {
		state = State.DOORS_OPEN;
		changeImage();
		try{
			Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println("Sleep Exception caught");
		}

	}

	/**
	 * Closes the doors of the elevator
	 */
	public void closeDoors() {
		state = State.DOORS_CLOSED;
		changeImage();
		try{
			Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println("Sleep Exception caught");
		}
	}

	/** 
	 * Adds the floor with specifications to the priority queue of floors for the elevator to visit. 
	 * @param target The target floor to be added to the priority queue
	 * @param source Indicates if the floor was added by pressing the 
	 * 		up button or down button on a floor, or by pressing a 
	 * 		floor button within the elevator. 
	 * 			0 = button pressed inside elevator
	 * 			1 = up button pressed on a floor
	 * 			2 = down button pressed on a floor
	 * */
	public void addToQueue(int target, int source) {
		if (floor < 1 || floor > 4) throw new IndexOutOfBoundsException("floor must be between 1 and 4");
		if (source < 0 || source > 2) throw new IndexOutOfBoundsException("source must be between 0 and 2");
		synchronized (queue) {
			if (queue.isEmpty()) { 
				queue.addFirst(target); 
				return; 
			} source = source - 1; 
			// n determines if this elevator is going down or up. 
			int n = queue.getFirst() - floor; 
			// This deals with the situation in which the floor pressed is the current floor. 
			if (target == floor && ( ((n<0)&&(source != 0)) || ((n>0)&&(source <= 0)) ) ) { 
				queue.addFirst(target); 
				return; 
			} else if (n < 0) { // This means the elevator is going down, not state dependent. 
				if (source == -1) { // Check if source is internal
					if (target < floor) { // checks if target floor is below current floor. 
						addBelow(target, 0);
						return;
					} else {
						int low = queue.indexOf(findLowest());
						addAbove(target, low);
						return;
					}
				} else if (source == 0) { // check if source is up button 
					int low = queue.indexOf(findLowest());
					addAbove(target, low+1);
					return;
				} else { // if source is down button (1)
					if (target < floor) { 
						addBelow(target, 0);
						return;
					} else { // target > floor. This is the weird case. 
						// check floor already contained! 
						int high = queue.indexOf(findHighest()); 
						addBelow(target, high+1); return;
					}
				}
			} else if (n > 0) { // This means the elevator is going up, not state dependent.
				if (source == -1) { // Check if source is internal
					if (target > floor) { // checks if target floor is above the current floor. 
						addAbove(target, 0);
						return;
					} else {
						int high = queue.indexOf(findHighest());
						addBelow(target, high);
						return;
					}
				} else if (source == 1) { // check if source is down button
					int high = queue.indexOf(findHighest());
					addBelow(target, high+1);
					return;
				} else { // if source is up button (2)
					if (target > floor) { 
						addAbove(target, 0);
						return;
					} else { // target < floor. This is the weird case. 
						// check floor already contained! 
						int low = queue.indexOf(findLowest()); 
						addBelow(target, low+1); return;
					}
				}
			} else return; // What here? if n=0, and source is not empty. This should be an error. 
		}
	}

	private void addBelow(int target, int index) {
		if (index == 0)  { 
			if (target > queue.get(index)) {
				queue.addFirst(target); return; }
			else if (queue.size() == 1) { // Consider target = queue.get(index) here. 
				if (target == queue.getFirst()) return;
				queue.addLast(target); return;
			} index++;
		}
		while (index < queue.size() && target < queue.get(index) && queue.get(index - 1) > queue.get(index)) {
			index++;
		} 
		if (index == queue.size()) queue.addLast(target);
		else { 
			if (target == queue.get(index)) return;
			queue.add(index, target);
		}
		return;
	}

	private void addAbove(int target, int index) {
		if (index == 0)  {
			if (target < queue.get(index)) 
			{ queue.addFirst(target); return; }
			else if (queue.size() == 1) { // Consider target = queue.get(index) here. 
				if (target == queue.getFirst()) return;
				queue.addLast(target); return; 
			} index++; 
		}
		while (index < queue.size() && target > queue.get(index) && queue.get(index-1) < queue.get(index)) {
			index++;
		} 
		if (index == queue.size()) queue.addLast(target);
		else { 
			if (target == queue.get(index)) return;
			queue.add(index, target);
		}
		return;
	}

	private int findLowest() {
		int n = 5; 
		for(int i = 0; i < queue.size(); i++) {
			int next = queue.get(i);
			if (next < n) n = next;
		} return n;
	}

	private int findHighest() {
		int n = 0; 
		for(int i = 0; i < queue.size(); i++) {
			int next = queue.get(i);
			if (next > n) n = next;
		} return n;
	}

	// changes the image on all 4 floors depending on the value in variable floor and state
	//
	// state = DOOR_OPEN then it will display open.jpg otherwise closed.jpg
	// The other 3 floor will have nothing.jpg

	private void changeImage() {
		System.out.println(floor + " " + state);
		if(floor == 1)
		{
			if(state == State.DOORS_OPEN)
			{
				label[3].removeAll();
				label[3].setIcon(new ImageIcon(open));
			}
			else
			{
				label[3].removeAll();
				label[3].setIcon(new ImageIcon(closed));
			}

		}
		else if(floor == 2)
		{
			if(state == State.DOORS_OPEN)
			{
				label[2].removeAll();
				label[2].setIcon(new ImageIcon(open));
			}
			else
			{
				label[2].removeAll();
				label[2].setIcon(new ImageIcon(closed));
			}
		}
		else if(floor == 3)
		{
			if(state == State.DOORS_OPEN)
			{
				label[1].removeAll();
				label[1].setIcon(new ImageIcon(open));
			}
			else
			{
				label[1].removeAll();
				label[1].setIcon(new ImageIcon(closed));
			}

		}
		else if(floor == 4)
		{
			if(state == State.DOORS_OPEN)
			{
				label[0].removeAll();
				label[0].setIcon(new ImageIcon(open));
			}
			else
			{
				label[0].removeAll();
				label[0].setIcon(new ImageIcon(closed));
			}
		}

		// assigns nothing.jpg to rest of the floors
		for(int i = 0; i < 4; i++)
		{
			if(4-i == floor )
				continue;

			label[i].removeAll();		
			label[i].setIcon(new ImageIcon(nothing));
		}
	}

	/**
	 * Used to reset the elevators to the 1 and 4 positions
	 * @param target The desired floor to be reset to.
	 */
	public void reset(int target) {
		if (target == 1) {
			while (floor != 1 && queue.size() == 0) {
				floor--;
				changeImage();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (target == 4 && queue.size() == 0) {
			while (floor != 4) {
				floor++;
				changeImage();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
