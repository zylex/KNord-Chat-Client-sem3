package dk.knord.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
	private static boolean running;
	static String username; // one "static" user per client
	static final String serverIP = "localhost";
	static final int port = 4711;
	private static DisplayWindow window;
	private static Thread serverThread = new Thread();
	private static final String WELCOME_MESSAGE = "KNord Chat Client Written by John Frederiksen, Andrius Ordojan and Paul Frunza.";

	public static void main(String[] args) {
		// start by getting a username

		username = JOptionPane.showInputDialog(null, "Enter a username: ",
				"ChatyMacChatChat Software", 1);
		if (username != null) {
			// load resources
			setRunning(true);
			userInput = new ChatClientInput();
			serverInput = new ChatServerInput();
			window = new DisplayWindow(userInput);
			// display a welcome message
			printMsg(WELCOME_MESSAGE);

			// specifications say to connect here.
			connect();

			window.setVisible(true); // show gui

			if (running == false) {
				printMsg("Shutting down in 2 seconds...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					window.close();
				}
				window.close();
			}
		}
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
			window.setUser(username);
		} catch (UnknownHostException e) {
			printMsg(e.getMessage() + " - UnknownHostException");
			running = false;
		} catch (IOException e) {
			printMsg(e.getMessage() + " - IOException");
			running = false;
		}
	}

	public static void disconnect() {
		// make sure there is a connection
		if (connection != null) {
			try {
				running = false;
				serverThread.interrupt();
				userInput.getOutput().close();
				serverInput.getInput().close();
				connection.close();
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

	public static void sendToServer(String message) {
		// send message to the server
		if (connection != null) {
			userInput.sendLine(message);
			userInput.sendLine("");
		}
	}

	/**
	 * @return the running
	 */
	public static boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public static void setRunning(boolean running) {
		ChatClient.running = running;
	}

}
