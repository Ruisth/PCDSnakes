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
			while (!isInterrupted()) {
				// Move o obstáculo
				if (obstacle.getRemainingMoves() > 0) {
					obstacle.move();
					board.setChanged();
					// Aguarda o próximo intervalo
					sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				} else {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
