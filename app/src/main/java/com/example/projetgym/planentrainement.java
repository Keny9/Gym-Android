package com.example.projetgym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class planentrainement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planentrainement);
        configurePlusButton();
    }

    public void configurePlusButton()
    {
        Button plusButton= (Button) findViewById(R.id.button2);
        plusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(planentrainement.this, categorie.class));
            }
    });
    }
}
