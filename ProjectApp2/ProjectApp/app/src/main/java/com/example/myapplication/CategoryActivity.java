package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Button category0 = (Button) findViewById(R.id.category0);
        Button category1 = (Button) findViewById(R.id.category1);
        Button category2 = (Button) findViewById(R.id.category2);
        Button category3 = (Button) findViewById(R.id.category3);
        Button category4 = (Button) findViewById(R.id.category4);
        Button category5 = (Button) findViewById(R.id.category5);
        Button category6 = (Button) findViewById(R.id.category6);
        Button category7 = (Button) findViewById(R.id.category7);
        Button category8 = (Button) findViewById(R.id.category8);



        category0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","카메라/가전");

                startActivity(intent);
            }
        });
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","가구");
                startActivity(intent);
            }
        });
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","컴퓨터");
                startActivity(intent);
            }
        });
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","의류/액세서리");
                startActivity(intent);
            }
        });
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","스포츠");
                startActivity(intent);
            }
        });
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","게임/완구");
                startActivity(intent);
            }
        });
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","음향/악기");
                startActivity(intent);
            }
        });
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","도서");
                startActivity(intent);
            }
        });
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CateBoardActivity.class);
                intent.putExtra("categoryValue","기타");
                startActivity(intent);
            }
        });



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
