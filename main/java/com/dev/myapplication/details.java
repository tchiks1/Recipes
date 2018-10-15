package com.dev.myapplication;

import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.myapplication.app.AppConfig;
import com.dev.myapplication.objet.receipts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class details extends AppCompatActivity {

    receipts obj;
    TextView perso,dur,description,title;
    ImageView img;
    String person,duration,desc,image,nom,quantite;ListView list;
    ArrayAdapter Ad;
    List<String> val;
    List<String> liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        liste=new ArrayList<>();
        list=(ListView)findViewById(R.id.list_ingr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         val = new ArrayList<>();

        if (getIntent().getExtras() !=null){
            obj = (receipts)getIntent().getSerializableExtra("obj");
            if (obj !=null){
                String image = AppConfig.IMAGE+obj.getImg();
                Log.e("obj","img "+image+" duration "+obj.getDuration()+" guest "+obj.getGuest()+" name "+obj.getName());
                Glide.with(this).load(image).into(img);

                perso.setText(Integer.toString(obj.getGuest()));
                dur.setText(Integer.toString(obj.getDuration()));

                description.setText(obj.getDesc());
                title.setText(obj.getName());
                nom=obj.getName2();
                quantite=obj.getQtity();
            }}

            String[]tabname  = nom.split(";");
            String[]tabqte  = quantite.split(";");
            String obj;

            for (int i =0; i<tabname.length; i++){
            obj = tabname[i]+" "+tabqte[i];
            val.add(obj);
            Log.e("testar1"," val "+val);
        }
           // for ()
           /* liste=Arrays.asList(quantite.split(","));
            Collections.addAll(liste,nom.split(","));
            String[] from=liste.toArray(new String[liste.size()]);

        Ad= new ArrayAdapter(this,R.layout.item_ingrd,R.id.txt_ingr,from);*/
           String[] a = {"1","2","3"};
        Ad= new ArrayAdapter(this,R.layout.item_ingrd,R.id.txt_ingr,a);
        list.setAdapter(Ad);
    }


    private void init() {
        perso=(TextView)findViewById(R.id.person);
        dur=(TextView)findViewById(R.id.dur);
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.desc);
        img=(ImageView)findViewById(R.id.image_view_autocolaps_background);

        person=perso.getText().toString().trim();
        duration=dur.getText().toString().trim();
        desc=description.getText().toString().trim();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    }

