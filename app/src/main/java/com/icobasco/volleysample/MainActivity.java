package com.icobasco.volleysample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String URL_STRING = "https://github.com/icobasco/json_fake_rubrica";
    public static final String URL_OBJ = "https://www.mocky.io/v2/5185415ba171ea3a00704eed";
    // Oppure utilizzare pagina statica con json su Mocky.io
    public static final String TAG = "MainActivity";

    private RequestQueue miaCoda = null;
    private Button btString = null;
    private Button btRequestJson = null;
    private Button btRequestArrayJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miaCoda = Volley.newRequestQueue(this);
        btString = findViewById(R.id.btRequestString);
        btRequestJson = findViewById(R.id.btRequestJson);
        btRequestArrayJson = findViewById(R.id.btRequestArrayJson);
    }

    @Override
    protected void onResume() {
        super.onResume();



        btString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Costruttore con 4 parametri:
                        1) Metodo
                        2) URL
                        3) Listener (x gestire quando funziona)
                        4) Listener (x gestire quando non funziona)
                */
                StringRequest miaRequest = new StringRequest(
                        Request.Method.GET,
                        URL_STRING,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "Response: " + response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "ERROR: " + error.getMessage());
                            }
                        }
                );
                miaCoda.add(miaRequest);
            }
        });


        btRequestJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    Dalla versione 8.0 (Oreo), mettere i permessi nel Manifest
                        android:usesCleartextTraffic="true"
                */
                /**
                 * Metodo
                 * URL
                 * Parametri che vogliamo inviare all'endpoint
                 */
                JsonObjectRequest jor = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_OBJ,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String surname = "";
                                try {
                                    surname = response.getString("cognome");
                                    Log.d(TAG, "Cognome: " + surname);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "ERROR: " + error.getMessage());
                            }
                        }
                );
            }
        });

        btRequestArrayJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                JsonArrayRequest jar = new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray >() {
                            @Override
                            public void onResponse(JSONArray response) {
                                String surname;
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject obj = response.getJSONObject(i);
                                        surname = obj.getString("cognome");
                                        Log.d(TAG, "Cognome: " + surname);
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }




                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "ERROR: " + error.getMessage());
                            }
                        }
                );

            }
        });


    }
}
