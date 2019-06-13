package com.example.signupin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DetailActivity extends BoardActivity {
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

    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String matchauthor = getEmail().replace('.',',');
        String author = "";
        String title = "";
        String content = "";
        String price = "";
        String time = "";

        Bundle extras = getIntent().getExtras();
        author = extras.getString("author");
        title = extras.getString("title");
        content = extras.getString("content");
        price = extras.getString("price");
        time = extras.getString("time");

        TextView detailtitle = (TextView) findViewById(R.id.detail_title);
        TextView detailcontent = (TextView) findViewById(R.id.detail_content);
        TextView detailprice = (TextView) findViewById(R.id.detail_price);
        TextView detailtime = (TextView) findViewById(R.id.detail_time);

        detailtitle.setText(title);
        detailcontent.setText(content);
        detailprice.setText(price);
        detailtime.setText(time);


        Button detail_modify = (Button) findViewById(R.id.detail_modify);
        Button detail_delete = (Button) findViewById(R.id.detail_delete);

        detail_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
                startActivity(intent);
            }
        });
        detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(, ""+position, Toast.LENGTH_LONG).show();

            }
        });

        if(author != "qwer12@gmail,com") {
            detail_modify.setVisibility(Button.VISIBLE);
            detail_delete.setVisibility(Button.VISIBLE);
        }



    }
}
