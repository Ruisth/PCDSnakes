package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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

	@Override
	public void run() {
		//Iniciar a posição da cobra
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		try {
			while (true) {
				sleep(Board.PLAYER_PLAY_INTERVAL*10);
				cells.getLast().request(this);
				if(!isHumanSnake()) {
					//fazer uma direção random e chamar a função move()

					Cell head = cells.getFirst();
					// Get available directions
					List<BoardPosition> availableDirections = board.getNeighboringPositions(head);

					//Get the possible random direction
					int randomIndex = (int) (Math.random() * availableDirections.size());
					Cell newCell = new Cell(availableDirections.get(randomIndex));

					//Compare the distances between old and new position of the snake
					double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
					double newDistanceGoal = newCell.getPosition().distanceTo(board.getGoalPosition());

					if (newDistanceGoal <= oldDistanceGoal) {
						move(newCell);
					}

				} else {
					//receber input do teclado e chamar a função move() com esse input
				}
			}
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public abstract boolean isHumanSnake();

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
	protected void move(Cell newCell) throws InterruptedException {
		// Check if the new position is valid and available
		if (isValid(newCell.getPosition())) {
			//Check if the new position is occupied by the snake
			for (Cell cell : cells) {
				if (cell.isEqual(newCell)) {
					return;
				}
			}
			newCell.request(this);
			// Move the snake to the new cell
			cells.addFirst(newCell);
			if (cells.size() > size) {
				// Remove the tail cell if the snake exceeds its size limit
				Cell tail = cells.removeLast();
				tail.release();
			}
		}
		// Update the GUI to reflect the snake's new position
		board.setChanged();
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

		//fazer a mesma coisa do automatic move, para elas escolherem outra direção

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
