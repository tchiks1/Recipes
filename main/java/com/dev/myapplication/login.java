package com.dev.myapplication;
//LOGIN

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dev.myapplication.app.AppConfig;
import com.dev.myapplication.app.AppController;
import com.dev.myapplication.helper.EvenementDataBase;
import com.dev.myapplication.helper.MyDataBase;
import com.dev.myapplication.helper.sessionuser;
import com.dev.myapplication.objet.cookers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class login extends Activity {
    //Lien vers ma page php sur mon serveur
    private EditText UserEditText;
    private EditText passEditText;
    public TextView incrip;
    int id; MyDataBase db;
    // User Session Manager Class
    String email;
    sessionuser session;
    LinearLayout liner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session =new sessionuser(login.this);

        db=new MyDataBase(this);
        if (session.isUserLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(login.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }

        // Récupération des éléments de la vue définis dans le xml
        UserEditText = (EditText) findViewById(R.id.loginedit_name);
        passEditText = (EditText) findViewById(R.id.loginedit_passwd);
        liner = (LinearLayout) findViewById(R.id.liner);

        incrip = (TextView) findViewById(R.id.btn_login_register);
        Button button = (Button) findViewById(R.id.btn_login);

        // Definition du listener du bouton
        incrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // appel de la classe Inscription
                startActivity(new Intent(login.this, register.class));
            }
        });

        // Definition du listener du bouton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int usersize = UserEditText.getText().length();
                int passsize = passEditText.getText().length();
                // si les deux champs sont remplis
                if (usersize > 3 && passsize > 3) {
                    String user = UserEditText.getText().toString();
                    String pass = passEditText.getText().toString();
                    // on appelle la fonction doLogin qui va communiquer avec le PHP
                   // doLogin(user, pass);
                    valid(user,pass);
                    Snackbar.make(liner, user+"do"+pass, Snackbar.LENGTH_SHORT).show();

                } else
                    Snackbar.make(liner, "le nom ou le mot de passe n'a pas une taille correcte.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doLogin(final String login,final String pass){

        final String pw;
        // creation d'un thread
        Thread t = new Thread(){
            public void run(){

                Looper.prepare();
                // On se connecte au serveur afin de communiquer avec le php

                try {
                    URL url = new URL(AppConfig.URL_LOGIN);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");

                    urlConnection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    String user,pass;

                        user = UserEditText.getText().toString();
                        pass = passEditText.getText().toString();
                        //on passe les parametres Login et Password  recuperables par le script php en post
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", user);
                        jsonObject.put("password", pass);

                        out.write(jsonObject.toString());
                        out.flush();
                        out.close();

                        UserEditText.setText("");
                        passEditText.setText("");

                        Integer response;

                        urlConnection.setDoInput(true);
                        urlConnection.setRequestMethod("GET");

                        JSONObject jsonObject1 = new JSONObject();

                        response = jsonObject1.getJSONObject("user").getInt("SUCCESS");

                        id = jsonObject1.getJSONObject("user").getInt("id");
                        email= jsonObject1.getJSONObject("user").getString("email");

                        Log.e("reponse",String.valueOf(response));
                        Log.e("email",String.valueOf(email));

                        if (response== 1){
                            session.createUserLoginSession(String.valueOf(id),user,pass);
                            // inserer les donnees de la table cookers dans la sqlite.
                            cookers cook=new cookers(id,user,email,pass);
                            EvenementDataBase.insert(cook);

                            // Starting MainActivity
                            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                            finish();
                        }
                        else {
                            Snackbar.make(liner,"Erreur d'Enregistrement, veuillez reessayer plutard.",Snackbar.LENGTH_SHORT).show();

                        }

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (ProtocolException e1) {
                    e1.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //on Ã©tablit un lien avec le script php

                Looper.loop();
            }
        };
        t.start();
    }

    private void valid(final String user, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        //pDialog.setMessage("Enregistrement ...");

        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response);


                try {

                    //<< get json string from server

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String email=jObj.getString("email");
                    id=jObj.getInt("uid");

                    if (!error) {

                        session.createUserLoginSession(String.valueOf(id),user,password);
                        // inserer les donnees de la table cookers dans la sqlite.
                        cookers cook=new cookers(id,user,email,password);
                        EvenementDataBase.insert(cook);

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(liner,"Erreur d'Enregistrement, veuillez reessayer plutard.",Snackbar.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Registration Error: " + error.getMessage());
                Snackbar.make(liner,error.getMessage(),Snackbar.LENGTH_SHORT).show();

                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("name", user);
                params.put("password", password);


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
}