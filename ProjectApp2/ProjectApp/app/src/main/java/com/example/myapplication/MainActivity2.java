package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    private Button Chatbtn;
    private Button Listbtn;
    private EditText editText;
    private EditText editText2;
    private EditText editText01;
    private EditText editText02;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:



                    return true;

                case R.id.navigation_category:

                    return true;

                case R.id.navigation_boardwrite:


                    return true;

                case R.id.navigation_chatlist:

                    GlobalData.Myemail = editText.getText().toString();
                    GlobalData.Otheremail = editText2.getText().toString();
                    GlobalData.MyName = editText01.getText().toString();
                    GlobalData.OtherName = editText02.getText().toString();
                    Intent intent3 = new Intent(getApplicationContext(),ListActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.navigation_myprofile:

                    return true;



            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Listbtn= (Button) findViewById(R.id.listbtn);
        Chatbtn = (Button) findViewById(R.id.chatbtn);
        editText = (EditText) findViewById(R.id.idinput);
        editText2 = (EditText) findViewById(R.id.idinput2);
        editText01 = (EditText) findViewById(R.id.idinput01);
        editText02 = (EditText) findViewById(R.id.idinput02);

        Listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                GlobalData.Myemail = editText.getText().toString();
                GlobalData.Otheremail = editText2.getText().toString();
                GlobalData.MyName = editText01.getText().toString();
                GlobalData.OtherName = editText02.getText().toString();
                startActivity(intent);

            }
        });

        Chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                GlobalData.Myemail = editText.getText().toString();
                GlobalData.Otheremail = editText2.getText().toString();
                GlobalData.MyName = editText01.getText().toString();
                GlobalData.OtherName = editText02.getText().toString();
                startActivity(intent);

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
