package com.example.projetgym.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.projetgym.R;

//Classe pour l'affichage de TOUS les forfaits
public class ConsulterForfaitsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_forfaits);

        Button button = (Button) findViewById(R.id.btn_forfaits_retour);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retour à l'interface des forfaits
                Intent intent = new Intent(ConsulterForfaitsActivity.this, ConsulterSonForfaitActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
