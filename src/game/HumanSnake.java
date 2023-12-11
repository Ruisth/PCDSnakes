package game;

import environment.Board;
import environment.Cell;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_RIGHT;

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

	 public void moveHuman(String direction) {
		 switch (direction) {
			 case "UP":
				 try {
					 if(getBoard().getCell(getCells().getFirst().getPosition().getCellAbove()) != null) {
						 move(getBoard().getCell(getCells().getFirst().getPosition().getCellAbove()));
					 }
				 } catch (InterruptedException e) {
					 throw new RuntimeException(e);
				 }
				 break;
			 case "DOWN":
				 try {
					 if(getBoard().getCell(getCells().getFirst().getPosition().getCellBelow()) != null) {
						 move(getBoard().getCell(getCells().getFirst().getPosition().getCellBelow()));
					 }
				 } catch (InterruptedException e) {
					 throw new RuntimeException(e);
				 }
				 break;
			 case "LEFT":
				 try {
					 if(getBoard().getCell(getCells().getFirst().getPosition().getCellLeft()) != null) {
						 move(getBoard().getCell(getCells().getFirst().getPosition().getCellLeft()));
					 }
				 } catch (InterruptedException e) {
					 throw new RuntimeException(e);
				 }
				 break;
			 case "RIGHT":
				 try {
					 if(getBoard().getCell(getCells().getFirst().getPosition().getCellRight()) != null) {
						 move(getBoard().getCell(getCells().getFirst().getPosition().getCellRight()));
					 }
				 } catch (InterruptedException e) {
					 throw new RuntimeException(e);
				 }
				 break;
			 default:
				 break;
		 }
	 }

	 private void stopSnake() {state = false;}

	 public void setDirection(int direction) {
		 this.direction = direction;
	 }

}
