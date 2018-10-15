package com.dev.myapplication;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.dev.myapplication.app.AppConfig;
import com.dev.myapplication.objet.receipts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class receiptsAdapter extends RecyclerView.Adapter<receiptsAdapter.MyViewHolder> {

    private Context mContext;
    clickedfavoris mclickedfavoris;
    private List<receipts> receiptsList;
    private ArrayList<receipts> arraylist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public ToggleButton overflow;

        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ToggleButton) view.findViewById(R.id.overflow);
        }
    }

    public receiptsAdapter(Context mContext, List<receipts> receiptsList, clickedfavoris mclickedfavoris) {
        this.mContext = mContext;
        this.receiptsList = receiptsList;
        this.mclickedfavoris = mclickedfavoris;
        this.arraylist = new ArrayList<receipts>();
        this.arraylist.addAll(receiptsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receipts_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final receipts receipts = receiptsList.get(position);
        holder.title.setText(receipts.getName());
        holder.count.setText(String.valueOf(receipts.getDuration()));
        String img = AppConfig.IMAGE +receipts.getImg() ;
        // loading receipts cover using Glide library
         Glide.with(mContext).load(img).into(holder.thumbnail);

         holder.thumbnail.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View view) {
                 Intent i=new Intent(mContext,details.class);
               i.putExtra("obj",receipts);
                 mContext.startActivity(i);

             }
         });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (holder.overflow.isChecked()){
                 mclickedfavoris.clicked(true,receipts);
                   //Toast.makeText(view.getContext(),"active",Toast.LENGTH_SHORT).show();
               }else {
                   mclickedfavoris.clicked(false,receipts);
                  // Toast.makeText(view.getContext(),"desactive",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    public void filter(String charText) {
        charText = charText.toLowerCase();
        receiptsList.clear();
        if (charText.length() == 0) {
            receiptsList.addAll(arraylist);
        } else {
            for (receipts wp : arraylist) {
                if (wp.getName().toLowerCase()
                        .contains(charText)) {
                    receiptsList.add(wp);
                }
            }
        }
       notifyDataSetChanged();
    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_receipts, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return receiptsList.size();
    }
}
