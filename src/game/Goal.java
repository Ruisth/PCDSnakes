package game;

import environment.Board;
import environment.Cell;
import environment.LocalBoard;

import static environment.Board.countDownLatch;

public class Goal extends GameElement  {
	private int value;
	private Board board;
	public static final int MAX_VALUE=10;
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		if(value == 0) {
			value = 1;
		} else {
			value++;
		}
	}

	/*public int captureGoal() throws InterruptedException {
		// Increment the goal's value
		incrementValue();
		// Check if the goal's value has reached the maximum value
		if (getValue() == Goal.MAX_VALUE) {
			// Trigger the game's end condition
			countDownLatch.countDown();
			return 1;
		}

		// Update the goal's position
		goalPosition = board.randomPosition();

		return -1;
	}*/
}
