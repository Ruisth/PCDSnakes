package environment;

import java.io.IOException;
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
public class LocalBoard extends Board{
	
	private static final int NUM_SNAKES = 2;
	public static final int NUM_OBSTACLES = 0;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	

	public LocalBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles( NUM_OBSTACLES);

		
		Goal goal=addGoal();
		System.err.println("All elements placed");
	}

	public void init() {
		// Create an ExecutorService with a fixed number of threads
		ExecutorService executor = Executors.newFixedThreadPool(snakes.size());

		// Submit tasks for each snake to the ExecutorService
		for (Snake snake : snakes) {
			executor.submit(() -> snake.start());
		}

		// Shut down the ExecutorService when all snakes have finished
		executor.shutdown();
		// TODO: launch other threads
		for (Obstacle obs:obstacles){
			ObstacleMover move = new ObstacleMover(obs, this);
			move.start();
		}
		setChanged();
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
