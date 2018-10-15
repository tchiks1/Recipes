package com.dev.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dev.myapplication.app.AppConfig;
import com.dev.myapplication.app.AppController;
import com.dev.myapplication.helper.sessionuser;
import com.dev.myapplication.objet.receipts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements clickedfavoris{
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private receiptsAdapter adapter;
    private List<receipts> receiptsList;
    private List<receipts> arraylist;
    sessionuser session;
    SearchView searchView;
    String img,nom,descr;
    int id,gues,duration;FrameLayout content;
    HashMap<String,String> users;FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 startActivity(new Intent(Main2Activity.this,post_recette.class));
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);
        session=new sessionuser(Main2Activity.this);

        users=session.getUserDetails();

        content=(FrameLayout)findViewById(R.id.content_frame);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchView = (SearchView)findViewById(R.id.searchView);

        receiptsList = new ArrayList<>();
        arraylist = new ArrayList<>();
        //preparereceiptss();
        loadreceipts();
        adapter = new receiptsAdapter(this, receiptsList,this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(7), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        /*searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a=new Intent(Main2Activity.this,search.class);
                startActivity(a);

            }
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               /* adapter.filter(s);
                adapter.notifyDataSetChanged();*/
               receiptsList.clear();
                if (s != null) {
               for (receipts obj : arraylist) {
                       if (arraylist.contains(s)) {
                           
                         receiptsList.add(obj);
                       }
                    adapter.notifyDataSetChanged();
                   }
               }else {
                    receiptsList.addAll(arraylist);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        /*ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);*/

        NavigationView navigationView = findViewById(R.id.nav_view);
          navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){

                            case R.id.nav_favoris:
                                loadfavoris() ;
                                Snackbar.make(content,"favoris",Snackbar.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_logout:
                                session.logoutUser();
                                startActivity(new Intent (Main2Activity.this,login.class));
                                finish();
                                break;
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

    }

    private void loadfavoris() {

        // Tag used to cancel the request
        String tag_string_req = "req_favoris";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_FAVORIS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String val = ""; String val1 = "";
                Log.d("test", "Register Response: " + response);
                try {
                    //<< get json string from server
                    JSONArray jObj = new JSONArray(response);
                    for (int i =0; i<jObj.length();i++) {
                        JSONObject user = jObj.getJSONObject(i);
                        String img_receip = user.getString("img_user");
                        String name_receip = user.getString("nom_rec");
                        String duration = user.getString("duration");
                        String desc = user.getString("descp");
                        String guest = user.getString("guest");
                        String cookie_id = user.getString("id");
                        JSONObject ingr = user.getJSONObject("ingr");
                        if (ingr !=null) {
                            JSONArray qte = ingr.getJSONArray("qte");
                            JSONArray name = ingr.getJSONArray("name");


                            int a = qte.getJSONArray(0).getInt(1);
                            Log.e("testar", String.valueOf(a));


                            for (int j =0;j<name.length();j++){
                                val += name.getString(j)+";";
                                val1 += qte.getString(j)+";";}

                        }
                        //villes.add(new Villes(ville));

                        receiptsList.add(new receipts(name_receip,Integer.parseInt(duration),Integer.parseInt(cookie_id),desc,img_receip,Integer.parseInt(guest),val,val1));
                    }
                    Log.e("testar","qte "+val1+" name "+val);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) ;
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void loadreceipts() {
        // Tag used to cancel the request
        String tag_string_req = "req_get";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_RECEIPTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Register Response: " + response);
                try {
                    String val = ""; String val1 = "";
                    //<< get json string from server
                    JSONArray jObj = new JSONArray(response);
                    for (int i =0; i<jObj.length();i++) {
                        JSONObject user = jObj.getJSONObject(i);
                        String img_receip = user.getString("img_user");
                        String name_receip = user.getString("nom_rec");
                        String duration = user.getString("duration");
                        String desc = user.getString("descp");
                        String guest = user.getString("guest");
                        String cookie_id = user.getString("id");
                        JSONObject ingr = user.getJSONObject("ingr");
                        //if (ingr !=null){
                                JSONArray ingrd  = ingr.getJSONArray("qte");

                                JSONArray a=ingrd.getJSONArray(0);
                             //int a=qte.getJSONArray(0).getInt(0);

                            //Log.e("testar",String.valueOf(a));

                            /*for (int j =0;j<a.length();j++){
                                 val += a.getString(j)+",";
                                 //val1 += qte.getString(j)+",";
                            }*/
                       // }
                                //villes.add(new Villes(ville));

                        //receiptsList.add(new receipts(name_receip,Integer.parseInt(duration),Integer.parseInt(cookie_id),desc,img_receip,Integer.parseInt(guest)));
                        receiptsList.add(new receipts(name_receip,Integer.parseInt(duration),Integer.parseInt(cookie_id),desc,img_receip,Integer.parseInt(guest),val,val1));
                    }
                    Log.e("testar","qte "+val1+" name "+val);
                    arraylist.addAll(receiptsList);
                    Log.e("arraylist",arraylist.get(0).getName());
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                // params.put("id", users.get("id"));
                 params.put("id", "" +
                         "2");

                return checkParams(params);
            }
            private Map<String,String> checkParams(Map<String,String> map) {

                Iterator<Map.Entry<String,String>> it =map.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String,String> pairs = it.next();
                    if(pairs.getValue() == null){
                        map.put(pairs.getKey(),"");
                    }
                }
                return map;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Adding few receipts for testing
     */
    private void preparereceiptss() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        receipts a = new receipts("True Romance", 13, covers[0]);
        receiptsList.add(a);

        a = new receipts("Xscpae", 8, covers[1]);
        receiptsList.add(a);

        a = new receipts("Maroon 5", 11, covers[2]);
        receiptsList.add(a);

        a = new receipts("Born to Die", 12, covers[3]);
        receiptsList.add(a);

        a = new receipts("Honeymoon", 14, covers[4]);
        receiptsList.add(a);

        a = new receipts("I Need a Doctor", 1, covers[5]);
        receiptsList.add(a);

        a = new receipts("Loud", 11, covers[6]);
        receiptsList.add(a);

        a = new receipts("Legend", 14, covers[7]);
        receiptsList.add(a);

        a = new receipts("Hello", 11, covers[8]);
        receiptsList.add(a);

        a = new receipts("Greatest Hits", 17, covers[9]);
        receiptsList.add(a);
    }

    @Override
    public void clicked(boolean a, receipts mreceipts) {
        if (a){
           // Toast.makeText(Main2Activity.this,"active id = "+mreceipts.getId(),Toast.LENGTH_SHORT).show();
            if(session.checkLogin()){
            upload(mreceipts.getId(),Integer.parseInt(users.get("id")),1);}else
                {
                    Snackbar.make(content,"connectez vous",Snackbar.LENGTH_SHORT).show();
                }
        }else {
            //Toast.makeText(Main2Activity.this,"desactive id= "+mreceipts.getId(),Toast.LENGTH_SHORT).show();
            if(session.checkLogin()) {
                upload(mreceipts.getId(),Integer.parseInt(users.get("id")),0);
            }else{
                Snackbar.make(content,"connectez vous",Snackbar.LENGTH_SHORT).show();
            }
            }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.drawer_view,menu);

        return false;
    }*/
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void upload(final int id, final int cooker_id, final int en) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FAV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("testr", "Register Response: " + response);

                try {

                    //<< get json string from server
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if(en==1) {
                            Snackbar.make(content, "ajouté aux favoris", Snackbar.LENGTH_SHORT).show();
                        }else
                        {
                            Snackbar.make(content, "enlevé aux favoris", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Main2Activity.this,
                            "error_network_timeout",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(Main2Activity.this,
                            "error_AuthFailureError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Main2Activity.this,
                            "error_ServerError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(Main2Activity.this,
                            "error_NetworkError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(Main2Activity.this,
                            "error_ParseError",
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams()  {


                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("cookers_id", String.valueOf(cooker_id));
                params.put("id", String.valueOf(id));
                params.put("en", String.valueOf(en));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
