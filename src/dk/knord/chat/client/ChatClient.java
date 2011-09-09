package dk.knord.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JOptionPane;

import dk.knord.chat.client.gui.DisplayWindow;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClient {
	private static ChatClientInput userInput;
	private static ChatServerInput serverInput;
	static Socket connection;
	static boolean running;
	static String username; // one "static" user per client
	static final String serverIP = "localhost";
	static final int port = 4711;
	private static DisplayWindow window;
	private static Thread serverThread = new Thread();

	public static void main(String[] args) {
		// start by getting a username

		username = JOptionPane.showInputDialog(null, "Enter a username: ",
				"ChatyMacChatChat Software", 1);
		// load resources
		running = true;
		userInput = new ChatClientInput();
		serverInput = new ChatServerInput();
		window = new DisplayWindow(userInput);
		// display a welcome message
		printMsg("KNord Chat Client\nWritten by John Frederiksen, Andrius Ordojan\n and Paul Frunza.");

		// specifications say to connect here.
		connect();

		window.setVisible(true); // show gui

	}

	/**
	 * @param username
	 *            the username to set
	 */
	public static void setUsername(String username) {
		ChatClient.username = username;
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
			sendToServer("CONNECT " + username);
		} catch (UnknownHostException e) {
			disconnect(); // stop program if error
			e.printStackTrace(); // should do something better
		} catch (IOException e) {
			disconnect(); // stop program if error
			e.printStackTrace(); // might do something better eventually
		}
	}

	public static void disconnect() {
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
		Collections.sort(chatters);
		window.displayChatters(chatters);
	}

	protected static void printMsg(String msg) {
		window.appendMsg(msg);
	}

	public static void sendToServer(String message) {
		// send message to the server
		userInput.sendLine(message);
		userInput.sendLine("");
	}

}
