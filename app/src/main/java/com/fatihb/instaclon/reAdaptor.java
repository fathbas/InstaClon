package com.fatihb.instaclon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class reAdaptor extends RecyclerView.Adapter<reAdaptor.PostHolder> {

    private ArrayList<String> userN;
    private ArrayList<String> userC;
    private ArrayList<String> userU;

    public reAdaptor(ArrayList<String> userN, ArrayList<String> userC, ArrayList<String> userU) {
        this.userN = userN;
        this.userC = userC;
        this.userU = userU;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recy_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.email.setText(userN.get(position).toString());
        holder.com.setText(userC.get(position).toString());
        Picasso.get().load(userU.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return userN.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView email;
        TextView com;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.im);
            email = itemView.findViewById(R.id.name);
            com = itemView.findViewById(R.id.comments);
        }
    }

}
