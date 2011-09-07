package dk.knord.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dk.knord.chat.client.KNordHeaderFields.Requests;

/**
 * @author John Frederiksen, Paul Frunza, Andrius Ordojan
 * 
 */
public class ChatServerInput implements Runnable {
	private final Socket connection;
	private final BufferedReader input;

	public ChatServerInput(Socket connection) throws IOException {
		this.connection = connection;
		input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	@Override
	public void run() {
		while (ChatClient.running) {
			try {
				
				//TODO next line problem if a bunch of new lines
				if (input.ready()) {
					StringTokenizer st = new StringTokenizer(input.readLine());
					
					switch (Requests.getCommand(st.nextToken())) {
					case Requests.Disconnect:
						ChatClient.disconnect();
						break;
					case Requests.List:
						List<String> chatters = new ArrayList<String>();
						for (int i = 0; i < st.countTokens(); i++) {
							String chatter = st.nextToken();
							if (chatter.isEmpty()) continue;
							
							chatters.add(chatter);
						}
						
						ChatClient.listChatters(chatters);
						break;
					case Requests.Message:
						String source = st.nextToken();
						String msg = st.nextToken();
						ChatClient.printMsg(source, msg);
						
						// how do you know if its a 1to1 or a brodcast?
						break;
					case Requests.NoSuchAlias:
						ChatClient.noSuchAlias();
						break;
					case Requests.Unsupported:
						ChatClient.unsupported();
						break;
					default:
						ChatClient.unknown();
					}
				}
			} 
			catch 
			(IOException e) {
				e.printStackTrace(System.err);
			}
		}

		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
