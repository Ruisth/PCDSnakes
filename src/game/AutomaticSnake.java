package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

import static environment.BoardPosition.isValid;

public class AutomaticSnake extends Snake {

	private boolean state = false;


	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		state = true;
		System.err.println("initial size:" + cells.size());
		//TODO: automatic movement
		while (state) {
			try {
				sleep(Board.PLAYER_PLAY_INTERVAL * 10);
				randomMove();
			} catch (InterruptedException e) {
				//System.out.println(currentThread() + ": " + e.toString());
				stopSnake();
			}
		}
	}

	public void randomMove() {
		try {
			// Get the current head cell of the snake
			Cell head = cells.getFirst();
			//head.lock.lock();
			Board board = getBoard();
			// Get available directions
			List<BoardPosition> availableDirections = board.getNeighboringPositions(head);
			double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
			double newDistanceGoal;
			Cell newCell = null;

			synchronized (this) {
				for (BoardPosition pos : availableDirections) {
					newDistanceGoal = pos.distanceTo(board.getGoalPosition());

					if (newDistanceGoal <= oldDistanceGoal) {
						newCell = board.getCell(pos);
						System.out.println(newCell.getPosition() + " moved to: [" + getCells().getFirst().getPosition().x + "," + getCells().getFirst().getPosition().y + "]");
					}
					if (head.getPosition().equals(board.getGoalPosition())) {
						this.wait();
					}
				}
			}

			//System.out.println("Snake " + getIdentification() + " old distance to goal : " + oldDistanceGoal);
			//System.out.println("Snake " + getIdentification() + " new distance to goal : " + newDistanceGoal);

			move(newCell);
			//Notify the GUI to change snake position
			//board.setChanged();
			//System.out.println("Snake " + getIdentification() + " moved to: [" + getCells().getFirst().getPosition().x + "," + getCells().getFirst().getPosition().y + "]");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isHumanSnake() {
		return false;
	}

	private void stopSnake(){
		state = false;
	}
}
