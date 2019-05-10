package com.example.projetgym.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetgym.R;

import java.util.Calendar;

public class PrendreRendezVousActivity extends AppCompatActivity {
    private static final String TAG = PrendreRendezVousActivity.class.getSimpleName();

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnRetour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendrerendezvous);

        btnRetour = findViewById(R.id.btnRetourPrendreRendezVous);

        calendrier();

        populateSpinner();

        clickEvenement();
    }

    /**
     * Evenement lors d'un clic
     */
    private void clickEvenement(){

        //Retour en arriere
        btnRetour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PrendreRendezVousActivity.this,RendezVousClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Generer le calendrier et controller les differentes actions
     */
    private void calendrier(){
        mDisplayDate = findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PrendreRendezVousActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG,"onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    //Creer la liste du spinner
    private void populateSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerSpecialiste);
        Spinner spinnerHeure = findViewById(R.id.spinnerHeure);

        // Creer un ArrayAdapter qui utilise l'array de string avec le layout par defaut du spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.specialist,android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Appliquer l'adapter au spinner
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterHeure = ArrayAdapter.createFromResource(this,R.array.heure,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeure.setAdapter(adapterHeure);

    }
}
