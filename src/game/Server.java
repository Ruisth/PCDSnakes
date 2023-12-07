package game;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private ServerSocket serverSocket;
    public static final int PORTO = 8080;
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
        private PrintWriter out;
        private final Socket conexao;

        public ConnectionHandler(Socket conexao){
            this.conexao = conexao;
        }

        public void doConnections() throws IOException{
            in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conexao.getOutputStream())),true);
        }

        public void serve() throws IOException{
            while(true){
                String str = in.readLine();
                if (str.equals("FIM")){
                    break;
                }
                System.out.println("Eco: " + str);
                out.println(str);
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
                startServing();
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        new Server();
    }
}
