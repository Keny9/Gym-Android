package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.Cours_list;
import com.example.projetgym.R;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;

public class AccueilActivity extends AppCompatActivity {

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

        btnCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(getApplicationContext(), Cours_list.class);
                startActivity(newActivity);
                finish();
            }
        });

        // SQLite databse
        db = new SQLiteHandler(getApplicationContext());

        // La session
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        //Evenement du click sur le bouton se déconnecter
        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Déconnecte l'utilisateur. Met isLoggedIn a false dans les sharedPreferences
     * et enlève les données de la base de donnée local SQLite de la table client
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteClients();

        // Launching the login activity
        Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
