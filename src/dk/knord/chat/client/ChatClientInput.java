/**
 * 
 */
package dk.knord.chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
			if (messageBuffer == null) {
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
		output = new PrintWriter(connection.getOutputStream());
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
			break;
		case ClientToServer.CONNECT:
			ChatClient.connect();
		default:
			ChatClient.printMsg("SENT:");
			for (int index = 0; index < messageBuffer.size(); index++) {
				sendLine(messageBuffer.get(index));
			}
			sendLine("");
		}
		messageBuffer.removeAll(messageBuffer);
	}

	private void sendLine(String msg) {
		output.println(msg);
		ChatClient.printMsg(msg);
		output.flush();
	}

}
