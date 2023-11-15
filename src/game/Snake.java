package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import static environment.BoardPosition.isValid;


/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	private static final int DELTA_SIZE = 10;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	private Board board;

	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	public abstract Snake createSnakeInstance(Board board);

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}

	public LinkedList<Cell> getCells() {
		return cells;
	}

	//Criação da movimentação das snakes
	protected void move(Cell cell) throws InterruptedException {
		// Check if the new position is valid and available
		if (isValid(cell.getPosition())) {
			// Move the snake to the new cell
			cells.addFirst(cell);
			if (cells.size() > size) {
				// Remove the tail cell if the snake exceeds its size limit
				Cell tail = cells.removeLast();
				tail.release();
			}
		}
		// Update the GUI to reflect the snake's new position
		board.setChanged();
	}

	private boolean isHumanPlayer() {
		return true;
	}

	public BoardPosition getNewPosition() {
		// Generate a random position
		int newX = (int) (Math.random() * Board.NUM_COLUMNS);
		int newY = (int) (Math.random() * Board.NUM_ROWS);

		return new BoardPosition(newX, newY);

	}


	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}

	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);

		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
		System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());
	}

	public Board getBoard() {
		return board;
	}


	// Method to set the snake's position to a random location
	private void setSnakeRandomPosition(Snake snake) throws InterruptedException {
		// Clear the current cells occupied by the snake
		for (int i = 0; i < snake.getLength(); i++) {
			try {
				snake.getCells().get(i).release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Generate a new random position
		BoardPosition newPosition = getNewPosition();

		//try {
			// Request the cells at the new position for the snake
			for (int i = 0; i < snake.getLength(); i++) {
				//if (isValid(newPosition)) {
					//if (i == 0) {
						board.getCell(newPosition).request(snake);
						//cells.add(snake.getCells().get(i));
					//} else {
						//cells.add(snake.getCells().get(i));
					//}
				//}
			}
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}

		// Update the GUI to reflect the new snake positions
		board.setChanged();
	}

	// Method to reset snake positions
	public void resetSnakePositions() throws InterruptedException {
		for (Snake snake : board.getSnakes()) {
			// Call the method to set the snake's position to a random location
			setSnakeRandomPosition(snake);
			board.setChanged();
		}
	}


}
