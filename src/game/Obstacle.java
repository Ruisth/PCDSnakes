package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import java.io.Serializable;

public class Obstacle extends GameElement implements Serializable {


	public static final int NUM_MOVES=3;
	public static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves = NUM_MOVES;
	Board board;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int x;
	private int y;
	public Obstacle(Board board) {
		super();
		this.board = board;
	}

	public int getRemainingMoves() {
		return remainingMoves;
	}
	public void setRemainingMoves(int numMoves){ this.remainingMoves = numMoves; }


	/*public void move() throws InterruptedException {

		while (this.getRemainingMoves() > 0){
			for(Cell[] cell1 : board.getCells()) {
				for (Cell cell2 : cell1) {
					if (cell2.getGameElement().equals(this)) {
						cell2.removeObstacle();
					}
				}
			}
			board.addObstacles(1);
			remainingMoves--;
			board.setChanged();
		}






	}*/

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BoardPosition getPosition() {
		return new BoardPosition(x,y);
	}


	private void setPosition(BoardPosition newPosition) {
		x = newPosition.getX();
		y = newPosition.getY();

		board.getCell(newPosition);
	}
}
