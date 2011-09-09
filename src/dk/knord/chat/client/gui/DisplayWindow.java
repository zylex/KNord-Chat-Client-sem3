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

import dk.knord.chat.client.ChatClient;
import dk.knord.chat.client.ChatClientInput;
import dk.knord.chat.client.KNordHeaderFields.ClientToServer;

public class DisplayWindow implements IDisplayWindow {

	private JFrame frmChatymacchatchatSoftware;
	private JTextField textField;
	private JButton btnSendMsg;
	private JTextArea textArea;
	private JList list;
	private JScrollPane scrollPane_1;

	private ChatClientInput userInput;

	public DisplayWindow(ChatClientInput chatClientInput) {
		initialize();
		this.userInput = chatClientInput;

		frmChatymacchatchatSoftware.setVisible(false);
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
				ChatClient
						.sendToServer(ClientToServer.commands[ClientToServer.DISCONNECT]);
				ChatClient.disconnect();
				frmChatymacchatchatSoftware.dispose();
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

		JScrollPane scrollPane = new JScrollPane();
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
			list.setListData(chatters);
			// sometimes makes an array out of bounds exception if the gui is
			// not refreshed with a runnable using invokeLater() method.
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					scrollPane_1.revalidate();
					scrollPane_1.repaint();
				}
			});
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
		userInput.handleInput(textField.getText());
		textField.setText("");
	}

}
