package br.edu.uniritter.mobile.aplicacaomodelo.services;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;

public class ChatMensagemServices {

    public static void getChatMensagens(ChatMensagensCallback callback) {
        FirebaseFirestore store = FirebaseServices.getFirebaseFirestoreInstance();

        Query query = store.collection("chat").orderBy("datahora").limit(100);
        query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("ChatMensagemServices",error.getMessage());
                            return;
                        }
//                        value.getDocuments().toString();
                        List<ChatMensagem> mensagens = value.toObjects(ChatMensagem.class);
                        Log.d("firestore",value.getDocuments().toString());
                        Log.d("firestore",mensagens.size()+"");
                        //Toast.makeText(getApplicationContext(),mensagens.get(0).getMensagem(), Toast.LENGTH_LONG).show();
                       callback.OnListChatMensagemCallback(mensagens);

                    }
                }
        );

    }
    public interface ChatMensagensCallback {

        public abstract void OnListChatMensagemCallback(List<ChatMensagem> lista ) ;
    }

    public static void gravaChatMensagem(ChatMensagem msg) {
        FirebaseFirestore store = FirebaseServices.getFirebaseFirestoreInstance();
        // teste para saber se adiciona ou muda
        if(msg.getId() == null) {
            store.collection("chat").add(msg)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("firestore", "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("firestore", "Error adding document", e);
                        }
                    });
        } else {
            store.collection("chat").document(msg.getId()).set(msg);
        }
    }
}
