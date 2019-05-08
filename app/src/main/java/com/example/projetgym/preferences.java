package com.example.projetgym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.view.View;

public class preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    public void displayToastMsg(View v) {

        toastMsg("©Mickey Mouse Development Team, All Rights Reserved");

    }
}
