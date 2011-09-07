package dk.knord.chat.client.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextPane;

import dk.knord.chat.client.ChatClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import java.awt.Color;

public class ChatClientGUI {

	private JFrame frame;
	private JTextArea displayArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientGUI window = new ChatClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 452, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JTextPane inputArea = new JTextPane();
		inputArea.setBounds(12, 243, 422, 17);
		inputArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(inputArea);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(188, 268, 70, 25);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = inputArea.getText();
				ChatClient.sendInput(input);
			}
		});
		frame.getContentPane().add(btnSend);
		
		displayArea = new JTextArea();
		displayArea.setBounds(12, 0, 422, 231);
		frame.getContentPane().add(displayArea);
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public void setDisplay(String text) {
		displayArea.setText(displayArea.getText() + text);
	}
}
