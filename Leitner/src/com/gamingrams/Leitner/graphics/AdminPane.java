package com.gamingrams.Leitner.graphics;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AdminPane extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3319363767762988926L;

	private MainWindow parent;
	
	private JTextArea 	statisticsArea;
	private JScrollPane	statisticsScrollPane;
	private JButton 	updateButton, removeElementButton, searchButton;
	
	private JComboBox<String> statChoices;
	private final static String[] statChoicesAlterantives = {"Full", "Concise"};
	
	public AdminPane(MainWindow parent) {
		super( new FlowLayout() );
		this.parent = parent;
		
		/* Initializing text areas and buttons. */
		// Text area.
		statisticsArea = new JTextArea(43,50);
		statisticsArea.setEditable(false);
		statisticsScrollPane = new JScrollPane(statisticsArea);
		// Update button.
		updateButton = new JButton("Update");
		removeElementButton = new JButton("Remove a question");
		searchButton = new JButton("Search");
		// Statistics choice.
		statChoices = new JComboBox<>(statChoicesAlterantives);
		statChoices.setSelectedItem(0);
		/* Adding listeners. */
		updateButton.addActionListener(this);
		removeElementButton.addActionListener(this);
		searchButton.addActionListener(this);
		
		/* Adding components to the pane. */
		add(statisticsScrollPane);
		add(statChoices);
		add(updateButton);
		add(removeElementButton);
		add(searchButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == updateButton) {
			String chosenStatChoice = (String) statChoices.getSelectedItem();
			
			if (chosenStatChoice.equals(statChoicesAlterantives[0])) {
				update();
			} else
			if (chosenStatChoice.equals(statChoicesAlterantives[1])) {
				updateConcise();
			} else {
				
			}
		} else
		if (e.getSource() == removeElementButton) {
			this.setEnabled(false);
			
			String ans = JOptionPane.showInputDialog("Which question do you want to remove?");
			int index = -1;
			try {
				index = Integer.parseInt(ans);
			} catch (Exception ex) {
				
			}
			
			parent.removeQuestion(index);
			update();
			this.setEnabled(true);
			
		} else
		if (e.getSource() == searchButton) {
			this.setEnabled(false);
			
			String ans = JOptionPane.showInputDialog("Which question do you want to find?");
			String output;
			int index = -1;
			try {
				index = Integer.parseInt(ans);
			} catch (NumberFormatException nex) {
				nex.printStackTrace();
			}
			
			output = parent.getStatisticsLine(index);
			if (output != null) {
				output = "[Question " + ans + "]\n" + output;
			} else {
				output = "Question [" + ans + "] not found.";
			}
			statisticsArea.setText(output);
			this.setEnabled(true);
		}
	}
	
	public void update() {
		statisticsArea.setText( parent.getStatistics() );
	}
	
	public void updateConcise() {
		statisticsArea.setText( parent.getConciseStatistics() );
	}
	
	
	
	
}
