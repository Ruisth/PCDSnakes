package game;

import coordination.FinishCountDownLatch;
import environment.BoardPosition;
import environment.Cell;
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
		try {
			/*for (Obstacle obs : board.getObstacles()) {*/
				while (obstacle.getRemainingMoves() > 0) {
					sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
					obstacle.move();
				}

				// Aguarda o próximo intervalo

			} catch(InterruptedException e){
				e.printStackTrace();
			}
	}
}
