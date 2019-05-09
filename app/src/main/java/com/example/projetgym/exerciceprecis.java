package com.example.projetgym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class exerciceprecis extends AppCompatActivity {


    TextView showValueSer;
    TextView showValueRep;
    TextView showValuePause;

    int counterSer=0;
    int counterRep=0;
    int counterPause=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciceprecis);

        showValueSer= (TextView) findViewById(R.id.textView8);
        showValueRep= (TextView) findViewById(R.id.textView10);
        showValuePause= (TextView) findViewById(R.id.textView12);
    }

    public void countInSer(View view)
    {
        counterSer++;
        showValueSer.setText(Integer .toString(counterSer));
    }
    public void countDeSer(View v)
    {
        if(counterSer==0)
        showValueSer.setText(Integer .toString(counterSer));
        else {
            counterSer--;
            showValueSer.setText(Integer .toString(counterSer));
        }
    }
    public void countInRep(View view)
    {
        counterRep++;
        showValueRep.setText(Integer .toString(counterRep));
    }
    public void countDeRep(View v)
    {
        if(counterRep==0)
            showValueRep.setText(Integer .toString(counterRep));
        else {
            counterRep--;
            showValueRep.setText(Integer .toString(counterRep));
        }
    }
    public void countInPause(View view)
    {
        counterPause++;
        showValuePause.setText(Integer .toString(counterPause));
    }
    public void countDePause(View v)
    {
        if(counterPause==0)
            showValuePause.setText(Integer .toString(counterPause));
        else {
            counterPause--;
            showValuePause.setText(Integer.toString(counterPause));
        }
    }
}