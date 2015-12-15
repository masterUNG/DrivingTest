package com.example.pongpichai.drvingtests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class C001SelectSec extends Activity {

    private ListView olv001SelectSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w001select_sec);

        C_SETxListView();
        C_SETxOnclick();
    }

    public void C_SETxListView () {

        try {
            olv001SelectSec = (ListView) findViewById(R.id.olv001SelectSec);

            String[] ListItem= new String[] {"หมวดที่1","หมวดที่2","หมวดที่3","หมวดที่4","หมวดที่5","หมวดที่6",
                    "หมวดที่7","หมวดที่8","หมวดที่9","หมวดที่10","หมวดที่11"};

            ArrayAdapter<String> aListAdapter = new ArrayAdapter<String>(C001SelectSec.this,android.R.layout.simple_list_item_1, ListItem);


            olv001SelectSec.setAdapter(aListAdapter);
        } catch (Exception e) {
            Log.e("Fail", "C_SETxListView error : " + e.getMessage());
        }
    }

    private void C_SETxOnclick () {

        try {

            olv001SelectSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean bolStatus = true;
                    Intent oIntent = new Intent(C001SelectSec.this,C002Section.class);
                    if (position == 10) {
                        bolStatus = true;   // นี่มาจากคลิก Itme 11
                    } else {
                        bolStatus = false;
                    }
                    oIntent.putExtra("tSecTion", position+1);
                    oIntent.putExtra("Status", bolStatus);
                    startActivity(oIntent);


                }
            });


        } catch (Exception e) {
            Log.e("Fail","C_SETxONclick error :" + e.getMessage());
        }
    }
}
