package remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	// TODO
}


/* Servidor da TingTing
public class Server {
    public class AcceptConnectionThread extends Thread {
        private ServerSocket ss;
        private GameGui_Server gameGui;
        private ArrayList<DealWithClient> dwcThreads;

        public AcceptConnectionThread(ServerSocket ss, GameGui_Server gameGui) {
            this.ss = ss;
            this.gameGui = gameGui;
            dwcThreads = new ArrayList<>();

        }

        public void run() {
            try {
                while (true) {

                    Game game = gameGui.getGame();
                    Socket socket = ss.accept();

                    HumanPlayer hp = new HumanPlayer(game.getNumberPlayers(), game, (byte) 5);
                    game.addPlayer(hp);

                    DealWithClient dwc = new DealWithClient(socket, this.gameGui, hp);
                    dwc.start();
                    dwcThreads.add(dwc);

                    System.out.println("AcceptConnectionThread -- DealWithClient");

                }
            } catch (InterruptedException | IOException e) {
//				e.printStackTrace();
                System.out.println("AcceptConnectionThread -- CATCH EXCEPTION");
//				for (DealWithClient d: dwcThreads)
//					d.
                return;
            }
        }
    }

    public class DealWithClient extends Thread {
        public class SendPlayerStatusThread extends Thread {
            public void run() {
                while (!gameGui.getGame().isGameFinished()) {
                    try {
                        sendStatus();
                        sleep(Game.REFRESH_INTERVAL);

                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                try {
                    sendStatus();
                    out.writeObject("END GAME");
                } catch (IOException e) {
//					e.printStackTrace();
                    return;
                }

            }

            private void sendStatus() throws IOException {
                ArrayList<PlayerStatus> ps = new ArrayList<PlayerStatus>();

                for (Player p : gameGui.getGame().getPlayers()) {
                    if (p.getCurrentCell() != null)
                        ps.add(new PlayerStatus(p));
                }

//				System.out.println("Server.SendPlayerStatusThread GETPLAYERS: " + gameGui.getGame().getPlayers()
//						+ "\n\n\n");
//				System.out.println("Server.SendPlayerStatusThread GETPLAYERSTATUS: " + ps + "\n\n\n");

                out.writeObject(ps);
            }
        }

        private BufferedReader in;
        private ObjectOutputStream out;
        private Socket socket;
        private GameGui_Server gameGui;
        private HumanPlayer HPlayer;

        public DealWithClient(Socket socket, GameGui_Server gameGui, HumanPlayer HPlayer) throws IOException {
            this.socket = socket;
            this.gameGui = gameGui;
            this.HPlayer = HPlayer;
            doConnections();
        }

        @Override
        public void run() {
            try {
                Thread sendStatus = new SendPlayerStatusThread();
                sendStatus.start();
//				threads.add(sendStatus);

                while (!gameGui.getGame().isGameFinished()) {
//					System.out.println("\n\nServer Waiting Directions .... \n\n");
                    receiveDirection();
                }

                sendStatus.interrupt();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("\n\nDealWithClient CATCH EXCEPTION\n\n");
                return;
            }
        }

        void doConnections() throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        }

        void receiveDirection() throws IOException, InterruptedException {
            String str = in.readLine();
            Direction direction = null;

            if (str != null) {
                switch (str) {
                    case "UP":
                        direction = Direction.UP;
                        break;
                    case "DOWN":
                        direction = Direction.DOWN;
                        break;

                    case "LEFT":
                        direction = Direction.LEFT;
                        break;

                    case "RIGHT":
                        direction = Direction.RIGHT;
                        break;
                }
            }

            if (direction != null) {
                System.out.println("Server.RECEIVEDIRECTION " + direction.name());
                HPlayer.playerPlacement(direction);
                gameGui.getBoardGui().clearLastPressedDirection();
            }
        }
    }

    public static final int PORTO = 8080;
//	private ArrayList<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        try {
            new Server().startServing();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    public void startServing() throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(PORTO);

        GameGui_Server gameGui = new GameGui_Server();
        Game game = gameGui.getGame();
        gameGui.init();

        try {
            AcceptConnectionThread act = new AcceptConnectionThread(ss, gameGui);
            act.start();
            Thread.sleep(Game.INITIAL_WAITING_TIME);
//			act.interrupt();
            game.start();

        } finally {
            ss.close();
        }
    }
}*/
