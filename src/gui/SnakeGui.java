package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;
import game.Snake;

/**
 *  Class to create and configure GUI.
 *  Only the listener to the button should be edited, see TODO below.
 * 
 * @author luismota
 *
 */
public class SnakeGui implements Observer {
	public static final int BOARD_WIDTH = 800;
	public static final int BOARD_HEIGHT = 800;
	public static final int NUM_COLUMNS = 40;
	public static final int NUM_ROWS = 30;
	private JFrame frame;
	private BoardComponent boardGui;
	private Board board;

	public SnakeGui(Board board, int x,int y) {
		super();
		this.board=board;
		frame= new JFrame("The Snake Game: "+(board instanceof LocalBoard?"Local":"Remote"));
		frame.setLocation(x, y);
		buildGui();
	}

	private void buildGui() {
		frame.setLayout(new BorderLayout());

		boardGui = new BoardComponent(board);
		boardGui.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		frame.add(boardGui,BorderLayout.CENTER);

		JButton resetObstaclesButton=new JButton("Reset snakes' directions");
		resetObstaclesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				for (Snake snake : board.getSnakes()) {
					// Get the current head position of the snake
					Cell headPosition = snake.getCells().getFirst();
					// Get neighboring positions
					List<BoardPosition> neighboringPositions = board.getNeighboringPositions(headPosition);
					// Choose a random neighboring position
					int randomIndex = (int) (Math.random() * neighboringPositions.size());
					BoardPosition boardPosition = neighboringPositions.get(randomIndex);
					// Retrieve the corresponding Cell object for the chosen BoardPosition
					Cell newHeadPosition = board.getCell(boardPosition);
					// Move the snake to the new position
					try {
						snake.move(newHeadPosition);
					} catch (InterruptedException ex) {
						throw new RuntimeException(ex);
					}
					board.setChanged();
					System.out.println("Old Position : [" + headPosition.getPosition().x + "," + headPosition.getPosition().y + "]" +
							" | New position : [" + newHeadPosition.getPosition().x + "," + newHeadPosition.getPosition().y + "]");
				}
            }

		});
		frame.add(resetObstaclesButton,BorderLayout.SOUTH);


		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		frame.setVisible(true);
		board.addObserver(this);
		board.init();
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}
}
