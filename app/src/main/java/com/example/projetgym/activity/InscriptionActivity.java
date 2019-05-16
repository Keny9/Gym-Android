package com.example.projetgym.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.projetgym.MainActivity;
import com.example.projetgym.R;
import com.example.projetgym.app.AppConfig;
import com.example.projetgym.app.AppController;
import com.example.projetgym.helper.SQLiteHandler;
import com.example.projetgym.object.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity {
    private static final String TAG = PrendreRendezVousActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        //SQLite base de donnée gestion
        db = new SQLiteHandler(getApplicationContext());

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setInterfaceInscription();
        setInputs();
        setBottomGone();
        setBoutons();

        //enlève le focus
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
    }



    private void inscrire(){
        final String identifiant = ((TextView)findViewById(R.id.identifiant)).getText().toString();
        final String nom = ((TextView)findViewById(R.id.nom)).getText().toString();
        final String prenom = ((TextView)findViewById(R.id.prenom)).getText().toString();
        final String date_naissance = ((TextView)findViewById(R.id.date_naissance)).getText().toString();
        final String courriel = ((TextView)findViewById(R.id.courriel)).getText().toString();
        final String telephone = ((TextView)findViewById(R.id.telephone)).getText().toString();
        final String mot_de_passe = ((TextView)findViewById(R.id.mot_de_passe)).getText().toString();

        // Tag utilisé pour annuler la requête
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_INSCRIRE_CLIENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Client client = new Client(identifiant,nom,prenom,courriel,0,date_naissance,telephone);

                        db.ajouterClient(client);

                        Toast.makeText(getApplicationContext(), "Votre inscription a été UN SUCCES!!!!!!!!.", Toast.LENGTH_LONG).show();


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
                params.put("identifiant", identifiant);
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("dateNaissance",date_naissance );
                params.put("courriel", courriel);
                params.put("telephone", telephone);
                params.put("motDePasse", mot_de_passe);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Fait en sorte que lorsque le clavier est ouvert, les boutons disparaissent.
     */
    private void setBottomGone(){
        View contentView = (View) findViewById(R.id.inscription_global_layout);
        // ContentView is the root view of the layout of this activity/fragment
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        View contentView = (View) findViewById(R.id.inscription_global_layout);
                        LinearLayout ll = (LinearLayout) findViewById(R.id.insc_boutons);

                        Rect r = new Rect();
                        contentView.getWindowVisibleDisplayFrame(r);
                        int screenHeight = contentView.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;

                        //Log.d(TAG, "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            ll.setVisibility(View.GONE);
                        }
                        else {
                            ll.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * Set les inputs
     */
    private void setInputs(){
        setDateNaissanceInput();
        setTelephoneInput();
        setIdentifiantInput();
        setMotDePasseInput();
        setValideMotDePasseInput();
    }

    private void setDateNaissanceInput(){
        EditText et = (EditText) findViewById(R.id.date_naissance);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputLayout til = (TextInputLayout)    findViewById(R.id.til_date_naissance);
                if (!hasFocus) {
                    til.setErrorEnabled(false);
                }else{

                    til.setErrorEnabled(true);
                    til.setError("Format : jj-mm-aaaa");
                }
            }
        });
    }

    private void setTelephoneInput(){
        EditText et = (EditText) findViewById(R.id.telephone);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputLayout til = (TextInputLayout)    findViewById(R.id.til_telephone);
                if (!hasFocus) {
                    til.setErrorEnabled(false);
                }else{
                    til.setErrorEnabled(true);
                    til.setError("Format : 8191231234");
                }
            }
        });
    }

    private void setIdentifiantInput(){
        EditText et = (EditText) findViewById(R.id.identifiant);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputLayout til = (TextInputLayout)    findViewById(R.id.til_identifiant);
                if (!hasFocus) {
                    til.setErrorEnabled(false);
                }else{
                    til.setErrorEnabled(true);
                    til.setError("Doit contenir au moins 5 caractères.");
                }
            }
        });
    }

    private void setMotDePasseInput(){
        EditText et = (EditText) findViewById(R.id.mot_de_passe);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputLayout til = (TextInputLayout)    findViewById(R.id.til_mot_de_passe);
                if (!hasFocus) {
                    til.setErrorEnabled(false);
                }else{
                    til.setErrorEnabled(true);
                    til.setError("Doit contenir au moins 5 caractères.");
                }
            }
        });
    }

    private void setValideMotDePasseInput(){
        EditText et = (EditText) findViewById(R.id.valider_mot_de_passe);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputLayout til = (TextInputLayout)    findViewById(R.id.til_valider_mot_de_passe);
                if (!hasFocus) {
                    til.setErrorEnabled(false);
                }else{
                    til.setErrorEnabled(true);
                    til.setError("Veuillez re-entrer le mot de passe.");
                }
            }
        });
    }

    /**
     * Set certains attributs pour l'affichage
     */
    private void setInterfaceInscription(){
        LinearLayout content = (LinearLayout) findViewById(R.id.insc_contenu);
        int padding_in_dp = 10;  // 20 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        content.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
    }

    /**
     * Set les boutons
     */
    private void setBoutons(){
        setBtnInscrire();
        setBtnAnnuler();
    }

    private void setBtnInscrire(){
        Button button = (Button) findViewById(R.id.btn_inscrire);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String valide = "";

                valide += validerIdentifiant();
                valide += validerMotDePasse();
                valide += validerPrenom();
                valide += validerNom();
                valide += validerDateNaissance();
                valide += validerTelephone();
                valide += validerCourriel();
                valide += validerConfirmeMotDePsse();

                if(valide != ""){
                    //TODO Insérer client dans la BD centrale et retour à la page login
                    Toast toast = Toast.makeText(getApplicationContext(), valide, Toast.LENGTH_SHORT);
                    toast.show();

                }
                else{
                    inscrire();
                }
            }
        });
    }

    private void setBtnAnnuler(){
        Button button = (Button) findViewById(R.id.btn_annuler);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Aller à l'accueil
                Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String validerIdentifiant(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.identifiant)).getText();

        if(temp == ""){
            return "Veuiller entrer un identifiant.\n";
        }
        if(temp.length() < 5){
            return "L'identifiant doit contenir au moins 5 caractères.\n";
        }
        return "";
    }

    public String validerMotDePasse(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.mot_de_passe)).getText();

        if(temp == ""){
            return "Veuiller entrer un mot de passe.\n";
        }
        if(temp.length() < 5){
            return "Le mot de passe doit contenir au moins 5 caractères.\n";
        }
        return "";
    }

    public String validerPrenom(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.prenom)).getText();

        if(temp == ""){
            return "Veuiller entrer un prénom.\n";
        }
        return "";
    }

    public String validerNom(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.nom)).getText();

        if(temp == ""){
            return "Veuiller entrer un nom.\n";
        }
        return "";
    }

    public String validerDateNaissance(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.date_naissance)).getText();

        if(temp == ""){
            return "Veuiller entrer une date de naissance.\n";
        }
        return "";
    }

    public String validerTelephone(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.telephone)).getText();

        if(temp == ""){
            return "Veuiller entrer un numéro de téléphone.\n";
        }

        int sz = temp.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(temp.charAt(i)) == false) {
                return "Le numéro de telephone doit contenir seulement des chiffres.\n";
            }
        }
        if(sz != 10){
            return "Le numéro de telephone doit contenir 10 chiffres.\n";
        }
        return "";
    }
    public String validerCourriel(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.courriel)).getText();

        if(temp == ""){
            return "Veuiller entrer une addresse courriel.\n";
        }
        return "";
    }
    public String validerConfirmeMotDePsse(){
        String temp = "";
        temp += ((TextView)findViewById(R.id.valider_mot_de_passe)).getText();

        if(temp == ""){
            return "Veuiller confirmer votre mot de passe.\n";
        }
        if(!temp.equals(((TextView)findViewById(R.id.mot_de_passe)).getText().toString())){
            return "Le mot de passe entré dans la validation n'est pas le même que lui entré précédement. \n";
        }
        return "";
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