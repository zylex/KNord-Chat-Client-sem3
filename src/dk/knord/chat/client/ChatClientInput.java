/**
 * 
 */
package dk.knord.chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import dk.knord.chat.client.KNordHeaderFields.ClientToServer;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClientInput {

	private PrintWriter output;
	private ArrayList<String> messageBuffer = new ArrayList<String>(0);

	public ChatClientInput() {
	}

	public void handleInput(String userInput) {
		// if blank line
		if (userInput.equals("")) {
			if (messageBuffer.isEmpty() || messageBuffer == null) {
				// display error message
				ChatClient.printMsg("Nothing to send.");
			} else {
				// send all messages
				sendMessages();
			}

		} else {
			// add line to the buffer
			messageBuffer.add(userInput);
		}
	}

	public void setConnection(Socket connection) throws IOException {
		output = new PrintWriter(connection.getOutputStream(), true); // auto
																		// flush
	}

	private void sendMessages() {
		switch (ClientToServer.getCommand(messageBuffer.get(0))) {
		case ClientToServer.ERROR:
			ChatClient.printMsg("ERROR: Invalid command.");
			break;
		case ClientToServer.BYE:
		case ClientToServer.DISCONNECT:
			sendLine(ClientToServer.commands[ClientToServer.DISCONNECT]);
			sendLine("");
			ChatClient.disconnect();
			ChatClient
					.printMsg("You have now been disconnected from the server.");
			break;
		case ClientToServer.CONNECT:
			StringTokenizer st = new StringTokenizer(messageBuffer.get(0));
			st.nextToken();
			ChatClient.setUsername(st.nextToken());
			ChatClient.connect();
			ChatClient.printMsg("You are now connected to the server.");
			sendAllLines();
			break;
		case ClientToServer.LIST:
			sendAllLines();
			ChatClient.printMsg("User list updated.");
			break;
		case ClientToServer.MESSAGE:
			st = new StringTokenizer(messageBuffer.get(0));
			st.nextToken();
			String dest = st.nextToken();
			String message = "To " + dest + ": " + messageBuffer.get(1);
			if (messageBuffer.size() > 2) {
				for (int index = 2; index < messageBuffer.size(); index++) {
					message += "\n" + messageBuffer.get(index);
				}
			}
			sendAllLines();
			ChatClient.printMsg(message);
			break;
		default:

		}
		messageBuffer.removeAll(messageBuffer);
	}

	public void sendLine(String msg) {
		output.println(msg);
		// ChatClient.printMsg("> " + msg);
	}

	private void sendAllLines() {
		for (int index = 0; index < messageBuffer.size(); index++) {
			sendLine(messageBuffer.get(index));
		}
		sendLine("");
	}

}
