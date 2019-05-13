package com.example.projetgym.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetgym.R;
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.helper.SessionManager;
import com.example.projetgym.object.Evenement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PrendreRendezVousActivity extends AppCompatActivity {
    private static final String TAG = PrendreRendezVousActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SessionManager session;

    private Spinner spinner;
    private Spinner spinnerHeure;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnRetour;
    private Button btnPrendreRendezVous;
    private TextView tvDate;
    private TextView tvPrix;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendrerendezvous);

        btnRetour = findViewById(R.id.btnRetourPrendreRendezVous);
        btnPrendreRendezVous = findViewById(R.id.btnPrendreRendezVous);
        spinner = findViewById(R.id.spinnerSpecialiste);
        spinnerHeure =  findViewById(R.id.spinnerHeure);
        tvDate = findViewById(R.id.tvDate);
        tvPrix = findViewById(R.id.tvPrix);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database
        db = new SQLiteHandler(getApplicationContext());

        // La session
        session = new SessionManager(getApplicationContext());

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

        btnPrendreRendezVous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String poste = spinner.getSelectedItem().toString();
                String sHeure = spinnerHeure.getSelectedItem().toString();
                int heure = Integer.parseInt(sHeure);
                String date = tvDate.getText().toString();
                int duree = 2;
                String sPrix = tvPrix.getText().toString();
                double prix = Double.parseDouble(sPrix);
                String nom = null;
                String prenom = null;
                String idEmploye = null;
                int idType = 2;
                String modeleCours = "0";
                String idRendezVous = null;

                if(poste.equals("Entraineur")){
                    idEmploye = "jocelyn1";
                }
                else if (poste.equals("Nutritioniste")){
                    idEmploye = "hugo1";
                }
                else if(poste.equals("Massothérapeute")){
                    idEmploye = "elsa1";
                }

                idRendezVous = "R" + heure + idEmploye + date;

                Log.d(TAG,"Voici les infos " + date + " et " + idEmploye + "et " + poste);

                if(!date.equals("")){
                    Evenement rdv = new Evenement(idRendezVous,modeleCours,idType,idEmploye,date,heure,duree,prix,nom,prenom,poste);
                    registerRendezVous(rdv);
                    Intent intent = new Intent(PrendreRendezVousActivity.this, RendezVousClientActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Veuillez entrer toutes les informations de votre rendez-vous.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Enregistrer le rendez-vous dans la base de donnee centrale
     * @param rdv Rendezvous a enregistré
     */
    private void registerRendezVous(final Evenement rdv){

        // Tag utilisé pour annuler la requête
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER_RDV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // L'evenement fait parti de la base de donnee MySQL
                        db.ajouterEvenement(rdv);
                        db.ajouterEventClient(rdv.getIdEvenement(), session.getIdentifiant());

                        Toast.makeText(getApplicationContext(), "Votre rendez-vous a été enregistré.", Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", rdv.getIdEvenement());
                params.put("id_modele", rdv.getModeleCours());
                params.put("id_type", Integer.toString(rdv.getType()));
                params.put("identifiant_employe", rdv.getIdEmploye());
                params.put("date", rdv.getDate());
                params.put("heure", Integer.toString(rdv.getHeure()));
                params.put("duree", Integer.toString(rdv.getDuree()));
                params.put("prix", Double.toString(rdv.getPrix()));
                params.put("id_client", session.getIdentifiant());

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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

                String date = year + "-" + month + "-" + dayOfMonth;
                mDisplayDate.setText(date);
            }
        };

    }

    //Creer la liste du spinner
    private void populateSpinner(){

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
