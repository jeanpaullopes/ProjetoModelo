                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package br.edu.uniritter.mobile.aplicacaomodelo.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        WebView wb = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if((String.valueOf(request.getUrl())).contains("external=true")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    view.getContext().startActivity(intent);
                    return true;
                } else {
                    view.loadUrl(String.valueOf(request.getUrl()));
                }

                return true;
            }
        });
        wb.loadUrl("https://developers.google.com/chart/interactive/docs/gallery/areachart#a-simple-example");

    }
    public void onClickSalva(View v) {
        ChatMensagem chat = new ChatMensagem("Android","teste de mensagem",
                new Timestamp(Calendar.getInstance().getTime()));
        ChatMensagemServices.gravaChatMensagem(chat);
    }
}