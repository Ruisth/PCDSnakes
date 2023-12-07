package remote;


import game.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/** Remore client, only for part II
 *
 * @author luismota
 *
 */

public class Client {
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	public void connectToServer() throws IOException{
		InetAddress endereco = InetAddress.getByName("localhost");
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
		try{
			connectToServer();
			sendMessages();
		}catch (IOException e) { // ERRO
			e.printStackTrace();
		}finally { // a fechar
			try{
				socket.close();
			} catch (IOException e){ // ERRO
				e.printStackTrace();
			}
		}
	}



	public static void main(String[] args) {
		// TODO
		new Client().runClient();
	}

}
