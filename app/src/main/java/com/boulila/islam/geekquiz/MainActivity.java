package com.boulila.islam.geekquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView vHelloText;
    private EditText vNameInput;
    private Button vPlayButn;
    private User vUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE=40;
    private SharedPreferences vPreferences;
    private static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";
    private static final String PREF_KEY_SCORE="PREF_KEY_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vUser=new User();
        vPreferences=getPreferences(MODE_PRIVATE);
        vHelloText= (TextView) findViewById(R.id.activity_main_helloText);
        vNameInput= (EditText) findViewById(R.id.activity_main_inputText);
        vPlayButn=  (Button) findViewById(R.id.activity_main_play_btn);

        vPlayButn.setEnabled(false);//Desactiver le bouton
        this.greetUser();
        vNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vPlayButn.setEnabled(charSequence.toString().length()!=0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        vPlayButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { vUser.setUserName(vNameInput.getText().toString());
                vPreferences.edit().putString(PREF_KEY_FIRSTNAME, vUser.getUserName()).apply();
                //The user just clicked

                Intent gameActivityIntent=new Intent(MainActivity.this,GameActivity.class);
                startActivityForResult(gameActivityIntent,GAME_ACTIVITY_REQUEST_CODE);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE== requestCode && RESULT_OK == resultCode){
            //Fetch the score from the intent
            int score=data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
            vPreferences.edit().putInt(PREF_KEY_SCORE,score).apply();
            this.greetUser();
        }
    }


    private void greetUser(){
        String firstName = vPreferences.getString(PREF_KEY_FIRSTNAME,null);
        if(firstName != null) {
            int score=vPreferences.getInt(PREF_KEY_SCORE,0);
            String welcome = "Content de vous revoir "+firstName+""+"\nVotre dernier score était "+
                    score+", ferez-vous mieux cette fois ci?";
            vHelloText.setTextSize(15);
            vHelloText.setText(welcome);
            vNameInput.setText(firstName);
            vNameInput.setSelection(firstName.length());
            vPlayButn.setEnabled(true);
        }
    }
}
