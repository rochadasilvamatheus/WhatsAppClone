package com.android.matheusrocha.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference database;

    //retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase() {

        if (database == null) {
            database = FirebaseDatabase.getInstance().getReference();
        }

        return database;
    }

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {

        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }


}
