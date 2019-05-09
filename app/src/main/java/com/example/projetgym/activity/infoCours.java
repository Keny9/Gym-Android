package com.example.projetgym;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class infoCours extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cours);

        //Récupérer le cours cliquer sur la page précédente
        Intent intent = getIntent();
        Evenement event = intent.getParcelableExtra("Cours");

        TextView txt = findViewById(R.id.textView);
        txt.setText(event.getIdentifiant_employe());
    }


}
