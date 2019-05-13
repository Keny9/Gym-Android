package com.example.projetgym.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

import com.example.projetgym.R;

public class preferences extends BaseActivity {

    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        btnRetour = findViewById(R.id.button);

        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),AccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    public void displayToastMsg(View v) {

        toastMsg("©Mickey Mouse Development Team, All Rights Reserved");

    }
}
