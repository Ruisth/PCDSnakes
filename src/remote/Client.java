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
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	}

	public void sendMessages() throws IOException{
		for (int i = 0; i < 10; i++){
			out.println("Ola " + i);
			String str = in.readLine();
			System.out.println(str);
		}
		out.println("FIM");
	}


	public void runClient(){
		SnakeGui gui = new SnakeGui(remote, 600, 0);
		gui.init();

		try{
			connectToServer();
			sendMessages();
		}catch (IOException e) { // ERRO
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
