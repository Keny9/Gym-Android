package com.example.projetgym.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;

// Classe pour l'affichage d'UN forfait
public class ConsulterSonForfaitActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_son_forfait);



        /*
            SET LES BOUTONS
        */
        Button button = (Button) findViewById(R.id.btn_consulter_son_forfait_retour);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retour à l'accueil
                Intent intent = new Intent(ConsulterSonForfaitActivity.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.btn_consulter_son_forfait_consulter_les_forfaits);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // S'en va à l'interface ConsulterForfaits
                Intent intent = new Intent(ConsulterSonForfaitActivity.this, ConsulterForfaitsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
