package remote;


import environment.Board;
import environment.LocalBoard;
import game.HumanSnake;
import game.Server;
import game.Snake;
import gui.ClientGui;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;

/** Remore client, only for part II
 *
 * @author luismota
 *
 */

public class Client {
	private InetAddress endereco;
	private ObjectInputStream in;
	private PrintWriter out;
	private Socket socket;
	private final int PORTO;

	private ClientGui clientGui;

	public Client(InetAddress endereco, int PORTO){
		super();
		this.endereco = endereco;
		this.PORTO = PORTO;
	}

	public void connectToServer() throws IOException{
		endereco = InetAddress.getByName("localhost");
		socket = new Socket(endereco, Server.PORTO);
		System.err.println("Socket : " + socket);
		in = new ObjectInputStream(socket.getInputStream());
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

	}

	public void sendMessages() throws IOException{
			//usar o keyevent ou keycode
	}

	public void receiveMessages() throws IOException, ClassNotFoundException {
		System.out.println("Recebi objeto");
		//Board boardteste = (Board) in.readObject();
		//System.out.println("board: " + boardteste.toString());
		//clientGui.getBoardGui().setBoard(boardteste);

		GameStatus gs = (GameStatus) in.readObject();

			clientGui.getBoard().setBoard(gs.getBoard());
			clientGui.getBoard().setCells(gs.getCells());
			clientGui.getBoard().setSnakes(gs.getSnakes());
			clientGui.getBoard().setObstacles(gs.getObstacles());
			clientGui.getBoard().setGoalPosition(gs.getGoalPosition());
			Board tempboard = clientGui.getBoard();
			System.out.println("Fiz setChanged");
			clientGui.getBoard().setChanged();
	}


	public void runClient(){
		RemoteBoard board = new RemoteBoard();
		clientGui = new ClientGui(board, 600, 0);
		clientGui.init();

		try{
			connectToServer();
			System.out.println("Conectei");

			while (true) {
				receiveMessages();
				//sendMessages();
			}
		}catch (IOException | ClassNotFoundException e) { // ERRO
			e.printStackTrace();
        } finally { // a fechar
			try{
				socket.close();
			} catch (IOException e){ // ERRO
				e.printStackTrace();
			}
		}
	}



	public static void main(String[] args) {
		try{
			new Client(InetAddress.getByName("localHost"), 8085).runClient();
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	}

}
