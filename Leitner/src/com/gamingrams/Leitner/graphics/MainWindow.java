package com.gamingrams.Leitner.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import javax.swing.*;

import com.gamingrams.Leitner.logic.*;


public class MainWindow extends JFrame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5214205784535304585L;
	/* START Variables */
	private static QuestionSet qs;
	public static final String QS_PATH = "question_set.dat";
	public static final int 
		WINDOW_WIDTH	= 600, 
		WINDOW_HEIGHT 	= 800;
	/* END */
	
	/* START Window components */
	// Toolkit
	private Toolkit tk;
	// Containers.
	private Container c;
	// Panes
	private JTabbedPane tabbedPane;
	private JPanel 
		addQuestionsPanel, 
		askQuestionsPanel, 
		adminPane;
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		super("Leitnersystemet");
		/* Initializes the main window. */
		tk = Toolkit.getDefaultToolkit();
		c = getContentPane();
		//setLayout(new FlowLayout());
		
		
		/* Initialize the tabbed pane. */
		tabbedPane = new JTabbedPane();
        c.add(tabbedPane);
        // Add panes to it.
        addQuestionsPanel	= new AddQuestionPane(this);
        askQuestionsPanel	= new AskQuestionPane(this);
        adminPane 			= new AdminPane(this);
        // Add panels to pane.
        tabbedPane.addTab("Ask question", askQuestionsPanel);
        tabbedPane.addTab("Add question", addQuestionsPanel);
        tabbedPane.addTab("Show statistics", adminPane);
		
		
		/* Adding listeners. */
		addWindowListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		writeQuestionsetToFile(qs);
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		writeQuestionsetToFile(qs);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		qs = importQuestionsFromFile();
		
	}

	public static QuestionSet importQuestionsFromFile() {
		QuestionSet qs = new QuestionSet();
		File f = new File(QS_PATH);
		if ( ! (f.exists() && f.isFile()) ) return qs;
		
		try {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream(f) );
			qs = (QuestionSet) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return qs;
	}
	
	public static void writeQuestionsetToFile(QuestionSet qs) {
		
		if (qs == null) return;
		
		File f = new File(QS_PATH);
		
		try {
			ObjectOutputStream utfil = new ObjectOutputStream( new FileOutputStream( f ) );
			utfil.writeObject(qs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeQuestionsetToFile() {
		
		if (qs == null) return;
		
		File f = new File(QS_PATH);
		
		try {
			ObjectOutputStream utfil = new ObjectOutputStream( new FileOutputStream( f ) );
			utfil.writeObject(qs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getStatistics() {
		String s = "";
		
		for (Question q : qs.getQuestions()) {
			s += "[Question " + qs.getIndex(q) + "]\n";
			s += q.toString() + "\n\n";
		}
		
		return s;
	}
	
	public String getConciseStatistics() {
		String s = "";
		
		for (Question q : qs.getQuestions()) {
			s += "[Question " + qs.getIndex(q) + "]\n";
			s += "Question: " + q.getQuestion() + "\n";
			s += "Answer: " + q.getAnswer() + "\n";
			s += "\n";
		}
		
		return s;
	}
    
	public void addQuestion(String question, String answer) {
		qs.addQuestion( new Question(question, answer));
		writeQuestionsetToFile(qs);
	}
	
	public void removeQuestion(int index) {
		qs.removeQuestion(index);
		writeQuestionsetToFile(qs);
	}
	
	public void removeQuestion(Question q) {
		qs.removeQuestion(q);
		writeQuestionsetToFile(qs);
	}
	
	public Question getQuestion() {
		return qs.getQuestion();
	}
    
	public String getStatisticsLine(int index) {
		if (index < 0 || index > qs.getQuestions().size()) return null;
		
		String s = null;
		try {
			s = qs.getQuestions().get(index).toString();
		} catch (IndexOutOfBoundsException iobEx) {
			iobEx.printStackTrace();
		}
		return s;
	}
	
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
