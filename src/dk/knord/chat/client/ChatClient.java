package dk.knord.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import dk.knord.chat.client.gui.DisplayWindow;
import dk.knord.chat.client.gui.IDisplayWindow;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClient implements IChatClient {
	private static ChatClientInput userInput;
	private static ChatServerInput serverInput;
	static Socket connection;
	static boolean running;
	static int id;
	static String username; // one "static" user per client
	static final String serverIP = "localhost";
	static final int port = 4711;
	private final IDisplayWindow displayWindow;

	public ChatClient(IDisplayWindow displayWindow) {
		this.displayWindow = displayWindow;

	}

	public static void main(String[] args) {
		IChatClient client = new ChatClient(displayWindow)
		IDisplayWindow window = new DisplayWindow();
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

	private static void connect() {
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

	protected static void disconnect() {
		// make sure there isn't already a connection
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO we should try to be clever here...
				e.printStackTrace();
			}
		}
	}

	protected static void listChatters(List<String> chatters) {
		//TODO implement
	}

	protected static void printMsg(String source, String msg) {
		// TODO Auto-generated method stub
	}

	public static void noSuchAlias() {
		// TODO Auto-generated method stub

	}

	public static void unsupported() {
		// TODO Auto-generated method stub

	}

	public static void unknown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMsg(String text) {
		// TODO Auto-generated method stub
		// send msg to server...
	}

}
