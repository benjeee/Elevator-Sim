import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;


public class MainControl extends JApplet {

	/**
	 * automatically found generated serial number
	 */
	private static final long serialVersionUID = 1808845258941607145L;
	/* gu = Ground floor: up
  	   su = Second floor: up
	   sd = Second floor: down
	   tu = Third floor: up
	   td = Third floor: down
	   fd = Forth floor: down   */

	public static Elevator left, right;
	
	public static boolean emergency = false;

	private JPanel mainPanel;
	private JPanel secondFloor_Panel, thirdFloor_Panel;
	private JPanel col1, col2, col3, col4, col5;
	
	private JLabel label[];

	JButton gu = new JButton("Ground floor: up");
	JButton su = new JButton("Second floor: up");
	JButton sd = new JButton("Second floor: down");
	JButton tu = new JButton("Third floor: up");
	JButton td = new JButton("Third floor: down");
	JButton fd = new JButton("Forth floor: down");

	private JPanel e1_Panel;
	JButton e1_g = new JButton("G");
	JButton e1_s = new JButton("2");
	JButton e1_t = new JButton("3");
	JButton e1_f = new JButton("4");
	JButton e1_close = new JButton("Close");
	JButton e1_open = new JButton("Open");
	JButton e1_stop = new JButton("Stop");
	JButton e1_emergency = new JButton("Emergency");

	private JPanel e2_Panel;
	JButton e2_g = new JButton("G");
	JButton e2_s = new JButton("2");
	JButton e2_t = new JButton("3");
	JButton e2_f = new JButton("4");
	JButton e2_close = new JButton("Close");
	JButton e2_open = new JButton("Open");
	JButton e2_stop = new JButton("Stop");
	JButton e2_emergency = new JButton("Emergency");

	public void init() {

		//		masterQueue = new LinkedList<String>();

		setUpGUI();

		// once elevator constructor is changed use this
		left = new Elevator(col2);
		right = new Elevator(col4);


		// set up elevator threads
		Thread[] elevators = new Thread[2];
		elevators[0] = new Thread(left);
		elevators[1] = new Thread(right);

		// make threads Daemon threads so they will end once the main thread ends
		elevators[0].setDaemon(true);
		elevators[1].setDaemon(true);

		// start elevators
		elevators[0].start();
		elevators[1].start();

	}

	private void setUpGUI() {
		this.setSize(800, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 5));

		col1 = new JPanel();
		col1.setLayout(new GridLayout(4, 1));
		col2 = new JPanel();
		col2.setLayout(new GridLayout(4, 1));
		col3 = new JPanel();
		col3.setLayout(new GridLayout(4, 1));
		col4 = new JPanel();
		col4.setLayout(new GridLayout(4, 1));
		col5 = new JPanel();
		col5.setLayout(new GridLayout(4, 1));

		secondFloor_Panel = new JPanel();
		secondFloor_Panel.setLayout(new GridLayout(2, 1));

		thirdFloor_Panel = new JPanel();
		thirdFloor_Panel.setLayout(new GridLayout(2, 1));

		e1_Panel = new JPanel();
		e1_Panel.setLayout(new GridLayout(8, 1));



		e2_Panel = new JPanel();
		e2_Panel.setLayout(new GridLayout(8, 1));



		thirdFloor_Panel.add(tu);
		thirdFloor_Panel.add(td);
		secondFloor_Panel.add(su);
		secondFloor_Panel.add(sd);


		e1_Panel.add(e1_g);
		e1_Panel.add(e1_s);
		e1_Panel.add(e1_t);
		e1_Panel.add(e1_f);
		e1_Panel.add(e1_close);
		e1_Panel.add(e1_open);
		e1_Panel.add(e1_stop);
		e1_Panel.add(e1_emergency);

		e2_Panel.add(e2_g);
		e2_Panel.add(e2_s);
		e2_Panel.add(e2_t);
		e2_Panel.add(e2_f);
		e2_Panel.add(e2_close);
		e2_Panel.add(e2_open);
		e2_Panel.add(e2_stop);
		e2_Panel.add(e2_emergency);


		label = new JLabel[6];

