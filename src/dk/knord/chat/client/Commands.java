/**
 * 
 */
package dk.knord.chat.client;

/**
 * @author zylex
 * 
 */
public class Commands {

	static final int CONNECT = 0;
	static final int DISCONNECT = 1;
	static final int MESSAGE = 2;
	static final int LIST = 3;
	static final int UNKNOWN = 4;
	static final int UNSUPPORTED = 5;
	static final int NO_SUCH_ALIAS = 6;
	static final int BYE = 7;

	static final String[] commands = new String[] { "CONNECT", "DISCONNECT",
			"MESSAGE", "LIST", "UNKNOWN", "UNSUPPORTED", "NO SUCH ALIAS", "BYE" };
	
	static int getCommand(String input) {
		for (int index = 0; index < commands.length; index++) {
			if (input.startsWith(commands[index]))
				return index;
		}
		return UNKNOWN;
	}

}
