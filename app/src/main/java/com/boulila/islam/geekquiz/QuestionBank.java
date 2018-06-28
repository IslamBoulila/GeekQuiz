package com.boulila.islam.geekquiz;

import java.util.Collections;
import java.util.List;

/**
 * Created by Islam BOULILA on 11/01/2018.
 */

public class QuestionBank {
    private List<Question> vQuestionList;
    private int vNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        vQuestionList = questionList;
        // Shuffle the question list
        Collections.shuffle(vQuestionList);
        vNextQuestionIndex=0;
    }
    public Question getQuestion(){
        //verifier qu'on boucle au tours les questions
        if(vNextQuestionIndex == vQuestionList.size())
            vNextQuestionIndex=0;
        return vQuestionList.get(vNextQuestionIndex++);
    }
}
