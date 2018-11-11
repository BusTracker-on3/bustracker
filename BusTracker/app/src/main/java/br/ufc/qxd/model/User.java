package br.ufc.qxd.model;

import android.content.Context;
import android.media.session.MediaSession;

import com.firebase.client.Firebase;

import br.ufc.qxd.util.LibraryClass;

public class User {
    private String id;
    private String name;
    private String email;
    private int registration;
    private String password;

    public User() {}

    public User(String id, String name, String email, int registration, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registration = registration;
        this.password = password;
    }

    public User(String name, String email, int registration, String password) {
        this.name = name;
        this.email = email;
        this.registration = registration;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
