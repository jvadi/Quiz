package components;

//TODO - Work out how to deal with questions for active quizzes. At present a question can be altered in principle (Add lock to question?)

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class QuizImpl extends UnicastRemoteObject implements Quiz{

	private int quizID; //Set only by constructor
	private Player quizMaster; //Set only be constructor
	private String quizName;
	private List<Question> questions;
	private QuizStatus quizStatus;
	
	public QuizImpl(int id, String name, Player quizMaster) throws RemoteException {
		this.quizID = id;
		this.quizMaster = quizMaster;
		this.quizName = name;
		this.questions = new ArrayList<Question>();
		this.quizStatus = QuizStatus.INACTIVE;
	}
	
	
	//Getters/Setters
	@Override
	public String getQuizName() throws RemoteException {
		return quizName;
	}
	
	@Override
	public void setQuizName(String quizName) throws RemoteException{
		if (this.quizStatus != QuizStatus.INACTIVE)
			throw new IllegalStateException();
		else if (quizName == null)
			throw  new NullPointerException();
		else
			this.quizName = quizName;
	}

	@Override
	public QuizStatus getStatus() throws RemoteException{
		return quizStatus;
	}
	
	@Override
	public int getQuizID()throws RemoteException {
		return quizID;
	}
	@Override
	public Player getQuizMaster() throws RemoteException{
		return quizMaster;
	}
	
	@Override
	public List<Question> getQuestionList() throws RemoteException{
		return questions;
	}
	
	//standard methods
	@Override
	public String display() throws RemoteException {
		return "Quiz [quizID=" + quizID + ", quizMaster=" + quizMaster
				+ ", quizName=" + quizName + ", quizStatus=" + quizStatus + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizImpl other = (QuizImpl) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (quizID != other.quizID)
			return false;
		if (quizMaster == null) {
			if (other.quizMaster != null)
				return false;
		} else if (!quizMaster.equals(other.quizMaster))
			return false;
		if (quizName == null) {
			if (other.quizName != null)
				return false;
		} else if (!quizName.equals(other.quizName))
			return false;
		if (quizStatus != other.quizStatus)
			return false;
		return true;
	}
	
	//Main Quiz methods
	
	@Override
	public boolean addQuestion(Question question)throws RemoteException{
		if (question.equals(null))
			throw new NullPointerException();
		else if (this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.questions.add(question);
			return true;
		}
	}
	
	@Override
	public Question getQuestion(int id) throws RemoteException{
		return this.questions.get(id);
	}
	
	@Override
	public boolean removeQuestion(int id) throws RemoteException{
		if (id < 0 || id >= this.questions.size() || this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.questions.remove(id);
			return true;
		}
	}

	@Override
	public boolean swapQuestion(int id1, int id2) throws RemoteException{
		if (id1 < 0 || id1 >= this.questions.size() ||
			id2 < 0 || id2 >= this.questions.size() ||
			this.quizStatus != QuizStatus.INACTIVE) {
			return false;
		}else{
			Question q1 = this.questions.get(id1);
			Question q2 = this.questions.get(id2);
			this.questions.remove(id1);
			this.questions.add(id1, q2);
			this.questions.remove(id2);
			this.questions.add(id2,q1);
			return true;
		}
	}
	
	@Override
	public boolean activate() throws RemoteException{
		if (this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.ACTIVE;
			return true;
		}
	}
	
	@Override
	public boolean complete() throws RemoteException{
		if (this.quizStatus != QuizStatus.ACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.COMPLETED;
			return true;
		}
	}
}
