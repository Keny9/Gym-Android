package com.example.projetgym.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;

// Classe pour l'affichage d'UN forfait
public class ConsulterUnForfaitActivity extends AppCompatActivity {
    private String nomForfait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_un_forfait);

        //nomForfait = nom du forfait selectionné
        //set le text du titre : titre += nom du forfait
        //set le src de l'image du forfait dans le imageview en dessous du titre
        /*
            SET LES BOUTONS
        */
        Button button = (Button) findViewById(R.id.btn_consulter_un_forfait_retour);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retour à l'interface des forfaits
                Intent intent = new Intent(ConsulterUnForfaitActivity.this, ConsulterForfaitsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.btn_consulter_un_forfait_abonner);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //s'abonne au forfait
            }
        });
    }
}
