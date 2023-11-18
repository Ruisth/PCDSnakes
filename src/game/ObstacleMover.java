package game;

import coordination.FinishCountDownLatch;
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
			/*try {
				while (!isInterrupted()) {
					// Move o obstáculo
					obstacle.move();
					// Notifica o board para atualizar a posição do obstáculo
					board.setChanged();
					// Aguarda o próximo intervalo
					sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
}
