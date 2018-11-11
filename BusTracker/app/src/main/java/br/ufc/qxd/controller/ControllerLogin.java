package br.ufc.qxd.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.ufc.qxd.bustracker.HomeProfile;
import br.ufc.qxd.bustracker.R;
import br.ufc.qxd.model.User;
import br.ufc.qxd.util.LibraryClass;

public class ControllerLogin extends AppCompatActivity {
    private EditText login, password;
    private User user;
    private FirebaseAuth mAuth;
    // private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mAuth = FirebaseAuth.getInstance();

        //    this.firebase = LibraryClass.getFirebase();
            initViews();
        //    verifyUserLogged();
    }

    public void initViews() {
        this.login = findViewById(R.id.login);
        this.password = findViewById(R.id.password);
    }

    public void initUser() {
        this.user = new User();
        this.user.setEmail(this.login.getText().toString());
        this.user.setPassword(this.password.getText().toString());
    }

    public void makeLogin(View view) {
        attemptLogin();
        // initUser();
        // verifyLogin();
        // Intent intent = new Intent(ControllerLogin.this, HomeProfile.class);
        //startActivity(intent);
    }

    /*
    private void verifyUserLogged(){
        if(firebase.getAuth() != null){
            callMainActivity();
        } else{
            initUser();
            if(!this.user.getTokenSP(this).isEmpty()){
                firebase.authWithPassword("password", this.user.getTokenSP(this),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                user.saveTokenSP(ControllerLogin.this, authData.getToken());
                                callMainActivity();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {}
                        });
            }
        }
    }

    private void verifyLogin(){
        firebase.authWithPassword(this.user.getEmail(), this.user.getPassword(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        user.saveTokenSP(ControllerLogin.this, authData.getToken());
                        callMainActivity();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        firebaseError.getMessage();
                    }
                });
    }

    private void callMainActivity(){
        Intent intent = new Intent(this, HomeProfile.class);
        startActivity(intent);
    }
    */

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
