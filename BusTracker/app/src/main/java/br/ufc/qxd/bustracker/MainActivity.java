package br.ufc.qxd.bustracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.ufc.qxd.controller.ControllerRegister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void get_register(View view){
        Intent intent = new Intent(MainActivity.this, ControllerRegister.class);
        startActivity(intent);
    }
}
