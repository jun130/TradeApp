package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    //private ArrayAdapter<String> adapter;
    private String Myemail;
    private String Otheremail;
    private String MyName;
    private String OtherName;
    private int NotiCount = 0;
    private int cnt =0;
    private ListRecyclerAdapter adapter;

    private FirebaseDatabase firebaseDatabase = null;
    private DatabaseReference databaseReference = null;
    //private ChatData chatData = null;
    private Data data = new Data();
    private String datakey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Intent intent = getIntent();
        Myemail = GlobalData.Myemail;
        Otheremail = GlobalData.Otheremail;
        MyName = GlobalData.MyName;
        OtherName =GlobalData.OtherName;

        init();
        DatabaseAdd(Myemail);
       // DatabaseChange(MyName);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListRecyclerAdapter();
        recyclerView.setAdapter(adapter);


    }

    private void DatabaseAdd(final String Name1) {
        databaseReference.child("USER").child(Name1).child("ChatList").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                data = dataSnapshot.getValue(Data.class);
                adapter.addItem(data);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Data removedata = dataSnapshot.getValue(Data.class);
                //Log.e("type",removedata.getTitle()+ removedata.getContent()+type);
                if(removedata.getTitle().equals(data.getTitle()) && removedata.getContent().equals(data.getContent()) &&
                   removedata.getType() == 0 && data.getType() == 0){
                   // createNotification(data.getTitle(),data.getTitle2(), data.getContent(), data.getTime(), data.getResId());
                }
                else {
                    // 내 문자면 알림x
                }

                adapter.removeitem(removedata.getTitle2());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void createNotification(String title,String title2,String content, String intime,int resid) {
        NotiCount++;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.putExtra("user2", title2); //전달할 값
        notificationIntent.putExtra("user2eamil", title); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.transfer);
        builder.setLargeIcon((BitmapFactory.decodeResource(getResources(), resid)));

        //builder.setColor(Color.RED);
        builder.setContentIntent(contentIntent);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);
        builder.setNumber(NotiCount);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));

        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }

    private void removeNotification() {

        // Notification 제거
        NotiCount = 0;
        NotificationManagerCompat.from(this).cancel(1);
    }


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
}
