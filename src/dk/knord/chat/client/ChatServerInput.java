/**
 * 
 */
package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatServerInput implements Runnable {

	private Socket connection;
	private BufferedReader input;
	private boolean lastCommandIsMessage = false;

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
	 */
	public boolean HandleInput(String serverInput) {
		if (connection == null) {
			if (!serverInput.startsWith(Commands.commands[Commands.CONNECT])) {
				// display error message
			}
		} else {
			if (lastCommandIsMessage) {

				lastCommandIsMessage = false;
			} else {
				switch (Commands.getCommand(serverInput)) {
				case Commands.CONNECT:
					// connect to the server
					ChatClient.connect();

					// send connect message to the server
					// output.println(serverInput);
					break;
				case Commands.BYE:
				case Commands.DISCONNECT:
					// send disconnect message to the server
					// output.println(Commands.commands[Commands.DISCONNECT]);

					// close connection
					ChatClient.disconnect();
					return false;
				case Commands.MESSAGE:
					StringTokenizer st = new StringTokenizer(serverInput);
					st.nextToken();
					// if there are more tokens
					if (st.hasMoreTokens()) {
						// send message to server
						// output.println(serverInput);
						lastCommandIsMessage = true;
					} else {
						// if not display error message
					}

					break;
				case Commands.LIST:
					// send list message to the server.
					// output.println(Commands.commands[Commands.LIST]);
					break;

				// command is unknown
				default:
					// display error message
				}
			}
		}
		if (!lastCommandIsMessage) {
			
		}			
			// output.println();
			return true;
	}
}
