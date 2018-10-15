package com.dev.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.myapplication.objet.favoris;

import java.util.List;

public class favorisAdapter extends RecyclerView.Adapter<favorisAdapter.MyViewHolder>{
    private Context mContext;
    private List<favoris> favorisList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public favorisAdapter(Context mContext, List<favoris> favorisList) {
        this.mContext = mContext;
        this.favorisList =favorisList;
    }

    @Override
    public favorisAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favoris_card, parent, false);

        return new favorisAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final favorisAdapter.MyViewHolder holder, final int position) {
        final favoris fav = favorisList.get(position);
        /*holder.title.setText(favoris.getName());
        holder.count.setText(String.valueOf(favoris.getDuration()));

        // loading receipts cover using Glide library
        Glide.with(mContext).load(favoris.getGuest()).into(holder.thumbnail);*/

        holder.thumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,details.class);
                mContext.startActivity(i);
                Toast.makeText(mContext,String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return favorisList.size();
    }

}
