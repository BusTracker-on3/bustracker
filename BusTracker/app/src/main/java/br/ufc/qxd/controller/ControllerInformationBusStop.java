package br.ufc.qxd.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.ufc.qxd.bustracker.HomeProfile;
import br.ufc.qxd.bustracker.R;

public class ControllerInformationBusStop extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.information_about_bus_stop_layout);

        Toolbar toolbar = findViewById(R.id.toolbarInformation_bus_BusStop);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_informations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myCheckinInformation:{
                Intent intent = new Intent(this, HomeProfile.class);
                finish();
                startActivity(intent);
                break;
            }
        }
        return true;
    }
}
