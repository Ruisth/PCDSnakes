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
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		try {
			cells.getLast().request(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO: automatic movement
		for (int i = 0; i < 100; i++){
			try{
				sleep(Board.PLAYER_PLAY_INTERVAL*10);
				randomMove();
			} catch (InterruptedException e) {
				System.out.println(currentThread() + ": " + e.toString());
            }

        }
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

			double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
			double newDistanceGoal = newCell.getPosition().distanceTo(board.getGoalPosition());
			//System.out.println(oldDistanceGoal);
			//System.out.println(newDistanceGoal);


			//Check if the new position is occupied by the snake
			for (Cell cell : cells) {
				if (cell.isEqual(newCell)) {
					return;
				}
			}

			//isValid
			if (isValid(newCell.getPosition())) {
				newCell.request(this);
				//Move the snake to the new cell
				cells.addFirst(newCell);

				//Remove the last cell if snake exceeds its size
				if (cells.size() > size) {
					Cell tail = cells.removeLast();
					tail.release();
				}
			}

			//Notify the GUI to change snake position
			board.setChanged();
			System.out.println("Snake " + getIdentification() + " moved to: " + getCells().getFirst());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isHumanPlayer() {
		return false;
	}



}
