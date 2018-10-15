package com.dev.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.dev.myapplication.objet.ingredient;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class post_recette extends AppCompatActivity {

    private EditText name,duration,description,guest,ingredients,quantite;
    private ImageView imgview;
    Uri photoUri;
    public String img,nom,dur,desc,gues,nom_user,ingr,qte;
    Button pub,add_ingr;LinearLayout rel; String id = "5";
    Bitmap selectedImage;
    List<ingredient> mReceivers;
    sessionuser ses;TextView txt;
    private ingr_adapter AdIngr;
    RecyclerView IngrRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recette);
        ingr= "";
        mReceivers=new ArrayList<>();
        name=(EditText)findViewById(R.id.nameedit);
        duration=(EditText)findViewById(R.id.duredit);
        description=(EditText)findViewById(R.id.descedit);
        guest=(EditText)findViewById(R.id.guesedit);
        rel=(LinearLayout)findViewById(R.id.post_recette);
        ingredients=(EditText) findViewById(R.id.ingredit);
        quantite=(EditText) findViewById(R.id.qteedit);
        IngrRv=(RecyclerView)findViewById(R.id.ingr_rv);

        add_ingr=(Button)findViewById(R.id.add_ingr_btn);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        IngrRv.setLayoutManager(mLayoutManager);
        IngrRv.setItemAnimator(new DefaultItemAnimator());

        AdIngr= new ingr_adapter(post_recette.this, mReceivers);

        IngrRv.setAdapter(AdIngr);

        /*ingredients.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == keyEvent.ACTION_DOWN) && (i==keyEvent.KEYCODE_ENTER)){
                    ingr = ingr + ingredients.getText().toString().trim() + "; " ;

                    Toast.makeText(getApplicationContext(),ingr,Toast.LENGTH_LONG).show();
                    txt.setVisibility(View.VISIBLE);
                    ingredients.setVisibility(View.INVISIBLE);
                    quantite.setVisibility(View.VISIBLE);
                    txt.setText(ingr);

                    return true;
                }
                return false;
            }
        });

         quantite.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == keyEvent.ACTION_DOWN) && (i==keyEvent.KEYCODE_ENTER)){

                    ingr = ingr + quantite.getText().toString().trim() + "/" ;

                    Toast.makeText(getApplicationContext(),ingr,Toast.LENGTH_LONG).show();
                    txt.setVisibility(View.VISIBLE);
                    ingredients.setVisibility(View.VISIBLE);
                    quantite.setVisibility(View.INVISIBLE);

                    return true;
                }
                return false;
            }
        });*/

        imgview=(ImageView)findViewById(R.id.image_view_autocolaps_background);

        ses=new sessionuser(post_recette.this);
        HashMap<String, String> user = ses.getUserDetails();
        nom_user = user.get("name");
        //id=user.get("id");
        add_ingr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ingredients.getText().toString().trim().isEmpty()) {
                if(!quantite.getText().toString().trim().isEmpty()) {
                    ingredient i = new ingredient();
                    i.setName(ingredients.getText().toString().trim());
                    i.setQuantity(quantite.getText().toString().trim());
                    mReceivers.add(i);
                }else{
                    Snackbar.make(rel, "ajoutez la quantite", Snackbar.LENGTH_SHORT).show();
                }
                }else{
                    Snackbar.make(rel, "ajoutez l'ingredient", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        pub=(Button)findViewById(R.id.publier);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 14);
            }
        });

     pub.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             nom=name.getText().toString();
             dur=duration.getText().toString();
             desc=description.getText().toString();
             gues=guest.getText().toString();

             if (photoUri!=null) {
                 img = getStringImage(selectedImage);
                 if (!nom.isEmpty() && !dur.isEmpty() && !desc.isEmpty() && !gues.isEmpty()) {
                     // envoi_msg(nom,dur,desc,gues);
                     Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
                     options.height = 100;//some compression configuration.

                     try {
                         Bitmap bitmap = MediaStore.Images.Media.getBitmap(post_recette.this.getContentResolver(), photoUri);
                         // Upload(userModel, getStringImage(bitmap));
                         Tiny.getInstance().source(bitmap).asBitmap().withOptions(options).compress(new BitmapCallback() {
                             @Override
                             public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                                 //return the compressed bitmap object
                                // Upload(userModel, getStringImage(bitmap));
                                 Snackbar.make(rel, "start", Snackbar.LENGTH_SHORT).show();
                                 envoi_msg(nom,dur,desc,gues, getStringImage(bitmap));
                             }
                         });


                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                 } else {
                     Snackbar.make(rel, "remplissez le champ vide", Snackbar.LENGTH_SHORT).show();
                 }
             }else {
                 Snackbar.make(rel, "image null", Snackbar.LENGTH_SHORT).show();
             }
         }
     });
    }

    private void envoi_msg(final String nom, final String dur, final String desc, final String gues, final String stringImage) {

        for (int a=0; a<mReceivers.size();a++ ){

            ingr = ingr + mReceivers.get(a).getQuantity()+"/"+ mReceivers.get(a).getName() +";";

        }
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("testr", "Register Response: " + response);

                try {
                    //<< get json string from server
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Snackbar.make(rel, "succes", Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(rel, errorMsg, Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(post_recette.this,
                            "error_network_timeout",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(post_recette.this,
                            "error_AuthFailureError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(post_recette.this,
                            "error_ServerError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(post_recette.this,
                            "error_NetworkError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(post_recette.this,
                            "error_ParseError",
                            Toast.LENGTH_LONG).show();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams()  {


                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("cookers_id",id);
                params.put("name", nom);
                params.put("duration", dur);
                params.put("description", desc);
                params.put("guest", gues);
                params.put("ingr", ingr);
                params.put("image", stringImage);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                photoUri = imageUri;
                 selectedImage = BitmapFactory.decodeStream(imageStream);
                imgview.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Snackbar.make(rel,"Something went wrong",Snackbar.LENGTH_SHORT).show();
                }
        }else {
            Snackbar.make(rel,"vous n'avez pas choisi l'image",Snackbar.LENGTH_SHORT).show();
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
