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
import br.edu.uniritter.mobile.aplicacaomodelo.model.Address;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;
import br.edu.uniritter.mobile.aplicacaomodelo.model.Geo;
import br.edu.uniritter.mobile.aplicacaomodelo.model.User;
import br.edu.uniritter.mobile.aplicacaomodelo.presenters.MainPresenter;
import br.edu.uniritter.mobile.aplicacaomodelo.presenters.MainPresenterContrato;
import br.edu.uniritter.mobile.aplicacaomodelo.services.ChatMensagemServices;
import br.edu.uniritter.mobile.aplicacaomodelo.services.FirebaseServices;

public class PrincipalActivity extends AppCompatActivity
              implements MainPresenterContrato.ViewContrato {


    private ChatMensagemAdapter chatAdapter;


    private MainPresenterContrato.PresenterContrato presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.presenter = new MainPresenter(this);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        /*
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
        */

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.buscarDados();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter = null;
    }

    public void onClickSalva(View v) {
        //ChatMensagem chat = new ChatMensagem("Android","teste de mensagem",
        //        new Timestamp(Calendar.getInstance().getTime()));
        //ChatMensagemServices.gravaChatMensagem(chat);
        presenter.gravaMensagem("Android","teste de mensagem");
    }

    public void clickNovaMensagem(View v) {
        mostraEditMensagem();

    }
    public void clickCancelarMensagem(View v) {
        escondeEditMensagem();

    }
    public void clickGravaMensagem(View v) {
        EditText ed = findViewById(R.id.editTextTextMultiLine);
        presenter.gravaMensagem("Jean Paul", ed.getText().toString());
        escondeEditMensagem();
    }

    @Override
    public void escondeEditMensagem() {
        EditText ed = findViewById(R.id.editTextTextMultiLine);
        ed.setText("");
        CardView cv = (CardView) findViewById(R.id.cardMensagem);
        cv.setVisibility(View.INVISIBLE);

    }

    @Override
    public void mostraEditMensagem() {
        CardView cv = (CardView) findViewById(R.id.cardMensagem);
        cv.setVisibility(View.VISIBLE);

    }

    public void clickLido(View v) {
        Switch sw = (Switch) v;
        ChatMensagem chat = (ChatMensagem) sw.getTag();
        presenter.marcaLido(chat, sw.isChecked());
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    @Override
    public void carregaRecyclerView(List<ChatMensagem> mensagens) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        RecyclerView rv = findViewById(R.id.rvChat);
        rv.setLayoutManager(llm);
        chatAdapter = new ChatMensagemAdapter(mensagens);
        rv.setAdapter(chatAdapter);
    }
}