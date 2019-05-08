package com.example.projetgym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        setInterfaceInscription();
    }

    private void setInterfaceInscription(){
        LinearLayout content = (LinearLayout) findViewById(R.id.insc_contenu);
        int padding_in_dp = 10;  // 20 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        content.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
    }
}