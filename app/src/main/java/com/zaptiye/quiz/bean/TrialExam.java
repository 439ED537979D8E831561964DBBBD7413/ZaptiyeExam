package com.zaptiye.quiz.bean;

import java.io.Serializable;

/**
 * Created by Mitesh on 16/03/17.
 */

public class TrialExam implements Serializable{

    private String examPurchaseID, examName,examID;
    private float examCost;


    public void setExamPurchaseID(String id) {
        examPurchaseID = id;
    }


    public String getExamPurchaseID() {
        return examPurchaseID;
    }


    public void setExamID(String id) {
        examID = id;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamName(String name) {
        examName = name;
    }

    public String getExamName() {
        return examName;
    }


    public void setExamCost(float cost){
        examCost=cost;
    }

    public float getExamCost(){
        return examCost;
    }
}
