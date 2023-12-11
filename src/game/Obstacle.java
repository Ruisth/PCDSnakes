package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import java.io.Serializable;

public class Obstacle extends GameElement implements Serializable {


	public static final int NUM_MOVES=3;
	public static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves = NUM_MOVES;
	private Board board;
	private int x;
	private int y;
	public Obstacle(Board board) {
		super();
		this.board = board;
	}

	public int getRemainingMoves() {
		return remainingMoves;
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Cell getObsCell(){
		return board.getCell(new BoardPosition(x, y));
	}

	public void move() throws InterruptedException {



		this.getObsCell().removeObstacle();
		this.setPosition(board.getRandomPosition());
		remainingMoves--;
	}

	private void setPosition(BoardPosition newPosition) {
		x = newPosition.getX();
		y = newPosition.getY();

		board.getCell(newPosition);
	}
}
