package com.example.pongpichai.drvingtests;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CMenu extends Activity {

    private Button ocbMenuTest, ocbMenuScore, ocbMenuSit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wmenu);

        SETView();
        Onclick();
    }

    private void SETView () {

        try {
            ocbMenuTest = (Button) findViewById(R.id.ocbMenuTest);
            ocbMenuScore = (Button) findViewById(R.id.ocbMenuScore);
            ocbMenuSit = (Button) findViewById(R.id.ocbMenuSit);
        }catch (Exception e) {
            Log.e("Fail", "SETView error : " + e.getMessage());
        }
    }

    private void Onclick () {

        try {
            ocbMenuTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent oIntent = new Intent(CMenu.this,C001SelectSec.class);
                    startActivity(oIntent);
                }
            });

            ocbMenuScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ocbMenuSit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } catch (Exception e) {
            Log.e("Fail", "Onclick error : " + e.getMessage());
        }
    }
}
