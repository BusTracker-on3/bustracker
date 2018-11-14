package br.ufc.qxd.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.ufc.qxd.bustracker.HomeProfile;
import br.ufc.qxd.bustracker.R;
import br.ufc.qxd.model.User;

public class ControllerLogin extends AppCompatActivity {
    private EditText login, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mAuth = FirebaseAuth.getInstance();
        initViews();
    }

    public void initViews() {
        this.login = findViewById(R.id.login);
        this.password = findViewById(R.id.password);
    }

    public boolean verifyFields() {
        if(this.login.getText().toString().isEmpty() || this.password.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    public void makeLogin(View view) {
        if(verifyFields())
            attemptLogin();
        else {
            Toast.makeText(this, "Campo vazio *", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    private void attemptLogin(){
        mAuth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString()).addOnCompleteListener(ControllerLogin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.getResult().getUser() != null) {
                    startActivity(new Intent(ControllerLogin.this, HomeProfile.class));
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Email e/ou senha incorretos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
