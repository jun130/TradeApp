package com.example.signupin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                intent.putExtra("categoryValue","주방/생활");
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
                intent.putExtra("categoryValue","게임/완구");
                startActivity(intent);
            }
        });

    }
}
