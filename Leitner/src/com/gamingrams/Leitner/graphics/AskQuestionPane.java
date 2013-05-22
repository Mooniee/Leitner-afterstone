package com.gamingrams.Leitner.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.gamingrams.Leitner.logic.Question;


public class AskQuestionPane extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6837334667948345957L;
	private Question question;
	private MainWindow parent;
	
	private JLabel lQuestion, lAnswer;
	private JScrollPane scQuestion, scAnswer;
	private JTextArea taQuestion, taAnswer;
	
	/* Button stuff. */
	private JButton registerButton;
	private String enabledRegisterButton = "Answer", disabledRegisterButton = "New question";
	
	
	public AskQuestionPane(MainWindow parent) {
		super(new FlowLayout());
		this.parent = parent;
		
		lQuestion = new JLabel("Question");
		
		taQuestion = new JTextArea(21, 45);
		taQuestion.setEditable(false);
		taQuestion.setEnabled(true);
		taQuestion.setBackground(Color.LIGHT_GRAY);
		scQuestion = new JScrollPane(taQuestion);
		
		lAnswer = new JLabel(" Answer ");
		
		taAnswer = new JTextArea(21, 45);
		taAnswer.setEditable(false);
		taAnswer.setEnabled(true);
		scAnswer = new JScrollPane(taAnswer);
		taAnswer.setBackground(Color.LIGHT_GRAY);
		registerButton = new JButton(disabledRegisterButton);
		
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
			if (question != null) {
				this.setEnabled(false);
				int yourAnswer = JOptionPane.showConfirmDialog(null, question.getAnswer(), "Was your answer correct?", JOptionPane.YES_NO_CANCEL_OPTION);
				this.setEnabled(true);
				
				switch (yourAnswer) {
				case JOptionPane.YES_OPTION:
					question.answer(true);
					break;
				case JOptionPane.NO_OPTION:
					question.answer(false);
					break;
				default:
					return;
				}
				
				parent.writeQuestionsetToFile();
				
				if (! newQuestion()) {
					registerButton.setText(disabledRegisterButton);
					taAnswer.setEditable(false);
					taAnswer.setText("");
					taAnswer.setBackground(Color.LIGHT_GRAY);
					taQuestion.setText("");
					taQuestion.setBackground(Color.LIGHT_GRAY);
				}
			} else {
				newQuestion();
			}
		}
		
	}
	
	public boolean newQuestion() {
		question = parent.getQuestion();
		if (question == null) return false;
		
		registerButton.setText(enabledRegisterButton);
		taAnswer.setText("");
		taAnswer.setEditable(true);
		taAnswer.setBackground(Color.WHITE);
		taQuestion.setText(question.getQuestion());
		taQuestion.setBackground(Color.WHITE);
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
