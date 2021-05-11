package br.edu.uniritter.mobile.aplicacaomodelo.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

public class FirebaseServices {
    private static FirebaseAuth fbAuth;
    private static FirebaseUser fbUser;
    private static FirebaseFirestore fbStore;

    public static FirebaseAuth getFirebaseAuthInstance() {
        if (fbAuth == null) {
            fbAuth = FirebaseAuth.getInstance();
        }
        return fbAuth;
    }

    public static FirebaseFirestore getFirebaseFirestoreInstance() {
        if (fbStore == null) {
            fbStore = FirebaseFirestore.getInstance();
        }
        return fbStore;
    }

    public static void setFirebaseUser(FirebaseUser fbu) {
        fbUser = fbu;
    }
    public static FirebaseUser getFirebaseUser() {
        return fbUser;
    }
}
