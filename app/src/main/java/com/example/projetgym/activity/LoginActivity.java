package com.example.projetgym.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.projetgym.R;
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button btnLogin;
    private Button btnInscrire;
    private EditText inputIdentifiant;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputIdentifiant = findViewById(R.id.identifiant);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnInscrire = findViewById(R.id.btnInscrire);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());

        //Verifier si l'utilisateur est deja connecté ou pas
        if(session.isLoggedIn()){
            //L'utilisateur est deja connecter, l'amener a l'accueil
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Click event login button
        btnLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                String identifiant = inputIdentifiant.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!identifiant.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(identifiant, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Veuillez remplir les champs.", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void checkLogin(final String identifiant, final String password){
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Connexion en cours...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // L'utilisateur a reussi a se connecter
                        // Creer la session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        JSONObject client = jObj.getJSONObject("client");
                        String identifiant = client.getString("identifiant");
                        String nom = client.getString("nom");
                        String prenom = client.getString("prenom");
                        String idForfait = client.getString("id_forfait");
                        String dateNaissance = client.getString("date_naisance");
                        String courriel = client.getString("courriel");
                        String telephone = client.getString("telephone");

                        // Insérer le client dans la table client
                        db.ajouterClient(identifiant,nom,prenom,courriel,idForfait,dateNaissance,telephone);

                        // Aller à l'accueil
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //Erreur dans la connexion, obtenir le message d'erreur
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON erreur
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur de connexion: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("identifiant", identifiant);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
