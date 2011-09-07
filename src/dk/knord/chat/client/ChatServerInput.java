/**
 * 
 */
package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatServerInput implements Runnable {

	Socket connection;
	BufferedReader input;

	public ChatServerInput(Socket connection) throws IOException {
		this.connection = connection;
		input = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
	}

	@Override
	public void run() {
		while (ChatClient.running) {
			// get input
			String textInput = "";
			try {
				textInput = input.readLine();
			} catch (IOException e) {
				// TODO something clever to come soon (maybe send error message
				// to gui)
				e.printStackTrace();
			}
			
			// update logic
			ChatClient.running = HandleInput(textInput);
			
			// update gui

		}
		// object disposal if needed

	}

	/**
	 * @param input
	 *            the String containing the command to be used
	 * @return
	 */
	public boolean HandleInput(String input) {
		switch (Commands.getCommand(input)) {
		case Commands.CONNECT:

			break;
		case Commands.BYE:
		case Commands.DISCONNECT:

			return false;
		case Commands.MESSAGE:

			break;
		case Commands.LIST:

			break;
		case Commands.UNKNOWN:

			break;
		case Commands.UNSUPPORTED:

			break;
		case Commands.NO_SUCH_ALIAS:

			break;
		default:

		}
		return true;
	}
}
