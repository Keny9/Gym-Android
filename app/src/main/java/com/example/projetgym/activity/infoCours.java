/****************************************
 Fichier : infoCours.java
 Auteur : Guillaume Côté
 Fonctionnalité : Code de l'Activité infoCours. Affiche les infos d'un cours et permet l'inscription
 Date : 2019-05-09

 Vérification :
 Date               Nom                   Approuvé
 =========================================================


 Historique de modifications :
 Date               Nom                   Description
 =========================================================

 ****************************************/

package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetgym.Cours;
import com.example.projetgym.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

public class infoCours extends BaseActivity {
    private static final String TAG = infoCours.class.getSimpleName();
    SessionManager session;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private Button btnRetour;
    private Button btnInscrire;
    Cours event;                        //Object cours venant de l'activité : Cours_list

    /**
     * Création de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cours);

        SessionManager test = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Récupérer le cours cliqué sur la page précédente
        Intent intent = getIntent();
        event = intent.getParcelableExtra("Cours");

        TextView txt = findViewById(R.id.nomCours);
        txt.setText(event.getModele());

        txt = findViewById(R.id.jour);
        txt.setText("Tous les " + event.getJour());

        txt = findViewById(R.id.heure);
        txt.setText("De " + event.getHeure() + " à " + (event.getHeure() + event.getDuree()));

        txt = findViewById(R.id.prix);
        txt.setText(event.getPrix() + "$");

        btnInscrire = findViewById(R.id.inscrire);
        btnRetour = findViewById(R.id.retour);

        session = new SessionManager(getApplicationContext());

        checkInscription();

        clickEvenement();
    }

    /**
     * Génère les clicks listeners sur les boutons de l'Activité
     */
    private void clickEvenement(){

        btnInscrire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                inscrire();
                checkInscription();
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(infoCours.this, Cours_list.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Vérifie si l'utilisateur est inscrit au cours
     */
    private void checkInscription(){
        // Tag used to cancel the request
        String tag_string_req = "req_cours";

        pDialog.setMessage("Chargement en cours...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_COURS_CLIENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    boolean error = jObj.getBoolean("error");

                    if(!error){
                        db.verifierInscrire(event.getIdEvenement(), session.getIdentifiant());

                        // Check for error node in json
                        if (success == 1) {
                            btnInscrire.setText(R.string.desinscrireCours);
                        } else {
                            btnInscrire.setText(R.string.inscrire);
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON erreur
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erreur: 2222 " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_event", event.getIdEvenement());
                params.put("id_client", session.getIdentifiant());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Enregistrer l'inscription du client dans la BD dans la base de donnee centrale
     */
    private void inscrire(){
        try{
            String myUrl = "jdbc:mysql://127.0.0.1/gymcentral?useTimezone=true&serverTimezone=EST";
            Connection c = DriverManager.getConnection(myUrl, "root",
                    "");
            Statement statement = c.createStatement();

            String id_evenement = event.getId();
            String id_client = "Marie1";

        pDialog.setMessage("Registering ...");
        showDialog();

        session = new SessionManager(getApplicationContext());

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_INSCRIRE_COURS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Success response 2: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // L'evenement fait parti de la base de donnee MySQL
                        db.inscrireCours(event, session.getIdentifiant());

                        Toast.makeText(getApplicationContext(), "Vous vous etes inscrit au cours de " + event.getModeleCours(), Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_event", event.getIdEvenement());
                params.put("id_client", session.getIdentifiant());

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
