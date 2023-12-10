package game;

import environment.Board;
import environment.Cell;

import java.awt.event.KeyEvent;

/** Class for a remote snake, controlled by a human
  * 
  * @author luismota
  *
  */
public class HumanSnake extends Snake {

	 private boolean state;

	 private int direction;

	 public HumanSnake(int id,Board board) {
		super(id,board);
	}

	 public boolean isHumanSnake() {
		 return true;
	 }

	 @Override
	 public void run() {
		doInitialPositioning();
		state = true;
		while (true) {
			try {
				sleep(Board.PLAYER_PLAY_INTERVAL);
				//moveHuman();
			} catch (InterruptedException e) {
				e.printStackTrace();
				stopSnake();
			}
		}
	 }

	 public void moveHuman(int direction) {
			//receber direção e ir buscar a celula dessa direção
		 	//move(newCell);

		 if (direction == KeyEvent.VK_UP) {
			 try {
				 move(getBoard().getCell(getCells().getFirst().getPosition().getCellAbove()));
			 } catch (InterruptedException e) {
				 throw new RuntimeException(e);
			 }
		 } else if (direction == KeyEvent.VK_DOWN) {
			 try {
				 move(getBoard().getCell(getCells().getFirst().getPosition().getCellBelow()));
			 } catch (InterruptedException e) {
				 throw new RuntimeException(e);
			 }
		 } else if (direction == KeyEvent.VK_LEFT) {
			 try {
				 move(getBoard().getCell(getCells().getFirst().getPosition().getCellLeft()));
			 } catch (InterruptedException e) {
				 throw new RuntimeException(e);
			 }
		 } else if (direction == KeyEvent.VK_RIGHT) {
			 try {
				 move(getBoard().getCell(getCells().getFirst().getPosition().getCellRight()));
			 } catch (InterruptedException e) {
				 throw new RuntimeException(e);
			 }
		 }
	 }

	 private void stopSnake() {state = false;}

	 public void setDirection(int direction) {
		 this.direction = direction;
	 }

}
