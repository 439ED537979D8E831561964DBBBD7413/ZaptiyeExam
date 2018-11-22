package com.zaptiye.quiz.bean;

import java.io.Serializable;

/**
 * Created by Mitesh on 17/03/17.
 */

public class TrialQuestions implements Serializable {

    private String questionID, question, attemptedQuestionID,questionIDReported, optionA, optionB, optionC, optionD, optionE;
    private int attemptedAnswer,correctAnswer;

    public void setQuestionID(String id) {
        questionID = id;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setAttemptedAnswer(int answer) {
        attemptedAnswer = answer;
    }

    public int getAttemptedAnswer() {
        return attemptedAnswer;
    }

    public void setQuestion(String ques) {
        question = ques;
    }

    public String getQuestion() {
        return question;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setCorrectAnswer(int answer) {
        correctAnswer = answer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


    public void setAttemptedQuestionID(String id) {
        attemptedQuestionID = id;
    }

    public String getAttemptedQuestionID() {
        return attemptedQuestionID;
    }

    public void setQuestionIDReported(String qID){
        questionIDReported=qID;
    }

    public String getQuestionIDReported(){
        return questionIDReported;
    }

}
