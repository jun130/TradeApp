package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ChatActivity extends AppCompatActivity {


    private EditText editText;
    private Button sendButton;
    private String Myemail;
    private String Otheremail;
    private String MyName;
    private String OtherName;
    private String inTime;

    private RecyclerView recyclerView;
    private ChatRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase = null;
    private DatabaseReference databaseReference = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getApplicationContext(),BoardActivity.class);
                    startActivity(intent1);

                    return true;

                case R.id.navigation_category:
                    Intent intent2 = new Intent(getApplicationContext(),CategoryActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_boardwrite:
                    Intent intent3 = new Intent(getApplicationContext(),PostActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_chatlist:


                    Intent intent4 = new Intent(getApplicationContext(),ListActivity.class);
                    startActivity(intent4);
                    return true;

                case R.id.navigation_myprofile:
                    Intent intent5 = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent5);
                    return true;




            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        editText = (EditText) findViewById(R.id.message_edit);
        sendButton = (Button) findViewById(R.id.send_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Intent intent = getIntent();

        MyName = GlobalData.MyName;
        Myemail = GlobalData.Myemail;

        if(intent.getStringExtra("user2") == null){
            OtherName =GlobalData.OtherName;
            Otheremail =GlobalData.Otheremail;
        }
        else{
            OtherName = intent.getStringExtra("user2");
            Otheremail = intent.getStringExtra("user2email");
        }

        init();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inTime   = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());

                Data chatData = new Data(Myemail, MyName, editText.getText().toString(),inTime, R.drawable.blank_profile,0); // 이미지 넣을때 여기를 건드려야함
                Data chatData2 = new Data(Otheremail, OtherName, editText.getText().toString(),inTime, R.drawable.blank_profile,1);


                databaseReference.child("USER").child(Myemail).child("ChatRoom").child(Otheremail).push().setValue(chatData);
                databaseReference.child("USER").child(Otheremail).child("ChatRoom").child(Myemail).push().setValue(chatData);



                //  chatlist push data
                databaseReference.child("USER").child(Otheremail).child("ChatList").push().setValue(chatData);
                databaseReference.child("USER").child(Myemail).child("ChatList").push().setValue(chatData2);
                databaseReference.child("USER").child(Otheremail).child("ChatList").push().setValue(chatData);
                databaseReference.child("USER").child(Myemail).child("ChatList").push().setValue(chatData2);

                //  chatlist duplicate remove
                DuplicateRemove(Myemail,Otheremail);
                DuplicateRemove(Otheremail,Myemail);

                editText.setText("");


            }
        });


        databaseReference.child("USER").child(Myemail).child("ChatRoom").child(Otheremail).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Data data = dataSnapshot.getValue(Data.class);

                //Data data = new Data();



                if(data.getTitle().equals(Myemail)){
                    data.setType(1);
                }
                else{
                    int userimage = R.drawable.blank_profile;  // userimage 비교하기

                    data.setType(0);
                    if(userimage != data.getResId()){
                        data.setResId(userimage);
                    }
                }
                adapter.addItem(data);
                adapter.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(recyclerView,null,adapter.getItemCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatRecyclerAdapter();
        recyclerView.setAdapter(adapter);



    }

    private void DuplicateRemove(final String Name1 , String Name2){

        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("USER").child(Name1).child("ChatList");
        Query usersQuery = usersRef.orderByChild("title").equalTo(Name2);

        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int exist = 1;

                String temp= "";
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()){

                        if(exist == 0){

                            usersRef.child(temp).setValue(null);
                        }
                        exist=0;
                        temp = userSnapshot.getKey();


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }




}