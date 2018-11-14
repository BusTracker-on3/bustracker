package br.ufc.qxd.bustracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufc.qxd.controller.ControllerCheckin;
import br.ufc.qxd.controller.ControllerEditPerfil;
import br.ufc.qxd.controller.ControllerInformationBusStop;
import br.ufc.qxd.model.Checkin;
import br.ufc.qxd.model.User;
import br.ufc.qxd.util.MyCallback;


public class HomeProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUFC, databaseReferenceIFCE, databaseReferencePREF;

    private List<Checkin> checkin;
    private List<Checkin> test;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_profile);

        mAuth = FirebaseAuth.getInstance();

        test = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        verifyNameUser(mAuth.getCurrentUser().getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_checkin: {
                Intent intent = new Intent(HomeProfile.this, ControllerCheckin.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_item_excluir_prefil: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Excluir Conta");
                builder.setMessage("Tem certeza que deseja excluir sua conta permanentemente?")
                        .setCancelable(false)
                        .setPositiveButton("Sim, tenho certeza.", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(mAuth.getCurrentUser().delete().isSuccessful()) {
                                    show("Sua Conta foi excluida com sucesso.");
                                    startActivity();
                                }
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            case R.id.nav_item_information_bus_stop: {
                Intent intent = new Intent(this, ControllerInformationBusStop.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_item_edit_perfil:{
                Intent intent = new Intent(this, ControllerEditPerfil.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_item_logout:{
                mAuth.signOut();
                Intent intent = new Intent(HomeProfile.this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            }
            default: {
                Toast.makeText(this, "Menu Default", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        try {
            KmlLayer layer = new KmlLayer(googleMap, R.raw.map, getApplicationContext());
            layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

          getChekinUFC(new MyCallback() {
              @Override
              public void onCallback(Checkin value) {
                  String title = "Ônibus UFC" + "\n" + "Hora: " + value.getHour() + ", " + "Data: " + value.getDate() +
                          "\n" + "Estado de Lotação do Ônibus: " + value.getCapacity();
                  LatLng bus = new LatLng(Double.parseDouble(value.getLatitude()), Double.parseDouble(value.getLongitude()));
                  googleMap.addMarker(new MarkerOptions().position(bus).title(title));
                  googleMap.moveCamera(CameraUpdateFactory.newLatLng(bus));
                  googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
              }
          });

        getCheckinPREF(new MyCallback() {
            @Override
            public void onCallback(Checkin value) {
                String title = "Ônibus PREFEITURA" + "\n" + "Hora: " + value.getHour() + ", " + "Data: " + value.getDate() +
                        "\n" + "Estado de Lotação do Ônibus: " + value.getCapacity();
                LatLng bus = new LatLng(Double.parseDouble(value.getLatitude()), Double.parseDouble(value.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(bus).title(title));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(bus));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            }
        });

        getCheckinIFCE(new MyCallback() {
            @Override
            public void onCallback(Checkin value) {
                String title = "Ônibus IFCE" + "\n" + "Hora: " + value.getHour() + ", " + "Data: " + value.getDate() +
                        "\n" + "Estado de Lotação do Ônibus: " + value.getCapacity();
                LatLng bus = new LatLng(Double.parseDouble(value.getLatitude()), Double.parseDouble(value.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(bus).title(title));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(bus));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            }
        });
    }

    private void verifyNameUser(final String email){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot users : dataSnapshot.getChildren()){
                    User user = users.getValue(User.class);
                    user.setId(users.getKey());
                    if(user.getEmail().equalsIgnoreCase(email)){
                        TextView user_name = findViewById(R.id.nav_header_user_name);
                        if(user_name != null)
                            user_name.setText(user.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("EXCEPTION", databaseError.getMessage().toString());
            }
        });
    }

    private void getChekinUFC(final MyCallback myCallback){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReferenceUFC = this.firebaseDatabase.getReference("CheckinsBusUFC");
        Query lastQuery = this.databaseReferenceUFC.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot aux : dataSnapshot.getChildren()){
                        Checkin chec = aux.getValue(Checkin.class);
                        chec.setId(aux.getKey());
                        if(chec.getDate().equalsIgnoreCase(getDate())) {
                            myCallback.onCallback(chec);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASEERROR", databaseError.getMessage());
            }
        });
    }

    private void getCheckinPREF(final MyCallback myCallback){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReferenceUFC = this.firebaseDatabase.getReference("CheckinsBusPrefeitura");
        Query lastQuery = this.databaseReferenceUFC.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot aux : dataSnapshot.getChildren()){
                        Checkin chec = aux.getValue(Checkin.class);
                        chec.setId(aux.getKey());
                        myCallback.onCallback(chec);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASEERROR", databaseError.getMessage());
            }
        });
    }

    private void getCheckinIFCE(final MyCallback myCallback){
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReferenceUFC = this.firebaseDatabase.getReference("CheckinsBusIFCE");
        Query lastQuery = this.databaseReferenceUFC.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot aux : dataSnapshot.getChildren()){
                        Checkin chec = aux.getValue(Checkin.class);
                        chec.setId(aux.getKey());
                        myCallback.onCallback(chec);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASEERROR", databaseError.getMessage());
            }
        });
    }

    private void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String getDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return formatDate.format(date);
    }

}