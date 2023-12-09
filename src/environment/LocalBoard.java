package environment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import game.*;

/** Class representing the state of a game running locally
 *
 * @author luismota
 *
 */
public class LocalBoard extends Board implements Serializable{

	public static final int NUM_OBSTACLES = 2;
	private static final int NUM_SNAKES = 2;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;


	public LocalBoard() {

		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles(NUM_OBSTACLES);

		Goal goal=addGoal();
		System.err.println("All elements placed");
	}

	public void init() {
		// Create an ExecutorService with a fixed number of threads
		ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);

		// Submit tasks for each snake to the ExecutorService
		for (Snake snake : snakes) {
			snake.start();
		}

		// TODO: launch other threads
		for (Obstacle obs : obstacles) {
			ObstacleMover mover = new ObstacleMover(obs, this);
			pool.submit(mover);
		}
		pool.shutdown();
		setChanged();


		//TODO Debug morte

	}

	public void endGame() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Matar threads e terminar jogo
		stopSnakes();

		isFinished = true;
		//O que fazer quando acabar
		System.err.println("GAME FINISHED!");
	}


	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}

}
