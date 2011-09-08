package dk.knord.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import dk.knord.chat.client.gui.DisplayWindow;

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
	private static DisplayWindow window;
	private static Thread serverThread = new Thread();

	public static void main(String[] args) {
		
		// load resources
		running = true;
		userInput = new ChatClientInput();
		serverInput = new ChatServerInput();
		window = new DisplayWindow(userInput);
		// specifications say to connect here.
		connect();
		
		window.setVisible(true); // show gui
		
	}

	protected static void connect() {
		disconnect();
		try {
			connection = new Socket(serverIP, port);
			userInput.setConnection(connection);
			serverInput.setConnection(connection);
			window.setUserInput(userInput);
			serverThread.interrupt();
			serverThread = new Thread(serverInput);
			serverThread.start(); // listen for server messages.
			
		} catch (UnknownHostException e) {
			disconnect(); // stop program if error
			e.printStackTrace(); // should do something better
		} catch (IOException e) {
			disconnect(); // stop program if error
			e.printStackTrace(); // might do something better eventually
		}
	}

	protected static void disconnect() {
		// make sure there is a connection
		if (connection != null) {
			try {
				connection.close();
				running = false;
			} catch (IOException e) {
				// TODO we should try to be clever here...
				e.printStackTrace();
			}
		}
	}

	protected static void listChatters(Vector<String> chatters) {
		window.displayChatters(chatters);
	}

	protected static void printMsg(String msg) {
		window.appendMsg(msg);
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
