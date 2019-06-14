package com.example.signupin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    public String get_category = "";

    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
    /*
    public String getAuthor() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button post_apply = (Button) findViewById(R.id.post_apply);
        final EditText add_title = (EditText) findViewById(R.id.add_title);
        final EditText add_content = (EditText) findViewById(R.id.add_content);
        final EditText add_price = (EditText) findViewById(R.id.add_price);
        //final TextView category_select = (TextView) findViewById(R.id.category_select);
        final Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  get_category = category_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final DatabaseReference pnRef = FirebaseDatabase.getInstance().getReference("PostNum");
        pnRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e("error",dataSnapshot.toString());
                        Log.e("error11",snapshot.toString());
                        PostNum n = new PostNum();
                        n = dataSnapshot.getValue(PostNum.class);
                        GlobalData.setPostNum(n.postnum++);
                        Log.e("error",String.valueOf(n.postnum));
                        pnRef.setValue(n);
                        Log.e("1231231", String.valueOf(GlobalData.getPostNum()));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //온클릭리스너
        post_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 저장된 텍스트 String에 저장
                //String get_id = getEmail().replace('.',',');
                String get_title = add_title.getText().toString();
                String get_content = add_content.getText().toString();
                String get_price = add_price.getText().toString();
                SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String get_time = mFormat.format(System.currentTimeMillis());







                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("id", GlobalData.Myemail);
                result.put("nickname",GlobalData.MyName);
                result.put("title", get_title);
                result.put("content", get_content);
                result.put("price", get_price);
                result.put("time", get_time);
                result.put("category", get_category);
                result.put("postnum", String.valueOf(GlobalData.getPostNum()));

                //firebase 정의
                mDatabase = FirebaseDatabase.getInstance().getReference();
                //firebase에 저장
                mDatabase.child("/게시판/물품목록/"+GlobalData.getPostNum()).setValue(result);
                mDatabase.child("/USER/"+GlobalData.Myemail.replace('.',',')+"/내게시물목록/"+GlobalData.getPostNum()).setValue(result);


                Intent intent = new Intent(PostActivity.this, BoardActivity.class);
                startActivity(intent);

            }
        });
    }
}
