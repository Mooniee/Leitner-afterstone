package com.gamingrams.Leitner.graphics;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AddQuestionPane extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4136323930004913179L;

	private MainWindow parent;
	
	private JLabel lQuestion, lAnswer;
	private JScrollPane scQuestion, scAnswer;
	private JTextArea taQuestion, taAnswer;
	private JButton registerButton;
	

	
	public AddQuestionPane(MainWindow parent) {
		super(new FlowLayout());
		this.parent = parent;
		
		lQuestion = new JLabel("Question");
		
		taQuestion = new JTextArea(21, 45);
		taQuestion.setEditable(true);
		scQuestion = new JScrollPane(taQuestion);
		
		lAnswer = new JLabel(" Answer ");
		
		taAnswer = new JTextArea(21, 45);
		taAnswer.setEditable(true);
		scAnswer = new JScrollPane(taAnswer);
		
		registerButton = new JButton("Register question");
		
		/* Register listeners. */
		registerButton.addActionListener(this);
		
		add(lQuestion);
		add(scQuestion);
		add(lAnswer);
		add(scAnswer);
		add(registerButton);
		
		setVisible(true);
	}
	
	public void setQuestion(String s) {
		taQuestion.setText(s);
	}
	
	public void setAnswer(String s) {
		taAnswer.setText(s);
	}
	
	public String getAnswer() {
		return taAnswer.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			if ( !(taQuestion.getText().equals("") || taAnswer.getText().equals("")) ) {
				parent.addQuestion(taQuestion.getText(), taAnswer.getText());
				taQuestion.setText("");
				taAnswer.setText("");
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
