package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    private Context cont;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    void removeitem(String str){
        for(int i =0; i < listData.size()-1; i++){
            if(str== listData.get(i).getTitle2()){
                listData.remove(i);
                break;
            }
        }
    }


    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView11;
        private TextView textView2;
        private TextView TimeView;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            textView1 = itemView.findViewById(R.id.RoomID);
            textView11 = itemView.findViewById(R.id.email);
            textView2 = itemView.findViewById(R.id.TextView);
            TimeView = itemView.findViewById(R.id.message_time);
            imageView = itemView.findViewById(R.id.imageView);

        }

        void onBind(Data data) {

            textView1.setText(data.getTitle2());
            textView11.setText(data.getTitle());
            textView2.setText(data.getContent());
            textView11.setVisibility(View.GONE);
            TimeView.setText(data.getTime());
            imageView.setImageResource(data.getResId());
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                imageView.setClipToOutline(true);
            }

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            GlobalData.OtherName = textView1.getText().toString();
            GlobalData.Otheremail = textView11.getText().toString();
            v.getContext().startActivity(intent);

        }

    }



}