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

public class infoCours extends AppCompatActivity {
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
     * Fonction permettant l'inscription à un cours
     */
    private void inscrire(){
        try{
            String myUrl = "jdbc:mysql://127.0.0.1/gymcentral?useTimezone=true&serverTimezone=EST";
            Connection c = DriverManager.getConnection(myUrl, "root",
                    "");
            Statement statement = c.createStatement();

            String id_evenement = event.getId();
            String id_client = "Marie1";

            String s = "INSERT INTO ta_client_evenement VALUES('"+id_evenement+"', '"+id_client+");";
            ResultSet rs = statement.executeQuery(s);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
