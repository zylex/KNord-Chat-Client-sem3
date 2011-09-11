package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
				//if (input.ready()) {
					String message = input.readLine();
					if (message != null) {
						if (message.equals("")) {
							// execute the messageBuffer
							executeMessageBuffer();
						} else {
							// add message to buffer
							inputMessageBuffer.add(message);
						}
					}
				//}
			} catch (IOException e) {
				ChatClient.printMsg(e.getMessage());
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
		} else {
			String command = inputMessageBuffer.get(0);
			switch (ServerToClient.getCommand(command)) {
			case ServerToClient.LIST:
				Vector<String> chatters = new Vector<String>(0);
				for (int i = 1; i < inputMessageBuffer.size(); i++) {
					chatters.add(inputMessageBuffer.get(i));
				}
				ChatClient.listChatters(chatters);
				break;
			case ServerToClient.DISCONNECT:
				ChatClient
						.printMsg("> RECEIVED: DISCONNECT, Client is disconnecting...");
				ChatClient.disconnect();
				ChatClient.printMsg("> You are now disconnected");
				break;
			case ServerToClient.MESSAGE:
				StringTokenizer st = new StringTokenizer(inputMessageBuffer.get(0));
				st.nextToken();
				String dest = st.nextToken();
				String message = "From " + dest + ": " + inputMessageBuffer.get(1);
				if (inputMessageBuffer.size() > 2) {
					for (int index = 2; index < inputMessageBuffer.size(); index++) {
						message += "\n" + inputMessageBuffer.get(index);
					}
				}
				ChatClient.printMsg(message);
				break;
			case ServerToClient.NO_SUCH_ALIAS:
				ChatClient.printMsg("There is no such alias.");
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

	/**
	 * @return the input
	 */
	public BufferedReader getInput() {
		return input;
	}
}
