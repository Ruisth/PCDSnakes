package remote;

import environment.Board;

import java.io.Serializable;

public class GameStatus implements Serializable {
    private Board board;

    public GameStatus(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
