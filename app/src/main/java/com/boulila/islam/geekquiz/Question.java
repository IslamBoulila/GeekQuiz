package com.boulila.islam.geekquiz;

import java.util.List;

/**
 * Created by Islam BOULILA on 11/01/2018.
 */

public class Question {

    private String vQuestion;  //The question
    private List<String> vChoiceList;   //Answers choices
    private int vAnswerIndex;  //indice de la reponse correcte dans la liste

    public Question() {
    }

    public Question(String question, List<String> choiceList, int answerIndex) {
        vQuestion = question;
        vChoiceList = choiceList;
        vAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return vQuestion;
    }

    public void setQuestion(String question) {
        vQuestion = question;
    }

    public List<String> getmChoiceList() {
        return vChoiceList;
    }

    public void setChoiceList(List<String> mChoiceList) {
        this.vChoiceList = mChoiceList;
    }

    public int getAnswerIndex() {
        return vAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        vAnswerIndex = answerIndex;
    }
}
