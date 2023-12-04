package game;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import static gui.SnakeGui.*;

public class Server {
    public class Connection extends Thread {
        private ServerSocket ss;
        public SnakeGui gui;
        private ArrayList<DealWithClient> dwcT;

        public Connection(ServerSocket ss, SnakeGui gui) {
            this.ss = ss;
            this.gui = gui;
            dwcT = new ArrayList<>();
        }

        public void run() {
            try {
                while (true) {
                    Board board = gui.getBoard();
                    Socket socket = ss.accept();

                    HumanSnake hSnake = new HumanSnake(board.getNumberSnakes(),board);
                    board.addSnake(hSnake);

                    DealWithClient dwc = new DealWithClient(socket, this.gui, hSnake);
                    dwc.start();
                    dwcT.add(dwc);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public class DealWithClient extends Thread {

        public class SendSnakeStatusThread extends Thread {
            public void run() {
                while (!gui.getBoard().isFinished()) {
                    try {
                        send();
                        sleep(Board.REMOTE_REFRESH_INTERVAL);
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                       return;
                    }
                }

                try {
                    send();
                    output.writeObject("FIM");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            private void send() throws IOException {
                LinkedList<Snake> snakeStatus = new LinkedList<Snake>();

                for (Snake s : gui.getBoard().getSnakes()) {
                    if (s.getCells() != null)
                        snakeStatus.add(new Snake(s));
                }
            }
        }
        private BufferedReader input;
        private ObjectOutputStream output;
        private Socket socket;

        private SnakeGui gui;

        public DealWithClient(Socket socket, SnakeGui gui, HumanSnake hSnake) {
        }

        void doConnections() throws IOException {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new ObjectOutputStream(socket.getOutputStream());
        }
    }

    public static final int PORT = 25565;

    public static void main(String[] args) {
        try {
            new Server().startServing();
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startServing() throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(PORT);
        LocalBoard localBoard = new LocalBoard();
        SnakeGui snakeGui = new SnakeGui(localBoard,600,0);
        snakeGui.init();

        try {
        } finally {
            socket.close();
        }
    }
}