package dk.knord.chat.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui implements IGui {

	private JFrame frmChatymacchatchatSoftware;
	private JTextField textField;
	private JButton btnSendMsg;
	private JTextArea textArea;

	private IClient client;
	
	public Gui(IClient cl) {
		initialize();
		
		client = cl;
	}

	private void initialize() {
		frmChatymacchatchatSoftware = new JFrame();
		frmChatymacchatchatSoftware.setTitle("ChatyMacChatChat Software");
		frmChatymacchatchatSoftware.setBounds(100, 100, 450, 300);
		frmChatymacchatchatSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frmChatymacchatchatSoftware.getContentPane().setLayout(gridBagLayout);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		frmChatymacchatchatSoftware.getContentPane().add(splitPane, gbc_splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		JList list = new JList();
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
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		textField = new JTextField();
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
		btnSendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.sendMsg(textField.getText());
				textField.setText("");
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
	public void appendMsg(String msg) {
		getTextArea().append(msg);
	}
	
	protected JTextArea getTextArea() {
		return textArea;
	}
}
