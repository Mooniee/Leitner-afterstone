package com.gamingrams.Leitner.logic;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class QuestionSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7704033121947455634L;
	
	
	private List<Question> questions;
	private Random r;
	
	public QuestionSet() {
		questions = new LinkedList<>();
		r = new Random();
	}
	
	public void addQuestion(Question q) {
		if (q != null) {
			questions.add(q);
		}
	}
	
	public void removeQuestion(Question q) {
		questions.remove(q);
	}
	
	public void removeQuestion(int index) {
		if (index < 0 || index > questions.size()) return;
		questions.remove(index);
	}
	
	public int getIndex(Question q) {
		return questions.indexOf(q);
	}
	
	
	public Question getQuestion() {
		List<Question> potentialQuestions = new LinkedList<>();
		for (Question q : questions) {
			if (q.isAskable()) {
				potentialQuestions.add(q);
			}
		}
		
		if (potentialQuestions.size() == 0)
			return null;
		
		return potentialQuestions.get(r.nextInt(potentialQuestions.size()));
	}
	
	
	
	public List<Question> getQuestions() {
		return questions;
	}
}
