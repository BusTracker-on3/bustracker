package br.ufc.qxd.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.ufc.qxd.bustracker.HomeProfile;
import br.ufc.qxd.bustracker.R;
import br.ufc.qxd.model.Checkin;

public class ControllerCheckin extends AppCompatActivity {
    private RadioButton radioSquare;
    private RadioButton radioAnexo;
    private RadioButton radioConstrutec;
    private RadioButton radioBikeShop;
    private RadioButton radioUFC;
    private RadioButton radioIFCE;
    private RadioButton radioIgrejaAdv;
    private RadioButton radioLiquigas;
    private RadioButton radioRodoviaria;
    private RadioButton radioNotRoute;
    private RadioButton radioBusUFC;
    private RadioButton radioBusPrefeitura;
    private RadioButton radioBusIFCE;
    private RadioButton capBusLotado;
    private RadioButton capBusMedio;
    private RadioButton capBusVago;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUFC, databaseReferenceIFCE, databaseReferencePREF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin_layout);

        Toolbar toolbar = findViewById(R.id.toolbarCheckin);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        initRadios();
        initialize_database();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checkin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myBack:{
                finish();
                return true;
            }
            case R.id.myCheckin:{
                if(radioSquare.isChecked()){
                    //-4.97018, -39.01586
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97018", "-39.01586"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97018", "-39.01586"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97018", "-39.01586"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioAnexo.isChecked()){
                    // -4.97015, -39.01958
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97015", "-39.01958"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97015", "-39.01958"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97015", "-39.01958"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioConstrutec.isChecked()){
                    // -4.97026, -39.02443
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97026", "-39.02443"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97026", "-39.02443"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97026", "-39.02443"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioBikeShop.isChecked()){
                    // -4.97071, -39.02546
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97071", "-39.02546"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97071", "-39.02546"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97071", "-39.02546"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioUFC.isChecked()){
                    // -4.9795, -39.05627
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.9795", "-39.05627"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.9795", "-39.05627"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.9795", "-39.05627"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioIFCE.isChecked()){
                    // -4.97808, -39.05783
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97808", "-39.05783"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97808", "-39.05783"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97808", "-39.05783"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioIgrejaAdv.isChecked()){
                    // -4.97269, -39.02257
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97269", "-39.02257"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97269", "-39.02257"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97269", "-39.02257"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioLiquigas.isChecked()){
                    // -4.97272, -39.01791
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97272", "-39.01791"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97272", "-39.01791"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97272", "-39.01791"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else if(radioRodoviaria.isChecked()){
                    // -4.97253, -39.01664
                    if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus UFC")){
                        databaseReferenceUFC.push().setValue(getCheckin("-4.97253", "-39.01664"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus Prefeitura")){
                        databaseReferencePREF.push().setValue(getCheckin("-4.97253", "-39.01664"));
                        sucessMessage();
                    }
                    else if(verifyRadioBusCheked().equalsIgnoreCase("Ônibus IFCE")){
                        databaseReferenceIFCE.push().setValue(getCheckin("-4.97253", "-39.01664"));
                        sucessMessage();
                    }
                    else {
                        checkRadios();
                    }
                }
                else {
                    checkRadios();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRadios(){
        radioSquare = findViewById(R.id.praca_leao);
        radioAnexo = findViewById(R.id.anexo);
        radioConstrutec = findViewById(R.id.construtec);
        radioBikeShop = findViewById(R.id.bikeShop);
        radioUFC = findViewById(R.id.ufc);
        radioIFCE = findViewById(R.id.ifce);
        radioIgrejaAdv = findViewById(R.id.igrejaAdventista);
        radioLiquigas = findViewById(R.id.liquigas);
        radioRodoviaria = findViewById(R.id.rodoviaria);
        radioNotRoute = findViewById(R.id.notRoute);
        radioBusUFC = findViewById(R.id.busUfc);
        radioBusPrefeitura = findViewById(R.id.busPrefeitura);
        radioBusIFCE = findViewById(R.id.busIFCE);
        capBusLotado = findViewById(R.id.capLotado);
        capBusMedio = findViewById(R.id.capMedio);
        capBusVago = findViewById(R.id.capVago);
    }

    private String verifyRadioBusCheked(){
        if(radioBusUFC.isChecked())
            return "Ônibus UFC";
        else if(radioBusPrefeitura.isChecked())
            return "Ônibus Prefeitura";
        else if(radioBusIFCE.isChecked())
            return "Ônibus IFCE";
        return null;
    }

    private String verifyRadioCapBusChecked(){
        if(capBusLotado.isChecked())
            return "Lotado";
        else if(capBusMedio.isChecked())
            return "Medio";
        else if(capBusVago.isChecked())
            return "Vago";
        return null;
    }

    private String getHour(){
        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date date_current = cal.getTime();
        return dateFormatHour.format(date_current);
    }

    private String getDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return formatDate.format(date);
    }

    private void initialize_database() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReferenceUFC = this.firebaseDatabase.getReference("CheckinsBusUFC");
        this.databaseReferenceIFCE = this.firebaseDatabase.getReference("CheckinsBusIFCE");
        this.databaseReferencePREF = this.firebaseDatabase.getReference("CheckinsBusPrefeitura");
    }

    private void sucessMessage(){
        Toast.makeText(this, "Checkin realizado sucesso.", Toast.LENGTH_LONG).show();
        finish();
        refreshHome();
    }

    private Checkin getCheckin(String latitude, String longitude){
        Checkin checkin = new Checkin(mAuth.getCurrentUser().getUid().toString(), latitude, longitude, verifyRadioBusCheked(), getDate(), getHour(), verifyRadioCapBusChecked());
        return checkin;
    }

    private void checkRadios(){
        Toast.makeText(this, "É necessário selecionar um lugar e um ônibus !!!", Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void refreshHome(){
        Intent intent = new Intent(this, HomeProfile.class);
        startActivity(intent);
    }

}
