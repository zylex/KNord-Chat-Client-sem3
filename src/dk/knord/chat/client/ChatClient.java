package dk.knord.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClient {
	private static ChatClientInput userInput;
	private static ChatServerInput serverInput;
	static Socket connection;
	static boolean running;
	static int id;
	static String username; // one "static" user per client
	static final String serverIP = "localhost";
	static final int port = 4711;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load resources
		running = true;
		// specifications say to connect here.
		connect();
		Thread serverThread = new Thread(serverInput);
		serverThread.start(); // listen for server messages.

		// load gui

		// main loop
		while (running) {
			// get user input
			String in = "";

			// update logic
			running = userInput.HandleInput(in);

			// update gui

		}
		// object disposal if needed
		serverThread.stop(); // needed???
	}

	public ChatClient() {
	}

	static void connect() {
		disconnect();
		try {
			connection = new Socket(serverIP, port);
			userInput = new ChatClientInput(connection);
			serverInput = new ChatServerInput(connection);
		} catch (UnknownHostException e) {
			running = false; // stop program if error
			e.printStackTrace(); // should do something better
		} catch (IOException e) {
			running = false; // stop program if error
			e.printStackTrace(); // might do something better eventually
		}
	}

	static void disconnect() {
		// make sure there isnt already a connection
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO we should try to be clever here...
				e.printStackTrace();
			}
		}
	}

}
