package com.example.projetgym.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projetgym.R;

public class BaseActivity extends AppCompatActivity {

    // Activity code here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings){
            Intent newActivity = new Intent(getApplicationContext(), preferences.class);
            startActivity(newActivity);
            finish();
        }else if(item.getItemId() == R.id.help){
            Toast.makeText(this, "Helpppppppppp me", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}