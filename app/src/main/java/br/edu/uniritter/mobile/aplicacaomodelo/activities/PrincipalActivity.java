                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package br.edu.uniritter.mobile.aplicacaomodelo.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;
import br.edu.uniritter.mobile.aplicacaomodelo.services.ChatMensagemServices;
import br.edu.uniritter.mobile.aplicacaomodelo.services.FirebaseServices;

public class PrincipalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        ChatMensagemServices.getChatMensagens(new ChatMensagemServices.ChatMensagensCallback() {
            @Override
            public void OnListChatMensagemCallback(List<ChatMensagem> lista) {
                String txt = "";
                for(ChatMensagem msg : lista) {
                    txt += msg.toString();
                }
                ((TextView) findViewById(R.id.textView)).setText(txt);
            }
        });



    }
    public void onClickSalva(View v) {
        ChatMensagem chat = new ChatMensagem("Android","teste de mensagem",
                new Timestamp(Calendar.getInstance().getTime()));
        ChatMensagemServices.gravaChatMensagem(chat);
    }
}