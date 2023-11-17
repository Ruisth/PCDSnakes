package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

import static environment.BoardPosition.isValid;

public class AutomaticSnake extends Snake {

	private boolean state = false;


	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		state = true;
		System.err.println("initial size:"+cells.size());
		//TODO: automatic movement
		while(state){
			try{
				sleep(Board.PLAYER_PLAY_INTERVAL*10);
				randomMove();
			} catch (InterruptedException e) {
				System.out.println(currentThread() + ": " + e.toString());
			}
		}
	}

	public void randomMove() {
		try {
			// Get the current head cell of the snake
			Cell head = cells.getFirst();
			//head.lock.lock();
			Board board = getBoard();

			// Get available directions
			List<BoardPosition> availableDirections = board.getNeighboringPositions(head);

			// Fazer a lista dos obstaculos
			List<Obstacle> obstacles = board.getObstacles();

			//Get the possible random direction
			int randomIndex = (int) (Math.random() * availableDirections.size());
			Cell newCell = new Cell(availableDirections.get(randomIndex));

			//Compare the distances between old and new position of the snake
			double oldDistanceGoal = head.getPosition().distanceTo(board.getGoalPosition());
			double newDistanceGoal = newCell.getPosition().distanceTo(board.getGoalPosition());
			System.out.println("Snake " + getIdentification() + " old distance to goal : " + oldDistanceGoal);
			System.out.println("Snake " + getIdentification() + " new distance to goal : " + newDistanceGoal);


			if (newCell.isOcupied()) {
				return;
			}

			//isValid
			if (isValid(newCell.getPosition())) {

				if (newDistanceGoal <= oldDistanceGoal) {
					newCell.request(this);


					// Impede que as snakes se sobreponham
					/*for (Snake snake : board.getSnakes()) {
						for (int i = 0; i < snake.getCells().size(); i++) {
							if (newCell.isEqual(snake.getCells().get(i))) {
								System.err.println("EM CHOQUE!!!");
								return;
							}
						}

					}*/
					//Move the snake to the new cell
					cells.addFirst(newCell);

					//Remove the last cell if snake exceeds its size
					if (cells.size() > size) {
						Cell tail = cells.removeLast();
						tail.release();
					}
				}
			}

			//Notify the GUI to change snake position
			board.setChanged();
			System.out.println("Snake " + getIdentification() + " moved to: [" + getCells().getFirst().getPosition().x + "," + getCells().getFirst().getPosition().y + "]");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isHumanSnake() {
		return false;
	}


	/*private void checkMovement(){
		for (Snake snake: getBoard().getSnakes()) {
			if( snake.getCells().equals())

		}
	}*/


}
