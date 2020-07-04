package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity{

    private static final String TAG = "BoardActivity";
    private FirebaseAuth firebaseAuth;


    private ArrayList<ItemData>itemData;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase = null;
    private DatabaseReference databaseReference = null;
    private Data data = new Data();

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();


        GlobalData.Myemail= getEmail().replace('.',',');
        DatabaseAdd(GlobalData.Myemail);
        final DatabaseReference nRef = FirebaseDatabase.getInstance().getReference("/USER/"+getEmail().replace('.',',')+"/Nickname");
        nRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Nickname n = new Nickname();
                        n = dataSnapshot.getValue(Nickname.class);
                        GlobalData.MyName = n.nickname;


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        itemData=new ArrayList<>();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/게시판/물품목록");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        ItemData i =snapshot.getValue(ItemData.class);
                        itemData.add(0,i);
                    }
                    adapter=new RecyclerViewAdapter(itemData);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemData idate = itemData.get(position);


                Intent intent = new Intent(getBaseContext(), DetailActivity.class);

                intent.putExtra("email", idate.getEmail());
                intent.putExtra("nickname", idate.getNickname());
                intent.putExtra("title", idate.getTitle());
                intent.putExtra("content", idate.getContent());
                intent.putExtra("price", idate.getPrice());
                intent.putExtra("time", idate.getTime());
                intent.putExtra("imageUrl", idate.getimageUrl());
                intent.putExtra("postnum",idate.getPostnum());
                Log.e("12333","12");
                Log.e("error",idate.getPostnum());

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));





        Button search_button = (Button) findViewById(R.id.search_button);
        final EditText search_edit = (EditText) findViewById(R.id.search_edit);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_search = search_edit.getText().toString();
                Intent intent = new Intent(BoardActivity.this, SearchBoardActivity.class);
                intent.putExtra("searchValue",get_search);
                startActivity(intent);
            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements  RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private BoardActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final BoardActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
               @Override
               public boolean onSingleTapUp(MotionEvent e) {
                   return true;
               }

               @Override
                public void onLongPress(MotionEvent e) {
                   View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                   if (child != null && clickListener != null) {
                       clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                   }
               }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    private void DatabaseAdd(final String Name1) {
        databaseReference.child("USER").child(Name1).child("ChatList").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                data = dataSnapshot.getValue(Data.class);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Data removedata = dataSnapshot.getValue(Data.class);
                //Log.e("type",removedata.getTitle()+ removedata.getContent()+type);
                if (removedata.getTitle().equals(data.getTitle()) && removedata.getContent().equals(data.getContent()) &&
                        removedata.getType() == 0 && data.getType() == 0) {
                    createNotification(data.getTitle(), data.getTitle2(), data.getContent(), data.getTime(), data.getResId());
                } else {
                    // 내 문자면 알림x
                }


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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.putExtra("user2", title2); //전달할 값
        notificationIntent.putExtra("user2email", title); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentTitle(title2);
        builder.setContentText(content);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.transfer);
        builder.setLargeIcon((BitmapFactory.decodeResource(getResources(), resid)));

        //builder.setColor(Color.RED);
        builder.setContentIntent(contentIntent);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));

        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }


}
