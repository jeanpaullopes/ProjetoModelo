                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package br.edu.uniritter.mobile.aplicacaomodelo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;
import br.edu.uniritter.mobile.aplicacaomodelo.services.FirebaseServices;

public class PrincipalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (1 == 2) {
            setContentView(R.layout.activity_principal);

        } else {
            setContentView(R.layout.produto_tipo_1);
            findViewById(R.id.textView3);
        }
        FirebaseFirestore store = FirebaseServices.getFirebaseFirestoreInstance();

        Query query = store.collection("chat").orderBy("datahora").limit(100);
        query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        List<ChatMensagem> mensagens = value.toObjects(ChatMensagem.class);
                        Log.d("firestore",value.getDocuments().toString());
                        Log.d("firestore",mensagens.size()+"");
                        //Toast.makeText(getApplicationContext(),mensagens.get(0).getMensagem(), Toast.LENGTH_LONG).show();
                        String corpo = "";
                        for (ChatMensagem msg : mensagens) {
                            corpo += msg.toString();
                        }
                        ((TextView)findViewById(R.id.textView)).setText(corpo);

                    }
                }
        );
        query.get();



    }
}