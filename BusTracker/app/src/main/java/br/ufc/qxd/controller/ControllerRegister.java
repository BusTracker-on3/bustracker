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
  //  private Firebase firebase;
    private User user;
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
        //FirebaseApp.initializeApp(ControllerRegister.this);
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
        attemptLoginOrRegister(true);

        User user = new User(name.getText().toString(),
                email.getText().toString(),
                Integer.parseInt(registration.getText().toString()),
                password.getText().toString());

        databaseReference.push().setValue(user);

        

    //    Toast.makeText(this, "entrou", Toast.LENGTH_LONG).show();

    //    this.user = new User();
    //    this.user.setName(this.name.getText().toString());
    //    this.user.setEmail(this.email.getText().toString());
    //    this.user.setRegistration(Integer.parseInt(this.registration.getText().toString()));
    //    this.user.setPassword(this.password.getText().toString());

     //   Toast.makeText(this, this.user.getName(), Toast.LENGTH_LONG).show();
     //  saveUser();
       // Toast.makeText(this, "depois", Toast.LENGTH_LONG).show();
      //  Log.e("teste2", "passou aqui tbm");
        /*
        String password_hash = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

            byte messageDigest[] = algorithm.digest(this.password.getText().toString().getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            password_hash  = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User(this.name.getText().toString(),
                this.email.getText().toString(), Integer.parseInt(this.registration.getText().toString()), password_hash);

        this.databaseReference.push().setValue(user);

    //    User request = new User(this.name.getText().toString(),
    //            this.email.getText().toString(), Integer.parseInt(this.registration.getText().toString()), password_hash);

        Task<AuthResult> userRecord = FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword());

        */
    //    clear_fields();
        Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
        finish();
    }

    /*

    private void saveUser(){
        this.firebase.createUser(this.user.getEmail(), this.user.getPassword(),
                new ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        user.setId(stringObjectMap.get("uid").toString());
                        user.saveDB();
                        showToast("aqui");
                        firebase.unauth();
                        finish();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        showToast(firebaseError.toException().toString());
                        Log.e("ERRROR", firebaseError.toException().toString());
                      //  firebaseError.toException();
                    }
                });
    }
    */

    public void exit_page_register(View view){
        Intent intent = new Intent(ControllerRegister.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.e("Error", message);
    }

    private void attemptLoginOrRegister(boolean isNewUser) {
        if(isNewUser) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(ControllerRegister.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                      //  Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                     //   Log.i("ok ----> ", "ok");
                          mAuth.signOut();
                    //    finish();
                    }else {
                        Log.w("Error", task.getException());
                    }

                }
            });
        }
    }
}
