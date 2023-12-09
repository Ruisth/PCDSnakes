package remote;

import environment.Board;
import game.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import environment.Cell;


/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */


public class RemoteBoard extends Board implements Serializable {

	public RemoteBoard(){
	}


	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
	}

	@Override
	public void handleKeyRelease() {
		//TODO
	}

	@Override
	public void init() {
		//TODO
		snakes.clear();
		for (Snake snake : snakes) {
			HumanSnake humanSnake = new HumanSnake(snake.getIdentification(), this);
			snakes.add(snake);
		}

		setChanged();
		notifyObservers();
	}
}
