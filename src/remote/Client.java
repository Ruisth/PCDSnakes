package remote;


import game.Server;
import game.Snake;
import gui.SnakeGui;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {

	private ObjectInputStream input;
	private PrintWriter output;
	private Socket socket;
	private SnakeGui snakeGui;

	private RemoteBoard board;

	public Client(String nickName) {
		this.snakeGui = new SnakeGui(nickName);
	}

	public void runClient(String address, int port) {
		try {
			snakeGui.init();
			connect(address, port);
			while (true) {
				receive();
				send();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | ClassCastException e2){
			return;
        } finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	void send() throws IOException {
		String directionPressed = board.getPressedDirection();
		if (directionPressed != null) {
			output.println(directionPressed);
		}
	}

	void receive() throws IOException, ClassNotFoundException {
		LinkedList<Snake> read = (LinkedList<Snake>) input.readObject();
		snakeGui.getBoard().setSnakes(read);
	}

	void connect(String address, int port) throws IOException {
		if (address == "") {
			InetAddress adr = InetAddress.getByName(null);
			socket = new Socket(adr, Server.PORT);
		} else {
			socket = new Socket(address, port);
		}
		input = new ObjectInputStream(socket.getInputStream());
		output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
	}

	public static void main(String[] args) {
			new Client("Player 1").runClient("localhost",8081);
	}

}
