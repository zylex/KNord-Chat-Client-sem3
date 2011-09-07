/**
 * 
 */
package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import dk.knord.chat.client.KNordHeaderFields.Requests;
import dk.knord.chat.client.KNordHeaderFields.Responses;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatClientInput {

	private PrintWriter output;
	private Socket connection;

	public ChatClientInput(Socket connection) throws IOException {
		this.connection = connection;
		output = new PrintWriter(connection.getOutputStream());
	}

	public boolean HandleInput(String userInput) {
		if (connection == null) {
			if (!userInput.startsWith(Responses.commands[Requests.])) {
				// display error message
			}
		} else {

			switch (Commands.getCommand(userInput)) {
			case Commands.CONNECT:
				// connect to the server
				ChatClient.connect();

				// send connect message to the server
				output.println(userInput);
				break;
			case Commands.BYE:
			case Commands.DISCONNECT:
				// send disconnect message to the server
				output.println(Commands.commands[Commands.DISCONNECT]); // use
																		// id or
																		// username
																		// depending
																		// on
																		// the
				// server side

				// close connection
				return false;
			case Commands.MESSAGE:
				StringTokenizer st = new StringTokenizer(userInput);
				// if there are more tokens

				// make sure there is an alias.
					// send message to server

					// if not display error message

				// wait for the actual message
					// send the message
				
				// if not display error message.
				break;
			case Commands.LIST:
				// send list message to the server.
				output.println(Commands.commands[Commands.LIST]);
				break;

			// command is unknown
			default:
				// display error message
			}
		}
		output.println();
		return true;
	}
}
