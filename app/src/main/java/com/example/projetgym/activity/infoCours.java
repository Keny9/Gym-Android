package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetgym.Cours;
import com.example.projetgym.R;

public class infoCours extends AppCompatActivity {
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cours);

        //Récupérer le cours cliqué sur la page précédente
        Intent intent = getIntent();
        Cours event = intent.getParcelableExtra("Cours");

        TextView txt = findViewById(R.id.nomCours);
        txt.setText(event.getModele());

        txt = findViewById(R.id.description);
        txt.setText(event.getDescription());

        txt = findViewById(R.id.jour);
        txt.setText("Tous les " + event.getJour());

        txt = findViewById(R.id.heure);
        txt.setText("De " + event.getHeure() + " à " + (event.getHeure() + event.getDuree()));

        txt = findViewById(R.id.prix);
        txt.setText(event.getPrix() + "$");

        btnRetour = findViewById(R.id.retour);
    }

    //Gere tous clicks possibles sur la page
    private void clickEvenement(){
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(infoCours.this, Cours_list.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
