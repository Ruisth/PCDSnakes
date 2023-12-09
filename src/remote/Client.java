package remote;


import environment.Board;
import environment.LocalBoard;
import game.HumanSnake;
import game.Server;
import game.Snake;
import gui.SnakeGui;

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
	private RemoteBoard remote = new RemoteBoard();
	private Snake playerSnake;

	private GameStatus gameStatus;

	public Client(InetAddress endereco, int PORTO){
		super();
		this.endereco = endereco;
		this.PORTO = PORTO;
	}

	public void connectToServer() throws IOException{
		endereco = InetAddress.getByName("localhost");
		System.out.println("1!");
		socket = new Socket(endereco, Server.PORTO);
		System.err.println("Socket : " + socket);
		System.out.println("2!");
		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("3!");
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		System.out.println("4!");
	}

	public void sendMessages() throws IOException{
			//usar o keyevent ou keycode
	}

	public void revieveMessages() throws IOException, ClassNotFoundException {
		gameStatus = (GameStatus) in.readObject();
		remote.setBoard(gameStatus.getBoard());
		remote.setChanged();
	}


	public void runClient(){
		SnakeGui gui = new SnakeGui(remote, 600, 0);
		gui.init();

		try{
			connectToServer();
			System.out.println("Conectei");
			while (true) {
				revieveMessages();
				System.out.println("5555555555");
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



	public static void main(String[] args) throws UnknownHostException {
		// TODO
		Client cliente = new Client(InetAddress.getByName("localHost"), 8085);
		System.out.println("1111111111");
		cliente.runClient();
		System.out.println("222222222222222");
		System.out.println("333333333333333");
		System.out.println("4444444444");
	}

}
