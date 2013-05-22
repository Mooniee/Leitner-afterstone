package com.gamingrams.Leitner.logic;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;


public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6516900344229758283L;
	
	public final static double[] ASK_TRESHOLD = {0.50, 0.60, 0.70, 0.80, 0.90};
	public final static int 
			MINUTE	= 60, 
			HOUR	= 60*MINUTE, 
			DAY		= 24*HOUR, 
			WEEK 	=  7*DAY, 
			MONTH 	=  4*WEEK;
	public final static long[] ANSWER_DURATION	= {
			0, 					// 0	   now
			MINUTE 	* 15	, 	// 1	10 min.	
			HOUR 			, 	// 2	 1 hour.
			HOUR 	* 6 	, 	// 3	 6 hours.
			DAY 			, 	// 4	 1 day.
			DAY 	* 3 	,	// 5	 3 days.
			WEEK 	* 1 	, 	// 6	 1 week.
			WEEK 	* 2 	, 	// 7	 2 weeks.
			MONTH 	* 1 	, 	// 8	 1 month.
		};
	
	private String question;
	private String answer;
	private int level;
	private List<DateTime> answertimes;
	private List<Boolean>  results;
	
	public Question(String question, String answer) {
		this.question	= question;
		this.answer		= answer;
		this.level		= 0;
		answertimes	= new ArrayList<>();
		results		= new ArrayList<>();
	}

	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public int getAnswerSize() {
		return results.size();
	}
	
	public int getRightAnswers() {
		int tot = 0;
		for (boolean ans : results) {
			if (ans == true) {
				tot++;
			}
		}
		return tot;
	}
	
	public int getWrongAnswers() {
		int tot = 0;
		for (boolean ans : results) {
			if (ans == false) {
				tot++;
			}
		}
		return tot;
	}
	
	private void calcLevel() {
		boolean lastAns = results.get(results.size()-1);

		if (lastAns == false) {
			level = 0;
		} else {
			if (level++ > ASK_TRESHOLD.length-1) level = ASK_TRESHOLD.length-1;
		}
	}
	
	public void answer(boolean answerState) {
		answertimes.add(DateTime.now());
		results.add(answerState);
		calcLevel();
	}
	
	public boolean isLongSinceAsked() {
		if (timeUntilAsked() == 0) return true;
		return false;
	}
	
	
	/**
	 *  This function gauges whether the question should be asked.
	 *  
	 *  A question should be asked on a logarithmic scale.
	 */
	public boolean isAskable() {
		int resSize = getAnswerSize();
		if (resSize == 0) return true;
		
		double profLevel = ((double) getRightAnswers())/resSize;
		if (
				profLevel < ASK_TRESHOLD[(int) Math.min(Math.max(level-1,0), ASK_TRESHOLD.length-1)] || 
				isLongSinceAsked()) {
			return true;
		}
		
		return false;
	}
	
	public long timeSinceLastAsked() {
		if (getAnswerSize() == 0) return 0L;
		DateTime now = DateTime.now();
		DateTime lastAsked = answertimes.get( getAnswerSize() -1 );
		
		return (new Duration(lastAsked, now)).getMillis();
	}
	
	public long timeUntilAsked() {
		double profLevel = ((double) getRightAnswers())/getAnswerSize();
		if (profLevel < ASK_TRESHOLD[(int) Math.min(Math.max(level-1,0), ASK_TRESHOLD.length-1)]) return 0L;
		
		return Math.max(ANSWER_DURATION[level]*1000 - timeSinceLastAsked(), 0);
	}
	
	
	public String toString() {
		String s;
		
		long days, hours, minutes, seconds = timeUntilAsked()/1000;
		days 	 = (long) Math.floor(seconds/(DAY));
		seconds -= days*DAY;
		hours 	 = (long) Math.floor(seconds/(HOUR));
		seconds -= hours*60*60;
		minutes  = (long) Math.floor(seconds/MINUTE);
		seconds -= minutes*60;
		
		NumberFormat npf = NumberFormat.getPercentInstance();
		
		
		s  =   "Right(" + getRightAnswers() + ")";
		s += ", Wrong(" + getWrongAnswers() + ")";
		s += ", Level(" + level + ")";
		s += "\nPercentage: " + npf.format(((double) getRightAnswers())/getAnswerSize());
		s += "\nWill ask in: " + days + " days and " + hours + ":" + minutes + ":" + seconds + " hours.";
		s += "\nQuestion: " + getQuestion();
		s += "\nAnswers: " + getAnswer();

		return s;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
