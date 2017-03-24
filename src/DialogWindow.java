import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bitsinharmony.recognito.MatchResult;
import com.bitsinharmony.recognito.Recognito;
import com.bitsinharmony.recognito.VoicePrint;

import net.codejava.sound.SoundRecordingUtil;

public class DialogWindow extends JFrame {
	// region Variables
		private JPanel mainPanel;
		private JLabel lblPhrase;
		private JButton btnStopRegister;
		
		// File read Variables
		private File file = new File("text/text2.txt");
		private String[] lines;
		private String text = "";
		
		// Recognito Variables
		private Recognito<String> recognito;
		private VoicePrint print;
		private List<VoicePrint> printList = new ArrayList<VoicePrint>();
		
	//endregion
		
	public DialogWindow(SoundRecordingUtil recorder, File wavFile, String type, JFrame frame) throws FileNotFoundException {
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
		
		//File reader
		Random random = new Random();
		int rnd = random.nextInt(295) + 1;
		
		lines = new String[300];
		
		FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        try {
			String line;
			for (int i = 0; i < 300; i++) {
				line = reader.readLine();
				lines[i] = line;
			}
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        for (int n = rnd; n < rnd + 2; n++) {
        	text += lines[n];
        }
		
		// Phrase Text
		lblPhrase = new JLabel();
		lblPhrase.setLocation(25, 10);
		lblPhrase.setSize(650, 279);
		lblPhrase.setText("<html><p>" + text + "</p></html>");
		lblPhrase.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhrase.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblPhrase.setForeground(Color.WHITE);
		mainPanel.add(lblPhrase);
		
		// Button Stop Register
		btnStopRegister = new JButton("ZAKOÑCZ");
		btnStopRegister.setLocation(275, 300);
		btnStopRegister.setSize(150, 40);
		btnStopRegister.setFont(new Font("Verdana", Font.PLAIN, 22));
		mainPanel.add(btnStopRegister);
		
		// Action Listener for Button Stop Register
		btnStopRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (type.equals("Registration")) {
					stopRecordRegistration(recorder, wavFile);
					DialogWindow.this.setVisible(false);
					frame.setEnabled(true);
					frame.setVisible(true);
				} else if (type.equals("Identification")) {
					stopRecordIdentification(recorder, wavFile);
					DialogWindow.this.setVisible(false);
					frame.setEnabled(true);
					frame.setVisible(true);
					identification(frame);
				} else {
					return;
				}
			}
		});
	}
	
	/**
	 * Method that stops recording a voice sample for registration process.
	 */
	private void stopRecordRegistration(SoundRecordingUtil recorder, File wavFile) {
		try {
			recorder.stop();
			recorder.save(wavFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that stops recording a voice sample for identification process.
	 */
	private void stopRecordIdentification(SoundRecordingUtil recorder, File wavFile) {
		try {
			recorder.stop();
			recorder.save(wavFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that is responsible for identification process
	 */
	private void identification(JFrame frame) {
		// Recognito code
		recognito = new Recognito<>(16000.0f);
		try {
			List<File> filesInFolder = Files.walk(Paths.get("sounds/patterns")).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
			for (File file : filesInFolder) {
				String name = file.getName().replaceFirst("[.][^.]+$", "");
				recognito.createVoicePrint(name, file);
			}
			List<MatchResult<String>> matches;
			matches = recognito.identify(new File("sounds/samples/Person" + MainWindow.identificationCount + ".wav"));
			MatchResult<String> match = matches.get(0);
			for (File file : filesInFolder) {
				String name = file.getName().replaceFirst("[.][^.]+$", "");
				if(match.getKey().equals(name)) {
					ResultWindow rw = new ResultWindow(name, match, frame);
					rw.setVisible(true);
					frame.setEnabled(false);
				}
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
