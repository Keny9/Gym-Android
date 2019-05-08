package com.example.projetgym.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetgym.R;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        setInterfaceInscription();


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
                    Toast toast = Toast.makeText(getApplicationContext(), valide, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void setInterfaceInscription(){
        LinearLayout content = (LinearLayout) findViewById(R.id.insc_contenu);
        int padding_in_dp = 10;  // 20 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        content.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
    }

    public String validerIdentifiant(){
        String temp = "";
        String erreur = "";
        temp += ((TextView)findViewById(R.id.identifiant)).getText();

        if(temp == ""){
            erreur = "Veuiller entrer un identifiant \n";
            return erreur;
        }
        if(temp.length() < 5){
            erreur = "L'identifiant doit contenir au moins 5 caractères";
            return "L'identifiant doit contenir au moins 5 caractères\n";
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