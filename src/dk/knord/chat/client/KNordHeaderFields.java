package dk.knord.chat.client;

/**
 * @author zylex
 * 
 */
public class KNordHeaderFields {
	public static class Requests {
		public static final int Disconnect = 0;
		public static final int Message = 1;
		public static final int List = 2;
		public static final int Unknown= 3;
		public static final int Unsupported = 4;
		public static final int NoSuchAlias = 5;
		
		public static final String[] commands = new String[] 
				{ "DISCONNECT", "MESSAGE", "LIST", "UNKNOWN", 
			"UNSUPPORTED", "NO SUCH ALIAS" };
		
		public static int getCommand(String input) {
			int command = 3; // Unknown by default
			
			for (int i = 0; i < commands.length; i++) {
				if (input.startsWith(commands[i]))
					command = i;
			}
			
			return command;
		}
	}
	
	public static class Responses {
		public static final String Connect = "CONNECT";
		public static final String Disconnect = "DISCONNECT";
		public static final String Message = "MESSAGE";
		public static final String MessageAll = "MESSAGE ALL";
		public static final String List = "LIST";
		public static final String Unknown = "UNKNOWN";
		public static final String Unsupported = "UNSUPPORTED";
		
		public static final String[] commands = new String[] 
				{ "CONNECT", "DISCONNECT", "MESSAGE", "MESSAGE ALL", 
			"LIST", "UNKNOWN", "UNSUPPORTED" };
		
		public static int getCommand(String input) {
			int command = 3; // Unknown by default
			
			for (int i = 0; i < commands.length; i++) {
				if (input.startsWith(commands[i]))
					command = i;
			}
			
			return command;
		}
	}
}
