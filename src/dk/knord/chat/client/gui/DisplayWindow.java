package dk.knord.chat.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

import dk.knord.chat.client.ChatClient;
import dk.knord.chat.client.ChatClientInput;
import dk.knord.chat.client.KNordHeaderFields.ClientToServer;

public class DisplayWindow implements IDisplayWindow {

	private JFrame frmChatymacchatchatSoftware;
	private JTextField textField;
	private JButton btnSendMsg;
	private JTextArea textArea;
	private JList list;
	private JScrollPane scrollPane_1, scrollPane;
	private ArrayList<String> currentChatters = new ArrayList<String>(0);

	private ChatClientInput userInput;

	public DisplayWindow(ChatClientInput chatClientInput) {
		initialize();
		this.userInput = chatClientInput;

		frmChatymacchatchatSoftware.setVisible(false);
	}

	/**
	 * @return the frmChatymacchatchatSoftware
	 */
	public JFrame getJFrame() {
		return frmChatymacchatchatSoftware;
	}

	private void initialize() {
		frmChatymacchatchatSoftware = new JFrame();
		frmChatymacchatchatSoftware.setTitle("ChatyMacChatChat Software");
		frmChatymacchatchatSoftware.setBounds(100, 100, 450, 300);
		frmChatymacchatchatSoftware
				.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmChatymacchatchatSoftware.dispose();
		frmChatymacchatchatSoftware.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frmChatymacchatchatSoftware.getContentPane().setLayout(gridBagLayout);

		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		frmChatymacchatchatSoftware.getContentPane().add(splitPane,
				gbc_splitPane);

		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);

		scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		Vector<String> listData = new Vector<String>();
		list = new JList(listData);
		list.setForeground(Color.WHITE);
		list.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(list);
		splitPane.setDividerLocation(300);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frmChatymacchatchatSoftware.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					sendMessageToChatClientInput();
					break;
				default:
				}
			}
		});
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		// auto scroll
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		btnSendMsg = new JButton("Send");
		btnSendMsg.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"released");
		btnSendMsg.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent arg0) {
				sendMessageToChatClientInput();
			}
		});
		GridBagConstraints gbc_btnSendMsg = new GridBagConstraints();
		gbc_btnSendMsg.gridx = 1;
		gbc_btnSendMsg.gridy = 0;
		panel.add(btnSendMsg, gbc_btnSendMsg);
		// word wrapping
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	}

	protected JButton getBtnSendMsg() {
		return btnSendMsg;
	}

	@Override
	public synchronized void appendMsg(String msg) {
		getTextArea().append(msg + "\n");
	}

	protected JTextArea getTextArea() {
		return textArea;
	}

	public void setVisible(boolean b) {
		frmChatymacchatchatSoftware.setVisible(b);
		textField.requestFocusInWindow();
	}

	public synchronized void displayChatters(Vector<String> chatters) {
		if (!chatters.isEmpty()) {
			int size = currentChatters.size();
			if (size > 0) {
				ArrayList<String> temp = new ArrayList<String>(0);
				temp.addAll(chatters);
				ArrayList<String> temp2 = new ArrayList<String>(0);
				if (size < temp.size()) {
					for (int i = 0; i < temp.size(); i++) {
						for (int j = 0; j < size; j++) {
							if (temp.get(i).equals(currentChatters.get(j))) {
								temp2.add(temp.get(i));
								break;
							}
						}
					}
					temp.removeAll(temp2);
					if (temp.size() > 0) {
						String msg = temp.remove(0);
						while (!temp.isEmpty()) {
							msg += ", " + temp.remove(0);
						}
						msg += " connected to the chat room.";
						appendMsg(msg);
					} else {
						appendMsg("Not sure who logged on");
					}
				} else if (size > temp.size()) {
					for (int i = 0; i < size; i++) {
						for (int j = 0; j < temp.size(); j++) {
							if (currentChatters.get(i).equals(temp.get(j))) {
								temp2.add(currentChatters.get(i));
								break;
							}
						}
					}
					currentChatters.removeAll(temp2);
					if (currentChatters.size() > 0) {
						String msg = currentChatters.remove(0);
						while (!currentChatters.isEmpty()) {
							msg += ", " + currentChatters.remove(0);
						}
						msg += " disconnected from the chat room.";
						appendMsg(msg);
					} else {
						appendMsg("Not sure who logged off");
					}
				} else {
					appendMsg("Something went horribly wrong.");
					// someone logged on and someone else logged off, or the
					// user sent the request
				}
			}
			currentChatters.clear();
			currentChatters.trimToSize();
			currentChatters.addAll(chatters);
			list.setListData(chatters);
			// sometimes makes an array out of bounds exception if the gui is
			// not refreshed with a runnable using invokeLater() method.
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						scrollPane_1.validate();
						scrollPane_1.repaint();
					}
				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param userInput
	 *            the userInput to set
	 */
	public void setUserInput(ChatClientInput userInput) {
		this.userInput = userInput;
	}

	private void sendMessageToChatClientInput() {
		if (list.isSelectionEmpty()) {
			userInput
					.handleInput(ClientToServer.commands[ClientToServer.MESSAGE_ALL]);
			userInput.handleInput(textField.getText());
			userInput.handleInput("");
		} else {
			String dest = (String) list.getModel().getElementAt(
					list.getSelectedIndex());
			userInput
					.handleInput(ClientToServer.commands[ClientToServer.MESSAGE]
							+ " " + dest);
			userInput.handleInput(textField.getText());
			userInput.handleInput("");
		}
		textField.setText("");
		list.clearSelection();
	}

	public void setUser(String username) {
		frmChatymacchatchatSoftware.setTitle("ChatyMacChatChat Software: "
				+ username);
	}

	public void close() {
		ChatClient
				.sendToServer(ClientToServer.commands[ClientToServer.DISCONNECT]);
		ChatClient.setRunning(false);
		ChatClient.disconnect();
		frmChatymacchatchatSoftware.dispose();
	}

}
