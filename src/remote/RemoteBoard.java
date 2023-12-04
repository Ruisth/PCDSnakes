package remote;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.Obstacle;
import game.Snake;

import static java.awt.event.KeyEvent.*;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{

	private boolean wasdOrArrows;
	private String directionPressed;
	
	@Override
	public void handleKeyPress(int keyCode) {
		if (wasdOrArrows) {
			switch (keyCode) {
				case VK_A:
					directionPressed = "LEFT";
					break;
				case VK_D:
					directionPressed = "RIGHT";
					break;
				case VK_W:
					directionPressed = "UP";
					break;
				case VK_S:
					directionPressed = "DOWN";
					break;
			}
		} else {
			switch (keyCode) {
				case VK_LEFT:
					directionPressed = "LEFT";
					break;
				case VK_RIGHT:
					directionPressed = "RIGHT";
					break;
				case VK_UP:
					directionPressed = "UP";
					break;
				case VK_DOWN:
					directionPressed = "DOWN";
					break;
			}
		}
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}

	@Override
	public void init() {
		// TODO 		
	}

	public String getPressedDirection() {
		return directionPressed;
	}
	

}
