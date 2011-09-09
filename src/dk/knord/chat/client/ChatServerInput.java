package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.corba.se.spi.activation.Server;

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
		while (ChatClient.running) {
			try {
				if (input.ready()) {
					String message = input.readLine();
					if (message.equals("")) {
						// execute the messageBuffer
					}
					else {
						//add message to buffer
						inputMessageBuffer.add(message);
					}
				
				
				/*
				// TODO next line problem if a bunch of new lines
				if (input.ready()) {
					StringTokenizer st = new StringTokenizer(input.readLine());

					switch (ServerToClient.getCommand(st.nextToken())) {
					case ServerToClient.DISCONNECT:
						ChatClient.disconnect();
						break;
					case ServerToClient.LIST:
						Vector<String> chatters = new Vector<String>(0);
						for (int i = 0; i < st.countTokens(); i++) {
							String chatter = st.nextToken();
							if (chatter.isEmpty())
								continue;

							chatters.add(chatter);
						}
						Collections.sort(chatters);
						ChatClient.listChatters(chatters);
						break;
					case ServerToClient.MESSAGE:
						st.nextToken();
						String msg = st.nextToken() + " says: ";
						while (st.hasMoreTokens()) {
							msg += st.nextToken();
						}
						ChatClient.printMsg(msg);

						// how do you know if its a 1to1 or a brodcast?
						// protocol does not allow it...
						
						break;
					case ServerToClient.NO_SUCH_ALIAS:
						ChatClient.noSuchAlias();
						break;
					case ServerToClient.UNSUPPORTED:
						ChatClient.unsupported();
						break;
					default:
						ChatClient.unknown();
					}*/
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
		if (inputMessageBuffer.isEmpty())
			ChatClient.printMsg("RECEIVED: Empty message from server.");
			ChatClient.sendToServer("UNKNOWN");
		String command = inputMessageBuffer.get(0);
		switch (ServerToClient.getCommand(command)) {
		default:
			// 
		}
	}

}
