package game;

import environment.BoardPosition;
import environment.LocalBoard;
import environment.Board;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		// TODO
		/*int remainingMoves = obstacle.getRemainingMoves();
		for (int i = 0; i < remainingMoves; i++){
			Board.addObstacles(LocalBoard.NUM_OBSTACLES);
			i--;
		}*/
	}
}
