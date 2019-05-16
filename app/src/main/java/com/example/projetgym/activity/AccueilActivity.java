package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;

public class AccueilActivity extends BaseActivity {

    private Button btnForfait;
    private Button btnCours;
    private Button btnRendezVous;
    private Button btnPlan;
    private Button btnPref;
    private Button btnDeconnexion;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btnForfait = findViewById(R.id.btnForfait);
        btnCours = findViewById(R.id.btnCours);
        btnRendezVous = findViewById(R.id.btnRendezVous);
        btnPlan = findViewById(R.id.btnPlan);
        btnPref = findViewById(R.id.btnPreference);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);

        // SQLite databse
        db = new SQLiteHandler(getApplicationContext());

        // La session
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        boutonEvenement();
    }

    private void boutonEvenement(){

        //Evenement du click sur le bouton se déconnecter
        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        //Evenement sur le click de rendez-vous
        btnRendezVous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AccueilActivity.this,RendezVousClientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Aller a la page des cours
        btnCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(getApplicationContext(), Cours_list.class);
                startActivity(newActivity);
                finish();
            }
        });

        //Consulter les forfaits
        btnForfait.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ConsulterSonForfaitActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Aller au plan d'entrainement
        btnPlan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), planentrainement.class);
                startActivity(intent);
                finish();
            }
        });

        //Aller a la page des preferences
        btnPref.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), preferences.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Déconnecte l'utilisateur. Met isLoggedIn a false dans les sharedPreferences
     * et enlève les données de la base de donnée local SQLite de la table client
     * */
    private void logoutUser() {
        session.setLogin(false,null);

        db.deleteTables();

        // Launching the login activity
        Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
