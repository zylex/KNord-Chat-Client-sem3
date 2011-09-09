package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import dk.knord.chat.client.KNordHeaderFields.ServerToClient;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatServerInput implements Runnable {
	private BufferedReader input;
	private ArrayList<String> inputMessageBuffer;

	public ChatServerInput() {
		inputMessageBuffer = new ArrayList<String>(0);
	}

	@Override
	public void run() {
		while (ChatClient.isRunning()) {
			try {
				if (input.ready()) {
					String message = input.readLine();
					if (message != null) {
						System.out.println(message);
						if (message.equals("")) {
							// execute the messageBuffer
							System.out.println("why is this executed twice?");
							executeMessageBuffer();
						} else {
							// add message to buffer
							inputMessageBuffer.add(message);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}

	}

	/**
	 * @param connection
	 *            the connection to set
	 * @throws IOException
	 */
	public void setConnection(Socket connection) throws IOException {
		input = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
	}

	private void executeMessageBuffer() {
		if (inputMessageBuffer.isEmpty()) {
			ChatClient.printMsg("RECEIVED: Empty message from server.");
			// TODO next line causes an infinite loop with hte server.
			/*ChatClient
					.sendToServer(ClientToServer.commands[ClientToServer.UNKNOWN]);*/
		} else {
			String command = inputMessageBuffer.get(0);
			switch (ServerToClient.getCommand(command)) {
			case ServerToClient.LIST:
				Vector<String> chatters = new Vector<String>(0);
				for (int i = 1; i < inputMessageBuffer.size(); i++) {
					chatters.add(inputMessageBuffer.get(i));
				}
				ChatClient.listChatters(chatters);
				// ChatClient.printMsg("RECEIVED:"
				// + ServerToClient.commands[ServerToClient.LIST]);

				for (int index = 0; index < inputMessageBuffer.size(); index++) {
					ChatClient.printMsg("> " + inputMessageBuffer.get(index));
				}

				break;
			case ServerToClient.DISCONNECT:
				ChatClient
						.printMsg("RECEIVED:\n> DISCONNECT > Client is disconnecting...");
				ChatClient.disconnect();
				ChatClient.printMsg("> You are now disconnected");
				break;
			default:
				ChatClient.printMsg("RECEIVED:\n");
				for (int index = 0; index < inputMessageBuffer.size(); index++) {
					ChatClient.printMsg("> " + inputMessageBuffer.get(index)
							+ "\n");
				}
			}
		}
		inputMessageBuffer = new ArrayList<String>(0);
	}
}
