package br.ufc.qxd.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.ufc.qxd.bustracker.R;
import br.ufc.qxd.model.User;

public class ControllerRegister extends Activity {
    private EditText name, email, registration, password;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        this.name = findViewById(R.id.user_name);
        this.email = findViewById(R.id.user_email);
        this.registration = findViewById(R.id.user_registration);
        this.password = findViewById(R.id.user_password);

        initialize_database();
    }

    private void initialize_database() {
        FirebaseApp.initializeApp(ControllerRegister.this);
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDatabase.getReference("Users");
    }

    public void register_new_user(View view){
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
        clear_fields();
        Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void clear_fields(){
        this.name.setText("");
        this.email.setText("");
        this.registration.setText("");
        this.password.setText("");
    }
}
