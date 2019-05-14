package com.example.projetgym.activity;

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

import com.example.projetgym.MainActivity;
import com.example.projetgym.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InscriptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        setInterfaceInscription();
        setInputs();
        setBottomGone();
        setBoutons();

        //enlève le focus
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();
    }



    private void inscrire(){
        try{
            String myUrl = "jdbc:mysql://127.0.0.1/gymcentral?useTimezone=true&serverTimezone=EST";
            ArrayList client = new ArrayList<>();
            Connection c = DriverManager.getConnection(myUrl, "root",
                    "");
            Statement statement = c.createStatement();

            String identifiant = ((TextView)findViewById(R.id.identifiant)).getText().toString();
            String motDePasse = ((TextView)findViewById(R.id.mot_de_passe)).getText().toString();
            String prenom = ((TextView)findViewById(R.id.prenom)).getText().toString();
            String nom = ((TextView)findViewById(R.id.nom)).getText().toString();
            String dateNaissance = ((TextView)findViewById(R.id.date_naissance)).getText().toString();
            String courriel = ((TextView)findViewById(R.id.courriel)).getText().toString();
            String noTel = ((TextView)findViewById(R.id.telephone)).getText().toString();

            String s = "INSERT INTO client VALUES('"+identifiant+"', '"+nom+"', '"+prenom+"', '"+dateNaissance+"', '"+courriel+"', '"+noTel+"', '"+motDePasse+"');";
            ResultSet rs = statement.executeQuery(s);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
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
}