package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;

import android.view.Menu;
import android.widget.Toast;

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

        // SQLite databse
        db = new SQLiteHandler(getApplicationContext());

        // La session
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        btnCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(getApplicationContext(), Cours_list.class);
                startActivity(newActivity);
                finish();
            }
        });

        boutonEvenement();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings){
            Intent newActivity = new Intent(getApplicationContext(), preferences.class);
            startActivity(newActivity);
            finish();
        }else if(item.getItemId() == R.id.help){
            Toast.makeText(this, "Helpppppppppp me", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void boutonEvenement(){

        //Evenement du click sur le bouton se déconnecter
        btnDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

//        //Evenement sur le click de rendez-vous
//        btnRendezVous.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(AccueilActivity.this,RendezVousClientActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    /**
     * Déconnecte l'utilisateur. Met isLoggedIn a false dans les sharedPreferences
     * et enlève les données de la base de donnée local SQLite de la table client
     * */
    private void logoutUser() {
        session.setLogin(false,null);

        db.deleteClients();

        // Launching the login activity
        Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
