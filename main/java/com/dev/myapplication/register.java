package com.dev.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity implements View.OnClickListener {
    sessionuser sessionUser;
    MyDataBase db;
    private EditText name, email, password;
    private Button btn;LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        sessionUser = new sessionuser(this);

        db=new MyDataBase(this);

    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.vadider_register){

                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    if (name.getText().toString().trim().length() > 3 && password.getText().toString().trim().length() > 3 ) {
                        if (checkEmail(email.getText().toString().trim())) {
                            valid(name.getText().toString().trim(),password.getText().toString().trim(),email.getText().toString().trim());

                        } else {
                            Snackbar.make(linear,"Entrez un Email valide.",Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(linear,"le nom et le mot de passe doit avoir au moins 4 caracteres",Snackbar.LENGTH_SHORT).show();

                    }
                } else {

                    Snackbar.make(linear,"veuillez Remplir les champs manquants",Snackbar.LENGTH_SHORT).show();
                }
       }
        }

private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        }

public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"
        );
    private void init(){
        name = (EditText)findViewById(R.id.nomedit_register);
        email = (EditText)findViewById(R.id.emailedit_register);
        password = (EditText)findViewById(R.id.passwdedit_register);
        linear=(LinearLayout)findViewById(R.id.linear);

        btn = (Button)findViewById(R.id.vadider_register);
        btn.setOnClickListener(this);
        // checkBox = (CheckBox)findViewById(R.id.checkboxs); checkBox.setOnClickListener(this);
    }

    private void valid(final String name, final String password, final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        //pDialog.setMessage("Enregistrement ...");

        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response);


                try {

                    //<< get json string from server

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Snackbar.make(linear, "nouveau cookers Enregistre.", Snackbar.LENGTH_SHORT).show();
                        // Launch login activity
                        Intent intent = new Intent(
                                getApplicationContext(),
                                login.class);
                        startActivity(intent);
                        finish();
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
                Log.e("TAG", "Registration Error: " + error.getMessage());
                Snackbar.make(linear,error.getMessage(),Snackbar.LENGTH_SHORT).show();

                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("password", password);

                params.put("email", email);

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