		for(int i = 0; i<6; i++)
		{
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		col1.add(label[0]);
		col1.add(label[1]);
		label[2].removeAll();
		label[2].setVerticalAlignment(JLabel.BOTTOM);
		label[2].setText("Left Elevator Control Panel");
		col1.add(label[2]);
		col1.add(e1_Panel);


		col3.add(fd);
		col3.add(thirdFloor_Panel);
		col3.add(secondFloor_Panel);
		col3.add(gu);


		col5.add(label[3]);
		col5.add(label[4]);
		label[5].removeAll();
		label[5].setVerticalAlignment(JLabel.BOTTOM);
		label[5].setText("Right Elevator Control Panel");
		col5.add(label[5]);
		col5.add(e2_Panel);

		mainPanel.add(col1);
		mainPanel.add(col2);
		mainPanel.add(col3);
		mainPanel.add(col4);
		mainPanel.add(col5);

		// action listeners of all the buttons

		gu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	  addToQueue("gu");
				assignElevator(1, State.MOVING_UP);
			}
		});

		su.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	  addToQueue("su");
				assignElevator(2, State.MOVING_UP);
			}
		});

		sd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	  addToQueue("sd");
				assignElevator(2, State.MOVING_DOWN);
			}
		});

		tu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	  addToQueue("tu");
				assignElevator(3, State.MOVING_UP);
			}
		});

		td.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	  addToQueue("td");
				assignElevator(3, State.MOVING_DOWN);
			}
		});

		fd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//		    	addToQueue("fd");
				assignElevator(4, State.MOVING_DOWN);

			}
		});

		//elevator 1 actions
		e1_g.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.addToQueue(1,0);
			}
		});

		e1_s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.addToQueue(2,0);
			}
		});

		e1_t.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.addToQueue(3,0);
			}
		});

		e1_f.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.addToQueue(4,0);
			}
		});

		e1_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.openDoors();
			}
		});

		e1_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				left.closeDoors();
			}
		});

		e1_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (left.stop) {
					left.stop = false;
				} else {
					left.stop = true;
				}
			}
		});

		e1_emergency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (emergency) {
					label[0].removeAll();
					label[0].setText("");
					label[3].removeAll();
					label[3].setText("");
					left.closeDoors();
					right.closeDoors();
					emergency = false;
				} else {
					label[0].removeAll();
					label[0].setText("EMERGENCY!");
					label[3].removeAll();
					label[3].setText("EMERGENCY!");
					left.openDoors();
					right.openDoors();
					emergency = true;
				}

			}
		});

		//elevator 2 actions
		e2_g.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.addToQueue(1,0);
			}
		});

		e2_s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.addToQueue(2,0);
			}
		});

		e2_t.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.addToQueue(3,0);
			}
		});

		e2_f.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.addToQueue(4,0);
			}
		});

		e2_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.openDoors();
			}
		});

		e2_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				right.closeDoors();
			}
		});

		e2_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (right.stop) {
					right.stop = false;
				} else {
					right.stop = true;
				}
			}
		});

		e2_emergency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (emergency) {
					label[3].removeAll();
					label[3].setText("");
					label[0].removeAll();
					label[0].setText("");
					left.closeDoors();
					right.closeDoors();
					emergency = false;
				} else {
					label[3].removeAll();
					label[3].setText("EMERGENCY!");
					label[0].removeAll();
					label[0].setText("EMERGENCY!");
					left.openDoors();
					right.openDoors();
					emergency = true;
				}
				 
			}
		});

		this.add(mainPanel);
		mainPanel.setVisible(true);  
		e1_Panel.setVisible(true);
		e2_Panel.setVisible(true);
	}

	/**
	 * Assigns the correct elevator the floor
	 * @param floor The floor that is being assigned
	 * @param state Whether the up or down button was pushed
	 */
	private void assignElevator(int floor, State state) {
		// both elevators are halted
		if (left.state == State.HALTED && right.state == State.HALTED) {
			pickClosestElevator(floor, state);
			// left moving up and right is halted
		} else if (left.state == State.MOVING_UP && right.state == State.HALTED) {
			if (state == State.MOVING_UP) {
				if (leftPassed(floor, state)) {
					assignRight(floor, state);
				} else {
					assignLeft(floor, state);
				}
			} else {
				assignRight(floor, state);
			}
			// right moving up and left is halted
		} else if (right.state == State.MOVING_UP && left.state == State.HALTED) {
			if (state == State.MOVING_UP) {
				if (rightPassed(floor, state)) {
					assignLeft(floor, state);
				} else {
					assignRight(floor, state);
				}
			} else {
				assignLeft(floor, state);
			}
			// left moving down and right is halted
		} else if (left.state == State.MOVING_DOWN && right.state == State.HALTED) {
			if (state == State.MOVING_DOWN) {
				if (leftPassed(floor, state)) {
					assignRight(floor, state);
				} else {
					assignLeft(floor, state);
				}
			} else {
				assignRight(floor, state);
			}
			// right moving down and left is halted
		} else if (right.state == State.MOVING_DOWN && left.state == State.HALTED) {
			if (state == State.MOVING_DOWN) {
				if (rightPassed(floor, state)) {
					assignLeft(floor, state);
				} else {
					assignRight(floor, state);
				}
			} else {
				assignLeft(floor, state);
			}
			// both moving up
		} else if (left.state == State.MOVING_UP && right.state == State.MOVING_UP) {
			if (state == State.MOVING_UP) {
				boolean leftPassed = leftPassed(floor, state);
				boolean rightPassed = rightPassed(floor, state);
				// left has passed floor, but right has not
				if (leftPassed && !rightPassed) {
					assignRight(floor, state);
					// right passed, but left has not
				} else if (!leftPassed && rightPassed) {
					assignLeft(floor, state);
					// neither passed
				} else if (!leftPassed && !rightPassed) {
					pickClosestElevator(floor, state);
					// both have passed
				} else {
					chooseSmallerQueue(floor, state);
				}
				// down button pushed
			} else {
				chooseSmallerQueue(floor, state);
			}
			// both moving down
		} else if (left.state == State.MOVING_DOWN && right.state == State.MOVING_DOWN) {
			if (state == State.MOVING_DOWN) {
				boolean leftPassed = leftPassed(floor, state);
				boolean rightPassed = rightPassed(floor, state);
				// left has passed floor, but right has not
				if (leftPassed && !rightPassed) {
					assignRight(floor, state);
					// right passed, but left has not
				} else if (!leftPassed && rightPassed) {
					assignLeft(floor, state);
					// neither passed
				} else if (!leftPassed && !rightPassed) {
					pickClosestElevator(floor, state);
					// both have passed
				} else {
					chooseSmallerQueue(floor, state);
				}
				// up button pushed	
			} else {
				chooseSmallerQueue(floor, state);
			}
			// left moving up and right moving down
		} else if (left.state == State.MOVING_UP && right.state == State.MOVING_DOWN) {
			if (state == State.MOVING_UP) {
				// left has not passed
				if (!leftPassed(floor, state)) {
					assignLeft(floor, state);
				} else {
					chooseSmallerQueue(floor, state);
				}
			} else {
				// right has not passed
				if (!rightPassed(floor, state)) {
					assignRight(floor, state);
				} else {
					chooseSmallerQueue(floor, state);
				}
			}
			// left moving down and right moving up
		} else if (left.state == State.MOVING_DOWN && right.state == State.MOVING_UP) {
			if (state == State.MOVING_UP) {
				// right has not passed
				if (!rightPassed(floor, state)) {
					assignRight(floor, state);
				} else {
					chooseSmallerQueue(floor, state);
				}
			} else {
				// left has not passed
				if (!leftPassed(floor, state)) {
					assignLeft(floor, state);
				} else {
					chooseSmallerQueue(floor, state);
				}
			}
			// just in case
		} else {
			chooseSmallerQueue(floor, state);
		}
	}

	/**
	 * Assign the elevator with the smaller queue
	 * @param floor The floor to be assigned
	 * @param state The direction the person wants to go
	 */
	private void chooseSmallerQueue(int floor, State state) {
		if (left.queue.size() <= right.queue.size()) {
			assignLeft(floor, state);
		} else {
			assignRight(floor, state);
		}
	}

	/**
	 * Tells whether the left elevator has passed the given floor
	 * @param floor THe floor being checked
	 * @param state Whether the up or down button was pressed
	 * @return True IF the elevator has passed the floor, false otherwise. 
	 * 		If the elevator will hit the desired floor, FALSE is returned.
	 */
	private boolean leftPassed(int floor, State state) {
		switch(state) {
		case MOVING_UP:
			return floor < left.floor ? false : true;
		case MOVING_DOWN:
			return floor > left.floor ? false : true;
		default:
			throw new IndexOutOfBoundsException("illegal state. Must be moving up or down");
		}
	}

	/**
	 * Tells whether the right elevator has passed the given floor
	 * @param floor THe floor being checked
	 * @param state Whether the up or down button was pressed
	 * @return True IF the elevator has passed the floor, false otherwise. 
	 * 		If the elevator will hit the desired floor, FALSE is returned.
	 */
	private boolean rightPassed(int floor, State state) {
		switch(state) {
		case MOVING_UP:
			return floor < right.floor ? false : true;
		case MOVING_DOWN:
			return floor > right.floor ? false : true;
		default:
			throw new IndexOutOfBoundsException("illegal state. Must be moving up or down");
		}
	}

	/**
	 * Chooses the closest elevator and assigns it to the given floor.
	 * @param floor The floor that is being assigned
	 * @param state Whether the up or down button was pushed
	 */
	private void pickClosestElevator(int floor, State state) {
		if (Math.abs(left.floor - floor) < Math.abs(right.floor - floor))
			assignLeft(floor, state);
		else
			assignRight(floor, state);
	}

	/**
	 * Assigns the left elevator to go to the given floor with the given direction
	 * @param floor The floor to be assigned
	 * @param state The direction to be traveled
	 */
	private void assignLeft(int floor, State state) {
		int source;
		switch (state) {
		case MOVING_DOWN:
			source = 2;
			break;
		case MOVING_UP:
			source = 1;
			break;
		default:
			source = -1;
		}
		if (source == -1)
			throw new IndexOutOfBoundsException("state must be moving up or down");
		left.addToQueue(floor, source);
	}

	/**
	 * Assigns the right elevator to go to the given floor with the given direction
	 * @param floor The floor to be assigned
	 * @param state The direction to be traveled
	 */
	private void assignRight(int floor, State state) {
		int source;
		switch (state) {
		case MOVING_DOWN:
			source = 2;
			break;
		case MOVING_UP:
			source = 1;
			break;
		default:
			source = -1;
		}
		if (source == -1)
			throw new IndexOutOfBoundsException("state must be moving up or down");
		right.addToQueue(floor, source);
	}

	public static void resetElevators() {
		if (left.state == State.HALTED && right.state == State.HALTED) {
			if (left.floor != 1) {
				left.addToQueue(1,2);
			}
			if (right.floor != 4) {
				right.addToQueue(4,1);
			}
		}
	}
}
