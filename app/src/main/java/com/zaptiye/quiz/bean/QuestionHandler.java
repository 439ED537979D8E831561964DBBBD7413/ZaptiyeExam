package com.zaptiye.quiz.bean;

import java.util.ArrayList;
import java.util.List;

import com.zaptiye.quiz.dao.QuestionsDAO;

/**
 * Handler question bank or list of question or single answar question.
 * @author I-BALL
 *
 */
public class QuestionHandler {

	public static List<Question> lstQuestions = new ArrayList<Question>();
	
	public QuestionHandler(String packageName,int levelNo) {
		levelNo++;
		QuestionsDAO questions = new QuestionsDAO(packageName);
		lstQuestions = questions.getSingleAnswareQuestion(levelNo);
		
	}
	
}
