package remote;


import game.Server;

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

	public Client() {

	}

	public void runClient(String address, int port) {
		try {
			connect(address, port);

			while (true) {

			}
		}
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
	// TODO
	}

}
