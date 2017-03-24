import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bitsinharmony.recognito.MatchResult;

public class ResultWindow extends JFrame {
	// region Variables
		private JPanel mainPanel;
		private JLabel lblResult;
		private JButton btnOk;
	// endregion
		
	public ResultWindow(String name, MatchResult<String> match, JFrame frame) {
		// Frame setup
		setUndecorated(true);
		setSize(700, 350);
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		// Main Panel setup
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 700, 350);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(null);
		
		getContentPane().add(mainPanel);
		
		// Result Text
		lblResult = new JLabel();
		lblResult.setSize(650, 100);
		lblResult.setLocation(25, 10);
		lblResult.setText("<html>Zidentyfikowano osobê jako<br><html>" + name + "<html><br><html>z prawdopodobieñstwem " + match.getLikelihoodRatio() + "%");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setFont(new Font("Verdana", Font.BOLD, 22));
		lblResult.setForeground(Color.WHITE);
		mainPanel.add(lblResult);
		
		// OK Button
		btnOk = new JButton("OK");
		btnOk.setLocation(275, 300);
		btnOk.setSize(150, 40);
		btnOk.setFont(new Font("Verdana", Font.BOLD, 22));
		mainPanel.add(btnOk);
		
		// Action Listener for Button OK
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultWindow.this.setVisible(false);
				frame.setEnabled(true);
				frame.setVisible(true);
			}
		});
	}
}
