package com.example.projetgym.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;

// Classe pour l'affichage d'UN forfait
public class ConsulterForfaitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_forfait);



        /*
            SET LES BOUTONS
        */
        Button button = (Button) findViewById(R.id.btn_forfait_retour);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retour à l'interface des forfaits
                Intent intent = new Intent(ConsulterForfaitActivity.this, ConsulterForfaitsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
