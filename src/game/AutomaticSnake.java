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
		System.err.println("initial size:"+cells.size());
		//TODO: automatic movement
		while(state){
			try{
				sleep(Board.PLAYER_PLAY_INTERVAL*10);
				randomMove();
			} catch (InterruptedException e) {
				System.out.println(currentThread() + ": " + e.toString());
			}
		}
	}

	@Override
	public Snake createSnakeInstance(Board board) {
		return new AutomaticSnake(getIdentification(), (LocalBoard) board);
	}

	public void randomMove() {
		try {
			// Get the current head cell of the snake
			Cell head = cells.getFirst();
			Board board = getBoard();

			// Get available directions
			List<BoardPosition> availableDirections = board.getNeighboringPositions(head);

			//Get the possible random direction
			int randomIndex = (int) (Math.random() * availableDirections.size());
			Cell newCell = new Cell(availableDirections.get(randomIndex));

			//Compare the distances between old and new position of the snake
			double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
			double newDistanceGoal = newCell.getPosition().distanceTo(board.getGoalPosition());
			System.out.println("Snake " + getIdentification() + " old distance to goal : " + oldDistanceGoal);
			System.out.println("Snake " + getIdentification() + " new distance to goal : " + newDistanceGoal);

			//Check if the new position is occupied by the snake
			for (Cell cell : cells) {
				if (cell.isEqual(newCell)) {
					return;
				}
			}
			//isValid
			if (isValid(newCell.getPosition())) {
				if (newDistanceGoal <= oldDistanceGoal) {
					newCell.request(this);
					//Move the snake to the new cell
					cells.addFirst(newCell);

					//Remove the last cell if snake exceeds its size
					if (cells.size() > size) {
						Cell tail = cells.removeLast();
						tail.release();
					}
				}
			}

			//Notify the GUI to change snake position
			board.setChanged();
			System.out.println("Snake " + getIdentification() + " moved to: [" + getCells().getFirst().getPosition().x + "," + getCells().getFirst().getPosition().y + "]");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isHumanSnake() {
		return false;
	}



}
