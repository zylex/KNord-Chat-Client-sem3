/**
 * 
 */
package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.GNOME.Accessibility.Command;

import dk.knord.chat.client.gui.ChatClientGUI;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClientInput {

	private PrintWriter output;
	private ArrayList<String> messageBuffer = new ArrayList<String>(0);

	public ChatClientInput() throws IOException {
		output = new PrintWriter(ChatClient.connection.getOutputStream());
	}

	public boolean handleInput(String userInput, ChatClientGUI gui) {
		// if blank line
		if (userInput.equals("")) {
			if (messageBuffer == null) {
				// display error message
			} else {
				// send all messages
				for (int index = 0; index < messageBuffer.size(); index++) {
					sendMessage(messageBuffer.get(index), gui);
				}
				sendMessage("", gui);
				messageBuffer = new ArrayList<String>(0);
			}

		} else {
			switch (Commands.getCommand(userInput)) {
			case Commands.CONNECT:
				ChatClient.connect();
				sendMessage(userInput, gui);
				sendMessage("", gui);
				break;
			case Commands.BYE:
			case Commands.DISCONNECT:
				sendMessage(Commands.commands[Commands.DISCONNECT], gui);
				sendMessage("", gui);
				ChatClient.disconnect();
				return false;
			}
			// add line to the buffer
			messageBuffer.add(userInput);
		}
		return true;
	}
	
	public void sendMessage(String message, ChatClientGUI gui) {
		output.println(message);
		gui.setDisplay("\nSENT: " + message);
		output.flush();
	}
	
}