package game;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;
import remote.GameStatus;
import remote.RemoteBoard;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Server {
    public static final int PORTO = 8085;

    public class AceitaConexaoThread extends Thread {
        private ServerSocket serverSocket;
        private SnakeGui snakeGui;
        private ArrayList<GereCliente> gcThread;

        public AceitaConexaoThread(ServerSocket serverSocket, SnakeGui snakeGui) {
            this.serverSocket = serverSocket;
            this.snakeGui = snakeGui;
            gcThread = new ArrayList<>();
        }

        public void run() {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();

                    HumanSnake snake = new HumanSnake(snakeGui.getBoard().getNumberSnakes(),snakeGui.getBoard());
                    snakeGui.getBoard().addSnake(snake);
                    snake.start();

                    System.out.println(snake.getIdentification() + "" + snake.isHumanSnake() + "" + snake.getCells());

                    GereCliente gc = new GereCliente(socket, this.snakeGui, snake);
                    gc.start();
                    gcThread.add(gc);

                    System.out.println("AceitaConexao -- GereCliente");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("AceitaConexao -- CATCH EXCEPTION");
                return;
            }
        }
    }

    public class GereCliente extends Thread {
        private BufferedReader in;
        private ObjectOutputStream out;
        private Socket socket;
        private SnakeGui snakeGui;
        private GameStatus gameStatus;
        private HumanSnake humanSnake;


        public class EnviaStatusThread extends Thread {
            public void run() {
                while (!snakeGui.getBoard().isFinished()) {
                     try {
                        sendStatus();
                        sleep(100);
                    } catch (InterruptedException | IOException e) {
                         e.printStackTrace();
                        return;
                    }
                }

                try {
                    sendStatus();
                    out.writeObject("FIM do JOGO");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            private void sendStatus() throws IOException {
                Board temp = snakeGui.getBoard();
                gameStatus = new GameStatus(temp.getBoard(), temp.getCells(),temp.getSnakes(),temp.getObstacles(),temp.getGoalPosition());
                //System.out.println("board: " + snakeGui.getBoard().toString());
                //Board boardteste = snakeGui.getBoard();
                out.writeObject(gameStatus);
                Board tempboard = snakeGui.getBoard();
                System.out.println(tempboard);
                out.reset();
            }
        }

        public GereCliente(Socket socket, SnakeGui snakeGui, HumanSnake humanSnake) throws IOException {
            this.socket = socket;
            this.snakeGui = snakeGui;
            this.humanSnake = humanSnake;
            fazConexao();
        }

        @Override
        public void run() {
            Thread sendStatus = new EnviaStatusThread();
            sendStatus.start();

                while (!snakeGui.getBoard().isFinished()) {
                    try {
                        recebeDirecao();
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }

            sendStatus.interrupt();
        }

        public void fazConexao() throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        }

        public void recebeDirecao() throws IOException, InterruptedException{
            String direction = in.readLine();
            humanSnake.moveHuman(direction);
        }
    }

    public void startServing() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(PORTO);

        LocalBoard board = new LocalBoard();
        SnakeGui snakeGui = new SnakeGui(board, 600, 0);
        snakeGui.init();

        try {
            AceitaConexaoThread aceita = new AceitaConexaoThread(serverSocket, snakeGui);
            aceita.start();
            Thread.sleep(10000);
            board.endGame();
        } finally {
            System.out.println("Socket fechou");
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Server().startServing();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}