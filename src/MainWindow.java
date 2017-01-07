import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import java.io.File;
import java.io.FileNotFoundException;

import javax.sound.sampled.LineUnavailableException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import net.codejava.sound.SoundRecordingUtil;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	//region Variables
	private JPanel mainPanel;
	
	private JLabel background;
	private JLabel lblRegistration;
	private JLabel lblIdentification;
	private JLabel lblAuthors;
	
	private ImageIcon backgroundImage;
	
	private JTextField textFieldName;
	private JTextField textFieldLastname;
	
	private TextPrompt tpName;
	private TextPrompt tpLastname;
	
	private JButton btnRegister1;
	private JButton btnRegister2;
	
	private String name;
	private String lastname;
	
	public static int identificationCount;
	
	private File wavFile;
	private SoundRecordingUtil recorder;
	private Thread recordThread;
			
	//endregion
	
	/**
	 * The class constructor.
	 */
	public MainWindow() {
		identificationCount = 0;
		
		// Frame setup
		setTitle("Biometric Speech Recognition System");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon_64.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 1000);
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		// Main Panel setup
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 794, 971);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(null);
		
		getContentPane().add(mainPanel);
		
		// Background Image setup
		try {
			backgroundImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("background_logo.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Registration Module setup
		
		// Registration Text
		lblRegistration = new JLabel("REJESTRACJA");
		lblRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistration.setFont(new Font("Verdana", Font.BOLD, 20));
		lblRegistration.setForeground(Color.WHITE);
		lblRegistration.setBounds(300, 80, 200, 30);
		mainPanel.add(lblRegistration);
		
		// Text Field for First Name
		textFieldName = new JTextField();
		textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldName.setFont(new Font("Verdana", Font.PLAIN, 26));
		textFieldName.setBounds(250, 120, 300, 40);
		textFieldName.setColumns(10);
		mainPanel.add(textFieldName);
		
		// Text Prompt for First Name
		tpName = new TextPrompt("Podaj imiÍ", textFieldName);
		tpName.setHorizontalAlignment(SwingConstants.CENTER);
		tpName.changeAlpha(0.2f);
		tpName.changeStyle(Font.ITALIC);
		
		// Text Field for Last Name
		textFieldLastname = new JTextField();
		textFieldLastname.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldLastname.setFont(new Font("Verdana", Font.PLAIN, 26));
		textFieldLastname.setBounds(250, 180, 300, 40);
		textFieldLastname.setColumns(10);
		mainPanel.add(textFieldLastname);
		
		//Text Prompt for Last Name
		tpLastname = new TextPrompt("Podaj nazwisko", textFieldLastname);
		tpLastname.setHorizontalAlignment(SwingConstants.CENTER);
		tpLastname.changeAlpha(0.2f);
		tpLastname.changeStyle(Font.ITALIC);
		
		// Button Register#1
		btnRegister1 = new JButton("Rejestruj prÛbkÍ g≥osu");
		btnRegister1.setFont(new Font("Verdana", Font.PLAIN, 22));
		btnRegister1.setBounds(250, 240, 300, 40);
		mainPanel.add(btnRegister1);
		
		// Identification Text
		lblIdentification = new JLabel("IDENTYFIKACJA");
		lblIdentification.setFont(new Font("Verdana", Font.BOLD, 20));
		lblIdentification.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdentification.setForeground(Color.WHITE);
		lblIdentification.setBounds(300, 700, 200, 30);
		mainPanel.add(lblIdentification);
		
		// Button Register#2
		btnRegister2 = new JButton("SPRAWDè G£OS");
		btnRegister2.setFont(new Font("Verdana", Font.PLAIN, 22));
		btnRegister2.setBounds(250, 750, 300, 40);
		mainPanel.add(btnRegister2);
		
		// Authors Text
		lblAuthors = new JLabel("Wykonali: Kamila Wasilewska, Kasia åwiπtek BrzeziÒska, Robert K≥Ûdka");
		lblAuthors.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthors.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblAuthors.setForeground(Color.WHITE);
		lblAuthors.setBounds(105, 940, 580, 30);
		mainPanel.add(lblAuthors);
		
		// Background setup (DO NOT MOVE - NEED TO BE LAST)
		background = new JLabel(backgroundImage);
		background.setBounds(-3, 5, 800, 1000);
		background.setLayout(new FlowLayout());
		mainPanel.add(background);
		
		btnRegister1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startRecordRegistration();
				DialogWindow dw;
				try {
					dw = new DialogWindow(recorder, wavFile, "Registration", MainWindow.this);
					dw.setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				setEnabled(false);
			}
		});
		
		btnRegister2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startRecordIdentification();
				DialogWindow dw;
				try {
					dw = new DialogWindow(recorder, wavFile, "Identification", MainWindow.this);
					dw.setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setEnabled(false);
			}
		});
		
	}
	
	/**
	 * Method that starts recording a voice sample for registration process.
	 */
	private void startRecordRegistration() {
		name = textFieldName.getText();
		lastname = textFieldLastname.getText();
		wavFile = new File("sounds/patterns/" + name + " " + lastname + ".wav");
		recorder = new SoundRecordingUtil();
		recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.start();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		});
		recordThread.start();
	}	
	
	/**
	 * Method that starts recording a voice sample for identification process.
	 */
	private void startRecordIdentification() {
		identificationCount += 1;
		wavFile = new File("sounds/samples/Person" + identificationCount + ".wav");
		recorder = new SoundRecordingUtil();
		recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.start();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		});
		recordThread.start();
	}
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
