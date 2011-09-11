package dk.knord.chat.client;

/**
 * @author zylex
 * 
 */
public class KNordHeaderFields {
	public static class ServerToClient {
		public static final int DISCONNECT = 0;
		public static final int MESSAGE = 1;
		public static final int LIST = 2;
		public static final int UNKNOWN = 3;
		public static final int UNSUPPORTED = 4;
		public static final int NO_SUCH_ALIAS = 5;

		public static final String[] commands = new String[] { "DISCONNECT",
				"MESSAGE", "LIST", "UNKNOWN", "UNSUPPORTED", "NO SUCH ALIAS" };

		public static int getCommand(String input) {
			int command = 3; // Unknown by default

			for (int i = 0; i < commands.length; i++) {
				if (input.startsWith(commands[i]))
					command = i;
			}
			return command;
		}
	}

	public static class ClientToServer {
		public static final int ERROR = -1;
		public static final int CONNECT = 0;
		public static final int DISCONNECT = 1;
		public static final int MESSAGE = 2;
		public static final int MESSAGE_ALL = 3;
		public static final int LIST = 4;
		public static final int UNKNOWN = 5;
		public static final int UNSUPPORTED = 6;
		public static final int BYE = 7;

		public static final String[] commands = new String[] { "CONNECT",
				"DISCONNECT", "MESSAGE", "MESSAGE ALL", "LIST", "UNKNOWN", "UNSUPPORTED", "BYE" };

		public static int getCommand(String input) {
			int command = -1; // Unknown by default

			for (int i = 0; i < commands.length; i++) {
				if (input.startsWith(commands[i]))
					command = i;
			}
			return command;
		}
	}
}
