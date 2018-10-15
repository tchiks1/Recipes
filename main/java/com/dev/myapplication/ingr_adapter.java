package com.dev.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.myapplication.objet.favoris;
import com.dev.myapplication.objet.ingredient;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ingr_adapter extends RecyclerView.Adapter<ingr_adapter.MyViewHolder>{
    private Context mContext;
    private List<ingredient> IngrList=new LinkedList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView add_btn;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nom_ingr);
            add_btn = (ImageView) view.findViewById(R.id.remove_ingr);
        }
    }

    public ingr_adapter(Context mContext, List<ingredient> IngrList) {
        this.mContext = mContext;
        this.IngrList =IngrList;
    }

    @Override
    public ingr_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingr, parent, false);

        return new ingr_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ingr_adapter.MyViewHolder holder, final int position) {


       // final ingredient ingr = IngrList.get(position);
        final ingredient ingr = this.IngrList.get(position);

        holder.name.setText(ingr.getName());

        holder.add_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ingredient contact = (ingredient) view.getTag();
                IngrList.remove(ingr);
                notifyDataSetChanged();
                Toast.makeText(mContext,String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return IngrList.size();
    }

}
