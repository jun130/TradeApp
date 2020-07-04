package com.example.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ItemData>itemData;

    public RecyclerViewAdapter(List<ItemData> itemData) {
        this.itemData = itemData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemData ld=itemData.get(position);
        holder.textemail.setText(ld.getEmail());
        holder.textnickname.setText(ld.getNickname());
        holder.texttitle.setText(ld.getTitle());
        holder.textcontent.setText(ld.getContent());
        holder.textprice.setText(ld.getPrice());
        holder.texttime.setText(ld.getTime());
        Picasso.get().load(ld.getimageUrl()).into(holder.itemimage);



    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView texttitle,textcontent,textprice,texttime,textemail,textnickname;
        private ImageView itemimage;
        public ViewHolder(View itemView) {
            super(itemView);
            textemail=(TextView)itemView.findViewById(R.id.list_email);
            textnickname=(TextView)itemView.findViewById(R.id.list_nickname);
            texttitle=(TextView)itemView.findViewById(R.id.list_title);
            textcontent=(TextView)itemView.findViewById(R.id.list_content);
            textprice=(TextView)itemView.findViewById(R.id.list_price);
            texttime=(TextView)itemView.findViewById(R.id.list_time);
            itemimage=(ImageView)itemView.findViewById(R.id.list_imageView);

        }
    }

}