package game;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;
import remote.GameStatus;
import remote.RemoteBoard;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
    public static final int PORTO = 8085;

    public class AceitaConexaoThread extends Thread{
        private ServerSocket serverSocket;
        private SnakeGui snakeGui;
        private ArrayList<GereCliente> gcThread;

        public AceitaConexaoThread(ServerSocket serverSocket, SnakeGui snakeGui){
            this.serverSocket = serverSocket;
            this.snakeGui = snakeGui;
            gcThread = new ArrayList<>();
        }

        public void run(){
            try{
                while(true){
                    Board board = snakeGui.getBoard();
                    Socket socket = serverSocket.accept();

                    GereCliente gc = new GereCliente(socket, this.snakeGui);
                    gc.start();
                    gcThread.add(gc);

                    System.out.println("AceitaConexao -- GereCliente");
                }
            }catch (IOException e) {
                e.printStackTrace();
                System.out.println("AceitaConexao -- CATCH EXCEPTION");
                return;
            }
        }
    }

    public class GereCliente extends Thread{
        private BufferedReader in;
        private ObjectOutputStream out;
        private Socket socket;
        private SnakeGui snakeGui;
        private GameStatus gameStatus;

        public class EnviaStatusThread extends Thread{
            public void run(){
                while (!snakeGui.getBoard().isFinished()){
                    try{
                        sendStatus();
                        sleep(Board.REMOTE_REFRESH_INTERVAL);
                    }catch (InterruptedException | IOException e){
                        e.printStackTrace();
                        return;
                    }
                }

                try {
                    sendStatus();
                    out.writeObject("FIM do JOGO");
                } catch (IOException e){
                    e.printStackTrace();
                    return;
                }
            }

            private void sendStatus() throws IOException{
                gameStatus = new GameStatus(snakeGui.getBoard());
                out.writeObject(gameStatus);
                out.reset();
            }
        }

        public GereCliente (Socket socket, SnakeGui snakeGui) throws IOException {
            this.socket = socket;
            this.snakeGui = snakeGui;
            fazConexao();
        }

        @Override
        public void run(){
            Thread sendStatus = new EnviaStatusThread();
            sendStatus.start();

                /*while (!snakeGui.getBoard().isFinished()){
                    recebeDirecao();
                }*/

            sendStatus.interrupt();
        }

        public void fazConexao() throws IOException{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        }

        /*public void recebeDirecao() throws IOException, InterruptedException{

        }*/
    }

    public void startServing() throws IOException, InterruptedException{
        ServerSocket serverSocket = new ServerSocket(PORTO);

        LocalBoard board = new LocalBoard();
        SnakeGui snakeGui = new SnakeGui(board, 600, 0);
        snakeGui.init();

        try{
            AceitaConexaoThread aceita = new AceitaConexaoThread(serverSocket,snakeGui);
            aceita.start();
            Thread.sleep(10000);
        } finally {
            serverSocket.close();
        }
    }
    public static void main(String[] args) {
        try{
            new Server().startServing();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
            return;
        }
    }




/*
    private ServerSocket serverSocket;
    public static final int PORTO = 8085;
    protected LocalBoard localBoard;

    // Método construtor para criar o Server sem GUI
    public Server(){
        System.out.println("Servidor criado!");
        this.localBoard = new LocalBoard();
        try{
            serverSocket = new ServerSocket(PORTO);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro na conexão... a fechar!");
            System.exit(1);
        }
    }

    // Método construtor para criar o Server com GUI
    public Server(LocalBoard board){
        System.out.println("Servidor criado!");
        this.localBoard = board;
        try{
            serverSocket = new ServerSocket(PORTO);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro na conexão... a fechar!");
            System.exit(1);
        }
    }
    //Server criado pelo LocalBoard para termos GUI;
    @Override
    public void run(){
        while (true){
            runServer();
        }
    }

    //Método principal para receber as conexões
    public void runServer(){
        while (true){
            try{
                waitForConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    // Cria um gestor de conexão por cada conexão
    public void waitForConnection() throws IOException{
        System.out.println("À espera de conexões... ");
        Socket conexao = serverSocket.accept();
        ConnectionHandler connectionHandler = new ConnectionHandler(conexao);
        connectionHandler.start();
    }


    // Classe que gere cada uma das conexões
    public class ConnectionHandler extends Thread{
        // Inicia a thread que espera que o jogo acabe e interrompe todos os players
        private BufferedReader in;
        private ObjectOutputStream out;
        private final Socket conexao;

        public ConnectionHandler(Socket conexao){
            this.conexao = conexao;
        }

        public void doConnections() throws IOException{
            in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            out = new ObjectOutputStream(conexao.getOutputStream());
        }

        public void serve() throws IOException{
            while(true){
                String str = in.readLine();
                if (str.equals("FIM")){
                    break;
                }
                System.out.println("Eco: " + str);
                //out.println(str);
            }
        }

        public void startServing() throws IOException{
            ServerSocket ss = new ServerSocket(PORTO);
            try {
                Socket socket = ss.accept();
                try {//ligação aceite
                    doConnections();
                    serve();
                }finally {// a fechar
                    socket.close();
                }
            }finally {
                ss.close();
            }
        }

        @Override
        public void run(){
            try {
                while (true) {
                    startServing();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LocalBoard board = new LocalBoard();
        SnakeGui game = new SnakeGui(board, 600, 0);
        game.init();
    }*/
}
