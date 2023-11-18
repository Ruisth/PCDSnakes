package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
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
		ocuppyingSnake = snake;

	}

	public void release()
			throws InterruptedException {
				//TODO coordination and mutual exclusion
		ocuppyingSnake = null;
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}


	public  void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
			gameElement = element;


	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public  Goal removeGoal() {
		gameElement = null;
		return null;
	}


	public void removeObstacle() {
	//TODO
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

}
