                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package br.edu.uniritter.mobile.aplicacaomodelo.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.adapters.ChatMensagemAdapter;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;
import br.edu.uniritter.mobile.aplicacaomodelo.services.ChatMensagemServices;
import br.edu.uniritter.mobile.aplicacaomodelo.services.FirebaseServices;

public class PrincipalActivity extends AppCompatActivity {


    private ChatMensagemAdapter chatAdapter;
    LinearLayoutManager llm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        setContentView(R.layout.activity_principal);
        RecyclerView rv = findViewById(R.id.rvChat);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //chatAdapter = new ChatMensagemAdapter(new ArrayList<ChatMensagem>());
        //rv.setAdapter(chatAdapter);

        ChatMensagemServices.getChatMensagens(new ChatMensagemServices.ChatMensagensCallback() {
            @Override
            public void OnListChatMensagemCallback(List<ChatMensagem> lista) {
                String txt = "";
                for(ChatMensagem msg : lista) {
                    txt += msg.toString();
                }
                Log.d("Principal", txt);
                chatAdapter = new ChatMensagemAdapter(lista);
                RecyclerView rv = findViewById(R.id.rvChat);
                rv.setLayoutManager(llm);
                rv.setAdapter(chatAdapter);


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
        //wb.loadUrl("https://uniritter.edu.br");


    }



    public void onClickSalva(View v) {
        ChatMensagem chat = new ChatMensagem("Android","teste de mensagem",
                new Timestamp(Calendar.getInstance().getTime()));
        ChatMensagemServices.gravaChatMensagem(chat);
    }

    public void clickNovaMensagem(View v) {
        Toast.makeText(this,"no novo",Toast.LENGTH_LONG).show();
        CardView cv = (CardView) findViewById(R.id.cardMensagem);
        EditText ed = findViewById(R.id.editTextTextMultiLine);
        ed.setText("");
        cv.setVisibility(View.VISIBLE);

    }
    public void clickCancelarMensagem(View v) {
        CardView cv = (CardView) findViewById(R.id.cardMensagem);
        cv.setVisibility(View.INVISIBLE);

    }
    public void clickGravaMensagem(View v) {
        CardView cv = (CardView) findViewById(R.id.cardMensagem);
        EditText ed = findViewById(R.id.editTextTextMultiLine);
        ChatMensagem chat =  new ChatMensagem("Jean Paul", ed.getText().toString(),
                new Timestamp(Calendar.getInstance().getTime()));
        ChatMensagemServices.gravaChatMensagem(chat);
        ed.setText("");
        cv.setVisibility(View.INVISIBLE);
    }

    public void clickLido(View v) {
        Switch sw = (Switch) v;
        ChatMensagem chat = (ChatMensagem) sw.getTag();
        Log.d("Principal",chat.getId()+" "+chat.isLido());
        chat.setLido( sw.isChecked() );
        ChatMensagemServices.gravaChatMensagem(chat);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }
}