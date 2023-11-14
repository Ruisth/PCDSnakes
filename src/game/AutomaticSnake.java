package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

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
			LinkedList<BoardPosition> availableDirections = new LinkedList<>();
			availableDirections.add(new BoardPosition(0, -1)); // Up
			availableDirections.add(new BoardPosition(0, 1));  // Down
			availableDirections.add(new BoardPosition(-1, 0)); // Left
			availableDirections.add(new BoardPosition(1, 0));  // Right

			//Get the possible random direction
			int randomIndex = (int) (Math.random() * availableDirections.size());
			BoardPosition direction =availableDirections.get(randomIndex);

			//Calculate the new position based on the random direction
			BoardPosition newPosition = new BoardPosition(
					head.getPosition().getX() + direction.getX(),
					head.getPosition().getY() + direction.getY());


			//Request the new cell
			if (newPosition.getX() >= 0 && newPosition.getX() < Board.NUM_COLUMNS
					&& newPosition.getY() >= 0 && newPosition.getY() < Board.NUM_COLUMNS
					&& newPosition != head.getPosition()) {


				board.getCell(newPosition).request(this);
				//Move the snake to the new cell
				cells.addFirst(board.getCell(newPosition));

				if (cells.size() > size) {
					Cell tail = cells.removeLast();
					tail.release();
				}
			}

			//Remove the last cell if snake exceeds its size


			//Notify the GUI to change snake position
			board.setChanged();
			System.out.println("Snake " + getIdentification() + " moved to: " + getCells().getFirst());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
}
