package remote;


import game.Server;
import gui.SnakeGui;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
            throw new RuntimeException(e);
        } finally {
			try {
				socket.close();
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

	void receive() {

	}

	void connect(String address, int port) throws IOException {
		if (address == "") {
			InetAddress adr = InetAddress.getByName(null);
			socket = new Socket(adr, Server.PORT);
		} else {
			socket = new Socket(address, Server.PORT);
		}
		input = new ObjectInputStream(socket.getInputStream());
		output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
	}

	public static void main(String[] args) {
		new Client("Player 1").runClient("localhost",25565);
	}

}
