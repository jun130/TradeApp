package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BoardActivity {
    public String email= "";
    public String nickname = "";
    public String title = "";
    public String content = "";
    public String price = "";
    public String time = "";
    public String Url = "";
    private FirebaseStorage storage;
    public String postnum = "";
    /*
    private void postComment() {
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String authorName = user.username;

                String commentText = mCommentField.getText().toString();
                Comment comment = new Comment(uid, authorName, commentText);

                mCommentReference.push().setValue(comment);

                mCommentField.setText(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    */

    /*
   public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //String matchauthor = getEmail().replace('.',',');




        Bundle extras = getIntent().getExtras();
        //author = extras.getString("author");
        email = extras.getString("email");
        nickname = extras.getString("nickname");
        title = extras.getString("title");
        content = extras.getString("content");
        price = "희망가격:" + extras.getString("price");
        time = extras.getString("time");
        Url = extras.getString("imageUrl");
        postnum = extras.getString("postnum");

        TextView detailtitle = (TextView) findViewById(R.id.detail_title);
        TextView detailcontent = (TextView) findViewById(R.id.detail_content);
        TextView detailprice = (TextView) findViewById(R.id.detail_price);
        TextView detailtime = (TextView) findViewById(R.id.detail_time);
        TextView detailnickname = (TextView) findViewById(R.id.detail_nickname);
        TextView detaileamil = (TextView) findViewById(R.id.detail_email);
        ImageView detailimage = (ImageView)findViewById(R.id.detail_image) ;

        detailtitle.setText(title);
        detailcontent.setText(content);
        detailprice.setText(price);
        detailtime.setText(time);
        detailnickname.setText(nickname);
        detaileamil.setText(email);
        Picasso.get().load(Url).into(detailimage);
        Button detail_CHAT = (Button) findViewById(R.id.detail_chat);
        Button detail_delete = (Button) findViewById(R.id.detail_delete);

        detail_CHAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                GlobalData.Otheremail = email;
                GlobalData.OtherName = nickname;
                startActivity(intent);
            }
        });

        detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    storage = FirebaseStorage.getInstance();
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(DetailActivity.this);
                    alert_confirm.setMessage("게시글을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    storage = FirebaseStorage.getInstance();
                                    storage.getReference().child("Postimages/" + email + postnum +".jpg").delete();
                                    final DatabaseReference boardReference = FirebaseDatabase.getInstance().getReference("/게시판/물품목록");
                                    boardReference.child(postnum).setValue(null);
                                    final DatabaseReference mypostReference = FirebaseDatabase.getInstance().getReference("/USER");
                                    mypostReference.child(email.replace(".",",")).child("내게시글목록").child(postnum).setValue(null);
                                    Toast.makeText(DetailActivity.this, "게시글이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                                }
                            }
                    );
                    alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(DetailActivity.this, "취소", Toast.LENGTH_LONG).show();
                        }
                    });
                    alert_confirm.show();

            }
        });


        if(email.equals(GlobalData.Myemail)) {
            detail_CHAT.setVisibility(View.GONE);
            detail_delete.setVisibility(View.VISIBLE);
        }
        //detailnickname.setVisibility(View.GONE);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
