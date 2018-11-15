package br.ufc.qxd.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.ufc.qxd.bustracker.HomeProfile;
import br.ufc.qxd.bustracker.MainActivity;
import br.ufc.qxd.bustracker.R;

public class ControllerEditPerfil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText email;
    private EditText password;
    private EditText oldpassword;

    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.edit_perfil_layout);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        email = findViewById(R.id.user_email_edit);
        password = findViewById(R.id.user_password_edit);
        oldpassword = findViewById(R.id.user_password_edit_old);

        email.setText(mAuth.getCurrentUser().getEmail());

        Toolbar toolbar = findViewById(R.id.toolbarEditPerfil);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myEdit:{
                updatePerfil(oldpassword.getText().toString(), password.getText().toString());
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            case R.id.myBackEdit:{
                finish();
                break;
            }
        }
        return true;
    }

    private void updatePerfil(String oldPassword, final String newPassword){
        String email = mAuth.getCurrentUser().getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                show("Atualização realizada com sucesso.");
                            }
                            else {
                                Log.e("ERROR", "Error");
                            }
                        }
                    });
                }
            }
        });
    }

    private void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
