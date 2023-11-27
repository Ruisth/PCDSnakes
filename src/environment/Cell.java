package environment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;

/** Main class for game representation.
 * 
 * @author luismota
 *
 */
public class Cell {
	private final BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;
	public Lock lock = new ReentrantLock();
	public Condition lockCondition = lock.newCondition();
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake)
			throws InterruptedException {
		//TODO coordination and mutual exclusion
		try{
			lock.lock();
			while (ocuppyingSnake!=null){
				lockCondition.await();
			}
			ocuppyingSnake = snake;
		}finally {
			lock.unlock();
		}
	}

	public void release()
			throws InterruptedException {
				//TODO coordination and mutual exclusion
		try {
			lock.lock();
			ocuppyingSnake = null;
			gameElement = null;
			lockCondition.signalAll();
		}finally {
			lock.unlock();
		}
	}

	public boolean isOcupiedBySnake() {
		try{
			lock.lock();
			return ocuppyingSnake!=null;
		}finally {
			lock.unlock();
		}
	}


	public  void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		try{
			lock.lock();
			gameElement = element;
		}finally {
			lock.unlock();
		}
	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public Goal removeGoal() {
		try{
			lock.lock();
			gameElement = null;
			return null;
		}finally {
			lock.unlock();
		}
	}


	public void removeObstacle() {
	//TODO
		try{
			lock.lock();
			gameElement = null;
		}finally {
			lock.unlock();
		}
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}

	public boolean isEqual(Cell newCell) {
        return this.position.x == newCell.position.x && this.position.y == newCell.position.y;
    }

	@Override
	public String toString() {
		return "(" + position.x + ", " + position.y + ")";
	}

}
