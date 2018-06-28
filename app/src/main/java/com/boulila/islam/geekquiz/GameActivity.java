package com.boulila.islam.geekquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView vQuestonTextView;
    private TextView vScoreTextView;
    private ProgressBar vProgressBar;
    //4 buttons
    private Button vAnswerBtn1;// answer 1
    private Button vAnswerBtn2;
    private Button vAnswerBtn3;
    private Button vAnswerBtn4;

    private QuestionBank vQuestionBank;
    private Question vCurrentQuestion;
    private int vNumberOfQuestion;
    private int vScore;
    private static final int numberOfQuestion=10;// nombre de questions utiliseee

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "CurrentScore";
    public static final String BUNDLE_STATE_QUESTION = "CurrentQuestion";
    public static final int INCREMENT_PROGRESS_BAR=100/numberOfQuestion;
    private boolean vEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //
        vQuestonTextView = (TextView) findViewById(R.id.activity_game_question_text);
        vScoreTextView=(TextView)findViewById(R.id.activity_Game_Score) ;
        vProgressBar=(ProgressBar)findViewById(R.id.activity_game_prb);
        vAnswerBtn1 = (Button) findViewById(R.id.activity_game_bnt1);
        vAnswerBtn2 = (Button) findViewById(R.id.activity_game_bnt2);
        vAnswerBtn3 = (Button) findViewById(R.id.activity_game_bnt3);
        vAnswerBtn4 = (Button) findViewById(R.id.activity_game_bnt4);

        vQuestionBank = this.generateQuestions();
        if (savedInstanceState != null) {
            vNumberOfQuestion = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            vScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
        } else {
            vNumberOfQuestion = 10;
            vScore = 0;
            vProgressBar.setProgress(0);
        }
        vEnableTouchEvents = true;



        //Use the tag to name the buttons
        vAnswerBtn1.setTag(0);
        vAnswerBtn2.setTag(1);
        vAnswerBtn3.setTag(2);
        vAnswerBtn4.setTag(3);

        vAnswerBtn1.setOnClickListener(this);
        vAnswerBtn2.setOnClickListener(this);
        vAnswerBtn3.setOnClickListener(this);
        vAnswerBtn4.setOnClickListener(this);


        vCurrentQuestion = vQuestionBank.getQuestion();
        this.displayQuestion(vCurrentQuestion);
        this.setScore();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_EXTRA_SCORE, vScore);
        outState.putInt(BUNDLE_STATE_QUESTION, vNumberOfQuestion);
        super.onSaveInstanceState(outState);
    }

    //on click method
    @Override
    public void onClick(View view) {
        int reponseIndex = (int) view.getTag();
        vProgressBar.incrementProgressBy(INCREMENT_PROGRESS_BAR);
        final Button btnClr = (Button) view; // Clicked Button !!! ( it's a view )
        if (reponseIndex == vCurrentQuestion.getAnswerIndex()) { //Good anwser
            btnClr.setBackgroundColor(Color.GREEN); // Set background Green - correct
            Toast.makeText(this, "Correcte", Toast.LENGTH_SHORT).show();
            vScore += 2;

            this.setScore();

        } else {
            //wrong answer
            btnClr.setBackgroundColor(Color.RED); // set back red - False
            /*set textColor white ( red with red hh ? ) */
            btnClr.setTextColor(Color.rgb(255, 255, 255));
            Toast.makeText(this, "Réponse Fausse", Toast.LENGTH_SHORT).show();

        }
        vEnableTouchEvents = false;



     new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            vEnableTouchEvents=true;
            vNumberOfQuestion--;

            if (vNumberOfQuestion == 0) {//End Of the game

                endGame();
            } else {
                vCurrentQuestion = vQuestionBank.getQuestion();
                displayQuestion(vCurrentQuestion);
                // it's time to reset color ?
                btnClr.setBackgroundResource(R.color.backGroungAnswer);
                btnClr.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
    },3000);//LENGTH_SHORT =3000


}
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return vEnableTouchEvents &&super.dispatchTouchEvent(ev);
    }
    //end game method
    private void endGame(){

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Bravo! Quiz terminé")
                .setMessage("Votre score est "+vScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //End the Activity
                        Intent intent =new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE,vScore);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }
    private void displayQuestion(final Question question){
        vQuestonTextView.setText(question.getQuestion());
        vAnswerBtn1.setText(question.getmChoiceList().get(0));
        vAnswerBtn2.setText(question.getmChoiceList().get(1));
        vAnswerBtn3.setText(question.getmChoiceList().get(2));
        vAnswerBtn4.setText(question.getmChoiceList().get(3));
        //resetColor();
    }
    private  void setScore()
    {
        String scoreText="SCORE: "+vScore+"/"+numberOfQuestion*2;
        vScoreTextView.setText(scoreText);
    }
    private QuestionBank generateQuestions(){
        Question question1=new Question("Qui est le créateur de langage Java?",
                Arrays.asList("Steve Jobs","James Gosling","Tim Cook","Bill Gates"),
                1);
        Question question2=new Question("En quelle année Mark Zuckerberg a t-il créé « The Facebook»?",
                Arrays.asList( "2002","2003","2004","2006"),1);
        Question question3 =new Question("Le Raspberry Pi est :",
                Arrays.asList("application Web de gestion pour les systèmes de gestion de base de données",
                        "un nano-ordinateur monocarte à processeur ARM ",
                        "environnement de développement pour développer des applications",
                        "une carte possède un microcontrôleur facilement programmable"),1);

        Question question4=new Question("Qui est la légende du monde de l'informatique/ordinateur?",
                Arrays.asList("Steve Jobs","Linus Torvalds","Larry Page","Bill Gates"),3);

        Question question5=new Question("Quel est le nombre de génération d'ordinateur?",
                Arrays.asList("4","5","6","7"),1);
        Question question6=new Question("Que signifie les trois lettres 'www' ?",
                Arrays.asList("world wide web","world web war","world wide wireless","web world widget"),0);
        Question question7=new Question("Quel protocole est utilisé pour transférer des fichiers sur Internet",
                Arrays.asList("Le FPS","Le FTP","Le FPT","Le XML"),1);
        Question question8=new Question("Grace a quel type de virus un hacker peut-il prendre le contrôle de votre ordinateur à distance ?",
                Arrays.asList("Macrovirus","Vers","Torjan","un autre"),2);
        Question question9 =new Question("A quoi sert un 'fire wall'?",Arrays.asList("Neutraliser les virus","Scanner les logiciels téléchargés",
                "Bloquer les connexions non autorisées","Supprimer les virus"),2);

        Question  question10 = new Question("Que signifie le A de ADSL ?",
                Arrays.asList("Asynchronous","Asymmetrical","Accelerator","Autre"),1);
        Question question11 = new Question("A quoi correspond 3Ghz d'un microprocesseur?",
                Arrays.asList("Capacité de stockage","Le nombre de transistors","La fréquence du signal d'horloge",
                        "La finesse de gravure"),2);
        Question question12=new Question("En quoi consiste « l'overclocking » ?",
                Arrays.asList("Mesurer les performances d'un microprocesseur",
                        "Combiner deux microprocesseurs","Augmenter la fréquence d'un microprocesseur",
                        "Augmenter la vitesse de traitement"),2);
        Question question13=new Question("Comment peut-on supprimer les données de formulaire ainsi que les identifiants et mots de passe enregistrés sur son navigateur web ?",
                Arrays.asList("En supprimant les cookies","En désinstallant le navigateur web",
                        "En supprimant l'historique de navigation","Autre méthode"),0);
        Question question14=new Question("Qu'est ce que le HTML ?",
                Arrays.asList("Un système de partage de fichiers via des réseaux sociaux","Un langage conçu pour représenter des pages web",
                        "Un logiciel de messagerie instantanée","Un ensemble d'équipements reliés entre eux pour échanger des informations"),1);

        return new QuestionBank(Arrays.asList(question1,question2,question3,question4,question5,question6,question7,
                question8,question9,question10,question11,question12,question13,question14));
    }
}
