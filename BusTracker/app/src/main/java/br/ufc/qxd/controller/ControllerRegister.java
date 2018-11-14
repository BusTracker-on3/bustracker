package br.ufc.qxd.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import br.ufc.qxd.bustracker.MainActivity;
import br.ufc.qxd.bustracker.R;
import br.ufc.qxd.model.User;

public class ControllerRegister extends AppCompatActivity {
    private EditText name, email, registration, password;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        mAuth = FirebaseAuth.getInstance();
        initViews();
        initialize_database();
    }


    private void initialize_database() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDatabase.getReference("Users");
    }

    public void initViews(){
        this.name = findViewById(R.id.user_name);
        this.email = findViewById(R.id.user_email);
        this.registration = findViewById(R.id.user_registration);
        this.password = findViewById(R.id.user_password);
    }

    public void register_new_user(View view){
        if(verifyFields()){
            attemptLoginOrRegister(true);
            User user = new User(name.getText().toString(), email.getText().toString(), Integer.parseInt(registration.getText().toString()), password.getText().toString());
            databaseReference.push().setValue(user);
            Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(this, "Campo vazio*", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            startActivity(intent);
        }
    }

    public void exit_page_register(View view){
        Intent intent = new Intent(ControllerRegister.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void attemptLoginOrRegister(boolean isNewUser) {
        if(isNewUser) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(ControllerRegister.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                          mAuth.signOut();
                    }else {
                        Log.w("Error", task.getException());
                    }
                }
            });
        }
    }

    private boolean verifyFields(){
        if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                registration.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}
